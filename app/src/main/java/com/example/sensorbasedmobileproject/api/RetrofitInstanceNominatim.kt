package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.utils.Constants.Companion.NOMINATIM_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceNominatim {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NOMINATIM_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NominatimApi by lazy {
        retrofit.create(NominatimApi::class.java)
    }
}