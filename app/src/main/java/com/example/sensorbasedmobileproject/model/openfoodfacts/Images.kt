package com.example.sensorbasedmobileproject.model.openfoodfacts

import Nutrition
import Nutrition_en
import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Images (

	@SerializedName("front") val front : Front,
	@SerializedName("3") val three : Three,
	@SerializedName("nutrition_en") val nutrition_en : Nutrition_en,
	@SerializedName("ingredients_en") val ingredients_en : Ingredients_en,
	@SerializedName("1") val one : One,
	@SerializedName("ingredients") val ingredients : Ingredients,
	@SerializedName("nutrition") val nutrition : Nutrition,
	@SerializedName("front_en") val front_en : Front_en,
	@SerializedName("2") val two : Two,
	@SerializedName("4") val four : Four,
	@SerializedName("5") val five : Five
)