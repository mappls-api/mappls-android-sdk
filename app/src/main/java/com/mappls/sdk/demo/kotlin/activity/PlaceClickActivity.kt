package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.databinding.ActivityPlaceClickBinding
import com.mappls.sdk.demo.R
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetail
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetailManager
import com.mappls.sdk.services.api.placedetail.model.PlaceDetailResponse


class PlaceClickActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityPlaceClickBinding
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var address:TextView? =null
    private var title:TextView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_click)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        showHideBottomSheet(false)
        val layoutBottomSheet = findViewById<LinearLayout>(R.id.place_click_bottom_sheet)
        title = findViewById(R.id.tv_title)
        address = findViewById(R.id.tv_address)
       bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)


    }
    protected fun setCameraAndTilt(): CameraPosition {
        return CameraPosition.Builder().target(LatLng(
            28.551087, 77.257373)).zoom(12.0).tilt(0.0).build()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mapplsMap.cameraPosition = setCameraAndTilt()
        mapplsMap.setOnPlaceClickListener {
            if (it != null) {
                Toast.makeText(this@PlaceClickActivity, it.toString(), Toast.LENGTH_LONG).show()
                callMapplsPin(it)
            }
            return@setOnPlaceClickListener false
        }
    }
    private fun callMapplsPin(mapplsPin: String) {
        val placeDetail = MapplsPlaceDetail.builder()
            .mapplsPin(mapplsPin)
            .build()
        MapplsPlaceDetailManager.newInstance(placeDetail).call(object :
            OnResponseCallback<PlaceDetailResponse> {
            override fun onSuccess(placeDetailResponse: PlaceDetailResponse?) {
                if (placeDetailResponse != null) {
                    showHideBottomSheet(true)
                   title!!.text = placeDetailResponse.placeName
                    address!!.text  = placeDetailResponse.address

                } else {
                    Toast.makeText(this@PlaceClickActivity, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@PlaceClickActivity, p1, Toast.LENGTH_SHORT).show()
            }

        })
    }
    override fun onMapError(p0: Int, p1: String?) {

    }
    private fun showHideBottomSheet(showHide: Boolean) {
        if (showHide) {
            bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED)
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

}