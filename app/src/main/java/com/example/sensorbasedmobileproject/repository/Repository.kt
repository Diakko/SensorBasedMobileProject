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

    suspend fun getAlepas(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("alepa")
    }
    suspend fun getSupermarkets(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("supermarket")
    }
    suspend fun getPrismas(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("prisma")
    }
    suspend fun getSMarkets(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("s-market")
    }
    suspend fun getSales(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("sale")
    }
    suspend fun getKMarkets(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("k-market")
    }
    suspend fun getCitymarkets(): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim("citymarket")
    }

}