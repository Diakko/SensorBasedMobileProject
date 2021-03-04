package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class OffItemRepository(private val offItemDao: OffItemDao) {

    val readAllData: LiveData<List<OffItem>> = offItemDao.readAllData()

    suspend fun addOffItem(offItem: OffItem) {
        val code = offItem.code!!
        // If not in database, insert
//        if (!offItemDao.productExists(code)) {
            offItemDao.addOffItem(offItem)
            // Get confirmation of insert and make a Toast
//        }
    }

    suspend fun checkIfExists(code: Long): Boolean {
        return offItemDao.productExists(code)
    }
}
