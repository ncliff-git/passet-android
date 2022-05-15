package com.github.ncliff.passet.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.PasswordManager
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.databinding.FragmentNoteSettingsBinding
import com.github.ncliff.passet.presentation.models.SharedDatabaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteSettingsFragment : Fragment(R.layout.fragment_note_settings) {
    private var _binding: FragmentNoteSettingsBinding? = null
    private val binding get() = _binding!!
    private var longitude: Double? = null
    private var latitude: Double? = null
    private var noteId: Int = -1
    private var bookmark = false
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedDatabaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteSettingsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        arguments?.getInt("note_id")?.let {
            noteId = it
        }
        completionFields()
        initPasswordGen()
        initDatePicker()
        initLocation()
        return _binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.account_setting_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                when (checkAllFields()) {
                    true -> {
                        if (noteId == -1) {
                            viewModel.insert(setBindingToNote()) {}
                        } else {
                            viewModel.update(setBindingToNote(noteId = noteId)) {}
                        }
                        findNavController().navigateUp()
                        true
                    }
                    false -> false
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Получение данных из БД по id
     */
    private fun completionFields() {
        if (noteId != -1)
            viewModel.findNoteById(noteId).observe(viewLifecycleOwner) {
                setNoteToBinding(it)
            }
    }

    /**
     * Проверяет все поля на корректность данных
     */
    private fun checkAllFields(): Boolean {
        if (binding.textFieldNameEdit.text.isNullOrEmpty()) {
            binding.textFieldName.error = resources.getText(R.string.must_be_filled)
        }
        if (binding.textFieldLoginEdit.text.isNullOrEmpty()) {
            binding.textFieldLogin.error = resources.getText(R.string.must_be_filled)
        }
        if (binding.textFieldPasswordEdit.text.isNullOrEmpty()) {
            binding.textFieldPassword.error = resources.getText(R.string.must_be_filled)
        }
        if (binding.editTextDateStart.text.isNullOrEmpty() || binding.editTextDateEnd.text.isNullOrEmpty()) {
            Toast.makeText(context, R.string.need_select_dates, Toast.LENGTH_LONG).show()
        }

        if (binding.textFieldNameEdit.text.isNullOrEmpty() ||
            binding.textFieldLoginEdit.text.isNullOrEmpty() ||
            binding.textFieldPasswordEdit.text.isNullOrEmpty() ||
            binding.editTextDateStart.text.isNullOrEmpty() ||
            binding.editTextDateEnd.text.isNullOrEmpty()
        ) {
            return false
        }
        return true
    }

    /**
     * Переносит данные из объекта Note в поля фрагмена
     */
    private fun setNoteToBinding(note: Note) {
        binding.textFieldNameEdit.setText(note.name)
        binding.textFieldLoginEdit.setText(note.login)
        binding.textFieldPasswordEdit.setText(note.password)
        binding.editTextDateStart.setText(note.dateStart)
        binding.editTextDateEnd.setText(note.dateEnd)
        bookmark = note.bookworm
        if (longitude == null && latitude == null) {
            longitude = note.locationLongitude
            latitude = note.locationLatitude
        }
        if (longitude != null && latitude != null) {
            binding.locationTextView.text = "$longitude $latitude"
        }
    }

    /**
     * Запонляет все данные из полей в Note
     */
    private fun setBindingToNote(noteId: Int? = null): Note {
        Log.d("List", "$longitude $latitude")
        return Note(
            id = noteId,
            name = binding.textFieldNameEdit.text.toString(),
            login = binding.textFieldLoginEdit.text.toString(),
            password = binding.textFieldPasswordEdit.text.toString(),
            dateStart = binding.editTextDateStart.text.toString(),
            dateEnd = binding.editTextDateEnd.text.toString(),
            bookworm = bookmark,
            locationLongitude = longitude,
            locationLatitude = latitude,
        )
    }

    // region Password generate

    /**
     * генерация паролей
     */
    private fun initPasswordGen() = with(binding) {
        numberPicker.maxValue = 32
        numberPicker.minValue = 6
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker.wrapSelectorWheel = false
        numberPicker.value = 12

        checkRadioButtonsPressed()
        cbNumbers.setOnClickListener { checkRadioButtonsPressed() }
        cbLowercase.setOnClickListener { checkRadioButtonsPressed() }
        cbUppercase.setOnClickListener { checkRadioButtonsPressed() }
        cbSpecialSymbols.setOnClickListener { checkRadioButtonsPressed() }
        generateButton.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val additionalSpecialSymbols = sp.getBoolean("additional_characters", false)
            textFieldPasswordEdit.setText(
                PasswordManager.generatePassword(
                    numberPicker.value,
                    cbNumbers.isChecked,
                    cbUppercase.isChecked,
                    cbLowercase.isChecked,
                    cbSpecialSymbols.isChecked,
                    additionalSpecialSymbols
                )
            )
        }
    }

    /**
     * Установка значения true для первого чекбокса если всех чекбоксах установлен false
     */
    private fun checkRadioButtonsPressed() {
        if (!(binding.cbNumbers.isChecked
                    || binding.cbLowercase.isChecked
                    || binding.cbUppercase.isChecked
                    || binding.cbSpecialSymbols.isChecked)
        ) {
            binding.cbNumbers.isChecked = true
        }
    }
    // endregion

    // region Date

    private fun initDatePicker() {
        val calendar = Calendar.getInstance()

        val dateStart: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.editTextDateStart.setText(updateCalendar(calendar))
            }

        val dateEnd: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.editTextDateEnd.setText(updateCalendar(calendar))
            }

        binding.apply {
            editTextDateStart.setOnClickListener {
                val maxDate = Calendar.getInstance()
                val dialog = DatePickerDialog(
                    requireContext(),
                    dateStart,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.datePicker.maxDate = maxDate.timeInMillis
                dialog.show()
            }
            editTextDateEnd.setOnClickListener {
                val minDate = Calendar.getInstance()
                minDate.add(Calendar.DATE, 1)
                val dialog = DatePickerDialog(
                    requireContext(),
                    dateEnd,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.datePicker.minDate = minDate.timeInMillis
                dialog.show()
            }
        }
    }

    private fun updateCalendar(calendar: Calendar): String {
        val format = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        return sdf.format(calendar.time)
    }
    // endregion

    // region Location

    /**
     * Переход и получение результата из фрагмента карты
     */
    private fun initLocation() {
        binding.mapButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteSettingsFragment_to_searchMapFragment)
        }

        setFragmentResultListener("location") { _, bundle ->
            longitude = bundle.getDouble("longitude")
            latitude = bundle.getDouble("latitude")

            binding.locationTextView.text = "$longitude $latitude"
        }
    }
    // endregion

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
