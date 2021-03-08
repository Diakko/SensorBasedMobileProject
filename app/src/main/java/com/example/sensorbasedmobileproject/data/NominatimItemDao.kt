package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NominatimItemDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addNominatimInfo(nominatim: NominatimItem)

    @Query("SELECT * FROM nominatim ORDER BY id ASC")
    fun readAllData(): LiveData<List<NominatimItem>>

    @Query("SELECT EXISTS (SELECT 1 FROM nominatim WHERE place_id = :place_id)")
    suspend fun productExists(place_id: Int): Boolean

}