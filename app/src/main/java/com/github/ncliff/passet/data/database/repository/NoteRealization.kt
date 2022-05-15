package com.github.ncliff.passet.data.database.repository

import androidx.lifecycle.LiveData
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.data.database.dao.NoteDao

class NoteRealization(private val noteDao: NoteDao) : NoteRepository {
    override val allNotes: LiveData<List<Note>>
        get() = noteDao.getAll()

    override val allBookworms: LiveData<List<Note>>
        get() = noteDao.getAllBookworm()

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

    override suspend fun deleteAllNote(onSuccess: () -> Unit) {
        noteDao.deleteAll()
        onSuccess()
    }

    override suspend fun updateNote(note: Note, onSuccess: () -> Unit) {
        noteDao.update(note)
        onSuccess()
    }

    override suspend fun bookwormUpdate(id: Int, onSuccess: () -> Unit) {
        noteDao.bookwormUpdate(id = id)
        onSuccess()
    }

    override fun findNoteById(noteId: Int) = noteDao.findById(noteId)
}