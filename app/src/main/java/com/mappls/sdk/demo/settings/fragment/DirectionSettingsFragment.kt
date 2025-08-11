package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mappls.sdk.demo.activity.restapis.DirectionsActivity
import com.mappls.sdk.demo.databinding.FragmentDirectionSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsDirectionsApiSettings
import com.mappls.sdk.services.api.directions.DirectionsCriteria

class DirectionSettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentDirectionSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDirectionSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.settingsHeader.headerTitle.text = "Directions Setting"
        mBinding.settingsHeader.headerBack.setOnClickListener {
            (requireActivity() as DirectionsActivity).popBackStack(this)
        }
        setValues()
        mBinding.btnDirectionOriginLocation.setOnClickListener {
            val origin = mBinding.etDirectionOriginLocation.text.toString()
            if(origin.isNotEmpty()) {
                MapplsDirectionsApiSettings.instance.origin = origin
            } else {
                Toast.makeText(requireContext(), "Please enter Origin Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDirectionDestinationLocation.setOnClickListener {
            val destination = mBinding.etDirectionDestinationLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsDirectionsApiSettings.instance.destination = destination
            } else {
                Toast.makeText(requireContext(), "Please enter Destination Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDirectionWaypointLocation.setOnClickListener {
            val destination = mBinding.etDirectionWaypointLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsDirectionsApiSettings.instance.wayPoints = destination
            } else {
                MapplsDirectionsApiSettings.instance.wayPoints = null
            }
        }
        mBinding.rgOverview.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDirectionsApiSettings.instance.overview = when(rg.checkedRadioButtonId) {
                mBinding.rbOverviewFull.id -> {
                    DirectionsCriteria.OVERVIEW_FULL
                }
                mBinding.rbOverviewSimplified.id -> {
                    DirectionsCriteria.OVERVIEW_SIMPLIFIED
                }
                mBinding.rbOverviewFalse.id -> {
                    DirectionsCriteria.OVERVIEW_FALSE
                }
                else ->
                    DirectionsCriteria.OVERVIEW_FULL
            }
        }
        mBinding.swDirectionAlternative.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.isAlternatives = isChecked
        }
        mBinding.rgGeometry.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDirectionsApiSettings.instance.geometries = when(rg.checkedRadioButtonId) {
                mBinding.rbGeometryPolylineFive.id -> {
                    DirectionsCriteria.GEOMETRY_POLYLINE
                }
                mBinding.rbGeometryPolylineSix.id -> {
                    DirectionsCriteria.GEOMETRY_POLYLINE6
                }
                mBinding.rbGeometryCoordiates.id -> {
                    DirectionsCriteria.GEOMETRY_COORDINATES
                }
                else ->
                    DirectionsCriteria.GEOMETRY_POLYLINE6
            }
        }
        mBinding.swDirectionSteps.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.steps = isChecked
        }
        mBinding.swDirectionLessVerbose.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.lessVerbose = isChecked
        }
        mBinding.swDirectionRoundaboutexit.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.roundaboutExits = isChecked
        }
        mBinding.cbAnnotationCongestion.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_CONGESTION)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_CONGESTION)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_CONGESTION)
            }
        }
        mBinding.cbAnnotationNodes.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_NODES)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_NODES)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_NODES)
            }
        }
        mBinding.cbAnnotationDuration.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_DURATION)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_DURATION)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_DURATION)
            }
        }
        mBinding.cbAnnotationDistance.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_DISTANCE)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_DISTANCE)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_DISTANCE)
            }
        }
        mBinding.cbAnnotationSpeed.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_SPEED)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_SPEED)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_SPEED)
            }
        }
        mBinding.cbAnnotationMaxSpeed.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_MAXSPEED)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_MAXSPEED)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_MAXSPEED)
            }
        }
        mBinding.cbAnnotationBaseDuration.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_BASE_DURATION)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_BASE_DURATION)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_BASE_DURATION)
            }
        }
        mBinding.cbAnnotationSpdlmt.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_SPEED_LIMIT)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_SPEED_LIMIT)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_SPEED_LIMIT)
            }
        }
        mBinding.cbAnnotationTollRoad.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.annotations.contains(DirectionsCriteria.ANNOTATION_TOLL_ROAD)) {
                    MapplsDirectionsApiSettings.instance.annotations.add(DirectionsCriteria.ANNOTATION_TOLL_ROAD)
                }
            } else {
                MapplsDirectionsApiSettings.instance.annotations.remove(DirectionsCriteria.ANNOTATION_TOLL_ROAD)
            }
        }
        mBinding.cbExcludeToll.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.excludes.contains(DirectionsCriteria.EXCLUDE_TOLL)) {
                    MapplsDirectionsApiSettings.instance.excludes.add(DirectionsCriteria.EXCLUDE_TOLL)
                }
            } else {
                MapplsDirectionsApiSettings.instance.excludes.remove(DirectionsCriteria.EXCLUDE_TOLL)
            }
        }
        mBinding.cbExcludeFerry.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.excludes.contains(DirectionsCriteria.EXCLUDE_FERRY)) {
                    MapplsDirectionsApiSettings.instance.excludes.add(DirectionsCriteria.EXCLUDE_FERRY)
                }
            } else {
                MapplsDirectionsApiSettings.instance.excludes.remove(DirectionsCriteria.EXCLUDE_FERRY)
            }
        }
        mBinding.cbExcludeTunnel.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.excludes.contains(DirectionsCriteria.EXCLUDE_TUNNEL)) {
                    MapplsDirectionsApiSettings.instance.excludes.add(DirectionsCriteria.EXCLUDE_TUNNEL)
                }
            } else {
                MapplsDirectionsApiSettings.instance.excludes.remove(DirectionsCriteria.EXCLUDE_TUNNEL)
            }
        }
        mBinding.cbExcludeMotorway.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.excludes.contains(DirectionsCriteria.EXCLUDE_MOTORWAY)) {
                    MapplsDirectionsApiSettings.instance.excludes.add(DirectionsCriteria.EXCLUDE_MOTORWAY)
                }
            } else {
                MapplsDirectionsApiSettings.instance.excludes.remove(DirectionsCriteria.EXCLUDE_MOTORWAY)
            }
        }
        mBinding.cbExcludeRestricted.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionsApiSettings.instance.excludes.contains(DirectionsCriteria.EXCLUDE_RESTRICTED)) {
                    MapplsDirectionsApiSettings.instance.excludes.add(DirectionsCriteria.EXCLUDE_RESTRICTED)
                }
            } else {
                MapplsDirectionsApiSettings.instance.excludes.remove(DirectionsCriteria.EXCLUDE_RESTRICTED)
            }
        }
        mBinding.swDirectionContinuestraight.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.continueStraight = isChecked
        }
        mBinding.swDirectionRouteRefresh.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.routeRefresh = isChecked
        }
        mBinding.btnDirectionSessionId.setOnClickListener {
            val sessionId = mBinding.etDirectionSessionId.text.toString()
            if(sessionId.isEmpty()) {
                MapplsDirectionsApiSettings.instance.sessionId = null
            } else {
                MapplsDirectionsApiSettings.instance.sessionId = sessionId
            }
        }
        mBinding.swDirectionSort.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.isSort  = isChecked
        }
        mBinding.swDirectionSkipWaypoint.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.skipWaypoints  = isChecked
        }
        mBinding.swDirectionInstructions.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.instructions  = isChecked
        }
        mBinding.rgRtype.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDirectionsApiSettings.instance.routeType = when(rg.checkedRadioButtonId) {
                mBinding.rbDirectionRtypeOptimal.id -> {
                    DirectionsCriteria.ROUTE_TYPE_OPTIMAL
                }
                mBinding.rbDirectionRtypeShortest.id -> {
                    DirectionsCriteria.ROUTE_TYPE_SHORTEST
                }
                mBinding.rbDirectionRtypeContainmentZone.id -> {
                    DirectionsCriteria.ROUTE_TYPE_WITHOUT_CONTAINMENT_ZONE
                }
                else ->
                    null
            }

        }
        mBinding.btnClearRType.setOnClickListener {
            mBinding.rgRtype.clearCheck()
            MapplsDirectionsApiSettings.instance.routeType = null
        }
    }

    private fun setValues() {
        mBinding.etDirectionOriginLocation.setText(MapplsDirectionsApiSettings.instance.origin)
        mBinding.etDirectionDestinationLocation.setText(MapplsDirectionsApiSettings.instance.destination)
        mBinding.etDirectionWaypointLocation.setText(MapplsDirectionsApiSettings.instance.wayPoints ?: "")
        when(MapplsDirectionsApiSettings.instance.overview) {
            DirectionsCriteria.OVERVIEW_FULL ->
                mBinding.rgOverview.check(mBinding.rbOverviewFull.id)
            DirectionsCriteria.OVERVIEW_SIMPLIFIED ->
                mBinding.rgOverview.check(mBinding.rbOverviewSimplified.id)
            DirectionsCriteria.OVERVIEW_FALSE ->
                mBinding.rgOverview.check(mBinding.rbOverviewFalse.id)
        }
        mBinding.swDirectionAlternative.isChecked = MapplsDirectionsApiSettings.instance.isAlternatives
        when(MapplsDirectionsApiSettings.instance.geometries) {
            DirectionsCriteria.GEOMETRY_POLYLINE -> {
                mBinding.rgGeometry.check(mBinding.rbGeometryPolylineFive.id)
            }
            DirectionsCriteria.GEOMETRY_POLYLINE6 -> {
                mBinding.rgGeometry.check(mBinding.rbGeometryPolylineSix.id)
            }
            DirectionsCriteria.GEOMETRY_COORDINATES -> {
                mBinding.rgGeometry.check(mBinding.rbGeometryCoordiates.id)
            }
        }
        mBinding.swDirectionSteps.isChecked = MapplsDirectionsApiSettings.instance.steps
        mBinding.swDirectionLessVerbose.isChecked = MapplsDirectionsApiSettings.instance.lessVerbose
        mBinding.swDirectionRoundaboutexit.isChecked = MapplsDirectionsApiSettings.instance.roundaboutExits
        mBinding.cbAnnotationCongestion.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_CONGESTION
        )
        mBinding.cbAnnotationNodes.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_NODES
        )
        mBinding.cbAnnotationDuration.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_DURATION
        )
        mBinding.cbAnnotationDistance.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_DISTANCE
        )
        mBinding.cbAnnotationSpeed.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_SPEED
        )
        mBinding.cbAnnotationMaxSpeed.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_MAXSPEED
        )
        mBinding.cbAnnotationBaseDuration.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_BASE_DURATION
        )
        mBinding.cbAnnotationSpdlmt.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_SPEED_LIMIT
        )
        mBinding.cbAnnotationTollRoad.isChecked = MapplsDirectionsApiSettings.instance.annotations.contains(
            DirectionsCriteria.ANNOTATION_TOLL_ROAD
        )
        mBinding.cbExcludeToll.isChecked = MapplsDirectionsApiSettings.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_TOLL
        )
        mBinding.cbExcludeFerry.isChecked = MapplsDirectionsApiSettings.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_FERRY
        )
        mBinding.cbExcludeTunnel.isChecked = MapplsDirectionsApiSettings.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_TUNNEL
        )
        mBinding.cbExcludeMotorway.isChecked = MapplsDirectionsApiSettings.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_MOTORWAY
        )
        mBinding.cbExcludeRestricted.isChecked = MapplsDirectionsApiSettings.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_RESTRICTED
        )
        mBinding.swDirectionContinuestraight.isChecked = MapplsDirectionsApiSettings.instance.continueStraight
        mBinding.swDirectionRouteRefresh.isChecked = MapplsDirectionsApiSettings.instance.routeRefresh
        mBinding.etDirectionSessionId.setText(MapplsDirectionsApiSettings.instance.sessionId ?: "")
        mBinding.swDirectionSort.isChecked = MapplsDirectionsApiSettings.instance.isSort
        mBinding.swDirectionSkipWaypoint.isChecked = MapplsDirectionsApiSettings.instance.skipWaypoints
        mBinding.swDirectionInstructions.isChecked = MapplsDirectionsApiSettings.instance.instructions
        when(MapplsDirectionsApiSettings.instance.routeType) {
            DirectionsCriteria.ROUTE_TYPE_OPTIMAL -> {
                mBinding.rgRtype.check(mBinding.rbDirectionRtypeOptimal.id)
            }
            DirectionsCriteria.ROUTE_TYPE_SHORTEST -> {
                mBinding.rgRtype.check(mBinding.rbDirectionRtypeShortest.id)
            }
            DirectionsCriteria.ROUTE_TYPE_WITHOUT_CONTAINMENT_ZONE -> {
                mBinding.rgRtype.check(mBinding.rbDirectionRtypeContainmentZone.id)
            }
        }

        mBinding.etUser.setText(MapplsDirectionsApiSettings.instance.user ?: "")
        mBinding.btnUser.setOnClickListener {
            val userValue = mBinding.etUser.text.toString()
            if(userValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.user = null
            } else {
                MapplsDirectionsApiSettings.instance.user = userValue
            }
        }
        mBinding.etLanguage.setText(MapplsDirectionsApiSettings.instance.language ?: "")
        mBinding.btnLanguage.setOnClickListener {
            val languageValue = mBinding.etLanguage.text.toString()
            if(languageValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.language = null
            } else {
                MapplsDirectionsApiSettings.instance.language = languageValue
            }
        }

        mBinding.etBearing.setText((MapplsDirectionsApiSettings.instance.bearing).toString() ?: "")
        mBinding.btnBearing.setOnClickListener {
            val bearingValue = mBinding.etBearing.text.toString()
            if(bearingValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.bearing = null
            } else {
                MapplsDirectionsApiSettings.instance.bearing = bearingValue.toDouble()
            }
        }
        mBinding.etRadius.setText((MapplsDirectionsApiSettings.instance.radius).toString() ?: "")
        mBinding.btnRadius.setOnClickListener {
            val radiusValue = mBinding.etRadius.text.toString()
            if(radiusValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.radius = null
            } else {
                MapplsDirectionsApiSettings.instance.radius = radiusValue.toDouble()
            }
        }
        mBinding.etClientAppName.setText((MapplsDirectionsApiSettings.instance.clientAppName).toString() ?: "")
        mBinding.btnClientAppName.setOnClickListener {
            val clientValue = mBinding.etClientAppName.text.toString()
            if(clientValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.clientAppName = null
            } else {
                MapplsDirectionsApiSettings.instance.clientAppName = clientValue
            }
        }

        mBinding.etAddApproaches.setText((MapplsDirectionsApiSettings.instance.addApproaches).toString() ?: "")
        mBinding.btnAddApproaches.setOnClickListener {
            val approacheValue = mBinding.etAddApproaches.text.toString()
            if(approacheValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.addApproaches = null
            } else {
                MapplsDirectionsApiSettings.instance.addApproaches = approacheValue
            }
        }
        mBinding.etAddWaypointIndices.setText((MapplsDirectionsApiSettings.instance.addWaypointIndices).toString() ?: "")
        mBinding.btnAddWaypointIndices.setOnClickListener {
            val indexValue = mBinding.etAddWaypointIndices.text.toString()
            if(indexValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.addWaypointIndices = null
            } else {
                MapplsDirectionsApiSettings.instance.addWaypointIndices = indexValue.toInt()
            }
        }
        mBinding.etAddWaypointNames.setText((MapplsDirectionsApiSettings.instance.addWaypointNames).toString() ?: "")
        mBinding.btnAddApproaches.setOnClickListener {
            val nameValue = mBinding.etAddWaypointNames.text.toString()
            if(nameValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.addWaypointNames = null
            } else {
                MapplsDirectionsApiSettings.instance.addWaypointNames = nameValue
            }
        }
        mBinding.etAddWaypointTargets.setText((MapplsDirectionsApiSettings.instance.addWaypointTargets).toString() ?: "")
        mBinding.btnAddWaypointTargets.setOnClickListener {
            val targetValue = mBinding.etAddWaypointTargets.text.toString()
            if(targetValue.isEmpty()) {
                MapplsDirectionsApiSettings.instance.addWaypointTargets = null
            } else {
                MapplsDirectionsApiSettings.instance.addWaypointTargets = targetValue
            }
        }
        mBinding.excludeContainmentZoneLessVerbose.isChecked = MapplsDirectionsApiSettings.instance.excludeContainmentZone

        mBinding.excludeContainmentZoneLessVerbose.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDirectionsApiSettings.instance.excludeContainmentZone = isChecked
        }
    }


}