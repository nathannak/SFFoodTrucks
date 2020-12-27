package com.demo.sffoodtrucks.repository

import com.demo.sffoodtrucks.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* Written by Nathan N 12/27/2020

*/

object FoodTrucksService {



    fun getRetrofit(): FoodTrucksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodTrucksApi::class.java)
    }
}