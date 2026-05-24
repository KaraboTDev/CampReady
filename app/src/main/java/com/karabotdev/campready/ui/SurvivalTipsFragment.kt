package com.karabotdev.campready.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.karabotdev.campready.database.CampReadyDatabase
import com.karabotdev.campready.databinding.FragmentSurvivalTipsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SurvivalTipsFragment : Fragment() {

    private var _binding: FragmentSurvivalTipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurvivalTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = CampReadyDatabase.getDatabase(requireContext()).survivalTipDao()
        val adapter = SurvivalTipAdapter()

        binding.rvSurvivalTips.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSurvivalTips.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            dao.getAllTips().collectLatest { tips ->
                adapter.submitList(tips)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}