package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.example.sensorbasedmobileproject.model.FunctionClass
import com.example.sensorbasedmobileproject.model.IngredientClass
import com.example.sensorbasedmobileproject.model.Name
import com.example.sensorbasedmobileproject.model.PreparationMethod
import com.example.sensorbasedmobileproject.model.Type
import com.example.sensorbasedmobileproject.model.Units
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fineli")
data class FineliItem(
    // id in our room database
    @PrimaryKey(autoGenerate = true) val id: Int,
    // id in the fineli databasé
    @SerializedName("id") val fineliId: Int?,
    val energy: Double?,
    val energyKcal: Double?,
    val fat: Double?,
    val protein: Double?,
    val carbohydrate: Double?,
    val alcohol: Double?,
    val organicAcids: Double?,
    val sugarAlcohol: Double?,
    val saturatedFat: Double?,
    val fiber: Double?,
    val sugar: Double?,
    val salt: Double?,
    val ediblePortion: Int?,
    @Embedded val type: Type?,
    @Embedded val name: Name?,
    @Embedded val preparationMethod: PreparationMethod?,
    val specialDiets: MutableList<String>?,
    val themes: MutableList<String>?,
    val units: MutableList<Units>?,
    @Embedded val ingredientClass: IngredientClass?,
    @Embedded val functionClass: FunctionClass?
)

