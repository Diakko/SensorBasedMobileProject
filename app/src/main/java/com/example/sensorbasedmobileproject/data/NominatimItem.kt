package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nominatim")
data class NominatimItem(
    @PrimaryKey(autoGenerate = true) val id: Int,

    val place_id: Int,
    val lat: Double,
    val lon: Double,
    val display_name: String
)
