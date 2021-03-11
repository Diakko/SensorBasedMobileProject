/**
 * Description: Livedata and DAO connections for shopping list
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData

class ShoppingListItemRepository(private val shoppingListItemDao: ShoppingListItemDao) {

    val readAllData: LiveData<List<ShoppingListItem>> = shoppingListItemDao.readAllData()

    suspend fun addShoppingListInfo(shoppingListItem: ShoppingListItem) {
        shoppingListItemDao.insertShoppingListData(shoppingListItem)
    }



}