/**
 * Description: DAO for making queries into OffItemDatabase
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

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
    suspend fun productExists(code: Long): Boolean

    @Query("SELECT * FROM openfoodfacts WHERE code = :code")
    suspend fun getProduct(code: Long): OffItem

}
