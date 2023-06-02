package com.mappls.sdk.demo.kotlin.settingscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityPlaceAutocompleteSettingBinding
import com.mappls.sdk.demo.java.utils.InputFilterMinMax
import com.mappls.sdk.demo.kotlin.settings.MapplsPlaceWidgetSetting
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria

class PlaceAutocompleteSettingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPlaceAutocompleteSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_place_autocomplete_setting)
        mBinding.etLatitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-90, 90))
        mBinding.etLongitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-180, 180))
        initSettings()
        initCallback()
    }

    private fun initCallback() {
        mBinding.cbDefault.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlaceWidgetSetting.instance.isDefault = isChecked
            if (isChecked) {
                mBinding.disableView.visibility = View.VISIBLE
            } else {
                mBinding.disableView.visibility = View.GONE
            }
        })
        mBinding.rgVertical.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == mBinding.rbTop.getId()) {
                MapplsPlaceWidgetSetting.instance.signatureVertical = PlaceOptions.GRAVITY_TOP
            } else {
                MapplsPlaceWidgetSetting.instance.signatureVertical = PlaceOptions.GRAVITY_BOTTOM
            }
        })
        mBinding.rgHorizontal.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_left -> MapplsPlaceWidgetSetting.instance.signatureHorizontal =
                    PlaceOptions.GRAVITY_LEFT

                R.id.rb_center -> MapplsPlaceWidgetSetting.instance.signatureHorizontal =
                    PlaceOptions.GRAVITY_CENTER

                R.id.rb_right -> MapplsPlaceWidgetSetting.instance.signatureHorizontal =
                    PlaceOptions.GRAVITY_RIGHT
            }
        })
        mBinding.rgLogoSize.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_small -> MapplsPlaceWidgetSetting.instance.logoSize =
                    PlaceOptions.SIZE_SMALL

                R.id.rb_medium -> MapplsPlaceWidgetSetting.instance.logoSize =
                    PlaceOptions.SIZE_MEDIUM

                R.id.rb_large -> MapplsPlaceWidgetSetting.instance.logoSize =
                    PlaceOptions.SIZE_LARGE
            }
        })
        mBinding.btnSaveLocation.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(
                    mBinding.etLatitude.getText().toString().trim()
                ) && !TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim())
            ) {
                MapplsPlaceWidgetSetting.instance.location = Point.fromLngLat(
                    mBinding.etLongitude.getText().toString().trim().toDouble(),
                    mBinding.etLatitude.getText().toString().trim().toDouble()
                )
            } else {
                MapplsPlaceWidgetSetting.instance.location = null
            }
            Toast.makeText(
                this@PlaceAutocompleteSettingActivity,
                "Location save successfully",
                Toast.LENGTH_SHORT
            ).show()
        })
        mBinding.btnSaveFilter.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(mBinding.etFilter.getText().toString().trim())) {
                MapplsPlaceWidgetSetting.instance.filter =
                    mBinding.etFilter.getText().toString().trim()
            } else {
                MapplsPlaceWidgetSetting.instance.filter = null
            }
            Toast.makeText(
                this@PlaceAutocompleteSettingActivity,
                "Filter save successfully",
                Toast.LENGTH_SHORT
            ).show()
        })
        mBinding.cbEnableHistory.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlaceWidgetSetting.instance.isEnableHistory = isChecked
            mBinding.etHistoryCount.isEnabled = isChecked
        })
        mBinding.enableLocation.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlaceWidgetSetting.instance.isEnableLocation = isChecked
        })
        mBinding.btnPodClear.setOnClickListener(View.OnClickListener {
            mBinding.rgPod.clearCheck()
            MapplsPlaceWidgetSetting.instance.pod = null
        })
        mBinding.rgPod.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_city -> MapplsPlaceWidgetSetting.instance.pod = AutoSuggestCriteria.POD_CITY
                R.id.rb_state -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_STATE

                R.id.rb_sub_district -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_SUB_DISTRICT

                R.id.rb_district -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_DISTRICT

                R.id.rb_sub_locality -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_SUB_LOCALITY

                R.id.rb_sub_sub_locality -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_SUB_SUB_LOCALITY

                R.id.rb_locality -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_LOCALITY

                R.id.rb_village -> MapplsPlaceWidgetSetting.instance.pod =
                    AutoSuggestCriteria.POD_VILLAGE

                R.id.rb_poi -> MapplsPlaceWidgetSetting.instance.pod = AutoSuggestCriteria.POD_POI
            }
        })
        mBinding.btnHint.setOnClickListener(View.OnClickListener {
            if (!TextUtils.isEmpty(mBinding.etHint.getText().toString().trim())) {
                MapplsPlaceWidgetSetting.instance.hint = mBinding.etHint.getText().toString().trim()
                Toast.makeText(
                    this@PlaceAutocompleteSettingActivity,
                    "Hint save successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mBinding.etHint.setText(MapplsPlaceWidgetSetting.instance.hint)
                Toast.makeText(
                    this@PlaceAutocompleteSettingActivity,
                    "Hint is mandatory",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        mBinding.cbEnableTextSearch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlaceWidgetSetting.instance.isEnableTextSearch = isChecked
        })

        mBinding.backgroundRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_white -> MapplsPlaceWidgetSetting.instance.backgroundColor =
                    android.R.color.white

                R.id.rb_black -> MapplsPlaceWidgetSetting.instance.backgroundColor =
                    android.R.color.black

                R.id.rb_red -> MapplsPlaceWidgetSetting.instance.backgroundColor =
                    android.R.color.holo_red_light

                R.id.rb_green -> MapplsPlaceWidgetSetting.instance.backgroundColor =
                    android.R.color.holo_green_dark

                R.id.rb_blue -> MapplsPlaceWidgetSetting.instance.backgroundColor =
                    android.R.color.holo_blue_bright
            }
        }
        mBinding.toolbarRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_white_toolbar -> MapplsPlaceWidgetSetting.instance.toolbarColor =
                    android.R.color.white

                R.id.rb_black_toolbar -> MapplsPlaceWidgetSetting.instance.toolbarColor =
                    android.R.color.black

                R.id.rb_red_toolbar -> MapplsPlaceWidgetSetting.instance.toolbarColor =
                    android.R.color.holo_red_light

                R.id.rb_green_toolbar -> MapplsPlaceWidgetSetting.instance.toolbarColor =
                    android.R.color.holo_green_dark

                R.id.rb_blue_toolbar -> MapplsPlaceWidgetSetting.instance.toolbarColor =
                    android.R.color.holo_blue_bright
            }
        }
        mBinding.cbEnableBridge.setOnCheckedChangeListener { _, isChecked ->
            MapplsPlaceWidgetSetting.instance.isBridgeEnable = isChecked
        }
        mBinding.cbEnableHyperlocal.setOnCheckedChangeListener { _, isChecked ->
            MapplsPlaceWidgetSetting.instance.isHyperLocalEnable = isChecked
        }
        mBinding.cbEnableFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
            MapplsPlaceWidgetSetting.instance.isEnableShowFavorite =
                isChecked
        }
        mBinding.etDBounce.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s)) {
                    val value = s.toString().toInt()
                    if (value > 1500) {
                        Toast.makeText(
                            this@PlaceAutocompleteSettingActivity,
                            "Maximum value should be 1500",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        MapplsPlaceWidgetSetting.instance.deBounce = value
                    }
                } else {
                    MapplsPlaceWidgetSetting.instance.deBounce = 0
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mBinding.etHistoryCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s)) {
                    val value = s.toString().toInt()
                    MapplsPlaceWidgetSetting.instance.historyCount = value
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        mBinding.cbDefault.isChecked = MapplsPlaceWidgetSetting.instance.isDefault
        mBinding.disableView.visibility = if (MapplsPlaceWidgetSetting.instance.isDefault) View.VISIBLE else View.GONE
        mBinding.etHistoryCount.isEnabled = MapplsPlaceWidgetSetting.instance.isEnableHistory
        if (MapplsPlaceWidgetSetting.instance.signatureVertical == PlaceOptions.GRAVITY_TOP) {
            mBinding.rgVertical.check(mBinding.rbTop.id)
        } else {
            mBinding.rgVertical.check(mBinding.rbBottom.id)
        }
        if (MapplsPlaceWidgetSetting.instance.signatureHorizontal == PlaceOptions.GRAVITY_LEFT) {
            mBinding.rgHorizontal.check(mBinding.rbLeft.id)
        } else if (MapplsPlaceWidgetSetting.instance.signatureHorizontal == PlaceOptions.GRAVITY_CENTER) {
            mBinding.rgHorizontal.check(mBinding.rbCenter.id)
        } else {
            mBinding.rgHorizontal.check(mBinding.rbRight.id)
        }
        if (MapplsPlaceWidgetSetting.instance.logoSize == PlaceOptions.SIZE_SMALL) {
            mBinding.rgLogoSize.check(R.id.rb_small)
        } else if (MapplsPlaceWidgetSetting.instance.logoSize == PlaceOptions.SIZE_MEDIUM) {
            mBinding.rgLogoSize.check(R.id.rb_medium)
        } else {
            mBinding.rgLogoSize.check(R.id.rb_large)
        }
        if (MapplsPlaceWidgetSetting.instance.location != null) {
            mBinding.etLatitude.setText(
                MapplsPlaceWidgetSetting.instance.location?.latitude().toString()
            )
            mBinding.etLongitude.setText(
                MapplsPlaceWidgetSetting.instance.location?.longitude().toString()
            )
        }
        if (MapplsPlaceWidgetSetting.instance.filter != null) {
            mBinding.etFilter.setText(MapplsPlaceWidgetSetting.instance.filter)
        }
        mBinding.cbEnableHistory.isChecked = MapplsPlaceWidgetSetting.instance.isEnableHistory
        if (MapplsPlaceWidgetSetting.instance.pod != null) {
            if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_CITY,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbCity.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_DISTRICT,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbDistrict.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_LOCALITY,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbLocality.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_STATE,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbState.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_SUB_DISTRICT,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbSubDistrict.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_SUB_LOCALITY,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbSubLocality.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_SUB_SUB_LOCALITY,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbSubSubLocality.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(
                    AutoSuggestCriteria.POD_VILLAGE,
                    ignoreCase = true
                )
            ) {
                mBinding.rgPod.check(mBinding.rbVillage.getId())
            } else if (MapplsPlaceWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_POI)) {
                mBinding.rgPod.check(mBinding.rbPoi.id)
            }
        } else {
            mBinding.rgPod.clearCheck()
        }
        mBinding.etHint.setText(MapplsPlaceWidgetSetting.instance.hint)
        mBinding.cbEnableTextSearch.isChecked = MapplsPlaceWidgetSetting.instance.isEnableTextSearch

        when (MapplsPlaceWidgetSetting.instance.backgroundColor) {
            android.R.color.white -> {
                mBinding.backgroundRG.check(mBinding.rbWhite.id)
            }

            android.R.color.black -> {
                mBinding.backgroundRG.check(mBinding.rbBlack.id)
            }

            android.R.color.holo_red_light -> {
                mBinding.backgroundRG.check(mBinding.rbRed.id)
            }

            android.R.color.holo_green_dark -> {
                mBinding.backgroundRG.check(mBinding.rbGreen.id)
            }

            android.R.color.holo_blue_bright -> {
                mBinding.backgroundRG.check(mBinding.rbBlue.id)
            }
        }


        when (MapplsPlaceWidgetSetting.instance.toolbarColor) {
            android.R.color.white -> {
                mBinding.toolbarRG.check(mBinding.rbWhiteToolbar.id)
            }

            android.R.color.black -> {
                mBinding.toolbarRG.check(mBinding.rbBlackToolbar.id)
            }

            android.R.color.holo_red_light -> {
                mBinding.toolbarRG.check(mBinding.rbRedToolbar.id)
            }

            android.R.color.holo_green_dark -> {
                mBinding.toolbarRG.check(mBinding.rbGreenToolbar.id)
            }

            android.R.color.holo_blue_bright -> {
                mBinding.toolbarRG.check(mBinding.rbBlueToolbar.id)
            }
        }
        mBinding.cbEnableBridge.isChecked = MapplsPlaceWidgetSetting.instance.isBridgeEnable
        mBinding.cbEnableHyperlocal.isChecked = MapplsPlaceWidgetSetting.instance.isHyperLocalEnable
        mBinding.etDBounce.setText(MapplsPlaceWidgetSetting.instance.deBounce.toString())
        mBinding.etHistoryCount.setText(MapplsPlaceWidgetSetting.instance.historyCount.toString())
        mBinding.cbEnableFavorite.isChecked = MapplsPlaceWidgetSetting.instance.isEnableShowFavorite
    }
}