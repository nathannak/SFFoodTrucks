package com.demo.sffoodtrucks.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.databinding.FragmentMapsBinding
import com.demo.sffoodtrucks.viewmodel.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.item_truck.view.*

class MapsFragment : Fragment() {

    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var mapsFragmentBinding : FragmentMapsBinding

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
        setupPopUpDismissListener()
        return mapsFragmentBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun populateMarkers(googleMap: GoogleMap) {

        sharedViewModel.openFoodTrucksLiveData.observe(viewLifecycleOwner, {

            if(it.size == 0 ) {
                Toast.makeText(context,"no results to show",Toast.LENGTH_LONG).show()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.77,-122.41), 12f))
            }else {
                val builder = LatLngBounds.Builder();
                it.forEach {

                    val latLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    val markerOptions = MarkerOptions().position(latLng).title(it.applicant).snippet(it.location)
                    googleMap.addMarker(markerOptions)
                    builder.include(latLng)
                }.also {

                    val bounds = builder.build();
                    val padding = 200
                    val cameraFactory = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                    googleMap.animateCamera(cameraFactory);
                    setupMarkerListener(googleMap)
                }
            }
        })
    }

    private fun setupMarkerListener(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener { marker ->
            mapsFragmentBinding.mapsSingleLayout.visibility = View.VISIBLE

            //re-use layout from recyclerview
            val inflatedLayout: View =
                layoutInflater.inflate(R.layout.item_truck, null, false)
            inflatedLayout.fti_name.text = marker?.snippet
            mapsFragmentBinding.truckInformationSingle.addView(inflatedLayout)

            false
        }
    }

    private fun setupPopUpDismissListener() {

        //when tint image layer is clicked, hide the pop-u and image
        mapsFragmentBinding.tintImageView.setOnClickListener {
            mapsFragmentBinding.mapsSingleLayout.visibility = View.INVISIBLE
        }
    }

}