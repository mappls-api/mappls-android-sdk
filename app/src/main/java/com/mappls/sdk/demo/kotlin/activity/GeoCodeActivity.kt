package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.utils.CheckInternet
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.geocoding.GeoCodeResponse
import com.mappls.sdk.services.api.geocoding.MapplsGeoCoding
import com.mappls.sdk.services.api.geocoding.MapplsGeoCodingManager

/**
 * Created by CEINFO on 26-02-2019.
 */
class GeoCodeActivity : AppCompatActivity(), OnMapReadyCallback {


    private var mapplsMap: MapplsMap? = null
    private var mapView: MapView? = null
    private var transparentProgressDialog: TransparentProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_layout)
        mapView = findViewById(R.id.map_view)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap




        mapplsMap.setPadding(20, 20, 20, 20)

        mapplsMap.cameraPosition = setCameraAndTilt()
        if (CheckInternet.isNetworkAvailable(this@GeoCodeActivity)) {
            getGeoCode("saket")
        } else {
            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show()
        }
    }

    protected fun setCameraAndTilt(): CameraPosition {
        return CameraPosition.Builder().target(LatLng(
                28.551087, 77.257373)).zoom(14.0).tilt(0.0).build()
    }


    protected fun progressDialogShow() {
        transparentProgressDialog!!.show()
    }

    protected fun progressDialogHide() {
        transparentProgressDialog!!.dismiss()
    }


    private fun getGeoCode(geocodeText: String) {
        progressDialogShow()
        val geoCoding = MapplsGeoCoding.builder()
                .setAddress(geocodeText)
                .build()
        MapplsGeoCodingManager.newInstance(geoCoding).call(object : OnResponseCallback<GeoCodeResponse> {
            override fun onSuccess(geoCodeResponse: GeoCodeResponse?) {
                if (geoCodeResponse != null) {
                    val placesList = geoCodeResponse.results
                    val place = placesList[0]
                    val add = "Latitude: " + place.latitude + " longitude: " + place.longitude
                    addMarker(java.lang.Double.valueOf(place.latitude), java.lang.Double.valueOf(place.longitude))
                    Toast.makeText(this@GeoCodeActivity, add, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@GeoCodeActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@GeoCodeActivity, p1, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addMarker(latitude: Double, longitude: Double) {
        mapplsMap?.addMarker(MarkerOptions().position(LatLng(
                latitude, longitude)))
    }

    override fun onMapError(i: Int, s: String) {

    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }


}