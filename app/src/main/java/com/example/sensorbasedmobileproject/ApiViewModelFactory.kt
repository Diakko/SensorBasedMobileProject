package com.example.sensorbasedmobileproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sensorbasedmobileproject.repository.ApiRepository

class ApiViewModelFactory(private val apiRepository: ApiRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApiViewModel(apiRepository) as T
    }

}