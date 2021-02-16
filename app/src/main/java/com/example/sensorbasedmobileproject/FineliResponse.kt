package com.example.sensorbasedmobileproject

import com.google.gson.annotations.SerializedName

object FineliResponse {

    data class Result(

        @SerializedName("id") val id: Int,
        @SerializedName("type") val type: Type,
        @SerializedName("name") val name: Name,
        @SerializedName("preparationMethod") val preparationMethod: PreparationMethod,
        @SerializedName("ediblePortion") val ediblePortion: Int,
        @SerializedName("specialDiets") val specialDiets: List<String>,
        @SerializedName("themes") val themes: List<String>,
        @SerializedName("units") val units: List<Units>,
        @SerializedName("ingredientClass") val ingredientClass: IngredientClass,
        @SerializedName("functionClass") val functionClass: FunctionClass,
        @SerializedName("energy") val energy: Double,
        @SerializedName("energyKcal") val energyKcal: Double,
        @SerializedName("fat") val fat: Double,
        @SerializedName("protein") val protein: Double,
        @SerializedName("carbohydrate") val carbohydrate: Double,
        @SerializedName("alcohol") val alcohol: Int,
        @SerializedName("organicAcids") val organicAcids: Int,
        @SerializedName("sugarAlcohol") val sugarAlcohol: Int,
        @SerializedName("saturatedFat") val saturatedFat: Double,
        @SerializedName("fiber") val fiber: Double,
        @SerializedName("sugar") val sugar: Double,
        @SerializedName("salt") val salt: Double
    )

    data class Abbreviation(

        @SerializedName("fi") val fi: String,
        @SerializedName("sv") val sv: String,
        @SerializedName("en") val en: String
    )

    data class Description(

        @SerializedName("fi") val fi: String,
        @SerializedName("sv") val sv: String,
        @SerializedName("en") val en: String
    )

    data class FunctionClass(

        @SerializedName("code") val code: String,
        @SerializedName("description") val description: Description,
        @SerializedName("abbreviation") val abbreviation: Abbreviation
    )

    data class IngredientClass(

        @SerializedName("code") val code: String,
        @SerializedName("description") val description: Description,
        @SerializedName("abbreviation") val abbreviation: Abbreviation
    )


    data class Name(

        @SerializedName("fi") val fi: String,
        @SerializedName("sv") val sv: String,
        @SerializedName("en") val en: String,
        @SerializedName("la") val la: String
    )

    data class PreparationMethod(

        @SerializedName("code") val code: String,
        @SerializedName("description") val description: Description,
        @SerializedName("abbreviation") val abbreviation: Abbreviation
    )


    data class Units(

        @SerializedName("code") val code: String,
        @SerializedName("description") val description: Description,
        @SerializedName("abbreviation") val abbreviation: Abbreviation,
        @SerializedName("mass") val mass: Int
    )

    data class Type(

        @SerializedName("code") val code: String,
        @SerializedName("description") val description: Description,
        @SerializedName("abbreviation") val abbreviation: Abbreviation
    )
}