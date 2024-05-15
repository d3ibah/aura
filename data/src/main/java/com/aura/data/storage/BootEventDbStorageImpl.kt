package com.aura.data.storage

import com.aura.data.AppDatabase
import com.aura.data.models.BootEventEntity
import com.aura.domain.models.BootEvent

class BootEventDbStorageImpl(private val database: AppDatabase) : BootEventDbStorage {

    override suspend fun getBootEvents(): List<BootEvent> {
        return database.bootEventDao().getAll().mapToBootEvent()
    }

    override suspend fun addBootEvent(event: BootEvent): Boolean {
        database.bootEventDao().insert(event.mapToBootEventEntity())
////        TODO: Add logic. Change return type to Result
        return true
    }

    private fun List<BootEventEntity>.mapToBootEvent() =
        this.map {
            BootEvent(
//                id = it.id,
                timestamp = it.timestamp
            )
        }


    private fun BootEvent.mapToBootEventEntity() =
        BootEventEntity(
//                id = id,
            timestamp = timestamp
        )
}