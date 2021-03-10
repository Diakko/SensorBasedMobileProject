/**
 * Description: Livedata and DAO connections
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class FineliItemRepository(private val fineliDao: FineliItemDao) {

    val readAllData: LiveData<List<FineliItem>> = fineliDao.readAllData()

    suspend fun addFineliInfo(fineliItem: FineliItem){
        fineliDao.addFineliInfo(fineliItem)
    }

    suspend fun getFineliByOffItemName(name: String): List<FineliItem> {
        return fineliDao.getProduct(name)
    }
}
