package com.mappls.sdk.demo.kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_traffic)
//        setContentView(R.layout.activity_traffic)
        mBinding.showTraffic.isChecked = false

        mBinding.freeflow.isClickable = false
        mBinding.nonfreeflow.isClickable = false
        mBinding.closure.isClickable = false
        mBinding.stopIcon.isClickable = false
        mBinding.freeflow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                mapplsMap?.enableTrafficFreeFlow(true);
            }else{
                mapplsMap?.enableTrafficFreeFlow(false)
            }
        }
        mBinding.nonfreeflow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                mapplsMap?.enableTrafficNonFreeFlow(true)
            }else{
                mapplsMap?.enableTrafficNonFreeFlow(false)
            }
        }
        mBinding.closure.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                mapplsMap?.enableTrafficClosure(true)
            }else{
                mapplsMap?.enableTrafficClosure(false)
            }
        }
        mBinding.stopIcon.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                mapplsMap?.enableTrafficStopIcon(true)
            }else{
                mapplsMap?.enableTrafficStopIcon(false)
            }
        }
        mBinding.showTraffic.setOnCheckedChangeListener { buttonView, isChecked ->
            if (mapplsMap != null){
                if (isChecked) {
                    mapplsMap?.enableTraffic(true)
                } else {
                    mapplsMap?.enableTraffic(false)
                }
            }else{
                mBinding.showTraffic.isChecked = false
            }

        }
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        val cameraPosition = CameraPosition.Builder().target(LatLng(
            25.321684, 82.987289)).zoom(15.0).tilt(0.0).build()
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