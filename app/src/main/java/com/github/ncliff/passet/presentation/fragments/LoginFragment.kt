package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.R
import com.github.ncliff.passet.presentation.models.LoginViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var num0: ImageView? = null
    private var num1: ImageView? = null
    private var num2: ImageView? = null
    private var num3: ImageView? = null
    private var num4: ImageView? = null
    private var num5: ImageView? = null
    private var num6: ImageView? = null
    private var num7: ImageView? = null
    private var num8: ImageView? = null
    private var num9: ImageView? = null
    private var passView1: View? = null
    private var passView2: View? = null
    private var passView3: View? = null
    private var passView4: View? = null

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElements(view)
        listeners()
        subscribeToObservables()
        passView1?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_off)
    }

    private fun subscribeToObservables() {
        loginViewModel.lenData.observe(viewLifecycleOwner) { len ->
            when (len) {
                1 -> passView1?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                2 -> {
                    passView1?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                    passView2?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                }
                3 -> {
                    passView1?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                    passView2?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                    passView3?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                }
                4 -> {
                    passView1?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                    passView2?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                    passView3?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                    passView4?.background = AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_on)
                }
            }

            if (loginViewModel.lenData.value == 4) {
                if (loginViewModel.pin == "0525") {
                    loginViewModel.pin = ""
                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    findNavController().navigate(action)
                } else {
                    passView1?.background =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_off)
                    passView2?.background =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_off)
                    passView3?.background =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_off)
                    passView4?.background =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.round_view_off)
                }
            }
        }
    }

    private fun listeners() {
        num0?.setOnClickListener { loginViewModel.setPinNum(0) }
        num1?.setOnClickListener { loginViewModel.setPinNum(1) }
        num2?.setOnClickListener { loginViewModel.setPinNum(2) }
        num3?.setOnClickListener { loginViewModel.setPinNum(3) }
        num4?.setOnClickListener { loginViewModel.setPinNum(4) }
        num5?.setOnClickListener { loginViewModel.setPinNum(5) }
        num6?.setOnClickListener { loginViewModel.setPinNum(6) }
        num7?.setOnClickListener { loginViewModel.setPinNum(7) }
        num8?.setOnClickListener { loginViewModel.setPinNum(8) }
        num9?.setOnClickListener { loginViewModel.setPinNum(9) }
    }

    private fun initViewElements(view: View) {
        num0 = view.findViewById(R.id.Num0)
        num1 = view.findViewById(R.id.Num1)
        num2 = view.findViewById(R.id.Num2)
        num3 = view.findViewById(R.id.Num3)
        num4 = view.findViewById(R.id.Num4)
        num5 = view.findViewById(R.id.Num5)
        num6 = view.findViewById(R.id.Num6)
        num7 = view.findViewById(R.id.Num7)
        num8 = view.findViewById(R.id.Num8)
        num9 = view.findViewById(R.id.Num9)

        passView1 = view.findViewById(R.id.PassView1)
        passView2 = view.findViewById(R.id.PassView2)
        passView3 = view.findViewById(R.id.PassView3)
        passView4 = view.findViewById(R.id.PassView4)
    }

    override fun onDestroy() {
        num0 = null
        num1 = null
        num2 = null
        num3 = null
        num4 = null
        num5 = null
        num6 = null
        num7 = null
        num8 = null
        num9 = null

        passView1 = null
        passView2 = null
        passView3 = null
        passView4 = null
        super.onDestroy()
    }
}