package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria

class MapplsAutosuggestApiSettings private constructor() {

    var enableCurrentLocation = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var customLocation: LatLng? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var tokenizeAddress: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var bridge: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @AutoSuggestCriteria.PODCriteria
    var pod: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @AutoSuggestCriteria.PODCriteria
        get() = field

    var filter: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var hyperLocal: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var responseLang: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var explain: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var enableMapCenter: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var isPrimary: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var zoom: Double? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    companion object {
        val instance: MapplsAutosuggestApiSettings by lazy { MapplsAutosuggestApiSettings() }
    }
}