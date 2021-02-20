package com.example.sensorbasedmobileproject.repository

import com.example.sensorbasedmobileproject.api.RetrofitInstance
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import retrofit2.Response

class ApiRepository {

    suspend fun getFineliFood(foodName: String): Response<ArrayList<Fineli>> {
        return RetrofitInstance.fineliApi.getFood(foodName)
    }

    suspend fun getOpenFood(foodName: String): Response<ArrayList<OpenFoodFactResponse>> {
        return RetrofitInstance.openFoodApi.getOpenFood(foodName)
    }

}