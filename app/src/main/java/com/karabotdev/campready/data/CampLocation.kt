package com.karabotdev.campready.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class CampLocation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val region: String,
    val description: String,
    val difficulty: String,
    val firesAllowed: Boolean,
    val hasSignal: Boolean,
    val distanceToTown: String,
    val dangers: String,
    val imageUrl: String = ""
)