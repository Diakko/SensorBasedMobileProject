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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sensorbasedmobileproject.MainViewModel
import com.example.sensorbasedmobileproject.MainViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.FineliItem
import com.example.sensorbasedmobileproject.data.FineliItemViewModel
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.repository.Repository
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchResult: TextView
    private lateinit var editText: EditText
    private lateinit var btnSearchFineli: Button
    private lateinit var viewModel: MainViewModel
    private lateinit var mFineliViewModel: FineliItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        mFineliViewModel = ViewModelProvider(this).get(FineliItemViewModel::class.java)
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
            if (response.isSuccessful) {

                insertDataToDatabase(response)

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

    private fun insertDataToDatabase(response: Response<ArrayList<Fineli>>) {
        val fineliId = response.body()?.get(0)?.id
        val energy = response.body()?.get(0)?.energy
        val energyKcal = response.body()?.get(0)?.energyKcal
        val fat = response.body()?.get(0)?.fat
        val protein = response.body()?.get(0)?.protein
        val carbohydrate = response.body()?.get(0)?.carbohydrate
        val alcohol = response.body()?.get(0)?.alcohol
        val organicAcids = response.body()?.get(0)?.organicAcids
        val sugarAlcohol = response.body()?.get(0)?.sugarAlcohol
        val saturatedFat = response.body()?.get(0)?.saturatedFat
        val fiber = response.body()?.get(0)?.fiber
        val sugar = response.body()?.get(0)?.sugar
        val salt = response.body()?.get(0)?.salt


        val fineli = FineliItem(
            0,
            fineliId,
            energy,
            energyKcal,
            fat,
            protein,
            carbohydrate,
            alcohol,
            organicAcids,
            sugarAlcohol,
            saturatedFat,
            fiber,
            sugar,
            salt
        )

        mFineliViewModel.addFineliData(fineli)
        Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
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
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}