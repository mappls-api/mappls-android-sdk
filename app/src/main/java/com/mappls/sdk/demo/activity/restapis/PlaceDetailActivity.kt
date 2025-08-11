package com.mappls.sdk.demo.activity.restapis

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.databinding.ActivityPlaceDetailBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetail
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetailManager
import com.mappls.sdk.services.api.placedetail.model.PlaceDetailResponse
import com.mappls.sdk.services.utils.ErrorCodes

class PlaceDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityPlaceDetailBinding
    private var mPlaceDetailManager: MapplsPlaceDetailManager? = null
    private var mMapplsMap: MapplsMap? = null
    private var showResponse = false
    private var hasResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.placeDetailHeader.headerTitle.text = "Place Detail"
        mBinding.placeDetailHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.btnPlaceDetailSearch.setOnClickListener {
            val mapplsPin = mBinding.searchEt.text.toString()
            if(mapplsPin.isNotEmpty()) {
                callPlaceDetail(mapplsPin)
            } else {
                Toast.makeText(this, "Please enter Mappls Pin", Toast.LENGTH_SHORT).show()
            }
        }
        showResponse(showResponse)
        mBinding.placeDetailShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.placeDetailShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }
    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.placeDetailShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.placeDetailShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.placeDetailJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.placeDetailJsonResponseSv.visibility = View.VISIBLE
            }
        }

    }

    private fun callPlaceDetail(mapplsPin: String) {
        mPlaceDetailManager?.cancel()
        hasResponse = false
        mBinding.placeDetailJsonResponse.text = ""
        mBinding.placeDetailJsonResponseSv.visibility = View.GONE
        val placeDetail = MapplsPlaceDetail.builder()
            .mapplsPin(mapplsPin)
            .build()
        mMapplsMap?.clear()
        mBinding.progressBarLayout.progressBar.visibility = View.VISIBLE
        mPlaceDetailManager = MapplsPlaceDetailManager.newInstance(placeDetail)
        mPlaceDetailManager?.call(object: OnResponseCallback<PlaceDetailResponse> {
            override fun onSuccess(placeDetailResponse: PlaceDetailResponse?) {
                mBinding.placeDetailJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(placeDetailResponse)
                placeDetailResponse?.let {
                    hasResponse = true
                    mBinding.progressBarLayout.progressBar.visibility = View.GONE
                    if(showResponse) {
                        mBinding.placeDetailJsonResponseSv.visibility = View.VISIBLE
                    }
                    if(it.latitude != null && it.longitude != null) {
                        mMapplsMap?.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                        mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 14.0))
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    mBinding.progressBarLayout.progressBar.visibility = View.GONE
                    Toast.makeText(this@PlaceDetailActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
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
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}