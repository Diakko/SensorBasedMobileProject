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
    suspend fun getNominatim(place: String): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatim(place)
    }
    suspend fun getExcludedNominatim(place: String, excluded: String): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatimExcluded(place, excluded)
    }

    suspend fun getViewBoxNominatim(place: String, viewBoxString: String): Response<ArrayList<Nominatim>> {
        return RetrofitInstanceNominatim.api.getNominatimViewBox(place, viewBoxString)
    }

}