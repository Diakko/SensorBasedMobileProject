/**
 * Description: ViewModel setup for Fineli
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

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

    // Add FineliItem to database
    fun addFineliData(fineliItem: FineliItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFineliInfo(fineliItem)
        }
    }
}
