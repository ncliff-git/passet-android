package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.github.ncliff.passet.R
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
            // TODO: Переделать работу generateButton в PasswordGeneratorFragment
//            generateButton.setOnClickListener {
//                textFieldPasswordEdit.setText(
//                    PasswordManager.generatePassword(
//                        numberPicker.value,
//                        cbNumbers.isChecked,
//                        cbUppercase.isChecked,
//                        cbLowercase.isChecked,
//                        cbSpecialSymbols.isChecked
//                    )
//                )
//            }
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