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