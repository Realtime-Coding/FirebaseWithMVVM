package com.example.firebasewithmvvm.data.repository

import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.data.model.User
import com.example.firebasewithmvvm.util.FireDatabase
import com.example.firebasewithmvvm.util.UiState
import com.google.firebase.database.FirebaseDatabase

class TaskRepositoryImp(
    val database: FirebaseDatabase
) : TaskRepository {

    override fun addTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).push()
        val uniqueKey = reference.key ?: "invalid"
        task.id = uniqueKey
        reference
            .setValue(task)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task,"Task has been created successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).orderByChild("user_id").equalTo(user?.id)
        reference.get()
            .addOnSuccessListener {
                val tasks = arrayListOf<Task?>()
                for (item in it.children){
                    val task = item.getValue(Task::class.java)
                    tasks.add(task)
                }
                result.invoke(UiState.Success(tasks.filterNotNull()))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}