package com.mappls.sdk.demo.activity.layers

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.GeoAnalyticsAdapter
import com.mappls.sdk.demo.databinding.ActivityGeoAnalyticsBinding
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsPlugin
import com.mappls.sdk.geoanalytics.listing.MapplsGeoAnalyticsList
import com.mappls.sdk.geoanalytics.listing.MapplsGeoAnalyticsListManager
import com.mappls.sdk.geoanalytics.listing.model.GeoAnalyticsListResponse
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback

class GeoAnalyticsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityGeoAnalyticsBinding
    private var geoAnalyticsPlugin: MapplsGeoAnalyticsPlugin? = null
    private var mMapplsMap: MapplsMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityGeoAnalyticsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        mBinding.geoAnalyticsHeader.headerTitle.text = getString(R.string.geo_analytics_title)
        mBinding.geoAnalyticsHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        val bottomSheetBehaviour = BottomSheetBehavior.from(mBinding.geoAnalyticsBottomSheet)
        bottomSheetBehaviour.isHideable = false
        bottomSheetBehaviour.peekHeight = 200
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initData() {
        mBinding.rvGeoAnalyticsList.layoutManager = LinearLayoutManager(this)
        val adapter = GeoAnalyticsAdapter(Utils.getGeoAnalyticsList()) { model, isShow ->
            if (isShow) {
                geoAnalyticsPlugin?.showGeoAnalytics(
                    model.geoAnalticsType,
                    model.geoAnaltyticsParams
                )
            } else {
                geoAnalyticsPlugin?.removeGeoAnalytics(model.geoAnalticsType)
            }

            if (isShow) {
                val mapplsGeoAnalyticsList = MapplsGeoAnalyticsList.builder()
                    .api(model.geoAnalticsType.name)
                    .attributes("b_box")
                    .geoBound(*model.geoBound)
                    .geoBoundType(model.geoBoundType)
                    .build()
                MapplsGeoAnalyticsListManager.newInstance(mapplsGeoAnalyticsList)
                    .call(object : OnResponseCallback<GeoAnalyticsListResponse> {
                        override fun onSuccess(geoAnalyticsResponse: GeoAnalyticsListResponse?) {
                            geoAnalyticsResponse?.let {
                                handleResponse(it)
                            } ?: run {
                                Toast.makeText(
                                    this@GeoAnalyticsActivity,
                                    "No Result",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onError(p0: Int, p1: String?) {
                            Toast.makeText(
                                this@GeoAnalyticsActivity,
                                "$p0 --- $p1",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
            }
        }
        mBinding.rvGeoAnalyticsList.adapter = adapter
    }

    private fun handleResponse(response: GeoAnalyticsListResponse) {
        if ((response.results?.getAttrValues?.size ?: 0) > 0) {
            val latLngList: MutableList<LatLng> = ArrayList()
            response.results.getAttrValues.forEach { value ->
                value.getAttrValues?.forEach { map->
                    val bBox = map["b_box"] as String?
                    val truncateBox =
                        bBox?.substring(4, bBox.length - 1)
                    val start =
                        truncateBox?.split(",".toRegex())?.toTypedArray()[0]
                    val last =
                        truncateBox?.split(",".toRegex())?.toTypedArray()[1]
                    if(start != null) {
                        latLngList.add(
                            LatLng(
                                start.split(" ".toRegex())
                                    .toTypedArray()[1].toDouble(),
                                start.split(" ".toRegex())
                                    .toTypedArray()[0].toDouble()
                            )
                        )
                    }
                    if(last != null) {
                        latLngList.add(
                            LatLng(
                                last.split(" ".toRegex())
                                    .toTypedArray()[1].toDouble(),
                                last.split(" ".toRegex())
                                    .toTypedArray()[0].toDouble()
                            )
                        )
                    }
                }

            }
            if(!mBinding.mapView.isDestroyed) {
                mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(
                    LatLngBounds.fromLatLngs(latLngList), 12, 12, 12, 36
                ))
            }
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
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.0, 77.0), 4.0))
        geoAnalyticsPlugin = MapplsGeoAnalyticsPlugin(mBinding.mapView, mapplsMap)
        initData()
    }

    override fun onMapError(code: Int, message: String?) {
        Toast.makeText(this, "$code --- $message", Toast.LENGTH_SHORT).show()
    }
}