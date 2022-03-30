package com.github.ncliff.passet.presentation.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.ColorObject
import com.github.ncliff.passet.databinding.FragmentAccountSettingsBinding
import java.text.SimpleDateFormat
import java.util.*

class AccountSettingsFragment : Fragment(R.layout.fragment_account_settings) {
    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var selectedColor: ColorObject

    // TODO: Переписать в объект
    // region object params test
    private var name: String = ""
    private var login: String = ""
    private var password: String = ""
    private var dateStart: String = ""
    private var dateEnd: String = ""
    private var locationW: String = ""
    private var locationH: String = ""
    // endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSettingsBinding.inflate(inflater, container, false)
        initPasswordGen()
        initDatePicker()
        initLocation()
        return _binding?.root
    }

    // region Password generate

    private fun initPasswordGen() {
        checkBoxListeners()
        initNumberPicker()
    }

    private fun initNumberPicker() = with (binding.numberPicker) {
        maxValue = 32
        minValue = 6
        descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        wrapSelectorWheel = false
    }

    private fun checkBoxListeners() {
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
        }
    }

    private fun checkingAllBoxes() {
        binding.apply {
            if (!(cbNumbers.isChecked || cbLowercase.isChecked || cbUppercase.isChecked || cbUppercase.isChecked)) {
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
