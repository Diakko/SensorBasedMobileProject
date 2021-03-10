package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class AllergenItemRepository(private val allergenItemDao: AllergenItemDao) {

    val readAllAllergenData: LiveData<List<AllergenItem>> = allergenItemDao.readAllAllergens()

    suspend fun addAllergenInfo(allergenItem: AllergenItem) {
        allergenItemDao.insertAllergenData(allergenItem)
    }
}