package com.github.ncliff.passet.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.databinding.FragmentNoteListBinding
import com.github.ncliff.passet.presentation.adapters.NotesAdapter
import com.github.ncliff.passet.presentation.models.SharedDatabaseViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.ui_view.ViewProvider


class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private var rvAdapter: NotesAdapter? = null
    private var noteList: List<Note> = emptyList()
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedDatabaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        settingUpList()
        onMapReady()
    }

    private fun settingUpList() {
        binding.NotesRecyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = NotesAdapter(
            { id ->
                val bundle = bundleOf("note_id" to id)
                findNavController().navigate(R.id.action_noteListFragment_to_note_navigation, bundle)
            },
            { id ->
                viewModel.bookwormUpdate(id) {}
            })
        binding.NotesRecyclerView.adapter = rvAdapter
        viewModel.getAllNotes().observe(viewLifecycleOwner) { listNotes ->
            noteList = listNotes.asReversed().toMutableList()
            rvAdapter?.setNoteList(listNotes.asReversed())
            if (rvAdapter?.itemCount != 0) {
                binding.includeEmptyList.imageView.visibility = View.INVISIBLE
                binding.includeEmptyList.textView.visibility = View.INVISIBLE
            } else {
                binding.includeEmptyList.imageView.visibility = View.VISIBLE
                binding.includeEmptyList.textView.visibility = View.VISIBLE
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteNote = noteList.get(viewHolder.adapterPosition)
                viewModel.delete(deleteNote) {}
                rvAdapter?.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(binding.addNewAccountButton, "${deleteNote.name} delete", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.insert(deleteNote) {}
                    }.show()
            }
        }).attachToRecyclerView(binding.NotesRecyclerView)
    }

    private fun onMapReady() {
        val locationManager = MapKitFactory.getInstance().createLocationManager()

        locationManager.requestSingleUpdate(object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                rvAdapter?.addLocation(location)
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {}
        })
    }

    private fun listeners() {
        binding.addNewAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_note_navigation)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}