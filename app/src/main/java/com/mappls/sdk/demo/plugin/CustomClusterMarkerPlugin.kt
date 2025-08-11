package com.mappls.sdk.demo.plugin

import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.model.ClusterModelItem
import com.mappls.sdk.geojson.Feature
import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.maps.style.expressions.Expression
import com.mappls.sdk.maps.style.layers.CircleLayer
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleColor
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleRadius
import com.mappls.sdk.maps.style.layers.PropertyFactory.textAllowOverlap
import com.mappls.sdk.maps.style.layers.PropertyFactory.textColor
import com.mappls.sdk.maps.style.layers.PropertyFactory.textField
import com.mappls.sdk.maps.style.layers.PropertyFactory.textIgnorePlacement
import com.mappls.sdk.maps.style.layers.PropertyFactory.textSize
import com.mappls.sdk.maps.style.layers.SymbolLayer
import com.mappls.sdk.maps.style.sources.GeoJsonSource
import com.mappls.sdk.maps.style.expressions.Expression.toNumber

import com.mappls.sdk.maps.style.expressions.Expression.all
import com.mappls.sdk.maps.style.expressions.Expression.get
import com.mappls.sdk.maps.style.expressions.Expression.gte
import com.mappls.sdk.maps.style.expressions.Expression.has
import com.mappls.sdk.maps.style.expressions.Expression.literal
import com.mappls.sdk.maps.style.expressions.Expression.lt
import com.mappls.sdk.maps.style.layers.PropertyFactory
import com.mappls.sdk.maps.style.sources.GeoJsonOptions


