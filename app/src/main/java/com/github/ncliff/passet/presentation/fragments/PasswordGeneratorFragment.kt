package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.github.ncliff.passet.data.PasswordManager
import com.github.ncliff.passet.databinding.FragmentPasswordGeneratorBinding

class PasswordGeneratorFragment : Fragment() {
    private var _binding: FragmentPasswordGeneratorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordGeneratorBinding.inflate(layoutInflater, container, false)
        initPasswordGen()
        return _binding?.root
    }

    // region Password generate

    private fun initPasswordGen() {
        passwordGenListeners()
        initNumberPickers()
    }

    private fun initNumberPickers() = with (binding) {
        numberOfPasswordsNumberPicker.max = 25
        numberOfPasswordsNumberPicker.min = 1
        numberOfPasswordsNumberPicker.setNumber(10)
        numberPicker.maxValue = 32
        numberPicker.minValue = 6
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker.wrapSelectorWheel = false
        numberPicker.value = 12
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
                var str = ""
                for (i in 0..numberOfPasswordsNumberPicker.getNumber()) {
                    str += PasswordManager.generatePassword(
                        numberPicker.value,
                        cbNumbers.isChecked,
                        cbUppercase.isChecked,
                        cbLowercase.isChecked,
                        cbSpecialSymbols.isChecked
                    )
                    str += "\n"
                }
                editTextPasswords.setText(str)
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
}