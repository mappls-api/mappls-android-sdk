package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.StyleActivityBinding
import com.mappls.sdk.demo.java.adapter.StyleAdapter
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.MapplsMapConfiguration
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.style.OnStyleLoadListener
import com.mappls.sdk.maps.style.model.MapplsStyle
import timber.log.Timber


class StyleActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: StyleActivityBinding
    private lateinit var adapter: StyleAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    private var mapplsMap: MapplsMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.style_activity)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

        bottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.peekHeight = 200
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        mBinding.rvStyle.layoutManager = LinearLayoutManager(this)
        adapter = StyleAdapter()
        mBinding.rvStyle.adapter = adapter
        mBinding.saveLastStyle.isChecked = MapplsMapConfiguration.getInstance().isShowLastSelectedStyle


        mBinding.saveLastStyle.setOnCheckedChangeListener { _, isChecked -> MapplsMapConfiguration.getInstance().isShowLastSelectedStyle = isChecked }

        adapter.setOnStyleSelectListener { style: MapplsStyle ->

            mapplsMap?.setMapplsStyle(style.name, object : OnStyleLoadListener {
                override fun onError(error: String) {
                    Toast.makeText(this@StyleActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onStyleLoaded(style: Style) {
                    Toast.makeText(this@StyleActivity, "onStyleLoaded", Toast.LENGTH_SHORT).show()
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            })

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

    override fun onMapError(p0: Int, p1: String?) {
        Timber.tag("onMapError").e("$p0------$p1")
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        Timber.tag("onMapReady").e("SUCCESS")
        this.mapplsMap = mapplsMap
        mapplsMap.uiSettings?.setLogoMargins(0, 0, 0, 200)
        this.mapplsMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.6466772, 76.8130614), 12.0))
            Timber.e(Gson().toJson(this.mapplsMap?.mapplsAvailableStyles))
            adapter.setStyleList(this.mapplsMap?.mapplsAvailableStyles)
    }
}