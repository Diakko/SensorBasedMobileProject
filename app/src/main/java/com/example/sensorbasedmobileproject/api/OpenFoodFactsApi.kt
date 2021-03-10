/**
 * Description: Interface for Open Food Facts API queries
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.api

import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import retrofit2.Response
import retrofit2.http.*

interface OpenFoodFactsApi {

    companion object {
        private const val API_VER = "v0"

        /**
         * The api prefix URL
         */
        private const val API_P = "/api/$API_VER"
    }

    @Headers("User-Agent: Fineli-App")
    @GET("$API_P/product/{barcode}.json")
    suspend fun getOpenFood(
        @Path("barcode") barcode: String): Response<OpenFoodFactResponse>

}
