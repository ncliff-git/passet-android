package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.ncliff.passet.R
import com.github.ncliff.passet.databinding.FragmentAccountListBinding


class AccountListFragment : Fragment(R.layout.fragment_account_list) {
    private var _binding: FragmentAccountListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountListFragment_to_accountSettingsFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}