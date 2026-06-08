package com.mappls.sdk.demo.settings.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.activity.widgets.AutoCompleteWidgetActivity
import com.mappls.sdk.demo.databinding.FragmentAutoCompleteSettingBinding
import com.mappls.sdk.demo.databinding.FragmentAutosuggestSettingsBinding
import com.mappls.sdk.demo.settings.model.AutoCompleteWidgetSetting
import com.mappls.sdk.demo.settings.model.MapplsPlacePickerSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria


class AutoCompleteSettingFragment : Fragment() {

    private lateinit var mBinding:FragmentAutoCompleteSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAutoCompleteSettingBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.autoCompleteSettingsHeader.headerTitle.text = "AutoComplete Setting"
        mBinding.autoCompleteSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as AutoCompleteWidgetActivity).popBackStack(this)
        }
        setValues()
        mBinding.swCurrentLocation.setOnCheckedChangeListener { _, isCheck ->
            AutoCompleteWidgetSetting.instance.userAddedLocationEnable = isCheck
        }

        mBinding.btnCustomLocation.setOnClickListener {
            if (mBinding.etCustomLat.text.toString()
                    .isNotEmpty() && mBinding.etCustomLng.text.toString().isNotEmpty()
            ) {
                val latitude = mBinding.etCustomLat.text.toString()
                val longitude = mBinding.etCustomLng.text.toString()
                if (Utils.validateLatitude(latitude) && Utils.validateLongitude(longitude)) {
                    AutoCompleteWidgetSetting.instance.location = Point.fromLngLat(longitude.toDouble(),latitude.toDouble())
                }
            }
        }

        mBinding.swTokeniseAddress.setOnCheckedChangeListener { _, isCheck ->
            AutoCompleteWidgetSetting.instance.tokenizeAddress = isCheck
        }

        mBinding.swBridge.setOnCheckedChangeListener { _, isCheck ->
            AutoCompleteWidgetSetting.instance.bridge = isCheck
        }
        mBinding.btnClearPod.setOnClickListener {
            AutoCompleteWidgetSetting.instance.pod = null
            mBinding.rgPod.clearCheck()
        }
        mBinding.rgPod.setOnCheckedChangeListener { _, checkId ->
            AutoCompleteWidgetSetting.instance.pod =
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
                AutoCompleteWidgetSetting.instance.filter = filter
            } else {
                AutoCompleteWidgetSetting.instance.filter = null
            }
        }

        mBinding.btnResponseLang.setOnClickListener {
            val responseLang = mBinding.etResponseLang.text.toString()
            if(responseLang.isNotEmpty()) {
                AutoCompleteWidgetSetting.instance.responseLang = responseLang
            } else {
                AutoCompleteWidgetSetting.instance.responseLang = null
            }
        }

        mBinding.swHyperlocal.setOnCheckedChangeListener { _, isChecked ->
            AutoCompleteWidgetSetting.instance.hyperLocal = isChecked
        }

        mBinding.etBg.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(AutoCompleteWidgetSetting.instance.backgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    AutoCompleteWidgetSetting.instance.backgroundColor = selectedColor
                    mBinding.etBg.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()
        }

        mBinding.etToolbar.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(AutoCompleteWidgetSetting.instance.toolbarColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    AutoCompleteWidgetSetting.instance.toolbarColor = selectedColor
                    mBinding.etBg.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()
        }

        mBinding.etResultBackgroundColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(AutoCompleteWidgetSetting.instance.resultBackgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    AutoCompleteWidgetSetting.instance.resultBackgroundColor = selectedColor
                    mBinding.etBg.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()
        }

    }

    private fun setValues() {
        mBinding.swCurrentLocation.isChecked =
            AutoCompleteWidgetSetting.instance.userAddedLocationEnable
        mBinding.etCustomLat.setText(
            AutoCompleteWidgetSetting.instance.location.latitude().toString() ?: ""
        )
        mBinding.etCustomLng.setText(
            AutoCompleteWidgetSetting.instance.location.longitude().toString() ?: ""
        )
        mBinding.swTokeniseAddress.isChecked = AutoCompleteWidgetSetting.instance.tokenizeAddress
        mBinding.swBridge.isChecked = AutoCompleteWidgetSetting.instance.bridge
        if (AutoCompleteWidgetSetting.instance.pod != null) {
            mBinding.rgPod.check(
                when (AutoCompleteWidgetSetting.instance.pod) {
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
        mBinding.etFilter.setText(AutoCompleteWidgetSetting.instance.filter ?: "")
        mBinding.etResponseLang.setText(AutoCompleteWidgetSetting.instance.responseLang ?: "")
        mBinding.swHyperlocal.isChecked = AutoCompleteWidgetSetting.instance.hyperLocal
        mBinding.etBg.setText(String.format("#%06X", AutoCompleteWidgetSetting.instance.backgroundColor))
        mBinding.etToolbar.setText(String.format("#%06X", AutoCompleteWidgetSetting.instance.toolbarColor))
        mBinding.etResultBackgroundColor.setText(String.format("#%06X", AutoCompleteWidgetSetting.instance.resultBackgroundColor))

    }
}