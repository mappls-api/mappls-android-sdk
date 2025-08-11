package com.mappls.sdk.demo.activity.camera

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.databinding.ActivityCameraBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng

class CameraActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityCameraBinding
    private var mMapplsMap: MapplsMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.cameraFeatureHeader.headerTitle.setText("Camera Feature")
        mBinding.cameraFeatureHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.tvCameraMove.setOnClickListener {
            mMapplsMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.567924, 77.238542), 16.0))
        }
        mBinding.tvCameraEase.setOnClickListener {
            mMapplsMap?.easeCamera(CameraUpdateFactory.newLatLngZoom(LatLng(29.012193, 76.958998), 16.0), 8000)
        }
        mBinding.tvCameraAnimate.setOnClickListener {
            mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(24.530866, 86.620040), 16.0), 8000)
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
        this.mMapplsMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.613426, 77.231774), 16.0))
        mBinding.cameraButtonView.visibility = View.VISIBLE
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}