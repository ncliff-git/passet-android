package com.github.ncliff.passet.data

data class PasswordNote(
    var name: String,
    var login: String,
    var password: String,
    var dateStart: String,
    var dateEnd: String,
    val bookworm: Boolean = false,
    var locationW: String = "",
    var locationH: String = "",
)
