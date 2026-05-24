package com.karabotdev.campready.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.karabotdev.campready.database.CampReadyDatabase
import com.karabotdev.campready.databinding.FragmentPackingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PackingFragment : Fragment() {

    private var _binding: FragmentPackingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = CampReadyDatabase.getDatabase(requireContext()).packingDao()
        val adapter = PackingAdapter { item ->
            viewLifecycleOwner.lifecycleScope.launch {
                dao.upsert(item.copy(isPacked = !item.isPacked))
            }
        }

        binding.rvPackingItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPackingItems.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            dao.getAllItems().collectLatest { items ->
                adapter.submitList(items)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}