class CustomClusterMarkerPlugin(
    private val mapView: MapView,
    private val mapplsMap: MapplsMap
) : MapView.OnDidFinishLoadingStyleListener, MapplsMap.OnMapClickListener {

    companion object{
        const val CLUSTER_MARKER_SOURCE = "cluster-marker-source"
        const val UN_CLUSTER_MARKER_LAYER = "un-cluster-marker-layer"
        const val CLUSTER_CIRCLE_LAYER = "cluster-circle-layer"
        const val CLUSTER_COUNT_LAYER = "cluster-count-layer"
        const val MARKER_ICON_PROPERTY = "marker-icon-property"
        const val MARKER_CLUSTER_PROPERTY = "marker-color-property"
        const val POINT_COUNT_TEXT = "point_count"
    }


    private var featureCollection = FeatureCollection.fromFeatures(arrayListOf<Feature>())

    init {
        mapView.addOnDidFinishLoadingStyleListener(this)
        mapplsMap.addOnMapClickListener(this)
        updateState()
    }

    private fun updateState() {
        mapplsMap.getStyle { style ->
            val source = style.getSourceAs<GeoJsonSource>(CLUSTER_MARKER_SOURCE)
            if (source == null) {
                initialise(style)
                return@getStyle
            }
            source.setGeoJson(featureCollection)
        }
    }

    fun setMarkers(latLngList: List<ClusterModelItem>?) {
        val features = mutableListOf<Feature>()
        latLngList?.forEach { latLng ->
            val feature = Feature.fromGeometry(
                Point.fromLngLat(
                   latLng.mPosition.longitude,
                           latLng.mPosition.latitude
                )
            )
            feature.addStringProperty(MARKER_ICON_PROPERTY, latLng.markerIcon)
            feature.addBooleanProperty(MARKER_CLUSTER_PROPERTY, true)
            features.add(feature)
        }
        featureCollection = FeatureCollection.fromFeatures(features)
        updateState()
    }

    private fun initialise(style: Style) {
        initImages(style)
        initialiseSource(style)
        initialiseUnClusterLayer(style)
        initialiseClusterLayer(style)
        initialiseCountLayer(style)
    }

    private fun initialiseCountLayer(style: Style) {
        style.removeLayer(CLUSTER_COUNT_LAYER)

        val count = SymbolLayer(CLUSTER_COUNT_LAYER, CLUSTER_MARKER_SOURCE).apply {
            setProperties(
                textField(Expression.toString(get(POINT_COUNT_TEXT))),
                textSize(12f),
                textColor(Color.WHITE),
                textIgnorePlacement(true),
                textAllowOverlap(true)
            )
        }
        style.addLayer(count)
    }

    private fun initialiseClusterLayer(style: Style) {
        val layers = arrayOf(
            intArrayOf(10, Color.BLUE),
            intArrayOf(5, Color.BLUE),
            intArrayOf(0, Color.BLUE)
        )

        for (i in layers.indices) {
            style.removeLayer("$CLUSTER_CIRCLE_LAYER$i")
            val circles:CircleLayer = CircleLayer("$CLUSTER_CIRCLE_LAYER$i", CLUSTER_MARKER_SOURCE)
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(18f)
                )
                val pointCount = toNumber(get(POINT_COUNT_TEXT))
            val filterExpression = if (i == 0) {
                all(
                    has("point_count"),
                    gte(pointCount, literal(layers[i][0]))
                )
            } else {
                all(
                    has("point_count"),
                    gte(pointCount, literal(layers[i][0])),
                    lt(pointCount, literal(layers[i - 1][0]))
                )
            }
            circles.setFilter(filterExpression)
            style.addLayer(circles)
        }
    }

    private fun initialiseUnClusterLayer(style: Style) {
        style.removeLayer(UN_CLUSTER_MARKER_LAYER)
        val unClusterLayer = SymbolLayer(UN_CLUSTER_MARKER_LAYER, CLUSTER_MARKER_SOURCE).apply {
            setProperties(
                PropertyFactory.iconImage(get(MARKER_ICON_PROPERTY)),
                PropertyFactory.iconSize(0.5f)
            )
            setFilter(has(MARKER_CLUSTER_PROPERTY))
        }
        style.addLayer(unClusterLayer)
    }

    private fun initImages(style: Style) {
        style.addImage("pin", ContextCompat.getDrawable(mapView.context, R.drawable.mappls_map_demo_marker)!!)
        style.addImage("car", ContextCompat.getDrawable(mapView.context, R.drawable.car_icon)!!)
    }

    private fun initialiseSource(style: Style) {
        style.removeSource(CLUSTER_MARKER_SOURCE)
        style.addSource(
            GeoJsonSource(
                CLUSTER_MARKER_SOURCE,
                featureCollection,
                GeoJsonOptions().withCluster(true).withClusterRadius(50).withClusterMaxZoom(14)
            )
        )
    }

    override fun onDidFinishLoadingStyle() {
        updateState()
    }

    override fun onMapClick(latLng: LatLng): Boolean {
        val features = mapplsMap.queryRenderedFeatures(
            mapplsMap.projection.toScreenLocation(latLng),
            CLUSTER_CIRCLE_LAYER + 0, CLUSTER_CIRCLE_LAYER + 1, CLUSTER_CIRCLE_LAYER + 2
        )
        if (features.isNotEmpty()) {
            val style = mapplsMap.style
            if (style != null && style.isFullyLoaded) {
                val source = style.getSourceAs<GeoJsonSource>(CLUSTER_MARKER_SOURCE)
                source?.let {
                    val clusterLeaves = it.getClusterLeaves(features[0], 8000, 0)
                    moveCameraToLeavesBounds(clusterLeaves)
                }
            }
        } else {
            val unClusterFeature = mapplsMap.queryRenderedFeatures(
                mapplsMap.projection.toScreenLocation(latLng),
                UN_CLUSTER_MARKER_LAYER
            )
            if (unClusterFeature.isNotEmpty()) {
                Toast.makeText(mapView.context, unClusterFeature[0].geometry().toString(), Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    private fun moveCameraToLeavesBounds(featureCollectionToInspect: FeatureCollection) {
        val latLngList = mutableListOf<LatLng>()
        featureCollectionToInspect.features()?.forEach { singleClusterFeature ->
            val clusterPoint = singleClusterFeature.geometry() as? Point
            clusterPoint?.let {
                latLngList.add(LatLng(it.latitude(), it.longitude()))
            }
        }
        if (latLngList.size > 1) {
            val latLngBounds = LatLngBounds.Builder()
                .includes(latLngList)
                .build()
            mapplsMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(latLngBounds, 20, 20, 20, 20)
            )
        }
    }
}
