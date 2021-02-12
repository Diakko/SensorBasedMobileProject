package com.example.sensorbasedmobileproject

import java.net.URL

class Networking(mHand: android.os.Handler) : Runnable {

    private val myHandler = mHand

    override fun run() {
        try {
            val myUrl = URL("https://fineli.fi/fineli/api/v1/foods?q=omena")
            val myConn = myUrl.openConnection()
            val iStream = myConn.getInputStream()
            val allText = iStream.bufferedReader().use { it.readText() }
            val result = StringBuilder()
            result.append(allText)
            val str = result.toString()
            val msg = myHandler.obtainMessage()
            msg.what = 0
            msg.obj = str
            myHandler.sendMessage(msg)
        } catch (e: Exception) {
            // TODO Exeption handling
        }
    }
}