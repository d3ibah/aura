package com.aura.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BootEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long
)
