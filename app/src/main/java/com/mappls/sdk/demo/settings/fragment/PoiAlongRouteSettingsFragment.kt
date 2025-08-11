package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mappls.sdk.demo.activity.restapis.PoiAlongRouteActivity
import com.mappls.sdk.demo.databinding.FragmentPoiAlongRouteSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsPoiAlongRouteApiSettings
import com.mappls.sdk.services.api.alongroute.POICriteria

class PoiAlongRouteSettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentPoiAlongRouteSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPoiAlongRouteSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.poiAlongRouteSettingsHeader.headerTitle.text = "Poi Along Route Setting"
        mBinding.poiAlongRouteSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as PoiAlongRouteActivity).popBackStack(this)
        }
        setValues()
        mBinding.btnPoiAlongRouteOriginLocation.setOnClickListener {
            val origin = mBinding.etPoiAlongRouteOriginLocation.text.toString()
            if(origin.isNotEmpty()) {
                MapplsPoiAlongRouteApiSettings.instance.origin = origin
            } else {
                Toast.makeText(requireContext(), "Please enter Origin Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnPoiAlongRouteDestinationLocation.setOnClickListener {
            val destination = mBinding.etPoiAlongRouteDestinationLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsPoiAlongRouteApiSettings.instance.destination = destination
            } else {
                Toast.makeText(requireContext(), "Please enter Destination Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnPoiAlongRouteCategory.setOnClickListener {
            val category = mBinding.etPoiAlongRouteCategory.text.toString()
            if(category.isNotEmpty()) {
                MapplsPoiAlongRouteApiSettings.instance.category = category
            } else {
                Toast.makeText(requireContext(), "Please enter Category", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnPoiAlongRouteBuffer.setOnClickListener {
            val buffer = mBinding.etPoiAlongRouteBuffer.text.toString()
            if(buffer.toIntOrNull() != null) {
                val value = buffer.toInt()
                if(value < 25) {
                    Toast.makeText(requireContext(), "Setting Buffer to 25", Toast.LENGTH_SHORT).show()
                    mBinding.etPoiAlongRouteBuffer.setText("25")
                    MapplsPoiAlongRouteApiSettings.instance.buffer = 25
                } else if(value > 1000) {
                    Toast.makeText(requireContext(), "Setting Buffer to 1000", Toast.LENGTH_SHORT).show()
                    mBinding.etPoiAlongRouteBuffer.setText("1000")
                    MapplsPoiAlongRouteApiSettings.instance.buffer = 1000
                } else {
                    MapplsPoiAlongRouteApiSettings.instance.buffer = value
                }
            } else {
                MapplsPoiAlongRouteApiSettings.instance.buffer = null
            }
        }
        mBinding.btnPoiAlongRoutePage.setOnClickListener {
            val page = mBinding.etPoiAlongRoutePage.text.toString()
            if(page.toIntOrNull() != null) {
                MapplsPoiAlongRouteApiSettings.instance.page = page.toInt()
            } else {
                MapplsPoiAlongRouteApiSettings.instance.page = null
            }
        }
        mBinding.swPoiAlongRouteSort.setOnCheckedChangeListener { cb, isChecked ->
            MapplsPoiAlongRouteApiSettings.instance.sort = isChecked
        }
        mBinding.btnPoiAlongRouteFilter.setOnClickListener {
            val filter = mBinding.etPoiAlongRouteFilter.text.toString()
            if(filter.isNotEmpty()) {
                MapplsPoiAlongRouteApiSettings.instance.filter = filter
            } else {
                MapplsPoiAlongRouteApiSettings.instance.filter = null
            }
        }
        mBinding.rgGeometry.setOnCheckedChangeListener { rb, checkedId ->
            when(checkedId) {
                mBinding.rbGeometryPolylineSix.id -> MapplsPoiAlongRouteApiSettings.instance.geometry =
                    POICriteria.GEOMETRY_POLYLINE6
                mBinding.rbGeometryPolylineFive.id -> MapplsPoiAlongRouteApiSettings.instance.geometry =
                    POICriteria.GEOMETRY_POLYLINE5
            }
        }
    }

    private fun setValues() {
        mBinding.etPoiAlongRouteOriginLocation.setText(MapplsPoiAlongRouteApiSettings.instance.origin)
        mBinding.etPoiAlongRouteDestinationLocation.setText(MapplsPoiAlongRouteApiSettings.instance.destination)
        mBinding.etPoiAlongRouteCategory.setText(MapplsPoiAlongRouteApiSettings.instance.category)
        mBinding.etPoiAlongRouteBuffer.setText(MapplsPoiAlongRouteApiSettings.instance.buffer?.toString() ?: "")
        mBinding.etPoiAlongRoutePage.setText(MapplsPoiAlongRouteApiSettings.instance.page?.toString() ?: "")
        mBinding.swPoiAlongRouteSort.isChecked = MapplsPoiAlongRouteApiSettings.instance.sort
        mBinding.etPoiAlongRouteFilter.setText(MapplsPoiAlongRouteApiSettings.instance.filter ?: "")
        when(MapplsPoiAlongRouteApiSettings.instance.geometry) {
            POICriteria.GEOMETRY_POLYLINE6 -> mBinding.rgGeometry.check(mBinding.rbGeometryPolylineSix.id)
            POICriteria.GEOMETRY_POLYLINE5 -> mBinding.rgGeometry.check(mBinding.rbGeometryPolylineFive.id)
        }

    }
}