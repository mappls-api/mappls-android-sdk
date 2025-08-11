package com.mappls.sdk.demo.activity.marker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.demo.databinding.CustomInfoWindowLayoutBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.MapplsMap.InfoWindowAdapter
import com.mappls.sdk.maps.MapplsMap.OnMarkerClickListener
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds


class AddCustomInfoWindowActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityBaseMapBinding
    private val latLngList = listOf(
        LatLng(25.321684, 82.987289),
        LatLng(25.331684, 82.997289),
        LatLng(25.321684, 82.887289),
        LatLng(25.311684, 82.987289)
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
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.add_custom_info_window_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener { finish() }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        for (latLng in latLngList) {
            mapplsMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("XYZ")
            )
        }

        mapplsMap.setInfoWindowAdapter(InfoWindowAdapter {
            val infoWindowBinding: CustomInfoWindowLayoutBinding = CustomInfoWindowLayoutBinding.inflate(LayoutInflater.from(this@AddCustomInfoWindowActivity), null, false)

            infoWindowBinding.text.text = "MapmyIndia Head Office, 237, Okhla"
            infoWindowBinding.root
        })

        mapplsMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->
            Toast.makeText(
                this@AddCustomInfoWindowActivity,
                marker.position.toString(),
                Toast.LENGTH_SHORT
            ).show()
            false
        })
        val latLngBounds = LatLngBounds.Builder()
            .includes(latLngList)
            .build()

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100, 10, 100, 10))
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}