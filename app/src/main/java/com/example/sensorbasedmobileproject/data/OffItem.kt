package com.example.sensorbasedmobileproject.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sensorbasedmobileproject.model.openfoodfacts.Nutriments

@Entity(tableName = "openfoodfacts")
data class OffItem(
    // id in our room database
    @PrimaryKey(autoGenerate = true) val id: Int,
    val code: Long?,
    val product_name: String?,
    val ingredients_text_debug: String?,
    val image_url: String?,
    val ingredients_text: String?,
    val allergens_from_ingredients: String?,
    val manufacturing_places: String?,
    val link: String?,
    @Embedded val nutriments: Nutriments?
    )
