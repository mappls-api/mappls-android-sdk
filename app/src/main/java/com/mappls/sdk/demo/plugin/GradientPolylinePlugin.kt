package com.mappls.sdk.demo.plugin

import android.graphics.Color
import com.mappls.sdk.geojson.Feature
import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.LineString
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.Style.OnStyleLoaded
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.style.expressions.Expression.color
import com.mappls.sdk.maps.style.expressions.Expression.interpolate
import com.mappls.sdk.maps.style.expressions.Expression.lineProgress
import com.mappls.sdk.maps.style.expressions.Expression.linear
import com.mappls.sdk.maps.style.expressions.Expression.stop
import com.mappls.sdk.maps.style.layers.LineLayer
import com.mappls.sdk.maps.style.layers.Property
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineCap
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineGradient
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineJoin
import com.mappls.sdk.maps.style.layers.PropertyFactory.lineWidth
import com.mappls.sdk.maps.style.sources.GeoJsonOptions
import com.mappls.sdk.maps.style.sources.GeoJsonSource


class GradientPolylinePlugin(
    private val mapplsMap: MapplsMap,
    mapView: MapView
) : MapView.OnDidFinishLoadingStyleListener {

    companion object {
        private const val UPPER_SOURCE_ID = "line-source-upper-id"
        private const val LAYER_ID = "line-layer-upper-id"
    }

    private var startColor: Int = Color.parseColor("#3dd2d0")
    private var endColor: Int = Color.parseColor("#FF20d0")
    private var latLngs: List<LatLng>? = null
    private var featureCollection: FeatureCollection? = null
    private var polylineSource: GeoJsonSource? = null

    init {
        updateSource()
        mapView.addOnDidFinishLoadingStyleListener(this)
    }

    fun setStartColor(startColor: Int) {
        this.startColor = startColor
    }

    fun setEndColor(endColor: Int) {
        this.endColor = endColor
    }

    fun createPolyline(latLngs: List<LatLng>) {
        this.latLngs = latLngs
        val points = latLngs.map { Point.fromLngLat(it.longitude, it.latitude) }
        featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(points)))
        featureCollection?.let { initSources(it) }
    }

    private fun create(style: Style) {
        if (style.getLayer(LAYER_ID) == null) {
            style.addLayer(
                LineLayer(LAYER_ID, UPPER_SOURCE_ID).withProperties(
                    lineCap(Property.LINE_CAP_ROUND),
                    lineJoin(Property.LINE_JOIN_BEVEL),
                    lineGradient(
                        interpolate(
                            linear(), lineProgress(),
                            stop(0f, color(startColor)),
                            stop(1f, color(endColor))
                        )
                    ),
                    lineWidth(4f)
                )
            )
        }
    }

    fun clear() {
        featureCollection = FeatureCollection.fromFeatures(arrayOf())
        updateSource()
    }

    private fun initSources(featureCollection: FeatureCollection) {
        mapplsMap.getStyle { style ->
            if (style.getSource(UPPER_SOURCE_ID) == null) {
                polylineSource = GeoJsonSource(
                    UPPER_SOURCE_ID,
                    featureCollection,
                    GeoJsonOptions().withLineMetrics(true).withBuffer(2)
                )
                style.addSource(polylineSource!!)
            }
        }
    }

    override fun onDidFinishLoadingStyle() {
        updateSource()
        latLngs?.let { createPolyline(it) }
    }

    private fun updateSource() {
        mapplsMap.getStyle { style ->
            val source = style.getSourceAs<GeoJsonSource>(UPPER_SOURCE_ID)
            if (source == null) {
                create(style)
                return@getStyle
            }
            featureCollection?.let { source.setGeoJson(it) }
        }
    }
}
