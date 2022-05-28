package com.example.firebasewithmvvm.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.firebasewithmvvm.R
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.databinding.FragmentNoteDetailBinding
import com.example.firebasewithmvvm.util.UiState
import com.example.firebasewithmvvm.util.hide
import com.example.firebasewithmvvm.util.show
import com.example.firebasewithmvvm.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    val TAG: String = "NoteDetailFragment"
    lateinit var binding: FragmentNoteDetailBinding
    val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (validation()){
                viewModel.addNote(
                    Note(
                        id = "",
                        text = binding.noteMsg.text.toString(),
                        date = Date()
                    )
                )
            }
        }
        viewModel.addNote.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.btnProgressAr.show()
                    binding.button.text = ""
                }
                is UiState.Failure -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Create"
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Create"
                    toast(state.data)
                }
            }
        }
    }

    private fun validation(): Boolean{
        var isValid = true

        if (binding.noteMsg.text.toString().isNullOrEmpty()){
            isValid = false
            toast("Enter message")
        }

        return isValid
    }

}