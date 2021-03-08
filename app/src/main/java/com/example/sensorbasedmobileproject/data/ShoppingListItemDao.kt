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
