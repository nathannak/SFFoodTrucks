package com.demo.sffoodtrucks.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.FragmentMapsBinding
import com.demo.sffoodtrucks.model.FoodTruckItem
import com.demo.sffoodtrucks.util.Constants
import com.demo.sffoodtrucks.viewmodel.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.item_truck.view.*

/* Written by Nathan N 12/27/2020

*/

class MapsFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var mapsFragmentBinding: FragmentMapsBinding
    private var foodTruckMap = HashMap<String, ArrayList<String>>()
    private lateinit var inflatedLayout: View

    private val callback = OnMapReadyCallback { googleMap ->
        populateMarkers(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_maps, container, false
        )
        setupPopupDismissListener()
        return mapsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //get ViewModel as soon as Fragment is attached
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun populateMarkers(googleMap: GoogleMap) {

        sharedViewModel.openFoodTrucksLiveData.observe(viewLifecycleOwner, { it ->

            if (it.isEmpty()) {
                Toast.makeText(context, "no results to show", Toast.LENGTH_LONG).show()

                //no results, just animate to city of SF
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            Constants.SF_LAT,
                            Constants.SF_LON
                        ), 12f
                    )
                )
            } else {
                val builder = LatLngBounds.Builder();
                it.forEach {

                    val latLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    val markerOptions = MarkerOptions().position(latLng).title(it.applicant)

                    //store data for markers to be used inside clickListener
                    populateMap(it)

                    googleMap.addMarker(markerOptions)
                    builder.include(latLng)
                }.also {

                    //make sure markers are all inside view
                    val bounds = builder.build();
                    val padding = Constants.MAP_PADDING
                    val cameraFactory = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                    googleMap.animateCamera(cameraFactory);
                    setupMarkerListener(googleMap)
                }
            }
        })
    }

    private fun populateMap(it: FoodTruckItem) {
        if (!foodTruckMap.containsKey(it.applicant)) foodTruckMap.put(it.applicant, arrayListOf())
        foodTruckMap[it.applicant]?.add(it.location)
        foodTruckMap[it.applicant]?.add(it.optionaltext)
        foodTruckMap[it.applicant]?.add(it.start24 + "-" + it.endtime)
    }

    private fun setupMarkerListener(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener { marker ->

            mapsFragmentBinding.mapsGroupLayout.visibility = View.VISIBLE

            //re-use layout item_truck.xml which is also used in recyclerview
            inflatedLayout =
                layoutInflater.inflate(R.layout.item_truck, null, false)

            inflatedLayout.apply {
                fti_name.text = marker?.title
                fti_address.text = foodTruckMap[marker?.title]?.get(0)
                fti_menu.text = foodTruckMap[marker?.title]?.get(1)
                fti_hours.text = foodTruckMap[marker?.title]?.get(2)
            }

            //inflate view programmatically
            mapsFragmentBinding.truckInformationSingle.addView(inflatedLayout)

            false
        }
    }

    private fun setupPopupDismissListener() {

        //when tint image layer is clicked, hide the pop-up and tint image
        mapsFragmentBinding.tintImageView.setOnClickListener {
            mapsFragmentBinding.mapsGroupLayout.visibility = View.INVISIBLE

            //remove old view, otherwise it uses old view with new data
            mapsFragmentBinding.truckInformationSingle.removeView(inflatedLayout)
        }
    }

}