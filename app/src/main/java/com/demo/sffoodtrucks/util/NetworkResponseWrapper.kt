package com.demo.sffoodtrucks.util

import androidx.lifecycle.MutableLiveData
import com.demo.sffoodtrucks.model.FoodTruck

/* Written by Nathan N 12/27/2020
Wrapper class for Retrofit response
*/

data class NetworkResponseWrapper(var allFoodTrucksLiveData : MutableLiveData<FoodTruck>?, var state : String)