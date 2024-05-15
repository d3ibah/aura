package com.aura

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aura.data.repository.BootEventRepositoryImpl
import com.aura.data.storage.BootEventDbStorageImpl
import com.aura.domain.usecases.GetBootEventUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.e("NotificationWorker", "getDbInstance")
        val getBootEventUseCase =
            GetBootEventUseCase(BootEventRepositoryImpl(BootEventDbStorageImpl((applicationContext as App).database)))

        val bootEvents = getBootEventUseCase.execute()

//          Create notification content based on bootEvents
        val notificationContent = when {
            bootEvents.isEmpty() -> "No boots detected"
            bootEvents.size == 1 -> "The boot was detected = ${formatDate(bootEvents[0].timestamp)}"
            else -> {
                val lastBoot = bootEvents[0].timestamp
                val previousBoot = bootEvents[1].timestamp
                val delta = (lastBoot - previousBoot) / 1000 // seconds
                "Last boots time delta = ${delta}s"
            }
        }

//          Display notification
        displayNotification("Boot Counter", notificationContent)

        return Result.success()
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun displayNotification(title: String, content: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "boot_counter_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Boot Counter Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        notificationManager.notify(1, notification)
    }
}