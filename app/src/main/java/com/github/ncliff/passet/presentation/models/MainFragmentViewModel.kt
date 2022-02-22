package com.github.ncliff.passet.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel(personalDataList: MutableList<PersonalData>) : ViewModel() {
    private val _personalData = MutableLiveData<MutableList<PersonalData>>()
    val personalData: LiveData<MutableList<PersonalData>> get() = _personalData

    private val _personalDataPosition: SingleLiveData<Int> by lazy { SingleLiveData() }
    val personalDataPosition: LiveData<Int> = _personalDataPosition
    init {
        _personalData.value = personalDataList
    }

    fun setPersonalDataPosition(position: Int) {
        _personalDataPosition.setValue(position)
    }

    fun setPersonalData(personalData: PersonalData) {
        _personalData.value?.add(personalData)
    }
}