package com.mappls.sdk.demo.activity.polyline

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.PolygonOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R


class PolygonActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mBinding: ActivityBaseMapBinding
    private val listOfLatlang = listOf(
        LatLng(28.703900, 77.101318),
        LatLng(28.703331, 77.102155),
        LatLng(28.703905, 77.102761),
        LatLng(28.704248, 77.102370),
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
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.draw_polygon_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener { finish() }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        /* this is done for move camera focus to particular position */
        val latLngBounds = LatLngBounds.Builder().includes(listOfLatlang).build()
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 70))

        mapplsMap.getStyle {
            if (it.isFullyLoaded) {
                mapplsMap.addPolygon(
                    PolygonOptions().addAll(listOfLatlang).fillColor(
                        "#753bb2d0".toColorInt()
                    )
                )
            }
        }

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