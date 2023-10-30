package com.mappls.sdk.demo.kotlin.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityCarAnimationBinding
import com.mappls.sdk.demo.kotlin.plugin.AnimatedCarPlugin
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.PolylineOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds

/**
 * Created by Saksham on 19/9/19.
 */
class CarAnimationActivity: AppCompatActivity(), OnMapReadyCallback {

    private val listOfLatlang: MutableList<LatLng> = mutableListOf()
    private var animatedCarPlugin: AnimatedCarPlugin? = null
    private var index: Int = 0
    private lateinit var mBinding: ActivityCarAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_car_animation)

        listOfLatlang.add(LatLng(28.705436, 77.100462))
        listOfLatlang.add(LatLng(28.705191, 77.100784))
        listOfLatlang.add(LatLng(28.704646, 77.101514))
        listOfLatlang.add(LatLng(28.704194, 77.101171))
        listOfLatlang.add(LatLng(28.704083, 77.101066))
        listOfLatlang.add(LatLng(28.703900, 77.101318))

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {

        val latLngBounds: LatLngBounds = LatLngBounds.Builder()
                .includes(listOfLatlang)
                .build()

        mapplsMap.getStyle {
            animatedCarPlugin = AnimatedCarPlugin(applicationContext, mBinding.mapView, mapplsMap)
            animatedCarPlugin!!.addMainCar(listOfLatlang[index], true)
            animatedCarPlugin!!.animateCar()
            animatedCarPlugin!!.setOnUpdateNextPoint(object : AnimatedCarPlugin.OnUpdatePoint {
                override fun updateNextPoint() {
                    if (index < listOfLatlang.size - 1) index += 1

                    animatedCarPlugin!!.updateNextPoint(listOfLatlang[index])
                    animatedCarPlugin!!.animateCar()
                }


            })
        }
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100))
        mapplsMap.addPolyline(PolylineOptions().addAll(listOfLatlang).color(Color.parseColor("#3bb2d0")).width(4f))
    }

    override fun onMapError(i: Int, s: String?) {

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
        if (animatedCarPlugin != null)
            animatedCarPlugin!!.clearAllCallBacks()
        mBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (animatedCarPlugin != null)
            animatedCarPlugin!!.addAllCallBacks()
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