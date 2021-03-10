/**
 * Description: Converter helpers
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.utils

import androidx.room.TypeConverter
import com.example.sensorbasedmobileproject.model.Units
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: MutableList<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toMutableList()

    @TypeConverter
    fun unitsListToJson(value: MutableList<Units>?) = Gson().toJson(value)

    @TypeConverter
    fun unitsJsonToList(value: String) = Gson().fromJson(value, Array<Units>::class.java).toMutableList()


}