package com.example.sensorbasedmobileproject.fragments.scan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.sensorbasedmobileproject.MainActivity
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.fragments.main.MainFragment
import com.google.zxing.integration.android.IntentIntegrator


class CameraScanFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera_scan, container, false)
        val integrator = IntentIntegrator.forSupportFragment(this@CameraScanFragment)

        integrator.setOrientationLocked(false)
        integrator.setPrompt("Scan QR code")
        integrator.setBeepEnabled(false)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)


        integrator.initiateScan()
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Scanned : " + result.contents, Toast.LENGTH_LONG).show()
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.action_home, MainFragment())
                transaction?.disallowAddToBackStack()
                transaction?.commit()
            }
        }
    }



}