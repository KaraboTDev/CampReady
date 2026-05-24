package com.karabotdev.campready.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karabotdev.campready.data.PackingItem
import com.karabotdev.campready.databinding.ItemPackingBinding

class PackingAdapter(private val onItemChecked: (PackingItem) -> Unit) :
    ListAdapter<PackingItem, PackingAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemPackingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PackingItem, onItemChecked: (PackingItem) -> Unit) {
            binding.tvItemName.text = item.name
            binding.tvCategory.text = item.category
            binding.cbPacked.isChecked = item.isPacked

            if (item.isPacked) {
                binding.tvItemName.paintFlags =
                    binding.tvItemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvItemName.paintFlags =
                    binding.tvItemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            binding.cbPacked.setOnClickListener {
                onItemChecked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPackingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemChecked)
    }

    class DiffCallback : DiffUtil.ItemCallback<PackingItem>() {
        override fun areItemsTheSame(oldItem: PackingItem, newItem: PackingItem) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PackingItem, newItem: PackingItem) =
            oldItem == newItem
    }
}