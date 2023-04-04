package com.mappls.sdk.demo.kotlin.activity

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityNearbyReportBinding
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import com.mappls.sdk.services.api.event.nearby.MapplsNearbyReport
import com.mappls.sdk.services.api.event.nearby.MapplsNearbyReportManager
import com.mappls.sdk.services.api.event.nearby.model.NearbyReportResponse

class NearbyReportActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityNearbyReportBinding
    private var mapplsMap: MapplsMap? = null
    private var transparentProgressDialog: TransparentProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nearby_report)
        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.search.setOnClickListener {
            if (mapplsMap != null) {
                val placeOptions = PlaceOptions.builder()
                    .backgroundColor(Color.WHITE)
                    .build()
                val placeAutocompleteFragment: PlaceAutocompleteFragment =
                    PlaceAutocompleteFragment.newInstance(placeOptions)
                placeAutocompleteFragment.setOnPlaceSelectedListener(object :
                    PlaceSelectionListener {
                    override fun onPlaceSelected(eLocation: ELocation?) {
                        mapplsMap?.clear()
                        if (eLocation?.latitude != null && eLocation.longitude != null) {
                            mapplsMap!!.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        eLocation.latitude.toDouble(),
                                        eLocation.longitude.toDouble()
                                    ), 14.0
                                )
                            )
                        } else {
                            mapplsMap!!.animateCamera(
                                CameraMapplsPinUpdateFactory.newMapplsPinZoom(
                                    eLocation?.mapplsPin!!,
                                    14.0
                                )
                            )
                        }
                        supportFragmentManager.popBackStack(
                            PlaceAutocompleteFragment::class.java.simpleName,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }

                    override fun onCancel() {
                        supportFragmentManager.popBackStack(
                            PlaceAutocompleteFragment::class.java.simpleName,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }

                    override fun requestForCurrentLocation() {
                        Toast.makeText(this@NearbyReportActivity,
                            "Please provide current location",
                            Toast.LENGTH_SHORT).show()

                    }
                })
                supportFragmentManager.beginTransaction().add(
                    R.id.fragment_container,
                    placeAutocompleteFragment,
                    PlaceAutocompleteFragment::class.java.simpleName
                )
                    .addToBackStack(PlaceAutocompleteFragment::class.java.simpleName)
                    .commit()
            }

        }

        mBinding.tvNearbyReport.setOnClickListener {
            val top = mBinding.selectionBox.top - mBinding.mapView.top
            val left = mBinding.selectionBox.left - mBinding.mapView.left
            val bottom = top + mBinding.selectionBox.height
            val right = left + mBinding.selectionBox.width
            if (mapplsMap != null) {
                val topLeft = mapplsMap!!.projection.fromScreenLocation(
                    PointF(
                        left.toFloat(), top.toFloat()
                    )
                )
                val rightBottom = mapplsMap!!.projection.fromScreenLocation(
                    PointF(
                        right.toFloat(), bottom.toFloat()
                    )
                )
                mapplsMap!!.clear()
                val mapplsNearbyReport = MapplsNearbyReport.builder()
                    .topLeft(
                        Point.fromLngLat(
                            topLeft.longitude,
                            topLeft.latitude
                        )
                    )
                    .bottomRight(
                        Point.fromLngLat(
                            rightBottom.longitude,
                            rightBottom.latitude
                        )
                    )
                    .build()
                progressDialogShow()
                MapplsNearbyReportManager.newInstance(mapplsNearbyReport)
                    .call(object : OnResponseCallback<NearbyReportResponse?> {
                        override fun onSuccess(nearbyReportResponse: NearbyReportResponse?) {
                            if (nearbyReportResponse != null && nearbyReportResponse.reports != null && nearbyReportResponse.reports.size > 0) {
                                for (nearbyReport in nearbyReportResponse.reports) {

                                        mapplsMap?.addMarker(
                                            MarkerOptions().position(
                                                LatLng(
                                                    nearbyReport.latitude,
                                                    nearbyReport.longitude
                                                )
                                            ).title(nearbyReport.category)
                                        )
                                    }

                            } else {
                                Toast.makeText(
                                    this@NearbyReportActivity,
                                    "No result found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            progressDialogHide()
                        }

                        override fun onError(i: Int, s: String) {
                            progressDialogHide()
                            Toast.makeText(this@NearbyReportActivity, s, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

    protected fun progressDialogShow() {
        transparentProgressDialog!!.show()
    }

    protected fun progressDialogHide() {
        transparentProgressDialog!!.dismiss()
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

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        mapplsMap.uiSettings?.setLogoMargins(0, 0, 0, 100)
        mapplsMap.cameraPosition =
            CameraPosition.Builder().target(LatLng(28.550716, 77.268928)).zoom(12.0).build()
        mBinding.tvNearbyReport.visibility = View.VISIBLE
        mBinding.selectionBox.visibility = View.VISIBLE
        mBinding.search.visibility = View.VISIBLE
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
}