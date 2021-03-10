/**
 * Description: Retrofit instances for making API calls
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.utils.Constants.Companion.BASE_URL_FINELI
import com.example.sensorbasedmobileproject.utils.Constants.Companion.BASE_URL_OPEN_FOOD_FACTS
import com.example.sensorbasedmobileproject.utils.Constants.Companion.NOMINATIM_URL
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
			
    private val retrofitNominatim by lazy {
        Retrofit.Builder()
            .baseUrl(NOMINATIM_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val fineliApi: FineliApi by lazy {
        retrofit.create(FineliApi::class.java)
    }

    val openFoodApi: OpenFoodFactsApi by lazy {
        retrofitOff.create(OpenFoodFactsApi::class.java)
    }

    val apiNominatim: NominatimApi by lazy {
        retrofitNominatim.create(NominatimApi::class.java)
    }
}
