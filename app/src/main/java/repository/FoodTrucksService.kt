package com.demo.sffoodtrucks.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoodTrucksService {

    fun getRetrofit(): FoodTrucksApi {
        return Retrofit.Builder()
            .baseUrl("https://data.sfgov.org/resource/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodTrucksApi::class.java)
    }
}