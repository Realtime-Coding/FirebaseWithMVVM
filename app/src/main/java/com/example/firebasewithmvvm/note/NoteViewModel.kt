package com.example.firebasewithmvvm.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val repository: NoteRepository
): ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val note: LiveData<List<Note>>
            get() = _notes

    fun getNotes() {
        _notes.value = repository.getNotes()
    }

}