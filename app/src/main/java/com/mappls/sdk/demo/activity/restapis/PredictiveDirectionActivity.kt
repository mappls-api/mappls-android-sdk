package com.mappls.sdk.demo.activity.restapis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.DirectionsActivity
import com.mappls.sdk.demo.databinding.ActivityPredictiveDirectionBinding
import com.mappls.sdk.demo.databinding.RouteTabItemLayoutBinding
import com.mappls.sdk.demo.plugin.DirectionPolylinePlugin
import com.mappls.sdk.demo.settings.fragment.DirectionSettingsFragment
import com.mappls.sdk.demo.settings.fragment.PredectiveDirectionSettingsFragment
import com.mappls.sdk.demo.settings.model.MapplsDirectionsApiSettings
import com.mappls.sdk.demo.settings.model.MapplsPredectiveDirectionsApiSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.MapplsDirectionManager
import com.mappls.sdk.services.api.directions.MapplsDirections
import com.mappls.sdk.services.api.directions.models.DirectionsResponse
import com.mappls.sdk.services.api.directions.models.DirectionsRoute
import com.mappls.sdk.services.api.directions.predictive.MapplsDirectionDateTimeCurrent
import com.mappls.sdk.services.api.directions.predictive.MapplsDirectionDateTimeSpecified
import com.mappls.sdk.services.utils.ErrorCodes

class PredictiveDirectionActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, OnMapReadyCallback {

