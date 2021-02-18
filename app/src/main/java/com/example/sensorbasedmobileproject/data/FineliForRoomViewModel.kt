package com.example.sensorbasedmobileproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FineliForRoomViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<FineliForRoom>>
    private val repository: FineliForRoomRepository

    init {
        val fineliForRoomDao = FineliForRoomDatabase.getDatabase(application).fineliDao()
        repository = FineliForRoomRepository(fineliForRoomDao)
        readAllData = repository.readAllData
    }

    fun addFineliData(fineliForRoom: FineliForRoom) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFineliInfo(fineliForRoom)
        }
    }



}