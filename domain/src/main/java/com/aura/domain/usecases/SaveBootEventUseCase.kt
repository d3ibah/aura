package com.aura.domain.usecases

import com.aura.domain.models.BootEvent
import com.aura.domain.repository.BootEventRepository

class SaveBootEventUseCase(private val bootEventRepository: BootEventRepository) {
    suspend fun execute(event: BootEvent): Boolean {
        return bootEventRepository.addBootEvents(event)
    }
}