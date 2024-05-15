package com.aura

import android.app.Application
import android.util.Log
import com.aura.data.AppDatabase
import com.aura.data.AppDatabaseImpl

class App: Application() {

    val database: AppDatabase by lazy { initDB() }

    override fun onCreate() {
        super.onCreate()

        
    }

    private fun initDB(): AppDatabase {
        Log.e("onCreate", "getDbInstance")
        return AppDatabaseImpl(context = this).instance
    }
}