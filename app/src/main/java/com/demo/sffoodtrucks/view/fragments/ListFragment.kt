package com.demo.sffoodtrucks.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.FragmentListBinding
import com.demo.sffoodtrucks.model.FoodTruckItem
import com.demo.sffoodtrucks.util.CheckConnectivity
import com.demo.sffoodtrucks.util.NetworkResponseWrapper
import com.demo.sffoodtrucks.view.adapters.FoodTruckListAdapter
import com.demo.sffoodtrucks.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/* Written by Nathan N 12/27/2020

*/

class ListFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var fragmentListBinding: FragmentListBinding
    private val foodTruckListAdapter = FoodTruckListAdapter(arrayListOf())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        updateData(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )

        setupObservers()
        setupRecView()

        return fragmentListBinding.getRoot()
    }

    private fun setupObservers() {

        sharedViewModel.networkResponseWrapper.observe(
            viewLifecycleOwner,
            { netwrokResponseWrapper ->
                when (netwrokResponseWrapper.state) {

                    "loading" -> {

                        Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
                        fragmentListBinding.progressBar.visibility = View.VISIBLE
                    }
                    "success" -> {

                        fragmentListBinding.progressBar.visibility = View.INVISIBLE
                        sharedViewModel.openFoodTrucksLiveData.observe(
                            viewLifecycleOwner,
                            Observer { fti ->
                                foodTruckListAdapter.updateFoodTruckList(fti as ArrayList<FoodTruckItem>)
                            })
                    }
                    "error" -> {

                        Toast.makeText(
                            context,
                            "failed to fetch results from network",
                            Toast.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.INVISIBLE
                    }
                    "empty" -> {

                        Toast.makeText(context, "no results to show", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    private fun setupRecView() {

        fragmentListBinding.listFragmentRecview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foodTruckListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
    }

    private fun updateData(context: Context) {
        //no connectivity
        if (sharedViewModel.openFoodTrucksLiveData.value?.isEmpty()!! && !CheckConnectivity(context).isConnected()) {
            sharedViewModel.networkResponseWrapper = MutableLiveData(
                NetworkResponseWrapper(
                    null,
                    "error"
                )
            )
        }
        //retain state across configuration changes with the help of ViewModel
        else if (sharedViewModel.openFoodTrucksLiveData.value?.isEmpty()!!) {
            //ask ViewModel to get list of open food trucks
            sharedViewModel.updateOpenFoodTrucks()
        }
    }

}