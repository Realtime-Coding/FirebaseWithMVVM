package com.example.firebasewithmvvm.data.repository

import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.data.model.User
import com.example.firebasewithmvvm.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun updateTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun deleteTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit)
}