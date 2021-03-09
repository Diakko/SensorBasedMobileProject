package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FineliItemDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addFineliInfo(fineli: FineliItem)

    @Query("SELECT * FROM fineli ORDER BY id ASC")
    fun readAllData(): LiveData<List<FineliItem>>

    @Query("SELECT * FROM fineli WHERE en = :name")
    suspend fun getProduct(name: String): List<FineliItem>
}
