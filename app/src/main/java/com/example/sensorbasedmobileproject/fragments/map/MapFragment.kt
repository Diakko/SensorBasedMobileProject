/**
 * Description:
 *
 * Fragment for map which shows nearby shops
 *
 * - Automatically get location from GPS, sends api call for nominatim api with a view box centered on user.
 * - Gets nearby shops (about 15 km max location difference) and displays them on the map.
 * - Clicking on a shop icon, displays shop information
 * - TODO: Navigation to location with osmbonuspack and link in marker to google search with shop name.
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 *
 */

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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.sensorbasedmobileproject.ApiViewModel
import com.example.sensorbasedmobileproject.ApiViewModelFactory
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.NominatimItem
import com.example.sensorbasedmobileproject.data.NominatimItemViewModel
import com.example.sensorbasedmobileproject.model.Nominatim
import com.example.sensorbasedmobileproject.repository.ApiRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import retrofit2.Response
import kotlin.math.absoluteValue

class MapFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationNow: Location = Location("LocationInit")
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationText: TextView
    private lateinit var map: MapView
    private lateinit var viewHere: View
    private lateinit var viewModel: ApiViewModel
    private lateinit var mNominatimItemViewModel: NominatimItemViewModel
    private var mNominatimList = emptyList<NominatimItem>()
    private var boundingBox = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewHere = inflater.inflate(R.layout.fragment_map, container, false)
        val ctx = context
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        // Set up map
        locationText = viewHere.findViewById(R.id.location_now_text)
        map = viewHere.findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(14.0)
        getLocationUpdates()
        map.controller.setCenter(GeoPoint(60.2, 25.0))
        // Initialized location
        locationNow.latitude = 60.2
        locationNow.longitude = 25.0

        mNominatimItemViewModel = ViewModelProvider(this).get(NominatimItemViewModel::class.java)
        mNominatimItemViewModel.readAllData.observe(viewLifecycleOwner, Observer { nominatim ->
            mNominatimList = nominatim

            if (nominatim.isNotEmpty()) {
                map.overlays.clear()
                val boundingBoxMinLon = (locationNow.longitude - 0.15)
                val boundingBoxMinLat = (locationNow.latitude - 0.1)
                val boundingBoxMaxLon = (locationNow.longitude + 0.15)
                val boundingBoxMaxLat = (locationNow.latitude + 0.1)
                for (shop in nominatim) {
                    if ((shop.lat!! <= boundingBoxMaxLat && shop.lat >= boundingBoxMinLat) && (shop.lon!! <= boundingBoxMaxLon && shop.lon >= boundingBoxMinLon)) {
                        addMarker(shop.lat, shop.lon, shop.display_name!!, "stores")
                    }
                }
                Log.d("LOCATIONNOW","$locationNow")
                val title = getString(R.string.map_point_address,
                    getAddress(locationNow.latitude, locationNow.longitude))
                addMarker(locationNow.latitude, locationNow.longitude, title, null)
            }
        })
        return viewHere
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ApiRepository()
        val viewModelFactory = ApiViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ApiViewModel::class.java)
        viewModel.myNominatimResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful && !(response.body()?.isEmpty())!!) {
                insertDataToDatabase(response)

            } else {
                Log.d("DBG", response.errorBody().toString())
                Toast.makeText(requireContext(), "No new stores found", Toast.LENGTH_SHORT).show()
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
        var exists: Boolean
        var checkPlaceId: Int

        for (item: Nominatim in response.body()!!) {
            checkPlaceId = item.place_id
            GlobalScope.launch(context = Dispatchers.IO) {
                exists = mNominatimItemViewModel.checkIfExists(checkPlaceId)
                if (!exists) {

                    val place_id = item.place_id
                    val licence = item.licence
                    val osm_type = item.osm_type
                    val osm_id = item.osm_id
                    // val boundingbox = item.boundingbox
                    val lat = item.lat
                    val lon = item.lon
                    val display_name = item.display_name
                    val type = item.type
                    val importance = item.importance
                    val icon = item.icon


                    val nominatim = NominatimItem(
                        0,
                        place_id,
                        licence,
                        osm_type,
                        osm_id,
                        // boundingbox,
                        lat,
                        lon,
                        display_name,
                        type,
                        importance,
                        icon
                    )
                    for (shop in response.body()!!) {
                        addMarker(shop.lat, shop.lon, shop.display_name, "stores")
                    }
                    val title = getString(R.string.map_point_address, getAddress(locationNow.latitude, locationNow.longitude))
                    addMarker(locationNow.latitude, locationNow.longitude, title, "none")

                    mNominatimItemViewModel.addNominatimData(nominatim)
                }
            }
        }
        Log.d("STORES", "Successfully added stores")
    }

    private fun addMarker(lat: Double,  lon: Double, title: String, customIcon: String?) {

        // Custom markers if needed
        when (customIcon) {
            "stores" -> {
                val marker = Marker(map)
                marker.position = GeoPoint(lat, lon)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.icon = requireContext().resources.getDrawable(R.drawable.shopping_icon)
                marker.title = title
                map.overlays.add(marker)
            }
            // Default marker
            else -> {
                val marker = Marker(map)
                marker.position = GeoPoint(lat, lon)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = title
                map.overlays.add(marker)
            }
        }
    }

    private fun getLocationUpdates() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        locationRequest = LocationRequest()
        locationRequest.interval = 100
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 30F
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    val oldLocation = locationNow
                    locationNow = location


                    // New shops if the location changed over ~2 km
                    if (((oldLocation.latitude - locationNow.latitude).absoluteValue >= 0.02) || ((oldLocation.longitude - locationNow.longitude).absoluteValue >= 0.02)) {

                        map.overlays.clear()
                        val boundingBoxMinLon = (locationNow.longitude - 0.15)
                        val boundingBoxMinLat = (locationNow.latitude - 0.1)
                        val boundingBoxMaxLon = (locationNow.longitude + 0.15)
                        val boundingBoxMaxLat = (locationNow.latitude + 0.1)
                        boundingBox = "$boundingBoxMinLon,$boundingBoxMinLat,$boundingBoxMaxLon,$boundingBoxMaxLat"

                        viewModel.getNominatimViewBox(boundingBox)


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


                        val title = getString(R.string.map_point_address, getAddress(locationNow.latitude, locationNow.longitude))
                        addMarker(locationNow.latitude, locationNow.longitude, title, "none")
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