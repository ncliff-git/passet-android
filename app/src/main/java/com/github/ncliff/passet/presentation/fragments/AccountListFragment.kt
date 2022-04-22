package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.DataUtils
import com.github.ncliff.passet.databinding.FragmentAccountListBinding
import com.github.ncliff.passet.presentation.adapters.MainNotesRecyclerViewAdapter

class AccountListFragment : Fragment(R.layout.fragment_account_list) {
    private var _binding: FragmentAccountListBinding? = null
    private var rvAdapter: MainNotesRecyclerViewAdapter? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountListBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        permissions()

        // TODO: Создать отдельно
        rvAdapter = MainNotesRecyclerViewAdapter()
        binding.NotesRecyclerView.adapter = rvAdapter

        binding.addNewAccountButton.setOnLongClickListener {
            rvAdapter?.addPasswordNotesList(DataUtils.testAddPasswordList())
            Toast.makeText(context, "LONG", Toast.LENGTH_SHORT).show()
            true
        }
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