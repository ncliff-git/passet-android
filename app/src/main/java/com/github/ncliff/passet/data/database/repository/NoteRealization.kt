package com.github.ncliff.passet.data.database.repository

import androidx.lifecycle.LiveData
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.data.database.dao.NoteDao

class NoteRealization(private val noteDao: NoteDao) : NoteRepository {
    override val allNotes: LiveData<List<Note>>
        get() = noteDao.getAll()

    override suspend fun insertNote(note: Note, onSuccess: () -> Unit) {
        noteDao.insert(note = note)
        onSuccess()
    }

    override suspend fun insertAllNote(vararg note: Note, onSuccess: () -> Unit) {
        noteDao.insertAll(note = note)
        onSuccess()
    }

    override suspend fun deleteNote(note: Note, onSuccess: () -> Unit) {
        noteDao.delete(note = note)
        onSuccess()
    }

    override suspend fun findNoteById(noteId: Int) = noteDao.findById(noteId)
}