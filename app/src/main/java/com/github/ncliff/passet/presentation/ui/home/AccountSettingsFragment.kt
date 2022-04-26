package com.github.ncliff.passet.presentation.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.PasswordManager
import com.github.ncliff.passet.databinding.FragmentNoteSettingsBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteSettingsFragment : Fragment(R.layout.fragment_note_settings) {
    private var _binding: FragmentNoteSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteSettingsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
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
                Toast.makeText(context, "Save!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
            findNavController().navigate(R.id.action_accountSettingsFragment_to_searchMapFragment)
        }
    }
    // endregion

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
