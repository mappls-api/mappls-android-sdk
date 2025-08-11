package com.mappls.sdk.demo.model

import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsRequest
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsType

data class GeoAnalyticsDataModel(
    val geoAnalticsType: MapplsGeoAnalyticsType,
    val geoAnaltyticsParams: MapplsGeoAnalyticsRequest,
    val geoBoundType: String,
    val geoBound: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeoAnalyticsDataModel

        if (geoAnalticsType != other.geoAnalticsType) return false
        if (geoAnaltyticsParams != other.geoAnaltyticsParams) return false
        if (geoBoundType != other.geoBoundType) return false
        if (!geoBound.contentEquals(other.geoBound)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = geoAnalticsType.hashCode()
        result = 31 * result + geoAnaltyticsParams.hashCode()
        result = 31 * result + geoBoundType.hashCode()
        result = 31 * result + geoBound.contentHashCode()
        return result
    }
}
