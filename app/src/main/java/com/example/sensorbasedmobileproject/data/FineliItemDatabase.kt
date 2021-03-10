/**
 * Description: Room Database for FineliItems
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sensorbasedmobileproject.utils.Converters

@Database(entities = [FineliItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class FineliItemDatabase : RoomDatabase() {

    // Get DAO
    abstract fun fineliDao(): FineliItemDao

    // Singleton
    companion object {
        @Volatile
        private var INSTANCE: FineliItemDatabase? = null

        // Returns the instance to be used
        fun getDatabase(context: Context): FineliItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FineliItemDatabase::class.java,
                    "fineli_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
