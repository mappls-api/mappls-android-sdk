package com.mappls.sdk.demo.activity.marker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.marker.MapplsPinMarkerActivity
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.IconFactory
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinPosition
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng

class MapplsPinCustomMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityBaseMapBinding

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
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.custom_marker_mappls_pin_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener { finish() }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {

        mapplsMap.setCameraMapplsPinPosition(CameraMapplsPinPosition.Builder().target("1T182A").zoom(14.0).build())
        val icon = IconFactory.getInstance(this).fromResource(R.drawable.mappls_map_demo_marker)

        mapplsMap.addMarker(MarkerOptions().mapplsPin("1T182A").icon(icon), object: MapplsMap.OnMarkerAddedListener {
            override fun onSuccess() {
                Toast.makeText(this@MapplsPinCustomMarkerActivity, "Marker Added Successfully",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onFailure() {
                Toast.makeText(this@MapplsPinCustomMarkerActivity, "Invalid Mappls Pin",
                    Toast.LENGTH_SHORT).show()
            }

        })

        /* this is done for animating/moving camera to particular position */
        val cameraPosition = CameraPosition.Builder().target(
            LatLng(
                25.321684, 82.987289
            )
        ).zoom(10.0).build()
        mapplsMap.cameraPosition = cameraPosition

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