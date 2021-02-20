package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val fineliApi: FineliApi by lazy {
        retrofit.create(FineliApi::class.java)
    }

    val openFoodApi: OpenFoodFactsApi by lazy {
        retrofit.create(OpenFoodFactsApi::class.java)
    }
}
