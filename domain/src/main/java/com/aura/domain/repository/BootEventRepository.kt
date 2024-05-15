package com.aura.domain.repository

import com.aura.domain.models.BootEvent


interface BootEventRepository {

    suspend fun getBootEvents(): List<BootEvent>
    suspend fun addBootEvents(event: BootEvent): Boolean
}