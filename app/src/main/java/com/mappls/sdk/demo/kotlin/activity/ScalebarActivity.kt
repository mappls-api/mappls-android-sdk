package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.BaseLayoutBinding
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugin.scalebar.ScaleBarOptions
import com.mappls.sdk.plugin.scalebar.ScaleBarPlugin


class ScalebarActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mBinding:BaseLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.base_layout)

        mBinding.mapView.onCreate(savedInstanceState)
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapError(p0: Int, p1: String?) {}

    override fun onMapReady(mapplsMap: MapplsMap) {


        /* this is done for animating/moving camera to particular position */
        val cameraPosition = CameraPosition.Builder().target(LatLng(
                25.321684, 82.987289)).zoom(10.0).tilt(0.0).build()
        mapplsMap.cameraPosition = cameraPosition

        val scaleBarPlugin = ScaleBarPlugin(mapView, mapplsMap!!)
        val scalebarOptions = ScaleBarOptions(this)
                .setTextColor(android.R.color.black)
                .setTextSize(40f)
                .setBarHeight(5f)
                .setBorderWidth(2f)
                .setRefreshInterval(15)
                .setMarginTop(R.dimen.scalebar_top_margin)
                .setMarginLeft(R.dimen.scalebar_left_margin)
                .setTextBarMargin(15f)
                .setMaxWidthRatio(0.5f)
                .setShowTextBorder(true)
                .setTextBorderWidth(5f)
        scaleBarPlugin.create(scalebarOptions)
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }
}