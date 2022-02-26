package com.github.ncliff.passet.presentation.models

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ncliff.passet.R

class PinCodeViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _securedPinCode = MutableLiveData<String>()
    private val _pinCode = MutableLiveData<String>()
    val pinCode: LiveData<String> = _pinCode

    private val _equalPinCode = MutableLiveData<Boolean>()
    val equalPinCode: LiveData<Boolean> = _equalPinCode

    private val _repeatPinCode = MutableLiveData<String>()
    val repeatPinCode: LiveData<String> = _repeatPinCode

    // start: Test
    val pinCodeState = MutableLiveData<PinCodeState>()

    // end

    init {
        val state = if (sharedPreferences.getString(PIN_CODE_KEY, "").isNullOrEmpty()) {
            PinCodeState.PIN_CODE_CREATE
        } else {
            PinCodeState.PIN_CODE_ENTER
        }
        pinCodeState.postValue(state)
    }

    fun onNumberClicked(num: Char) {
        val existingPinCode = _pinCode.value ?: ""
        val newPassCode = existingPinCode + num
        _pinCode.postValue(newPassCode)

        if (newPassCode.length >= 6) {
            if (sharedPreferences.getString(PIN_CODE_KEY, "").isNullOrEmpty()) {
                pinCodeState.postValue(PinCodeState.PIN_CODE_CREATE)
                if (repeatPinCode.value.isNullOrEmpty()) {
                    pinCodeState.postValue(PinCodeState.PIN_CODE_REPEAT)
                    _repeatPinCode.postValue(newPassCode)
                    _pinCode.postValue("")
                    return
                }
                if (repeatPinCode.value != newPassCode) {
                    _equalPinCode.postValue(false)
                    _repeatPinCode.postValue("")
                    _pinCode.postValue("")
                    return
                }
                sharedPreferences.edit().run {
                    putString(PIN_CODE_KEY, newPassCode)
                    apply()
                }
            }
            pinCodeState.postValue(PinCodeState.PIN_CODE_ENTER)
            val pinCodeInSharedPreference = sharedPreferences.getString(PIN_CODE_KEY, "")
            _equalPinCode.postValue(newPassCode == pinCodeInSharedPreference)
            _securedPinCode.postValue(pinCodeInSharedPreference ?: "")
            _pinCode.postValue("")
        }
    }

    fun onBackspaceClicked() {
        val existingPinCode = _pinCode.value?.dropLast(1) ?: ""
        _pinCode.postValue(existingPinCode)
    }

    companion object {
        const val PIN_CODE_KEY = "PIN_CODE_KEY"

        enum class PinCodeState {
            PIN_CODE_ENTER,
            PIN_CODE_CREATE,
            PIN_CODE_REPEAT,
        }
    }
}