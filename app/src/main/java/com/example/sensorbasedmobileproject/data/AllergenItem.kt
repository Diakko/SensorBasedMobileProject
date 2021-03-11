package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "allergenlist")
data class AllergenItem(
    @PrimaryKey val id: Int,
    val wheat: Boolean,
    val rye: Boolean,
    val barley: Boolean,
    val spelt: Boolean,
    val kamutGrain: Boolean,
    val oats: Boolean,
    val otherCerealProducts: Boolean,
    val fish: Boolean,
    val crustacean: Boolean,
    val mollusc: Boolean,
    val egg: Boolean,
    val nuts: Boolean,
    val soy: Boolean,
    val milk: Boolean,
    val celery: Boolean,
    val mustard: Boolean,
    val lupine: Boolean,
    val sulfur: Boolean
    )
