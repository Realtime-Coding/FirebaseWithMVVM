package com.example.firebasewithmvvm.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasewithmvvm.data.model.Task
import com.example.firebasewithmvvm.databinding.TaskLayoutBinding

class TaskListingAdapter(
    val onItemClicked: ((Int, Task) -> Unit)? = null,
    val onDeleteClicked: ((Int, Task) -> Unit)? = null,
) : RecyclerView.Adapter<TaskListingAdapter.MyViewHolder>() {

    private var list: MutableList<Task> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item,position)
    }

    fun updateList(list: MutableList<Task>){
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task,position: Int) {
            binding.title.setText(item.description)
            binding.itemLayout.setOnClickListener {
                onItemClicked?.invoke(position,item)
            }
            binding.delete.setOnClickListener {
                onDeleteClicked?.invoke(position,item)
            }
        }
    }
}