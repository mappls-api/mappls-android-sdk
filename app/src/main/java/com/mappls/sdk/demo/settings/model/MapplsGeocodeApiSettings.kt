package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.services.api.geocoding.GeoCodingCriteria


class MapplsGeocodeApiSettings private constructor() {


    var itemCount: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var clientAppName: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @GeoCodingCriteria.BiasCriteria
    var bias: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @GeoCodingCriteria.BiasCriteria
        get() = field

    @GeoCodingCriteria.PODCriteria
    var podFilter: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @GeoCodingCriteria.PODCriteria
        get() = field

    var bound: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var scores: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field



    companion object {
        val instance: MapplsGeocodeApiSettings by lazy { MapplsGeocodeApiSettings() }
    }
}