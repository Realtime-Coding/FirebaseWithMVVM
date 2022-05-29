package com.example.firebasewithmvvm.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Note(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val tags: List<String> = arrayListOf(),
    @ServerTimestamp
    val date: Date = Date(),
) : Parcelable
