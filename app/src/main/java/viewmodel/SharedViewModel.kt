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

/* Written by Nathan N 12/27/2020

*/

class SharedViewModel(private val repo: Repository = Repository()) : ViewModel() {

    /*
    helps project current fragment's name
    in Toolbar through DataBinding.
    We have to expose it to MainActivity
    */
    var currentFragment: String = "Map"

    /*
    Sets and gets network response in wrapped state so we can observe
    and react upon it the list fragment.
    We have to expose networkResponseWrapper to ListFragment
    */
    var networkResponseWrapper: MutableLiveData<NetworkResponseWrapper> = MutableLiveData()

    //We have to expose openFoodTrucksLiveData to ListFragment
    var openFoodTrucksLiveData: MutableLiveData<List<FoodTruckItem>>    = MutableLiveData(arrayListOf())

    //gets list of open food trucks and sort them, also detect if all trucks are closed
    fun updateOpenFoodTrucks() {

        //set network wrapper state to loading first
        networkResponseWrapper.value =
            NetworkResponseWrapper(null, "loading")

        viewModelScope.launch {

            //set network state from loading to success or error
            val responseWrapper = repo.getAllFoodTrucks()
            networkResponseWrapper.value = responseWrapper

            if (responseWrapper.state == "success") {

                val list = filterOpenFoodTrucks(responseWrapper)?.sortedBy {it.applicant}

                openFoodTrucksLiveData.postValue(list)

                //empty response case, when all trucks are closed.
                if(list?.size!! ==  0)
                    networkResponseWrapper.value = NetworkResponseWrapper(null,"empty")
            }
        }
    }

    private fun filterOpenFoodTrucks(responseWrapper: NetworkResponseWrapper): List<FoodTruckItem>? {
        return responseWrapper.allFoodTrucksLiveData?.value?.filter{ fti ->
            isFoodTruckOpen(fti)
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
