package com.aura.data.storage

import com.aura.domain.models.BootEvent


interface BootEventDbStorage {

    suspend fun getBootEvents(): List<BootEvent>

    suspend fun addBootEvent(event: BootEvent): Boolean

}