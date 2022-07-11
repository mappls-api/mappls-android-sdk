package com.mappls.sdk.demo.kotlin.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDrivingRangeBinding
import com.mappls.sdk.demo.kotlin.settings.MapplsDrivingRangeSetting
import com.mappls.sdk.drivingrange.*
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.location.LocationComponentActivationOptions
import com.mappls.sdk.maps.location.LocationComponentOptions
import com.mappls.sdk.maps.location.engine.LocationEngineCallback
import com.mappls.sdk.maps.location.engine.LocationEngineResult
import com.mappls.sdk.maps.location.permissions.PermissionsListener
import com.mappls.sdk.maps.location.permissions.PermissionsManager
import java.lang.Exception

class DrivingRangeActivity : AppCompatActivity(), OnMapReadyCallback, PermissionsListener {

    private lateinit var mPermissionsManager: PermissionsManager
    private var mMapplsMap: MapplsMap? = null
    private var mapplsDrivingRangePlugin: MapplsDrivingRangePlugin? = null
    private lateinit var mBinding: ActivityDrivingRangeBinding
    private var isLocationCall: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_driving_range)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap

        mapplsDrivingRangePlugin = MapplsDrivingRangePlugin(
            mBinding.mapView, mMapplsMap!!
        )
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapplsMap.getStyle {
                enableLocation(it)
            }


        } else {
            mPermissionsManager = PermissionsManager(this)
            mPermissionsManager.requestLocationPermissions(this)
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation(style: Style) {
        val options: LocationComponentOptions = LocationComponentOptions.builder(this)
            .trackingGesturesManagement(true)
            .build()
        val locationComponentActivationOptions =
            LocationComponentActivationOptions.builder(this, style)
                .locationComponentOptions(options)
                .build()
        mMapplsMap?.locationComponent?.activateLocationComponent(
            locationComponentActivationOptions
        )
        mMapplsMap?.locationComponent?.isLocationComponentEnabled = true
        if (MapplsDrivingRangeSetting.instance.isUsingCurrentLocation) {
            mMapplsMap?.locationComponent?.locationEngine?.getLastLocation(object :
                LocationEngineCallback<LocationEngineResult> {
                override fun onSuccess(p0: LocationEngineResult?) {
                    if (p0 == null || p0.lastLocation == null) {
                        return
                    }
                    if (isLocationCall) {
                        return
                    }
                    isLocationCall = true
                    val location = p0.lastLocation!!
                    drawDrivingRange(Point.fromLngLat(location.longitude, location.latitude))

                }

                override fun onFailure(p0: Exception) {
                    p0.stackTrace
                }
            })
            if (!isLocationCall) {
                val location = mMapplsMap?.locationComponent?.lastKnownLocation
                if (location != null) {
                    isLocationCall = true;
                    drawDrivingRange(Point.fromLngLat(location.longitude, location.latitude))
                }
            }
        } else {
            drawDrivingRange(MapplsDrivingRangeSetting.instance.location)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mPermissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun drawDrivingRange(location: Point) {
        mMapplsMap?.cameraPosition =
            CameraPosition.Builder().target(LatLng(location.latitude(), location.longitude()))
                .zoom(12.0).build()

        val speed: MapplsDrivingRangeSpeed = if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL) {
            MapplsDrivingRangeSpeed.optimal()
        } else if(MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT){
            MapplsDrivingRangeSpeed.predictiveSpeedFromCurrentTime()
        } else {
            MapplsDrivingRangeSpeed.predictiveSpeedFromCustomTime(MapplsDrivingRangeSetting.instance.time)
        }

        val option = MapplsDrivingRangeOption.builder()
            .location(location)
            .rangeTypeInfo(
                MapplsDrivingRangeTypeInfo.builder()
                    .rangeType(MapplsDrivingRangeSetting.instance.rangeType)
                    .contours(
                        listOf(
                            MapplsDrivingRangeContour.builder()
                                .value(MapplsDrivingRangeSetting.instance.contourValue)
                                .color(MapplsDrivingRangeSetting.instance.contourColor)
                                .build()
                        )
                    ).build()
            )
            .drivingProfile(MapplsDrivingRangeSetting.instance.drivingProfile)
            .showLocations(MapplsDrivingRangeSetting.instance.isShowLocations)
            .isForPolygons(MapplsDrivingRangeSetting.instance.isForPolygon)
            .denoise(MapplsDrivingRangeSetting.instance.denoise)
            .generalize(MapplsDrivingRangeSetting.instance.generalize)
            .speedTypeInfo(speed)
            .build()
        mapplsDrivingRangePlugin?.drawDrivingRange(option, object :
            IDrivingRangeListener {
            override fun onSuccess() {
                Toast.makeText(this@DrivingRangeActivity, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(code: Int, message: String) {
                Toast.makeText(this@DrivingRangeActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onExplanationNeeded(p0: MutableList<String>?) {

    }

    override fun onPermissionResult(p0: Boolean) {
        if (p0) {
            mMapplsMap?.getStyle {
                enableLocation(it)
            }
        }

    }
}