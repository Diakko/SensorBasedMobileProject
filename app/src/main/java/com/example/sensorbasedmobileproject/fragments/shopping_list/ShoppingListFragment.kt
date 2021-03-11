/**
 * Description:
 *
 * Fragment for shopping list
 *
 * - Input shopping list item and amount with edit text and choose type with spinner.
 * - Displays shopping list items in recycler view
 * - Shopping list items can be stroke through to indicate it's already picked up and cleared after usage.
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 *
 */

package com.example.sensorbasedmobileproject.fragments.shopping_list

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.ShoppingListItem
import com.example.sensorbasedmobileproject.data.ShoppingListItemDatabase
import com.example.sensorbasedmobileproject.data.ShoppingListItemViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingListFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fragmentView: View
    private lateinit var spinner: Spinner
    private lateinit var shoppingListItem: String
    private var shoppingListItemAmount: Int = 0
    private lateinit var shoppingListItemType: String
    private lateinit var shoppingListViewModel: ShoppingListItemViewModel
    private val shoppingListDatabase by lazy {context?.let { ShoppingListItemDatabase.getDatabase(it)}}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_shopping_list, container, false)

        // Shopping List
        val shoppingListItemEditText = fragmentView.findViewById<EditText>(R.id.edit_text_shopping_list_item)
        val shoppingListItemAmountEditText = fragmentView.findViewById<EditText>(R.id.edit_text_shopping_list_item_amount)

        // Recyclerview
        val recyclerAdapter =  ShoppingListAdapter(requireContext())
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
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        return fragmentView
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
}