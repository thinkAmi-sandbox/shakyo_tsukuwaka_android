package com.example.thinkami.bloodpressure

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thinkami.bloodpressure.model.LocalDateTimeConverter
import com.example.thinkami.bloodpressure.model.WeightRecord
import com.example.thinkami.bloodpressure.model.WeightRecordDao

@Database(entities = [WeightRecord::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun weightRecordDao(): WeightRecordDao
}