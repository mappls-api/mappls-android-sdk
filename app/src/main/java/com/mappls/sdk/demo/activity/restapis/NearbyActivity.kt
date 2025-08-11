package com.mappls.sdk.demo.activity.restapis

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.adapter.AutoSuggestAdapter
import com.mappls.sdk.demo.adapter.NearbyAdapter
import com.mappls.sdk.demo.databinding.ActivityNearbyBinding
import com.mappls.sdk.demo.settings.fragment.NearbySettingsFragment
import com.mappls.sdk.demo.settings.model.MapplsNearbyApiSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.maps.Mappls
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinBoundUpdate
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.maps.location.LocationComponentActivationOptions
import com.mappls.sdk.maps.location.engine.LocationEngineCallback
import com.mappls.sdk.maps.location.engine.LocationEngineRequest
import com.mappls.sdk.maps.location.engine.LocationEngineResult
import com.mappls.sdk.maps.location.modes.CameraMode
import com.mappls.sdk.maps.location.modes.RenderMode
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.nearby.MapplsNearby
import com.mappls.sdk.services.api.nearby.MapplsNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.utils.ErrorCodes
import java.lang.Exception

class NearbyActivity : AppCompatActivity(), OnMapReadyCallback, LocationEngineCallback<LocationEngineResult>, MapplsMap.OnMapLongClickListener, TabLayout.OnTabSelectedListener {

    private lateinit var mBinding: ActivityNearbyBinding
    private var mMapplsMap: MapplsMap? = null
    private var currentLocation: Location? = null
    private var initialNearbyApiCall = false
    private var mapplsNearbyManager: MapplsNearbyManager? = null
    private var showResponse = false
    private var hasResponse = false
    private lateinit var mAdapter: NearbyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityNearbyBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.nearbyHeader.headerTitle.text = "Nearby"
        mBinding.nearbyHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.nearbyHeader.ivSettings.visibility = View.VISIBLE
        mBinding.nearbyHeader.ivSettings.setOnClickListener {
            replaceFragment(NearbySettingsFragment())
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mAdapter = NearbyAdapter() {

        }
        mBinding.rvNearbyList.adapter = mAdapter
        mBinding.rvNearbyList.layoutManager = LinearLayoutManager(this)
        mBinding.mapView.getMapAsync(this)

        showResponse(showResponse)
        mBinding.nearbyShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.nearbyShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }

