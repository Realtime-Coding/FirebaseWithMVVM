package com.example.firebasewithmvvm.data.repository

import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
}