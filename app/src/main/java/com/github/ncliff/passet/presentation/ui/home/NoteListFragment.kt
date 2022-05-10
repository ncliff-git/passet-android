package com.github.ncliff.passet.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ncliff.passet.R
import com.github.ncliff.passet.databinding.FragmentNoteListBinding
import com.github.ncliff.passet.presentation.adapters.NotesAdapter

class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private var rvAdapter: NotesAdapter? = null
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
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
    }

    private fun settingUpList() {
        binding.NotesRecyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = NotesAdapter { position ->
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteSettingsFragment(position)
            findNavController().navigate(action)
        }
        binding.NotesRecyclerView.adapter = rvAdapter
        viewModel.getAllNotes().observe(viewLifecycleOwner) { listNotes ->
            rvAdapter?.setNoteList(listNotes.asReversed())
            if (rvAdapter?.itemCount != 0) {
                binding.includeEmptyList.imageView.visibility = View.INVISIBLE
                binding.includeEmptyList.textView.visibility = View.INVISIBLE
            } else {
                binding.includeEmptyList.imageView.visibility = View.VISIBLE
                binding.includeEmptyList.textView.visibility = View.VISIBLE
            }
            // TODO: Убрать логи
            listNotes.forEach {
                Log.d("List", "----- ${it.id} ${it.name}")
            }
        }
    }

    private fun listeners() {
        binding.addNewAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_noteSettingsFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}