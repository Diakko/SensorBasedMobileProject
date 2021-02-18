package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class FineliForRoomRepository(private val fineliDao: FineliForRoomDao) {

    val readAllData: LiveData<List<FineliForRoom>> = fineliDao.readAllData()

    suspend fun addFineliInfo(fineliForRoom: FineliForRoom){
        fineliDao.addFineliInfo(fineliForRoom)
    }
}