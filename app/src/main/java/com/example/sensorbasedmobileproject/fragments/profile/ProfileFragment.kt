/**
 * Description:
 *
 * Fragment for users own "profile"
 *
 * - Users daily steps displayed as number as well as observable ring. Max amount set to 9000 atm.
 * - Choose allergens with checkboxes and use the shared preferences to save them and steps.
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 *
 */

package com.example.sensorbasedmobileproject.fragments.profile

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.utils.Constants
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.util.*

class ProfileFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private lateinit var stepsTextView: TextView
    private lateinit var stepsProgressBar: CircularProgressBar
    private lateinit var fragmentView: View
    private var checked = mutableListOf<CheckBox>()

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

        loadData()
        resetSteps()

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get shared preferences reference
        val sharedPrefCheckboxes =
            this.activity?.getSharedPreferences(Constants.ALLERGY_PREFERENCES_CHECKBOXES, MODE_PRIVATE)
        val sharedPref =
            this.activity?.getSharedPreferences(Constants.ALLERGY_PREFERENCES, MODE_PRIVATE)

        // Find items
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

        // Make a list of checkboxes
        val checkboxes = listOf<CheckBox>(
            wheat,
            rye,
            barley,
            spelt,
            kamutGrain,
            oats,
            otherCerealProducts,
            fish,
            sulfur,
            mollusc,
            egg,
            nuts,
            soy,
            milk,
            celery,
            mustard,
            lupine,
            crustacean
        )
        val checkboxesNames = listOf(
            "wheat",
            "rye",
            "barley",
            "spelt",
            "kamutGrain",
            "oats",
            "otherCerealProducts",
            "fish",
            "sulfur",
            "mollusc",
            "egg",
            "nuts",
            "soy",
            "milk",
            "celery",
            "mustard",
            "lupine",
            "crustacean"
        )

        var i = 0
        // Get shared preferences and if true, make checkbox status "Checked"
        checkboxes.forEach {
            val name = it.text.toString()
            val value = sharedPrefCheckboxes?.getBoolean(name, false)
            if (value!!) {
                it.isChecked = true
                it.jumpDrawablesToCurrentState()
            } else {
                it.isChecked = false
                it.jumpDrawablesToCurrentState()
            }
        }

        // Get button reference and set click listener
        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            var i = 0
            // For each checkbox, check if checked and save state in shared preferences
            checkboxes.forEach {

                if (it.isChecked) {
                    val name = it.text.toString()
                    checked.add(it)
                    with(sharedPrefCheckboxes?.edit()) {
                        this?.putBoolean(name, true)
                        this?.apply()
                    }
                    with(sharedPref?.edit()) {
                        this?.putBoolean(checkboxesNames[i], true)
                        this?.apply()

                        Log.d("DBGITprofile", checkboxesNames[i])
                    }
                } else {
                    val name = it.text.toString()
                    checked.remove(it)
                    with(sharedPrefCheckboxes?.edit()) {
                        this?.putBoolean(name, false)
                        this?.apply()
                    }
                    with(sharedPref?.edit()) {
                        this?.remove(checkboxesNames[i])
                        this?.apply()
                    }
                }
                i++
            }

            // Notify user that the allergens are set
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    requireContext(),
                    "Allergens set",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        setBoxesChecked(checked)
    }

    private fun getResId(resName: String): Int {
        return try {
            resources.getIdentifier(resName, "string", context?.packageName)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
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
        val sharedPreferences = activity?.getSharedPreferences(Constants.STEPS_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putFloat(Constants.STEPS_PREFERENCES, previousTotalSteps)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences(Constants.STEPS_PREFERENCES, MODE_PRIVATE)
        val savedStepsNumber = sharedPreferences?.getFloat(Constants.STEPS_PREFERENCES, 0f)
        previousTotalSteps = savedStepsNumber!!
    }

    private fun setBoxesChecked(list: MutableList<CheckBox>) {
        list.forEach {
            it.isChecked = true
            it.jumpDrawablesToCurrentState()
        }
    }
}

