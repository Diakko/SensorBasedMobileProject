package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.utils.Constants.Companion.BASE_URL_FINELI
import com.example.sensorbasedmobileproject.utils.Constants.Companion.BASE_URL_OPEN_FOOD_FACTS
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_FINELI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitOff by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_OPEN_FOOD_FACTS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val fineliApi: FineliApi by lazy {
        retrofit.create(FineliApi::class.java)
    }

    val openFoodApi: OpenFoodFactsApi by lazy {
        retrofitOff.create(OpenFoodFactsApi::class.java)
    }
}
