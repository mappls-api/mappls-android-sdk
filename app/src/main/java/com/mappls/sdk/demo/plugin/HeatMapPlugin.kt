package com.mappls.sdk.demo.plugin

import com.mappls.sdk.geojson.Feature
import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.style.expressions.Expression.get
import com.mappls.sdk.maps.style.expressions.Expression.heatmapDensity
import com.mappls.sdk.maps.style.expressions.Expression.interpolate
import com.mappls.sdk.maps.style.expressions.Expression.linear
import com.mappls.sdk.maps.style.expressions.Expression.literal
import com.mappls.sdk.maps.style.expressions.Expression.rgb
import com.mappls.sdk.maps.style.expressions.Expression.rgba
import com.mappls.sdk.maps.style.expressions.Expression.stop
import com.mappls.sdk.maps.style.expressions.Expression.zoom
import com.mappls.sdk.maps.style.layers.CircleLayer
import com.mappls.sdk.maps.style.layers.HeatmapLayer
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleColor
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleOpacity
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleRadius
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleStrokeColor
import com.mappls.sdk.maps.style.layers.PropertyFactory.circleStrokeWidth
import com.mappls.sdk.maps.style.layers.PropertyFactory.heatmapColor
import com.mappls.sdk.maps.style.layers.PropertyFactory.heatmapIntensity
import com.mappls.sdk.maps.style.layers.PropertyFactory.heatmapOpacity
import com.mappls.sdk.maps.style.layers.PropertyFactory.heatmapRadius
import com.mappls.sdk.maps.style.layers.PropertyFactory.heatmapWeight
import com.mappls.sdk.maps.style.sources.GeoJsonSource


class HeatMapPlugin private constructor(
    private val mapplsMap: MapplsMap,
    private val mapView: MapView,
    private val builder: Builder
) : MapView.OnDidFinishLoadingStyleListener {

    companion object {
        private const val HEAT_MAP_SOURCE_ID = "heatMapSourceId"
        private const val HEAT_MAP_LAYER_ID = "heatMapLayerId"
        private const val CIRCLE_LAYER_ID = "heatMapCircleLayerId"
        private const val PROPERTY_MAG = "mag"

        fun builder(mapplsMap: MapplsMap, mapView: MapView): Builder {
            return Builder(mapplsMap, mapView).maxZoom(9.0f)
        }
    }

    private var featureCollection: FeatureCollection? = null

    init {
        mapView.addOnDidFinishLoadingStyleListener(this)
        updateState()
    }

    override fun onDidFinishLoadingStyle() {
        updateState()
        addHeatMap()
    }

    private fun updateState() {
        mapplsMap.getStyle { style ->
            val source = style.getSource(HEAT_MAP_SOURCE_ID) as? GeoJsonSource
            if (source == null) {
                initialise(style)
            } else {
                featureCollection?.let { source.setGeoJson(it) }
            }
        }
    }

    private fun initialise(style: Style) {
        addHeatMapSource(style)
        addHeatmapLayer(style)
        addCircleLayer(style)
    }

    private fun addHeatMapSource(style: Style) {
        if (style.getSource(HEAT_MAP_SOURCE_ID) == null) {
            style.addSource(GeoJsonSource(HEAT_MAP_SOURCE_ID, featureCollection))
        }
    }

    private fun addHeatmapLayer(style: Style) {
        if (style.getLayer(HEAT_MAP_LAYER_ID) == null) {
            val layer = HeatmapLayer(HEAT_MAP_LAYER_ID, HEAT_MAP_SOURCE_ID).apply {
                maxZoom = builder.maxZoom
                setProperties(
                    heatmapColor(
                        interpolate(
                            linear(), heatmapDensity(),
                            literal(0), rgba(33, 102, 172, 0),
                            literal(0.2), rgb(103, 169, 207),
                            literal(0.4), rgb(209, 229, 240),
                            literal(0.6), rgb(253, 219, 199),
                            literal(0.8), rgb(239, 138, 98),
                            literal(1), rgb(178, 24, 43)
                        )
                    ),
                    heatmapWeight(
                        interpolate(
                            linear(), get(PROPERTY_MAG),
                            stop(0, 0),
                            stop(6, 1)
                        )
                    ),
                    heatmapIntensity(
                        interpolate(
                            linear(), zoom(),
                            stop(0, 1),
                            stop(9, 3)
                        )
                    ),
                    heatmapRadius(
                        interpolate(
                            linear(), zoom(),
                            stop(0, 2),
                            stop(9, 20)
                        )
                    ),
                    heatmapOpacity(
                        interpolate(
                            linear(), zoom(),
                            stop(7, 1),
                            stop(9, 0)
                        )
                    )
                )
            }
            style.addLayer(layer)
        }
    }

    private fun addCircleLayer(style: Style) {
        if (style.getLayer(CIRCLE_LAYER_ID) == null) {
            val circleLayer = CircleLayer(CIRCLE_LAYER_ID, HEAT_MAP_SOURCE_ID).apply {
                setProperties(
                    circleRadius(
                        interpolate(
                            linear(), zoom(),
                            literal(7), interpolate(
                                linear(), get(PROPERTY_MAG),
                                stop(1, 1),
                                stop(6, 4)
                            ),
                            literal(16), interpolate(
                                linear(), get(PROPERTY_MAG),
                                stop(1, 5),
                                stop(6, 50)
                            )
                        )
                    ),
                    circleColor(
                        interpolate(
                            linear(), get(PROPERTY_MAG),
                            literal(1), rgba(33, 102, 172, 0),
                            literal(2), rgb(103, 169, 207),
                            literal(3), rgb(209, 229, 240),
                            literal(4), rgb(253, 219, 199),
                            literal(5), rgb(239, 138, 98),
                            literal(6), rgb(178, 24, 43)
                        )
                    ),
                    circleOpacity(
                        interpolate(
                            linear(), zoom(),
                            stop(7, 0),
                            stop(8, 1)
                        )
                    ),
                    circleStrokeColor("white"),
                    circleStrokeWidth(1.0f)
                )
            }
            style.addLayerBelow(circleLayer, HEAT_MAP_LAYER_ID)
        }
    }

    fun addHeatMap() {
        val features = builder.heatMapOptionList.mapNotNull { option ->
            option.mag?.let {
                Feature.fromGeometry(option.point).apply {
                    addNumberProperty(PROPERTY_MAG, it)
                }
            }
        }
        featureCollection = FeatureCollection.fromFeatures(features)
        updateState()
    }

    class Builder internal constructor(
        private val mapplsMap: MapplsMap,
        private val mapView: MapView
    ) {
        internal var maxZoom: Float = 9.0f
        internal val heatMapOptionList: MutableList<HeatMapOption> = mutableListOf()

        fun maxZoom(maxZoom: Float) = apply { this.maxZoom = maxZoom }

        fun addAll(heatMapData: List<HeatMapOption>) = apply { heatMapOptionList.addAll(heatMapData) }

        fun add(heatMapOption: HeatMapOption) = apply { heatMapOptionList.add(heatMapOption) }

        fun build(): HeatMapPlugin {
            return HeatMapPlugin(mapplsMap, mapView, this)
        }
    }

    data class HeatMapOption(
        val point: Point,
        val mag: Double?
    )
}
