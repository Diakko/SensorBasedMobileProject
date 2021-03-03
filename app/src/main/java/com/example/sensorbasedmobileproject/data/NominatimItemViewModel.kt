package com.example.sensorbasedmobileproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NominatimItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<NominatimItem>>
    private val repository: NominatimItemRepository

    init {
        val nominatimItemDao = NominatimItemDatabase.getDatabase(application).nominatimDao()
        repository = NominatimItemRepository(nominatimItemDao)
        readAllData = repository.readAllData
    }
    fun addNominatimData(nominatimItem: NominatimItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNominatimInfo(nominatimItem)
        }
    }
}