package com.mappls.sdk.demo.kotlin.model

import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.geometry.LatLng

class GeofenceDetail {


    companion object {
        var TYPE_POLYGON = "POLYGON"
        var TYPE_CIRCLE = "CIRCLE"
    }

    var gfLabel: String? = null
    var gfType: String? = null
    var circleRadius: Int? = null
    var active = false
    var circleCentre: LatLng? = null
    var polygonPoints: List<Point>? = null




}