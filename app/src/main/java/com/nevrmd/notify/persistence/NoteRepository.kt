package com.nevrmd.notify.persistence

import com.nevrmd.notify.model.Note

class NoteRepository(private val dao: NoteDao) {
    val notes = dao.getAllNotes()

    suspend fun insert(note: Note): Long {
        return dao.insertNote(note)
    }

    suspend fun update(note: Note) {
        return dao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        return dao.deleteNote(note)
    }
}