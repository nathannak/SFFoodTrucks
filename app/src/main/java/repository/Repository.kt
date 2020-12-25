package com.demo.sffoodtrucks.repository

import android.util.Log
import android.util.Log.VERBOSE
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.demo.sffoodtrucks.model.FoodTruck
import com.demo.sffoodtrucks.util.NetworkResponseWrapper

class Repository {

    suspend fun getAllFoodTrucks(): NetworkResponseWrapper {
        val response = FoodTrucksService.getRetrofit().getFoodTrucks()

        if(response.isSuccessful){

            Log.v("MYTAG","making a network call")

            val repoFoodTruckLiveData : MutableLiveData<FoodTruck> = MutableLiveData()
            repoFoodTruckLiveData.value = response.body()
            return NetworkResponseWrapper(repoFoodTruckLiveData,"success")
        }
        else return NetworkResponseWrapper(null,"error")
    }

}