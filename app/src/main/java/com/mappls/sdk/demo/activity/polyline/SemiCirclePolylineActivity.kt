package com.mappls.sdk.demo.activity.polyline

import android.R.style
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.demo.utils.SemiCirclePointsListHelper.showCurvedPolyline
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.maps.style.sources.GeoJsonOptions
import com.mappls.sdk.plugin.annotation.LineManager
import com.mappls.sdk.plugin.annotation.LineOptions


class SemiCirclePolylineActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mBinding: ActivityBaseMapBinding
    private var listOfLatLng = ArrayList<LatLng>()

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
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.semicircle_polyline_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener { finish() }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        listOfLatLng = showCurvedPolyline(LatLng(28.7039, 77.101318),
                LatLng(28.704248, 77.102370), 0.5) as ArrayList<LatLng>
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        val latLngBounds = LatLngBounds.Builder()
            .includes(listOfLatLng)
            .build()

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100))
        mapplsMap.getStyle {
            if(it.isFullyLoaded){
                val lineManager = LineManager(
                    mBinding.mapView,
                    mapplsMap,
                    it,
                    GeoJsonOptions().withLineMetrics(true).withBuffer(2)
                )
                val lineOptions = LineOptions()
                    .points(listOfLatLng)
                    .lineColor("#FF0000")
                    .lineWidth(4f)

                lineManager.lineDasharray = arrayOf<Float>(4f, 6f)
                lineManager.create(lineOptions)
            }
        }
    }

    override fun onMapError(p0: Int, p1: String?) {
        TODO("Not yet implemented")
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