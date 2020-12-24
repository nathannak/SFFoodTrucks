package com.demo.sffoodtrucks.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.sffoodtrucks.R
import com.demo.sffoodtrucks.viewmodel.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment() {

    private lateinit var sharedViewModel : SharedViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        setUpMarkerObserver(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
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

    private fun setUpMarkerObserver(googleMap: GoogleMap) {

        sharedViewModel.openFoodTrucksLiveData.observe(viewLifecycleOwner, Observer {

            val builder = LatLngBounds.Builder();

            it.forEach {

                val latLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                val markerOptions = MarkerOptions().position(latLng).title(it.applicant)
                googleMap.addMarker(markerOptions)
                builder.include(latLng)

                googleMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener  {
                    override fun onMarkerClick(p0: Marker?): Boolean {
                        Toast.makeText(context,p0?.title,Toast.LENGTH_LONG).show()
                        return false
                    }
                })

                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            }.also {
                val bounds = builder.build();
                val padding: Int = 100
                val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                googleMap.animateCamera(cu);
            }

        })
    }

}