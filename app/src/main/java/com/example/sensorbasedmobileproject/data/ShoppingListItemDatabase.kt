package com.example.sensorbasedmobileproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ShoppingListItem::class)], version = 1)
abstract class ShoppingListItemDatabase: RoomDatabase() {
    abstract fun shoppingListItemDao(): ShoppingListItemDao

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