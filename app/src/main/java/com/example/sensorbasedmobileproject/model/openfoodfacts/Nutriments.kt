package com.example.sensorbasedmobileproject.model.openfoodfacts

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

data class Nutriments (

	@SerializedName("nova-group") val novaGroup : Int,
	@SerializedName("carbohydrates_serving") val carbohydratesServing : Double,
	@SerializedName("fiber_serving") val fiberServing : Double,
	@SerializedName("cholesterol") val cholesterol : Int,
	@SerializedName("cholesterol_value") val cholesterolSalue : Int,
	@SerializedName("trans-fat") val transFat : Int,
	@SerializedName("nova-group_serving") val novaGroupServing : Int,
	@SerializedName("saturated-fat_serving") val saturatedFatServing : Double,
	@SerializedName("energy_serving") val energyServing : Int,
	@SerializedName("carbohydrates_unit") val carbohydratesUnit : String,
	@SerializedName("saturated-fat_100g") val saturatedFat100g : Double,
	@SerializedName("carbohydrates") val carbohydrates : Double,
	@SerializedName("sugars_value") val sugarsValue : Double,
	@SerializedName("sodium") val sodium : Double,
	@SerializedName("fiber_value") val fiberValue : Double,
	@SerializedName("iron") val iron : Double,
	@SerializedName("energy_unit") val energyUnit : String,
	@SerializedName("calcium_unit") val calciumUnit : String,
	@SerializedName("fiber_unit") val fiberUnit : String,
	@SerializedName("fat_value") val fatValue : Double,
	@SerializedName("iron_unit") val ironUnit : String,
	@SerializedName("vitamin-a_serving") val vitaminAServing : Double,
	@SerializedName("nova-group_100g") val novaGroup100g : Int,
	@SerializedName("proteins_100g") val proteins100g : Double,
	@SerializedName("sodium_value") val sodiumValue : Int,
	@SerializedName("fat_serving") val fatServing : Int,
	@SerializedName("vitamin-c_100g") val vitaminC100g : Int,
	@SerializedName("vitamin-a") val vitaminA : Double,
	@SerializedName("nutrition-score-fr") val nutritionScoreFr : Int,
	@SerializedName("fat_100g") val fat100g : Double,
	@SerializedName("calcium_100g") val calcium100g : Double,
	@SerializedName("energy-kcal_serving") val energyKcalServing : Int,
	@SerializedName("carbohydrates_value") val carbohydratesValue : Double,
	@SerializedName("iron_serving") val ironServing : Double,
	@SerializedName("vitamin-a_unit") val vitaminAUnit : String,
	@SerializedName("energy") val energyNutriments : Int,
	@SerializedName("iron_value") val ironValue : Double,
	@SerializedName("trans-fat_serving") val transFatServing : Int,
	@SerializedName("vitamin-a_100g") val vitaminA100g : Double,
	@SerializedName("sugars_serving") val sugarsServing : Int,
	@SerializedName("energy_value") val energyValue : Int,
	@SerializedName("salt_serving") val saltServing : Double,
	@SerializedName("energy-kcal_100g") val energyKcal100g : Int,
	@SerializedName("sugars_100g") val sugars100g : Double,
	@SerializedName("saturated-fat") val saturatedFat : Double,
	@SerializedName("energy-kcal_unit") val energyKcalUnit : String,
	@SerializedName("cholesterol_unit") val cholesterolUnit : String,
	@SerializedName("vitamin-a_value") val vitaminAValue : Int,
	@SerializedName("fat") val fat : Double,
	@SerializedName("sodium_serving") val sodiumServing : Double,
	@SerializedName("fat_unit") val fatUnit : String,
	@SerializedName("vitamin-c_serving") val vitaminCServing : Int,
	@SerializedName("trans-fat_unit") val transFatUnit : String,
	@SerializedName("sodium_100g") val sodium100g : Double,
	@SerializedName("proteins_serving") val proteinsServing : Int,
	@SerializedName("cholesterol_100g") val cholesterol100g : Int,
	@SerializedName("proteins") val proteins : Double,
	@SerializedName("nutrition-score-fr_100g") val nutritionScoreFr100g : Int,
	@SerializedName("fiber") val fiber : Double,
	@SerializedName("trans-fat_value") val transFatValue : Int,
	@SerializedName("fruits-vegetables-nuts-estimate-from-ingredients_100g") val fruitsVegetablesNutsEstimateFromIngredients100g : Int,
	@SerializedName("fiber_100g") val fiber_100g : Double,
	@SerializedName("energy-kcal_value") val energyKcalValue : Int,
	@SerializedName("energy_100g") val energy100g : Int,
	@SerializedName("proteins_value") val proteinsValue : Double,
	@SerializedName("calcium_serving") val calciumServing : Double,
	@SerializedName("vitamin-c_value") val vitaminCValue : Int,
	@SerializedName("vitamin-c_unit") val vitaminCUnit : String,
	@SerializedName("saturated-fat_unit") val saturatedFatUnit : String,
	@SerializedName("iron_100g") val iron100g : Double,
	@SerializedName("proteins_unit") val proteinsUnit : String,
	@SerializedName("sugars_unit") val sugarsUnit : String,
	@SerializedName("cholesterol_serving") val cholesterolServing : Int,
	@SerializedName("trans-fat_100g") val transFat100g : Int,
	@SerializedName("sodium_unit") val sodiumUnit : String,
	@SerializedName("energy-kcal") val energyKcal : Int,
	@SerializedName("saturated-fat_value") val saturatedFatValue : Double,
	@SerializedName("calcium_value") val calciumValue : Int,
	@SerializedName("salt_unit") val saltUnit : String,
	@SerializedName("calcium") val calcium : Double,
	@SerializedName("sugars") val sugars : Double,
	@SerializedName("vitamin-c") val vitaminC : Int,
	@SerializedName("salt") val salt : Double,
	@SerializedName("carbohydrates_100g") val carbohydrates100g : Double,
	@SerializedName("salt_100g") val salt100g : Double,
	@SerializedName("salt_value") val saltValue : Int
)