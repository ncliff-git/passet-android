package com.github.ncliff.passet.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.PasswordManager
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.databinding.FragmentNoteSettingsBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteSettingsFragment : Fragment(R.layout.fragment_note_settings) {
    private var _binding: FragmentNoteSettingsBinding? = null
    private val binding get() = _binding!!
    private var longitude: Double? = null
    private var latitude: Double? = null
    private val args: NoteSettingsFragmentArgs by navArgs()
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteSettingsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        initPasswordGen()
        initDatePicker()
        initLocation()
        completionFields()

        return _binding?.root
    }

    private fun completionFields() {
        val noteId = args.noteId

        Log.d("List", "note id: $noteId")
        if (noteId != -1) {
            viewModel.getAllNotes().observe(viewLifecycleOwner) { noteList ->
                noteList.forEach {
                    Log.d("List", "Search id: $noteId itId: ${it.id}")
                    if (it.id == noteId) {
                        Log.d("List", "Id: $noteId")
                        binding.textFieldNameEdit.setText(it.name)
                        binding.textFieldLoginEdit.setText(it.login)
                        binding.textFieldPasswordEdit.setText(it.password)
                        binding.editTextDateStart.setText(it.dateStart)
                        binding.editTextDateEnd.setText(it.dateEnd)
                        longitude = it.locationLongitude
                        latitude = it.locationLatitude
                        binding.locationTextView.text = "$longitude $latitude"
                    }
                }
            }
//            viewModel.findNoteById(noteId)
//            viewModel.noteById.observe(viewLifecycleOwner) {
//                Log.d("List", "Id: $noteId")
//                binding.textFieldNameEdit.setText(it.name)
//                binding.textFieldLoginEdit.setText(it.login)
//                binding.textFieldPasswordEdit.setText(it.password)
//                binding.editTextDateStart.setText(it.dateStart)
//                binding.editTextDateEnd.setText(it.dateEnd)
//                longitude = it.locationLongitude
//                latitude = it.locationLatitude
//                binding.locationTextView.text = "$longitude $latitude"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.account_setting_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                // TODO: Добавить запрет на обратный переход при неудачной проверке uiCheckFields()
                when (uiCheckFields()) {
                    true -> {
                        viewModel.insert(createInstanceNote()) {
                            Log.d("List", "Insert")
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

    private fun uiCheckFields(): Boolean {
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
            binding.editTextDateEnd.text.isNullOrEmpty()) {
            return false
        }
        return true
    }

     // TODO: Убрать или нет
    private fun createInstanceNote() : Note {
        return Note(
            id = null,
            name = binding.textFieldNameEdit.text.toString(),
            login = binding.textFieldLoginEdit.text.toString(),
            password = binding.textFieldPasswordEdit.text.toString(),
            dateStart = binding.editTextDateStart.toString(),
            dateEnd = binding.editTextDateEnd.toString(),
            locationLongitude = longitude,
            locationLatitude = latitude,
        )
    }

    // region Password generate

    private fun initPasswordGen() {
        passwordGenListeners()
        initNumberPicker()
    }

    private fun initNumberPicker() = with (binding.numberPicker) {
        maxValue = 32
        minValue = 6
        descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        wrapSelectorWheel = false
        value = 12
    }

    private fun passwordGenListeners() {
        checkingAllBoxes()
        binding.apply {
            cbNumbers.setOnClickListener {
                checkingAllBoxes()
            }
            cbLowercase.setOnClickListener {
                checkingAllBoxes()
            }
            cbUppercase.setOnClickListener {
                checkingAllBoxes()
            }
            cbSpecialSymbols.setOnClickListener {
                checkingAllBoxes()
            }
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
    }

    private fun checkingAllBoxes() {
        binding.apply {
            if (!(cbNumbers.isChecked || cbLowercase.isChecked || cbUppercase.isChecked || cbSpecialSymbols.isChecked)) {
                cbNumbers.isChecked = true
            }
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
