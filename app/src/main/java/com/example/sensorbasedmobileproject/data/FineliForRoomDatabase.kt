package com.example.sensorbasedmobileproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FineliForRoom::class], version = 1, exportSchema = false)
abstract class FineliForRoomDatabase : RoomDatabase() {

    abstract fun fineliDao(): FineliForRoomDao

    companion object {
        @Volatile
        private var INSTANCE: FineliForRoomDatabase? = null

        fun getDatabase(context: Context): FineliForRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FineliForRoomDatabase::class.java,
                    "fineli_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}