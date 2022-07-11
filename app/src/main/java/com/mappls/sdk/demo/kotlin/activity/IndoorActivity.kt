package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.IndoorLayoutBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.Icon
import com.mappls.sdk.maps.annotations.IconFactory
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng


/**
 * Created by Saksham on 4/12/19.
 */
class IndoorActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: MapplsMap? = null
    private lateinit var mBinding :IndoorLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.indoor_layout)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

    }

    override fun onMapError(i: Int, err: String?) {

    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        map = mapplsMap
        //To turn on layer control
        mapplsMap.uiSettings?.isLayerControlEnabled = true



        val iconFactory: IconFactory = IconFactory.getInstance(this)
        val icon: Icon = iconFactory.fromResource(R.drawable.placeholder)
        mapplsMap.addMarker(MarkerOptions()
                .position(LatLng(28.5425071, 77.1560724))
                .icon(icon))

        val cameraPosition: CameraPosition = CameraPosition.Builder()
                .target(LatLng(28.5425071, 77.1560724))
                .zoom(16.0)
                .tilt(0.0)
                .build()

        mapplsMap.cameraPosition = cameraPosition
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
}