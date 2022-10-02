package com.example.firebasewithmvvm.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.data.model.User
import com.example.firebasewithmvvm.data.repository.TaskRepository
import com.example.firebasewithmvvm.util.UiState
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    val repository: TaskRepository
): ViewModel() {

    private val _addTask = MutableLiveData<UiState<Pair<Task,String>>>()
    val addTask: LiveData<UiState<Pair<Task,String>>>
        get() = _addTask

    private val _updateTask = MutableLiveData<UiState<Pair<Task,String>>>()
    val updateTask: LiveData<UiState<Pair<Task,String>>>
        get() = _updateTask

    private val _deleteTask = MutableLiveData<UiState<Pair<Task,String>>>()
    val deleteTask: LiveData<UiState<Pair<Task,String>>>
        get() = _deleteTask


    private val _tasks = MutableLiveData<UiState<List<Task>>>()
    val tasks: LiveData<UiState<List<Task>>>
        get() = _tasks

    fun addTask(task: Task){
        _addTask.value = UiState.Loading
        repository.addTask(task) { _addTask.value = it }
    }

    fun updateTask(task: Task){
        _updateTask.value = UiState.Loading
        repository.updateTask(task) { _updateTask.value = it }
    }

    fun getTasks(user: User?) {
        _tasks.value = UiState.Loading
        repository.getTasks(user) { _tasks.value = it }
    }

    fun deleteTask(task: Task){
        _deleteTask.value = UiState.Loading
        repository.deleteTask(task) { _deleteTask.value = it }
    }

}