/**
 * Description: Room Database for OffItems
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

@Database(entities = [OffItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class OffItemDatabase : RoomDatabase() {

    // Get DAO
    abstract fun offDao(): OffItemDao

    // Singleton
    companion object {
        @Volatile
        private var INSTANCE: OffItemDatabase? = null

        // Returns the instance to be used
        fun getDatabase(context: Context): OffItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OffItemDatabase::class.java,
                    "off_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}