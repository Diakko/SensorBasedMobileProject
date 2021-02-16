package com.example.sensorbasedmobileproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

class CameraScanFragment() : Fragment() {

    lateinit var btnBarcode: Button
    lateinit var resultTextView: TextView
    lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera_scan, container, false)
        btnBarcode = view.findViewById(R.id.scan_barcode_button)
        resultTextView = view.findViewById(R.id.result_textview)

        btnBarcode.setOnClickListener {
            val intentIntegrator = IntentIntegrator(activity)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()
        }

        return view
    }


   override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            handler = Handler()
            handler.postDelayed({
                if (result.contents == null) {
                    Toast.makeText(requireContext(), "cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("BarcodeScanner", "Scanned")
                    Toast.makeText(requireContext(), "Scanned -> " + result.contents, Toast.LENGTH_SHORT)
                        .show()
                    resultTextView.text = String.format("Scanned Result: %s", result)
                }
            }, 10)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}