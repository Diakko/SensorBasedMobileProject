/**
 * Description: Room Database entity for shopping list item
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppinglist")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val item_name: String,
    val amount: Int,
    val type: String) {
    override fun toString() = "$item_name - $amount $type"
}
