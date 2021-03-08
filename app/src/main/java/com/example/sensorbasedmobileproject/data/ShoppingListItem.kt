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
