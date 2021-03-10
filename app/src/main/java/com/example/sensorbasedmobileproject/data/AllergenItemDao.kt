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

    @Query("DELETE FROM allergenlist")
    fun clearAllergens()

}