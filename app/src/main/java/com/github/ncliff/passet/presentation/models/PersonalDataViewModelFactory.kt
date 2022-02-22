package com.github.ncliff.passet.presentation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PersonalDataViewModelFactory(private val personalList: List<PersonalData>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainFragmentViewModel::class.java -> MainFragmentViewModel(personalList.toMutableList()) as T
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    }
}