package com.example.notesfrontend

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.notesfrontend.data.NoteRepository
import java.text.SimpleDateFormat
import java.util.*

/**
 * Activity for viewing a note's details.
 */
// PUBLIC_INTERFACE
class NoteViewActivity : AppCompatActivity() {

    private var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_note_view)

        noteId = intent.getStringExtra("note_id")
        showNote()
    }

    private fun showNote() {
        val note = noteId?.let { NoteRepository.getById(it) }
        if (note != null) {
            findViewById<TextView>(R.id.textViewTitle).text = note.title
            findViewById<TextView>(R.id.textViewDate).text =
                SimpleDateFormat("MMM d, yyyy HH:mm", Locale.getDefault()).format(Date(note.updatedAt))
            findViewById<TextView>(R.id.textViewContent).text = note.content
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val note = noteId?.let { NoteRepository.getById(it) }
        return when (item.itemId) {
            R.id.action_edit -> {
                if (note != null) {
                    val intent = Intent(this, NoteEditActivity::class.java)
                    intent.putExtra("note_id", note.id)
                    startActivity(intent)
                    finish()
                }
                true
            }
            R.id.action_delete -> {
                if (note != null) {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.confirm_delete)
                        .setMessage(note.title)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            NoteRepository.delete(note.id)
                            finish()
                        }
                        .setNegativeButton(R.string.no, null)
                        .show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        showNote()
    }
}
