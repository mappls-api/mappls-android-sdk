package com.mappls.sdk.demo.activity.marker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.utils.BitmapUtils
import com.mappls.sdk.plugin.annotation.OnSymbolDragListener
import com.mappls.sdk.plugin.annotation.Symbol
import com.mappls.sdk.plugin.annotation.SymbolManager
import com.mappls.sdk.plugin.annotation.SymbolOptions

class MarkerDraggingActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityBaseMapBinding
    private val latLng = LatLng(28.705436, 77.100462)
    private var symbolManager: SymbolManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityBaseMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.baseMapHeader.headerTitle.text = getString(R.string.marker_dragging_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0))
        initMarker(mapplsMap)
    }

    private fun initMarker(mapplsMap: MapplsMap) {
        mapplsMap.getStyle {
            symbolManager = SymbolManager(mBinding.mapView, mapplsMap, it)
            val symbolOptions = SymbolOptions()
                .icon(
                    BitmapUtils.getBitmapFromDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.mappls_map_demo_marker
                        )
                    )
                )
                .draggable(true)
                .position(latLng)
            symbolManager?.iconAllowOverlap = true
            symbolManager?.iconIgnorePlacement = false
            symbolManager?.create(symbolOptions)
            symbolManager?.addDragListener(object : OnSymbolDragListener {
                override fun onAnnotationDragStarted(p0: Symbol?) {

                }

                override fun onAnnotationDrag(p0: Symbol?) {

                }

                override fun onAnnotationDragFinished(symbol: Symbol?) {
                    Toast.makeText(
                        this@MarkerDraggingActivity,
                        symbol?.position.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

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