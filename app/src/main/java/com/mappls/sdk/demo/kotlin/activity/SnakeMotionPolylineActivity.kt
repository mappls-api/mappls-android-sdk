package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.plugin.SnakePolyLinePlugin
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
import java.util.*

class SnakeMotionPolylineActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    lateinit var mapplsMap: MapplsMap

    private lateinit var snakePolyLinePlugin: SnakePolyLinePlugin

    companion object {
        private val ORIGIN_POINT = Point.fromLngLat(77.2667594, 28.5506561)

        private val DESTINATION_POINT = Point.fromLngLat(77.101318, 28.703900)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_motion_polyline)
        mapView = findViewById(R.id.map_view)
        mapView.getMapAsync(this)

    }


    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        mapplsMap.getStyle {
            snakePolyLinePlugin = SnakePolyLinePlugin(mapView, mapplsMap)
            getDirectionRoute()
        }
    }


    private fun getDirectionRoute() {
        val directions = MapplsDirections.builder()
                .origin(ORIGIN_POINT)
                .steps(true)
                .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .destination(DESTINATION_POINT)
                .build()

        MapplsDirectionManager.newInstance(directions).call(object : OnResponseCallback<DirectionsResponse> {
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                if (directionsResponse != null) {
                    //handle response
                    val currentRoute = directionsResponse.routes()[0]
                    val points = PolylineUtils.decode(currentRoute.geometry()!!, Constants.PRECISION_6)
                    val latLngs: MutableList<LatLng> = ArrayList()
                    for (point in points) {
                        latLngs.add(LatLng(point.latitude(), point.longitude()))
                    }
                    val latLngBounds = LatLngBounds.Builder()
                            .includes(latLngs)
                            .build()
                    mapplsMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10, 10, 10, 10))

                    snakePolyLinePlugin.create(currentRoute.legs()!![0].steps())

                }
            }

            override fun onError(p0: Int, p1: String?) {

            }
        })
    }


    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        snakePolyLinePlugin.removeCallback()

    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mapView.onSaveInstanceState(outState)
    }


}