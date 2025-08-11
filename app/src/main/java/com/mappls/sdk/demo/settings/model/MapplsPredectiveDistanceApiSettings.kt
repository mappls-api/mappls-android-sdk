package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.services.api.directions.DirectionsCriteria

class MapplsPredectiveDistanceApiSettings private constructor() {

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

    var isCurrentTime: Boolean = true
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @DirectionsCriteria.SpecifiedTypeCriteria
    var locationType: Int = DirectionsCriteria.SPECIFIED_DEPARTURE
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @DirectionsCriteria.SpecifiedTypeCriteria
        get() = field

    var specifiedTime: Long? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field


    companion object {
        val instance: MapplsPredectiveDistanceApiSettings by lazy { MapplsPredectiveDistanceApiSettings() }
    }
}