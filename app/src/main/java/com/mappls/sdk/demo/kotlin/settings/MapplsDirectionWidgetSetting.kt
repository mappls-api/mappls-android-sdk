package com.mappls.sdk.demo.kotlin.settings

import com.mappls.sdk.demo.R
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.directions.DirectionsCriteria

class MapplsDirectionWidgetSetting {
    var isDefault = true
    var isShowAlternative = true
    var isShowStartNavigation = true
    var isSteps = true
    var resource = DirectionsCriteria.RESOURCE_ROUTE
    var profile = DirectionsCriteria.PROFILE_DRIVING
    var overview = DirectionsCriteria.OVERVIEW_FULL
    var excludes: MutableList<String>? = null
    var destination: Point? = null
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
    var isShowPOISearch: Boolean = false;

    companion object {
        val instance = MapplsDirectionWidgetSetting()
    }
}
