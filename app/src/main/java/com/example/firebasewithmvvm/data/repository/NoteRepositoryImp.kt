package com.example.firebasewithmvvm.data.repository

import android.content.SharedPreferences
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.data.model.User
import com.example.firebasewithmvvm.util.FireStoreCollection
import com.example.firebasewithmvvm.util.FireStoreDocumentField
import com.example.firebasewithmvvm.util.SharedPrefConstants
import com.example.firebasewithmvvm.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NoteRepositoryImp(
    val database: FirebaseFirestore
) : NoteRepository {

    override fun getNotes(user: User?, result: (UiState<List<Note>>) -> Unit) {
        database.collection(FireStoreCollection.NOTE)
            .whereEqualTo(FireStoreDocumentField.USER_ID,user?.id)
            .orderBy(FireStoreDocumentField.DATE, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val notes = arrayListOf<Note>()
                for (document in it) {
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                }
                result.invoke(
                    UiState.Success(notes)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun addNote(note: Note, result: (UiState<Pair<Note,String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document()
        note.id = document.id
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(note,"Note has been created successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun updateNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been update successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun deleteNote(note: Note, result: (UiState<String>) -> Unit) {
        database.collection(FireStoreCollection.NOTE).document(note.id)
            .delete()
            .addOnSuccessListener {
                result.invoke(UiState.Success("Note successfully deleted!"))
            }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message))
            }
    }
}