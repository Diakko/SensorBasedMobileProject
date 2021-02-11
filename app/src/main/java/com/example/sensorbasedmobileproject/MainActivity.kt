package com.example.sensorbasedmobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    // RETROFIT
    private lateinit var searchResult: TextView
    private val fineliApiService by lazy { FineliApiService.create() }
    private var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchResult = findViewById(R.id.search_result)

        beginSearch("banaani")

    }

    // do search
    private fun beginSearch(srsearch: String) {
        fineliApiService.hitCountCheck("query", "json", "search", srsearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> "${srsearch}: ${result.query.searchInfo.id}  found".also {
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
}