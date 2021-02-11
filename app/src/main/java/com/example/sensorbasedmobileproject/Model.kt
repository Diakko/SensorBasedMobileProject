package com.example.sensorbasedmobileproject

object Model {

    data class FineliResponse(
        val data: List<InfoResponse>
    )

    data class InfoResponse(
        val id: Int,
        val type: TypeResponse,
        val name: NameResponse,
        val energyKcal: Double
    )

    data class TypeResponse(
        val code: String
    )

    data class NameResponse(
        val fi: String,
        val sv: String,
        val en: String,
        val la: String
    )

}

