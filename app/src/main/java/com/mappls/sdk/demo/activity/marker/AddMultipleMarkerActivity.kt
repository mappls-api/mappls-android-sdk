package com.mappls.sdk.demo.activity.marker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.maps.utils.BitmapUtils
import com.mappls.sdk.plugin.annotation.Symbol
import com.mappls.sdk.plugin.annotation.SymbolManager
import com.mappls.sdk.plugin.annotation.SymbolOptions

class AddMultipleMarkerActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityBaseMapBinding
    private val latLngList: MutableList<LatLng> = ArrayList()

    private var symbolManager: SymbolManager? = null
    private var highlightedSymbolManager: SymbolManager? = null
    private var lastSymbolClicked: Symbol? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBaseMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.baseMapHeader.headerTitle.text = "Multiple Marker"
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

        latLngList.add(LatLng(12.91897, 77.69652))
        latLngList.add(LatLng(12.91897, 77.6967))
        latLngList.add(LatLng(12.91898, 77.69669))
        latLngList.add(LatLng(12.91898, 77.69669))

        latLngList.add(LatLng(12.918989999999999, 77.69662000000001))

        latLngList.add(
            LatLng(
                12.918989999999999,
                77.69662000000001
            )
        )
        latLngList.add(
            LatLng(
                12.918999999999999,
                77.6967
            )
        )
        latLngList.add(
            LatLng(
                12.918999999999999,
                77.6967
            )
        )
        latLngList.add(
            LatLng(
                12.91908,
                77.69679000000001
            )
        )
        latLngList.add(
            LatLng(
                12.91908,
                77.69679000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92023,
                77.69615
            )
        )
        latLngList.add(
            LatLng(
                12.9199,
                77.69612000000001
            )
        )
        latLngList.add(
            LatLng(
                12.91982,
                77.69648000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92053,
                77.69581000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92053,
                77.69581000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92164,
                77.69559000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92164,
                77.69559000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92234,
                77.69524000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92234,
                77.69524000000001
            )
        )
        latLngList.add(
            LatLng(
                12.92286,
                77.69522
            )
        )
        latLngList.add(
            LatLng(
                12.92286,
                77.69522
            )
        )
        latLngList.add(
            LatLng(
                12.9231,
                77.69519000000001
            )
        )
        latLngList.add(
            LatLng(
                12.9231,
                77.69519000000001
            )
        )
        latLngList.add(
            LatLng(
                12.923399999999999,
                77.69518000000001
            )
        )
        latLngList.add(
            LatLng(
                12.923119999999999,
                77.69512
            )
        )
        latLngList.add(
            LatLng(
                12.923119999999999,
                77.69512
            )
        )
        latLngList.add(
            LatLng(
                12.92307,
                77.69521
            )
        )
        latLngList.add(
            LatLng(
                12.923179999999999,
                77.69522
            )
        )
        latLngList.add(
            LatLng(
                12.923179999999999,
                77.69522
            )
        )
//
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mapplsMap.getStyle { style ->
            style.addImage(
                "icon_image", BitmapUtils.getBitmapFromDrawable(
                    ContextCompat.getDrawable(
                        this@AddMultipleMarkerActivity,
                        R.drawable.mappls_map_demo_marker
                    )
                )!!
            )
            style.addImage(
                "highlighted_image", BitmapUtils.getBitmapFromDrawable(
                    ContextCompat.getDrawable(
                        this@AddMultipleMarkerActivity,
                        R.drawable.map_marker_icon
                    )
                )!!
            )
            if (symbolManager == null) {
                symbolManager = SymbolManager(mBinding.mapView, mapplsMap, style)
            }
            if (highlightedSymbolManager == null) {
                highlightedSymbolManager = SymbolManager(mBinding.mapView, mapplsMap, style)
            }
            latLngList.forEach {
                val symbolOptions = SymbolOptions()
                    .position(it)
                    .icon("icon_image")
                    .iconSize(1f)
                symbolManager?.create(symbolOptions)

            }
            symbolManager?.iconIgnorePlacement = false
            symbolManager?.iconAllowOverlap = true

            symbolManager?.addClickListener {
                highlightedSymbolManager?.clearAll()
                val options = SymbolOptions()
                    .position(it.position)
                    .icon("highlighted_image")
                highlightedSymbolManager?.create(options)


                lastSymbolClicked = it
                return@addClickListener true
            }
        }


        val latLngBounds: LatLngBounds = LatLngBounds.Builder()
            .includes(latLngList)
            .build()

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 30))
    }

    override fun onMapError(p0: Int, p1: String?) {

    }


    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
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
        mBinding.mapView.onDestroy()
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