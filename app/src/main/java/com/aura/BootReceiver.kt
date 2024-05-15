package com.aura

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aura.data.repository.BootEventRepositoryImpl
import com.aura.data.storage.BootEventDbStorageImpl
import com.aura.domain.models.BootEvent
import com.aura.domain.usecases.SaveBootEventUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private const val WORK_NAME = "BootNotificationWork"

private const val INTERVAL = 15L

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.e("BootReceiver", "getDbInstance")
            val saveBootEventUseCase =
                SaveBootEventUseCase(BootEventRepositoryImpl(BootEventDbStorageImpl((context.applicationContext as App).database)))
            GlobalScope.launch {
                saveBootEventUseCase.execute(BootEvent(timestamp = System.currentTimeMillis()))
            }

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<NotificationWorker>(INTERVAL, TimeUnit.MINUTES).build()
            )
        }
    }
}