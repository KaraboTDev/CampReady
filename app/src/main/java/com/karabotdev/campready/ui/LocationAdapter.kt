package com.karabotdev.campready.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karabotdev.campready.data.CampLocation
import com.karabotdev.campready.databinding.ItemLocationBinding

class LocationAdapter : ListAdapter<CampLocation, LocationAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: CampLocation) {
            binding.tvLocationName.text = location.name
            binding.tvRegion.text = "📍 ${location.region}"
            binding.tvDescription.text = location.description
            binding.tvDifficulty.text = "⚡ ${location.difficulty}"
            binding.tvSignal.text = if (location.hasSignal) "📶 Signal" else "🚫 No Signal"
            binding.tvFires.text = if (location.firesAllowed) "🔥 Fires OK" else "🚫 No Fires"
            binding.tvDangers.text = "⚠️ ${location.dangers}"
            binding.tvDistance.text = "🚗 ${location.distanceToTown}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CampLocation>() {
        override fun areItemsTheSame(oldItem: CampLocation, newItem: CampLocation) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CampLocation, newItem: CampLocation) =
            oldItem == newItem
    }
}