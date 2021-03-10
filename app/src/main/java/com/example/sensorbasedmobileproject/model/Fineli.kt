/**
 * Description: Data class for the Fineli model
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Fineli(

    val id: Int,
    @Embedded val type: Type,
    @Embedded val name: Name,
    @Embedded val preparationMethod: PreparationMethod,
    val ediblePortion: Int,
    val specialDiets: MutableList<String>?,
    val themes: MutableList<String>?,
    val units: MutableList<Units>?,
    @Embedded val ingredientClass: IngredientClass,
    @Embedded val functionClass: FunctionClass,
    val energy: Double,
    val energyKcal: Double,
    val fat: Double,
    val protein: Double,
    val carbohydrate: Double,
    val alcohol: Double,
    val organicAcids: Double,
    val sugarAlcohol: Double,
    val saturatedFat: Double,
    val fiber: Double,
    val sugar: Double,
    val salt: Double
)

data class Abbreviation(

    @SerializedName("fi") val fi_a: String,
    @SerializedName("sv") val sv_a: String,
    @SerializedName("en") val en_a: String
)


data class Description(
    @SerializedName("fi") val fi_d: String,
    @SerializedName("sv") val sv_d: String,
    @SerializedName("en") val en_d: String
)


data class FunctionClass(

    @SerializedName("code") val code_fc: String,
    @Embedded (prefix = "fc")val description: Description,
    @Embedded (prefix = "fc")val abbreviation: Abbreviation
)


data class IngredientClass(

    @SerializedName("code") val code_ic: String,
    @Embedded (prefix = "ic")val description: Description,
    @Embedded (prefix = "ic")val abbreviation: Abbreviation
)


data class Name(

    val fi: String,
    val sv: String,
    val en: String,
    val la: String
)


data class PreparationMethod(

    @SerializedName("code") val code_pm: String,
    @Embedded (prefix = "pm")val description: Description,
    @Embedded (prefix = "pm")val abbreviation: Abbreviation
)


data class Units(

    @SerializedName("code") val code_u: String,
    @Embedded (prefix = "u")val description: Description,
    @Embedded (prefix = "u")val abbreviation: Abbreviation,
    val mass: Float
)

data class Type(

    @SerializedName("code") val code_t: String,
    @Embedded (prefix = "t")val description: Description,
    @Embedded (prefix = "t")val abbreviation: Abbreviation
)