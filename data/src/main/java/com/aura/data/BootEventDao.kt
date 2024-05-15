package com.aura.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aura.data.models.BootEventEntity

@Dao
interface BootEventDao {
    @Insert
    suspend fun insert(bootEventEntity: BootEventEntity)

    @Query("SELECT * FROM BootEventEntity ORDER BY timestamp DESC")
    suspend fun getAll(): List<BootEventEntity>
}