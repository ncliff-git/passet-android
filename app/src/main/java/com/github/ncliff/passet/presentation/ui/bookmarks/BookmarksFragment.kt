package com.github.ncliff.passet.presentation.ui.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.database.Note
import com.github.ncliff.passet.databinding.FragmentBookmarksBinding
import com.github.ncliff.passet.presentation.adapters.BookwormNotesAdapter
import com.github.ncliff.passet.presentation.adapters.NotesAdapter
import com.github.ncliff.passet.presentation.models.SharedDatabaseViewModel
import com.github.ncliff.passet.presentation.ui.home.NoteListFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus

class BookmarksFragment : Fragment() {
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private var rvAdapter: BookwormNotesAdapter? = null
    private var noteList: List<Note> = emptyList()
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedDatabaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingUpList()
        onMapReady()
    }

    private fun settingUpList() {
        binding.bookmarksRecyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = BookwormNotesAdapter() { id ->
                val action = BookmarksFragmentDirections.actionBookmarksFragmentToAccountSettingsFragment2(id)
                findNavController().navigate(action)
            }
        binding.bookmarksRecyclerView.adapter = rvAdapter
        viewModel.getAllBookworms().observe(viewLifecycleOwner) { listNotes ->
            noteList = listNotes.asReversed().toMutableList()
            rvAdapter?.setNoteList(listNotes.asReversed())
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
                Snackbar.make(binding.root, "${deleteNote.name} delete", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.insert(deleteNote) {}
                    }.show()
            }
        }).attachToRecyclerView(binding.bookmarksRecyclerView)
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
}