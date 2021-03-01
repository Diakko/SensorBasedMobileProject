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

    abstract fun offDao(): OffItemDao

    companion object {
        @Volatile
        private var INSTANCE: OffItemDatabase? = null

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