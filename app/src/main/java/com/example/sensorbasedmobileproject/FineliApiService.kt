package com.example.sensorbasedmobileproject

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface FineliApiService {

    @GET("foods")
    fun getFineliData(@Query("q") q: String): Observable<Model.InfoResponse>

    companion object {
        fun create(): FineliApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    MoshiConverterFactory.create())
                .baseUrl("https://fineli.fi/fineli/api/v1/")
                .build()

            return retrofit.create(FineliApiService::class.java)
        }
    }
}
