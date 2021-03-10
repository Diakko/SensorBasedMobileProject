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