/**
 * Description: Data class for the Nominatim model
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */

package com.example.sensorbasedmobileproject.model

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
