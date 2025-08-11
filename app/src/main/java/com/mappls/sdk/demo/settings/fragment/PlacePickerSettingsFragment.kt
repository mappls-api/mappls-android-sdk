package com.mappls.sdk.demo.settings.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mappls.sdk.demo.activity.widgets.PlacePickerActivity
import com.mappls.sdk.demo.databinding.FragmentPlacePickerSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsPlacePickerSettings
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria


class PlacePickerSettingsFragment : Fragment() {

    lateinit var mBinding:FragmentPlacePickerSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlacePickerSettingsBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.settingsHeader.headerTitle.text = "Place Picker Setting"
        mBinding.settingsHeader.headerBack.setOnClickListener {
            (requireActivity() as PlacePickerActivity).popBackStack(this)
        }
        setValue()
        mBinding.swTokenizeAddress.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.tokenizeAddress = isCheck
        }
        mBinding.swHistory.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.enableHistory = isCheck
        }

        mBinding.swIncludeSearchButton.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.includeSearch = isCheck
        }

        mBinding.swIncludeDeviceLocation.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.includeDeviceLocation = isCheck
        }
        mBinding.swEntryCoordinateSnapEnable.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.entryCoordinateSnapEnable = isCheck
        }
        mBinding.swShowEntryCoordinate.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.showEntryCoordinate = isCheck
        }
        mBinding.swEnableFootPrint.setOnCheckedChangeListener { _, isCheck ->
            MapplsPlacePickerSettings.instance.enableFootPrint = isCheck
        }
        mBinding.btnLocation.setOnClickListener {
            val etloc = mBinding.etLocation.text.toString()
            if (etloc.isNotEmpty()) {
                val latLng = etloc.split(",")
                if (latLng.size == 2) {
                    val latitude = latLng[0].toDoubleOrNull()
                    val longitude = latLng[1].toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        MapplsPlacePickerSettings.instance.location =
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
                MapplsPlacePickerSettings.instance.filter = etFilter
            } else {
                MapplsPlacePickerSettings.instance.filter = null
            }
        }
        mBinding.btnSearchHint.setOnClickListener {
            val etHint = mBinding.etSearchHint.text.toString()
            if(etHint.isEmpty().not()) {
                MapplsPlacePickerSettings.instance.hint = etHint
            } else {
                MapplsPlacePickerSettings.instance.hint = null
            }
        }
        mBinding.btnZoom.setOnClickListener {
            val zoom = mBinding.etZoom.text.toString().toDoubleOrNull()
            if(zoom != null && zoom < 24 && zoom > 0) {
                MapplsPlacePickerSettings.instance.zoom = zoom
            } else {
                MapplsPlacePickerSettings.instance.zoom = null
            }
        }
        mBinding.btnClearPod.setOnClickListener {
            MapplsPlacePickerSettings.instance.pod = null
            mBinding.rgPod.clearCheck()
        }
        mBinding.rgPod.setOnCheckedChangeListener { _, checkId ->
            MapplsPlacePickerSettings.instance.pod =
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
            MapplsPlacePickerSettings.instance.signatureVertical =
                when(checkId) {
                    mBinding.rbTop.id -> PlaceOptions.GRAVITY_TOP
                    mBinding.rbBottom.id -> PlaceOptions.GRAVITY_BOTTOM
                    else -> {
                        PlaceOptions.GRAVITY_TOP
                    }
                }
        }
        mBinding.rgSignatureHorizontal.setOnCheckedChangeListener { _, checkId ->
            MapplsPlacePickerSettings.instance.signatureHorizontal =
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
            MapplsPlacePickerSettings.instance.logoSize =
                when(checkId) {
                    mBinding.rbSmall.id -> PlaceOptions.SIZE_SMALL
                    mBinding.rbMedium.id -> PlaceOptions.SIZE_MEDIUM
                    mBinding.rbLarge.id -> PlaceOptions.SIZE_LARGE
                    else -> {
                        PlaceOptions.SIZE_SMALL
                    }
                }
        }
        mBinding.rgBackground.setOnCheckedChangeListener { _, checkId ->
            MapplsPlacePickerSettings.instance.backgroundColor =
                when(checkId) {
                    mBinding.rbWhite.id -> android.R.color.white
                    mBinding.rbBlack.id -> android.R.color.black
                    mBinding.rbRed.id -> android.R.color.holo_red_light
                    mBinding.rbGreen.id -> android.R.color.holo_green_dark
                    mBinding.rbBlue.id -> android.R.color.holo_blue_bright
                    else -> {
                        android.R.color.white
                    }
                }
        }

        mBinding.rgToolbarBg.setOnCheckedChangeListener { _, checkId ->
            MapplsPlacePickerSettings.instance.toolbarColor =
                when(checkId) {
                    mBinding.rbWhiteTool.id -> android.R.color.white
                    mBinding.rbBlackTool.id -> android.R.color.black
                    mBinding.rbRedTool.id -> android.R.color.holo_red_light
                    mBinding.rbGreenTool.id -> android.R.color.holo_green_dark
                    mBinding.rbBlueTool.id -> android.R.color.holo_blue_bright
                    else -> {android.R.color.white}
                }
        }
        mBinding.btnClearToolTint.setOnClickListener {
            MapplsPlacePickerSettings.instance.toolbarTintColor = null
            mBinding.rgToolbarTint.clearCheck()
        }
        mBinding.rgToolbarTint.setOnCheckedChangeListener { _, checkId ->
            MapplsPlacePickerSettings.instance.toolbarTintColor =
                when(checkId) {
                    mBinding.rbWhiteTint.id -> android.R.color.white
                    mBinding.rbBlackTint.id -> android.R.color.black
                    mBinding.rbRedTint.id -> android.R.color.holo_red_light
                    mBinding.rbGreenTint.id -> android.R.color.holo_green_dark
                    mBinding.rbBlueTint.id -> android.R.color.holo_blue_bright
                    else -> null
                }
        }

        mBinding.etToolbarColor.setOnClickListener { v ->

                ColorPickerDialogBuilder
                    .with(context)
                    .setTitle("Choose color")
                    .initialColor(MapplsPlacePickerSettings.instance.pickerToolbarColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok"
                    ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                        MapplsPlacePickerSettings.instance.pickerToolbarColor = selectedColor
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
        mBinding.etFootprintFillColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsPlacePickerSettings.instance.footprintFillColor.replace("#", "").toInt(16))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsPlacePickerSettings.instance.footprintFillColor = selectedColor.toString()
                    mBinding.etFootprintFillColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and MapplsPlacePickerSettings.instance.footprintFillColor.replace("#", "").toInt(16))
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()

        }
        mBinding.etFootprintStrokeColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsPlacePickerSettings.instance.footprintStrokeColor.replace("#", "").toInt(16))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsPlacePickerSettings.instance.footprintStrokeColor = selectedColor.toString()
                    mBinding.etFootprintStrokeColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and MapplsPlacePickerSettings.instance.footprintStrokeColor.replace("#", "").toInt(16))
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()

        }
        mBinding.etEntryCoordinateCircleColor.setOnClickListener { v ->

            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsPlacePickerSettings.instance.entryCoordinateCircleColor.replace("#", "").toInt(16))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsPlacePickerSettings.instance.entryCoordinateCircleColor = selectedColor.toString()
                    mBinding.etEntryCoordinateCircleColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and  MapplsPlacePickerSettings.instance.entryCoordinateCircleColor.replace("#", "").toInt(16))
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> })
                .build()
                .show()

        }
        mBinding.btnEntryCoordinateSnappingRadius.setOnClickListener { v ->
            val snapRadius = mBinding.etEntryCoordinateSnappingRadius.text.toString()
            if(snapRadius.isEmpty().not()) {
                MapplsPlacePickerSettings.instance.entryCoordinateSnappingRadius = snapRadius.toInt()
            } else {
                MapplsPlacePickerSettings.instance.entryCoordinateSnappingRadius = 0
            }


        }
        mBinding.btnFootprintFillOpacity.setOnClickListener { v ->
            val footOpacity = mBinding.etFootprintFillOpacity.text.toString()
            if(footOpacity.isNotEmpty()) {
                MapplsPlacePickerSettings.instance.footprintFillOpacity = footOpacity.toDouble()
            } else {
                MapplsPlacePickerSettings.instance.footprintFillOpacity = 0.0
            }


        }
        mBinding.btnFootprintLineStrokeOpacity.setOnClickListener { v ->
            val lineOpacity = mBinding.etFootprintLineStrokeOpacity.text.toString()
            if(lineOpacity.isNotEmpty()) {
                MapplsPlacePickerSettings.instance.footprintLineStrokeOpacity = lineOpacity.toDouble()
            } else {
                MapplsPlacePickerSettings.instance.footprintLineStrokeOpacity = 0.0
            }
        }
        mBinding.btnFootprintLineStrokeWidth.setOnClickListener { v ->
            val widthOpacity = mBinding.etFootprintLineStrokeWidth.text.toString()
            if(widthOpacity.isNotEmpty()) {
                MapplsPlacePickerSettings.instance.footprintLineStrokeWidth = widthOpacity.toInt()
            } else {
                MapplsPlacePickerSettings.instance.footprintLineStrokeWidth = 0
            }
        }
    }

    private fun setValue() {
        mBinding.etToolbarColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsPlacePickerSettings.instance.pickerToolbarColor)
            )
        )
        mBinding.etFootprintFillColor.setText(
            String.format(
                "#%06X",
                0xFFFFFF and MapplsPlacePickerSettings.instance.footprintFillColor.replace("#", "").toInt(16)
            )
        )
        mBinding.etFootprintStrokeColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsPlacePickerSettings.instance.footprintStrokeColor.replace("#", "").toInt(16))
            )
        )
        mBinding.etEntryCoordinateCircleColor.setText(
            java.lang.String.format(
                "#%06X",
                (0xFFFFFF and MapplsPlacePickerSettings.instance.entryCoordinateCircleColor.replace("#", "").toInt(16))
            )
        )
        mBinding.etEntryCoordinateSnappingRadius.setText("${MapplsPlacePickerSettings.instance.entryCoordinateSnappingRadius}")
        mBinding.etFootprintFillOpacity.setText("${MapplsPlacePickerSettings.instance.footprintFillOpacity}")
        mBinding.etFootprintLineStrokeOpacity.setText("${MapplsPlacePickerSettings.instance.footprintLineStrokeOpacity}")
        mBinding.etFootprintLineStrokeWidth.setText("${MapplsPlacePickerSettings.instance.footprintLineStrokeWidth}")

        mBinding.swEntryCoordinateSnapEnable.isChecked = MapplsPlacePickerSettings.instance.entryCoordinateSnapEnable
        mBinding.swShowEntryCoordinate.isChecked = MapplsPlacePickerSettings.instance.showEntryCoordinate
        mBinding.swEnableFootPrint.isChecked = MapplsPlacePickerSettings.instance.enableFootPrint

        mBinding.swTokenizeAddress.isChecked = MapplsPlacePickerSettings.instance.tokenizeAddress
        mBinding.swIncludeSearchButton.isChecked = MapplsPlacePickerSettings.instance.includeSearch
        mBinding.swIncludeDeviceLocation.isChecked = MapplsPlacePickerSettings.instance.includeDeviceLocation
        mBinding.swHistory.isChecked = MapplsPlacePickerSettings.instance.enableHistory

        mBinding.etSearchHint.setText(MapplsPlacePickerSettings.instance.hint)
        mBinding.etFilter.setText(MapplsPlacePickerSettings.instance.filter ?: "")
        mBinding.etLocation.setText(MapplsPlacePickerSettings.instance.location?.toString() ?: "")
        mBinding.etZoom.setText(MapplsPlacePickerSettings.instance.zoom?.toString() ?: "")
        if(MapplsPlacePickerSettings.instance.pod != null) {
            mBinding.rgPod.check(
                when(MapplsPlacePickerSettings.instance.pod) {
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
            when(MapplsPlacePickerSettings.instance.signatureVertical) {
                PlaceOptions.GRAVITY_TOP -> mBinding.rbTop.id
                PlaceOptions.GRAVITY_BOTTOM -> mBinding.rbBottom.id

                else -> -1
            }
        )

        mBinding.rgSignatureHorizontal.check(
            when(MapplsPlacePickerSettings.instance.signatureHorizontal) {
                PlaceOptions.GRAVITY_LEFT -> mBinding.rbLeft.id
                PlaceOptions.GRAVITY_CENTER -> mBinding.rbCenter.id
                PlaceOptions.GRAVITY_RIGHT -> mBinding.rbRight.id

                else -> -1
            }
        )

        mBinding.rgLogoSize.check(
            when(MapplsPlacePickerSettings.instance.logoSize) {
                PlaceOptions.SIZE_SMALL -> mBinding.rbSmall.id
                PlaceOptions.SIZE_MEDIUM -> mBinding.rbMedium.id
                PlaceOptions.SIZE_LARGE -> mBinding.rbLarge.id

                else -> -1
            }
        )

        mBinding.rgBackground.check(
            when(MapplsPlacePickerSettings.instance.backgroundColor) {
                android.R.color.white -> mBinding.rbWhite.id
                android.R.color.black -> mBinding.rbBlack.id
                android.R.color.holo_red_light -> mBinding.rbRed.id
                android.R.color.holo_green_dark -> mBinding.rbGreen.id
                android.R.color.holo_blue_bright -> mBinding.rbBlue.id
                else -> -1
            }
        )
        if(MapplsPlacePickerSettings.instance.toolbarColor != null) {
            mBinding.rgToolbarBg.check(
                when(MapplsPlacePickerSettings.instance.toolbarColor) {
                    android.R.color.white -> mBinding.rbWhiteTool.id
                    android.R.color.black -> mBinding.rbBlackTool.id
                    android.R.color.holo_red_light -> mBinding.rbRedTool.id
                    android.R.color.holo_green_dark -> mBinding.rbGreenTool.id
                    android.R.color.holo_blue_bright -> mBinding.rbBlueTool.id
                    else -> -1
                }
            )
        }
        if(MapplsPlacePickerSettings.instance.toolbarTintColor != null) {
            mBinding.rgToolbarTint.check(
                when(MapplsPlacePickerSettings.instance.toolbarTintColor) {
                    android.R.color.white -> mBinding.rbWhiteTint.id
                    android.R.color.black -> mBinding.rbBlackTint.id
                    android.R.color.holo_red_light -> mBinding.rbRedTint.id
                    android.R.color.holo_green_dark -> mBinding.rbGreenTint.id
                    android.R.color.holo_blue_bright -> mBinding.rbBlueTint.id
                    else -> -1
                }
            )
        }
    }


}