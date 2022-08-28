package com.example.thinkami.bloodpressure

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    companion object {
        lateinit var database: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            "weight_record"
        ).build()
    }
}