package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.GeocodeActivity
import com.mappls.sdk.demo.activity.restapis.NearbyActivity
import com.mappls.sdk.demo.databinding.FragmentGeocodeSettingBinding
import com.mappls.sdk.demo.settings.model.MapplsGeocodeApiSettings
import com.mappls.sdk.demo.settings.model.MapplsNearbyApiSettings
import com.mappls.sdk.services.api.geocoding.GeoCodingCriteria
import com.mappls.sdk.services.api.nearby.NearbyCriteria

class GeocodeSettingFragment : Fragment() {

    private lateinit var mBinding: FragmentGeocodeSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGeocodeSettingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.geocodeSettingsHeader.headerTitle.text = "Geocode Setting"
        mBinding.geocodeSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as GeocodeActivity).popBackStack(this)
        }
        setValues()
        mBinding.btnGeocodeItemCount.setOnClickListener {
            MapplsGeocodeApiSettings.instance.itemCount = mBinding.etGeocodeItemCount.text.toString().toIntOrNull()
        }
        mBinding.btnGeocodeBounds.setOnClickListener {
            MapplsGeocodeApiSettings.instance.bound = if(mBinding.etGeocodeBounds.text.toString().isEmpty()) null else mBinding.etGeocodeBounds.text.toString()
        }
        mBinding.btnGeocodeClearPod.setOnClickListener {
            MapplsGeocodeApiSettings.instance.podFilter = null
            mBinding.rgGeocodePod.clearCheck()
        }
        mBinding.rgGeocodePod.setOnCheckedChangeListener { _, checkId ->
            MapplsGeocodeApiSettings.instance.podFilter =
                when(checkId) {
                    mBinding.rbGeocodeHouseNumber.id -> GeoCodingCriteria.POD_HOUSE_NUMBER
                    mBinding.rbGeocodeHouseName.id -> GeoCodingCriteria.POD_HOUSE_NAME
                    mBinding.rbGeocodePoi.id -> GeoCodingCriteria.POD_POINT_OF_INTEREST
                    mBinding.rbGeocodeStreet.id -> GeoCodingCriteria.POD_STREET
                    mBinding.rbGeocodeSubSubLocality.id -> GeoCodingCriteria.POD_SUB_SUB_LOCALITY
                    mBinding.rbGeocodeVillage.id -> GeoCodingCriteria.POD_VILLAGE
                    mBinding.rbGeocodeSubLocality.id -> GeoCodingCriteria.POD_SUB_LOCALITY
                    mBinding.rbGeocodeSubDistrict.id -> GeoCodingCriteria.POD_SUB_DISTRICT
                    mBinding.rbGeocodeLocality.id -> GeoCodingCriteria.POD_LOCALITY
                    mBinding.rbGeocodeCity.id -> GeoCodingCriteria.POD_CITY
                    mBinding.rbGeocodeDistrict.id -> GeoCodingCriteria.POD_DISTRICT
                    mBinding.rbGeocodePincode.id -> GeoCodingCriteria.POD_PINCODE
                    mBinding.rbGeocodeState.id -> GeoCodingCriteria.POD_STATE


                    else -> null
                }
        }
        mBinding.btnGeocodeAppName.setOnClickListener {
            MapplsGeocodeApiSettings.instance.clientAppName = if(mBinding.etGeocodeAppName.text.toString().isEmpty()) null else mBinding.etGeocodeAppName.text.toString()
        }
        mBinding.btnGeocodeClearBias.setOnClickListener {
            MapplsGeocodeApiSettings.instance.bias = null
            mBinding.rgGeocodeBias.clearCheck()
        }
        mBinding.rgGeocodeBias.setOnCheckedChangeListener { _, checkId ->
            MapplsGeocodeApiSettings.instance.bias =
                when(checkId) {
                    mBinding.rbGeocodeDefault.id -> GeoCodingCriteria.BIAS_DEFAULT
                    mBinding.rbGeocodeRural.id -> GeoCodingCriteria.BIAS_RURAL
                    mBinding.rbGeocodeUrban.id -> GeoCodingCriteria.BIAS_URBAN

                    else -> null
                }
        }
        mBinding.swGeocodeScores.setOnCheckedChangeListener { _,isCheck ->
            MapplsGeocodeApiSettings.instance.scores = isCheck
        }
    }

    private fun setValues() {
        if(MapplsGeocodeApiSettings.instance.podFilter != null) {
            mBinding.rgGeocodePod.check(
                when(MapplsGeocodeApiSettings.instance.podFilter) {
                    GeoCodingCriteria.POD_HOUSE_NUMBER -> mBinding.rbGeocodeHouseNumber.id
                    GeoCodingCriteria.POD_HOUSE_NAME -> mBinding.rbGeocodeHouseName.id
                    GeoCodingCriteria.POD_POINT_OF_INTEREST -> mBinding.rbGeocodePoi.id
                    GeoCodingCriteria.POD_STREET -> mBinding.rbGeocodeStreet.id
                    GeoCodingCriteria.POD_SUB_SUB_LOCALITY -> mBinding.rbGeocodeSubSubLocality.id
                    GeoCodingCriteria.POD_VILLAGE -> mBinding.rbGeocodeVillage.id
                    GeoCodingCriteria.POD_SUB_LOCALITY -> mBinding.rbGeocodeSubLocality.id
                    GeoCodingCriteria.POD_SUB_DISTRICT -> mBinding.rbGeocodeSubDistrict.id
                    GeoCodingCriteria.POD_LOCALITY -> mBinding.rbGeocodeLocality.id
                    GeoCodingCriteria.POD_CITY -> mBinding.rbGeocodeCity.id
                    GeoCodingCriteria.POD_DISTRICT -> mBinding.rbGeocodeDistrict.id
                    GeoCodingCriteria.POD_PINCODE -> mBinding.rbGeocodePincode.id
                    GeoCodingCriteria.POD_STATE -> mBinding.rbGeocodeState.id
                    else -> -1
                }
            )
        }
        mBinding.etGeocodeItemCount.setText(MapplsGeocodeApiSettings.instance.itemCount?.toString() ?: "")
        mBinding.etGeocodeBounds.setText(MapplsGeocodeApiSettings.instance.bound ?: "")
        mBinding.etGeocodeAppName.setText(MapplsGeocodeApiSettings.instance.clientAppName ?: "")
        if(MapplsGeocodeApiSettings.instance.bias != null) {
            mBinding.rgGeocodeBias.check(
                when(MapplsGeocodeApiSettings.instance.bias) {
                    GeoCodingCriteria.BIAS_DEFAULT -> mBinding.rbGeocodeDefault.id
                    GeoCodingCriteria.BIAS_RURAL -> mBinding.rbGeocodeRural.id
                    GeoCodingCriteria.BIAS_URBAN -> mBinding.rbGeocodeUrban.id
                    else -> -1
                }

            )
        }
        mBinding.swGeocodeScores.isChecked = MapplsGeocodeApiSettings.instance.scores
    }

}