package com.github.ncliff.passet.model.boxes.room.entities

data class BoxViewTuple(
    val id: Long,
    val name: String,
)

data class BoxUpdateParamsTuple(
    val id: Long,
    val name: String,
    val login: String,
    val password: String,
)