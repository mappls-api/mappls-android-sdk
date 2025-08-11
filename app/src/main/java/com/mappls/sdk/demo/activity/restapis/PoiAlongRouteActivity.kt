package com.mappls.sdk.demo.activity.restapis

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.DirectionsActivity
import com.mappls.sdk.demo.activity.restapis.NearbyActivity
import com.mappls.sdk.demo.adapter.NearbyAdapter
import com.mappls.sdk.demo.adapter.PoiAlongRouteAdapter
import com.mappls.sdk.demo.databinding.ActivityPoiAlongRouteBinding
import com.mappls.sdk.demo.plugin.DirectionPolylinePlugin
import com.mappls.sdk.demo.settings.fragment.NearbySettingsFragment
import com.mappls.sdk.demo.settings.fragment.PoiAlongRouteSettingsFragment
import com.mappls.sdk.demo.settings.model.MapplsDirectionsApiSettings
import com.mappls.sdk.demo.settings.model.MapplsPoiAlongRouteApiSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.alongroute.MapplsPOIAlongRoute
import com.mappls.sdk.services.api.alongroute.MapplsPOIAlongRouteManager
import com.mappls.sdk.services.api.alongroute.POICriteria
import com.mappls.sdk.services.api.alongroute.models.POIAlongRouteResponse
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.MapplsDirectionManager
import com.mappls.sdk.services.api.directions.MapplsDirections
import com.mappls.sdk.services.api.directions.models.DirectionsResponse
import com.mappls.sdk.services.api.directions.models.DirectionsRoute
import com.mappls.sdk.services.utils.ErrorCodes

class PoiAlongRouteActivity : AppCompatActivity(), OnMapReadyCallback, TabLayout.OnTabSelectedListener {

