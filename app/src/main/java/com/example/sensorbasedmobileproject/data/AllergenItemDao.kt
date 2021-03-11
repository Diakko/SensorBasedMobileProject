/**
 * Description: DAO for making queries into AllergenItemDatabase
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AllergenItemDao {

    @Query("SELECT * FROM allergenlist")
    fun readAllAllergens(): LiveData<List<AllergenItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllergenData(allergenItem: AllergenItem)

    @Delete
    fun deleteAllergens(allergenItem: AllergenItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAllergens(allergenItem: AllergenItem)

    @Query("DELETE FROM allergenlist")
    fun clearAllergens()

    @Query("SELECT EXISTS (SELECT 1 FROM allergenlist WHERE id = 0)")
    suspend fun productExists(): Boolean

    @Query("SELECT * FROM allergenlist WHERE id = 0")
    suspend fun getItem(): AllergenItem

}