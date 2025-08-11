package com.mappls.sdk.demo.activity.animation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.demo.plugin.AnimatedCarPlugin
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.PolylineOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import androidx.core.graphics.toColorInt

class CarAnimationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityBaseMapBinding
    private var mMapplsMap: MapplsMap? = null
    private var animatedCarPlugin: AnimatedCarPlugin? = null
    private var index: Int = 0
    private val listOfLatlang = listOf(
        LatLng(28.705436, 77.100462),
        LatLng(28.705191, 77.100784),
        LatLng(28.704646, 77.101514),
        LatLng(28.704194, 77.101171),
        LatLng(28.704083, 77.101066),
        LatLng(28.703900, 77.101318),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityBaseMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.baseMapHeader.headerTitle.setText(R.string.animate_car_title)
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
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
        val latLngBounds: LatLngBounds = LatLngBounds.Builder()
            .includes(listOfLatlang)
            .build()

        mapplsMap.getStyle {
            animatedCarPlugin = AnimatedCarPlugin(applicationContext, mBinding.mapView, mapplsMap)
            animatedCarPlugin?.addMainCar(listOfLatlang[index], true)
            animatedCarPlugin?.animateCar()
            animatedCarPlugin?.setOnUpdateNextPoint(object : AnimatedCarPlugin.OnUpdatePoint {
                override fun updateNextPoint() {
                    if (index < listOfLatlang.size - 1) index += 1

                    animatedCarPlugin?.updateNextPoint(listOfLatlang[index])
                    animatedCarPlugin?.animateCar()
                }
            })
        }
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100))
        mapplsMap.addPolyline(PolylineOptions().addAll(listOfLatlang).color("#3bb2d0".toColorInt()).width(4f))
    }

    override fun onMapError(code: Int, message: String?) {
        Toast.makeText(this, "$code --- $message", Toast.LENGTH_SHORT).show()
    }
}