package com.mappls.sdk.demo.kotlin.settings

import com.mappls.sdk.demo.R
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions

 class MapplsPlacePickerSetting {


     companion object {
         val instance = MapplsPlacePickerSetting()
     }


    var isDefault = true
    var pickerToolbarColor = R.color.mappls_demo_white
    var isIncludeDeviceLocation = true
    var isIncludeSearch = true
    var location: Point? = null
    var filter: String? = null
    var isEnableHistory = false
    var pod: String? = null
    var isTokenizeAddress = true
    var backgroundColor = R.color.mappls_demo_white
    var toolbarColor = R.color.mappls_demo_white
     var hint = "Search Here"
    var signatureVertical = PlaceOptions.GRAVITY_TOP
    var signatureHorizontal = PlaceOptions.GRAVITY_LEFT
    var logoSize = PlaceOptions.SIZE_MEDIUM
    var historyCount: Int? = null
    var zoom: Double? = null




}
