package com.example.sensorbasedmobileproject.fragments.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.InputType.TYPE_CLASS_PHONE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sensorbasedmobileproject.ApiViewModel
import com.example.sensorbasedmobileproject.ApiViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import com.example.sensorbasedmobileproject.data.OffItemViewModel
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import com.example.sensorbasedmobileproject.repository.ApiRepository
import kotlinx.android.synthetic.main.custom_row_off.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search_off.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var viewModel: ApiViewModel
    private lateinit var mOffViewModel: OffItemViewModel
    private lateinit var editTextValue: Editable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        // Recyclerview
        val adapter = OffListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Off viewmodel
        mOffViewModel = ViewModelProvider(this).get(OffItemViewModel::class.java)
        mOffViewModel.readAllData.observe(viewLifecycleOwner, { off ->
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
        editText.inputType = InputType.TYPE_CLASS_NUMBER;

        // If no arguments
        if (arguments?.isEmpty == true) {
            editTextValue = editText.text
        } else {
            // If arguments found, do the search
            val ean = arguments?.getString("ean")
            viewModel.getOpenFood(ean.toString())
        }


        // Observe response
        viewModel.myOffResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful && response.body()?.status == 1) {
                Log.d("DBG", response.body()?.product?.allergens_from_ingredients.toString())
                insertDataToDatabase(response)
            } else {
                Log.d("DBG", response.errorBody().toString())
                Toast.makeText(
                    requireContext(),
                    "EAN not found in Open Food Facts API",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        // Listen to editText and on complete do search via viewModels getFood(),
        // clear editText and hide keyboard
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
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
        var exists: Boolean
        val code = response.body()?.code

        // Check if product is already in local database
        GlobalScope.launch(context = Dispatchers.IO) {
            exists = mOffViewModel.checkIfExists(code!!)

            // Product is in local database, inform user
            if (exists) {

                // Toasts inside GlobalScope need to be done with Handler/Looper
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        requireContext(),
                        "Product already in local database",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {

                // Product not in local database, proceed with adding into database
                val offItem = OffItem(
                    0,
                    code,
                    response.body()?.product?.product_name,
                    response.body()?.product?.ingredients_text_debug,
                    response.body()?.product?.image_url,
                    response.body()?.product?.ingredients_text,
                    response.body()?.product?.allergens_from_ingredients,
                    response.body()?.product?.manufacturing_places,
                    response.body()?.product?.link,
                    response.body()?.product?.nutriments!!
                )

                Log.d("DBG", offItem.toString())
                mOffViewModel.addOffData(offItem)

                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

private fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

private fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

