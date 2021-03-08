package com.example.sensorbasedmobileproject.repository

import com.example.sensorbasedmobileproject.api.RetrofitInstance
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.Nominatim
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import retrofit2.Response

class ApiRepository {

    suspend fun getFineliFood(foodName: String): Response<ArrayList<Fineli>> {
        return RetrofitInstance.fineliApi.getFood(foodName)
    }

    suspend fun getOpenFood(ean: String): Response<OpenFoodFactResponse> {
        return RetrofitInstance.openFoodApi.getOpenFood(ean)
    }

    suspend fun getNominatim(place: String): Response<ArrayList<Nominatim>> {
        return RetrofitInstance.apiNominatim.getNominatim(place)
    }

    suspend fun getExcludedNominatim(place: String, excluded: String): Response<ArrayList<Nominatim>> {
        return RetrofitInstance.apiNominatim.getNominatimExcluded(place, excluded)
    }

    suspend fun getViewBoxNominatim(place: String, viewBoxString: String): Response<ArrayList<Nominatim>> {
        return RetrofitInstance.apiNominatim.getNominatimViewBox(place, viewBoxString)
    }

}