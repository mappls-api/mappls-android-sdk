package com.mappls.sdk.demo.plugin

import androidx.core.graphics.toColorInt
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geojson.Feature

import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.LineString
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.style.expressions.Expression
import com.mappls.sdk.maps.style.expressions.Expression.get
import com.mappls.sdk.maps.style.expressions.Expression.literal
import com.mappls.sdk.maps.style.expressions.Expression.match
import com.mappls.sdk.maps.style.expressions.Expression.stop
import com.mappls.sdk.maps.style.layers.LineLayer
import com.mappls.sdk.maps.style.layers.Property
import com.mappls.sdk.maps.style.layers.PropertyFactory.*
import com.mappls.sdk.maps.style.sources.GeoJsonOptions
import com.mappls.sdk.maps.style.sources.GeoJsonSource
import com.mappls.sdk.maps.utils.ColorUtils
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.models.DirectionsRoute
import com.mappls.sdk.services.api.predictive.directions.model.PredictiveDirectionsTrip
import com.mappls.sdk.services.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class DirectionPolylinePlugin(private val mapplsMap: MapplsMap, mapView: MapView) :
    MapView.OnDidFinishLoadingStyleListener {

    private var mFeatureCollection: FeatureCollection = Utils.EMPTY_FEATURE

    init {
        updateSource()
        mapView.addOnDidFinishLoadingStyleListener(this)
    }

    private fun updateSource() {
        mapplsMap.getStyle {
            val source = it.getSource(ROUTE_SOURCE_ID) as GeoJsonSource?
            if (source == null) {
                initSource()
                return@getStyle
            }
            source.setGeoJson(mFeatureCollection)
        }
    }

    fun clear() {
        mFeatureCollection = Utils.EMPTY_FEATURE
        updateSource()
    }

    private fun initSource() {
        mapplsMap.getStyle {
            addSource(it)
            addSelectedLayer(it)
            addAlternativeLayer(it)
        }
    }

    fun setTrips(
        routes: List<DirectionsRoute>,
        selectedIndex: Int = 0,
        geometries: String = DirectionsCriteria.GEOMETRY_POLYLINE6
    ) {
        if (routes.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                var routeIndex = selectedIndex
                if (routes.size < selectedIndex) {
                    routeIndex = 0;
                }
                val listOfFeatures = mutableListOf<Feature>()
                routes.forEachIndexed { index, route ->
                    if (route.geometry() != null) {
                        val feature =
                            if (geometries == DirectionsCriteria.GEOMETRY_POLYLINE || geometries == DirectionsCriteria.GEOMETRY_POLYLINE6) Feature.fromGeometry(
                                LineString.fromPolyline(
                                    route.geometry()!!,
                                    if (geometries == DirectionsCriteria.GEOMETRY_POLYLINE) Constants.PRECISION_5 else Constants.PRECISION_6
                                )
                            ) else Feature.fromGeometry(LineString.fromJson(route.geometry()))
                        feature.addStringProperty(
                            FILTER_TEXT,
                            if (index == routeIndex) "selected" else "alternate"
                        )
                        listOfFeatures.add(feature)
                    }
                }
                mFeatureCollection = FeatureCollection.fromFeatures(listOfFeatures)
                launch(Dispatchers.Main) {
                    updateSource()
                }
            }


        }
    }

    fun setTrips(routes: PredictiveDirectionsTrip) {
        val legs = routes.legs

        if (legs.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                val listOfFeatures = mutableListOf<Feature>()
                val points = mutableListOf<Point>()
                legs?.forEach {
                    points.addAll(PolylineUtils.decode(it.shape, Constants.PRECISION_6))
                }
                val feature = Feature.fromGeometry(LineString.fromLngLats(points))
                feature.addStringProperty(
                    FILTER_TEXT,
                    "selected"
                )
                listOfFeatures.add(feature)

                mFeatureCollection = FeatureCollection.fromFeatures(listOfFeatures)
                launch(Dispatchers.Main) {
                    updateSource()
                }
            }


        }
    }

    private fun addSelectedLayer(style: Style) {
        val lineLayer = LineLayer(ROUTE_SELECTED_LAYER_ID, ROUTE_SOURCE_ID)
            .withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineColor("#07b9fc".toColorInt()),
                lineWidth(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(4f, 4f),
                        stop(10f, 6.5f),
                        stop(13f, 7f),
                        stop(16f, 9f),
                        stop(19f, 14f),
                        stop(22f, 18f)
                    )
                ),
                lineOffset(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(14, 0f),
                        stop(20, 0f)
                    )
                )
            )
        val lineCaseLayer = LineLayer(ROUTE_SELECTED_CASE_LAYER_ID, ROUTE_SOURCE_ID)
            .withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineColor("#000000".toColorInt()),
                lineWidth(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(4f, 5f),
                        stop(10f, 7.5f),
                        stop(13f, 8.5f),
                        stop(16f, 10.5f),
                        stop(19f, 16f),
                        stop(22f, 27f)
                    )
                ),
                lineOffset(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(14, 0f),
                        stop(20, 0f)
                    )
                )
            )

        lineLayer.setFilter(
            match(
                get(FILTER_TEXT), literal(false),
                stop("selected", true)
            )
        )

        lineCaseLayer.setFilter(
            match(
                get(FILTER_TEXT), literal(false),
                stop("selected", true)
            )
        )

        addBelowLayerToMap(lineLayer, lineCaseLayer, "highway_name", style)
    }

    private fun addAlternativeLayer(style: Style) {
        val lineLayer = LineLayer(ROUTE_ALTERNATE_LAYER_ID, ROUTE_SOURCE_ID)
            .withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineColor("#a1bbd2".toColorInt()),
                lineWidth(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(4f, 4f),
                        stop(10f, 6.5f),
                        stop(13f, 7f),
                        stop(16f, 9f),
                        stop(19f, 14f),
                        stop(22f, 18f)
                    )
                ),
                lineOffset(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(14, 0f),
                        stop(20, 0f)
                    )
                )
            )
        val lineCaseLayer = LineLayer(ROUTE_ALTERNATE_CASE_LAYER_ID, ROUTE_SOURCE_ID)
            .withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineColor("#000000".toColorInt()),
                lineWidth(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(4f, 5f),
                        stop(10f, 7.5f),
                        stop(13f, 8.5f),
                        stop(16f, 10.5f),
                        stop(19f, 16f),
                        stop(22f, 27f)
                    )
                ),
                lineOffset(
                    Expression.interpolate(
                        Expression.exponential(1.5f), Expression.zoom(),
                        stop(14, 0f),
                        stop(20, 0f)
                    )
                )
            )

        lineLayer.setFilter(
            match(
                get(FILTER_TEXT), literal(false),
                stop("alternate", true)
            )
        )

        lineCaseLayer.setFilter(
            match(
                get(FILTER_TEXT), literal(false),
                stop("alternate", true)
            )
        )

        addBelowLayerToMap(lineLayer, lineCaseLayer, ROUTE_SELECTED_CASE_LAYER_ID, style)
    }

    private fun addBelowLayerToMap(
        lineLayer: LineLayer,
        lineCaseLayer: LineLayer,
        layerName: String,
        style: Style
    ) {
        if (style.isFullyLoaded) {
            if (style.getLayer(layerName) != null) {
                style.addLayerBelow(lineCaseLayer, layerName)
            } else {
                style.addLayer(lineCaseLayer)
            }
            style.addLayerAbove(lineLayer, lineCaseLayer.id)
        }
    }

    private fun addSource(style: Style) {
        style.addSource(GeoJsonSource(ROUTE_SOURCE_ID, mFeatureCollection))
    }


    override fun onDidFinishLoadingStyle() {
        updateSource()
    }

    companion object {
        private const val ROUTE_SOURCE_ID = "mappls-direction-source"
        private const val ROUTE_SELECTED_LAYER_ID = "mappls-direction-selected-layer"
        private const val ROUTE_SELECTED_CASE_LAYER_ID = "mappls-direction-selected-case-layer"
        private const val ROUTE_ALTERNATE_LAYER_ID = "mappls-direction-alternate-layer"
        private const val ROUTE_ALTERNATE_CASE_LAYER_ID = "mappls-direction-alternate-case-layer"
        private const val FILTER_TEXT = "direction_type"
    }
}