
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

    private val listOfInitialShops = mutableListOf("r-kioski","alepa", "alepa helsinki", "alepa espoo", "alepa vantaa", "k-market helsinki", "k-market vantaa", "k-market espoo", "k-market", "prisma helsinki", "prisma vantaa", "prisma espoo", "s-market", "s-market espoo", "s-market helsinki", "s-market vantaa", "supermarket", "citymarket", "sale")
    private val listOfShops = mutableListOf("r-kioski", "alepa", "k-market", "sale", "prisma", "supermarket", "citymarket", "s-market", "lidl")
    val myResponse: MutableLiveData<Response<ArrayList<Fineli>>> = MutableLiveData()
    val myNominatimResponse: MutableLiveData<Response<ArrayList<Nominatim>>> = MutableLiveData()


    fun getFood(foodName: String) {
        viewModelScope.launch {
            val response: Response<ArrayList<Fineli>> = repository.getFood(foodName)
            myResponse.value = response

        }
    }



    fun getNominatim() {


        for (shops in listOfShops) {
            viewModelScope.launch {
                val response: Response<ArrayList<Nominatim>> = repository.getNominatim(shops)
                myNominatimResponse.value = response
            }
        }
    }

    fun getNominatimExcluded(excluded: String) {
        for (shops in listOfShops) {
            viewModelScope.launch {
                val response: Response<ArrayList<Nominatim>> = repository.getExcludedNominatim(shops, excluded)
                myNominatimResponse.value = response
            }
        }
    }

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