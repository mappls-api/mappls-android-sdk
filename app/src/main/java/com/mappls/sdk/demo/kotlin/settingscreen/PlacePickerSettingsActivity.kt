package com.mappls.sdk.demo.kotlin.settingscreen


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityPlacePickerSettingsBinding
import com.mappls.sdk.demo.java.utils.InputFilterMinMax
import com.mappls.sdk.demo.kotlin.settings.MapplsPlacePickerSetting
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria

class PlacePickerSettingsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPlacePickerSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_picker_settings)
        mBinding.etLatitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-90, 90))
        mBinding.etLongitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-180, 180))
        initSettings()
        initCallback()
    }

    private fun initCallback() {
        mBinding.pickerToolbarColor.text = String.format("#%06X", 0xFFFFFF and MapplsPlacePickerSetting.instance.pickerToolbarColor)
        mBinding.includeDeviceLocation.isChecked = MapplsPlacePickerSetting.instance.isIncludeDeviceLocation
        mBinding.includeSearchButton.isChecked = MapplsPlacePickerSetting.instance.isIncludeSearch
        mBinding.tokenizeAddressBtn.isChecked = MapplsPlacePickerSetting.instance.isTokenizeAddress
        mBinding.cbDefault.setOnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlacePickerSetting.instance.isDefault = isChecked
            if (isChecked) {
                mBinding.disableView.visibility = View.VISIBLE
            } else {
                mBinding.disableView.visibility = View.GONE
            }
        }
        mBinding.rgVertical.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == mBinding.rbTop.getId()) {
                MapplsPlacePickerSetting.instance.signatureVertical = PlaceOptions.GRAVITY_TOP
            } else {
                MapplsPlacePickerSetting.instance.signatureVertical = PlaceOptions.GRAVITY_BOTTOM
            }
        }
        mBinding.rgHorizontal.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_left -> MapplsPlacePickerSetting.instance.signatureHorizontal = PlaceOptions.GRAVITY_LEFT
                R.id.rb_center -> MapplsPlacePickerSetting.instance.signatureHorizontal = PlaceOptions.GRAVITY_CENTER
                R.id.rb_right -> MapplsPlacePickerSetting.instance.signatureHorizontal = PlaceOptions.GRAVITY_RIGHT
            }
        }
        mBinding.rgLogoSize.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_small -> MapplsPlacePickerSetting.instance.logoSize = PlaceOptions.SIZE_SMALL
                R.id.rb_medium -> MapplsPlacePickerSetting.instance.logoSize = PlaceOptions.SIZE_MEDIUM
                R.id.rb_large -> MapplsPlacePickerSetting.instance.logoSize = PlaceOptions.SIZE_LARGE
            }
        }
        mBinding.btnSaveLocation.setOnClickListener {
            if (!TextUtils.isEmpty(mBinding.etLatitude.text.toString().trim()) && !TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim())) {
                MapplsPlacePickerSetting.instance.location = Point.fromLngLat(mBinding.etLongitude.getText().toString().trim().toDouble(), mBinding.etLatitude.getText().toString().trim().toDouble())
            } else {
                MapplsPlacePickerSetting.instance.location = null
            }
            Toast.makeText(this@PlacePickerSettingsActivity, "Location save successfully", Toast.LENGTH_SHORT).show()
        }
        mBinding.btnSaveHistoryCount.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etHistoryCount.text.toString())) {
                MapplsPlacePickerSetting.instance.historyCount = mBinding.etHistoryCount.getText().toString().toInt()
            } else {
                MapplsPlacePickerSetting.instance.historyCount = null
            }
            Toast.makeText(this@PlacePickerSettingsActivity, "History Count save successfully", Toast.LENGTH_SHORT).show()
        }
        mBinding.btnSaveFilter.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(mBinding.etFilter.text.toString().trim())) {
                MapplsPlacePickerSetting.instance.filter = mBinding.etFilter.getText().toString().trim()
            } else {
                MapplsPlacePickerSetting.instance.filter = null
            }
            Toast.makeText(this@PlacePickerSettingsActivity, "Filter save successfully", Toast.LENGTH_SHORT).show()
        })
        mBinding.cbEnableHistory.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlacePickerSetting.instance.isEnableHistory = isChecked
            if (isChecked) {
                mBinding.historyCountLayout.visibility = View.VISIBLE
            } else {
                mBinding.historyCountLayout.visibility = View.GONE
            }
        })
        mBinding.btnPodClear.setOnClickListener(View.OnClickListener {
            mBinding.rgPod.clearCheck()
            MapplsPlacePickerSetting.instance.pod = null
        })
        mBinding.rgPod.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_city -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_CITY
                R.id.rb_state -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_STATE
                R.id.rb_sub_district -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_SUB_DISTRICT
                R.id.rb_district -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_DISTRICT
                R.id.rb_sub_locality -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_SUB_LOCALITY
                R.id.rb_sub_sub_locality -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_SUB_SUB_LOCALITY
                R.id.rb_locality -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_LOCALITY
                R.id.rb_village -> MapplsPlacePickerSetting.instance.pod = AutoSuggestCriteria.POD_VILLAGE
            }
        }
        mBinding.btnHint.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(mBinding.etHint.getText().toString().trim())) {
                MapplsPlacePickerSetting.instance.hint = mBinding.etHint.getText().toString().trim()
                Toast.makeText(this@PlacePickerSettingsActivity, "Hint save successfully", Toast.LENGTH_SHORT).show()
            } else {
                mBinding.etHint.setText(MapplsPlacePickerSetting.instance.hint)
                Toast.makeText(this@PlacePickerSettingsActivity, "Hint is mandatory", Toast.LENGTH_SHORT).show()
            }
        })
        mBinding.btnZoom.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etZoom.getText().toString().trim())) {
                MapplsPlacePickerSetting.instance.zoom = mBinding.etZoom.getText().toString().trim().toDouble()
                Toast.makeText(this@PlacePickerSettingsActivity, "zoom save successfully", Toast.LENGTH_SHORT).show()
            }
        }

        /* mBinding.cbEnableTextSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 MapplsPlacePickerSetting.getInstance().setEnableTextSearch(isChecked);
             }
         });*/mBinding.backgroundRG.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_white -> MapplsPlacePickerSetting.instance.backgroundColor = R.color.white
                R.id.rb_black -> MapplsPlacePickerSetting.instance.backgroundColor = android.R.color.black
                R.id.rb_red -> MapplsPlacePickerSetting.instance.backgroundColor = android.R.color.holo_red_light
                R.id.rb_green -> MapplsPlacePickerSetting.instance.backgroundColor = android.R.color.holo_green_dark
                R.id.rb_blue -> MapplsPlacePickerSetting.instance.backgroundColor = android.R.color.holo_blue_bright
            }
        })
        mBinding.toolbarRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_white_toolbar -> MapplsPlacePickerSetting.instance.toolbarColor = R.color.white
                R.id.rb_black_toolbar -> MapplsPlacePickerSetting.instance.toolbarColor = android.R.color.black
                R.id.rb_red_toolbar -> MapplsPlacePickerSetting.instance.toolbarColor =android.R.color.holo_red_light
                R.id.rb_green_toolbar -> MapplsPlacePickerSetting.instance.toolbarColor = android.R.color.holo_green_dark
                R.id.rb_blue_toolbar -> MapplsPlacePickerSetting.instance.toolbarColor = android.R.color.holo_blue_bright
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        mBinding.toolbarColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsPlacePickerSetting.instance.pickerToolbarColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok") { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                        MapplsPlacePickerSetting.instance.pickerToolbarColor = selectedColor
                        mBinding.pickerToolbarColor.text = String.format("#%06X", 0xFFFFFF and selectedColor)
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog, which -> }
                    .build()
                    .show()
        }
        mBinding.includeDeviceLocation.setOnCheckedChangeListener { buttonView, isChecked -> MapplsPlacePickerSetting.instance.isIncludeDeviceLocation = isChecked }
        mBinding.includeSearchButton.setOnCheckedChangeListener { buttonView, isChecked -> MapplsPlacePickerSetting.instance.isIncludeSearch = isChecked }
        mBinding.tokenizeAddressBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsPlacePickerSetting.instance.isTokenizeAddress = isChecked }
        mBinding.cbDefault.isChecked = MapplsPlacePickerSetting.instance.isDefault
        mBinding.disableView.visibility = if (MapplsPlacePickerSetting.instance.isDefault) View.VISIBLE else View.GONE
        if (MapplsPlacePickerSetting.instance.historyCount != null) {
            mBinding.etHistoryCount.setText(MapplsPlacePickerSetting.instance.historyCount.toString())
        }
        if (MapplsPlacePickerSetting.instance.signatureVertical == PlaceOptions.GRAVITY_TOP) {
            mBinding.rgVertical.check(mBinding.rbTop.id)
        } else {
            mBinding.rgVertical.check(mBinding.rbBottom.id)
        }
        if (MapplsPlacePickerSetting.instance.signatureHorizontal == PlaceOptions.GRAVITY_LEFT) {
            mBinding.rgHorizontal.check(mBinding.rbLeft.getId())
        } else if (MapplsPlacePickerSetting.instance.signatureHorizontal == PlaceOptions.GRAVITY_CENTER) {
            mBinding.rgHorizontal.check(mBinding.rbCenter.getId())
        } else {
            mBinding.rgHorizontal.check(mBinding.rbRight.getId())
        }
        if (MapplsPlacePickerSetting.instance.logoSize == PlaceOptions.SIZE_SMALL) {
            mBinding.rgLogoSize.check(R.id.rb_small)
        } else if (MapplsPlacePickerSetting.instance.logoSize == PlaceOptions.SIZE_MEDIUM) {
            mBinding.rgLogoSize.check(R.id.rb_medium)
        } else {
            mBinding.rgLogoSize.check(R.id.rb_large)
        }
        if (MapplsPlacePickerSetting.instance.location != null) {
            mBinding.etLatitude.setText(MapplsPlacePickerSetting.instance.location?.latitude().toString())
            mBinding.etLongitude.setText(MapplsPlacePickerSetting.instance.location?.longitude().toString())
        }
        if (MapplsPlacePickerSetting.instance.filter != null) {
            mBinding.etFilter.setText(MapplsPlacePickerSetting.instance.filter)
        }
        mBinding.cbEnableHistory.isChecked = MapplsPlacePickerSetting.instance.isEnableHistory
        if (!MapplsPlacePickerSetting.instance.isEnableHistory) {
            mBinding.historyCountLayout.visibility = View.GONE
        } else {
            mBinding.historyCountLayout.visibility = View.VISIBLE
        }
        if (MapplsPlacePickerSetting.instance.pod != null) {
            if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_CITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbCity.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_DISTRICT, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbDistrict.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_LOCALITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbLocality.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_STATE, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbState.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_SUB_DISTRICT, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbSubDistrict.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_SUB_LOCALITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbSubLocality.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbSubSubLocality.getId())
            } else if (MapplsPlacePickerSetting.instance.pod.equals(AutoSuggestCriteria.POD_VILLAGE, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbVillage.getId())
            }
        } else {
            mBinding.rgPod.clearCheck()
        }
        mBinding.etHint.setText(MapplsPlacePickerSetting.instance.hint)
        if (MapplsPlacePickerSetting.instance.zoom != null) {
            mBinding.etZoom.setText(MapplsPlacePickerSetting.instance.zoom.toString())
        }

        if (MapplsPlacePickerSetting.instance.backgroundColor == R.color.white) {
            mBinding.backgroundRG.check(mBinding.rbWhite.id)
        } else if (MapplsPlacePickerSetting.instance.backgroundColor == android.R.color.black) {
            mBinding.backgroundRG.check(mBinding.rbBlack.id)
        } else if (MapplsPlacePickerSetting.instance.backgroundColor == android.R.color.holo_red_light) {
            mBinding.backgroundRG.check(mBinding.rbRed.id)
        } else if (MapplsPlacePickerSetting.instance.backgroundColor == android.R.color.holo_green_dark) {
            mBinding.backgroundRG.check(mBinding.rbGreen.id)
        } else if (MapplsPlacePickerSetting.instance.backgroundColor == android.R.color.holo_blue_bright) {
            mBinding.backgroundRG.check(mBinding.rbBlue.id)
        }
        if (MapplsPlacePickerSetting.instance.toolbarColor == R.color.white) {
            mBinding.toolbarRG.check(mBinding.rbWhiteToolbar.id)
        } else if (MapplsPlacePickerSetting.instance.toolbarColor == android.R.color.black) {
            mBinding.toolbarRG.check(mBinding.rbBlackToolbar.id)
        } else if (MapplsPlacePickerSetting.instance.toolbarColor == android.R.color.holo_red_light) {
            mBinding.toolbarRG.check(mBinding.rbRedToolbar.id)
        } else if (MapplsPlacePickerSetting.instance.toolbarColor == android.R.color.holo_green_dark) {
            mBinding.toolbarRG.check(mBinding.rbGreenToolbar.id)
        } else if (MapplsPlacePickerSetting.instance.toolbarColor == android.R.color.holo_blue_bright) {
            mBinding.toolbarRG.check(mBinding.rbBlueToolbar.id)
        }
    }
}