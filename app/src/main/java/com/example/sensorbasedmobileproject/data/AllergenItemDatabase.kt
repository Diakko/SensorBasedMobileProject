package com.example.sensorbasedmobileproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(AllergenItem::class)], version = 1)
abstract class AllergenItemDatabase: RoomDatabase() {
    abstract fun allergenItemDao(): AllergenItemDao

    companion object {
        private var sInstance: AllergenItemDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): AllergenItemDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.applicationContext,
                    AllergenItemDatabase::class.java,
                    "allergenitems.db").build()
            }
            return sInstance!!
        }
    }

}