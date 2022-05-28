package com.example.firebasewithmvvm.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}