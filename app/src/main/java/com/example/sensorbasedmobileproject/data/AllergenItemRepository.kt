/**
 * Description: Livedata and DAO connections for Allergens
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class AllergenItemRepository(private val allergenItemDao: AllergenItemDao) {

    val readAllAllergenData: LiveData<List<AllergenItem>> = allergenItemDao.readAllAllergens()

    suspend fun updateAllergenInfo(allergenItem: AllergenItem) {
        allergenItemDao.updateAllergens(allergenItem)
    }

    suspend fun addAllergenInfo(allergenItem: AllergenItem) {
        allergenItemDao.insertAllergenData(allergenItem)
    }

    suspend fun checkIfExists(): Boolean {
        return allergenItemDao.productExists()
    }

    suspend fun clearDB() {
        return allergenItemDao.clearAllergens()
    }

    suspend fun getAllergyItem(): AllergenItem {
        return allergenItemDao.getItem()
    }
}