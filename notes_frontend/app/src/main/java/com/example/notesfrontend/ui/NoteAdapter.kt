package com.example.notesfrontend.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.R
import com.example.notesfrontend.model.Note
import java.text.SimpleDateFormat
import java.util.*

/**
 * RecyclerView Adapter for displaying notes.
 */
// PUBLIC_INTERFACE
class NoteAdapter(
    private var notes: List<Note>,
    private val onNoteClicked: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    fun updateList(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(v)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], onNoteClicked)
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textContentPreview: TextView = itemView.findViewById(R.id.textContentPreview)
        private val textDate: TextView = itemView.findViewById(R.id.textDate)

        fun bind(note: Note, onClick: (Note) -> Unit) {
            textTitle.text = note.title
            textContentPreview.text = note.content.trim().replace("\n", " ")
                .let { if (it.length > 60) it.substring(0, 57) + "..." else it }
            textDate.text = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault()).format(Date(note.updatedAt))

            itemView.setOnClickListener { onClick(note) }
        }
    }
}
