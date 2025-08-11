package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.demo.R
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions


class MapplsPlacePickerSettings {

    var pickerToolbarColor: Int = R.color.white
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var includeDeviceLocation: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var includeSearch: Boolean = false
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

    var tokenizeAddress: Boolean =false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var enableFootPrint: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var footprintFillColor: String = "#ffff44"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var footprintStrokeColor: String = "#ffff44"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var entryCoordinateCircleColor :String ="#0000FF"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var entryCoordinateSnapEnable:Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var entryCoordinateSnappingRadius:Int = 50
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var showEntryCoordinate:Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var entryCoordinateCircleRadius:Float = 8.0f
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field
    var footprintFillOpacity: Double =0.3
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var footprintLineStrokeOpacity: Double = 1.0
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var footprintLineStrokeWidth: Int = 2
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var backgroundColor: Int = R.color.white
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var toolbarColor: Int  = R.color.white
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var toolbarTintColor: Int ?= null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var hint: String ?= null
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

    var logoSize: Int  =  PlaceOptions.SIZE_SMALL
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




    companion object{
        val instance : MapplsPlacePickerSettings by lazy {
            MapplsPlacePickerSettings()
        }
    }
}