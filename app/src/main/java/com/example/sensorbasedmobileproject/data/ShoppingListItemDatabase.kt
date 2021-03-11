/**
 * Description: Room database for shopping list items
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ShoppingListItem::class)], version = 1)
abstract class ShoppingListItemDatabase: RoomDatabase() {

    // Get DAO
    abstract fun shoppingListItemDao(): ShoppingListItemDao

    // Singleton
    companion object {
        private var sInstance: ShoppingListItemDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): ShoppingListItemDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.applicationContext, ShoppingListItemDatabase::class.java, "shoppinglistitems.db").build()
            }
            return sInstance!!
        }
    }
}