package com.demo.sffoodtrucks.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FoodTrucksService {

    private const val BASE_URL = "https://data.sfgov.org/resource/"

    fun getRetrofit(): FoodTrucksApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodTrucksApi::class.java)
    }
}