package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class NominatimItemRepository(private val nominatimDao: NominatimItemDao) {

    val readAllData: LiveData<List<NominatimItem>> = nominatimDao.readAllData()

    suspend fun addNominatimInfo(nominatimItem: NominatimItem){
        nominatimDao.addNominatimInfo(nominatimItem)
    }

    suspend fun checkIfExists(place_id: Int): Boolean {
        return nominatimDao.productExists(place_id)
    }
}