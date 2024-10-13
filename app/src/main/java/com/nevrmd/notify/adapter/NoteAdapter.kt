package com.nevrmd.notify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nevrmd.notify.databinding.LayoutNoteBinding
import com.nevrmd.notify.model.Note

class NoteAdapter(
    private val noteList: List<Note>,
    private val listener: OnNoteClickListener,
) : RecyclerView.Adapter<NoteAdapter.ViewHolderClass>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = LayoutNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = noteList[position]
        with(holder) {
            tvTitle.text = currentItem.title
            tvBody.text = currentItem.body
            binding.root.setOnClickListener { listener.onClick(currentItem) }
        }
    }

    override fun getItemCount(): Int = noteList.size

    class ViewHolderClass(val binding: LayoutNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle: TextView = binding.tvTitle
        val tvBody: TextView = binding.tvBody
    }
}

fun interface OnNoteClickListener {

    fun onClick(note: Note)
}