package com.github.ncliff.passet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.database.Note
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class BookwormNotesAdapter(private val clickListener: (id: Int) -> Unit): RecyclerView.Adapter<BookwormNotesAdapter.NotesAdapter>() {
    private var listNote = emptyList<Note>()
    private var location: Location? = null

    class NotesAdapter(view: View) : RecyclerView.ViewHolder(view) {
        private val textName: TextView? = view.findViewById(R.id.cardName)
        private val textLogin: TextView? = view.findViewById(R.id.cardLogin)
        private val textDays: TextView? = view.findViewById(R.id.cardDays)
        private val textDistance: TextView? = view.findViewById(R.id.cardDistance)
        private val cardLine: View? = view.findViewById(R.id.cardLine)
        private val dayLayout: LinearLayout? = view.findViewById(R.id.cardDaysLayout)

        fun bind(note: Note, location: Location?) {
            val calendar = Calendar.getInstance()
            val end = SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(note.dateEnd)
            val oneDay = (24 * 60 * 60 * 1000).toDouble()
            var time = ceil((end.time - calendar.time.time)/ oneDay).toInt()
            if (time <= 0) time = 0

            cardLine?.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.roby))
            textName?.text = note.name
            textLogin?.text = note.login
            textDays?.text = "$time day"
            if (time <= 3) {
                textDays?.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                dayLayout?.setBackgroundResource(R.drawable.bg_day_shape)
            }
            if (location != null && note.locationLatitude != null && note.locationLongitude != null) {
                val dist = Geo.distance(
                    Point(location.position.latitude, location.position.longitude),
                    Point(note.locationLatitude!!, note.locationLongitude!!)
                ) / 1000
                textDistance?.text = "${String.format("%.1f", dist)} km"
            } else {
                textDistance?.text = ""
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNoteList(noteList: List<Note>) {
        listNote = noteList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addLocation(location: Location) {
        this.location = location
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = listNote[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesAdapter(layout)
    }

    override fun onBindViewHolder(holder: NotesAdapter, position: Int) {
        holder.bind(getItem(position), location)
        holder.itemView.setOnClickListener {
            val itemId = getItem(position).id
            if (itemId != null) {
                clickListener(itemId)
            }
        }
    }

    override fun getItemCount() = listNote.size
}