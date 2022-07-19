package com.example.firebasewithmvvm.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebasewithmvvm.R
import com.example.firebasewithmvvm.databinding.FragmentNoteListingBinding
import com.example.firebasewithmvvm.databinding.FragmentTaskListingBinding

private const val ARG_PARAM1 = "param1"

class TaskListingFragment : Fragment() {

    val TAG: String = "TaskListingFragment"
    private var param1: String? = null
    lateinit var binding: FragmentTaskListingBinding

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
            createTaskFragmentSheet.show(childFragmentManager,"create_task")
        }
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