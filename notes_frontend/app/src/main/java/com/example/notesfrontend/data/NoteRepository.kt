package com.example.notesfrontend.data

import com.example.notesfrontend.model.Note

/**
 * Manages notes in-memory.
 */
// PUBLIC_INTERFACE
object NoteRepository {
    private val notes = mutableListOf<Note>()

    fun getAll(): List<Note> = notes.sortedByDescending { it.updatedAt }

    fun search(query: String): List<Note> =
        notes.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.content.contains(query, ignoreCase = true)
        }.sortedByDescending { it.updatedAt }

    fun getById(id: String): Note? = notes.find { it.id == id }

    fun add(note: Note) {
        notes.add(note)
    }

    fun update(note: Note) {
        val idx = notes.indexOfFirst { it.id == note.id }
        if (idx != -1) {
            notes[idx] = note.copy(updatedAt = System.currentTimeMillis())
        }
    }

    fun delete(id: String) {
        notes.removeAll { it.id == id }
    }
}
