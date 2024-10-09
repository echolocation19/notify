package com.nevrmd.notify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nevrmd.notify.databinding.LayoutNoteBinding
import com.nevrmd.notify.model.Note

class NoteAdapter(private val noteList: List<Note>, function: (Note) -> Unit): RecyclerView.Adapter<NoteAdapter.ViewHolderClass>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = LayoutNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = noteList[position]
        holder.tvTitle.text = currentItem.title
        holder.tvBody.text = currentItem.body
    }

    override fun getItemCount(): Int = noteList.size

    class ViewHolderClass(private val binding: LayoutNoteBinding): RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvTitle
        val tvBody: TextView = binding.tvBody
    }
}