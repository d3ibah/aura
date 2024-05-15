package com.aura.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aura.App
import com.aura.data.repository.BootEventRepositoryImpl
import com.aura.data.storage.BootEventDbStorageImpl
import com.aura.domain.usecases.GetBootEventUseCase
import com.aura.domain.usecases.SaveBootEventUseCase

class MainViewModelFactory(app: App) : ViewModelProvider.Factory {

    private val bootEventRepository by lazy {
        BootEventRepositoryImpl(BootEventDbStorageImpl(database = (app.applicationContext as App).database))
    }
    private val saveBootEventUseCase by lazy { SaveBootEventUseCase(bootEventRepository) }
    private val getBootEventUseCase by lazy { GetBootEventUseCase(bootEventRepository) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(saveBootEventUseCase, getBootEventUseCase) as T
    }
}