package com.demo.sffoodtrucks.util

import androidx.lifecycle.MutableLiveData
import com.demo.sffoodtrucks.model.FoodTruck

data class NetworkResponseWrapper(var allFoodTrucksLiveData : MutableLiveData<FoodTruck>?, var state : String)