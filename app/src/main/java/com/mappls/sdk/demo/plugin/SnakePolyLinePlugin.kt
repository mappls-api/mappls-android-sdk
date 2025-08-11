package com.mappls.sdk.demo.plugin

import android.graphics.Color
import com.mappls.sdk.geojson.Feature
import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.LineString
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.style.layers.LineLayer
import com.mappls.sdk.maps.style.layers.Property
import com.mappls.sdk.maps.style.layers.Property.LINE_CAP_ROUND
import com.mappls.sdk.maps.style.layers.Property.LINE_JOIN_ROUND
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineCap
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineColor
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineJoin
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineOpacity
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineWidth
import com.mappls.sdk.maps.style.sources.GeoJsonSource
import com.mappls.sdk.services.api.directions.models.LegStep
import com.mappls.sdk.services.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SnakePolyLinePlugin(
    private val mapView: MapView,
    private val mapplsMap: MapplsMap
) : MapView.OnDidFinishLoadingStyleListener {

    companion object {
        private const val NAVIGATION_LINE_WIDTH = 6f
        private const val NAVIGATION_LINE_OPACITY = 0.8f
        private const val DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID = "DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID"
        private const val DRIVING_ROUTE_POLYLINE_SOURCE_ID = "DRIVING_ROUTE_POLYLINE_SOURCE_ID"
        private const val DRAW_SPEED_MILLISECONDS = 500L
    }

    private var legSteps: List<LegStep>? = null
    private var drawJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        mapView.addOnDidFinishLoadingStyleListener(this)
        initialiseSourceAndLayer()
    }

    private fun initialiseSourceAndLayer() {
        mapplsMap.getStyle { style ->
            addSource(style)
            addLayer(style)
        }
    }

    private fun addLayer(style: Style) {
        if (style.getLayer(DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID) == null) {
            style.addLayer(
                LineLayer(DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID, DRIVING_ROUTE_POLYLINE_SOURCE_ID)
                    .withProperties(
                        lineWidth(NAVIGATION_LINE_WIDTH),
                        lineOpacity(NAVIGATION_LINE_OPACITY),
                        lineCap(Property.LINE_CAP_ROUND),
                        lineJoin(Property.LINE_JOIN_ROUND),
                        lineColor(Color.BLUE)
                    )
            )
        }
    }

    private fun addSource(style: Style) {
        if (style.getSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID) == null) {
            style.addSource(GeoJsonSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID))
        }
    }

    fun create(legSteps: List<LegStep>) {
        this.legSteps = legSteps
        startDrawingRoute()
    }

    fun removeCallback() {
        drawJob?.cancel()
    }

    override fun onDidFinishLoadingStyle() {
        removeCallback()
        startDrawingRoute()
    }

    private fun startDrawingRoute() {
        val steps = legSteps ?: return
        drawJob = coroutineScope.launch {
            val featureList = mutableListOf<Feature>()
            for (step in steps) {
                step.geometry()?.let { geometry ->
                    val lineString = LineString.fromPolyline(geometry, Constants.PRECISION_6)
                    featureList.add(Feature.fromGeometry(lineString))
                }

                mapplsMap.getStyle { style ->
                    val source = style.getSourceAs<GeoJsonSource>(DRIVING_ROUTE_POLYLINE_SOURCE_ID)
                    source?.setGeoJson(FeatureCollection.fromFeatures(featureList))
                }

                delay(DRAW_SPEED_MILLISECONDS)
            }
        }
    }
}