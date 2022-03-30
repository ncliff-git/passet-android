package com.github.ncliff.passet.presentation.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.ColorList
import com.github.ncliff.passet.data.ColorObject
import com.github.ncliff.passet.data.DataUtils
import com.github.ncliff.passet.databinding.FragmentAccountSettingsBinding
import com.github.ncliff.passet.presentation.adapters.ColorSpinnerAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import java.text.SimpleDateFormat
import java.util.*

class AccountSettingsFragment : Fragment(R.layout.fragment_account_settings) {
    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var selectedColor: ColorObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSettingsBinding.inflate(inflater, container, false)
        loadColorSpinner()
        checkBoxListeners()
        numberPickerProcessing()
        datePicker()

        return _binding?.root
    }

    private fun datePicker() {
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
                getDataCalendar(calendar, dateStart)
                editTextDateStart.setText(updateCalendar(calendar))
            }
            editTextDateEnd.setOnClickListener {
                getDataCalendar(calendar, dateEnd)
                editTextDateEnd.setText(updateCalendar(calendar))
            }
        }
    }

    private fun getDataCalendar(calendar: Calendar, date: DatePickerDialog.OnDateSetListener) {
        context?.let { cnt ->
            DatePickerDialog(
                cnt,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateCalendar(calendar: Calendar): String {
        val format = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        return sdf.format(calendar.time)
    }

    private fun numberPickerProcessing() = with (binding.numberPicker) {
        maxValue = 32
        minValue = 6
        descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        wrapSelectorWheel = false
    }

    private fun loadColorSpinner() {
        selectedColor = ColorList().defaultColor
        binding.colorSpinner.apply {
            adapter = ColorSpinnerAdapter(context, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectedColor = ColorList().basicColors()[position]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun checkBoxListeners() {

        binding.mapButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountSettingsFragment_to_searchMapFragment)
        }

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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
