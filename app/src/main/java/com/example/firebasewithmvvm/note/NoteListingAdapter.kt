package com.example.firebasewithmvvm.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.databinding.ItemNoteLayoutBinding
import com.example.firebasewithmvvm.util.addChip
import com.example.firebasewithmvvm.util.hide
import java.text.SimpleDateFormat

class NoteListingAdapter(
    val onItemClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    val sdf = SimpleDateFormat("dd MMM yyyy")
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
            binding.title.setText(item.title)
            binding.date.setText(sdf.format(item.date))
            binding.tags.apply {
                if (item.tags.isNullOrEmpty()){
                    removeAllViews()
                    hide()
                    return
                }
                if (item.tags.size > 2){
                    item.tags.subList(0,2).forEach { tag -> addChip(tag)  }
                    addChip("+${item.tags.size - 2}")
                }else{
                    item.tags.forEach { tag -> addChip(tag) }
                }
            }
            binding.desc.apply {
                if (item.description.length > 120){
                    text = "${item.description.substring(0,120)}..."
                }else{
                    text = item.description
                }
            }
            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}