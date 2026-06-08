package com.mappls.sdk.demo.settings.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mappls.sdk.demo.activity.widgets.DirectionWidgetActivity
import com.mappls.sdk.demo.databinding.FragmentDirectionWidgetSettingBinding
import com.mappls.sdk.demo.settings.model.MapplsDirectionWidgetSetting
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria
import com.mappls.sdk.services.api.directions.DirectionsCriteria


class DirectionWidgetSettingFragment : Fragment() {

    lateinit var mBinding:FragmentDirectionWidgetSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDirectionWidgetSettingBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.settingsHeader.headerTitle.text = "Direction Widget Setting"
        mBinding.settingsHeader.headerBack.setOnClickListener {
            (requireActivity() as DirectionWidgetActivity).popBackStack(this)
        }
        setValue()
        mBinding.swTokenizeAddress.setOnCheckedChangeListener { _, isCheck ->
            MapplsDirectionWidgetSetting.instance.tokenizeAddress = isCheck
        }
        mBinding.swHistory.setOnCheckedChangeListener { _, isCheck ->
            MapplsDirectionWidgetSetting.instance.enableHistory = isCheck
        }

        mBinding.swShowStartNavigation.setOnCheckedChangeListener { _, isCheck ->
            MapplsDirectionWidgetSetting.instance.showStartNavigation = isCheck
        }

        mBinding.swShowAlternative.setOnCheckedChangeListener { _, isCheck ->
            MapplsDirectionWidgetSetting.instance.showAlternative = isCheck
        }
        mBinding.swSteps.setOnCheckedChangeListener { _, isCheck ->
            MapplsDirectionWidgetSetting.instance.steps = isCheck
        }
        mBinding.swShowPoiSearch.setOnCheckedChangeListener { _, isCheck ->
            MapplsDirectionWidgetSetting.instance.isShowPOISearch = isCheck
        }

