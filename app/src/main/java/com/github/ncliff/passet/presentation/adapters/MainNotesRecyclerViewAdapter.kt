package com.github.ncliff.passet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.PasswordNote

class MainNotesRecyclerViewAdapter() : RecyclerView.Adapter<MainNotesRecyclerViewAdapter.ViewHolder>() {
    private var _passwordNoteArrayList = ArrayList<PasswordNote>()

    @SuppressLint("NotifyDataSetChanged")
    fun addPasswordNotesList(passwordNoteList: List<PasswordNote>) {
        _passwordNoteArrayList = ArrayList(passwordNoteList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = _passwordNoteArrayList.size

    private fun getItem(position: Int) = _passwordNoteArrayList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textName: TextView? = view.findViewById(R.id.cardName)
        private val textLogin: TextView? = view.findViewById(R.id.cardLogin)
        private val textDays: TextView? = view.findViewById(R.id.cardDays)
        private val textDistance: TextView? = view.findViewById(R.id.cardDistance)

        fun bind(note: PasswordNote) {
            textName?.text = note.name
            textLogin?.text = note.login
            // TODO: Решить ворос как добавлять информацию о днях
            textDays?.text = "30 дней"
            // TODO: Решить ворос как измерять дистанцию
            textDistance?.text = "2 км"
        }
    }
}