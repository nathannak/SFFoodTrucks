package com.demo.sffoodtrucks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sffoodtrucks.repository.Repository
import kotlinx.coroutines.launch
import model.FoodTruck
import model.FoodTruckItem

class SharedViewModel : ViewModel() {

    val repo: Repository = Repository()

    /*helps determine name of the current fragment
    in Toolbar through DataBinding */
    var currentFragment: String = "List"

    var foodTruckLiveData: MutableLiveData<FoodTruck> = MutableLiveData()
    var openFoodTrucksLiveData: MutableLiveData<List<FoodTruckItem>> = MutableLiveData()

    fun getOpenFoodTrucks() {
        viewModelScope.launch {
            foodTruckLiveData.value = repo.getAllFoodTrucks().value

            val openFoodTrucks = foodTruckLiveData.value?.filter { fti ->
                fti.starttime == "8AM"
            }

            openFoodTrucksLiveData.postValue(openFoodTrucks)
        }
    }

}
