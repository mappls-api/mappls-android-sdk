package com.mappls.sdk.demo.settings.model

import android.graphics.Color

class MapplsGeofenceSetting {


    var circleOutlineWidth: Float = 1f
    var circleFillColor: Int = Color.parseColor("#D81B60")
    var circleFillOutlineColor: Int = Color.parseColor("#511050")
    var draggingLineColor: Int = Color.parseColor("#000000")
    var maxRadius: Int = 1000
    var minRadius: Int = 25

    var polygonDrawingLineColor: Int = Color.parseColor("#000000")
    var polygonFillColor: Int = Color.parseColor("#511050")
    var polygonFillOutlineColor: Int = Color.parseColor("#511050")
    var polygonOutlineWidth: Float = 1f
    var simplifyWhenIntersectingPolygonDetected: Boolean = false
    var isPolygon: Boolean = false

    var seekbarPrimaryColor: Int = Color.parseColor("#D81B60")
    var seekbarSecondaryColor: Int = Color.parseColor("#511050")
    var seekbarCornerRadius: Float = 5f
    var showSeekBar: Boolean = true

    companion object{
        val instance:MapplsGeofenceSetting by lazy {
            MapplsGeofenceSetting()
        }
    }
}