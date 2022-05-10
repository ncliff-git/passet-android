package com.github.ncliff.passet.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.ncliff.passet.data.DataUtils.DATABASE_NAME
import com.github.ncliff.passet.data.database.dao.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        private var database: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME).build()
                database as NoteDatabase
            } else {
                database as NoteDatabase
            }
        }
    }
}