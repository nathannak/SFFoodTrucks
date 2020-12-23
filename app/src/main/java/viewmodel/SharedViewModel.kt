package com.demo.sffoodtrucks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

//    var currentFragment : MutableLiveData<String> = MutableLiveData<String>().apply{
//        postValue("List")
//    }

    var currentFragment : String = "List"

}
