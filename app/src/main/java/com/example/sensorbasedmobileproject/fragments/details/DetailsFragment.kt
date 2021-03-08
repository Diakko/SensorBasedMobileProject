package com.example.sensorbasedmobileproject.fragments.details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            Handler(Looper.getMainLooper()).post {

                view.energyKcal100g.text = offItem.nutriments?.energyKcal100g.toString()
                view.fat100g.text = offItem.nutriments?.fat100g.toString()
                view.saturatedFat100g.text = offItem.nutriments?.saturatedFat100g.toString()
                view.carbohydrates100g.text = offItem.nutriments?.carbohydrates100g.toString()
                view.proteins100g.text = offItem.nutriments?.proteins100g.toString()
                view.salt100g.text = offItem.nutriments?.salt100g.toString()
            }

        }


    }
}