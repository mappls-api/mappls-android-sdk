package com.mappls.sdk.demo.activity.animation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityMarkerRotationBinding
import com.mappls.sdk.demo.plugin.MarkerPlugin
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds

class MarkerRotationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityMarkerRotationBinding
    private var mMapplsMap: MapplsMap? = null
    private val latLngStart: LatLng = LatLng(28.705436, 77.100462)
    private val latLngEnd: LatLng = LatLng(28.703800, 77.101818)
    private var markerPlugin: MarkerPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMarkerRotationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.markerRotationHeader.headerTitle.setText(R.string.marker_rotation_title)
        mBinding.markerRotationHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.tvMarkerRotation.setOnClickListener {
            markerPlugin?.startRotation()
        }
        mBinding.tvMarkerTransition.setOnClickListener {
            markerPlugin?.startTransition(latLngStart, latLngEnd)
        }
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
            .include(latLngStart)
            .include(latLngEnd)
            .build()

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100))

        mapplsMap.getStyle {
            initMarker()
        }
        mBinding.markerButtonView.visibility = View.VISIBLE
    }

    private fun initMarker() {
        markerPlugin = MarkerPlugin(mMapplsMap!!, mBinding.mapView)
        markerPlugin?.icon = ContextCompat.getDrawable(this, R.drawable.mappls_map_demo_marker)
        markerPlugin?.addMarker(latLngStart)
        markerPlugin?.addTitle("Title")
        markerPlugin?.addDescription("Description")
    }
    override fun onMapError(p0: Int, p1: String?) {

    }
}