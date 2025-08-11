package com.mappls.sdk.demo.activity.restapis

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.PlaceDetailActivity
import com.mappls.sdk.demo.databinding.ActivityNearbyReportBinding
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.event.nearby.MapplsNearbyReport
import com.mappls.sdk.services.api.event.nearby.MapplsNearbyReportManager
import com.mappls.sdk.services.api.event.nearby.model.NearbyReportResponse
import com.mappls.sdk.services.utils.ErrorCodes

class NearbyReportActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityNearbyReportBinding
    private var mMapplsMap: MapplsMap? = null
    private var mNearbyReportManager: MapplsNearbyReportManager? = null
    private var showResponse = false
    private var hasResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityNearbyReportBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.nearbyReportHeader.headerTitle.text = "Nearby Report"
        mBinding.nearbyReportHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.btnNearbyReportSearch.setOnClickListener {
            val left = mBinding.selectionBox.left
            val top = mBinding.selectionBox.top - mBinding.mapView.top
            val right = mBinding.selectionBox.right
            val bottom = top + mBinding.selectionBox.height
            val topLeft = mMapplsMap?.projection?.fromScreenLocation(PointF(left.toFloat(), top.toFloat()))
            val rightBottom = mMapplsMap?.projection?.fromScreenLocation(PointF(right.toFloat(), bottom.toFloat()))
            mMapplsMap?.clear()
            if(topLeft != null && rightBottom != null) {
                callNearbyReport(topLeft, rightBottom)
            }
        }
        showResponse(showResponse)
        mBinding.nearbyReportShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.nearbyReportShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }

    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.nearbyReportShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.nearbyReportShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.nearbyReportJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.nearbyReportJsonResponseSv.visibility = View.VISIBLE
            }
        }

    }

    private fun callNearbyReport(topLeft: LatLng, rightBottom: LatLng) {
        mMapplsMap?.clear()
        mNearbyReportManager?.cancel()
        hasResponse = false
        mBinding.nearbyReportJsonResponse.text = ""
        mBinding.nearbyReportJsonResponseSv.visibility = View.GONE
        mBinding.progressBarLayout.progressBar.visibility = View.VISIBLE
        val builder = MapplsNearbyReport.builder()
            .topLeft(Point.fromLngLat(topLeft.longitude, topLeft.latitude))
            .bottomRight(Point.fromLngLat(rightBottom.longitude, rightBottom.latitude))
        mNearbyReportManager = MapplsNearbyReportManager.newInstance(builder.build())
        mNearbyReportManager?.call(object: OnResponseCallback<NearbyReportResponse> {
            override fun onSuccess(nearbyReportResponse: NearbyReportResponse?) {
                mBinding.nearbyReportJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(nearbyReportResponse)
                nearbyReportResponse?.let {
                    mBinding.progressBarLayout.progressBar.visibility = View.GONE
                    hasResponse = true
                    if(showResponse) {
                        mBinding.nearbyReportJsonResponseSv.visibility = View.VISIBLE
                    }
                    it.reports?.forEach {
                        mMapplsMap?.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    mBinding.progressBarLayout.progressBar.visibility = View.GONE
                    Toast.makeText(this@NearbyReportActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
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
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
        mBinding.selectionBox.visibility = View.VISIBLE
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}