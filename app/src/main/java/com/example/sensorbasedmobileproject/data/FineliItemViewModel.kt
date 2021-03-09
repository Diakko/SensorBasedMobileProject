package com.example.sensorbasedmobileproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FineliItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<FineliItem>>
    private val repository: FineliItemRepository

    init {
        val fineliItemDao = FineliItemDatabase.getDatabase(application).fineliDao()
        repository = FineliItemRepository(fineliItemDao)
        readAllData = repository.readAllData
    }

    fun addFineliData(fineliItem: FineliItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFineliInfo(fineliItem)
        }
    }

    suspend fun getFineliByOffItemName(name: String): List<FineliItem> {
        lateinit var listOfFineliItems: List<FineliItem>
        val job = viewModelScope.launch(Dispatchers.IO) {
            listOfFineliItems = repository.getFineliByOffItemName(name)
        }
        job.join()
        return listOfFineliItems
    }



}
