package com.github.ncliff.passet.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.R
import com.github.ncliff.passet.databinding.FragmentNoteListBinding
import com.github.ncliff.passet.presentation.adapters.MainNotesRecyclerViewAdapter

class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    private var _binding: FragmentNoteListBinding? = null
    private var rvAdapter: MainNotesRecyclerViewAdapter? = null
    private val binding get() = _binding!!

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
        permissions()
    }

    private fun listeners() {
        binding.addNewAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountListFragment_to_accountSettingsFragment)
        }
    }

    private fun permissions() {
        val requestFineLocation = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("TagCheck", "permission granted")
            } else {
                Log.d("TagCheck", "permission denied")
            }
        }

        requestFineLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}