package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.presentation.adapters.PersonalDataAdapter
import com.github.ncliff.passet.presentation.models.DataStorage
import com.github.ncliff.passet.presentation.models.MainFragmentViewModel
import com.github.ncliff.passet.presentation.models.PersonalDataViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(R.layout.fragment_main) {
    private var rvPersonalList: RecyclerView? = null
    private var rvPersonalDataAdapter: PersonalDataAdapter? = null
    private var fb: FloatingActionButton? = null
    private val mainFragmentViewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            PersonalDataViewModelFactory(DataStorage.getVersionsList())
        ).get(MainFragmentViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParams(view)
        subscribeToObservables()
    }

    private fun initParams(view: View) {
        rvPersonalList = view.findViewById(R.id.rv_personal_data_list)
        rvPersonalDataAdapter = PersonalDataAdapter { itemPosition ->
            mainFragmentViewModel.setPersonalDataPosition(itemPosition)
        }
        rvPersonalList?.adapter = rvPersonalDataAdapter
        fb = view.findViewById(R.id.floatingActionButton)
        fb?.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToGroupSelecting()
            findNavController().navigate(action)
        }
    }

    private fun subscribeToObservables() {
        mainFragmentViewModel.apply {
            personalDataPosition.observe(viewLifecycleOwner) {
                val action = MainFragmentDirections.actionMainFragmentToDetailsFragment2(personalData.value?.get(it))
                findNavController().navigate(action)
            }
            personalData.observe(viewLifecycleOwner) { list ->
                rvPersonalDataAdapter?.addPersonalList(list)
            }
        }
    }

    override fun onDestroy() {
        rvPersonalList = null
        rvPersonalDataAdapter = null
        super.onDestroy()
    }

}