    private lateinit var mBinding: ActivityPredictiveDirectionBinding
    private var mProfile: String = DirectionsCriteria.PROFILE_DRIVING
    private var mMapplsMap: MapplsMap? = null
    private var mDirectionsManager: MapplsDirectionManager? = null
    private var mDirectionsPlugin: DirectionPolylinePlugin? = null
    private var hasResponse = false
    private var showResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityPredictiveDirectionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.directionsPredictiveHeader.headerTitle.text = "Get Predective Direction"
        mBinding.directionsPredictiveHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.directionsPredictiveHeader.ivSettings.visibility = View.VISIBLE
        mBinding.directionsPredictiveHeader.ivSettings.setOnClickListener {
            val fragment = PredectiveDirectionSettingsFragment()
            replaceFragment(fragment)
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

        addTabs()
        mBinding.profileTabLayout.addOnTabSelectedListener(this)
        showResponse(showResponse)
        mBinding.predectiveDirectionShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.predectiveDirectionShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.directionsFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        callDirections()
    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.predectiveDirectionShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.predectiveDirectionShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.directionsDetailLayout.visibility = View.VISIBLE
                mBinding.directionsJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.directionsJsonResponseSv.visibility = View.VISIBLE
                mBinding.directionsDetailLayout.visibility = View.GONE
            }
        }
    }

    private fun getDistanceText(distance: Double): String {
        if(distance < 1000) {
            return "$distance m"
        } else {
            return "${distance/1000.0} km"
        }
    }
    private fun getDurationText(duration: Double): String {
        val min = (duration % 3600 / 60).toLong()
        val hours = (duration % 86400 / 3600).toLong()
        val days = (duration / 86400).toLong()

        return if (days > 0) {
            "$days d" + if (hours > 0) " $hours hr" else ""
        } else {
            if (hours > 0) {
                "$hours hr" + if (min > 0) " $min min" else ""
            } else {
                if (min >= 1) "$min min" else "1 min"
            }
        }
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
        mBinding.profileTabLayout.removeOnTabSelectedListener(this)
        mBinding.mapView.onDestroy()
    }

    private fun addTabs() {
        val drivingTab = mBinding.profileTabLayout.newTab()
        val tabDrivingBinding =
            RouteTabItemLayoutBinding.inflate(LayoutInflater.from(this), null, false)
        tabDrivingBinding.tabIcon.setImageResource(R.drawable.route_driving_icon)
        tabDrivingBinding.tabText.setText("Drive")
        tabDrivingBinding.tabText.setTextColor("#21D0B2".toColorInt())
        tabDrivingBinding.tabIcon.setColorFilter("#21D0B2".toColorInt())
        drivingTab.setCustomView(tabDrivingBinding.root)
        mBinding.profileTabLayout.addTab(drivingTab)

        val bikingTab = mBinding.profileTabLayout.newTab()
        val tabBikingBinding =
            RouteTabItemLayoutBinding.inflate(LayoutInflater.from(this), null, false)
        tabBikingBinding.tabIcon.setImageResource(R.drawable.route_biking_icon)
        tabBikingBinding.tabText.setText("Bike")
        tabBikingBinding.tabText.setTextColor("#C6D0F7".toColorInt())
        tabBikingBinding.tabIcon.setColorFilter("#C6D0F7".toColorInt())
        bikingTab.setCustomView(tabBikingBinding.root)
        mBinding.profileTabLayout.addTab(bikingTab)

        val walkTab = mBinding.profileTabLayout.newTab()
        val tabWalkingBinding =
            RouteTabItemLayoutBinding.inflate(LayoutInflater.from(this), null, false)
        tabWalkingBinding.tabIcon.setImageResource(R.drawable.route_walking_icon)
        tabWalkingBinding.tabText.setText("Walk")
        tabWalkingBinding.tabText.setTextColor("#C6D0F7".toColorInt())
        tabWalkingBinding.tabIcon.setColorFilter("#C6D0F7".toColorInt())
        walkTab.setCustomView(tabWalkingBinding.root)
        mBinding.profileTabLayout.addTab(walkTab)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap
        mDirectionsPlugin = DirectionPolylinePlugin(mapplsMap, mBinding.mapView)
        callDirections()
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val tabView = tab?.customView
        tabView?.let {
            val tabText = it.findViewById<TextView>(R.id.tab_text)
            val tabIcon = it.findViewById<ImageView>(R.id.tab_icon)

            // Change text and icon color for the selected tab
            tabText.setTextColor("#21D0B2".toColorInt())
            tabIcon.setColorFilter("#21D0B2".toColorInt())
        }
        when(tab?.position) {
            0 -> {
                mProfile = DirectionsCriteria.PROFILE_DRIVING
            }
            1 -> {
                mProfile = DirectionsCriteria.PROFILE_BIKING
            }
            2-> {
                mProfile = DirectionsCriteria.PROFILE_WALKING
            }
        }
        callDirections()
    }

    private fun callDirections() {
        mDirectionsManager?.cancel()
        mDirectionsPlugin?.clear()
        mBinding.directionsJsonResponse.text = ""
        mBinding.directionsJsonResponseSv.visibility = View.GONE
        mBinding.directionsDetailLayout.visibility = View.GONE

        val builder = MapplsDirections.builder()
            .resource(DirectionsCriteria.RESOURCE_ROUTE_PREDICTIVE)
            .profile(mProfile)
            .overview(MapplsPredectiveDirectionsApiSettings.instance.overview)
            .alternatives(MapplsPredectiveDirectionsApiSettings.instance.isAlternatives)
            .geometries(MapplsPredectiveDirectionsApiSettings.instance.geometries)
            .steps(MapplsPredectiveDirectionsApiSettings.instance.steps)
        val origin = MapplsPredectiveDirectionsApiSettings.instance.origin
        val destination = MapplsPredectiveDirectionsApiSettings.instance.destination
        val waypoints = MapplsPredectiveDirectionsApiSettings.instance.wayPoints

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
        waypoints?.let {
            val wapointList = it.split(";")
            wapointList.forEach { waypoint->
                if(waypoint.contains(",") && waypoint.split(",").size > 1) {
                    val latitude = waypoint.split(",")[1]
                    val longitude = waypoint.split(",")[0]
                    if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                        builder.addWaypoint(Point.fromLngLat(longitude.toDouble(), latitude.toDouble()))
                    }
                } else {
                    builder.addWaypoint(waypoint)
                }
            }
        }
        if(MapplsPredectiveDirectionsApiSettings.instance.isCurrentTime) {
            builder.dateTime(MapplsDirectionDateTimeCurrent())
        } else {
            var time = System.currentTimeMillis() + (60 * 60 * 1000)
            if(MapplsPredectiveDirectionsApiSettings.instance.specifiedTime != null && MapplsPredectiveDirectionsApiSettings.instance.specifiedTime!! > System.currentTimeMillis()) {
                time = MapplsPredectiveDirectionsApiSettings.instance.specifiedTime!!
            }
            builder.dateTime(MapplsDirectionDateTimeSpecified(MapplsPredectiveDirectionsApiSettings.instance.locationType,
                time))
        }
        mDirectionsManager = MapplsDirectionManager.newInstance(builder.build())
        mDirectionsManager?.call(object: OnResponseCallback<DirectionsResponse> {
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                mBinding.directionsJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(
                    Gson().fromJson(directionsResponse?.toJson(), Map::class.java))
                hasResponse = true
                if(directionsResponse != null) {
                    mDirectionsPlugin?.setTrips(directionsResponse.routes(), geometries = MapplsDirectionsApiSettings.instance.geometries)
                    if(directionsResponse.routes().isNotEmpty()) {
                        showDirectionData(directionsResponse.routes()[0])
                        val coordinates = PolylineUtils.decode(directionsResponse.routes()[0].geometry()!!, if(MapplsDirectionsApiSettings.instance.geometries == DirectionsCriteria.GEOMETRY_POLYLINE) 5 else 6)
                        val latLngBounds = LatLngBounds.Builder()
                        coordinates.forEach {
                            latLngBounds.include(LatLng(it.latitude(), it.longitude()))
                        }
                        mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 20, 20, 20, 120))
                    }
                }
                if(showResponse) {
                    mBinding.directionsJsonResponseSv.visibility = View.VISIBLE
                    mBinding.directionsDetailLayout.visibility = View.GONE
                } else {
                    mBinding.directionsJsonResponseSv.visibility = View.GONE
                    mBinding.directionsDetailLayout.visibility = View.VISIBLE
                }
            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    Toast.makeText(this@PredictiveDirectionActivity, "$p0 --- $p1", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }


    private fun showDirectionData(route: DirectionsRoute) {
        mBinding.tvDirectionsDistance.text = "Distance: ${getDistanceText(route.distance()?:0.0)}"
        mBinding.tvDirectionsTime.text = "Time: ${getDurationText(route.duration()?:0.0)}"
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val tabView = tab?.customView
        tabView?.let {
            val tabText = it.findViewById<TextView>(R.id.tab_text)
            val tabIcon = it.findViewById<ImageView>(R.id.tab_icon)

            // Change text and icon color for the unselected tab
            tabText.setTextColor("#C6D0F7".toColorInt())
            tabIcon.setColorFilter("#C6D0F7".toColorInt())
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}