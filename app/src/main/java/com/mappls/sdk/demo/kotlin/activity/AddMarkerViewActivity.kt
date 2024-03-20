package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.JsonObject
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.BaseLayoutBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.MapplsMap.OnMapClickListener
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.utils.BitmapUtils
import com.mappls.sdk.plugin.annotation.OnSymbolClickListener
import com.mappls.sdk.plugin.annotation.Symbol
import com.mappls.sdk.plugin.annotation.SymbolManager
import com.mappls.sdk.plugin.annotation.SymbolOptions
import com.mappls.sdk.plugin.markerview.MarkerView
import com.mappls.sdk.plugin.markerview.MarkerViewManager

class AddMarkerViewActivity : AppCompatActivity(), OnMapReadyCallback, OnSymbolClickListener {
    private lateinit var mBinding: BaseLayoutBinding
    private var markerView: MarkerView? = null
    private var markerViewManager: MarkerViewManager? = null
    private var symbolManager: SymbolManager? = null
    private var showInfoWindow: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.base_layout)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
       mapplsMap.getStyle { style->

           // Initialize the MarkerViewManager
           style.addImage(
               "icon_image", BitmapUtils.getBitmapFromDrawable(
                   ContextCompat.getDrawable(
                       this@AddMarkerViewActivity,
                       R.drawable.placeholder
                   )
               )!!
           )
           if (symbolManager == null) {
               symbolManager = SymbolManager(mBinding.mapView, mapplsMap, style)
           }
           val jsonObject = JsonObject()
           jsonObject.addProperty("place_name", "This is Infowindow")
           val symbolOptions = SymbolOptions()
               .position(LatLng(25.321684, 82.987289))
               .icon("icon_image")
               .data(jsonObject)
               .iconSize(1f)
           symbolManager?.iconAllowOverlap = true
           symbolManager?.iconIgnorePlacement = true
           symbolManager?.create(symbolOptions)
           symbolManager?.addClickListener(this)
           markerViewManager = MarkerViewManager(mBinding.mapView, mapplsMap)

           mapplsMap.addOnMapClickListener(OnMapClickListener {
               // Hide the custom layout when the map is clicked
               if (showInfoWindow) {
                   if (markerView != null) {
                       markerViewManager?.removeMarker(markerView!!)
                   }
                   showInfoWindow = false
                   return@OnMapClickListener true // Consume the event
               }
               false // Allow the event to propagate
           })
           val cameraPosition = CameraPosition.Builder().target(
               LatLng(
                   25.321684, 82.987289
               )
           ).zoom(8.0).tilt(0.0).build()
           mapplsMap.cameraPosition = cameraPosition

       }
    }

    override fun onMapError(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun onAnnotationClick(symbol: Symbol?): Boolean {

        val customView = LayoutInflater.from(this).inflate(
                R.layout.marker_view_bubble, null);
//        customView.setVisibility(View.GONE);
        if (!showInfoWindow) {
            showInfoWindow = true
            customView!!.layoutParams =
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

            // Set the View's TextViews with content
            val titleTextView = customView.findViewById<TextView>(R.id.marker_window_title)
            if(symbol?.data != null && symbol.data!!.asJsonObject.has("place_name")) {
                titleTextView.text = symbol.data!!.asJsonObject?.getAsJsonPrimitive("place_name")?.asString
            }
            val button1 = customView.findViewById<Button>(R.id.marker_window_button)
            val button2 = customView.findViewById<Button>(R.id.marker_window_button1)
            val button3 = customView.findViewById<Button>(R.id.marker_window_button2)

            button1.setOnClickListener {
                Toast.makeText(
                    this@AddMarkerViewActivity,
                    "Button 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
            button2.setOnClickListener {
                Toast.makeText(
                    this@AddMarkerViewActivity,
                    "Button 2",
                    Toast.LENGTH_SHORT
                ).show()
            }
            button3.setOnClickListener {
                Toast.makeText(
                    this@AddMarkerViewActivity,
                    "Button 3",
                    Toast.LENGTH_SHORT
                ).show()
            }
            markerView = MarkerView(symbol?.position!!, customView!!)
            markerViewManager!!.addMarker(markerView!!)
        } else {
            markerViewManager!!.removeMarker(markerView!!)
            showInfoWindow = false
        }
        return true
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
        if (markerViewManager != null) {
            markerViewManager!!.onDestroy()
        }
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