package com.mappls.sdk.demo.activity.widgets.autocomplete

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityAutocompleteFragmentBinding
import com.mappls.sdk.demo.settings.model.AutoCompleteWidgetSetting
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions

class FullModeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityAutocompleteFragmentBinding
    private var mMapplsMap: MapplsMap? = null

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                if (intent != null) {
                    val eLocation = PlaceAutocomplete.getPlace(intent)
                    if (eLocation != null) {
                        if (eLocation.latitude != null && eLocation.longitude != null) {
                            mMapplsMap?.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        eLocation.latitude,
                                        eLocation.longitude
                                    )
                                )
                            )
                            mMapplsMap?.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        eLocation.latitude,
                                        eLocation.longitude
                                    ), 16.0
                                )
                            )
                        } else {
                            mMapplsMap?.addMarker(MarkerOptions().mapplsPin(eLocation.mapplsPin))
                            mMapplsMap?.animateCamera(
                                CameraMapplsPinUpdateFactory.newMapplsPinZoom(
                                    eLocation.mapplsPin,
                                    16.0
                                )
                            )
                        }
                    }
                }
                // Handle the Intent
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityAutocompleteFragmentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.cardFragmentHeader.headerTitle.text = "Full Mode Activity"
        mBinding.cardFragmentHeader.headerBack.setOnClickListener { finish() }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.tvSearch.setOnClickListener {
            val builder = PlaceOptions.builder()
                .userAddedLocationEnable(AutoCompleteWidgetSetting.instance.userAddedLocationEnable)
                .location(AutoCompleteWidgetSetting.instance.location)
                .enableTextSearch(AutoCompleteWidgetSetting.instance.enableTextSearch)
                .backgroundColor(AutoCompleteWidgetSetting.instance.backgroundColor)
                .resultBackgroundColor(AutoCompleteWidgetSetting.instance.resultBackgroundColor)
                .toolbarColor(AutoCompleteWidgetSetting.instance.toolbarColor)
                .logoSize(AutoCompleteWidgetSetting.instance.logoSize)
// Conditionally add nullable properties
            AutoCompleteWidgetSetting.instance.filter?.let {builder.filter(it) }
            AutoCompleteWidgetSetting.instance.zoom?.let { builder.zoom(it) }
            AutoCompleteWidgetSetting.instance.pod?.let { builder.pod(it) }
            AutoCompleteWidgetSetting.instance.tokenizeAddress?.let { builder.tokenizeAddress(it) }
            AutoCompleteWidgetSetting.instance.bridge?.let { builder.bridge(it) }
            val intent = PlaceAutocomplete.IntentBuilder().placeOptions(builder.build()).build(this)
            startActivityForResult.launch(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
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

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    22.553147478403194,
                    77.23388671875
                ), 4.0
            )
        )
        mBinding.searchLayout.visibility = View.VISIBLE

    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}