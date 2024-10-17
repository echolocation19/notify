package com.nevrmd.notify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.nevrmd.notify.R
import com.nevrmd.notify.databinding.FragmentNoteBinding
import com.nevrmd.notify.model.Note
import com.nevrmd.notify.persistence.NoteDatabase
import com.nevrmd.notify.persistence.NoteRepository
import com.nevrmd.notify.viewmodel.MainViewModel
import com.nevrmd.notify.viewmodel.MainViewModelFactory

class NoteFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val view = binding.root

        val dao = NoteDatabase.getInstance(inflater.context).noteDao
        val repository = NoteRepository(dao)
        val factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        var isBeingEdited = false

        // -1 is a default value of args.id
        // we get it when NoteFragment is initiated through "+" button
        if (args.id != -1) {
            binding.etTitle.setText(args.title)
            binding.etBody.setText(args.body)
            isBeingEdited = true
        }

        binding.fabSave.setOnClickListener {
            val id = if(isBeingEdited) args.id else 0
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()

            val note: Note = Note(id, title, body)
            when(isBeingEdited) {
                true -> mainViewModel.update(note)
                false -> mainViewModel.insert(note)
            }
            Navigation.findNavController(view).navigate(R.id.navigateToHomeFragment)
        }
        return view
    }
}