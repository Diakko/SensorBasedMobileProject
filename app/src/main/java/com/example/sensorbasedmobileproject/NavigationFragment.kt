package com.example.sensorbasedmobileproject

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class NavigationFragment(private val applicationContext: Context) : Fragment() {

    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, R.string.yes_alert, Toast.LENGTH_SHORT).show()
    }
    private val negativeButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, R.string.no_alert, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_navigation, container, false)
        val cameraButton = view.findViewById<ImageButton>(R.id.camera_button)
        cameraButton.setOnClickListener(){

            val builder = AlertDialog.Builder(applicationContext)
            builder.setTitle(R.string.alert_title)
            builder.setMessage(R.string.alert_text)
            builder.setPositiveButton("Ok", DialogInterface.OnClickListener(function = positiveButtonClick))
            builder.setNegativeButton("No", negativeButtonClick)
            builder.show()
        }
        return view
    }
}