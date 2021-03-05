package com.example.sensorbasedmobileproject.model.openfoodfacts

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

data class Nutriscore_data (

	@SerializedName("sugars") val sugars : Double,
	@SerializedName("fruits_vegetables_nuts_colza_walnut_olive_oils_value") val fruits_vegetables_nuts_colza_walnut_olive_oils_value : Double,
	@SerializedName("saturated_fat") val saturated_fat : Double,
	@SerializedName("saturated_fat_ratio_value") val saturated_fat_ratio_value : Double,
	@SerializedName("energy") val energy : Int,
	@SerializedName("positive_points") val positive_points : Int,
	@SerializedName("sodium_value") val sodium_value : Double,
	@SerializedName("saturated_fat_ratio_points") val saturated_fat_ratio_points : Int,
	@SerializedName("energy_points") val energy_points : Int,
	@SerializedName("fiber_points") val fiber_points : Int,
	@SerializedName("proteins_value") val proteins_value : Double,
	@SerializedName("proteins_points") val proteins_points : Int,
	@SerializedName("saturated_fat_ratio") val saturated_fat_ratio : Double,
	@SerializedName("is_cheese") val is_cheese : Int,
	@SerializedName("is_fat") val is_fat : Int,
	@SerializedName("saturated_fat_value") val saturated_fat_value : Double,
	@SerializedName("sodium") val sodium : Int,
	@SerializedName("fiber_value") val fiber_value : Double,
	@SerializedName("sugars_value") val sugars_value : Double,
	@SerializedName("fiber") val fiber : Double,
	@SerializedName("negative_points") val negative_points : Int,
	@SerializedName("proteins") val proteins : Double,
	@SerializedName("fruits_vegetables_nuts_colza_walnut_olive_oils_points") val fruits_vegetables_nuts_colza_walnut_olive_oils_points : Double,
	@SerializedName("grade") val grade : String,
	@SerializedName("is_water") val is_water : Int,
	@SerializedName("energy_value") val energy_value : Int,
	@SerializedName("saturated_fat_points") val saturated_fat_points : Int,
	@SerializedName("sugars_points") val sugars_points : Int,
	@SerializedName("is_beverage") val is_beverage : Int,
	@SerializedName("score") val score : Int,
	@SerializedName("fruits_vegetables_nuts_colza_walnut_olive_oils") val fruits_vegetables_nuts_colza_walnut_olive_oils : Double,
	@SerializedName("sodium_points") val sodium_points : Int
)