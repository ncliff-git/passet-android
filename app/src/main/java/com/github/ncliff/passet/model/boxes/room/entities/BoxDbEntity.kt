package com.github.ncliff.passet.model.boxes.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ncliff.passet.model.boxes.entities.Box
import com.github.ncliff.passet.model.boxes.entities.BoxCreateData

@Entity(tableName = "boxes")
data class BoxDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val login: String,
    val password: String,
) {
    fun toBox(): Box = Box(
        id = this.id,
        name = this.name,
        login = this.login,
        password = this.password
    )

    companion object {
        fun fromBoxCreateData(box: BoxCreateData): BoxDbEntity = BoxDbEntity(
            id = 0,
            name = box.name,
            login = box.login,
            password = box.password,
        )
    }
}