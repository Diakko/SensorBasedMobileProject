package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OffItemDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addOffItem(off: OffItem)

    @Query("SELECT * FROM openfoodfacts ORDER BY id ASC")
    fun readAllData(): LiveData<List<OffItem>>

    @Query("SELECT EXISTS (SELECT 1 FROM openfoodfacts WHERE code = :code)")
    fun productExists(code: Long): Boolean

}
