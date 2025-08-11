package com.mappls.sdk.demo.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mappls.sdk.demo.activity.restapis.GeocodeActivity
import com.mappls.sdk.demo.activity.restapis.ReverseGeocodeActivity
import com.mappls.sdk.demo.databinding.FragmentReverseGeocodeSettingBinding
import com.mappls.sdk.demo.settings.model.MapplsReverseGeocodeApiSettings

class ReverseGeocodeSettingFragment : Fragment() {

    private lateinit var mBinding: FragmentReverseGeocodeSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentReverseGeocodeSettingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.revGeocodeSettingsHeader.headerTitle.text = "Reverse Geocode Setting"
        mBinding.revGeocodeSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as ReverseGeocodeActivity).popBackStack(this)
        }

        mBinding.etRevGeocodeLang.setText(MapplsReverseGeocodeApiSettings.instance.lang ?: "")
        mBinding.btnRevGeocodeLang.setOnClickListener {
            val lang = mBinding.etRevGeocodeLang.text.toString()
            if(lang.isEmpty()) {
                MapplsReverseGeocodeApiSettings.instance.lang = null
            } else {
                MapplsReverseGeocodeApiSettings.instance.lang = lang
            }
        }
    }
}