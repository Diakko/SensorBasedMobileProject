package com.example.sensorbasedmobileproject.model

import com.google.gson.annotations.SerializedName
import java.net.URL

data class Nominatim (

    val place_id: Int,
    val licence: String,
    val osm_type: String,
    val osm_id: Long,
    // val boundingbox: List<Double>,
    val lat: Double,
    val lon: Double,
    val display_name: String,
    // val type_class: String,
    val type: String,
    val importance: Double,
    val icon: String
)
