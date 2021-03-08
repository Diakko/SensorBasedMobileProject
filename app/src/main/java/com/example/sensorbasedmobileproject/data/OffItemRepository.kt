package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class OffItemRepository(private val offItemDao: OffItemDao) {

    val readAllData: LiveData<List<OffItem>> = offItemDao.readAllData()

    suspend fun addOffItem(offItem: OffItem) {
        offItemDao.addOffItem(offItem)
    }

    suspend fun checkIfExists(code: Long): Boolean {
        return offItemDao.productExists(code)
    }
}
