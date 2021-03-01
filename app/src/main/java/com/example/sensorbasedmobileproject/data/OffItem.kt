package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "openfoodfacts")
data class OffItem(
    // id in our room database
    @PrimaryKey(autoGenerate = true) val id: Int,
    val code: Long
    )
