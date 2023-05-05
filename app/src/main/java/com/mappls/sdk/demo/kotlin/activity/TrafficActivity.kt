package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityTrafficBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng

class TrafficActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityTrafficBinding
    private var mapplsMap: MapplsMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_traffic)
        mBinding.showTraffic.isChecked = false

        mBinding.freeflow.isClickable = false
        mBinding.nonfreeflow.isClickable = false
        mBinding.closure.isClickable = false
        mBinding.stopIcon.isClickable = false
        mBinding.freeflow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mapplsMap?.isEnableTraffic == true)
                mapplsMap?.enableTrafficFreeFlow(isChecked)
        }
        mBinding.nonfreeflow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mapplsMap?.isEnableTraffic == true)
                mapplsMap?.enableTrafficNonFreeFlow(isChecked)
        }
        mBinding.closure.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mapplsMap?.isEnableTraffic == true)
                mapplsMap?.enableTrafficClosure(isChecked)
        }
        mBinding.stopIcon.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mapplsMap?.isEnableTraffic == true)
                mapplsMap?.enableTrafficStopIcon(isChecked)
        }
        mBinding.showTraffic.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mapplsMap != null) {
                mapplsMap?.enableTraffic(isChecked)
                mBinding.freeflow.isClickable = isChecked
                mBinding.nonfreeflow.isClickable = isChecked
                mBinding.closure.isClickable = isChecked
                mBinding.stopIcon.isClickable = isChecked

                if (isChecked) {
                    mBinding.freeflow.isChecked = (mapplsMap?.isEnableTrafficFreeFlow == true)
                    mBinding.nonfreeflow.isChecked = (mapplsMap?.isEnableTrafficNonFreeFlow == true)
                    mBinding.closure.isChecked = (mapplsMap?.isEnableTrafficClosure == true)
                    mBinding.stopIcon.isChecked = (mapplsMap?.isEnableTrafficStopIcon == true)
                } else {
                    mBinding.freeflow.isChecked = false
                    mBinding.nonfreeflow.isChecked = false
                    mBinding.closure.isChecked = false
                    mBinding.stopIcon.isChecked = false
                }
            }
        }
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        mBinding.trafficBtnLayout.visibility = View.VISIBLE

        val cameraPosition = CameraPosition.Builder().target(
            LatLng(
                25.321684, 82.987289
            )
        ).zoom(15.0).tilt(0.0).build()
        mapplsMap.cameraPosition = cameraPosition
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()

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

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }
}