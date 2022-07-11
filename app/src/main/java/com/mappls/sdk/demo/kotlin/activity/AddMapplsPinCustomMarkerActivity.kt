package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.mappls.sdk.demo.R
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.Icon
import com.mappls.sdk.maps.annotations.IconFactory
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory

/**
 * Created by Saksham on 26-11-2020.
 */
class AddMapplsPinCustomMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        mapView = findViewById<MapView>(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapError(p0: Int, p1: String?) {}

    override fun onMapReady(mapplsMap: MapplsMap) {



        var iconFactory = IconFactory.getInstance(this)
        var icon: Icon = iconFactory.fromResource(R.drawable.placeholder)
        mapplsMap.addMarker(MarkerOptions().mapplsPin("MMI000").icon(icon), object : MapplsMap.OnMarkerAddedListener {
            override fun onSuccess() {
                Toast.makeText(this@AddMapplsPinCustomMarkerActivity, "Marker added Successfully", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure() {
                Toast.makeText(this@AddMapplsPinCustomMarkerActivity, "Invalid ELoc", Toast.LENGTH_SHORT).show()
            }

        })

        /* this is done for animating/moving camera to particular position */
        mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 16.0))
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}