package com.mappls.sdk.demo.kotlin.model

import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsRequest
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsType


/**
 * Created by Saksham on 27-09-2021
 */

data class GeoAnalyticsModel(
    val type: MapplsGeoAnalyticsType,
    val params: MapplsGeoAnalyticsRequest,
    val geoboundType: String,
    val geoBound: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeoAnalyticsModel

        if (type != other.type) return false
        if (params != other.params) return false
        if (geoboundType != other.geoboundType) return false
        if (!geoBound.contentEquals(other.geoBound)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + params.hashCode()
        result = 31 * result + geoboundType.hashCode()
        result = 31 * result + geoBound.contentHashCode()
        return result
    }
}