package com.demo.sffoodtrucks.repository

import androidx.lifecycle.MutableLiveData
import com.demo.sffoodtrucks.model.FoodTruck

class Repository {

    private var repoFoodTruckLiveData : MutableLiveData<FoodTruck> = MutableLiveData()

    suspend fun getAllFoodTrucks():  MutableLiveData<FoodTruck>  {
            val response = FoodTrucksService.getRetrofit().getFoodTrucks()
            repoFoodTruckLiveData.value = response.body()
            return repoFoodTruckLiveData
    }



}