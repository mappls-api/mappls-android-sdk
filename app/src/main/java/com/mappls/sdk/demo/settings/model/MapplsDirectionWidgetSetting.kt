package com.mappls.sdk.demo.settings.model

import android.R
import androidx.core.content.ContextCompat
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.Mappls.getApplicationContext
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.directions.DirectionsCriteria


class MapplsDirectionWidgetSetting {
    var showAlternative: Boolean = true
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field


    var showStartNavigation: Boolean = true
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var steps: Boolean = true
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var resource = DirectionsCriteria.RESOURCE_ROUTE
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var profile = DirectionsCriteria.PROFILE_DRIVING
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var overview = DirectionsCriteria.OVERVIEW_FULL
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var excludes: MutableList<String> = mutableListOf()
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field


    var placeNameTextColor = ContextCompat.getColor(getApplicationContext(), R.color.black)
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var addressTextColor = ContextCompat.getColor(getApplicationContext(), R.color.black)
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field


    var location: Point? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var filter: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var enableHistory: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var pod: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var tokenizeAddress: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field


    var backgroundColor: Int = ContextCompat.getColor(getApplicationContext(), R.color.white)
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var toolbarColor: Int = ContextCompat.getColor(getApplicationContext(), R.color.white)
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var toolbarTintColor: Int = ContextCompat.getColor(getApplicationContext(), R.color.black)
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var hint: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var signatureVertical: Int = PlaceOptions.GRAVITY_TOP
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var signatureHorizontal: Int = PlaceOptions.GRAVITY_LEFT
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var logoSize: Int = PlaceOptions.SIZE_SMALL
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var historyCount: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var zoom: Double? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var isShowPOISearch: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field


    companion object {
        val instance: MapplsDirectionWidgetSetting by lazy {
            MapplsDirectionWidgetSetting()
        }
    }
}