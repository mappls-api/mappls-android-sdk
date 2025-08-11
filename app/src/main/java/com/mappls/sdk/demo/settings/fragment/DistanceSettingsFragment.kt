package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mappls.sdk.demo.activity.restapis.DistanceActivity
import com.mappls.sdk.demo.databinding.FragmentDistanceSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsDistanceApiSettings
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.utils.MapplsUtils

class DistanceSettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentDistanceSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDistanceSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.distanceSettingsHeader.headerTitle.text = "Distance Setting"
        mBinding.distanceSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as DistanceActivity).popBackStack(this)
        }
        setValues()
        mBinding.btnDistanceOriginLocation.setOnClickListener {
            val origin = mBinding.etDistanceOriginLocation.text.toString()
            if(origin.isNotEmpty()) {
                MapplsDistanceApiSettings.instance.origin = origin
            } else {
                Toast.makeText(requireContext(), "Please enter Origin Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDistanceDestinationLocation.setOnClickListener {
            val destination = mBinding.etDistanceDestinationLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsDistanceApiSettings.instance.destination = destination
            } else {
                Toast.makeText(requireContext(), "Please enter Destination Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDistanceWaypointLocation.setOnClickListener {
            val destination = mBinding.etDistanceWaypointLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsDistanceApiSettings.instance.wayPoints = destination
            } else {
                MapplsDistanceApiSettings.instance.wayPoints = null
            }
        }
        mBinding.rgRtype.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDistanceApiSettings.instance.routeType = when(rg.checkedRadioButtonId) {
                mBinding.rbDistanceRtypeOptimal.id -> {
                    DirectionsCriteria.DISTANCE_ROUTE_TYPE_OPTIMAL
                }
                mBinding.rbDistanceRtypeShortest.id -> {
                    DirectionsCriteria.DISTANCE_ROUTE_TYPE_SHORTEST
                }
                else ->
                    null
            }

        }
        mBinding.btnClearRType.setOnClickListener {
            mBinding.rgRtype.clearCheck()
            MapplsDistanceApiSettings.instance.routeType = null
        }
        mBinding.btnSources.setOnClickListener {
            val sources = mBinding.etDistanceSources.text.toString()
            if(sources.isNotEmpty()) {
                val sourcesList = mutableListOf<Int>()
                sources.split(";").forEach {
                    val index = it.toIntOrNull()
                    if(index!= null) {
                        sourcesList.add(index)
                    }
                }
                MapplsDistanceApiSettings.instance.sources = sourcesList
            } else {
                MapplsDistanceApiSettings.instance.sources = null
            }
        }
        mBinding.btnDestinations.setOnClickListener {
            val destinations = mBinding.etDistanceDestinations.text.toString()
            if(destinations.isNotEmpty()) {
                val destinationsList = mutableListOf<Int>()
                destinations.split(";").forEach {
                    val index = it.toIntOrNull()
                    if(index!= null) {
                        destinationsList.add(index)
                    }
                }
                MapplsDistanceApiSettings.instance.destinations = destinationsList
            } else {
                MapplsDistanceApiSettings.instance.destinations = null
            }
        }
        mBinding.btnFallbackSpeed.setOnClickListener {
            val fallbackSpeed = mBinding.etDistanceFallbackSpeed.text.toString()
            if(fallbackSpeed.isNotEmpty() && fallbackSpeed.toDoubleOrNull() != null) {
                MapplsDistanceApiSettings.instance.fallbackSpeed = fallbackSpeed.toDouble()
            } else {
                MapplsDistanceApiSettings.instance.fallbackSpeed = null
            }
        }
    }

    private fun setValues() {
        mBinding.etDistanceOriginLocation.setText(MapplsDistanceApiSettings.instance.origin)
        mBinding.etDistanceDestinationLocation.setText(MapplsDistanceApiSettings.instance.destination)
        mBinding.etDistanceWaypointLocation.setText(MapplsDistanceApiSettings.instance.wayPoints ?: "")
        when(MapplsDistanceApiSettings.instance.routeType) {
            DirectionsCriteria.DISTANCE_ROUTE_TYPE_OPTIMAL -> {
                mBinding.rgRtype.check(mBinding.rbDistanceRtypeOptimal.id)
            }
            DirectionsCriteria.DISTANCE_ROUTE_TYPE_SHORTEST -> {
                mBinding.rgRtype.check(mBinding.rbDistanceRtypeShortest.id)
            }
        }
        val sources = MapplsDistanceApiSettings.instance.sources
        if(sources != null) {
            mBinding.etDistanceSources.setText(MapplsUtils.join(";", sources.toTypedArray()))
        }
        val destinations = MapplsDistanceApiSettings.instance.destinations
        if(destinations != null) {
            mBinding.etDistanceDestinations.setText(MapplsUtils.join(";", destinations.toTypedArray()))
        }
        mBinding.etDistanceFallbackSpeed.setText(MapplsDistanceApiSettings.instance.fallbackSpeed?.toString() ?: "")
    }

}