package com.example.firebasewithmvvm.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.databinding.FragmentTaskListingBinding
import com.example.firebasewithmvvm.ui.auth.AuthViewModel
import com.example.firebasewithmvvm.util.UiState
import com.example.firebasewithmvvm.util.hide
import com.example.firebasewithmvvm.util.show
import com.example.firebasewithmvvm.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class TaskListingFragment : Fragment() {

    val TAG: String = "TaskListingFragment"
    private var param1: String? = null
    val viewModel: TaskViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    lateinit var binding: FragmentTaskListingBinding
    val adapter by lazy{
        TaskListingAdapter(
            onItemClicked = { pos, item -> onTaskClicked(item)},
            onDeleteClicked = { pos, item -> }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentTaskListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTaskButton.setOnClickListener {
            val createTaskFragmentSheet = CreateTaskFragment()
            createTaskFragmentSheet.setDismissListener {
                if (it) {
                    authViewModel.getSession {
                        viewModel.getTasks(it)
                    }
                }
            }
            createTaskFragmentSheet.show(childFragmentManager,"create_task")
        }

        binding.taskListing.layoutManager = LinearLayoutManager(requireContext())
        binding.taskListing.adapter = adapter

        authViewModel.getSession {
            viewModel.getTasks(it)
        }
        observer()
    }

    private fun observer(){
        viewModel.tasks.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }

    private fun onTaskClicked(task: Task){
        val createTaskFragmentSheet = CreateTaskFragment(task)
        createTaskFragmentSheet.setDismissListener {
            if (it) {
                authViewModel.getSession {
                    viewModel.getTasks(it)
                }
            }
        }
        createTaskFragmentSheet.show(childFragmentManager,"create_task")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            TaskListingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}