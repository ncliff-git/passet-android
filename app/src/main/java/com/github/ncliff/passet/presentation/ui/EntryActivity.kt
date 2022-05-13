package com.github.ncliff.passet.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.DataUtils
import com.github.ncliff.passet.databinding.ActivityEntryBinding
import com.github.ncliff.passet.presentation.models.PinCodeViewModel
import com.github.ncliff.passet.presentation.models.PinCodeViewModelFactory
import com.google.android.material.snackbar.Snackbar

class EntryActivity : AppCompatActivity() {
    private var  _binding: ActivityEntryBinding? = null
    private val binding get() = _binding!!
    private lateinit var pinCodeViewModel: PinCodeViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEntryBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences(DataUtils.APP_PREFERENCES, Context.MODE_PRIVATE)
        pinCodeViewModel = ViewModelProvider(this, PinCodeViewModelFactory(sharedPreferences))
            .get(PinCodeViewModel::class.java)
        observersAndClickListener()
        moveTo()
        setContentView(binding.root)
    }

    private fun moveTo() {
        pinCodeViewModel.pinCodeAccess.observe(this) { _booleanPinCode ->
            when (_booleanPinCode) {
                true -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                false -> {
                    Snackbar.make(binding.root, R.string.pin_code_error, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.RED)
                        .show()
                }
            }
        }
    }

    private fun observersAndClickListener() {
        pinCodeViewModel.pinCodeState.observe(this) { state ->
            binding.pinCodeText.text = when (state) {
                PinCodeViewModel.Companion.PinCodeState.PIN_CODE_ENTER -> getString(R.string.pin_code_enter)
                PinCodeViewModel.Companion.PinCodeState.PIN_CODE_CREATE -> getString(R.string.pin_code_create)
                PinCodeViewModel.Companion.PinCodeState.PIN_CODE_REPEAT -> getString(R.string.pin_code_repeat)
                null -> "null"
            }
        }

        pinCodeViewModel.pinCode.observe(this) { pinCode ->
            binding.apply {
                dot1.isEnabled = pinCode.isEmpty()
                dot2.isEnabled = pinCode.length < 2
                dot3.isEnabled = pinCode.length < 3
                dot4.isEnabled = pinCode.length < 4
                dot5.isEnabled = pinCode.length < 5
                dot6.isEnabled = pinCode.length < 6
            }
        }

        binding.apply {
            num0.setOnClickListener{ pinCodeViewModel.onNumberClicked('0') }
            num1.setOnClickListener{ pinCodeViewModel.onNumberClicked('1') }
            num2.setOnClickListener{ pinCodeViewModel.onNumberClicked('2') }
            num3.setOnClickListener{ pinCodeViewModel.onNumberClicked('3') }
            num4.setOnClickListener{ pinCodeViewModel.onNumberClicked('4') }
            num5.setOnClickListener{ pinCodeViewModel.onNumberClicked('5') }
            num6.setOnClickListener{ pinCodeViewModel.onNumberClicked('6') }
            num7.setOnClickListener{ pinCodeViewModel.onNumberClicked('7') }
            num8.setOnClickListener{ pinCodeViewModel.onNumberClicked('8') }
            num9.setOnClickListener{ pinCodeViewModel.onNumberClicked('9') }
            backspace.setOnClickListener{ pinCodeViewModel.onBackspaceClicked() }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}