package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.adapter.NearByAdapter
import com.mappls.sdk.demo.java.utils.CheckInternet
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.nearby.MapplsNearby
import com.mappls.sdk.services.api.nearby.MapplsNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult
import java.util.*

/**
 * Created by CEINFO on 26-02-2019.
 */
class NearByActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mapplsMap: MapplsMap? = null
    private var mapView: MapView? = null
    private var transparentProgressDialog: TransparentProgressDialog? = null
    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var count = 0
    private var floatingActionButton: FloatingActionButton? = null
    private var mLayoutManager: androidx.recyclerview.widget.LinearLayoutManager? = null
    private lateinit var radiusSeekbar: SeekBar
    var radius = 1000
    private lateinit var keywordEt: EditText
    private  lateinit var locationEt:EditText
    private lateinit var hitAPiBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.near_by_activity)
        mapView = findViewById(R.id.map_view)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        Toast.makeText(this, "Please click on map to get nearby.", Toast.LENGTH_SHORT).show()
        recyclerView = findViewById(R.id.nearByRecyclerview)
        mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@NearByActivity)
        recyclerView!!.layoutManager = mLayoutManager
        radiusSeekbar = findViewById(R.id.seekBar)
        hitAPiBtn= findViewById(R.id.hit_api_btn);
        keywordEt = findViewById(R.id.keyword_et)
        locationEt = findViewById(R.id.location_et)
       // radiusSeekbar.min = 500
        radiusSeekbar.max = 10000
        radiusSeekbar.progress = 1000
        hitAPiBtn.setOnClickListener {
            val location = locationEt.text.toString()
            if (!TextUtils.isEmpty(location)) {
                getNearBy(locationEt.text.toString())
            }
        }
        radiusSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress < 500) {
                    radiusSeekbar.progress = 500
                } else {
                    radius = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        setCameraAndTilt(28.67, 77.56)
        floatingActionButton = findViewById(R.id.marker_list)
        floatingActionButton!!.setImageResource(R.drawable.location_pointer)
        count = 0
        floatingActionButton!!.setOnClickListener { v ->
            if (count == 0) {
                count = 1
                floatingActionButton!!.setImageResource(R.drawable.listing_option)
                recyclerView!!.visibility = View.GONE
            } else {
                count = 0
                floatingActionButton!!.setImageResource(R.drawable.location_pointer)
                recyclerView!!.visibility = View.VISIBLE
            }
        }

        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap






        mapplsMap.setPadding(20, 20, 20, 20)


        //mapplsMap.cameraPosition = setCameraAndTilt()
        mapplsMap.addOnMapClickListener { latLng ->
            locationEt.setText("${latLng.latitude},${latLng.longitude}");
            mapplsMap.clear()
            if (CheckInternet.isNetworkAvailable(this@NearByActivity)) {
               // locationEt.setText("")
                getNearBy("${latLng.latitude},${latLng.longitude}")
            } else {
                Toast.makeText(this@NearByActivity, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show()
            }
            return@addOnMapClickListener false
        }
    }

    override fun onMapError(i: Int, s: String) {

    }

    private fun setCameraAndTilt(lat: Double, lng: Double): CameraPosition {
        return CameraPosition.Builder().target(LatLng(
                lat, lng)).zoom(14.0).tilt(0.0).build()
    }

    protected fun progressDialogShow() {
        transparentProgressDialog!!.show()
    }

    protected fun progressDialogHide() {
        transparentProgressDialog!!.dismiss()
    }

    private fun getNearBy(location: String) {
        mapplsMap!!.clear()
        progressDialogShow()
        val builder = MapplsNearby.builder()
        if (!TextUtils.isEmpty(location)) {
            if (!location.contains(",")) {
                mapplsMap!!.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom(location, 11.0))
                builder.setLocation(location)
            } else {
              mapplsMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.split(",").toTypedArray()[0].toDouble(), location.split(",").toTypedArray()[1].toDouble()), 14.0))
                builder.setLocation(location.split(",").toTypedArray()[0].toDouble(), location.split(",").toTypedArray()[1].toDouble())
            }
        }

        builder.keyword(keywordEt.text.toString())


        builder.radius(radius)
                .build()
        MapplsNearbyManager.newInstance(builder.build()).call(object : OnResponseCallback<NearbyAtlasResponse> {
            override fun onSuccess(nearbyResponse: NearbyAtlasResponse?) {
                if (nearbyResponse != null) {
                    val nearByList = nearbyResponse.suggestedLocations
                    if (nearByList.size > 0) {
                        addMarker(nearByList)
                    }
                } else {
                    Toast.makeText(this@NearByActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
                progressDialogHide()
            }

            override fun onError(p0: Int, p1: String?) {
                progressDialogHide()
            }

        })
    }


    private fun addMarker(nearByList: ArrayList<NearbyAtlasResult>) {
        for (marker in nearByList) {
            mapplsMap!!.addMarker(MarkerOptions().position(LatLng(marker.latitude, marker.longitude)).title(marker.placeName))
        }

        recyclerView!!.adapter = NearByAdapter(nearByList)
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }
}