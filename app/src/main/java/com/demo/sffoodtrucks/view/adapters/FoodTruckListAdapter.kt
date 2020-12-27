package com.demo.sffoodtrucks.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.ItemTruckBinding
import com.demo.sffoodtrucks.model.FoodTruckItem

/* Written by Nathan N 12/27/2020

*/

class FoodTruckListAdapter(private var acronymList: ArrayList<FoodTruckItem>) : RecyclerView.Adapter<FoodTruckListAdapter.FoodTruckViewHolder>() {

    fun updateFoodTruckList(newfoodTruckList: ArrayList<FoodTruckItem>) {
        acronymList.clear()
        acronymList.addAll(newfoodTruckList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodTruckViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemTruckBinding>(inflater, R.layout.item_truck, parent, false)
        return FoodTruckViewHolder(view)
    }

    override fun getItemCount() = acronymList.size

    override fun onBindViewHolder(holder: FoodTruckViewHolder, position: Int) {
        holder.view.fti = acronymList[position]
    }

    class FoodTruckViewHolder(var view: ItemTruckBinding) : RecyclerView.ViewHolder(view.root)

}