package com.karabotdev.campready.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packing_items")
data class PackingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val isPacked: Boolean = false
)