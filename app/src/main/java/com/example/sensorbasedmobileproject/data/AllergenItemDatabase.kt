/**
 * Description: Room Database entity for allergen item
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

@Database(entities = [(AllergenItem::class)], version = 1)
abstract class AllergenItemDatabase: RoomDatabase() {

    // Get DAO
    abstract fun allergenItemDao(): AllergenItemDao

    // Singleton
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