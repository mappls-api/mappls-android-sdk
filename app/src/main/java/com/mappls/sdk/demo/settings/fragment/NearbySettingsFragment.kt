package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.activity.restapis.NearbyActivity
import com.mappls.sdk.demo.databinding.FragmentNearbySettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsAutosuggestApiSettings
import com.mappls.sdk.demo.settings.model.MapplsNearbyApiSettings
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria
import com.mappls.sdk.services.api.nearby.NearbyCriteria
import com.mappls.sdk.services.utils.MapplsUtils

class NearbySettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentNearbySettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentNearbySettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.nearbySettingsHeader.headerTitle.text = "Nearby Setting"
        mBinding.nearbySettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as NearbyActivity).popBackStack(this)
        }
        setValues()
        mBinding.swNearbyCurrentLocation.setOnCheckedChangeListener { _, isCheck ->
            MapplsNearbyApiSettings.instance.enableCurrentLocation = isCheck
        }
        mBinding.btnNearbyCustomLocation.setOnClickListener {
            val customLocation = mBinding.etNearbyCustomLocation.text.toString()
            if(customLocation.isNotEmpty()) {
                MapplsNearbyApiSettings.instance.customLocation = customLocation
            } else {
                Toast.makeText(requireContext(), "Please enter Custom Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnNearbyKeyword.setOnClickListener {
            val keyword = mBinding.etNearbyKeyword.text.toString()
            if(keyword.isNotEmpty()) {
                MapplsNearbyApiSettings.instance.keyword = keyword
            } else {
                Toast.makeText(requireContext(), "Please enter Keyword", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnNearbyPage.setOnClickListener {
            MapplsNearbyApiSettings.instance.page = mBinding.etNearbyKeyword.text.toString().toIntOrNull()
        }
        mBinding.btnClearSort.setOnClickListener {
            MapplsNearbyApiSettings.instance.sortBy = null
            mBinding.rgSort.clearCheck()
        }
        mBinding.rgSort.setOnCheckedChangeListener { _, checkId ->
            MapplsNearbyApiSettings.instance.sortBy =
                when(checkId) {
                    mBinding.rbDistanceAscending.id -> NearbyCriteria.DISTANCE_ASCENDING
                    mBinding.rbDistanceDescending.id -> NearbyCriteria.DISTANCE_DESCENDING
                    mBinding.rbNameAscending.id -> NearbyCriteria.NAME_ASCENDING
                    mBinding.rbNameDescending.id -> NearbyCriteria.NAME_DESCENDING
                    mBinding.rbImportance.id -> NearbyCriteria.IMPORTANCE

                    else -> null
                }
        }
        mBinding.btnClearSearch.setOnClickListener {
            MapplsNearbyApiSettings.instance.searchBy = null
            mBinding.rgSearch.clearCheck()
        }
        mBinding.rgSearch.setOnCheckedChangeListener { _, checkId ->
            MapplsNearbyApiSettings.instance.searchBy =
                when(checkId) {
                    mBinding.rbSearchDistance.id -> NearbyCriteria.DISTANCE
                    mBinding.rbSearchImportance.id -> NearbyCriteria.IMPORTANCE

                    else -> null
                }
        }
        mBinding.btnNearbyRadius.setOnClickListener {
            MapplsNearbyApiSettings.instance.radius = mBinding.etNearbyRadius.text.toString().toIntOrNull()
        }
        mBinding.btnNearbyItemCount.setOnClickListener {
            MapplsNearbyApiSettings.instance.itemCount = mBinding.etNearbyItemCount.text.toString().toIntOrNull()
        }
        mBinding.btnNearbyBounds.setOnClickListener {
            MapplsNearbyApiSettings.instance.bounds = if(mBinding.etNearbyBounds.text.toString().isEmpty()) null else mBinding.etNearbyBounds.text.toString()
        }

        mBinding.btnNearbyClearPod.setOnClickListener {
            MapplsNearbyApiSettings.instance.pod = null
            mBinding.rgNearbyPod.clearCheck()
        }
        mBinding.rgNearbyPod.setOnCheckedChangeListener { _, checkId ->
            MapplsNearbyApiSettings.instance.pod =
                when(checkId) {
                    mBinding.rbNearbySubLocality.id -> NearbyCriteria.POD_SUB_LOCALITY
                    mBinding.rbNearbyCity.id -> NearbyCriteria.POD_CITY
                    mBinding.rbNearbyState.id -> NearbyCriteria.POD_STATE
                    mBinding.rbNearbyLocality.id -> NearbyCriteria.POD_LOCALITY

                    else -> null
                }
        }
        mBinding.btnNearbyFilter.setOnClickListener {
            val filter = mBinding.etNearbyFilter.text.toString()
            if(filter.isNotEmpty()) {
                MapplsNearbyApiSettings.instance.filter = filter
            } else {
                MapplsNearbyApiSettings.instance.filter = null
            }
        }
        mBinding.swNearbyExplain.setOnCheckedChangeListener { _, isCheck ->
            MapplsNearbyApiSettings.instance.explain = isCheck
        }
        mBinding.swNearbyRichData.setOnCheckedChangeListener { _, isCheck ->
            MapplsNearbyApiSettings.instance.richData = isCheck
        }
        mBinding.btnNearbyUserName.setOnClickListener {
            val userName = mBinding.etNearbyUserName.text.toString()
            if(userName.isNotEmpty()) {
                MapplsNearbyApiSettings.instance.userName = userName
            } else {
                MapplsNearbyApiSettings.instance.userName = null
            }
        }
        mBinding.btnNearbyIncludes.setOnClickListener {
            val includes = mBinding.etNearbyIncludes.text.toString()
            if(includes.isNotEmpty()) {
                MapplsNearbyApiSettings.instance.includes = includes.split(",").toList()
            } else {
                MapplsNearbyApiSettings.instance.includes = null
            }
        }
    }

    private fun setValues() {
        mBinding.swNearbyCurrentLocation.isChecked = MapplsNearbyApiSettings.instance.enableCurrentLocation
        mBinding.etNearbyCustomLocation.setText(MapplsNearbyApiSettings.instance.customLocation)
        mBinding.etNearbyKeyword.setText(MapplsNearbyApiSettings.instance.keyword)
        mBinding.etNearbyPage.setText(MapplsNearbyApiSettings.instance.page?.toString() ?: "")
        if(MapplsNearbyApiSettings.instance.sortBy != null) {
            mBinding.rgSort.check(
                when(MapplsNearbyApiSettings.instance.sortBy) {
                    NearbyCriteria.DISTANCE_ASCENDING -> mBinding.rbDistanceAscending.id
                    NearbyCriteria.DISTANCE_DESCENDING -> mBinding.rbDistanceDescending.id
                    NearbyCriteria.NAME_ASCENDING -> mBinding.rbNameAscending.id
                    NearbyCriteria.NAME_DESCENDING -> mBinding.rbNameDescending.id
                    NearbyCriteria.IMPORTANCE -> mBinding.rbImportance.id
                    else -> -1
                }
            )
        }
        if(MapplsNearbyApiSettings.instance.searchBy != null) {
            mBinding.rgSearch.check(
                when(MapplsNearbyApiSettings.instance.searchBy) {
                    NearbyCriteria.DISTANCE -> mBinding.rbSearchDistance.id
                    NearbyCriteria.IMPORTANCE -> mBinding.rbSearchImportance.id
                    else -> -1
                }
            )
        }
        mBinding.etNearbyRadius.setText(MapplsNearbyApiSettings.instance.radius?.toString() ?: "")
        if(MapplsNearbyApiSettings.instance.pod != null) {
            mBinding.rgNearbyPod.check(
                when(MapplsNearbyApiSettings.instance.pod) {
                    NearbyCriteria.POD_SUB_LOCALITY -> mBinding.rbNearbySubLocality.id
                    NearbyCriteria.POD_CITY -> mBinding.rbNearbyCity.id
                    NearbyCriteria.POD_STATE -> mBinding.rbNearbyState.id
                    NearbyCriteria.POD_LOCALITY -> mBinding.rbNearbyLocality.id
                    else -> -1
                }
            )
        }
        mBinding.etNearbyItemCount.setText(MapplsNearbyApiSettings.instance.itemCount?.toString() ?: "")
        mBinding.etNearbyBounds.setText(MapplsNearbyApiSettings.instance.bounds ?: "")
        mBinding.etNearbyFilter.setText(MapplsNearbyApiSettings.instance.filter ?: "")
        mBinding.swNearbyExplain.isChecked = MapplsNearbyApiSettings.instance.explain
        mBinding.swNearbyRichData.isChecked = MapplsNearbyApiSettings.instance.richData
        mBinding.etNearbyUserName.setText(MapplsNearbyApiSettings.instance.userName ?: "")
        if(MapplsNearbyApiSettings.instance.includes != null) {
            mBinding.etNearbyIncludes.setText(MapplsUtils.join(",", MapplsNearbyApiSettings.instance.includes?.toTypedArray()))
        }
    }

}