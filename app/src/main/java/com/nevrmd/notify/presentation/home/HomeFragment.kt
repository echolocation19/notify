package com.nevrmd.notify.presentation.home

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nevrmd.notify.R
import com.nevrmd.notify.data.NoteRepository
import com.nevrmd.notify.data.database.NoteDatabase
import com.nevrmd.notify.databinding.FragmentHomeBinding
import com.nevrmd.notify.domain.NoteEntity
import com.nevrmd.notify.presentation.BaseFragment
import com.nevrmd.notify.presentation.ViewModelFactory
import com.nevrmd.notify.presentation.note.NoteAdapter
import com.nevrmd.notify.presentation.note.OnNoteClickListener

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    viewModelClass = HomeViewModel::class.java
) {

    override fun createViewModelFactory(): ViewModelFactory {
        val dao = NoteDatabase.getInstance(requireContext()).noteDao
        val repository = NoteRepository(dao)
        return ViewModelFactory(repository)
    }

    // это по сути onCreateView
    // тут пока нельзя использовать binding напрямую
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
        initRecyclerView()
        fabAdd.setOnClickListener { navigateToNoteFragment() }
    }

    private fun FragmentHomeBinding.initRecyclerView() = with(rvNotes) {
        layoutManager = GridLayoutManager(requireContext(), GRID_LAYOUT_SPAN_COUNT)
        viewModel.getNotes().observe(viewLifecycleOwner) { list ->
            adapter = NoteAdapter(list, object : OnNoteClickListener {
                override fun onClick(selectedItem: NoteEntity) {
                    noteClicked(selectedItem)
                }

                override fun onLongClick(selectedItem: NoteEntity) {
                    noteLongClicked(selectedItem)
                }
            })
        }
    }

    private fun noteClicked(selectedItem: NoteEntity) {
        navigateToNoteFragmentWithArgs(selectedItem)
    }

    private fun noteLongClicked(selectedItem: NoteEntity) {
        showDeletePopup(requireView(), selectedItem)
    }

    private fun navigateToNoteFragmentWithArgs(selectedItem: NoteEntity) {
        val id = selectedItem.id
        val title = selectedItem.title
        val body = selectedItem.body
        val action = HomeFragmentDirections.navigateToNoteFragment(title, body, id)
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun navigateToNoteFragment() {
        Navigation.findNavController(binding.root).navigate(R.id.navigateToNoteFragment)
    }

    private fun showDeletePopup(view: View, selectedItem: NoteEntity) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.header_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.hDelete -> {
                    deleteNote(selectedItem)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun deleteNote(selectedItem: NoteEntity) {
        viewModel.delete(selectedItem)
    }

    private companion object {

        const val GRID_LAYOUT_SPAN_COUNT = 2
    }
}