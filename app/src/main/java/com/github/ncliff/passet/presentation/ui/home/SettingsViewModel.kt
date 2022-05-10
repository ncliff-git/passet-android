package com.github.ncliff.passet.presentation.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.data.database.NoteDatabase
import com.github.ncliff.passet.data.database.repository.NoteRealization
import com.github.ncliff.passet.data.database.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    var repository: NoteRepository

    init {
        val dao = NoteDatabase.getInstance(context).getNoteDao()
        repository = NoteRealization(dao)
    }

    fun insert(vararg note: Note, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllNote(note = note, onSuccess)
        }
}