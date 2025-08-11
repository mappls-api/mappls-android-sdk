package com.mappls.sdk.demo.activity.layers

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDrivingRangeBinding
import com.mappls.sdk.demo.settings.fragment.DrivingRangeSettingFragment
import com.mappls.sdk.demo.settings.model.MapplsDrivingRangeApiSetting
import com.mappls.sdk.drivingrange.IDrivingRangeListener
import com.mappls.sdk.drivingrange.MapplsDrivingRangeContour
import com.mappls.sdk.drivingrange.MapplsDrivingRangeOption
import com.mappls.sdk.drivingrange.MapplsDrivingRangePlugin
import com.mappls.sdk.drivingrange.MapplsDrivingRangeSpeed
import com.mappls.sdk.drivingrange.MapplsDrivingRangeTypeInfo
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.location.LocationComponentActivationOptions
import com.mappls.sdk.maps.location.LocationComponentOptions
import com.mappls.sdk.maps.location.engine.LocationEngineCallback
import com.mappls.sdk.maps.location.engine.LocationEngineResult
import com.mappls.sdk.maps.location.permissions.PermissionsListener
import com.mappls.sdk.maps.location.permissions.PermissionsManager
import com.mappls.sdk.services.utils.ErrorCodes

class DrivingRangeActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityDrivingRangeBinding
    private var mMapplsMap: MapplsMap? = null
    private var mapplsDrivingRangePlugin : MapplsDrivingRangePlugin?= null
    private var mPermissionsManager: PermissionsManager? = null
    private var isLocationCall = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityDrivingRangeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.drivingRangeTopHeader.headerTitle.text = getString(R.string.driving_range_title)
        mBinding.drivingRangeTopHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.drivingRangeTopHeader.ivSettings.visibility = View.VISIBLE

        mBinding.drivingRangeTopHeader.ivSettings.setOnClickListener {
            replaceFragment(DrivingRangeSettingFragment())
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.fragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        callDrivingRange()
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
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    22.553147478403194,
                    77.23388671875
                ), 4.0
            )
        )
        mapplsDrivingRangePlugin = MapplsDrivingRangePlugin(mBinding.mapView, mapplsMap)
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapplsMap.getStyle {style->
                enableLocation(style);
            };

        } else {
            mPermissionsManager = PermissionsManager(permissionRequest);
            mPermissionsManager?.requestLocationPermissions(this);
        }

    }

    val permissionRequest = object : PermissionsListener{
        override fun onExplanationNeeded(p0: List<String?>?) {

        }

        override fun onPermissionResult(b: Boolean) {
            if (b) {
                if (mMapplsMap != null) {
                    mMapplsMap!!.getStyle { style -> enableLocation(style) }
                }
            }
        }

    }
    @SuppressLint("MissingPermission")
    private fun enableLocation(style: Style) {
        val options = LocationComponentOptions.builder(this)
            .trackingGesturesManagement(true)
            .build()
        val locationComponentActivationOptions =
            LocationComponentActivationOptions.builder(this, style)
                .locationComponentOptions(options)
                .build()
        mMapplsMap!!.locationComponent
            .activateLocationComponent(locationComponentActivationOptions)
        mMapplsMap!!.locationComponent.isLocationComponentEnabled = true
        if (MapplsDrivingRangeApiSetting.instance.isUsingCurrentLocation) {
            mMapplsMap!!.locationComponent.locationEngine!!
                .getLastLocation(object : LocationEngineCallback<LocationEngineResult?> {
                    override fun onSuccess(locationEngineResult: LocationEngineResult?) {
                        if (locationEngineResult == null || locationEngineResult.lastLocation == null) {
                            return
                        }
                        if (isLocationCall) {
                            return
                        }
                        isLocationCall = true
                        val location: Location? = locationEngineResult.lastLocation
                        callDrivingRange(
                            Point.fromLngLat(location!!.longitude,location.latitude)
                        )
                    }

                    override fun onFailure(p0: java.lang.Exception) {

                    }

                })
            if (!isLocationCall) {
                val location: Location? = mMapplsMap!!.getLocationComponent().getLastKnownLocation()
                if (location != null) {
                    isLocationCall = true
                    callDrivingRange(
                        Point.fromLngLat(location!!.longitude,location.latitude)
                    )
                }
            }
        } else {
            callDrivingRange(MapplsDrivingRangeApiSetting.instance.location)
        }
    }
    private fun callDrivingRange(location: Point) {
        mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude(), location.longitude()),
            12.0
        ));

        val speed = if(MapplsDrivingRangeApiSetting.instance.speedType == MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL) {
            MapplsDrivingRangeSpeed.optimal()
        } else if(MapplsDrivingRangeApiSetting.instance.predectiveType == MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT) {
            MapplsDrivingRangeSpeed.predictiveSpeedFromCurrentTime();
        } else {
            MapplsDrivingRangeSpeed.predictiveSpeedFromCustomTime(MapplsDrivingRangeApiSetting.instance.time);
        }
        val contours = MapplsDrivingRangeContour.builder()
            .value(MapplsDrivingRangeApiSetting.Companion.instance.value)
            .color(MapplsDrivingRangeApiSetting.Companion.instance.color)
            .build()
        val list :MutableList<MapplsDrivingRangeContour> = mutableListOf()
        list.add(contours)
        val rangeTypeInfo = MapplsDrivingRangeTypeInfo.builder()
            .rangeType(MapplsDrivingRangeApiSetting.Companion.instance.rangeType)
            .contours(list)
            .build()

        val builder = MapplsDrivingRangeOption.builder()
            .location(location)
            .drivingProfile(MapplsDrivingRangeApiSetting.Companion.instance.drivingProfile)
            .rangeTypeInfo(rangeTypeInfo)
            .isForPolygons(MapplsDrivingRangeApiSetting.Companion.instance.isForPolygons)
            .speedTypeInfo(speed)
        if(MapplsDrivingRangeApiSetting.Companion.instance.generalize != null) {
            builder.generalize(MapplsDrivingRangeApiSetting.Companion.instance.generalize)
        }
        if(MapplsDrivingRangeApiSetting.Companion.instance.denoise != null) {
            builder.denoise(MapplsDrivingRangeApiSetting.Companion.instance.denoise)
        }
        if(MapplsDrivingRangeApiSetting.Companion.instance.showLocations) {
            builder.showLocations(MapplsDrivingRangeApiSetting.Companion.instance.showLocations)
        }

        mapplsDrivingRangePlugin?.drawDrivingRange(builder.build())

        mapplsDrivingRangePlugin!!.drawDrivingRange(builder.build(),object : IDrivingRangeListener {
            override fun onSuccess() {
                Toast.makeText(this@DrivingRangeActivity, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(code: Int, message: String) {
                Toast.makeText(this@DrivingRangeActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onMapError(p0: Int, p1: String?) {
        if(p0 != ErrorCodes.CANCEL_CALL) {
            Toast.makeText(this@DrivingRangeActivity, "$p0 --- $p1", Toast.LENGTH_SHORT)
                .show()
        }
    }
}