package com.example.sensorbasedmobileproject

object FineliApiResponseModel {
    data class Result(val query: Query)
    data class Query(val searchInfo: SearchInfo)
    data class SearchInfo(val id: Int)
}