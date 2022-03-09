package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.ColorList
import com.github.ncliff.passet.data.ColorObject
import com.github.ncliff.passet.databinding.FragmentAccountSettingsBinding
import com.github.ncliff.passet.presentation.adapters.ColorSpinnerAdapter

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
        return _binding?.root
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
        checkingAllBoxes()
        binding.apply {
            checkbox1.setOnClickListener {
                checkingAllBoxes()
            }
            checkbox2.setOnClickListener {
                checkingAllBoxes()
            }
            checkbox3.setOnClickListener {
                checkingAllBoxes()
            }
            checkbox4.setOnClickListener {
                checkingAllBoxes()
            }
        }
    }

    private fun checkingAllBoxes() {
        binding.apply {
            if (!(checkbox1.isChecked || checkbox2.isChecked || checkbox3.isChecked || checkbox4.isChecked)) {
                checkbox1.isChecked = true
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}