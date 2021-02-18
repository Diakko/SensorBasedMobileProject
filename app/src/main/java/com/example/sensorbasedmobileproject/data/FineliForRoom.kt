package com.example.sensorbasedmobileproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fineli")
data class FineliForRoom(
    // id in our room database
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    // id in the fineli databasé
    @SerializedName("id")
    val fineliId: Int,
//    val type: Type,
//    val name: Name,
//    val preparationMethod: PreparationMethod,
//    val ediblePortion: Int,
//    val specialDiets: List<String>,
//    val themes: List<String>,
//    val units: List<Units>,
//    val ingredientClass: IngredientClass,
//    val functionClass: FunctionClass,
    val energy: Double,
    val energyKcal: Double,
    val fat: Double,
    val protein: Double,
    val carbohydrate: Double,
    val alcohol: Int,
    val organicAcids: Double,
    val sugarAlcohol: Int,
    val saturatedFat: Double,
    val fiber: Double,
    val sugar: Double,
    val salt: Double
)
//
//data class Abbreviation(
//
//    val fi: String,
//    val sv: String,
//    val en: String
//)
//
//
//data class Description(
//
//    val fi: String,
//    val sv: String,
//    val en: String
//)
//
//
//data class FunctionClass(
//
//    val code: String,
//    val description: Description,
//    val abbreviation: Abbreviation
//)
//
//
//data class IngredientClass(
//
//    val code: String,
//    val description: Description,
//    val abbreviation: Abbreviation
//)
//
//
//data class Name(
//
//    val fi: String,
//    val sv: String,
//    val en: String,
//    val la: String
//)
//
//
//data class PreparationMethod(
//
//    val code: String,
//    val description: Description,
//    val abbreviation: Abbreviation
//)
//
//
//data class Units(
//
//    val code: String,
//    val description: Description,
//    val abbreviation: Abbreviation,
//    val mass: Float
//)
//
//data class Type(
//
//    val code: String,
//    val description: Description,
//    val abbreviation: Abbreviation
//)