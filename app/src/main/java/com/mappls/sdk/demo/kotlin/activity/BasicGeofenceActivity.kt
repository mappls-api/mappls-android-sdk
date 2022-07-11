package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.LayoutBasicGeofenceBinding
import com.mappls.sdk.demo.kotlin.settings.MapplsGeofenceSetting
import com.mappls.sdk.geofence.ui.GeoFence
import com.mappls.sdk.geofence.ui.GeoFenceType
import com.mappls.sdk.geofence.ui.listeners.GeoFenceViewCallback
import com.mappls.sdk.geofence.ui.views.GeoFenceOptions
import com.mappls.sdk.geofence.ui.views.GeoFenceView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.geometry.LatLng

class BasicGeofenceActivity: AppCompatActivity(), GeoFenceViewCallback {

    private lateinit  var mBinding: LayoutBasicGeofenceBinding
    private lateinit var geoFence: GeoFence
    private lateinit var geofenceView: GeoFenceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         mBinding = DataBindingUtil.setContentView(this, R.layout.layout_basic_geofence)

        mBinding = DataBindingUtil.setContentView(this, R.layout.layout_basic_geofence)
        geoFence = GeoFence()
         if (MapplsGeofenceSetting.instance.isDefault){
            geofenceView = GeoFenceView(this);
             geoFence.geoFenceType = GeoFenceType.CIRCLE
             geoFence.circleCenter= LatLng(24.6496185, 77.3062072)
            geoFence.circleRadius=200

        }else {
           val geoFenceOptions = GeoFenceOptions.builder();
            geoFenceOptions.circleOutlineWidth(MapplsGeofenceSetting.instance.circleOutlineWidth);
            geoFenceOptions.circleFillColor(MapplsGeofenceSetting.instance.circleFillColor);
            geoFenceOptions.circleFillOutlineColor(MapplsGeofenceSetting.instance.circleFillOutlineColor);
            geoFenceOptions.draggingLineColor(MapplsGeofenceSetting.instance.draggingLineColor);
            geoFenceOptions.maxRadius(MapplsGeofenceSetting.instance.maxRadius);
            geoFenceOptions.minRadius(MapplsGeofenceSetting.instance.minRadius);
            geoFenceOptions.polygonDrawingLineColor(MapplsGeofenceSetting.instance.polygonDrawingLineColor);
            geoFenceOptions.polygonFillColor(MapplsGeofenceSetting.instance.polygonFillColor);
            geoFenceOptions.polygonFillOutlineColor(MapplsGeofenceSetting.instance.polygonFillColor);
            geoFenceOptions.polygonOutlineWidth(MapplsGeofenceSetting.instance.polygonOutlineWidth);
             geoFenceOptions.showSeekBar(MapplsGeofenceSetting.instance.showSeekBar)
             geoFenceOptions.seekbarPrimaryColor(MapplsGeofenceSetting.instance.seekbarPrimaryColor)
             geoFenceOptions.seekbarSecondaryColor(MapplsGeofenceSetting.instance.seekbarSecondaryColor)
             geoFenceOptions.seekbarCornerRadius(MapplsGeofenceSetting.instance.seekbarCornerRadius)

            geofenceView =  GeoFenceView(this, geoFenceOptions.build())
             geoFence.geoFenceType = if(MapplsGeofenceSetting.instance.isPolygon) GeoFenceType.POLYGON else GeoFenceType.CIRCLE
             geoFence.circleCenter=LatLng(24.6496185, 77.3062072)
             geoFence.circleRadius=200

            geofenceView.simplifyWhenIntersectingPolygonDetected(MapplsGeofenceSetting.instance.isSimplifyWhenIntersectingPolygonDetected);

        }



        geofenceView.geoFence = geoFence
        geofenceView.onCreate(savedInstanceState)
        mBinding.rootLayout.addView(geofenceView)
        geofenceView.setGeoFenceViewCallback(this)

    }


    override fun onStart() {
        super.onStart()
        geofenceView.onStart()
    }

    override fun onResume() {
        super.onResume()
        geofenceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        geofenceView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        geofenceView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        geofenceView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        geofenceView.onDestroy()
    }

    override fun onCircleRadiusChanging(p0: Int) {

    }

    override fun onUpdateGeoFence(p0: GeoFence?) {

    }

    override fun onGeoFenceReady(p0: MapplsMap) {

    }

    override fun hasIntersectionPoints() {

    }

    override fun geoFenceType(p0: GeoFenceType) {

    }


}