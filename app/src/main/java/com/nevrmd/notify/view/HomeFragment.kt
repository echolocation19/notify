package com.nevrmd.notify.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nevrmd.notify.R
import com.nevrmd.notify.adapter.NoteAdapter
import com.nevrmd.notify.databinding.FragmentHomeBinding
import com.nevrmd.notify.model.Note
import com.nevrmd.notify.persistence.NoteDatabase
import com.nevrmd.notify.persistence.NoteRepository
import com.nevrmd.notify.viewmodel.MainViewModel
import com.nevrmd.notify.viewmodel.MainViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val dao = NoteDatabase.getInstance(inflater.context).noteDao
        val repository = NoteRepository(dao)
        val factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        initRecyclerView()
        binding.fabAdd.setOnClickListener { navigateToNoteFragment() }
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvNotes.layoutManager = GridLayoutManager(this.context, 2)
        displayNotesList()
    }

    private fun displayNotesList() {
        mainViewModel.notes.observe(viewLifecycleOwner) { list ->
            binding.rvNotes.adapter = NoteAdapter(list) {
                selectedItem: Note -> noteCLicked(selectedItem)
            }
        }
    }

    private fun noteCLicked(selectedItem: Note) {
        navigateToNoteFragmentWithArgs(selectedItem) // тут нужно передать параметры через safe args, чтобы редактировать note
    }

    private fun navigateToNoteFragmentWithArgs(selectedItem: Note) {
        val id = selectedItem.id
        val title = selectedItem.title
        val body = selectedItem.body
        val action = HomeFragmentDirections.navigateToNoteFragment(title, body, id)
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun navigateToNoteFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.navigateToNoteFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}