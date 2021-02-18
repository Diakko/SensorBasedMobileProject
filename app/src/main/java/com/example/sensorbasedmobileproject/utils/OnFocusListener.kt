package com.example.sensorbasedmobileproject.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class OnFocusLostListener: View.OnFocusChangeListener {
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}