package com.example.sensorbasedmobileproject.fragments.map

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.sensorbasedmobileproject.MainViewModel
import com.example.sensorbasedmobileproject.MainViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.NominatimItem
import com.example.sensorbasedmobileproject.data.NominatimItemViewModel
import com.example.sensorbasedmobileproject.model.Nominatim
import com.example.sensorbasedmobileproject.repository.Repository
import com.google.android.gms.location.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import retrofit2.Response

class MapFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationNow: Location = Location("LocationInit")
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationText: TextView
    private lateinit var map: MapView
    // private val pathPoints: ArrayList<GeoPoint?> = ArrayList()
    private lateinit var viewHere: View
    private lateinit var viewModel: MainViewModel
    private lateinit var mNominatimItemViewModel: NominatimItemViewModel
    private  var mNominatimList = ArrayList<Nominatim>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewHere = inflater.inflate(R.layout.fragment_map, container, false)
        val ctx = context
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        locationText = viewHere.findViewById(R.id.location_now_text)
        map = viewHere.findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(14.0)
        getLocationUpdates()
        map.controller.setCenter(GeoPoint(60.0, 25.0))



        mNominatimItemViewModel = ViewModelProvider(this).get(NominatimItemViewModel::class.java)
        mNominatimItemViewModel.readAllData.observe(viewLifecycleOwner, Observer { nominatim -> mNominatimList })
        return viewHere
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        viewModel.myNominatimResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful && !(response.body()?.isEmpty())!!) {
                insertDataToDatabase(response)
            } else {
                Log.d("DBG", response.errorBody().toString())
                Toast.makeText(requireContext(), "No Alepas found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun insertDataToDatabase(response: Response<ArrayList<Nominatim>>) {
        val place_id = response.body()?.get(0)?.place_id
        val lat = response.body()?.get(0)?.lat
        val lon = response.body()?.get(0)?.lon
        val display_name = response.body()?.get(0)?.display_name

        val nominatim = NominatimItem(
            0,
            place_id!!,
            lat!!,
            lon!!,
            display_name!!
        )

        val marker = Marker(map)
        marker.position = GeoPoint(lat, lon)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(marker)
        marker.title = display_name

        mNominatimItemViewModel.addNominatimData(nominatim)
        Toast.makeText(requireContext(), "Successfully retrieved Alepa", Toast.LENGTH_SHORT).show()
    }

    private fun getLocationUpdates() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        locationRequest = LocationRequest()
        locationRequest.interval = 100
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 5F
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    val oldLocation = locationNow
                    // val distanceText = viewHere.findViewById<TextView>(R.id.distance_between_text)
                    locationNow = location
                    locationText.text = getString(R.string.address_location,

                        getAddress(
                            locationNow.latitude,
                            locationNow.longitude
                        ),
                        locationNow.latitude.toString(),
                        locationNow.longitude.toString()
                    )


                    map.controller.setCenter(GeoPoint(locationNow.latitude, locationNow.longitude))
                    Log.d(
                        "LOCATION_UPDATED",
                        "Location now: latitude: ${locationNow.latitude}, longitude: ${locationNow.longitude}"
                    )

                    /*val distanceBetween = oldLocation.distanceTo(locationNow).toInt()
                    distanceText.text = getString(R.string.distance_locations,
                        distanceBetween.toString()
                    )*/
                    if (oldLocation != locationNow) {
                        val marker = Marker(map)
                        marker.position = GeoPoint(locationNow)
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        map.overlays.add(marker)
                        marker.title = getString(R.string.map_point_address, getAddress(locationNow.latitude, locationNow.longitude))

                        /*val polyline = Polyline()
                        map.overlays.add(polyline)
                        pathPoints.add(GeoPoint(locationNow))
                        polyline.setPoints(pathPoints)*/
                    }


                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                0
            )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                0
            )
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun getAddress(lat: Double?, lng: Double?): String {
        val geoCoder = Geocoder(context)
        val list = geoCoder.getFromLocation(lat ?: 0.0, lng ?: 0.0, 1)
        return list[0].getAddressLine(0)
    }




}