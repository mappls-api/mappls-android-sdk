package com.mappls.sdk.demo.model

import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.geometry.LatLng

data class GeofenceDetailModel(
    var gfLabel: String? = null,
    var gfType: String? = null,
    var circleRadius: Int? = null,
    var active: Boolean = false,
    var circleCentre: LatLng? = null,
    var polygonPoints: List<Point>? = null
) {
    companion object {
        const val TYPE_POLYGON = "POLYGON"
        const val TYPE_CIRCLE = "CIRCLE"
    }

    fun getGPS(): List<Point>? = polygonPoints
    fun setGPS(gps: List<Point>?) {
        polygonPoints = gps
    }
}
