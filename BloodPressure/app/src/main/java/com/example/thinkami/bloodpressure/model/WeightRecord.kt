package com.example.thinkami.bloodpressure.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "weight_record")
data class WeightRecord(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="weight") val weight: Int,
    @ColumnInfo(name="recorded_at") val recordedAt: LocalDateTime?
)
