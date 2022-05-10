package com.github.ncliff.passet.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.ncliff.passet.data.database.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE note_bookworm = 1")
    suspend fun getAllBookworm(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun findById(id: Int): Note

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}