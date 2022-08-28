package com.example.thinkami.bloodpressure.model

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): LocalDateTime? {
        val instant: Instant? = value?.let { Instant.ofEpochSecond(it) }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    @TypeConverter
    fun toTimeStamp(value: LocalDateTime?): Long? {
        return value?.atZone(ZoneId.systemDefault())?.toEpochSecond()
    }
}