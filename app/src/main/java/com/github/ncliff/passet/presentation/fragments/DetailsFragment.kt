package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.ncliff.passet.R
import com.github.ncliff.passet.databinding.FragmentDetailsBinding
import com.github.ncliff.passet.presentation.models.DataStorage
import com.github.ncliff.passet.presentation.models.GenPassword.genPassword

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private var headerImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParams(view)
    }

    private fun initParams(view: View) {
        binding.apply {
            if (args.personalDetails == null) {
                imageViewHeader.setImageResource(DataStorage.getHeader(args.groupType))
            } else {
                imageViewHeader.setImageResource(DataStorage.getHeader(args.personalDetails?.type))
                textInputEditTextName.setText(args.personalDetails?.name)
                textInputEditTextLogin.setText(args.personalDetails?.login)
                textInputEditTextPassword.setText(args.personalDetails?.password)
            }

            val monthSize = resources.getStringArray(R.array.MonthSize)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, monthSize)
            autoCompleteTextViewSelector.setAdapter(arrayAdapter)

            var num = 16
            buttonPlus.setOnClickListener {
                textViewLen.text = (++num).toString()
            }
            buttonMinus.setOnClickListener {
                textViewLen.text = (--num).toString()
            }
            buttonGenerate.setOnClickListener {
                textInputEditTextPassword.setText(
                    genPassword(
                        num,
                        checkBoxNum.isChecked,
                        checkBoxLowercase.isChecked,
                        checkBoxUppercase.isChecked,
                        checkBoxSpecialSymbol.isChecked
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        headerImage = null

        super.onDestroy()
    }
}