/**
 * Description: View model setup for Shopping list items
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

class ShoppingListItemViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ShoppingListItem>>
    private val repository: ShoppingListItemRepository

    init {
        val shoppingListDao = ShoppingListItemDatabase.getDatabase(application).shoppingListItemDao()
        repository = ShoppingListItemRepository(shoppingListDao)
        readAllData = repository.readAllData
    }

    fun addShoppingListData(shoppingListItem: ShoppingListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addShoppingListInfo(shoppingListItem)
        }
    }
}