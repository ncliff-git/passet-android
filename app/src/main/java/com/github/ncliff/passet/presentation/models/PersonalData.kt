package com.github.ncliff.passet.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonalData(
    val name: String,
    val login: String,
    val password: String,
    val date: String,
    val type: Int,
) : Parcelable
