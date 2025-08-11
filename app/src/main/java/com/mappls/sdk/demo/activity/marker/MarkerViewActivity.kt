package com.mappls.sdk.demo.activity.marker

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.JsonObject
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityMarkerViewBinding
import com.mappls.sdk.demo.databinding.MarkerViewBubbleBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.MapplsMap.OnMapClickListener
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.utils.BitmapUtils
import com.mappls.sdk.plugin.annotation.OnSymbolClickListener
import com.mappls.sdk.plugin.annotation.Symbol
import com.mappls.sdk.plugin.annotation.SymbolManager
import com.mappls.sdk.plugin.annotation.SymbolOptions
import com.mappls.sdk.plugin.markerview.MarkerView
import com.mappls.sdk.plugin.markerview.MarkerViewManager


class MarkerViewActivity : AppCompatActivity(), OnMapReadyCallback, OnSymbolClickListener {
    lateinit var mBinding:ActivityMarkerViewBinding
    private var markerView: MarkerView? = null
    private var markerViewManager: MarkerViewManager? = null
    private var symbolManager: SymbolManager? = null
    private var showInfowindow = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMarkerViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.topHeader.headerTitle.text = getString(R.string.marker_view_title)
        mBinding.topHeader.ivSettings.visibility = View.GONE
        mBinding.topHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)


    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mapplsMap.getStyle(object : Style.OnStyleLoaded {
            override fun onStyleLoaded(style: Style) {
                // Initialize the MarkerViewManager
                BitmapUtils.getBitmapFromDrawable(
                    ContextCompat.getDrawable(
                        this@MarkerViewActivity,
                        R.drawable.mappls_map_demo_marker
                    )
                )?.let {
                    style.addImage(
                        "icon_image", it
                    )
                }
                if (symbolManager == null) {
                    symbolManager = SymbolManager(mBinding.mapView, mapplsMap, style)
                }
                val jsonObject = JsonObject()
                jsonObject.addProperty("place_name", "This is Infowindow")
                val symbolOptions: SymbolOptions = SymbolOptions()
                    .position(LatLng(25.321684, 82.987289))
                    .icon("icon_image")
                    .data(jsonObject)
                    .iconSize(1f)
                symbolManager?.iconAllowOverlap = true
                symbolManager?.iconIgnorePlacement = true
                symbolManager?.create(symbolOptions)
                symbolManager?.addClickListener(this@MarkerViewActivity)
                markerViewManager = MarkerViewManager(mBinding.mapView, mapplsMap)


                mapplsMap.addOnMapClickListener(OnMapClickListener {
                    // Hide the custom layout when the map is clicked
                    if (showInfowindow) {
                        markerView?.let { it1 -> markerViewManager!!.removeMarker(it1) }
                        return@OnMapClickListener true // Consume the event
                    }
                    false // Allow the event to propagate
                })
                val cameraPosition = CameraPosition.Builder().target(
                    LatLng(
                        25.321684, 82.987289
                    )
                ).zoom(8.0).tilt(0.0).build()
                mapplsMap.setCameraPosition(cameraPosition)
            }
        })
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
        if (markerViewManager != null) {
            markerViewManager?.onDestroy()
        }
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


    override fun onAnnotationClick(symbol: Symbol?): Boolean {

        if (!showInfowindow) {
            showInfowindow = true
            val infoWindowBinding = MarkerViewBubbleBinding.inflate(layoutInflater, null, false)

            infoWindowBinding.root.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            // Set the View's TextViews with content

            if (symbol?.data != null && symbol.data!!.getAsJsonObject()
                    .has("place_name")
            ) {
                infoWindowBinding.markerWindowTitle.text = symbol.data!!.getAsJsonObject().getAsJsonPrimitive("place_name")
                    .getAsString()
            }

            infoWindowBinding.markerWindowButton.setOnClickListener {
                Toast.makeText(
                    this@MarkerViewActivity,
                    "Button 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
            infoWindowBinding.markerWindowButton1.setOnClickListener {
                Toast.makeText(
                    this@MarkerViewActivity,
                    "Button 2",
                    Toast.LENGTH_SHORT
                ).show()
            }
            infoWindowBinding.markerWindowButton2.setOnClickListener {
                Toast.makeText(
                    this@MarkerViewActivity,
                    "Button 3",
                    Toast.LENGTH_SHORT
                ).show()
            }
            markerView = symbol?.position?.let { MarkerView(it, infoWindowBinding.root) }
            markerView?.setOnPositionUpdateListener { pointF ->

                return@setOnPositionUpdateListener (PointF(pointF.x - 320, pointF.y - 400))
            }
            markerViewManager?.addMarker(markerView!!)
        } else {
            markerView?.let { markerViewManager?.removeMarker(it) }
            showInfowindow = false
        }
        return true
    }
}