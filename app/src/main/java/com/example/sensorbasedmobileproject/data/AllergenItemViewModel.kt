package com.example.sensorbasedmobileproject.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllergenItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllAllergenData: LiveData<List<AllergenItem>>
    private val repository: AllergenItemRepository

    init {
        val allergenItemDao = AllergenItemDatabase.getDatabase(application).allergenItemDao()
        repository = AllergenItemRepository(allergenItemDao)
        readAllAllergenData = repository.readAllAllergenData
    }

    fun addAllergenData(allergenItem: AllergenItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllergenInfo(allergenItem)
        }
    }
}