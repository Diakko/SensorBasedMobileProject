package com.example.sensorbasedmobileproject.fragments.profile

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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sensorbasedmobileproject.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class ProfileFragment : Fragment(), SensorEventListener{

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private var targetSteps : Int? = 9000
    private lateinit var stepsTextView: TextView
    private lateinit var stepsTargetTextView: TextView
    private lateinit var stepsTargetEditText: EditText
    private lateinit var stepsProgressBar: CircularProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        stepsTextView = view.findViewById(R.id.text_view_steps_taken)
        stepsTargetTextView = view.findViewById(R.id.text_view_steps_target)
        stepsTargetEditText = view.findViewById(R.id.editTextTargetSteps)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepsProgressBar = view.findViewById(R.id.circular_progress_bar)
        stepsProgressBar.progressMax = 9000F // default 9000 steps target
        stepsTargetTextView.text = "/ 9000"

        loadData()
        resetSteps()
        setTargetSteps()
        return view
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
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

    private fun setTargetSteps() {
        stepsTargetTextView.setOnClickListener {
            Toast.makeText(context, "Long tap to set target, input amount below", Toast.LENGTH_LONG).show()
        }

        stepsTargetTextView.setOnLongClickListener {

            if (stepsTargetEditText.text.toString().trim().isEmpty()) {
                Toast.makeText(context, "Input a valid number", Toast.LENGTH_SHORT).show()
            }else {
                targetSteps = stepsTargetEditText.text.toString().toInt()
                stepsTargetTextView.text = "/ $targetSteps"
                stepsProgressBar.progressMax = targetSteps!!.toFloat()
                saveData()
            }
            true
        }
    }

    private fun saveData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt("StepsTarget", targetSteps!!)
        editor?.putFloat("Steps", previousTotalSteps)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedStepsNumber = sharedPreferences?.getFloat("Steps", 0f)
        val savedTargetStepsNumber = sharedPreferences?.getInt("StepsTarget", 9000)
        Log.d("PROFILE_FRAGMENT","$savedStepsNumber / $savedTargetStepsNumber")
        previousTotalSteps = savedStepsNumber!!
        targetSteps = savedTargetStepsNumber!!
        stepsProgressBar.progressMax = savedTargetStepsNumber.toFloat()

    }


}