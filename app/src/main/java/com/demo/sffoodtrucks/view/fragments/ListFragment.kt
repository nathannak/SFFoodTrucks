package com.demo.sffoodtrucks.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.viewmodel.SharedViewModel

class ListFragment : Fragment() {

    lateinit var sharedViewModel : SharedViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.getOpenFoodTrucks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        setupObservers()
        return view
    }

    private fun setupObservers() {
        sharedViewModel.openFoodTrucksLiveData.observe(viewLifecycleOwner, Observer { fti ->
            if(fti.size>0){
                Toast.makeText(context,"dcd",Toast.LENGTH_LONG).show()
            }
        })
    }

}