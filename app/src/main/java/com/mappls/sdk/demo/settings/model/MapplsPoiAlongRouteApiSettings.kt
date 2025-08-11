package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.services.api.alongroute.POICriteria

class MapplsPoiAlongRouteApiSettings private constructor() {

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

    var category: String = "FODOTH"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var buffer: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var page: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var sort: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var filter: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @POICriteria.GeometriesCriteria
    var geometry: String = POICriteria.GEOMETRY_POLYLINE6
        set(value) {
             field = value // Use 'field' to assign the value to the backing field
        }
        @POICriteria.GeometriesCriteria
        get() = field



    companion object {
        val instance: MapplsPoiAlongRouteApiSettings by lazy { MapplsPoiAlongRouteApiSettings() }
    }
}