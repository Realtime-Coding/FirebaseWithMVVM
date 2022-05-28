package com.example.firebasewithmvvm.data.repository

import com.example.firebasewithmvvm.data.model.Note

interface NoteRepository {

    fun getNotes(): List<Note>
}