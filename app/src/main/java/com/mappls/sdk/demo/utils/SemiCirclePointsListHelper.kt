package com.mappls.sdk.demo.utils

import com.mappls.sdk.maps.geometry.LatLng
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object SemiCirclePointsListHelper {

    private const val EARTH_RADIUS = 6371009.0 // Earth's radius in meters

    /**
     * Computes a list of LatLng points forming a semicircular arc between two points.
     *
     * @param p1 Start point
     * @param p2 End point
     * @param k  Curvature factor (positive value; higher values result in more pronounced curves)
     * @return List of LatLng points representing the semicircular arc
     */
    fun showCurvedPolyline(p1: LatLng, p2: LatLng, k: Double): List<LatLng> {
        val latLngs = mutableListOf<LatLng>()

        val d = computeDistanceBetween(p1, p2)
        val h = computeHeading(p1, p2)

        val midPoint = computeOffset(p1, d * 0.5, h)

        val x = (1 - k * k) * d * 0.5 / (2 * k)
        val r = (1 + k * k) * d * 0.5 / (2 * k)

        val center = computeOffset(midPoint, x, h + 90.0)

        val h1 = computeHeading(center, p1)
        val h2 = computeHeading(center, p2)

        val numPoints = 100
        val step = (h2 - h1) / numPoints

        for (i in 0..numPoints) {
            val heading = h1 + i * step
            val point = computeOffset(center, r, heading)
            latLngs.add(point)
        }

        return latLngs
    }

    private fun computeHeading(from: LatLng, to: LatLng): Double {
        val fromLat = Math.toRadians(from.latitude)
        val fromLng = Math.toRadians(from.longitude)
        val toLat = Math.toRadians(to.latitude)
        val toLng = Math.toRadians(to.longitude)
        val dLng = toLng - fromLng
        val heading = atan2(
            sin(dLng) * cos(toLat),
            cos(fromLat) * sin(toLat) - sin(fromLat) * cos(toLat) * cos(dLng)
        )
        return wrap(Math.toDegrees(heading), -180.0, 180.0)
    }

    private fun wrap(n: Double, min: Double, max: Double): Double {
        return if (n >= min && n < max) n else mod(n - min, max - min) + min
    }

    private fun mod(x: Double, m: Double): Double {
        return (x % m + m) % m
    }

    private fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng {
        val distanceRad = distance / EARTH_RADIUS
        val headingRad = Math.toRadians(heading)
        val fromLat = Math.toRadians(from.latitude)
        val fromLng = Math.toRadians(from.longitude)

        val sinDistance = sin(distanceRad)
        val cosDistance = cos(distanceRad)
        val sinFromLat = sin(fromLat)
        val cosFromLat = cos(fromLat)

        val sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * cos(headingRad)
        val dLng = atan2(
            sinDistance * cosFromLat * sin(headingRad),
            cosDistance - sinFromLat * sinLat
        )

        val lat = asin(sinLat)
        val lng = fromLng + dLng

        return LatLng(Math.toDegrees(lat), Math.toDegrees(lng))
    }

    private fun computeAngleBetween(from: LatLng, to: LatLng): Double {
        return distanceRadians(
            Math.toRadians(from.latitude),
            Math.toRadians(from.longitude),
            Math.toRadians(to.latitude),
            Math.toRadians(to.longitude)
        )
    }

    private fun computeDistanceBetween(from: LatLng, to: LatLng): Double {
        return computeAngleBetween(from, to) * EARTH_RADIUS
    }

    private fun distanceRadians(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        return arcHav(havDistance(lat1, lat2, lng1 - lng2))
    }

    private fun havDistance(lat1: Double, lat2: Double, dLng: Double): Double {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2)
    }

    private fun hav(x: Double): Double {
        val sinHalf = sin(x * 0.5)
        return sinHalf * sinHalf
    }

    private fun arcHav(x: Double): Double {
        return 2.0 * asin(sqrt(x))
    }
}