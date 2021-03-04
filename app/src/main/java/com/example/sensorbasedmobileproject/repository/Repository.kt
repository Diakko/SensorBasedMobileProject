package com.example.sensorbasedmobileproject.repository

import com.example.sensorbasedmobileproject.api.RetrofitInstance
import com.example.sensorbasedmobileproject.api.RetrofitInstanceNominatim
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.Nominatim
import retrofit2.Response

class Repository {

    suspend fun getFood(foodName: String): Response<ArrayList<Fineli>> {
        return RetrofitInstance.api.getFood(foodName)
    }

    suspend fun getNominatim(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("alepa")
    }

}