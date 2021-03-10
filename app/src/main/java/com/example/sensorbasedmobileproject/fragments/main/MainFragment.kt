/**
 * Description:
 *
 * Fragment for searching Open Food Facts API via
 * 1) scanning a barcode or
 * 2) entering an ean code to the search field
 *
 * - Displays found items in a recyclerview
 * - Navigate to product details by clicking an item
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.fragments.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sensorbasedmobileproject.ApiViewModel
import com.example.sensorbasedmobileproject.ApiViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import com.example.sensorbasedmobileproject.data.OffItemViewModel
import com.example.sensorbasedmobileproject.model.openfoodfacts.OpenFoodFactResponse
import com.example.sensorbasedmobileproject.repository.ApiRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private lateinit var fabScan: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        // Recyclerview setup
        val adapter = OffListAdapter()

        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // OffItem viewmodel setup
        mOffViewModel = ViewModelProvider(this).get(OffItemViewModel::class.java)
        mOffViewModel.readAllData.observe(viewLifecycleOwner, { off ->
            adapter.setData(off)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up FAB
        fabScan = view.findViewById(R.id.floatingActionButton)
        fabScan.setOnClickListener {
            Navigation.findNavController(view).navigate(
                MainFragmentDirections.actionActionHomeToActionCamera()
            )
        }

        // API viewmodel setup
        val repository = ApiRepository()
        val viewModelFactory = ApiViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ApiViewModel::class.java)

        // Set up editText
        editText = view.findViewById(R.id.ean)
        editText.inputType = InputType.TYPE_CLASS_NUMBER

        // If no arguments, search is empty
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
                insertDataToDatabase(response)
            } else {
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
                viewModel.getOpenFood(editTextValue.toString())
                editText.text.clear()
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })

        // TODO: remove from final product. This method gets some products for debugging purposes.
        getItems(viewModel)
    }

    private fun insertDataToDatabase(response: Response<OpenFoodFactResponse>) {
        var exists: Boolean
        val code = response.body()?.code

        // Check if product is already in local database
        GlobalScope.launch(context = Dispatchers.IO) {
            exists = mOffViewModel.checkIfExists(code!!)

            // Product is in local database, inform user
            if (exists) {

                // TODO: check that doesn't toast when coming back from details view
                // Toasts inside GlobalScope need to be done with coroutines
//                launch(Dispatchers.Main) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Product already in local database",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }

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

                mOffViewModel.addOffData(offItem)

                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

// For hiding the soft keyboard
private fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

private fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

// Helper method for getting product data from Open Food Facts
private fun getItems(viewModel: ApiViewModel) {
    val list = listOf(
        "8076809513388",
        "6408430011667",
        "6408430000135",
        "5000128653572",
        "20321734")

    for (i in list) {
        viewModel.getOpenFood(i)
    }

}