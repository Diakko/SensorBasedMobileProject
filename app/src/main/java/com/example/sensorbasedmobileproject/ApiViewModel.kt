
package com.example.sensorbasedmobileproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import com.example.sensorbasedmobileproject.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiViewModel(private val apiRepository: ApiRepository): ViewModel() {

    val myFineliResponse: MutableLiveData<Response<ArrayList<Fineli>>> = MutableLiveData()
    val myOpenResponse: MutableLiveData<Response<ArrayList<OpenFoodFactResponse>>> = MutableLiveData()


    fun getFineliFood(foodName: String) {
        viewModelScope.launch {
            val response: Response<ArrayList<Fineli>> = apiRepository.getFineliFood(foodName)
            myFineliResponse.value = response

        }
    }

    fun getOpenFood(foodName: String) {
        viewModelScope.launch {
            val response: Response<ArrayList<OpenFoodFactResponse>> = apiRepository.getOpenFood(foodName)
            myOpenResponse.value = response

        }
    }

}