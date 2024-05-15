package com.aura.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.domain.models.BootEvent
import com.aura.domain.usecases.GetBootEventUseCase
import com.aura.domain.usecases.SaveBootEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveBootEventUseCase: SaveBootEventUseCase,
    private val getBootEventUseCase: GetBootEventUseCase
) : ViewModel() {
    private val _saveBootEventStateFlow = MutableStateFlow<Boolean>(false)
    internal val saveBootEventStateFlow = _saveBootEventStateFlow.asStateFlow()
    private val _getBootEventsStateFlow = MutableStateFlow<List<BootEvent>>(mutableListOf())
    internal val getBootEventsStateFlow = _getBootEventsStateFlow.asStateFlow()

    fun save(event: BootEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _saveBootEventStateFlow.emit(saveBootEventUseCase.execute(event))
            } catch (e: Exception) {
                Log.e("Save bootEvent", "${e.message}")
            }

        }
    }

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _getBootEventsStateFlow.emit(getBootEventUseCase.execute())
            } catch (e: Exception) {
                Log.e("Get bootEvents", "${e.message}")
            }

        }
    }
}