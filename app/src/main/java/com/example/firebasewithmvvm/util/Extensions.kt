package com.example.firebasewithmvvm.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firebasewithmvvm.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.disable(){
    isEnabled = false
}

fun View.enabled(){
    isEnabled = true
}

fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}

fun ChipGroup.addChip(text: String) {
    val chip: Chip = LayoutInflater.from(context).inflate(R.layout.item_chip,null,false) as Chip
    chip.text = if (text.length > 9) text.substring(0,9) + "..." else text
    chip.isClickable = false
    addView(chip)
}