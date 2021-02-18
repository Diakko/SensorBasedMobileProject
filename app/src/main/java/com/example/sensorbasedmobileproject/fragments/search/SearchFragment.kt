package com.example.sensorbasedmobileproject.fragments.search

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sensorbasedmobileproject.MainViewModel
import com.example.sensorbasedmobileproject.MainViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.repository.Repository

class SearchFragment : Fragment() {

    private lateinit var searchResult: TextView
    private lateinit var editText: EditText

    private lateinit var btnSearchFineli: Button
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        editText = view.findViewById(R.id.searchable)
        var editTextValue = editText.text


        searchResult = view.findViewById(R.id.search_result)
        btnSearchFineli = view.findViewById(R.id.btn_search_fineli)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful) {
                searchResult.text =

                    "ID: " + response.body()?.get(0)?.id.toString() + "\n" +
                            "NAME: " + response.body()?.get(0)?.name?.fi.toString() + "\n" +
                            "DESC: " + response.body()?.get(0)?.type?.description?.fi.toString() + "\n" +
                            "KCAL: " + response.body()?.get(0)?.energyKcal.toString()
                Log.d("Response", response.body()?.get(0)?.id.toString())
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })

        btnSearchFineli.setOnClickListener {
            if (isNetworkAvailable(context)) {
                viewModel.getFood(editTextValue.toString())

            }
        }




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

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}