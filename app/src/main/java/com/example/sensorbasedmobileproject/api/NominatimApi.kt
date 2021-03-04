package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.model.Nominatim
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NominatimApi {

    @Headers("User-Agent: Nominatim-App")
    @GET("q")
    suspend fun getNominatim(@Query("q") q: String): Response<ArrayList<Nominatim>>
}