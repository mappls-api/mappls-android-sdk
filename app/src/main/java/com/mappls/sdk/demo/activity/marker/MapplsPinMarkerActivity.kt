package com.mappls.sdk.demo.activity.marker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityMapplsPinMarkerBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng

class MapplsPinMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityMapplsPinMarkerBinding
    private var mMapplsMap: MapplsMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMapplsPinMarkerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.mapplsPinMarkerHeader.headerTitle.text = getString(R.string.marker_mappls_pin_title)
        mBinding.mapplsPinMarkerHeader.ivSettings.visibility = View.GONE
        mBinding.mapplsPinMarkerHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.btnAddMarker.setOnClickListener {
            val mapplsPins = mBinding.mapplsPinEt.text.toString().trim()
            if(!mapplsPins.isEmpty()) {
                val mapplsPinList = mapplsPins.split(",")
                addMarkers(mapplsPinList)
            } else {
                Toast.makeText(this, "Please enter Mappls Pin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addMarkers(mapplsPinList: List<String>) {
        mMapplsMap?.clear()
        if(mapplsPinList.isNotEmpty()) {
            if(mapplsPinList.size == 1) {
                mMapplsMap?.addMarker(MarkerOptions().mapplsPin(mapplsPinList.first()), object: MapplsMap.OnMarkerAddedListener {
                    override fun onSuccess() {
                        Toast.makeText(this@MapplsPinMarkerActivity, "Marker Added Successfully",
                            Toast.LENGTH_SHORT).show()
                        mMapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom(mapplsPinList.first(), 14.0))
                    }

                    override fun onFailure() {
                        Toast.makeText(this@MapplsPinMarkerActivity, "Invalid Mappls Pin",
                            Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                val markerOptions = mapplsPinList.map { MarkerOptions().mapplsPin(it) }
                mMapplsMap?.addMarkers(markerOptions, object: MapplsMap.OnMarkerAddedListener {
                    override fun onSuccess() {
                        Toast.makeText(this@MapplsPinMarkerActivity, "Marker Added Successfully",
                            Toast.LENGTH_SHORT).show()
                        mMapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPinList, 10, 100, 10, 10))
                    }

                    override fun onFailure() {
                        Toast.makeText(this@MapplsPinMarkerActivity, "Invalid Mappls Pin",
                            Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.0, 77.0), 4.0))
        mBinding.addMapplsPinMarkerLayout.visibility = View.VISIBLE
    }

    override fun onMapError(p0: Int, p1: String?) {

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
        mBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
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