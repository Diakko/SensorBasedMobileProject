package com.example.sensorbasedmobileproject.fragments.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sensorbasedmobileproject.ApiViewModel
import com.example.sensorbasedmobileproject.ApiViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.FineliItem
import com.example.sensorbasedmobileproject.data.FineliItemViewModel
import com.example.sensorbasedmobileproject.data.OffItem
import com.example.sensorbasedmobileproject.data.OffItemViewModel
import com.example.sensorbasedmobileproject.fragments.search.ListAdapter
import com.example.sensorbasedmobileproject.model.Fineli
import com.example.sensorbasedmobileproject.repository.ApiRepository
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.fragment_details.view.recyclerview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailsFragment : Fragment() {

    private lateinit var offViewModel: OffItemViewModel
    private lateinit var offItem: OffItem
    private lateinit var mFineliViewModel: FineliItemViewModel
    private lateinit var viewModel: ApiViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details, container, false)
        offViewModel = ViewModelProvider(this).get(OffItemViewModel::class.java)

        // Set toolbar backbutton listener
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireView().findNavController().navigate(R.id.action_global_action_home)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fineli viewmodel
        mFineliViewModel = ViewModelProvider(this).get(FineliItemViewModel::class.java)
        mFineliViewModel.readAllData.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // TODO: Navigate back with the back button
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = "Product details"


        // Set up viewModel stuffs
        val repository = ApiRepository()
        val viewModelFactory = ApiViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ApiViewModel::class.java)


        // Observe response
        viewModel.myFineliResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful && !(response.body()?.isEmpty())!!) {
                insertDataToDatabase(response)
                // TODO: insert GET data to adapter to show here
                Log.d("DBG", response.body().toString())

            } else {
                Log.d("DBG", response.errorBody().toString())
                Toast.makeText(requireContext(),
                    "Keyword not found in Fineli API",
                    Toast.LENGTH_LONG).show()
            }
        })


        // Get ean from arguments & search DB for item in IO thread
        GlobalScope.launch(context = Dispatchers.IO) {
            val ean = arguments?.getString("ean")
            offItem = offViewModel.getOffItem(ean?.toLong()!!)

            // Do the Fineli Call
            viewModel.getFineliFood(offItem.product_name.toString())

            val listOfFineliItems = offItem.product_name?.let {
                mFineliViewModel.getFineliByOffItemName(it)
            }

            if (listOfFineliItems != null) {
                for (i in listOfFineliItems) {
                    Log.d("DBG", i.toString())
                }
            }

            // Update fragment_details in Main thread
            launch(Dispatchers.Main) {
                "Energy: ${offItem.nutriments?.energyKcal100g.toString()} kcal".also {
                    view.energyKcal100g.text = it
                }
                "Fat: ${offItem.nutriments?.fat100g.toString()} g".also { view.fat100g.text = it }
                "- Saturated fat: ${offItem.nutriments?.saturatedFat100g.toString()} g".also {
                    view.saturatedFat100g.text = it
                }
                "Carbohydrates: ${offItem.nutriments?.carbohydrates100g.toString()} g".also {
                    view.carbohydrates100g.text = it
                }

                "- Sugars: ${offItem.nutriments?.sugars100g.toString()} g".also {
                    view.sugars100g.text = it
                }

                if (offItem.nutriments?.fiber_100g.toString()
                        .equals("") || offItem.nutriments?.fiber_100g == null
                ) {
                    view.fiber_100g.visibility = View.GONE
                } else {
                    "Fibers: ${offItem.nutriments?.fiber_100g.toString()} g".also {
                        view.fiber_100g.text = it
                    }
                }

                "Proteins: ${offItem.nutriments?.proteins100g.toString()} g".also {
                    view.proteins100g.text = it
                }
                "Salt: ${offItem.nutriments?.salt100g.toString()} g".also {
                    view.salt100g.text = it
                }
                "- Sodium: ${offItem.nutriments?.sodium100g.toString()} g".also {
                    view.sodium100g.text = it
                }


                if (offItem.link.equals("") || offItem.link == null) {
                    view.link.visibility = View.GONE
                } else {
                    "Link to product website: \n${offItem.link}".also { view.link.text = it }
                }
            }
        }
    }

    private fun insertDataToDatabase(response: Response<ArrayList<Fineli>>) {

        if (response.body()?.size!! > 1) {
            for (i in response.body()!!) {

            }

        }

        val fineli = FineliItem(
            0,
            response.body()?.get(0)?.id,
            response.body()?.get(0)?.energy,
            response.body()?.get(0)?.energyKcal,
            response.body()?.get(0)?.fat,
            response.body()?.get(0)?.protein,
            response.body()?.get(0)?.carbohydrate,
            response.body()?.get(0)?.alcohol,
            response.body()?.get(0)?.organicAcids,
            response.body()?.get(0)?.sugarAlcohol,
            response.body()?.get(0)?.saturatedFat,
            response.body()?.get(0)?.fiber,
            response.body()?.get(0)?.sugar,
            response.body()?.get(0)?.salt,
            response.body()?.get(0)?.ediblePortion,
            response.body()?.get(0)?.type,
            response.body()?.get(0)?.name,
            response.body()?.get(0)?.preparationMethod,
            response.body()?.get(0)?.specialDiets,
            response.body()?.get(0)?.themes,
            response.body()?.get(0)?.units,
            response.body()?.get(0)?.ingredientClass,
            response.body()?.get(0)?.functionClass
        )

        mFineliViewModel.addFineliData(fineli)
        Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()
    }


}