        mBinding.btnLocation.setOnClickListener {
            val etloc = mBinding.etLocation.text.toString()
            if (etloc.isNotEmpty()) {
                val latLng = etloc.split(",")
                if (latLng.size == 2) {
                    val latitude = latLng[0].toDoubleOrNull()
                    val longitude = latLng[1].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        MapplsDirectionWidgetSetting.instance.location =
                            Point.fromLngLat(longitude, latitude)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Invalid latitude or longitude",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Please enter Location", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        mBinding.btnFilter.setOnClickListener {
            val etFilter = mBinding.etFilter.text.toString()
            if(etFilter.isEmpty().not()) {
                MapplsDirectionWidgetSetting.instance.filter = etFilter
            } else {
                MapplsDirectionWidgetSetting.instance.filter = null
            }
        }
        mBinding.btnSearchHint.setOnClickListener {
            val etHint = mBinding.etSearchHint.text.toString()
            if(etHint.isEmpty().not()) {
                MapplsDirectionWidgetSetting.instance.hint = etHint
            } else {
                MapplsDirectionWidgetSetting.instance.hint = null
            }
        }
        mBinding.btnClearPod.setOnClickListener {
            MapplsDirectionWidgetSetting.instance.pod = null
            mBinding.rgPod.clearCheck()
        }
        mBinding.rgPod.setOnCheckedChangeListener { _, checkId ->
            MapplsDirectionWidgetSetting.instance.pod =
                when(checkId) {
                    mBinding.rbSubSubLocality.id -> AutoSuggestCriteria.POD_SUB_SUB_LOCALITY
                    mBinding.rbSubLocality.id -> AutoSuggestCriteria.POD_SUB_LOCALITY
                    mBinding.rbCity.id -> AutoSuggestCriteria.POD_CITY
                    mBinding.rbState.id -> AutoSuggestCriteria.POD_STATE
                    mBinding.rbDistrict.id -> AutoSuggestCriteria.POD_DISTRICT
                    mBinding.rbLocality.id -> AutoSuggestCriteria.POD_LOCALITY
                    mBinding.rbSubDistrict.id -> AutoSuggestCriteria.POD_SUB_DISTRICT
                    mBinding.rbVillage.id -> AutoSuggestCriteria.POD_VILLAGE

                    else -> null
                }

        }

        mBinding.rgSignature.setOnCheckedChangeListener { _, checkId ->
            MapplsDirectionWidgetSetting.instance.signatureVertical =
                when(checkId) {
                    mBinding.rbTop.id -> PlaceOptions.GRAVITY_TOP
                    mBinding.rbBottom.id -> PlaceOptions.GRAVITY_BOTTOM
                    else -> {
                        PlaceOptions.GRAVITY_TOP
                    }
                }
        }
        mBinding.rgSignatureHorizontal.setOnCheckedChangeListener { _, checkId ->
            MapplsDirectionWidgetSetting.instance.signatureHorizontal =
                when(checkId) {
                    mBinding.rbLeft.id -> PlaceOptions.GRAVITY_LEFT
                    mBinding.rbRight.id -> PlaceOptions.GRAVITY_RIGHT
                    mBinding.rbCenter.id -> PlaceOptions.GRAVITY_CENTER
                    else -> {
                        PlaceOptions.GRAVITY_LEFT
                    }
                }
        }


        mBinding.rgLogoSize.setOnCheckedChangeListener { _, checkId ->
            MapplsDirectionWidgetSetting.instance.logoSize =
                when(checkId) {
                    mBinding.rbSmall.id -> PlaceOptions.SIZE_SMALL
                    mBinding.rbMedium.id -> PlaceOptions.SIZE_MEDIUM
                    mBinding.rbLarge.id -> PlaceOptions.SIZE_LARGE
                    else -> {
                        PlaceOptions.SIZE_SMALL
                    }
                }
        }
        mBinding.rgResources.setOnCheckedChangeListener { _, checkId ->
            MapplsDirectionWidgetSetting.instance.resource =
                when(checkId) {
                    mBinding.rbRoute.id -> DirectionsCriteria.RESOURCE_ROUTE
                    mBinding.rbRouteEta.id -> DirectionsCriteria.RESOURCE_ROUTE_ETA
                    mBinding.rbRouteTraffic.id -> DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC
                    else -> {
                        DirectionsCriteria.RESOURCE_ROUTE
                    }
                }
        }

        mBinding.rgProfile.setOnCheckedChangeListener { _, checkId ->
            MapplsDirectionWidgetSetting.instance.profile =
                when(checkId) {
                    mBinding.rbDriving.id -> DirectionsCriteria.PROFILE_DRIVING
                    mBinding.rbBiking.id ->DirectionsCriteria.PROFILE_BIKING
                    mBinding.rbWalking.id -> DirectionsCriteria.PROFILE_WALKING
                    mBinding.rbTrucking.id ->DirectionsCriteria.PROFILE_TRUCKING

                    else -> {DirectionsCriteria.PROFILE_DRIVING}
                }
        }
        mBinding.rgOverView.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDirectionWidgetSetting.instance.overview = when(rg.checkedRadioButtonId) {
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

        mBinding.etToolbarColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsDirectionWidgetSetting.instance.toolbarColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsDirectionWidgetSetting.instance.toolbarColor = selectedColor
                    mBinding.etToolbarColor.setText(
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
        mBinding.etToolbarTint.setOnClickListener { v ->

            MapplsDirectionWidgetSetting.instance.toolbarTintColor?.let {
                ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Choose color")
                    .initialColor(it)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok"
                    ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                        MapplsDirectionWidgetSetting.instance.toolbarTintColor = selectedColor
                        mBinding.etToolbarTint.setText(
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
        mBinding.etAddressTxtColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsDirectionWidgetSetting.instance.addressTextColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsDirectionWidgetSetting.instance.addressTextColor = selectedColor
                    mBinding.etAddressTxtColor.setText(
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
        mBinding.etPlaceNameColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsDirectionWidgetSetting.instance.placeNameTextColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsDirectionWidgetSetting.instance.placeNameTextColor = selectedColor
                    mBinding.etPlaceNameColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and  selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()

        }
        mBinding.etBackgroundColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsDirectionWidgetSetting.instance.backgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsDirectionWidgetSetting.instance.backgroundColor = selectedColor
                    mBinding.etBackgroundColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and  selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()

        }
        mBinding.cbExcludeToll.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionWidgetSetting.instance.excludes.contains(DirectionsCriteria.EXCLUDE_TOLL)) {
                    MapplsDirectionWidgetSetting.instance.excludes.add(DirectionsCriteria.EXCLUDE_TOLL)
                }
            } else {
                MapplsDirectionWidgetSetting.instance.excludes.remove(DirectionsCriteria.EXCLUDE_TOLL)
            }
        }
        mBinding.cbExcludeFerry.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionWidgetSetting.instance.excludes.contains(DirectionsCriteria.EXCLUDE_FERRY)) {
                    MapplsDirectionWidgetSetting.instance.excludes.add(DirectionsCriteria.EXCLUDE_FERRY)
                }
            } else {
                MapplsDirectionWidgetSetting.instance.excludes.remove(DirectionsCriteria.EXCLUDE_FERRY)
            }
        }
        mBinding.cbExcludeTunnel.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionWidgetSetting.instance.excludes.contains(DirectionsCriteria.EXCLUDE_TUNNEL)) {
                    MapplsDirectionWidgetSetting.instance.excludes.add(DirectionsCriteria.EXCLUDE_TUNNEL)
                }
            } else {
                MapplsDirectionWidgetSetting.instance.excludes.remove(DirectionsCriteria.EXCLUDE_TUNNEL)
            }
        }
        mBinding.cbExcludeMotorway.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionWidgetSetting.instance.excludes.contains(DirectionsCriteria.EXCLUDE_MOTORWAY)) {
                    MapplsDirectionWidgetSetting.instance.excludes.add(DirectionsCriteria.EXCLUDE_MOTORWAY)
                }
            } else {
                MapplsDirectionWidgetSetting.instance.excludes.remove(DirectionsCriteria.EXCLUDE_MOTORWAY)
            }
        }
        mBinding.cbExcludeRestricted.setOnCheckedChangeListener { cb, isChecked ->
            if(isChecked) {
                if(!MapplsDirectionWidgetSetting.instance.excludes.contains(DirectionsCriteria.EXCLUDE_RESTRICTED)) {
                    MapplsDirectionWidgetSetting.instance.excludes.add(DirectionsCriteria.EXCLUDE_RESTRICTED)
                }
            } else {
                MapplsDirectionWidgetSetting.instance.excludes.remove(DirectionsCriteria.EXCLUDE_RESTRICTED)
            }
        }

    }

    private fun setValue() {
        mBinding.etToolbarColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsDirectionWidgetSetting.instance.toolbarColor)
            )
        )
        mBinding.etToolbarTint.setText(
            String.format(
                "#%06X",
                0xFFFFFF and MapplsDirectionWidgetSetting.instance.toolbarTintColor)
        )
        mBinding.etPlaceNameColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsDirectionWidgetSetting.instance.placeNameTextColor)
            )
        )
        mBinding.etBackgroundColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsDirectionWidgetSetting.instance.backgroundColor)
            )
        )
        mBinding.etAddressTxtColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsDirectionWidgetSetting.instance.addressTextColor)
            )
        )

        mBinding.swSteps.isChecked = MapplsDirectionWidgetSetting.instance.steps
        mBinding.swShowPoiSearch.isChecked = MapplsDirectionWidgetSetting.instance.isShowPOISearch
        mBinding.swShowAlternative.isChecked = MapplsDirectionWidgetSetting.instance.showAlternative

        mBinding.swTokenizeAddress.isChecked = MapplsDirectionWidgetSetting.instance.tokenizeAddress
        mBinding.swShowStartNavigation.isChecked = MapplsDirectionWidgetSetting.instance.showStartNavigation
        mBinding.swHistory.isChecked = MapplsDirectionWidgetSetting.instance.enableHistory

        mBinding.etSearchHint.setText(MapplsDirectionWidgetSetting.instance.hint)
        mBinding.etFilter.setText(MapplsDirectionWidgetSetting.instance.filter ?: "")
        mBinding.etLocation.setText(MapplsDirectionWidgetSetting.instance.location?.toString() ?: "")
        if(MapplsDirectionWidgetSetting.instance.pod != null) {
            mBinding.rgPod.check(
                when(MapplsDirectionWidgetSetting.instance.pod) {
                    AutoSuggestCriteria.POD_SUB_SUB_LOCALITY -> mBinding.rbSubSubLocality.id
                    AutoSuggestCriteria.POD_SUB_LOCALITY -> mBinding.rbSubLocality.id
                    AutoSuggestCriteria.POD_CITY -> mBinding.rbCity.id
                    AutoSuggestCriteria.POD_STATE -> mBinding.rbState.id
                    AutoSuggestCriteria.POD_DISTRICT -> mBinding.rbDistrict.id
                    AutoSuggestCriteria.POD_LOCALITY -> mBinding.rbLocality.id
                    AutoSuggestCriteria.POD_SUB_DISTRICT -> mBinding.rbSubDistrict.id
                    AutoSuggestCriteria.POD_VILLAGE -> mBinding.rbVillage.id
                    else -> -1
                }
            )
        }
        mBinding.rgSignature.check(
            when(MapplsDirectionWidgetSetting.instance.signatureVertical) {
                PlaceOptions.GRAVITY_TOP -> mBinding.rbTop.id
                PlaceOptions.GRAVITY_BOTTOM -> mBinding.rbBottom.id

                else -> -1
            }
        )

        mBinding.rgSignatureHorizontal.check(
            when(MapplsDirectionWidgetSetting.instance.signatureHorizontal) {
                PlaceOptions.GRAVITY_LEFT -> mBinding.rbLeft.id
                PlaceOptions.GRAVITY_CENTER -> mBinding.rbCenter.id
                PlaceOptions.GRAVITY_RIGHT -> mBinding.rbRight.id

                else -> -1
            }
        )

        mBinding.rgLogoSize.check(
            when(MapplsDirectionWidgetSetting.instance.logoSize) {
                PlaceOptions.SIZE_SMALL -> mBinding.rbSmall.id
                PlaceOptions.SIZE_MEDIUM -> mBinding.rbMedium.id
                PlaceOptions.SIZE_LARGE -> mBinding.rbLarge.id

                else -> -1
            }
        )

        mBinding.rgResources.check(
            when(MapplsDirectionWidgetSetting.instance.resource) {
                DirectionsCriteria.RESOURCE_ROUTE -> mBinding.rbRoute.id
                DirectionsCriteria.RESOURCE_ROUTE_ETA -> mBinding.rbRouteEta.id
                DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC -> mBinding.rbRouteTraffic.id
                else -> -1
            }
        )
        mBinding.rgProfile.check(
            when(MapplsDirectionWidgetSetting.instance.profile) {
                DirectionsCriteria.PROFILE_DRIVING -> mBinding.rbDriving.id
                DirectionsCriteria.PROFILE_BIKING -> mBinding.rbBiking.id
                DirectionsCriteria.PROFILE_WALKING -> mBinding.rbWalking.id
                DirectionsCriteria.PROFILE_TRUCKING -> mBinding.rbTrucking.id

                else -> -1
            }
        )
        when(MapplsDirectionWidgetSetting.instance.overview) {
            DirectionsCriteria.OVERVIEW_FULL ->
                mBinding.rgOverView.check(mBinding.rbOverviewFull.id)
            DirectionsCriteria.OVERVIEW_SIMPLIFIED ->
                mBinding.rgOverView.check(mBinding.rbOverviewSimplified.id)
            DirectionsCriteria.OVERVIEW_FALSE ->
                mBinding.rgOverView.check(mBinding.rbOverviewFalse.id)
        }

        mBinding.cbExcludeToll.isChecked = MapplsDirectionWidgetSetting.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_TOLL
        )
        mBinding.cbExcludeFerry.isChecked = MapplsDirectionWidgetSetting.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_FERRY
        )
        mBinding.cbExcludeTunnel.isChecked = MapplsDirectionWidgetSetting.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_TUNNEL
        )
        mBinding.cbExcludeMotorway.isChecked = MapplsDirectionWidgetSetting.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_MOTORWAY
        )
        mBinding.cbExcludeRestricted.isChecked = MapplsDirectionWidgetSetting.instance.excludes.contains(
            DirectionsCriteria.EXCLUDE_RESTRICTED
        )
    }

}