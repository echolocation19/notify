package com.nevrmd.notify.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevrmd.notify.model.Note
import com.nevrmd.notify.persistence.NoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NoteRepository): ViewModel() {
    val notes = repository.notes

    val inputTitle = MutableLiveData<String?>()
    val inputBody = MutableLiveData<String?>()

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun initNoteEdit(note: Note) {
        inputTitle.value = note.title
        inputBody.value = note.body
        TODO()
    }
}