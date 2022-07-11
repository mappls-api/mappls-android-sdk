package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.gestures.AndroidGesturesManager
import com.mappls.sdk.gestures.MoveGestureDetector
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng

class GesturesActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private var mapplsMap: MapplsMap? = null
    private var androidGesturesManager: AndroidGesturesManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        mapView = findViewById<MapView>(R.id.map_view) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        androidGesturesManager = AndroidGesturesManager(mapView.context, false)
        addTouchGesture()
    }

    private fun addTouchGesture() {

        mapView.setOnTouchListener { v, event ->
            androidGesturesManager?.onTouchEvent(event)
            return@setOnTouchListener false
        }

        androidGesturesManager?.setMoveGestureListener(object : MoveGestureDetector.OnMoveGestureListener {
            override fun onMoveBegin(detector: MoveGestureDetector): Boolean {
                if (mapplsMap != null && detector.pointersCount == 1) {
                    val latLng = mapplsMap?.projection?.fromScreenLocation(detector.focalPoint)
                    Toast.makeText(this@GesturesActivity, "onMoveBegin: $latLng", Toast.LENGTH_SHORT).show()
                }

                return true
            }

            override fun onMove(detector: MoveGestureDetector, distanceX: Float, distanceY: Float): Boolean {
                if (mapplsMap != null && detector.pointersCount == 1) {
                    val latLng = mapplsMap?.projection?.fromScreenLocation(detector.focalPoint)
                    Toast.makeText(this@GesturesActivity, "onMove: $latLng", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onMoveEnd(detector: MoveGestureDetector, velocityX: Float, velocityY: Float) {
                if (mapplsMap != null) {
                    val latLng = mapplsMap?.projection?.fromScreenLocation(detector.focalPoint)
                    Toast.makeText(this@GesturesActivity, "onMoveEnd: $latLng", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onMapError(p0: Int, p1: String?) {}

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap;


        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.0, 77.0), 14.0))
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
