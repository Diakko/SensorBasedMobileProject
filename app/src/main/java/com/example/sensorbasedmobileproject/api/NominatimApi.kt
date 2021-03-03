package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.model.Nominatim
import retrofit2.Response
import retrofit2.http.GET

interface NominatimApi {

    @GET("search.php?q=alepa&format=json&countrycodes=fi")
    suspend fun getNominatim(): Response<ArrayList<Nominatim>>
}