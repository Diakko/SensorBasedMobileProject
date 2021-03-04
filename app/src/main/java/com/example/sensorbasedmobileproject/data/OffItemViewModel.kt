package com.example.sensorbasedmobileproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OffItemViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<OffItem>>
    private val repository: OffItemRepository

    init {
        val offItemDao = OffItemDatabase.getDatabase(application).offDao()
        repository = OffItemRepository(offItemDao)
        readAllData = repository.readAllData
    }

    fun addOffData(offItem: OffItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOffItem(offItem)
        }
    }

    suspend fun checkIfExists(code: Long): Boolean {
        var exists = false
        val job = viewModelScope.launch(Dispatchers.IO) {
            exists = repository.checkIfExists(code)
        }
        job.join()
        return exists
    }

}
