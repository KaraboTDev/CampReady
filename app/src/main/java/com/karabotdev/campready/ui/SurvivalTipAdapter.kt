package com.karabotdev.campready.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karabotdev.campready.data.SurvivalTip
import com.karabotdev.campready.databinding.ItemSurvivalTipBinding

class SurvivalTipAdapter : ListAdapter<SurvivalTip, SurvivalTipAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemSurvivalTipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tip: SurvivalTip) {
            binding.tvCategory.text = tip.category.uppercase()
            binding.tvTipTitle.text = tip.title
            binding.tvTipContent.text = tip.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSurvivalTipBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<SurvivalTip>() {
        override fun areItemsTheSame(oldItem: SurvivalTip, newItem: SurvivalTip) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SurvivalTip, newItem: SurvivalTip) =
            oldItem == newItem
    }
}