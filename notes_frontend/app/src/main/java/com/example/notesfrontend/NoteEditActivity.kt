package com.example.notesfrontend

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.data.NoteRepository
import com.example.notesfrontend.model.Note
import com.google.android.material.textfield.TextInputEditText

/**
 * Activity for creating/editing a note.
 */
// PUBLIC_INTERFACE
class NoteEditActivity : AppCompatActivity() {
    private var editingNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_note_edit)

        val editTitle = findViewById<TextInputEditText>(R.id.editTitle)
        val editContent = findViewById<TextInputEditText>(R.id.editContent)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val id = intent.getStringExtra("note_id")
        editingNote = id?.let { NoteRepository.getById(it) }

        if (editingNote != null) {
            editTitle.setText(editingNote!!.title)
            editContent.setText(editingNote!!.content)
        }

        btnSave.setOnClickListener {
            val title = editTitle.text?.toString()?.trim().orEmpty()
            val content = editContent.text?.toString()?.trim().orEmpty()
            if (title.isEmpty()) {
                Toast.makeText(this, R.string.title, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (editingNote == null) {
                NoteRepository.add(Note(title = title, content = content))
                Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show()
            } else {
                editingNote!!.title = title
                editingNote!!.content = content
                NoteRepository.update(editingNote!!)
                Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
