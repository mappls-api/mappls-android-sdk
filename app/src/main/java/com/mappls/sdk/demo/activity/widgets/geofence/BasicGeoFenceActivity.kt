package com.mappls.sdk.demo.activity.widgets.geofence

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.databinding.ActivityBasicGeoFenceBinding
import com.mappls.sdk.demo.settings.model.MapplsGeofenceSetting
import com.mappls.sdk.geofence.ui.GeoFence
import com.mappls.sdk.geofence.ui.GeoFenceType
import com.mappls.sdk.geofence.ui.views.GeoFenceOptions
import com.mappls.sdk.geofence.ui.views.GeoFenceView
import com.mappls.sdk.maps.geometry.LatLng


class BasicGeoFenceActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityBasicGeoFenceBinding
    private var mGeoFenceView: GeoFenceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityBasicGeoFenceBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.basicGeoFenceHeader.headerTitle.text = "Basic Geofence"
        mBinding.basicGeoFenceHeader.headerBack.setOnClickListener {
            finish()
        }

        val options = GeoFenceOptions.builder()
        .circleOutlineWidth(MapplsGeofenceSetting.instance.circleOutlineWidth)
        .circleFillColor(MapplsGeofenceSetting.instance.circleFillColor)
        .circleFillOutlineColor(MapplsGeofenceSetting.instance.circleFillOutlineColor)
        .draggingLineColor(MapplsGeofenceSetting.instance.draggingLineColor)
        .maxRadius(MapplsGeofenceSetting.instance.maxRadius)
        .minRadius(MapplsGeofenceSetting.instance.minRadius)
        .polygonDrawingLineColor(MapplsGeofenceSetting.instance.polygonDrawingLineColor)
        .polygonFillColor(MapplsGeofenceSetting.instance.polygonFillColor)
        .polygonFillOutlineColor(MapplsGeofenceSetting.instance.polygonFillOutlineColor)
        .polygonOutlineWidth(MapplsGeofenceSetting.instance.polygonOutlineWidth)
        .showSeekBar(MapplsGeofenceSetting.instance.showSeekBar)
        .seekbarPrimaryColor(MapplsGeofenceSetting.instance.seekbarPrimaryColor)
        .seekbarSecondaryColor(MapplsGeofenceSetting.instance.seekbarSecondaryColor)
        .seekbarCornerRadius(MapplsGeofenceSetting.instance.seekbarCornerRadius)
        .build()
        mGeoFenceView = GeoFenceView(this, options)
        mBinding.baseLayout.addView(mGeoFenceView)
        mGeoFenceView?.onCreate(savedInstanceState)
        val geoFence = GeoFence()
        geoFence.geoFenceType = GeoFenceType.CIRCLE
        geoFence.circleCenter = LatLng(24.6496185, 77.3062072)
        geoFence.circleRadius = 200
        mGeoFenceView?.geoFence = geoFence

    }

    override fun onStart() {
        super.onStart()
        mGeoFenceView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mGeoFenceView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mGeoFenceView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mGeoFenceView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mGeoFenceView?.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mGeoFenceView?.onDestroy()
    }

}