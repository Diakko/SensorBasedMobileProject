package com.example.sensorbasedmobileproject.fragments.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sensorbasedmobileproject.ApiViewModel
import com.example.sensorbasedmobileproject.ApiViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import com.example.sensorbasedmobileproject.data.OffItemViewModel
import com.example.sensorbasedmobileproject.fragments.search.OffListAdapter
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import com.example.sensorbasedmobileproject.repository.ApiRepository
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search_off.view.*
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var viewModel: ApiViewModel
    private lateinit var mOffViewModel: OffItemViewModel
    private lateinit var editTextValue: Editable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        // Recyclerview
        val adapter = OffListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Off viewmodel
        mOffViewModel = ViewModelProvider(this).get(OffItemViewModel::class.java)
        mOffViewModel.readAllData.observe(viewLifecycleOwner, Observer { off ->
            adapter.setData(off)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up viewModel stuffs
        val repository = ApiRepository()
        val viewModelFactory = ApiViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ApiViewModel::class.java)

        // Set up editText
        editText = view.findViewById(R.id.ean)

        // If no arguments
        if (arguments?.isEmpty == true) {
            editTextValue = editText.text
        } else {
            // If arguments found, do the search
            val ean = arguments?.getString("ean")
            viewModel.getOpenFood(ean.toString())
        }

        // Observe response
        viewModel.myOffResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                insertDataToDatabase(response)
            } else {
                Log.d("DBG", response.errorBody().toString())
                Toast.makeText(requireContext(), "EAN not found in Open Food Facts API", Toast.LENGTH_LONG).show()
            }
        })

        // Listen to editText and on complete do search via viewModels getFood(),
        // clear editText and hide keyboard
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("DBG", "Start EAN search")
                viewModel.getOpenFood(editTextValue.toString())
                editText.text.clear()
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun insertDataToDatabase(response: Response<OpenFoodFactResponse>) {

        val code = response.body()?.code?.toLong()
        val ingredients_text_debug = response.body()?.product?.ingredients_text_debug
        val image_url = response.body()?.product?.image_url
        val product_name = response.body()?.product?.product_name

        val offItem = OffItem(
            0,
            code,
            product_name,
            ingredients_text_debug,
            image_url
        )

        mOffViewModel.addOffData(offItem)
        Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

