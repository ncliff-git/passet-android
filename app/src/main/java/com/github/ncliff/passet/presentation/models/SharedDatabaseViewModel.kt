package com.github.ncliff.passet.presentation.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.data.database.NoteDatabase
import com.github.ncliff.passet.data.database.repository.NoteRealization
import com.github.ncliff.passet.data.database.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedDatabaseViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    var repository: NoteRepository

    init {
        val dao = NoteDatabase.getInstance(context).getNoteDao()
        repository = NoteRealization(dao)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.allNotes
    }

    fun getAllBookworms(): LiveData<List<Note>> {
        return repository.allBookworms
    }

    fun findNoteById(noteId: Int) = repository.findNoteById(noteId)

    fun insert(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note = note, onSuccess)
        }

    fun update(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note = note, onSuccess)
        }

    fun bookwormUpdate(noteId: Int, onSuccess: () -> Unit ) =
        viewModelScope.launch(Dispatchers.IO) {
        repository.bookwormUpdate(id = noteId, onSuccess)
    }

    fun delete(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note = note, onSuccess)
        }

    fun deleteAll(onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNote(onSuccess)
        }

}