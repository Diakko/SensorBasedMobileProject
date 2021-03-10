/**
 * Description: Fragment for displaying detailed information of the products nutriment data
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private lateinit var offViewModel: OffItemViewModel
    private lateinit var offItem: OffItem

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar title
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.title = getString(R.string.product_details)

        // Get ean from arguments & search DB for item in IO thread
        GlobalScope.launch(context = Dispatchers.IO) {
            val ean = arguments?.getString("ean")
            offItem = offViewModel.getOffItem(ean?.toLong()!!)

            // Set detail texts in the Main thread
            launch(Dispatchers.Main) {
                view.energyKcal100g.text = getString(R.string.energy, offItem.nutriments?.energyKcal100g.toString())
                view.fat100g.text = getString(R.string.fat, offItem.nutriments?.fat100g.toString())
                view.saturatedFat100g.text = getString(R.string.sat_fat, offItem.nutriments?.saturatedFat100g.toString())
                view.carbohydrates100g.text = getString(R.string.carbs, offItem.nutriments?.carbohydrates100g.toString())
                view.sugars100g.text = getString(R.string.sugars, offItem.nutriments?.sugars100g.toString())

                // show data only if exists
                if (offItem.nutriments?.fiber_100g.toString().equals("") || offItem.nutriments?.fiber_100g == null) {
                    view.fiber_100g.visibility = GONE
                } else {
                    view.fiber_100g.text = getString(R.string.fibers, offItem.nutriments?.fiber_100g.toString())
                }

                view.proteins100g.text = getString(R.string.proteins, offItem.nutriments?.proteins100g.toString())
                view.salt100g.text = getString(R.string.salt, offItem.nutriments?.salt100g.toString())
                view.sodium100g.text = getString(R.string.sodium, offItem.nutriments?.sodium100g.toString())

                // show data only if exists
                if (offItem.link.equals("") || offItem.link == null) {
                    view.link.visibility = GONE
                } else {
                    view.link.text = getString(R.string.link, offItem.link)
                }
            }
        }
    }
}