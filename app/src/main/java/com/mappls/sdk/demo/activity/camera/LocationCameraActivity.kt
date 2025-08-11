package com.mappls.sdk.demo.activity.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.LocationModeAdapter
import com.mappls.sdk.demo.databinding.ActivityLocationCameraBinding
import com.mappls.sdk.demo.utils.LocationTypeMode
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.location.LocationComponentActivationOptions
import com.mappls.sdk.maps.location.OnCameraTrackingChangedListener
import com.mappls.sdk.maps.location.OnRenderModeChangedListener
import com.mappls.sdk.maps.location.modes.CameraMode
import com.mappls.sdk.maps.location.modes.RenderMode

class LocationCameraActivity : AppCompatActivity(), OnMapReadyCallback, OnCameraTrackingChangedListener, OnRenderModeChangedListener {

    private lateinit var mBinding: ActivityLocationCameraBinding
    private var mMapplsMap: MapplsMap? = null
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ConstraintLayout>
    private lateinit var mAdapter: LocationModeAdapter

    private var callback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                mBinding.scrimView.visibility = View.GONE
            } else {
                mBinding.scrimView.visibility = View.VISIBLE
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityLocationCameraBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.modeBottomSheet) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomInset = maxOf(systemBars.bottom, ime.bottom)

            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                bottomInset
            )
            insets
        }

        mBinding.cameraLocationHeader.headerTitle.text = "Location Camera Option"
        mBinding.cameraLocationHeader.headerBack.setOnClickListener {
            finish()
        }
        bottomSheetBehaviour = BottomSheetBehavior.from(mBinding.modeBottomSheet)
        bottomSheetBehaviour.isHideable = true
        bottomSheetBehaviour.isDraggable = false
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
        mAdapter = LocationModeAdapter(null) { mode, value ->
            if (mMapplsMap?.locationComponent != null) {
                if (mode == LocationTypeMode.LOCATION_CAMERA_MODE) {
                    mMapplsMap?.locationComponent?.cameraMode = value
                } else if (mode == LocationTypeMode.LOCATION_RENDER_MODE) {
                    mMapplsMap?.locationComponent?.renderMode = value
                }
            }
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
            mAdapter.setLocationMode(null)
        }
        bottomSheetBehaviour.addBottomSheetCallback(callback)
        mBinding.rvModeType.adapter = mAdapter
        mBinding.rvModeType.layoutManager = LinearLayoutManager(this)

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.modeSelector.setOnClickListener {
            mBinding.tvBottomSheetTitle.text = "Choose Mode"
            mAdapter.setLocationMode(LocationTypeMode.LOCATION_RENDER_MODE)
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED

        }
        mBinding.trackingSelector.setOnClickListener {
            mBinding.tvBottomSheetTitle.text = "Choose Tracking"
            mAdapter.setLocationMode(LocationTypeMode.LOCATION_CAMERA_MODE)
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED

        }
        mBinding.ivCloseBottomSheet.setOnClickListener {
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
            mAdapter.setLocationMode(null)
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
        mMapplsMap?.locationComponent?.removeOnCameraTrackingChangedListener(this)
        mMapplsMap?.locationComponent?.removeRenderModeChangedListener(this)
        bottomSheetBehaviour.removeBottomSheetCallback(callback)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    22.553147478403194,
                    77.23388671875
                ), 4.0
            )
        )
        mapplsMap.getStyle {
            enableLocationComponent(it)
        }

    }

    private fun enableLocationComponent(style: Style) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, style)
                    .build()
            val locationComponent = mMapplsMap?.locationComponent
            if (locationComponent != null) {
                locationComponent.activateLocationComponent(locationComponentActivationOptions)
                locationComponent.isLocationComponentEnabled = true
                locationComponent.renderMode = RenderMode.NORMAL
                locationComponent.cameraMode = CameraMode.NONE

                mBinding.cameraLocationButtonView.visibility = View.VISIBLE
                setRenderModeText(locationComponent.renderMode)
                setTrackingModeText(locationComponent.cameraMode)
                mMapplsMap?.locationComponent?.addOnCameraTrackingChangedListener(this)
                mMapplsMap?.locationComponent?.addOnRenderModeChangedListener(this)
            }
        }
    }

    private fun setRenderModeText(renderMode: Int) {
        when (renderMode) {
            RenderMode.NORMAL -> {
                mBinding.tvMode.text = "Normal"
            }

            RenderMode.COMPASS -> {
                mBinding.tvMode.text = "Compass"
            }

            RenderMode.GPS -> {
                mBinding.tvMode.text = "GPS"
            }
        }
        mAdapter.setSelectedRenderMode(renderMode)
    }

    private fun setTrackingModeText(cameraMode: Int) {
        when (cameraMode) {
            CameraMode.NONE -> {
                mBinding.tvTracking.text = "None"
            }

            CameraMode.NONE_COMPASS -> {
                mBinding.tvTracking.text = "None Compass"
            }

            CameraMode.NONE_GPS -> {
                mBinding.tvTracking.text = "None GPS"
            }

            CameraMode.TRACKING -> {
                mBinding.tvTracking.text = "Tracking"
            }

            CameraMode.TRACKING_COMPASS -> {
                mBinding.tvTracking.text = "Tracking Compass"
            }

            CameraMode.TRACKING_GPS_NORTH -> {
                mBinding.tvTracking.text = "Tracking GPS North"
            }

            CameraMode.TRACKING_GPS -> {
                mBinding.tvTracking.text = "Tracking GPS"
            }
        }
        mAdapter.setSelectedCameraMode(cameraMode)
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onCameraTrackingDismissed() {

    }

    override fun onCameraTrackingChanged(p0: Int) {
        setTrackingModeText(p0)
    }

    override fun onRenderModeChanged(p0: Int) {
        setRenderModeText(p0)
    }
}