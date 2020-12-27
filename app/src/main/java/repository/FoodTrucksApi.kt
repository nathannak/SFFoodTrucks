package com.demo.sffoodtrucks.repository

import com.demo.sffoodtrucks.model.FoodTruck
import retrofit2.Response
import retrofit2.http.GET

/* Written by Nathan N 12/27/2020

*/


interface FoodTrucksApi {
    @GET("jjew-r69b.json")
    suspend fun getFoodTrucks(): Response<FoodTruck>
}