package com.example.sensorbasedmobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navigationFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navigationFragment = NavigationFragment(this)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.bottom_nav_fragment, navigationFragment)
            replace(R.id.nav_host_fragment, navHostFragment)
            commit()
        }
    }
}