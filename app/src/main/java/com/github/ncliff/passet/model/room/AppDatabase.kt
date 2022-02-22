package com.github.ncliff.passet.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.ncliff.passet.model.boxes.room.BoxesDao
import com.github.ncliff.passet.model.boxes.room.entities.BoxDbEntity

@Database(
    version = 1,
    entities = [
        BoxDbEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getBoxesDao(): BoxesDao
}