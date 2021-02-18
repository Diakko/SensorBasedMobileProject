package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class FineliItemRepository(private val fineliDao: FineliItemDao) {

    val readAllData: LiveData<List<FineliItem>> = fineliDao.readAllData()

    suspend fun addFineliInfo(fineliItem: FineliItem){
        fineliDao.addFineliInfo(fineliItem)
    }
}