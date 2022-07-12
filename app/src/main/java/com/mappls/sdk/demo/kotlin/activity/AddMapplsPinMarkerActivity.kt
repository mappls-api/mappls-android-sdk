package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityAddMarkerBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import java.util.*

/**
 ** Created by Saksham on 26-11-2020.
 **/

class AddMapplsPinMarkerActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mapplsMap: MapplsMap? = null
    private lateinit var mBinding: ActivityAddMarkerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_marker)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.btnSearch.setOnClickListener(View.OnClickListener {
            val mapplsPin: String = mBinding.etMapplsPin.getText().toString()
            if (!mapplsPin.isEmpty()) {
                val mapplsPinList = mapplsPin.split(",".toRegex())
                addMarker(mapplsPinList)
            } else {
                Toast.makeText(this@AddMapplsPinMarkerActivity, "Please add Mappls Pin", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addMarker(mapplsPins: List<String>) {
        if (mapplsMap != null) {
            mapplsMap?.clear()
            val markerOptions: MutableList<MarkerOptions> = ArrayList()
            val mapplsPinList: MutableList<String> = ArrayList()
            for (mapplsPin in mapplsPins) {
                markerOptions.add(MarkerOptions().mapplsPin(mapplsPin).title(mapplsPin))
                mapplsPinList.add(mapplsPin)
            }
            mapplsMap?.addMarkers(markerOptions, object : MapplsMap.OnMarkerAddedListener {
                override fun onSuccess() {
                    Toast.makeText(this@AddMapplsPinMarkerActivity, "Marker Added Successfully", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure() {
                    Toast.makeText(this@AddMapplsPinMarkerActivity, "Invalid Mappls Pin", Toast.LENGTH_SHORT).show()
                }
            })
            if (mapplsPinList.size > 0) {
                if (mapplsPinList.size == 1) {
                    mapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom(mapplsPinList[0], 16.0))
                } else {
                    mapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPinList, 10, 100, 10, 10))
                }
            }

//            mapplsMap.showMarkers(markers, 10, 100, 10, 10);
        }
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.0, 77.0), 5.0))
        mBinding.layoutMapplsPin.visibility = View.VISIBLE
    }

    override fun onMapError(i: Int, s: String?) {}
}