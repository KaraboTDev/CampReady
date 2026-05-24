package com.karabotdev.campready.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.karabotdev.campready.database.CampReadyDatabase
import com.karabotdev.campready.databinding.FragmentLocationsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationsFragment : Fragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = CampReadyDatabase.getDatabase(requireContext()).locationDao()
        val adapter = LocationAdapter()

        binding.rvLocations.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLocations.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            dao.getAllLocations().collectLatest { locations ->
                adapter.submitList(locations)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}