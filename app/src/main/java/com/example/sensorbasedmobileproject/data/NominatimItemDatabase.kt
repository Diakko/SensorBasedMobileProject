package com.example.sensorbasedmobileproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sensorbasedmobileproject.model.Nominatim
import com.example.sensorbasedmobileproject.utils.Converters

@Database(entities = [NominatimItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class NominatimItemDatabase : RoomDatabase() {

    abstract fun nominatimDao(): NominatimItemDao

    companion object{
        @Volatile
        private var INSTANCE: NominatimItemDatabase? = null

        fun getDatabase(context: Context): NominatimItemDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NominatimItemDatabase::class.java,
                    "nominatim_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}