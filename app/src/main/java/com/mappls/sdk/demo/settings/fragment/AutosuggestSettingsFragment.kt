package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.databinding.FragmentAutosuggestSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsAutosuggestApiSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria

class AutosuggestSettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentAutosuggestSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAutosuggestSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.autosuggestSettingsHeader.headerTitle.text = "Autosuggest Setting"
        mBinding.autosuggestSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as AutoSuggestActivity).popBackStack(this)
        }
        setValues()
        mBinding.swCurrentLocation.setOnCheckedChangeListener { _, isCheck ->
            MapplsAutosuggestApiSettings.instance.enableCurrentLocation = isCheck
        }

        mBinding.btnCustomLocation.setOnClickListener {
            if (mBinding.etCustomLat.text.toString()
                    .isNotEmpty() && mBinding.etCustomLng.text.toString().isNotEmpty()
            ) {
                val latitude = mBinding.etCustomLat.text.toString()
                val longitude = mBinding.etCustomLng.text.toString()
                if (Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                    MapplsAutosuggestApiSettings.instance.customLocation =
                        LatLng(latitude.toDouble(), longitude.toDouble())
                } else {
                    MapplsAutosuggestApiSettings.instance.customLocation = null
                }
            } else {
                MapplsAutosuggestApiSettings.instance.customLocation = null
            }
        }

        mBinding.swTokeniseAddress.setOnCheckedChangeListener { _, isCheck ->
            MapplsAutosuggestApiSettings.instance.tokenizeAddress = isCheck
        }

        mBinding.swBridge.setOnCheckedChangeListener { _, isCheck ->
            MapplsAutosuggestApiSettings.instance.bridge = isCheck
        }

        mBinding.swEnableMapCenter.setOnCheckedChangeListener { _, isCheck ->
            MapplsAutosuggestApiSettings.instance.enableMapCenter = isCheck
        }

        mBinding.btnIsPrimary.setOnClickListener {
            MapplsAutosuggestApiSettings.instance.isPrimary = if(mBinding.etPrimary.text.toString().isNotEmpty()) mBinding.etPrimary.text.toString() else null
        }

        mBinding.btnZoom.setOnClickListener {
            val zoom = mBinding.etZoom.text.toString().toDoubleOrNull()
            if(zoom != null && zoom < 24 && zoom > 0) {
                MapplsAutosuggestApiSettings.instance.zoom = zoom
            } else {
                MapplsAutosuggestApiSettings.instance.zoom = null
            }
        }
        mBinding.btnClearPod.setOnClickListener {
            MapplsAutosuggestApiSettings.instance.pod = null
            mBinding.rgPod.clearCheck()
        }
        mBinding.rgPod.setOnCheckedChangeListener { _, checkId ->
            MapplsAutosuggestApiSettings.instance.pod =
                when(checkId) {
                    mBinding.rbSubSubLocality.id -> AutoSuggestCriteria.POD_SUB_SUB_LOCALITY
                    mBinding.rbSubLocality.id -> AutoSuggestCriteria.POD_SUB_LOCALITY
                    mBinding.rbCity.id -> AutoSuggestCriteria.POD_CITY
                    mBinding.rbState.id -> AutoSuggestCriteria.POD_STATE
                    mBinding.rbPoi.id -> AutoSuggestCriteria.POD_POI
                    mBinding.rbDistrict.id -> AutoSuggestCriteria.POD_DISTRICT
                    mBinding.rbLocality.id -> AutoSuggestCriteria.POD_LOCALITY
                    mBinding.rbSubDistrict.id -> AutoSuggestCriteria.POD_SUB_DISTRICT
                    mBinding.rbVillage.id -> AutoSuggestCriteria.POD_VILLAGE

                    else -> null
                }
        }

        mBinding.btnFilter.setOnClickListener {
            val filter = mBinding.etFilter.text.toString()
            if(filter.isNotEmpty()) {
                MapplsAutosuggestApiSettings.instance.filter = filter
            } else {
                MapplsAutosuggestApiSettings.instance.filter = null
            }
        }

        mBinding.btnResponseLang.setOnClickListener {
            val responseLang = mBinding.etResponseLang.text.toString()
            if(responseLang.isNotEmpty()) {
                MapplsAutosuggestApiSettings.instance.responseLang = responseLang
            } else {
                MapplsAutosuggestApiSettings.instance.responseLang = null
            }
        }

        mBinding.swHyperlocal.setOnCheckedChangeListener { _, isChecked ->
            MapplsAutosuggestApiSettings.instance.hyperLocal = isChecked
        }

        mBinding.swExplain.setOnCheckedChangeListener { _, isChecked ->
            MapplsAutosuggestApiSettings.instance.explain = isChecked
        }

    }

    private fun setValues() {
        mBinding.swCurrentLocation.isChecked =
            MapplsAutosuggestApiSettings.instance.enableCurrentLocation
        mBinding.etCustomLat.setText(
            MapplsAutosuggestApiSettings.instance.customLocation?.latitude?.toString() ?: ""
        )
        mBinding.etCustomLng.setText(
            MapplsAutosuggestApiSettings.instance.customLocation?.longitude?.toString() ?: ""
        )
        mBinding.swTokeniseAddress.isChecked = MapplsAutosuggestApiSettings.instance.tokenizeAddress
        mBinding.swBridge.isChecked = MapplsAutosuggestApiSettings.instance.bridge
        mBinding.swEnableMapCenter.isChecked = MapplsAutosuggestApiSettings.instance.enableMapCenter
        mBinding.etPrimary.setText(MapplsAutosuggestApiSettings.instance.isPrimary ?: "")
        mBinding.etZoom.setText(MapplsAutosuggestApiSettings.instance.zoom?.toString() ?: "")
        if(MapplsAutosuggestApiSettings.instance.pod != null) {
            mBinding.rgPod.check(
                when(MapplsAutosuggestApiSettings.instance.pod) {
                    AutoSuggestCriteria.POD_SUB_SUB_LOCALITY -> mBinding.rbSubSubLocality.id
                    AutoSuggestCriteria.POD_SUB_LOCALITY -> mBinding.rbSubLocality.id
                    AutoSuggestCriteria.POD_CITY -> mBinding.rbCity.id
                    AutoSuggestCriteria.POD_STATE -> mBinding.rbState.id
                    AutoSuggestCriteria.POD_POI -> mBinding.rbPoi.id
                    AutoSuggestCriteria.POD_DISTRICT -> mBinding.rbDistrict.id
                    AutoSuggestCriteria.POD_LOCALITY -> mBinding.rbLocality.id
                    AutoSuggestCriteria.POD_SUB_DISTRICT -> mBinding.rbSubDistrict.id
                    AutoSuggestCriteria.POD_VILLAGE -> mBinding.rbVillage.id
                    else -> -1
                }
            )
        }
        mBinding.etFilter.setText(MapplsAutosuggestApiSettings.instance.filter ?: "")
        mBinding.etResponseLang.setText(MapplsAutosuggestApiSettings.instance.responseLang ?: "")
        mBinding.swHyperlocal.isChecked = MapplsAutosuggestApiSettings.instance.hyperLocal
        mBinding.swExplain.isChecked = MapplsAutosuggestApiSettings.instance.explain

    }

}