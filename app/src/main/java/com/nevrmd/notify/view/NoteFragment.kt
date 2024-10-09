package com.nevrmd.notify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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
        binding.fabSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()
            val note: Note = Note(0, title, body)
            mainViewModel.insert(note)
            Navigation.findNavController(view).navigate(R.id.navigateToHomeFragment)
        }

        return view
    }
}