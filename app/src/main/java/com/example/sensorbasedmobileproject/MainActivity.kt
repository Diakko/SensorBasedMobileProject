package com.example.sensorbasedmobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainScreenFragment: Fragment
    private lateinit var navigationFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigationFragment = NavigationFragment(this)
        mainScreenFragment = MainFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.bottom_nav_fragment, navigationFragment)
            replace(R.id.main_screen_fragment, mainScreenFragment)
            commit()
        }
    }
}