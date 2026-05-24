package com.karabotdev.campready.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survival_tips")
data class SurvivalTip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val category: String
)