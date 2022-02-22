package com.github.ncliff.passet.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _lenData = MutableLiveData<Int>()
    val lenData: LiveData<Int> get() = _lenData
    var pin = ""

    fun setPinNum(pinNum: Int) {
        pin += pinNum.toString()
        _lenData.value = pin.length
        if (pin.length > 4) {
            _lenData.value = 0
            pin = ""
        }
    }
}