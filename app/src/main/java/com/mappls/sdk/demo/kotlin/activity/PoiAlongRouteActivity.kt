package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityPoiAlongRouteBinding
import com.mappls.sdk.demo.kotlin.adapter.PoiAlongAdapter
import com.mappls.sdk.demo.kotlin.plugin.DirectionPolylinePlugin
import com.mappls.sdk.demo.kotlin.utility.CheckInternet
import com.mappls.sdk.demo.java.utils.InputFilterMinMax
import com.mappls.sdk.demo.kotlin.utility.TransparentProgressDialog
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.alongroute.MapplsPOIAlongRoute
import com.mappls.sdk.services.api.alongroute.MapplsPOIAlongRouteManager
import com.mappls.sdk.services.api.alongroute.models.POIAlongRouteResponse
import com.mappls.sdk.services.api.alongroute.models.SuggestedPOI
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.MapplsDirectionManager
import com.mappls.sdk.services.api.directions.MapplsDirections
import com.mappls.sdk.services.api.directions.models.DirectionsResponse
import com.mappls.sdk.services.utils.Constants
import timber.log.Timber
import java.util.*

class PoiAlongRouteActivity: AppCompatActivity(), OnMapReadyCallback, MapplsMap.OnMapLongClickListener {
    private lateinit var mBinding:ActivityPoiAlongRouteBinding
    private lateinit var mapplsMap: MapplsMap
    private lateinit var mapView: MapView
    private lateinit var transparentProgressDialog: TransparentProgressDialog
    private val profile = DirectionsCriteria.PROFILE_DRIVING

