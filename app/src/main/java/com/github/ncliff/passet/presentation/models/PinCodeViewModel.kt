package com.github.ncliff.passet.presentation.models

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ncliff.passet.data.DataUtils

class PinCodeViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _pinCode = MutableLiveData<String>()
    val pinCode: LiveData<String> = _pinCode

    private val _pinCodeEqual = MutableLiveData<Boolean>()
    val pinCodeEqual: LiveData<Boolean> = _pinCodeEqual

    private val _pinCodeState = MutableLiveData<PinCodeState>()
    val pinCodeState: LiveData<PinCodeState> = _pinCodeState

    private var repeatPinCode: String = ""

    init {
        val state = if (sharedPreferences.getString(DataUtils.PIN_CODE_KEY, "").isNullOrEmpty()) {
            PinCodeState.PIN_CODE_CREATE
        } else {
            PinCodeState.PIN_CODE_ENTER
        }
        _pinCodeState.postValue(state)
    }

    fun onNumberClicked(num: Char) {
        val existingPinCode = _pinCode.value ?: ""
        val newPassCode = existingPinCode + num
        _pinCode.postValue(newPassCode)

        if (newPassCode.length >= 6) {
            if (sharedPreferences.getString(DataUtils.PIN_CODE_KEY, "").isNullOrEmpty()) {
                _pinCodeState.postValue(PinCodeState.PIN_CODE_CREATE)
                if (repeatPinCode.isEmpty()) {
                    _pinCodeState.postValue(PinCodeState.PIN_CODE_REPEAT)
                    repeatPinCode = newPassCode
                    _pinCode.postValue("")
                    return
                }
                if (repeatPinCode != newPassCode) {
                    _pinCodeEqual.postValue(false)
                    repeatPinCode = ""
                    _pinCode.postValue("")
                    return
                }
                sharedPreferences.edit().run {
                    putString(DataUtils.PIN_CODE_KEY, newPassCode)
                    apply()
                }
            }
            _pinCodeState.postValue(PinCodeState.PIN_CODE_ENTER)
            val pinCodeInSharedPreference = sharedPreferences.getString(DataUtils.PIN_CODE_KEY, "")
            _pinCodeEqual.postValue(newPassCode == pinCodeInSharedPreference)
            _pinCode.postValue("")
        }
    }

    fun onBackspaceClicked() {
        val existingPinCode = _pinCode.value?.dropLast(1) ?: ""
        _pinCode.postValue(existingPinCode)
    }

    companion object {
        enum class PinCodeState {
            PIN_CODE_ENTER,
            PIN_CODE_CREATE,
            PIN_CODE_REPEAT,
        }
    }
}