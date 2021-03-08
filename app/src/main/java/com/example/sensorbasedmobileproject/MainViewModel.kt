
package com.example.sensorbasedmobileproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.model.Nominatim
import com.example.sensorbasedmobileproject.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    private val listOfShops = mutableListOf("r-kioski", "alepa", "k-market", "sale", "prisma", "supermarket", "citymarket", "s-market", "lidl", "abc")
    val myResponse: MutableLiveData<Response<ArrayList<Fineli>>> = MutableLiveData()
    val myNominatimResponse: MutableLiveData<Response<ArrayList<Nominatim>>> = MutableLiveData()

    fun getFood(foodName: String) {
        viewModelScope.launch {
            val response: Response<ArrayList<Fineli>> = repository.getFood(foodName)
            myResponse.value = response

        }
    }

    fun getShoppingList() {

    }

    // Search for all shops in Finland
    fun getNominatim() {
        for (shops in listOfShops) {
            viewModelScope.launch {
                val response: Response<ArrayList<Nominatim>> = repository.getNominatim(shops)
                myNominatimResponse.value = response
            }
        }
    }

    // Search for all shops that's not in the database, needs a string for search. Inefficient as the API url can be only so long
    fun getNominatimExcluded(excluded: String) {
        for (shops in listOfShops) {
            viewModelScope.launch {
                val response: Response<ArrayList<Nominatim>> = repository.getExcludedNominatim(shops, excluded)
                myNominatimResponse.value = response
            }
        }
    }

    // Search for nearby shops
    fun getNominatimViewBox(viewBox: String) {
        for (shop in listOfShops) {
            viewModelScope.launch {
                val response: Response<ArrayList<Nominatim>> = repository.getViewBoxNominatim(shop, viewBox)
                myNominatimResponse.value = response
            }
            Log.d("VIEWBOX", "Searched for $shop with $viewBox")
        }
    }

}