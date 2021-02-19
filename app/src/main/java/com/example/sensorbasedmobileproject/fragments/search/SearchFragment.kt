package com.example.sensorbasedmobileproject.fragments.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sensorbasedmobileproject.MainViewModel
import com.example.sensorbasedmobileproject.MainViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.*
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.repository.Repository
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var viewModel: MainViewModel
    private lateinit var mFineliViewModel: FineliItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fineli viewmodel
        mFineliViewModel = ViewModelProvider(this).get(FineliItemViewModel::class.java)
        mFineliViewModel.readAllData.observe(viewLifecycleOwner, Observer { fineli ->
            adapter.setData(fineli)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up viewModel stuffs
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // Set up editText
        editText = view.findViewById(R.id.searchable)
        var editTextValue = editText.text

        // Observe response
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful && !(response.body()?.isEmpty())!!) {
                insertDataToDatabase(response)
            } else {
                Log.d("DBG", response.errorBody().toString())
                Toast.makeText(requireContext(), "Keyword not found in Fineli API", Toast.LENGTH_LONG).show()
            }
        })

        // Listen to editText and on complete do search via viewModels getFood(),
        // clear editText and hide keyboard
        editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getFood(editTextValue.toString())
                editText.text.clear()
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
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
        val ediblePortion = response.body()?.get(0)?.ediblePortion
        val sugar = response.body()?.get(0)?.sugar
        val salt = response.body()?.get(0)?.salt
        val type = response.body()?.get(0)?.type
        val name = response.body()?.get(0)?.name
        val preparationMethod = response.body()?.get(0)?.preparationMethod
        val functionClass = response.body()?.get(0)?.functionClass
        val ingredientClass = response.body()?.get(0)?.ingredientClass

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
            salt,
            ediblePortion,
            type,
            name,
            preparationMethod,
            ingredientClass,
            functionClass
        )

        mFineliViewModel.addFineliData(fineli)
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

