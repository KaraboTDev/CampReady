package com.karabotdev.campready.database

import androidx.room.*
import com.karabotdev.campready.data.CampLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<CampLocation>>

    @Upsert
    suspend fun upsert(location: CampLocation)

    @Delete
    suspend fun delete(location: CampLocation)
}