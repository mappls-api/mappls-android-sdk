package com.mappls.sdk.demo.settings.model

class MapplsReverseGeocodeApiSettings private constructor() {

    var lang: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    companion object {
        val instance: MapplsReverseGeocodeApiSettings by lazy { MapplsReverseGeocodeApiSettings() }
    }
}