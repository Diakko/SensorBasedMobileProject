/**
 * Description: DAO for making queries into ShoppingListItemDatabase
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingListItemDao {

    @Query("SELECT * FROM shoppinglist")
    fun readAllData(): LiveData<List<ShoppingListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingListData(shoppingListItem: ShoppingListItem)

    @Delete
    fun delete(shoppingListItem: ShoppingListItem)

    @Query("DELETE FROM shoppinglist")
    fun clearShoppingList()
}
