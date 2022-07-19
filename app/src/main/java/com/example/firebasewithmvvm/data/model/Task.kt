package com.example.firebasewithmvvm.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Task(
    var id: String = "",
    var user_id: String = "",
    val description: String = "",
    val date: String = "",
) : Parcelable
