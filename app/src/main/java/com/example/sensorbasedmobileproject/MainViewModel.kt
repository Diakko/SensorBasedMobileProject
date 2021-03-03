
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
            val response: Response<ArrayList<Nominatim>> = repository.getNominatim()
            myNominatimResponse.value = response
        }
    }

}