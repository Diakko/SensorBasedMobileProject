package com.example.sensorbasedmobileproject.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import com.example.sensorbasedmobileproject.data.OffItemViewModel
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private lateinit var viewModel: OffItemViewModel
    private lateinit var offItem: OffItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details, container, false)
        viewModel = ViewModelProvider(this).get(OffItemViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get ean from arguments & search DB for item in IO thread
        GlobalScope.launch(context = Dispatchers.IO) {
            val ean = arguments?.getString("ean")
            offItem = viewModel.getOffItem(ean?.toLong()!!)

            // Update fragment_details in Main thread
            launch(Dispatchers.Main) {
                "Energy: ${offItem.nutriments?.energyKcal100g.toString()} kcal".also { view.energyKcal100g.text = it }
                "Fat: ${offItem.nutriments?.fat100g.toString()} g".also { view.fat100g.text = it }
                "Saturated grams: ${offItem.nutriments?.saturatedFat100g.toString()} g".also { view.saturatedFat100g.text = it }
                "Carbohydrates: ${offItem.nutriments?.carbohydrates100g.toString()} g".also { view.carbohydrates100g.text = it }
                "Proteins: ${offItem.nutriments?.proteins100g.toString()} g".also { view.proteins100g.text = it }
                "Salt: ${offItem.nutriments?.salt100g.toString()} g".also { view.salt100g.text = it }
                "Link to product website: \n${offItem.link}".also { view.link.text = it }
            }
        }
    }
}