    private lateinit var mBinding: ActivityPoiAlongRouteBinding
    private var mMapplsMap: MapplsMap? = null
    private var mDirectionsManager: MapplsDirectionManager? = null
    private var mPoiAlongRouteManager: MapplsPOIAlongRouteManager? = null
    private var mDirectionsPlugin: DirectionPolylinePlugin? = null
    private var showResponse = false
    private var hasResponse = false
    private lateinit var mAdapter: PoiAlongRouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityPoiAlongRouteBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.poiAlongRouteHeader.headerTitle.text = "POI Along Route"
        mBinding.poiAlongRouteHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.poiAlongRouteHeader.ivSettings.visibility = View.VISIBLE
        mBinding.poiAlongRouteHeader.ivSettings.setOnClickListener {
            replaceFragment(PoiAlongRouteSettingsFragment())
        }
        mAdapter = PoiAlongRouteAdapter() {

        }
        mBinding.rvPoiAlongRouteList.adapter = mAdapter
        mBinding.rvPoiAlongRouteList.layoutManager = LinearLayoutManager(this)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        showResponse(showResponse)
        mBinding.poiAlongRouteShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.poiAlongRouteShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }
        mBinding.poiAlongRouteTabLayout.addOnTabSelectedListener(this)
    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.poiAlongRouteShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.poiAlongRouteShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.poiAlongRouteJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.poiAlongRouteJsonResponseSv.visibility = View.VISIBLE
            }
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.poiAlongRouteFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        callDirections()
    }



    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.poiAlongRouteTabLayout.removeOnTabSelectedListener(this)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
        mDirectionsPlugin = DirectionPolylinePlugin(mapplsMap, mBinding.mapView)
        callDirections()
    }

    private fun callDirections() {
        mDirectionsManager?.cancel()
        mDirectionsPlugin?.clear()
        val builder = MapplsDirections.builder()
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .resource(DirectionsCriteria.RESOURCE_ROUTE)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
        val origin = MapplsPoiAlongRouteApiSettings.instance.origin
        val destination = MapplsPoiAlongRouteApiSettings.instance.destination
        if(origin.contains(",") && origin.split(",").size > 1) {
            val latitude = origin.split(",")[1]
            val longitude = origin.split(",")[0]
            if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                builder.origin(Point.fromLngLat(longitude.toDouble(), latitude.toDouble()))
            } else {
                Toast.makeText(this, "Invalid Origin Coordinates", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            builder.origin(origin)
        }

        if(destination.contains(",") && destination.split(",").size > 1) {
            val latitude = destination.split(",")[1]
            val longitude = destination.split(",")[0]
            if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                builder.destination(Point.fromLngLat(longitude.toDouble(), latitude.toDouble()))
            } else {
                Toast.makeText(this, "Invalid Destination Coordinates", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            builder.destination(destination)
        }
        if(MapplsPoiAlongRouteApiSettings.instance.geometry == POICriteria.GEOMETRY_POLYLINE6) {
            builder.geometries(DirectionsCriteria.GEOMETRY_POLYLINE6)
        } else {
            builder.geometries(DirectionsCriteria.GEOMETRY_POLYLINE)
        }
        mDirectionsManager = MapplsDirectionManager.newInstance(builder.build())
        mDirectionsManager?.call(object: OnResponseCallback<DirectionsResponse> {
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                if(directionsResponse != null && directionsResponse.routes().isNotEmpty()) {
                    mDirectionsPlugin?.setTrips(directionsResponse.routes(), geometries = if(MapplsPoiAlongRouteApiSettings.instance.geometry == POICriteria.GEOMETRY_POLYLINE6) {
                        DirectionsCriteria.GEOMETRY_POLYLINE6} else {
                        DirectionsCriteria.GEOMETRY_POLYLINE})
                    val coordinates = PolylineUtils.decode(directionsResponse.routes()[0].geometry()!!, if(MapplsPoiAlongRouteApiSettings.instance.geometry == DirectionsCriteria.GEOMETRY_POLYLINE) 5 else 6)
                    val latLngBounds = LatLngBounds.Builder()
                    coordinates.forEach {
                        latLngBounds.include(LatLng(it.latitude(), it.longitude()))
                    }
                    mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 20, 20, 20, 120))
                    callPoiAlongRoute(directionsResponse.routes()[0].geometry()!!)
                }
            }
            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    Toast.makeText(this@PoiAlongRouteActivity, "Directions: $p0 --- $p1", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }



    private fun callPoiAlongRoute(geometry: String) {
        mPoiAlongRouteManager?.cancel()
        mAdapter.setPoiAlongRouteSearchResults(listOf())
        mBinding.poiAlongRouteJsonResponse.text = ""
        mBinding.poiAlongRouteJsonResponseSv.visibility = View.GONE
        val builder = MapplsPOIAlongRoute.builder()
            .path(geometry)
            .category(MapplsPoiAlongRouteApiSettings.instance.category)
//            .geometries(MapplsPoiAlongRouteApiSettings.instance.geometry)
        hasResponse = false
        if(MapplsPoiAlongRouteApiSettings.instance.buffer != null) {
            builder.buffer(MapplsPoiAlongRouteApiSettings.instance.buffer)
        }
        if(MapplsPoiAlongRouteApiSettings.instance.page != null) {
            builder.page(MapplsPoiAlongRouteApiSettings.instance.page)
        }
//        if(MapplsPoiAlongRouteApiSettings.instance.sort) {
//            builder.sort(MapplsPoiAlongRouteApiSettings.instance.sort)
//        }
//        if(MapplsPoiAlongRouteApiSettings.instance.filter != null) {
//            builder.filter(MapplsPoiAlongRouteApiSettings.instance.filter)
//        }

        mPoiAlongRouteManager = MapplsPOIAlongRouteManager.newInstance(builder.build())
        mPoiAlongRouteManager?.call(object: OnResponseCallback<POIAlongRouteResponse> {
            override fun onSuccess(poiAlongRouteResponse: POIAlongRouteResponse?) {
                mBinding.poiAlongRouteJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(poiAlongRouteResponse)
                poiAlongRouteResponse?.suggestedPOIs?.let {
                    hasResponse = true
                    var isLatLngAvailable = false
                    mAdapter.setPoiAlongRouteSearchResults(it)
                    it.forEach {
                        if(it.latitude != null && it.longitude != null) {
                            isLatLngAvailable = true
                            mMapplsMap?.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                        } else {
                            mMapplsMap?.addMarker(MarkerOptions().mapplsPin(it.mapplsPin))
                        }
                    }
                }
                if(showResponse) {
                    mBinding.poiAlongRouteJsonResponseSv.visibility = View.VISIBLE
                }

            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    Toast.makeText(this@PoiAlongRouteActivity, "Poi Along Route: $p0 ---- $p1", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

    }


    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab?.position == 0) {
            mBinding.poiAlongRouteListLayout.visibility = View.GONE
        } else {
            mBinding.poiAlongRouteListLayout.visibility = View.VISIBLE
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}