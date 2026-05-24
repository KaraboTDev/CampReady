package com.karabotdev.campready.database

import androidx.room.*
import com.karabotdev.campready.data.SurvivalTip
import kotlinx.coroutines.flow.Flow

@Dao
interface SurvivalTipDao {
    @Query("SELECT * FROM survival_tips")
    fun getAllTips(): Flow<List<SurvivalTip>>

    @Upsert
    suspend fun upsert(tip: SurvivalTip)
}