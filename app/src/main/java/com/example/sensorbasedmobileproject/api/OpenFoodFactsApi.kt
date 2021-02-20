
package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface OpenFoodFactsApi {

    @Headers("User-Agent: Fineli-App")
    @GET("foods")
    suspend fun getOpenFood(@Query("q") q: String): Response<ArrayList<OpenFoodFactResponse>>
}