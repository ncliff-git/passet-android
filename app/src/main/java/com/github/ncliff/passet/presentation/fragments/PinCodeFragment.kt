package com.github.ncliff.passet.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.EncryptSharedPreferences
import com.github.ncliff.passet.databinding.FragmentPinCodeBinding
import com.github.ncliff.passet.presentation.models.PinCodeViewModel
import com.github.ncliff.passet.presentation.models.PinCodeViewModelFactory

class PinCodeFragment : Fragment(R.layout.fragment_pin_code) {
    private var  _binding: FragmentPinCodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pinCodeViewModel: PinCodeViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPinCodeBinding.inflate(inflater, container, false)
        sharedPreferences = EncryptSharedPreferences.getInstance(requireContext()).sharedPreferences
        pinCodeViewModel = ViewModelProvider(this, PinCodeViewModelFactory(sharedPreferences))
            .get(PinCodeViewModel::class.java)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textSelector()
        dotSelectorAndClickListener()

        // TODO: PinCode Toast удалить или изменить
        pinCodeViewModel.securedPinCode.observe(viewLifecycleOwner) { securedPinCode ->
            Toast.makeText(context, "${pinCodeViewModel.equalPinCode.value == true}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun textSelector() {
        if (sharedPreferences.getString(PinCodeViewModel.PIN_CODE_KEY, "").isNullOrEmpty()) {
            binding.pinCodeText.text = getString(R.string.pin_code_create)
        } else {
            binding.pinCodeText.text = getString(R.string.pin_code_enter)
        }

        pinCodeViewModel.repeatPinCode.observe(viewLifecycleOwner) { repeat ->
            if (repeat.isNullOrEmpty()) {
                binding.pinCodeText.text = getString(R.string.pin_code_create)
            } else {
                binding.pinCodeText.text = getString(R.string.pin_code_repeat)
            }
        }
    }

    private fun dotSelectorAndClickListener() {
        pinCodeViewModel.pinCode.observe(viewLifecycleOwner) { pinCode ->
            binding.apply {
                dot1.isEnabled = pinCode.length < 1
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

    companion object {
        fun newInstance() = PinCodeFragment()
    }
}