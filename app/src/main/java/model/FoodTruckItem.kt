package com.demo.sffoodtrucks.model

/* Written by Nathan N 12/27/2020

*/

data class FoodTruckItem(
    val addr_date_create: String,
    val addr_date_modified: String,
    val applicant: String,
    val block: String,
    val cnn: String,
    val coldtruck: String,
    val dayofweekstr: String,
    val dayorder: String,
    val end24: String,
    val endtime: String,
    val latitude: String,
    val location: String,
    val location_2: Location2,
    val locationdesc: String,
    val locationid: String,
    val longitude: String,
    val lot: String,
    val optionaltext: String,
    val permit: String,
    val start24: String,
    val starttime: String,
    val x: String,
    val y: String
)