        mBinding.nearbyTabLayout.addOnTabSelectedListener(this)

    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.nearbyShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.nearbyShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.nearbyJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.nearbyJsonResponseSv.visibility = View.VISIBLE
            }
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.nearbyFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        callNearbyApi()
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
        mBinding.nearbyTabLayout.removeOnTabSelectedListener(this)
        mMapplsMap?.removeOnMapLongClickListener(this)
        mMapplsMap?.locationComponent?.locationEngine?.removeLocationUpdates(this)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap
        mapplsMap.addOnMapLongClickListener(this)
        Toast.makeText(this, "Long Click to update Custom Location if current location not enable",
            Toast.LENGTH_SHORT).show()
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
        mapplsMap.getStyle {
            enableCurrentLocation(it)
        }
    }

    private fun enableCurrentLocation(style: Style) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, style)
                    .build()
            mMapplsMap?.locationComponent?.activateLocationComponent(
                locationComponentActivationOptions
            )
            mMapplsMap?.locationComponent?.isLocationComponentEnabled = true
            mMapplsMap?.locationComponent?.renderMode = RenderMode.COMPASS
            mMapplsMap?.locationComponent?.cameraMode = CameraMode.NONE
            mMapplsMap?.locationComponent?.locationEngine?.requestLocationUpdates(
                LocationEngineRequest.Builder(5000).setDisplacement(500f).build(), this, Looper.getMainLooper())
        }
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onSuccess(p0: LocationEngineResult?) {
        if(p0?.lastLocation != null) {
            this.currentLocation = p0.lastLocation
            if(!initialNearbyApiCall) {
                callNearbyApi()
            }
        }
    }

    private fun callNearbyApi() {

        initialNearbyApiCall = true
        mMapplsMap?.clear()
        mAdapter.setNearbySearchResults(listOf())
        mBinding.nearbyJsonResponse.text = ""
        mBinding.nearbyJsonResponseSv.visibility = View.GONE
        val builder = MapplsNearby.builder()
        if(MapplsNearbyApiSettings.instance.enableCurrentLocation) {
            if(currentLocation != null) {
                builder.setLocation(currentLocation?.latitude!!, currentLocation?.longitude)
            } else {
                Toast.makeText(this, "Please wait for fetching Current Location", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            val customLocation = MapplsNearbyApiSettings.instance.customLocation
            if(customLocation.contains(",")) {
                val latitude = customLocation.split(",")[0]
                val longitude = customLocation.split(",")[0]
                if(Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                    builder.setLocation(latitude.toDouble(), longitude.toDouble())
                } else {
                    Toast.makeText(this, "Invalid Custom Location", Toast.LENGTH_SHORT).show()
                    return
                }
            } else {
                builder.setLocation(customLocation)
            }

        }
        builder.keyword(MapplsNearbyApiSettings.instance.keyword)
        if(MapplsNearbyApiSettings.instance.page != null) {
            builder.page(MapplsNearbyApiSettings.instance.page)
        }
//        if(MapplsNearbyApiSettings.instance.sortBy != null) {
//            builder.sortBy(MapplsNearbyApiSettings.instance.sortBy)
//        }
//        if(MapplsNearbyApiSettings.instance.searchBy != null) {
//            builder.searchBy(MapplsNearbyApiSettings.instance.searchBy)
//        }
        if(MapplsNearbyApiSettings.instance.radius != null) {
            builder.radius(MapplsNearbyApiSettings.instance.radius)
        }
//        if(MapplsNearbyApiSettings.instance.itemCount != null) {
//            builder.itemCount(MapplsNearbyApiSettings.instance.itemCount)
//        }
//        if(MapplsNearbyApiSettings.instance.bounds != null) {
//            builder.bounds(MapplsNearbyApiSettings.instance.bounds)
//        }
//        if(MapplsNearbyApiSettings.instance.pod != null) {
//            builder.pod(MapplsNearbyApiSettings.instance.pod)
//        }
//        if(MapplsNearbyApiSettings.instance.filter != null) {
//            builder.filter(MapplsNearbyApiSettings.instance.filter)
//        }
//        if(MapplsNearbyApiSettings.instance.explain) {
//            builder.explain(MapplsNearbyApiSettings.instance.explain)
//        }
//        if(MapplsNearbyApiSettings.instance.richData) {
//            builder.richData(MapplsNearbyApiSettings.instance.richData)
//        }
//        if(MapplsNearbyApiSettings.instance.userName != null) {
//            builder.userName(MapplsNearbyApiSettings.instance.userName)
//        }
//        if(MapplsNearbyApiSettings.instance.ignoreAutoExpand) {
//            builder.ignoreAutoExpand(MapplsNearbyApiSettings.instance.ignoreAutoExpand)
//        }
//        if(MapplsNearbyApiSettings.instance.includes != null) {
//            builder.includes(*MapplsNearbyApiSettings.instance.includes!!.toTypedArray())
//        }
        mapplsNearbyManager?.cancel()
        hasResponse = false
        mapplsNearbyManager = MapplsNearbyManager.newInstance(builder.build())
        mapplsNearbyManager?.call(object: OnResponseCallback<NearbyAtlasResponse> {
            override fun onSuccess(nearbyAtlasResponse: NearbyAtlasResponse?) {
                mBinding.nearbyJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(nearbyAtlasResponse)
                nearbyAtlasResponse?.suggestedLocations?.let {
                    hasResponse = true
                    mAdapter.setNearbySearchResults(it)
                   var isLatLngAvailable = false
                    it.forEach {
                        if(it.latitude != null && it.longitude != null) {
                            isLatLngAvailable = true
                            mMapplsMap?.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                        } else {
                            mMapplsMap?.addMarker(MarkerOptions().mapplsPin(it.mapplsPin))
                        }
                    }
                    if(isLatLngAvailable) {
                        mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(
                            LatLngBounds.Builder().includes(it.map { LatLng(it.latitude, it.longitude) }).build(),
                            20, 20, 20, 20
                        ))
                    } else {
                        mMapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(it.map { it.mapplsPin },
                            20, 20, 20, 20
                        ))
                    }
                    if(showResponse) {
                        mBinding.nearbyJsonResponseSv.visibility = View.VISIBLE
                    }
                } ?: run {
                    Toast.makeText(this@NearbyActivity, "Response is null", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    Toast.makeText(this@NearbyActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

    }

    override fun onFailure(p0: Exception) {

    }

    override fun onMapLongClick(p0: LatLng): Boolean {
        if(!MapplsNearbyApiSettings.instance.enableCurrentLocation) {
            MapplsNearbyApiSettings.instance.customLocation = "${p0.latitude},${p0.longitude}"
            callNearbyApi()
        }
        return false
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab?.position == 0) {
            mBinding.nearbyListLayout.visibility = View.GONE
        } else {
            mBinding.nearbyListLayout.visibility = View.VISIBLE
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

}