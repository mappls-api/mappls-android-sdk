package com.mappls.sdk.demo.activity.animation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.demo.plugin.TrackingPlugin
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.MapplsDirectionManager
import com.mappls.sdk.services.api.directions.MapplsDirections
import com.mappls.sdk.services.api.directions.models.DirectionsResponse
import com.mappls.sdk.services.utils.Constants

class TrackingActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityBaseMapBinding
    private var travelledPoints: List<Point>? = null
    private var mMapplsMap: MapplsMap? = null
    private var trackingPlugin: TrackingPlugin? = null
    private var index = 0
    private val trackingHandler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            sendMessageToBackgroundHandler()
            trackingHandler.postDelayed(this, 3000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityBaseMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.baseMapHeader.headerTitle.setText(R.string.tracking_sample_title)
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
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
        mapplsMap.getStyle {
            trackingPlugin = TrackingPlugin(mBinding.mapView, mapplsMap)
            callRouteETA()
        }
    }

    override fun onMapError(code: Int, message: String?) {
        Toast.makeText(this, "$code --- $message", Toast.LENGTH_SHORT).show()
    }

    private fun sendMessageToBackgroundHandler() {
        try {
            // GeoPoint nextLocation = mOrderTrackingActivity.getLocationBlockingQueue().take();
            if (index < travelledPoints!!.size - 1) {
                trackingPlugin!!.animateCar(travelledPoints!![index], travelledPoints!![index + 1])
                index++
//                callTravelledRoute()
            } else {
                trackingHandler.removeCallbacks(runnable)
                //                                Toast.makeText(this, "Route END", Toast.LENGTH_SHORT).show();
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun callRouteETA() {
        mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(19.15183700, 72.93374500), 12.0))
        val builder = MapplsDirections.builder()
            .steps(true)
            .origin(Point.fromLngLat(72.93374500, 19.15183700))
            .destination(Point.fromLngLat(72.9344, 19.1478))
            .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)
            .overview(DirectionsCriteria.OVERVIEW_SIMPLIFIED)
        MapplsDirectionManager.newInstance(builder.build()).call(object: OnResponseCallback<DirectionsResponse> {
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                if (directionsResponse != null && directionsResponse.routes().size > 0) {
                    val directionsRoute = directionsResponse.routes()[0]
                    if (directionsRoute?.geometry() != null) {
                        travelledPoints = PolylineUtils.decode(directionsRoute.geometry()!!,
                            Constants.PRECISION_6)
                        startTracking()
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {

            }

        })
    }

    private fun startTracking() {
        trackingPlugin!!.addMarker(travelledPoints!![0])
        trackingHandler.post(runnable)
    }

    private fun callTravelledRoute() {
        val direction = MapplsDirections.builder()
            .origin(travelledPoints!![index])
            .destination(Point.fromLngLat(72.9344, 19.1478))
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .steps(true)
            .routeType(DirectionsCriteria.ROUTE_TYPE_SHORTEST)
            .resource(DirectionsCriteria.RESOURCE_ROUTE)
            .build()
        MapplsDirectionManager.newInstance(direction).call(object: OnResponseCallback<DirectionsResponse> {
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                if (directionsResponse != null && directionsResponse.routes().size > 0) {
                    val directionsRoute = directionsResponse.routes()[0]
                    if (directionsRoute?.geometry() != null) {
                        trackingPlugin?.updatePolyline(directionsRoute)
                        val remainingPath = PolylineUtils.decode(directionsRoute.geometry()!!, Constants.PRECISION_6)
                        val latLngList: MutableList<LatLng> = ArrayList()
                        for (point in remainingPath) {
                            latLngList.add(LatLng(point.latitude(), point.longitude()))
                        }
                        if (latLngList.size > 0) {
                            if (latLngList.size == 1) {
                                mMapplsMap?.easeCamera(CameraUpdateFactory.newLatLngZoom(latLngList[0], 12.0))
                            } else {
                                val latLngBounds = LatLngBounds.Builder()
                                    .includes(latLngList)
                                    .build()
                                mMapplsMap?.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 180, 0, 180, 0))
                            }
                        }
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {

            }

        })
    }

}
