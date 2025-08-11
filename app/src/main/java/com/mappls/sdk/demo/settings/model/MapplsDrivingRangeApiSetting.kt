package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.drivingrange.DrivingRangeCriteria
import com.mappls.sdk.geojson.Point

class MapplsDrivingRangeApiSetting {

    var location: Point = Point.fromLngLat(77.268935,28.550791)
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var isUsingCurrentLocation : Boolean = true
    set(value) {
        field = value // Use 'field' to assign the value to the backing field
    }
    get() = field

    var drivingProfile:String = "auto"
        set(value) {
            field = value
        }
        get() = field

    var value:Int = 50
        set(value) {
            field = value
        }
        get() = field

    var rangeType:String = DrivingRangeCriteria.RANGE_TYPE_TIME
        set(value) {
            field = value
        }
        get() = field

    var color:String = "ff0000"
        set(value) {
            field = value
        }
        get() = field


    var isForPolygons:Boolean = false
        set(value) {
            field = value
        }
        get() = field


    var lineWidth:Float = 4.0f
        set(value) {
            field = value
        }
        get() = field

    var outlineColor:String ?= null
        set(value) {
            field = value
        }
        get() = field

    var generalize:Float ?= null
        set(value) {
            field = value
        }
        get() = field

    var denoise:Float ?= null
        set(value) {
            field = value
        }
        get() = field
    var showLocations:Boolean = false
        set(value) {
            field = value
        }
        get() = field

    var name:String ?= null
        set(value) {
            field = value
        }
        get() = field
    var speedType:Int ?= null
        set(value) {
            field = value
        }
        get() = field
    var predectiveType:Int ?= null
        set(value) {
            field = value
        }
        get() = field

    var time: Long = currentTime
        set(value) {
            field = value
        }
        get() = field


    companion object{
        const val SPEED_TYPE_OPTIMAL: Int = 0
        const val SPEED_TYPE_PREDECTIVE: Int = 1
        const val PREDECTIVE_TYPE_CURRENT: Int = 0
        const val PREDECTIVE_TYPE_CUSTOM: Int = 1
        private val speedType: Int = SPEED_TYPE_OPTIMAL
        private val predectiveType: Int = PREDECTIVE_TYPE_CURRENT
        private var currentTime = System.currentTimeMillis()
        val instance:MapplsDrivingRangeApiSetting by lazy {
            MapplsDrivingRangeApiSetting()
        }
    }
}