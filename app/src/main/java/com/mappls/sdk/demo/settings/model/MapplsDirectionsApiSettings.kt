package com.mappls.sdk.demo.settings.model

import androidx.annotation.FloatRange
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.WalkingOptions

class MapplsDirectionsApiSettings private constructor() {

    var origin: String = "77.268935,28.550791"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var destination: String = "77.2002561,28.6129131"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var wayPoints: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @DirectionsCriteria.OverviewCriteria
    var overview: String = DirectionsCriteria.OVERVIEW_FULL
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @DirectionsCriteria.OverviewCriteria
        get() = field

    var isAlternatives: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @DirectionsCriteria.GeometriesCriteria
    var geometries: String = DirectionsCriteria.GEOMETRY_POLYLINE6
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @DirectionsCriteria.GeometriesCriteria
        get() = field

    var steps: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var lessVerbose: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var roundaboutExits: Boolean = true
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var annotations: MutableList<String> = mutableListOf()
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var excludes: MutableList<String> = mutableListOf()
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var continueStraight: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var bannerInstructions: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var routeRefresh: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var sessionId: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var isSort: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var skipWaypoints: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var instructions: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @DirectionsCriteria.RouteType
    var routeType: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @DirectionsCriteria.RouteType
        get() = field

    var user:String?=""
        set(value){ field = value }
        get() = field
    var language:String?=""
        set(value){ field = value }
        get() = field

    @FloatRange(from = 0.0, to = 360.0)
    var bearing: Double? = null
        set(value) {
            field = value
        }
        get() = field

    var radius: Double ?= null
        set(value){ field = value }
        get() = field
    var clientAppName:String?=""
        set(value){ field = value }
        get() = field
    var addApproaches:String?=""
        set(value){ field = value }
        get() = field
    var addWaypointIndices:Int ?=null
        set(value){ field = value }
        get() = field

    var addWaypointNames:String?= ""
        set(value){ field = value }
        get() = field
    var addWaypointTargets:String? = null
        set(value){ field = value }
        get() = field
    var excludeContainmentZone:Boolean = false
        set(value){ field = value }
        get() = field
    var walkingOptions: WalkingOptions?=null
        set(value){ field = value }
        get() = field



    //walkingOptions

    companion object {
        val instance: MapplsDirectionsApiSettings by lazy { MapplsDirectionsApiSettings() }
    }

}