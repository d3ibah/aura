package com.aura.domain.usecases

import com.aura.domain.models.BootEvent
import com.aura.domain.repository.BootEventRepository

class GetBootEventUseCase(private val bootEventRepository: BootEventRepository) {
    suspend fun execute(): List<BootEvent> {

        return bootEventRepository.getBootEvents()
    }
}