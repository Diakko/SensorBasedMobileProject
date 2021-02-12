package com.example.sensorbasedmobileproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    // RETROFIT
    private lateinit var searchResult: TextView
    private lateinit var btnSearchFineli: Button
    private val fineliApiService by lazy { FineliApiService.create() }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchResult = findViewById(R.id.search_result)
        btnSearchFineli = findViewById(R.id.btn_search_fineli)

        btnSearchFineli.setOnClickListener {
            Log.d("DBG", "button press")
            if (isNetworkAvailable(this)) {
                beginSearch("banaani") }
        }
    }

    // do search
    private fun beginSearch(q: String) {
        fineliApiService.getFineliData(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> "${q}: ${result.data} found".also {
                    Log.d("DBG", "RESULT: $it")
                    searchResult.text = it
                } },
                { error -> (error.message) }
            ).also { disposable = it }
    }

    // when quitting, dispose of ongoing async operations
    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}