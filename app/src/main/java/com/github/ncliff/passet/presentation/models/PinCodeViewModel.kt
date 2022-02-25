package com.github.ncliff.passet.presentation.models

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinCodeViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _pinCode = MutableLiveData<String>()
    val pinCode: LiveData<String> = _pinCode

    private val _securedPinCode = MutableLiveData<String>()
    val securedPinCode: LiveData<String> = _securedPinCode

    val equalPinCode = MutableLiveData<Boolean>()
    val repeatPinCode = MutableLiveData<String>()

    fun onNumberClicked(num: Char) {
        val existingPinCode = _pinCode.value ?: ""
        val newPassCode = existingPinCode + num
        _pinCode.postValue(newPassCode)

        if (newPassCode.length >= 6) {
            if (sharedPreferences.getString(PIN_CODE_KEY, "").isNullOrEmpty()) {
                if (repeatPinCode.value.isNullOrEmpty()) {
                    repeatPinCode.postValue(newPassCode)
                    _pinCode.postValue("")
                    return
                }
                if (repeatPinCode.value != newPassCode) {
                    repeatPinCode.postValue("")
                    _pinCode.postValue("")
                    return
                }
                sharedPreferences.edit().run {
                    putString(PIN_CODE_KEY, newPassCode)
                    apply()
                }
            }
            val pinCodeInSharedPreference = sharedPreferences.getString(PIN_CODE_KEY, "")
            equalPinCode.postValue(newPassCode == pinCodeInSharedPreference)
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
    }
}