package com.mappls.sdk.demo.activity.restapis

import android.annotation.SuppressLint
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
import com.mappls.sdk.demo.databinding.ActivityDirectionsBinding
import com.mappls.sdk.demo.databinding.RouteTabItemLayoutBinding
import com.mappls.sdk.demo.settings.fragment.DistanceSettingsFragment
import com.mappls.sdk.demo.settings.model.MapplsDistanceApiSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.distance.MapplsDistanceMatrix
import com.mappls.sdk.services.api.distance.MapplsDistanceMatrixManager
import com.mappls.sdk.services.api.distance.models.DistanceResponse
import com.mappls.sdk.services.api.distance.models.DistanceResults
import com.mappls.sdk.services.utils.ErrorCodes

class DistanceActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, OnMapReadyCallback {

    private lateinit var mBinding: ActivityDirectionsBinding
    private var mResource: String = DirectionsCriteria.RESOURCE_DISTANCE
    private var mProfile: String = DirectionsCriteria.PROFILE_DRIVING
    private var mMapplsMap: MapplsMap? = null
    private var mDistanceManager: MapplsDistanceMatrixManager? = null
    private var showResponse = false
    private var hasResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityDirectionsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.directionsHeader.headerTitle.text = "Get Distance"
        mBinding.directionsHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.directionsHeader.ivSettings.visibility = View.VISIBLE
        mBinding.directionsHeader.ivSettings.setOnClickListener {
            val fragment = DistanceSettingsFragment()
            replaceFragment(fragment)
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

        addTabs()

        mBinding.profileTabLayout.addOnTabSelectedListener(this)
        mBinding.rgResourceLayout.setOnCheckedChangeListener { group, isChecked ->
            when(group.checkedRadioButtonId) {
                mBinding.rbNonTraffic.id -> {
                    mResource = DirectionsCriteria.RESOURCE_DISTANCE
                }
                mBinding.rbTraffic.id -> {
                    mResource = DirectionsCriteria.RESOURCE_DISTANCE_TRAFFIC
                }
                mBinding.rbRouteEta.id -> {
                    mResource = DirectionsCriteria.RESOURCE_DISTANCE_ETA
                }
            }
            callDistance()
        }

        showResponse(showResponse)
        mBinding.directionShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.directionShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }
    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.directionShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.directionShowHideResponseLayout.tvShowData.isEnabled = showResponse
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
        callDistance()
    }

    @SuppressLint("WrongConstant")
    private fun callDistance() {
        mDistanceManager?.cancel()
        mBinding.directionsJsonResponse.text = ""
        mBinding.directionsJsonResponseSv.visibility = View.GONE
        mBinding.directionsDetailLayout.visibility = View.GONE
        val builder = MapplsDistanceMatrix.builder()
            .profile(mProfile)
            .resource(mResource)

        val origin = MapplsDistanceApiSettings.instance.origin
        val destination = MapplsDistanceApiSettings.instance.destination
        val waypoints = MapplsDistanceApiSettings.instance.wayPoints

        if(origin.contains(",") && origin.split(",").size > 1) {
            val latitude = origin.split(",")[1]
            val longitude = origin.split(",")[0]
            if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                builder.coordinate(Point.fromLngLat(longitude.toDouble(), latitude.toDouble()))
            } else {
                Toast.makeText(this, "Invalid Origin Coordinates", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            builder.coordinate(origin)
        }
        waypoints?.let {
            val wapointList = it.split(";")
            wapointList.forEach { waypoint->
                if(waypoint.contains(",") && waypoint.split(",").size > 1) {
                    val latitude = waypoint.split(",")[1]
                    val longitude = waypoint.split(",")[0]
                    if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                        builder.coordinate(Point.fromLngLat(longitude.toDouble(), latitude.toDouble()))
                    }
                } else {
                    builder.coordinate(waypoint)
                }
            }
        }

        if(destination.contains(",") && destination.split(",").size > 1) {
            val latitude = destination.split(",")[1]
            val longitude = destination.split(",")[0]
            if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                builder.coordinate(Point.fromLngLat(longitude.toDouble(), latitude.toDouble()))
            } else {
                Toast.makeText(this, "Invalid Destination Coordinates", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            builder.coordinate(destination)
        }
//        if(MapplsDistanceApiSettings.instance.sources != null) {
//            builder.sources(MapplsDistanceApiSettings.instance.sources)
//        }
//        if(MapplsDistanceApiSettings.instance.destinations != null) {
//            builder.destinations(MapplsDistanceApiSettings.instance.destinations)
//        }
//        if(MapplsDistanceApiSettings.instance.routeType != null) {
//            builder.routeType(MapplsDistanceApiSettings.instance.routeType)
//        }
//        if(MapplsDistanceApiSettings.instance.fallbackSpeed != null) {
//            builder.fallbackSpeed(MapplsDistanceApiSettings.instance.fallbackSpeed)
//        }
//        if(MapplsDistanceApiSettings.instance.fallbackCoordinate != null) {
//            builder.fallbackCoordinate(MapplsDistanceApiSettings.instance.fallbackCoordinate)
//        }

        mDistanceManager = MapplsDistanceMatrixManager.newInstance(builder.build())
        mDistanceManager?.call(object: OnResponseCallback<DistanceResponse> {
            override fun onSuccess(distanceResponse: DistanceResponse?) {
                mBinding.directionsJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(
                    Gson().fromJson(distanceResponse?.toJson(), Map::class.java))
                hasResponse = true
                if(distanceResponse != null && distanceResponse.results() != null) {
                    showDirectionData(distanceResponse.results()!!)
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
                    Toast.makeText(this@DistanceActivity, "$p0 --- $p1", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

    }


    private fun showDirectionData(result: DistanceResults) {
        mBinding.tvDirectionsDistance.text = "Distance: ${getDistanceText(result.distances()?.get(0)?.get(1)?:0.0)}"
        mBinding.tvDirectionsTime.text = "Time: ${getDurationText(result.durations()?.get(0)?.get(1)?:0.0)}"
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
                mBinding.rgResourceLayout.visibility = View.VISIBLE
            }
            1 -> {
                mProfile = DirectionsCriteria.PROFILE_BIKING
                mBinding.rgResourceLayout.visibility = View.GONE
                mBinding.rgResourceLayout.check(mBinding.rbNonTraffic.id)
                mResource = DirectionsCriteria.RESOURCE_DISTANCE
            }
            2-> {
                mProfile = DirectionsCriteria.PROFILE_WALKING
                mBinding.rgResourceLayout.visibility = View.GONE
                mBinding.rgResourceLayout.check(mBinding.rbNonTraffic.id)
                mResource = DirectionsCriteria.RESOURCE_DISTANCE
            }
        }
        callDistance()
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

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap
        callDistance()
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}