package com.aura.data.repository

import com.aura.data.storage.BootEventDbStorage
import com.aura.domain.models.BootEvent
import com.aura.domain.repository.BootEventRepository

class BootEventRepositoryImpl(
    private val bootEventDbStorage: BootEventDbStorage
) : BootEventRepository {

    override suspend fun getBootEvents(): List<BootEvent> {
//      TODO: Add result handling
        return bootEventDbStorage.getBootEvents()
    }

    override suspend fun addBootEvents(event: BootEvent): Boolean {
        return bootEventDbStorage.addBootEvent(event)
    }
}