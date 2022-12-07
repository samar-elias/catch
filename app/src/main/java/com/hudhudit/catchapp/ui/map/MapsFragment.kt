package com.hudhudit.catchapp.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.REQUEST_CODE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentMapsBinding
import com.hudhudit.catchapp.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment() {
    lateinit var binding: FragmentMapsBinding

    private val viewModel by viewModels<MapViewModel>()



    var currentLocation: Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val boundBuilder = LatLngBounds.Builder()
    var mapFragment: SupportMapFragment? = null
    private val requestCall = 42
    private var lat=23.54879797
    private var lng=23.54879797
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(requireContext());
//
//
//        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//
//        fetchLocation()

        onClick()
    }

    private fun onClick(){
        binding.notifications.setOnClickListener {
            if(AppConstants.userType == "0"){
                findNavController().navigate(R.id.action_mapsFragment_to_catcheeNotificationsFragment)
            }else if (AppConstants.userType == "1"){
                findNavController().navigate(R.id.action_mapsFragment_to_catcherNotificationsFragment)
            }
        }
        binding.history.setOnClickListener {
            if (AppConstants.userType == "0"){
                findNavController().navigate(R.id.action_mapsFragment_to_catcheeHistoryFragment)
            }else if (AppConstants.userType == "1"){
                findNavController().navigate(R.id.action_mapsFragment_to_catcherHistoryFragment)
            }
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        val task: Task<Location> = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                lat=currentLocation!!.latitude
                lng=currentLocation!!.longitude
                Log.d("mylocation", "lat"+currentLocation!!.latitude.toString() + "lng"+currentLocation!!.longitude.toString())

                // removeAllMarkers()
                val callback = OnMapReadyCallback { googleMap ->
                    val latLng = LatLng( currentLocation!!.latitude,
                        currentLocation!!.longitude)
                    boundBuilder.include(latLng)
                    Glide.with(this)
                        .asBitmap()
                        .load(R.drawable.ic_curnt_location)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                            ) {
                                val markerOptions = MarkerOptions().position(latLng).title( "i am here")
                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))
                                googleMap.addMarker(markerOptions)

                                googleMap.animateCamera(
                                    CameraUpdateFactory.newLatLngBounds(
                                        boundBuilder.build(),
                                        1000,
                                        1000,
                                        100
                                    )
                                )


                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                            }

                        }
                        )
                }

                mapFragment!!.getMapAsync(callback)


            }
        }


    }

    data class GoogleMapMarkerModel(
        val user_id: String,
        var marker: Marker

    )




}