    private lateinit var poiRecyclerView: RecyclerView
    private lateinit var mLayoutManager: LinearLayoutManager
    private var directionPolylinePlugin: DirectionPolylinePlugin? = null
    private lateinit var view: RelativeLayout
    private lateinit var etStartLat: EditText
    private lateinit var etStartLng: EditText
    private lateinit var etDestLat: EditText
    private lateinit var etDestLng: EditText
    private lateinit var etKeyword: EditText
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_poi_along_route)
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        etStartLat = findViewById(R.id.et_start_lat)
        etStartLng = findViewById(R.id.et_start_lng)
        etDestLat = findViewById(R.id.et_dest_lat)
        etDestLng = findViewById(R.id.et_dest_lng)
        etKeyword = findViewById(R.id.et_keyword)
        etStartLng.filters = arrayOf<InputFilter>(InputFilterMinMax(-180, 180))
        etStartLat.filters = arrayOf<InputFilter>(InputFilterMinMax(-90, 90))
        etDestLng.filters = arrayOf<InputFilter>(InputFilterMinMax(-180, 180))
        etDestLat.filters = arrayOf<InputFilter>(InputFilterMinMax(-90, 90))
        initReferences()
        view = findViewById(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(view)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.peekHeight = 250
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        val button: Button = findViewById(R.id.btn_search)
        button.setOnClickListener { getDirections() }
        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
    }

    private fun initReferences() {
        poiRecyclerView = findViewById(R.id.poiRecyclerview)
        mLayoutManager = LinearLayoutManager(this)
        poiRecyclerView.layoutManager = mLayoutManager
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onMapReady(p0: MapplsMap) {
        mapplsMap = p0
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                28.594475, 77.202432), 10.0))
        findViewById<View>(R.id.details_layout).visibility = View.VISIBLE
        mapplsMap.uiSettings?.setLogoMargins(0, 0, 0, 250)
        if (CheckInternet.isNetworkAvailable(this@PoiAlongRouteActivity)) {
            Timber.tag("route").v("calling")
            getDirections()
        } else {
            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getDirections() {
        var startLat = 0.0
        var startLng = 0.0
        var destLat = 0.0
        var destLng = 0.0
        val startLatText = etStartLat.text.toString()
        val startLngText = etStartLng.text.toString()
        val destLatText = etDestLat.text.toString()
        val destLngText = etDestLng.text.toString()
        val keyword = etKeyword.text.toString()
        if (TextUtils.isEmpty(startLatText) || TextUtils.isEmpty(startLngText) || TextUtils.isEmpty(destLatText) || TextUtils.isEmpty(destLngText) || TextUtils.isEmpty(keyword)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            startLat = startLatText.toDouble()
            startLng = startLngText.toDouble()
            destLng = destLngText.toDouble()
            destLat = destLatText.toDouble()
        } catch (e: Exception) {
            //Igonore
        }
        if (startLat == 0.0 || startLng == 0.0 || destLat == 0.0 || destLng == 0.0) {
            Toast.makeText(this, "Invalid Coordinates", Toast.LENGTH_SHORT).show()
            return
        }
        view.visibility = View.GONE

        progressDialogShow()
        val directions = MapplsDirections.builder()
                .origin(Point.fromLngLat(startLng, startLat))
                .destination(Point.fromLngLat(destLng, destLat))
                .profile(profile)
                .resource(DirectionsCriteria.RESOURCE_ROUTE)
                .steps(false)
                .alternatives(false)
                .overview(DirectionsCriteria.OVERVIEW_FULL).build()
        MapplsDirectionManager.newInstance(directions).call(object: OnResponseCallback<DirectionsResponse>{
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                if (directionsResponse != null) {
                    val results = directionsResponse.routes()
                    if (results.size > 0) {
                        mapplsMap.clear()
                        val directionsRoute = results[0]
                        if (directionsRoute?.geometry() != null) {
                            drawPath(PolylineUtils.decode(directionsRoute.geometry()!!, Constants.PRECISION_6))

                            callPOIAlongRoute(directionsRoute.geometry()!!, keyword)
                        } else {
                            progressDialogHide()
                        }
                    } else {
                        progressDialogHide()
                    }
                } else {
                    progressDialogHide()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                progressDialogHide()
                Toast.makeText(this@PoiAlongRouteActivity, p1, Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun callPOIAlongRoute(path: String, keyword: String) {
        val poiAlongRoute = MapplsPOIAlongRoute.builder()
                .category(keyword)
                .path(path)
                .buffer(300)
                .build()
        MapplsPOIAlongRouteManager.newInstance(poiAlongRoute).call(object : OnResponseCallback<POIAlongRouteResponse> {
            override fun onSuccess(response: POIAlongRouteResponse?) {
                progressDialogHide()
                if (response != null) {
                    view.visibility = View.VISIBLE
                    val pois = response.suggestedPOIs
                    addMarker(pois)

                }
            }

            override fun onError(p0: Int, p1: String?) {
                progressDialogHide()
                Toast.makeText(this@PoiAlongRouteActivity, p1, Toast.LENGTH_LONG).show()
            }

        })

    }


    private fun drawPath(waypoints: List<Point>) {
        val listOfLatlang = ArrayList<LatLng>()
        for (point in waypoints) {
            listOfLatlang.add(LatLng(point.latitude(), point.longitude()))
        }

        if(directionPolylinePlugin == null) {
            directionPolylinePlugin =DirectionPolylinePlugin(mapplsMap!!, mapView!!, profile)
            directionPolylinePlugin!!.createPolyline(listOfLatlang)
        } else {
            directionPolylinePlugin!!.updatePolyline(profile, listOfLatlang)
        }

        val latLngBounds = LatLngBounds.Builder().includes(listOfLatlang).build()
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10, 250, 10, 100))
    }


    private fun addMarker(pois: List<SuggestedPOI>) {
        for (marker in pois) {
            mapplsMap.addMarker(MarkerOptions().position(LatLng(marker.latitude, marker.longitude)).title(marker.poi))
        }
        poiRecyclerView.adapter = PoiAlongAdapter(pois)
    }

    private fun progressDialogShow() {
        transparentProgressDialog.show()
    }

    private fun progressDialogHide() {
        transparentProgressDialog.dismiss()
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

    override fun onMapLongClick(latLng: LatLng): Boolean {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Select Point as Source or Destination")

        alertDialog.setPositiveButton("Source") { dialog, which ->
            etStartLat.setText("${latLng.latitude}")
            etStartLng.setText("${latLng.longitude}")
            getDirections()
        }
        alertDialog.setNegativeButton("Destination") { dialog, which ->
            etDestLat.setText("${latLng.latitude}")
            etDestLng.setText("${latLng.longitude}")
            getDirections()
        }

        alertDialog.setCancelable(true)
        alertDialog.show()
        return false
    }
}