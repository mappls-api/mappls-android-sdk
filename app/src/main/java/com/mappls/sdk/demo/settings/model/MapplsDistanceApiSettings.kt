package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.services.api.directions.DirectionsCriteria

class MapplsDistanceApiSettings private constructor() {

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

    @DirectionsCriteria.DistanceRouteType
    var routeType: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @DirectionsCriteria.DistanceRouteType
        get() = field

    var sources: List<Int>? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var destinations: List<Int>? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var fallbackSpeed: Double? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var fallbackCoordinate: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    //dateTime

    companion object {
        val instance: MapplsDistanceApiSettings by lazy { MapplsDistanceApiSettings() }
    }
}