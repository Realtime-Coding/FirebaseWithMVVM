package com.example.firebasewithmvvm.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.databinding.ItemNoteLayoutBinding
import java.text.SimpleDateFormat

class NoteListingAdapter(
    val onItemClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    val sdf = SimpleDateFormat("dd MMM yyyy . hh:mm a")
    private var list: MutableList<Note> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Note>){
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

    inner class MyViewHolder(val binding: ItemNoteLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note){
            binding.date.setText(sdf.format(item.date))
            binding.title.setText(item.title)
            binding.desc.setText(item.description)
            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}