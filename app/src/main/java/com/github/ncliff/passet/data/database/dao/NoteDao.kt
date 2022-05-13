package com.github.ncliff.passet.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.ncliff.passet.data.database.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE note_bookworm = 1")
    fun getAllBookworm(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun findById(id: Int): LiveData<Note>

    @Query("UPDATE note SET note_bookworm = NOT(note_bookworm) WHERE id = :id")
    suspend fun bookwormUpdate(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}