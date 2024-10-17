package com.nevrmd.notify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevrmd.notify.model.Note
import com.nevrmd.notify.persistence.NoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NoteRepository): ViewModel() {
    val notes = repository.notes

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}