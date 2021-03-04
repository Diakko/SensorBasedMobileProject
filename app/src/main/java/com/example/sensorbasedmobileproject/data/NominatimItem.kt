package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nominatim")
data class NominatimItem(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val place_id: Int,
    val licence: String,
    val osm_type: String,
    val osm_id: Long,
    // val boundingbox: List<Double>,
    val lat: Double,
    val lon: Double,
    val display_name: String,
    // @SerializedName("type_class") val class: String,
    val type: String,
    val importance: Double,
    val icon: String
)
