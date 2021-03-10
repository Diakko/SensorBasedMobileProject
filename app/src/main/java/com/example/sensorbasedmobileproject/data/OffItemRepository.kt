/**
 * Description: Livedata and DAO connections
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

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

    suspend fun getOffItem(code: Long): OffItem {
        return offItemDao.getProduct(code)
    }

}
