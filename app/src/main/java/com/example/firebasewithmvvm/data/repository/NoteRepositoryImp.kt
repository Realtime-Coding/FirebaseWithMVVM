package com.example.firebasewithmvvm.data.repository

import com.example.firebasewithmvvm.data.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NoteRepositoryImp(
    val database: FirebaseFirestore
): NoteRepository {

    override fun getNotes(): List<Note> {
        //We will get data from firebase
        return arrayListOf(
            Note(
                id= "fdasf",
                text = "Note 1",
                date = Date()
            ),
            Note(
                id= "fdasf",
                text = "Note 2",
                date = Date()
            ),
            Note(
                id= "fdasf",
                text = "Note 2",
                date = Date()
            )
        )
    }
}