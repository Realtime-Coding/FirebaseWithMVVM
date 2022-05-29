package com.example.firebasewithmvvm.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebasewithmvvm.R
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.databinding.FragmentNoteDetailBinding
import com.example.firebasewithmvvm.util.*
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    val TAG: String = "NoteDetailFragment"
    lateinit var binding: FragmentNoteDetailBinding
    val viewModel: NoteViewModel by viewModels()
    var objNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        observer()
    }

    private fun observer() {
        viewModel.addNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                }
            }
        }
        viewModel.updateNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                }
            }
        }
    }

    private fun updateUI() {
        val sdf = SimpleDateFormat("dd MMM yyyy . hh:mm a")
        objNote = arguments?.getParcelable("note")
        objNote?.let { note ->
            binding.title.setText(note.title)
            binding.date.setText(sdf.format(note.date))
            binding.tags.apply {
                note.tags.forEachIndexed { index, tag ->
                    addChip(tag,true) {
                        note.tags.removeAt(index)
                        this.removeViewAt(index)
                    }
                }
            }
            binding.description.setText(note.description)
            binding.update.show()
            binding.delete.show()
            isMakeEnableUI(false)
        } ?: run {
            binding.title.setText("")
            binding.date.setText(sdf.format(Date()))
            binding.tags.removeAllViews()
            binding.description.setText("")
            binding.update.hide()
            binding.delete.hide()
            isMakeEnableUI(true)
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.description.setOnClickListener {
            isMakeEnableUI(true)
        }
        binding.update.setOnClickListener {
            if (validation()) {
                if (objNote == null) {
                    viewModel.addNote(getNote())
                } else {
                    viewModel.updateNote(getNote())
                }
            }
        }
        binding.delete.setOnClickListener {
            toast("delete Note")
        }
    }

    private fun isMakeEnableUI(isDisable: Boolean = false) {
        binding.title.isEnabled = isDisable
        binding.date.isEnabled = isDisable
        binding.tags.isEnabled = isDisable
        binding.addTagLl.isEnabled = isDisable
        binding.description.isEnabled = isDisable
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.title.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_title))
        }
        if (binding.description.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_description))
        }
        return isValid
    }

    private fun getNote(): Note {
        val tags = binding.tags.children.toList().map { (it as Chip).text.toString() }.toMutableList()
        return Note(
            id = objNote?.id ?: "",
            title = binding.title.text.toString(),
            description = binding.description.text.toString(),
            tags = tags,
            date = Date()
        )
    }
}
