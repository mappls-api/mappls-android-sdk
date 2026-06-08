package com.mappls.sdk.demo.settings.model

import android.graphics.Color
import com.mappls.sdk.demo.R
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions


class AutoCompleteWidgetSetting private constructor() {

    var location: Point = Point.fromLngLat(77.23388671875,22.553147478403194)
    var userAddedLocationEnable: Boolean = false
    var filter: String? = null
    var historyCount: Int? = null
    var favoriteCount: Int? = null
    var saveHistory: Boolean = true
    var enableTextSearch: Boolean = true
    var pod: String? = null
    var tokenizeAddress: Boolean = false
    var backgroundColor: Int =  Color.WHITE
    var resultBackgroundColor: Int =  Color.WHITE
    var placeNameTextColor: Int = 0xFF000000.toInt()
    var addressTextColor: Int = 0xFF888888.toInt()
    var savedPlaceNameTextColor: Int = 0xFF000000.toInt()
    var favoritePlaceNameTextColor: Int = 0xFF000000.toInt()
    var distanceTextColor: Int = 0xFF000000.toInt()
    var errorBackgroundColor: Int = 0xFFFFCDD2.toInt()
    var errorTextColor: Int = 0xFFD32F2F.toInt()
    var toolbarColor: Int =  Color.WHITE
    var statusBarColor: Int? = null
    var showPoweredByText: Boolean = true
    var poweredByTextColor: Int = 0xFF000000.toInt()
    var toolbarTintColor: Int = 0xFFFFFFFF.toInt()
    var attributionVerticalAlignment: Int = 0 // TOP/BOTTOM
    var attributionHorizontalAlignment: Int = 0 // LEFT/RIGHT/CENTER
    var attributionBackgroundColor: Int = 0xFFE0E0E0.toInt()
    var logoSize: Int = PlaceOptions.SIZE_MEDIUM
    var internalMinCharactersForSearch: Int = 3
    var hint: String? = null
    var hyperLocal: Boolean = false
    var bridge: Boolean = false
    var isShowCurrentLocation: Boolean = true
    var currentLocationTextColor: Int = 0xFF000000.toInt()
    var currentLocationBackground: Int = 0xFFBDBDBD.toInt()
    var currentLocationIcon: Int = android.R.drawable.ic_menu_mylocation
    var responseLang: String? = null
    var hintColor: Int? = null

    companion object {
        @JvmStatic
        val instance: AutoCompleteWidgetSetting by lazy { AutoCompleteWidgetSetting() }
    }
}
