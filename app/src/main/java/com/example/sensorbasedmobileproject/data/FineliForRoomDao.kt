package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FineliForRoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFineliInfo(fineli: FineliForRoom)

    @Query("SELECT * FROM fineli ORDER BY id ASC")
    fun readAllData(): LiveData<List<FineliForRoom>>

}