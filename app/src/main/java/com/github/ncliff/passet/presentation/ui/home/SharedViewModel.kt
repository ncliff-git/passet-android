package com.github.ncliff.passet.presentation.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.data.database.NoteDatabase
import com.github.ncliff.passet.data.database.repository.NoteRealization
import com.github.ncliff.passet.data.database.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    var repository: NoteRepository
    private val _noteById = MutableLiveData<Note>()
    val noteById: LiveData<Note> = _noteById

    init {
        val dao = NoteDatabase.getInstance(context).getNoteDao()
        repository = NoteRealization(dao)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.allNotes
    }

    fun insert(note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note = note, onSuccess)
        }

    fun insertAll(vararg note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllNote(note = note, onSuccess)
        }

    fun findNoteById(noteId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            _noteById.value = repository.findNoteById(noteId)
        }
}