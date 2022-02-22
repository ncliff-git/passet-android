package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.RelativeLayout
import com.github.ncliff.passet.R
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.presentation.models.DataStorage

class GroupSelecting : Fragment(R.layout.fragment_group_selecting) {
    private var socialButton: RelativeLayout? = null
    private var webButton: RelativeLayout? = null
    private var shopButton: RelativeLayout? = null
    private var deviceButton: RelativeLayout? = null
    private var emailButton: RelativeLayout? = null
    private var bankButton: RelativeLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElements(view)
        listeners()
    }

    fun initViewElements(view: View) {
        socialButton = view.findViewById(R.id.socialButton)
        webButton = view.findViewById(R.id.webButton)
        shopButton = view.findViewById(R.id.shopButton)
        deviceButton = view.findViewById(R.id.deviceButton)
        emailButton = view.findViewById(R.id.emailButton)
        bankButton = view.findViewById(R.id.bankButton)
    }

    fun listeners() {
        socialButton?.setOnClickListener { actionDetailsFragment(DataStorage.SOCIAL_ITEM) }
        webButton?.setOnClickListener { actionDetailsFragment(DataStorage.WEB_ITEM) }
        shopButton?.setOnClickListener { actionDetailsFragment(DataStorage.SHOP_ITEM) }
        deviceButton?.setOnClickListener { actionDetailsFragment(DataStorage.DEVICE_ITEM) }
        emailButton?.setOnClickListener { actionDetailsFragment(DataStorage.EMAIL_ITEM) }
        bankButton?.setOnClickListener { actionDetailsFragment(DataStorage.BANK_ITEM) }
    }

    private fun actionDetailsFragment(headerNum: Int) {
        val action = GroupSelectingDirections.actionMainFragmentToDetailsFragment(null, headerNum)
        findNavController().navigate(action)
    }


    override fun onDestroy() {
        socialButton = null
        webButton = null
        shopButton = null
        deviceButton = null
        emailButton = null
        bankButton = null
        super.onDestroy()
    }
}