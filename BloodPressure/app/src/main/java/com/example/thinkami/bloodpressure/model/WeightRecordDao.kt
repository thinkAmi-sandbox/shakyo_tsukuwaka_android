package com.example.thinkami.bloodpressure.model

import androidx.room.*

@Dao
interface WeightRecordDao {
    @Query("Select * From weight_record")
    suspend fun getAll(): List<WeightRecord>

    @Query("SELECT * FROM weight_record WHERE id = :id")
    suspend fun findById(id: Int): WeightRecord

    @Insert
    suspend fun insert(weight_record: WeightRecord)

    @Query("UPDATE weight_record SET weight = :weight WHERE id = :id")
    suspend fun update(id: Int, weight: Int)

    @Query("DELETE FROM weight_record WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM weight_record")
    suspend fun deleteAll()
}