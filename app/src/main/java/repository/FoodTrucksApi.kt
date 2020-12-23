package com.demo.sffoodtrucks.repository

import com.demo.sffoodtrucks.model.FoodTruck
import retrofit2.Response
import retrofit2.http.GET

interface FoodTrucksApi {
    @GET("jjew-r69b.json")
    suspend fun getFoodTrucks(): Response<FoodTruck>
}