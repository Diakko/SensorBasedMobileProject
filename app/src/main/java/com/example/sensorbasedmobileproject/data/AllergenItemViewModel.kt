/**
 * Description: View model setup for allergen items
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

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

    fun updateAllergenData(allergenItem: AllergenItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAllergenInfo(allergenItem)
        }
    }

    fun clearDB(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearDB()
        }
    }

    // Get one AllergenItem
    suspend fun getAllergenItem(): AllergenItem {
        lateinit var exists: AllergenItem
        val job = viewModelScope.launch(Dispatchers.IO) {
            exists = repository.getAllergyItem()
        }
        job.join()
        return exists
    }

    // Check if exists by Ean code
    suspend fun checkIfExists(): Boolean {
        var exists = false
        val job = viewModelScope.launch(Dispatchers.IO) {
            exists = repository.checkIfExists()
        }
        job.join()
        return exists
    }
}