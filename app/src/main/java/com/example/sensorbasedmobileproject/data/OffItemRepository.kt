package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class OffItemRepository(private val offItemDao: OffItemDao) {

    val readAllData: LiveData<List<OffItem>> = offItemDao.readAllData()

    suspend fun addOffInfo(offItem: OffItem) {
        val code = offItem.code!!
        if (!offItemDao.productExists(code)) {
            offItemDao.addOffItem(offItem)
        }
    }

    fun checkIfExists(code: Long): Boolean {
        return offItemDao.productExists(code)
    }
}