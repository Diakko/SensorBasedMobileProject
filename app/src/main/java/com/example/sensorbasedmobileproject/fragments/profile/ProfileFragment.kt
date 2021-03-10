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
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.ShoppingListItem
import com.example.sensorbasedmobileproject.data.ShoppingListItemDatabase
import com.example.sensorbasedmobileproject.data.ShoppingListItemViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment() : Fragment(), SensorEventListener, AdapterView.OnItemSelectedListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private lateinit var stepsTextView: TextView
    private lateinit var stepsProgressBar: CircularProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var fragmentView: View
    private lateinit var spinner: Spinner
    private lateinit var shoppingListItem: String
    private var shoppingListItemAmount: Int = 0
    private lateinit var shoppingListItemType: String
    private lateinit var shoppingListViewModel: ShoppingListItemViewModel
    private val shoppingListDatabase by lazy {context?.let {ShoppingListItemDatabase.getDatabase(it)}}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Stepcounter Initialization
        stepsTextView = fragmentView.findViewById(R.id.text_view_steps_taken)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepsProgressBar = fragmentView.findViewById(R.id.circular_progress_bar)
        stepsProgressBar.progressMax = 10000F // default 9000 steps target

        // Shopping List
        val shoppingListItemEditText = fragmentView.findViewById<EditText>(R.id.edit_text_shopping_list_item)
        val shoppingListItemAmountEditText = fragmentView.findViewById<EditText>(R.id.edit_text_shopping_list_item_amount)

        // Recyclerview
        val recyclerAdapter =  ShoppingListAdapter()
        recyclerView = fragmentView.findViewById(R.id.recyclerview)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Add Button
        val addButton = fragmentView.findViewById<ImageButton>(R.id.add_button)

        addButton.setOnClickListener() {
            if (shoppingListItemAmountEditText.text.isNotEmpty() && shoppingListItemEditText.text.isNotEmpty()) {
                if (shoppingListDatabase != null) {
                        GlobalScope.launch {
                                shoppingListItem = shoppingListItemEditText.text.toString()
                                shoppingListItemAmount =
                                    shoppingListItemAmountEditText.text.toString().toInt()
                                shoppingListItemAmountEditText.text.clear()
                                shoppingListItemEditText.text.clear()
                                shoppingListDatabase!!.shoppingListItemDao()
                                    .insertShoppingListData(ShoppingListItem(0,
                                        shoppingListItem,
                                        shoppingListItemAmount,
                                        shoppingListItemType))

                        }
                    }
            } else {
                Toast.makeText(requireContext(), R.string.add_item_toast, Toast.LENGTH_LONG).show()
            }
        }

        // Keyboard hidings
        shoppingListItemAmountEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })
        shoppingListItemEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })

        // Clear button
        val clearButton = fragmentView.findViewById<Button>(R.id.clear_button)

        clearButton.setOnClickListener() {
            if (shoppingListDatabase != null) {
                GlobalScope.launch {
                    shoppingListDatabase!!.shoppingListItemDao().clearShoppingList()
                }
            }
        }

        shoppingListViewModel = ViewModelProvider(this).get(ShoppingListItemViewModel::class.java)
        shoppingListViewModel.readAllData.observe(viewLifecycleOwner, Observer { shoppingListItem ->
            recyclerAdapter.setData(shoppingListItem)
        })


        // Spinner item
        spinner = fragmentView.findViewById(R.id.type_spinner)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(requireContext(), R.array.type_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.solidColor
            spinner.adapter = adapter
        }


        loadData()
        resetSteps()
        return fragmentView
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

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // Spinner adapter overrides
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        shoppingListItemType = parent.getItemAtPosition(pos).toString()
        Log.d("SHOPPING", shoppingListItemType)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Log.d("SHOPPING", "nothing selected")
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