package com.mappls.sdk.demo.settings.model

import com.mappls.sdk.services.api.nearby.NearbyCriteria

class MapplsNearbyApiSettings private constructor() {

    var keyword: String = "Tea"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var enableCurrentLocation = true
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var customLocation: String = "28.550629,77.268859"
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var page: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @NearbyCriteria.SortingCriteria
    var sortBy: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @NearbyCriteria.SortingCriteria
        get() = field

    @NearbyCriteria.SearchingCriteria
    var searchBy: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @NearbyCriteria.SearchingCriteria
        get() = field


    var radius: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var itemCount: Int? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var bounds: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    @NearbyCriteria.PodCriteria
    var pod: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        @NearbyCriteria.PodCriteria
        get() = field

    var filter: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var explain: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var richData: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var userName: String? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var ignoreAutoExpand: Boolean = false
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    var includes: List<String>? = null
        set(value) {
            field = value // Use 'field' to assign the value to the backing field
        }
        get() = field

    companion object {
        val instance: MapplsNearbyApiSettings by lazy { MapplsNearbyApiSettings() }
    }
}