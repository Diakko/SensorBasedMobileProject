
package com.example.sensorbasedmobileproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.Nominatim
import com.example.sensorbasedmobileproject.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<ArrayList<Fineli>>> = MutableLiveData()
    val myNominatimResponse: MutableLiveData<Response<ArrayList<Nominatim>>> = MutableLiveData()


    fun getFood(foodName: String) {
        viewModelScope.launch {
            val response: Response<ArrayList<Fineli>> = repository.getFood(foodName)
            myResponse.value = response

        }
    }

    fun getNominatim() {

        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getAlepas()
            myNominatimResponse.value = response
        }
        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getKMarkets()
            myNominatimResponse.value = response
        }/*
        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getPrismas()
            myNominatimResponse.value = response
        }
        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getSMarkets()
            myNominatimResponse.value = response
        }
        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getSupermarkets()
            myNominatimResponse.value = response
        }
        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getCitymarkets()
            myNominatimResponse.value = response
        }
        viewModelScope.launch {
            val response: Response<ArrayList<Nominatim>> = repository.getSales()
            myNominatimResponse.value = response
        }*/
    }

}