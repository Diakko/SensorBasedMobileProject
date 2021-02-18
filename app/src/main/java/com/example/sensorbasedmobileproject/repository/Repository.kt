package com.example.sensorbasedmobileproject.repository

import com.example.sensorbasedmobileproject.api.RetrofitInstance
import com.example.sensorbasedmobileproject.model.Fineli
import retrofit2.Response

class Repository {

    suspend fun getFood(foodName: String): Response<ArrayList<Fineli>> {
        return RetrofitInstance.api.getFood(foodName)
    }

}