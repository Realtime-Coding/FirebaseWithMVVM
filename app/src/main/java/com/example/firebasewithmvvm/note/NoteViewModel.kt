package com.example.firebasewithmvvm.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.data.repository.NoteRepository
import com.example.firebasewithmvvm.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val repository: NoteRepository
): ViewModel() {

    private val _notes = MutableLiveData<UiState<List<Note>>>()
    val note: LiveData<UiState<List<Note>>>
            get() = _notes

    private val _addNote = MutableLiveData<UiState<String>>()
    val addNote: LiveData<UiState<String>>
        get() = _addNote

    fun getNotes() {
        _notes.value = UiState.Loading
        repository.getNotes { _notes.value = it }
    }

    fun addNote(note: Note){
        _addNote.value = UiState.Loading
        repository.addNote(note) { _addNote.value = it }
    }

}