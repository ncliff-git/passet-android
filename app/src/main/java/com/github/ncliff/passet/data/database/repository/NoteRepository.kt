package com.github.ncliff.passet.data.database.repository

import androidx.lifecycle.LiveData
import com.github.ncliff.passet.data.database.Note

interface NoteRepository {
    val allNotes: LiveData<List<Note>>
    suspend fun insertNote(note: Note, onSuccess: () -> Unit)
    suspend fun insertAllNote(vararg note: Note, onSuccess:() -> Unit)
    suspend fun deleteNote(note: Note, onSuccess:() -> Unit)
    suspend fun findNoteById(noteId: Int): Note
}