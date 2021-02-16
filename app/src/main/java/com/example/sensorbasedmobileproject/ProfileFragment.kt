package com.example.sensorbasedmobileproject

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class ProfileFragment : Fragment(), SensorEventListener{

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private lateinit var stepsTextView: TextView
    private lateinit var stepsProgressBar: CircularProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        stepsTextView = view.findViewById(R.id.text_view_steps_taken)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        loadData()
        resetSteps()
        return view
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null) {
            Toast.makeText(requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show()
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
        TODO("Not yet implemented")
    }

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
        editor?.putFloat("steps", previousTotalSteps)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences?.getFloat("steps", 0f)
        Log.d("PROFILE_FRAGMENT","$savedNumber")
        previousTotalSteps = savedNumber!!

    }


}