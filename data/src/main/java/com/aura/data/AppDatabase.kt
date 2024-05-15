package com.aura.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aura.data.models.BootEventEntity

@Database(entities = [BootEventEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bootEventDao(): BootEventDao
}

class AppDatabaseImpl(private val context: Context) {

    companion object {
        private const val DB_NAME = "boot_counter"
    }

    val instance by lazy { getDbInstance() }

    private fun getDbInstance(): AppDatabase {
        Log.e("AppDatabaseImpl", "getDbInstance")
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }
}