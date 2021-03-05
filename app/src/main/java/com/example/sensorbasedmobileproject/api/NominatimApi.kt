package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.model.Nominatim
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NominatimApi {

    @Headers("User-Agent: Nominatim-App")
    @GET("search.php?format=json&countrycodes=fi&limit=50")
    suspend fun getNominatim(@Query("q") q: String): Response<ArrayList<Nominatim>>

    @Headers("User-Agent: Nominatim-App")
    @GET("search.php?format=json&countrycodes=fi&limit=50")
    suspend fun getNominatimExcluded(@Query("q") q: String, @Query("exclude_place_ids") exclude_place_ids: String): Response<ArrayList<Nominatim>>
}