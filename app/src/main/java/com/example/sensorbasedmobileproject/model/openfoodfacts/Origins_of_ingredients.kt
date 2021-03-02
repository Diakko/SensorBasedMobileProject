package com.example.sensorbasedmobileproject.model.openfoodfacts

import com.example.sensorbasedmobileproject.model.openfoodfacts.Aggregated_origins
import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Origins_of_ingredients (

	@SerializedName("aggregated_origins") val aggregated_origins : List<Aggregated_origins>,
	@SerializedName("transportation_value") val transportation_value : Int,
	@SerializedName("transportation_score") val transportation_score : Double,
	@SerializedName("epi_value") val epi_value : Int,
	@SerializedName("value") val value : Int,
	@SerializedName("origins_from_origins_field") val origins_from_origins_field : List<String>,
	@SerializedName("epi_score") val epi_score : Double
)