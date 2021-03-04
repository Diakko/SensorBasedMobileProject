
package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.model.Fineli
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FineliApi {

    @Headers("User-Agent: Fineli-App")
    @GET("foods")
    suspend fun getFood(@Query("q") q: String): Response<ArrayList<Fineli>>
}
