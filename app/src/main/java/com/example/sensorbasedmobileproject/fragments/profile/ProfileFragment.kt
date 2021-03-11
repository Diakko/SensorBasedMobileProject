package com.example.sensorbasedmobileproject.fragments.profile

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.AllergenItem
import com.example.sensorbasedmobileproject.data.AllergenItemViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment() : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private lateinit var stepsTextView: TextView
    private lateinit var stepsProgressBar: CircularProgressBar
    private lateinit var fragmentView: View
    private lateinit var allergenItemViewModel: AllergenItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Stepcounter Initialization
        stepsTextView = fragmentView.findViewById(R.id.text_view_steps_taken)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepsProgressBar = fragmentView.findViewById(R.id.circular_progress_bar)
        stepsProgressBar.progressMax = 10000F // default 9000 steps target


        // Initialize viewmodel
        allergenItemViewModel = ViewModelProvider(this).get(AllergenItemViewModel::class.java)

        loadData()
        resetSteps()

        // Checkbox state init?


        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var exists: Boolean

        var _1 = false
        var _2 = false
        var _3 = false
        var _4 = false
        var _5 = false
        var _6 = false
        var _7 = false
        var _8 = false
        var _9 = false
        var _10 = false
        var _11 = false
        var _12 = false
        var _13 = false
        var _14 = false
        var _15 = false
        var _16 = false
        var _17 = false
        var _18 = false

        val wheat = view.findViewById<CheckBox>(R.id.wheat)
        val rye = view.findViewById<CheckBox>(R.id.rye)
        val barley = view.findViewById<CheckBox>(R.id.barley)
        val spelt = view.findViewById<CheckBox>(R.id.spelt)
        val kamutGrain = view.findViewById<CheckBox>(R.id.kamutGrain)
        val oats = view.findViewById<CheckBox>(R.id.oats)
        val otherCerealProducts = view.findViewById<CheckBox>(R.id.otherCerealProducts)
        val fish = view.findViewById<CheckBox>(R.id.fish)
        val crustacean = view.findViewById<CheckBox>(R.id.crustacean)
        val mollusc = view.findViewById<CheckBox>(R.id.mollusc)
        val egg = view.findViewById<CheckBox>(R.id.egg)
        val nuts = view.findViewById<CheckBox>(R.id.nuts)
        val soy = view.findViewById<CheckBox>(R.id.soy)
        val milk = view.findViewById<CheckBox>(R.id.milk)
        val celery = view.findViewById<CheckBox>(R.id.celery)
        val mustard = view.findViewById<CheckBox>(R.id.mustard)
        val lupine = view.findViewById<CheckBox>(R.id.lupine)
        val sulfur = view.findViewById<CheckBox>(R.id.sulfur)

        // Get allergies and check boxes accordingly
        GlobalScope.launch(context = Dispatchers.IO) {
            // Check if exists
            exists = allergenItemViewModel.checkIfExists()

            if (exists) {

                val allergenItem = allergenItemViewModel.getAllergenItem()

                GlobalScope.launch(context = Dispatchers.Main) {
                    if (allergenItem.wheat) {
                        wheat.isChecked
                    }
                    Log.d("DGB what", wheat.toString())
                    if (allergenItem.rye) {
                        rye.isChecked
                    }
                    if (allergenItem.barley) {
                        barley.isChecked
                    }
                    if (allergenItem.spelt) {
                        spelt.isChecked
                    }
                    if (allergenItem.kamutGrain) {
                        kamutGrain.isChecked
                    }
                    if (allergenItem.oats) {
                        oats.isChecked
                    }
                    if (allergenItem.otherCerealProducts) {
                        otherCerealProducts.isChecked
                    }
                    if (allergenItem.fish) {
                        fish.isChecked
                    }
                    if (allergenItem.crustacean) {
                        crustacean.isChecked
                    }
                    if (allergenItem.mollusc) {
                        mollusc.isChecked
                    }
                    if (allergenItem.egg) {
                        egg.isChecked
                    }
                    if (allergenItem.nuts) {
                        nuts.isChecked
                    }
                    if (allergenItem.soy) {
                        soy.isChecked
                    }
                    if (allergenItem.milk) {
                        milk.isChecked
                    }
                    if (allergenItem.celery) {
                        celery.isChecked
                    }
                    if (allergenItem.mustard) {
                        mustard.isChecked
                    }
                    if (allergenItem.lupine) {
                        lupine.isChecked
                    }
                    if (allergenItem.sulfur) {
                        sulfur.isChecked
                    }
                }
            }
        }

        val button = view.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            if (wheat.isChecked) {
                _1 = true
            }
            if (rye.isChecked) {
                _2 = true
            }
            if (barley.isChecked) {
                _3 = true
            }
            if (spelt.isChecked) {
                _4 = true
            }
            if (kamutGrain.isChecked) {
                _5 = true
            }
            if (oats.isChecked) {
                _6 = true
            }
            if (otherCerealProducts.isChecked) {
                _7 = true
            }
            if (fish.isChecked) {
                _8 = true
            }
            if (crustacean.isChecked) {
                _9 = true
            }
            if (mollusc.isChecked) {
                _10 = true
            }
            if (egg.isChecked) {
                _11 = true
            }
            if (nuts.isChecked) {
                _12 = true
            }
            if (soy.isChecked) {
                _13 = true
            }
            if (milk.isChecked) {
                _14 = true
            }
            if (celery.isChecked) {
                _15 = true
            }
            if (mustard.isChecked) {
                _16 = true
            }
            if (lupine.isChecked) {
                _17 = true
            }
            if (sulfur.isChecked) {
                _18 = true
            }

            // create allergenItem
            val allergenItem = AllergenItem(0,
                _1,
                _2,
                _3,
                _4,
                _5,
                _6,
                _7,
                _8,
                _9,
                _10,
                _11,
                _12,
                _13,
                _14,
                _15,
                _16,
                _17,
                _18)

            GlobalScope.launch(context = Dispatchers.IO) {
                // Check if exists
                exists = allergenItemViewModel.checkIfExists()
                if (!exists) {
                    // If not, add
                    Log.d("DBG add", allergenItem.toString())
                    allergenItemViewModel.addAllergenData(allergenItem)
                } else {
                    // If exists, delete and add new
                    Log.d("DBG update", allergenItem.toString())
                    allergenItemViewModel.clearDB()
                    allergenItemViewModel.addAllergenData(allergenItem)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(requireContext(),
                "No sensor detected on this device",
                Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            stepsTextView.text = ("$currentSteps")
            stepsProgressBar.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // Activity step calculator functions
    private fun resetSteps() {
        stepsTextView.setOnClickListener {
            Toast.makeText(context, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }
        stepsTextView.setOnLongClickListener {
            previousTotalSteps = totalSteps
            stepsTextView.text = 0.toString()
            saveData()
            true
        }
    }

    private fun saveData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putFloat("Steps", previousTotalSteps)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedStepsNumber = sharedPreferences?.getFloat("Steps", 0f)
        previousTotalSteps = savedStepsNumber!!

    }


}

