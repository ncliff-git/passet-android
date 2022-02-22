package com.github.ncliff.passet.model.boxes.entities

data class BoxCreateData(
    val name: String,
    val login: String,
    val password: String,
    val repeatPassword: String,
)
