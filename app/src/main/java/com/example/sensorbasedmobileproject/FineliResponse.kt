package com.example.sensorbasedmobileproject

object FineliResponse {

    data class FineliResponse(
        val data: List<Food>,
    )

    data class Food(
        val id: Int,
        val type: Type,
        val name: Name,
        val energyKcal: Double,
    )

    data class Type(
        val code: String,
    )

    data class Name(
        val fi: String,
        val sv: String,
        val en: String,
        val la: String,
    )

}

