package com.example.firebasewithmvvm.ui.task

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.firebasewithmvvm.R
import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.databinding.FragmentCreateTaskBinding
import com.example.firebasewithmvvm.ui.auth.AuthViewModel
import com.example.firebasewithmvvm.util.UiState
import com.example.firebasewithmvvm.util.hide
import com.example.firebasewithmvvm.util.show
import com.example.firebasewithmvvm.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateTaskFragment : BottomSheetDialogFragment() {

    val TAG: String = "CreateTaskFragment"
    lateinit var binding: FragmentCreateTaskBinding
    val viewModel: TaskViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    var closeFunction: ((Boolean) -> Unit)? = null
    var isSuccessAddTask: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancel.setOnClickListener {
            this.dismiss()
        }

        binding.done.setOnClickListener {
            if (validation()) {
                viewModel.addTask(getTask())
            }
        }
        observer()
    }

    private fun observer(){
        viewModel.addTask.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    isSuccessAddTask = true
                    binding.progressBar.hide()
                    toast(state.data.second)
                    this.dismiss()
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.taskEt.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_task_detail))
        }
        return isValid
    }

    private fun getTask(): Task {
        val sdf = SimpleDateFormat("dd MMM yyyy . hh:mm a")
        return Task(
            id = "",
            description = binding.taskEt.text.toString(),
            date = sdf.format(Date())
        ).apply { authViewModel.getSession { this.user_id = it?.id ?: "" } }
    }

    fun setDismissListener(function: ((Boolean) -> Unit)?) {
        closeFunction = function
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        closeFunction?.invoke(isSuccessAddTask)
    }
}
