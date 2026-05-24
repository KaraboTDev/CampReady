package com.karabotdev.campready.database

import androidx.room.*
import com.karabotdev.campready.data.PackingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PackingDao {
    @Query("SELECT * FROM packing_items")
    fun getAllItems(): Flow<List<PackingItem>>

    @Upsert
    suspend fun upsert(item: PackingItem)

    @Update
    suspend fun update(item: PackingItem)

    @Delete
    suspend fun delete(item: PackingItem)
}