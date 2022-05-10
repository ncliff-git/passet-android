package com.github.ncliff.passet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.database.Note

class NotesAdapter(private val clickListener: (position: Int) -> Unit) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private var _noteList = emptyList<Note>()

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textName: TextView? = view.findViewById(R.id.cardName)
        private val textLogin: TextView? = view.findViewById(R.id.cardLogin)
        private val textDays: TextView? = view.findViewById(R.id.cardDays)
        private val textDistance: TextView? = view.findViewById(R.id.cardDistance)

        fun bind(note: Note) {
            //val time = note.dateEnd.toInt() - note.dateStart.toInt()

            textName?.text = note.name
            textLogin?.text = note.login
            // TODO: Решить ворос как добавлять информацию о днях
            //textDays?.text = "$time дней"
            // TODO: Решить ворос как измерять дистанцию
            textDistance?.text = "2 км"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNoteList(noteList: List<Note>) {
        _noteList = noteList
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = _noteList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val itemId = getItem(position).id
            if (itemId != null) {
                clickListener(itemId)
            }
        }
    }

    override fun getItemCount() = _noteList.size
}