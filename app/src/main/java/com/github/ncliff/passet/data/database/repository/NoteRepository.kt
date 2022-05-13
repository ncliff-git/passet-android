package com.github.ncliff.passet.data.database.repository

import androidx.lifecycle.LiveData
import com.github.ncliff.passet.data.database.Note

interface NoteRepository {
    val allNotes: LiveData<List<Note>>
    val allBookworms: LiveData<List<Note>>
    suspend fun insertNote(note: Note, onSuccess: () -> Unit)
    suspend fun insertAllNote(vararg note: Note, onSuccess:() -> Unit)
    suspend fun deleteNote(note: Note, onSuccess:() -> Unit)
    suspend fun updateNote(note: Note, onSuccess:() -> Unit)
    suspend fun bookwormUpdate(id: Int, onSuccess: () -> Unit)
    fun findNoteById(noteId: Int): LiveData<Note>
}