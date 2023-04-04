package com.mappls.sdk.demo.kotlin.settings

import com.mappls.sdk.demo.R
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions

class MapplsPlaceWidgetSetting {
    var isDefault = true
    var signatureVertical = PlaceOptions.GRAVITY_TOP
    var signatureHorizontal = PlaceOptions.GRAVITY_LEFT
    var logoSize = PlaceOptions.SIZE_MEDIUM
    var location: Point? = null
    var filter: String? = null
    var isEnableHistory = false
    var pod: String? = null
    var hint = "Search Here"
    var isEnableTextSearch = false
    var isEnableLocation = false
    var backgroundColor = R.color.white
    var toolbarColor = R.color.white
    var isBridgeEnable = false
    var isHyperLocalEnable = false

    companion object {
        val instance = MapplsPlaceWidgetSetting()
    }
}
