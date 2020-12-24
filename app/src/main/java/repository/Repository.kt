package com.demo.sffoodtrucks.repository

import androidx.lifecycle.MutableLiveData
import com.demo.sffoodtrucks.model.FoodTruck
import com.demo.sffoodtrucks.util.NetworkResponseWrapper

class Repository {

    suspend fun getAllFoodTrucks(): NetworkResponseWrapper {
            val response = FoodTrucksService.getRetrofit().getFoodTrucks()

            if(response.isSuccessful){
                val repoFoodTruckLiveData : MutableLiveData<FoodTruck> = MutableLiveData()
                repoFoodTruckLiveData.value = response.body()
                return NetworkResponseWrapper(repoFoodTruckLiveData,"success")
            }
            else return NetworkResponseWrapper(null,"error")
    }

}