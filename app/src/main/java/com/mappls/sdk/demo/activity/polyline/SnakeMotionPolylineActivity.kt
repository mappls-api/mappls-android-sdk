package com.mappls.sdk.demo.activity.polyline

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.demo.plugin.SnakePolyLinePlugin
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapView
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


class SnakeMotionPolylineActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mBinding: ActivityBaseMapBinding

    private var mapplsMap: MapplsMap? = null

    private var snakePolyLinePlugin: SnakePolyLinePlugin? = null

    companion object {
        val ORIGIN_POINT = Point.fromLngLat(77.2667594, 28.5506561)
        val DESTINATION_POINT = Point.fromLngLat(77.101318, 28.703900)
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
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.snake_motion_polyline_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener { finish() }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        snakePolyLinePlugin = SnakePolyLinePlugin(mBinding.mapView, mapplsMap)

        mapplsMap.getStyle {
            if (it.isFullyLoaded){
                getDirectionRoute()
            }
        }


    }

    private fun getDirectionRoute() {
        val mapplsDirections = MapplsDirections.builder()
            .origin(ORIGIN_POINT)
            .steps(true)
            .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .destination(DESTINATION_POINT)
            .build()
        MapplsDirectionManager.newInstance(mapplsDirections)
            .call(object : OnResponseCallback<DirectionsResponse?> {
                override fun onSuccess(directionsResponse: DirectionsResponse?) {
                    if (directionsResponse != null) {
                        val currentRoute = directionsResponse.routes()[0]
                        val points = PolylineUtils.decode(
                            currentRoute.geometry()!!, Constants.PRECISION_6
                        )
                        val latLngs: MutableList<LatLng> = ArrayList()
                        for (point in points) {
                            latLngs.add(LatLng(point.latitude(), point.longitude()))
                        }
                        val latLngBounds = LatLngBounds.Builder()
                            .includes(latLngs)
                            .build()
                        mapplsMap!!.moveCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                latLngBounds,
                                10,
                                10,
                                10,
                                10
                            )
                        )

                        snakePolyLinePlugin?.create(currentRoute.legs()!![0].steps()!!)

                    }
                }

                override fun onError(i: Int, s: String) {
                }
            })
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
        snakePolyLinePlugin?.removeCallback()

    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
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