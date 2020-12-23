package com.demo.sffoodtrucks.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.FragmentListBinding
import com.demo.sffoodtrucks.model.FoodTruckItem
import com.demo.sffoodtrucks.view.FoodTruckListAdapter
import com.demo.sffoodtrucks.viewmodel.SharedViewModel

class ListFragment : Fragment() {

    lateinit var sharedViewModel : SharedViewModel
    private lateinit var fragmentListBinding : FragmentListBinding
    private val foodTruckListAdapter = FoodTruckListAdapter(arrayListOf())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.updateOpenFoodTrucks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )
        val view: View = fragmentListBinding.getRoot()

        setupObservers()
        setupRecView()

        return view
    }

    private fun setupObservers() {

        sharedViewModel.wrapper.observe(viewLifecycleOwner, Observer { netwrokResponseWrapper ->
            when (netwrokResponseWrapper.state) {

                "loading" -> Toast.makeText(context, "loading", Toast.LENGTH_LONG).show()
                "success" -> {
                    Toast.makeText(context, "success", Toast.LENGTH_LONG).show()
                    sharedViewModel.openFoodTrucksLiveData.observe(viewLifecycleOwner, Observer { fti ->
                        foodTruckListAdapter.updateFoodTruckList(fti as ArrayList<FoodTruckItem>)
                    })
                }
                "error" -> {

                }
            }
        })
    }

    private fun setupRecView() {
        fragmentListBinding.listFragmentRecview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foodTruckListAdapter
        }
    }

}