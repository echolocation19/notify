package com.nevrmd.notify.presentation.home

import androidx.lifecycle.viewModelScope
import com.nevrmd.notify.data.NoteRepository
import com.nevrmd.notify.domain.NoteEntity
import com.nevrmd.notify.presentation.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NoteRepository): BaseViewModel<HomeState>(
    initialState = HomeState()
) {

    fun getNotes() = repository.notes

    fun delete(note: NoteEntity) = viewModelScope.launch {
        repository.delete(note)
    }
}