package com.hudhudit.catchapp.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels

import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController

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
import com.hudhud.eva.core.onClick
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.driverlocation.DriverModel
import com.hudhudit.catchapp.apputils.modules.registration.catcherregistration.CatcherUser
import com.hudhudit.catchapp.core.base.BaseFragment
import com.hudhudit.catchapp.databinding.FragmentMapsBinding
import com.hudhudit.catchapp.ui.splash.SplashActivity

import com.hudhudit.catchapp.utils.Resource

import com.hudhudit.catchapp.utils.AppConstants

import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@AndroidEntryPoint
class MapsFragment : BaseFragment() {
    lateinit var binding: FragmentMapsBinding

    private val viewModel by viewModels<MapViewModel>()
    var currentLocation: Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val boundBuilder = LatLngBounds.Builder()
    var mapFragment: SupportMapFragment? = null
    private var lat:Double=31.969313097394057
    private var lng:Double=35.86470320252633

    var googleMapMarkers: MutableList<GoogleMapMarkerModel> = mutableListOf()
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

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext());


        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

       fetchLocation()

        var locations= mutableListOf<LatLng>()
        locations.add(LatLng(31.969313097394057, 35.86470320252633))
        locations.add(LatLng(23.54879797, 23.54879797))
        locations.add(LatLng(31.968368301663176, 35.86537712663036))


      locations.forEach {
         var x= CalculationByDistance(LatLng(lat,lng), LatLng(it.latitude,it.longitude))
          //println(x.toString())
      }
        binding.btnStart.onClick {

            getAllDriver()
        }
        onClick()
    }



    fun calculateDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Double {
        val results = FloatArray(3)
        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
        return results[0].toDouble()
    }
    fun CalculationByDistance(StartP: LatLng, EndP: LatLng): Double {
        val Radius = 6371 // radius of earth in Km
        val lat1 = StartP.latitude
        val lat2 = EndP.latitude
        val lon1 = StartP.longitude
        val lon2 = EndP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + (Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2)))
        val c = 2 * Math.asin(Math.sqrt(a))
        val valueResult = Radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
        Log.i(
            "Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec
        )
        return Radius * c
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
//        binding.logout.setOnClickListener { logoutPopUp() }
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

                 removeAllMarkers()
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
                                getAllDriver()

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

    private fun getAllDriver(){
        viewModel.getDriver()

        viewModel.getDriverStatus.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if (it!!.status == Resource.Status.SUCCESS) {
                    it.data!!.toMutableList().forEach{
                        addProviderToMarker(it)

                    }
                    removeAllMarkers()
                    Log.d("datakk", it.data!!.toMutableList().toString())


                }
                if (it!!.status == Resource.Status.ERROR) {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.reset()
        })
    }

    fun addProviderToMarker(
        driverModel: DriverModel?
    ) {
        val callback = OnMapReadyCallback { googleMap ->
            val sydney = LatLng(driverModel!!.lat!!.toDouble(), driverModel.lang!!.toDouble())
            boundBuilder.include(sydney)
            Glide.with(this)
                .asBitmap()
                .load(R.drawable.taxi)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {

                        var markerOptions =
                            MarkerOptions()
                                .position(sydney)
                                .title("user_name")
                        .icon(BitmapDescriptorFactory.fromBitmap(resource))

                        var markerModel = googleMap.addMarker(markerOptions).let {
                            GoogleMapMarkerModel(
                                "1",
                                it!!
                            )
                        }


                        if (markerModel != null) {
                            googleMapMarkers.add(markerModel)
                        }


                        // googleMapMarkers.removeAt(0)

                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                }
                )


        }
        mapFragment!!.getMapAsync(callback)
    }

    data class GoogleMapMarkerModel(
        val user_id: String,
        var marker: Marker

    )

    private fun removeAllMarkers() {
        for (mLocationMarker in googleMapMarkers) {
            mLocationMarker.marker.remove()
        }
        googleMapMarkers.clear()
    }

    private fun logoutPopUp(){
        val alertView: View =
            LayoutInflater.from(context).inflate(R.layout.dilalog_logout, null)
        val alertBuilder = AlertDialog.Builder(context).setView(alertView).show()
        alertBuilder.show()
        alertBuilder.setCancelable(false)

        alertBuilder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancel: CardView = alertView.findViewById(R.id.cancel)
        val logout: CardView = alertView.findViewById(R.id.logout)

        cancel.setOnClickListener { alertBuilder.dismiss() }
        logout.setOnClickListener {
            val preferences: SharedPreferences = requireActivity().getSharedPreferences(
                AppConstants.SHARED_PREF_KEY,
                Context.MODE_PRIVATE
            )
            AppConstants.userType = ""
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            val splashIntent = Intent(requireActivity(), SplashActivity::class.java)
            startActivity(splashIntent)
            requireActivity().finish()
            alertBuilder.dismiss()}
    }


}