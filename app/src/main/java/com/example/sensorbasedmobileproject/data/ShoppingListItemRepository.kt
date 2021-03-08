package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class ShoppingListItemRepository(private val shoppingListItemDao: ShoppingListItemDao) {

    val readAllData: LiveData<List<ShoppingListItem>> = shoppingListItemDao.readAllData()

    suspend fun addShoppingListInfo(shoppingListItem: ShoppingListItem) {
        shoppingListItemDao.insertShoppingListData(shoppingListItem)
    }



}