package com.demo.sffoodtrucks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sffoodtrucks.model.FoodTruckItem
import com.demo.sffoodtrucks.repository.Repository
import com.demo.sffoodtrucks.view.util.NetworkResponseWrapper
import kotlinx.coroutines.launch
import java.util.function.ToDoubleBiFunction

class SharedViewModel : ViewModel() {


    /*
    Sets and gets network response in wrapped state so we can observe
    and react upon it the list fragment
     */
    var wrapper : MutableLiveData<NetworkResponseWrapper> = MutableLiveData()

    /*
    helps project current fragment's name
    in Toolbar through DataBinding
    */
    var currentFragment: String = "Map"

    val repo: Repository = Repository()
    var openFoodTrucksLiveData: MutableLiveData<List<FoodTruckItem>> = MutableLiveData()

    fun updateOpenFoodTrucks() {

        //set network wrapper state to loading first
        wrapper.value =
            NetworkResponseWrapper(null, "loading")

        viewModelScope.launch {

            val responseWrapper = repo.getAllFoodTrucks()
            wrapper.value = responseWrapper

            val openFoodTrucks = responseWrapper.allFoodTrucksLiveData?.value?.filter { fti ->

                //isFoodTruckOpen(fti)
                fti.starttime == "8AM"

            }

            openFoodTrucksLiveData.postValue(openFoodTrucks)
        }
    }

    private fun isFoodTruckOpen(fti: FoodTruckItem): Boolean {
        TODO()
    }

}
