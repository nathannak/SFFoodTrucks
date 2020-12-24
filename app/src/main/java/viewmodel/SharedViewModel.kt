package com.demo.sffoodtrucks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sffoodtrucks.model.FoodTruckItem
import com.demo.sffoodtrucks.repository.Repository
import com.demo.sffoodtrucks.util.DateTimeHelper
import com.demo.sffoodtrucks.util.NetworkResponseWrapper
import kotlinx.coroutines.launch
import java.util.*

class SharedViewModel : ViewModel() {

    private val repo: Repository = Repository()

    /*
    helps project current fragment's name
    in Toolbar through DataBinding
    */
    var currentFragment: String = "Map"

    /*
    Sets and gets network response in wrapped state so we can observe
    and react upon it the list fragment
    */
    var networkResponseWrapper: MutableLiveData<NetworkResponseWrapper> = MutableLiveData()
    var openFoodTrucksLiveData: MutableLiveData<List<FoodTruckItem>> = MutableLiveData()

    fun updateOpenFoodTrucks() {

        //set network wrapper state to loading first
        networkResponseWrapper.value =
            NetworkResponseWrapper(null, "loading")

        viewModelScope.launch {

            //set network state from loading to success or error
            val responseWrapper = repo.getAllFoodTrucks()
            networkResponseWrapper.value = responseWrapper

            if (responseWrapper.state == "success") {
                openFoodTrucksLiveData.postValue(filterOpenFoodTrucks(responseWrapper))
            }

        }
    }

    private fun filterOpenFoodTrucks(responseWrapper: NetworkResponseWrapper): List<FoodTruckItem>? {
        return responseWrapper.allFoodTrucksLiveData?.value?.filter { fti ->
            //isFoodTruckOpen(fti)
            true
        }
    }

    private fun isFoodTruckOpen(foodTruckItem: FoodTruckItem): Boolean {
        val dateTime = DateTimeHelper()
        return (
           dateTime.getCurDay().toLowerCase(Locale.getDefault()).equals(foodTruckItem.dayofweekstr.toLowerCase(
           Locale.getDefault())
           ) &&
           dateTime.getCurHour() <  foodTruckItem.end24.substring(0,2).toInt()
           &&
           dateTime.getCurHour() >=  foodTruckItem.start24.substring(0,2).toInt()
        )
    }

}
