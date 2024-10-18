package com.mappls.sdk.demo.kotlin.settingscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDirectionWidgetSettingBinding
import com.mappls.sdk.demo.java.utils.InputFilterMinMax
import com.mappls.sdk.demo.kotlin.settings.MapplsDirectionWidgetSetting
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import java.util.*

class DirectionWidgetSettingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDirectionWidgetSettingBinding
    private var excludes: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_direction_widget_setting)
        mBinding.etLatitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-90, 90))
        mBinding.etLongitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-180, 180))
        initSettings()
        initCallback()
    }

    private fun initCallback() {
        if (MapplsDirectionWidgetSetting.instance.excludes == null) {
            excludes = ArrayList()
        } else {
            excludes = MapplsDirectionWidgetSetting.instance.excludes
            for (item in excludes!!) {
                when {
                    item.equals(DirectionsCriteria.EXCLUDE_FERRY, ignoreCase = true) -> {
                        mBinding.cbFerry.isChecked = true
                    }
                    item.equals(DirectionsCriteria.EXCLUDE_MOTORWAY, ignoreCase = true) -> {
                        mBinding.cbMotorway.isChecked = true
                    }
                    item.equals(DirectionsCriteria.EXCLUDE_TOLL, ignoreCase = true) -> {
                        mBinding.cbToll.isChecked = true
                    }
                }
            }
        }
        mBinding.showAlternativeBtn.isChecked = MapplsDirectionWidgetSetting.instance.isShowAlternative
        mBinding.showStepsBtn.isChecked = MapplsDirectionWidgetSetting.instance.isSteps
        mBinding.showStartNavigationBtn.isChecked = MapplsDirectionWidgetSetting.instance.isShowStartNavigation
        mBinding.tokenizeAddressBtn.isChecked = MapplsDirectionWidgetSetting.instance.isTokenizeAddress
        mBinding.cbDefault.setOnCheckedChangeListener { buttonView, isChecked ->
            MapplsDirectionWidgetSetting.instance.isDefault = isChecked
            if (isChecked) {
                mBinding.disableView.visibility = View.VISIBLE
            } else {
                mBinding.disableView.visibility = View.GONE
            }
        }
        mBinding.cbFerry.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                excludes!!.add(DirectionsCriteria.EXCLUDE_FERRY)
            } else {
                excludes!!.remove(DirectionsCriteria.EXCLUDE_FERRY)
            }
            MapplsDirectionWidgetSetting.instance.excludes = excludes
        }
        mBinding.cbToll.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                excludes!!.add(DirectionsCriteria.EXCLUDE_TOLL)
            } else {
                excludes!!.remove(DirectionsCriteria.EXCLUDE_TOLL)
            }
            MapplsDirectionWidgetSetting.instance.excludes = excludes
        }
        mBinding.cbMotorway.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                excludes!!.add(DirectionsCriteria.EXCLUDE_MOTORWAY)
            } else {
                excludes!!.remove(DirectionsCriteria.EXCLUDE_MOTORWAY)
            }
            MapplsDirectionWidgetSetting.instance.excludes = excludes
        }
        mBinding.rgVertical.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == mBinding.rbTop.id) {
                MapplsDirectionWidgetSetting.instance.signatureVertical = PlaceOptions.GRAVITY_TOP
            } else {
                MapplsDirectionWidgetSetting.instance.signatureVertical = PlaceOptions.GRAVITY_BOTTOM
            }
        }
        mBinding.rgHorizontal.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_left -> MapplsDirectionWidgetSetting.instance.signatureHorizontal = PlaceOptions.GRAVITY_LEFT
                R.id.rb_center -> MapplsDirectionWidgetSetting.instance.signatureHorizontal = PlaceOptions.GRAVITY_CENTER
                R.id.rb_right -> MapplsDirectionWidgetSetting.instance.signatureHorizontal = PlaceOptions.GRAVITY_RIGHT
            }
        }
        mBinding.rgLogoSize.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_small ->MapplsDirectionWidgetSetting.instance.logoSize = PlaceOptions.SIZE_SMALL
                R.id.rb_medium ->MapplsDirectionWidgetSetting.instance.logoSize = PlaceOptions.SIZE_MEDIUM
                R.id.rb_large -> MapplsDirectionWidgetSetting.instance.logoSize = PlaceOptions.SIZE_LARGE
            }
        }
        mBinding.btnSaveLocation.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etLatitude.text.toString().trim()) && !TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim())) {
                MapplsDirectionWidgetSetting.instance.location = Point.fromLngLat(mBinding.etLongitude.getText().toString().trim().toDouble(), mBinding.etLatitude.getText().toString().trim().toDouble())
            } else {
                MapplsDirectionWidgetSetting.instance.location = null
            }
            Toast.makeText(this@DirectionWidgetSettingActivity, "Location save successfully", Toast.LENGTH_SHORT).show()
        }
        mBinding.btnSaveHistoryCount.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etHistoryCount.getText().toString())) {
                MapplsDirectionWidgetSetting.instance.historyCount = mBinding.etHistoryCount.getText().toString().toInt()
            } else {
                MapplsDirectionWidgetSetting.instance.historyCount = null
            }
            Toast.makeText(this@DirectionWidgetSettingActivity, "History Count save successfully", Toast.LENGTH_SHORT).show()
        }
        mBinding.btnSaveFilter.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etFilter.text.toString().trim())) {
                MapplsDirectionWidgetSetting.instance.filter = mBinding.etFilter.getText().toString().trim()
            } else {
                MapplsDirectionWidgetSetting.instance.filter = null
            }
            Toast.makeText(this@DirectionWidgetSettingActivity, "Filter save successfully", Toast.LENGTH_SHORT).show()
        }
        mBinding.cbEnableHistory.setOnCheckedChangeListener { buttonView, isChecked ->
            MapplsDirectionWidgetSetting.instance.isEnableHistory = isChecked
            if (isChecked) {
                mBinding.historyCountLayout.visibility = View.VISIBLE
            } else {
                mBinding.historyCountLayout.visibility = View.GONE
            }
        }
        mBinding.btnPodClear.setOnClickListener { v ->
            mBinding.rgPod.clearCheck()
            MapplsDirectionWidgetSetting.instance.pod = null
        }
        mBinding.rgResources.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_route ->MapplsDirectionWidgetSetting.instance.resource = DirectionsCriteria.RESOURCE_ROUTE
                R.id.rb_route_eta -> MapplsDirectionWidgetSetting.instance.resource = DirectionsCriteria.RESOURCE_ROUTE_ETA
                R.id.rb_route_traffic -> MapplsDirectionWidgetSetting.instance.resource = DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC
            }
        }
        mBinding.rgProfiles.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_driving -> MapplsDirectionWidgetSetting.instance.profile = DirectionsCriteria.PROFILE_DRIVING
                R.id.rb_walking -> MapplsDirectionWidgetSetting.instance.profile = DirectionsCriteria.PROFILE_WALKING
                R.id.rb_biking ->MapplsDirectionWidgetSetting.instance.profile = DirectionsCriteria.PROFILE_BIKING
                R.id.rb_trucking ->MapplsDirectionWidgetSetting.instance.profile = DirectionsCriteria.PROFILE_TRUCKING
            }
        }
        mBinding.rgOverviews.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_full -> MapplsDirectionWidgetSetting.instance.overview = DirectionsCriteria.OVERVIEW_FULL
                R.id.rb_none -> MapplsDirectionWidgetSetting.instance.overview = DirectionsCriteria.OVERVIEW_FALSE
                R.id.rb_simplified -> MapplsDirectionWidgetSetting.instance.overview = DirectionsCriteria.OVERVIEW_SIMPLIFIED
            }
        }
        mBinding.rgPod.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_city -> MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_CITY
                R.id.rb_state -> MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_STATE
                R.id.rb_sub_district ->MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_SUB_DISTRICT
                R.id.rb_district -> MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_DISTRICT
                R.id.rb_sub_locality -> MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_SUB_LOCALITY
                R.id.rb_sub_sub_locality -> MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_SUB_SUB_LOCALITY
                R.id.rb_locality -> MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_LOCALITY
                R.id.rb_village ->MapplsDirectionWidgetSetting.instance.pod = AutoSuggestCriteria.POD_VILLAGE
            }
        }
        mBinding.btnHint.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etHint.getText().toString().trim())) {
                MapplsDirectionWidgetSetting.instance.hint = mBinding.etHint.getText().toString().trim()
                Toast.makeText(this@DirectionWidgetSettingActivity, "Hint save successfully", Toast.LENGTH_SHORT).show()
            } else {
                mBinding.etHint.setText(MapplsDirectionWidgetSetting.instance.hint)
                Toast.makeText(this@DirectionWidgetSettingActivity, "Hint is mandatory", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnZoom.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etZoom.getText().toString().trim())) {
                MapplsDirectionWidgetSetting.instance.zoom = mBinding.etZoom.getText().toString().trim().toDouble()
                Toast.makeText(this@DirectionWidgetSettingActivity, "zoom save successfully", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.backgroundRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_white -> MapplsDirectionWidgetSetting.instance.backgroundColor = R.color.mappls_demo_white
                R.id.rb_black -> MapplsDirectionWidgetSetting.instance.backgroundColor = android.R.color.black
                R.id.rb_red -> MapplsDirectionWidgetSetting.instance.backgroundColor = android.R.color.holo_red_light
                R.id.rb_green -> MapplsDirectionWidgetSetting.instance.backgroundColor = android.R.color.holo_green_dark
                R.id.rb_blue -> MapplsDirectionWidgetSetting.instance.backgroundColor = android.R.color.holo_blue_bright
            }
        }
        mBinding.toolbarRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_white_toolbar -> MapplsDirectionWidgetSetting.instance.toolbarColor = R.color.mappls_demo_white
                R.id.rb_black_toolbar -> MapplsDirectionWidgetSetting.instance.toolbarColor = android.R.color.black
                R.id.rb_red_toolbar -> MapplsDirectionWidgetSetting.instance.toolbarColor = android.R.color.holo_red_light
                R.id.rb_green_toolbar -> MapplsDirectionWidgetSetting.instance.toolbarColor = android.R.color.holo_green_dark
                R.id.rb_blue_toolbar -> MapplsDirectionWidgetSetting.instance.toolbarColor = android.R.color.holo_blue_bright
            }
        }
        mBinding.cbPoiSearch.setOnCheckedChangeListener { buttonView, isChecked ->
            MapplsDirectionWidgetSetting.instance.isShowPOISearch = isChecked
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        mBinding.tokenizeAddressBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsDirectionWidgetSetting.instance.isTokenizeAddress = isChecked }
        mBinding.showStartNavigationBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsDirectionWidgetSetting.instance.isShowStartNavigation = isChecked }
        mBinding.showStepsBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsDirectionWidgetSetting.instance.isSteps = isChecked }
        mBinding.showAlternativeBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsDirectionWidgetSetting.instance.isShowAlternative = isChecked }
        mBinding.cbDefault.isChecked = MapplsDirectionWidgetSetting.instance.isDefault
        mBinding.disableView.visibility = if (MapplsDirectionWidgetSetting.instance.isDefault) View.VISIBLE else View.GONE
        if (MapplsDirectionWidgetSetting.instance.historyCount != null) {
            mBinding.etHistoryCount.setText(MapplsDirectionWidgetSetting.instance.historyCount.toString())
        }
        if (MapplsDirectionWidgetSetting.instance.signatureVertical == PlaceOptions.GRAVITY_TOP) {
            mBinding.rgVertical.check(mBinding.rbTop.id)
        } else {
            mBinding.rgVertical.check(mBinding.rbBottom.id)
        }
        if (MapplsDirectionWidgetSetting.instance.signatureHorizontal == PlaceOptions.GRAVITY_LEFT) {
            mBinding.rgHorizontal.check(mBinding.rbLeft.id)
        } else if (MapplsDirectionWidgetSetting.instance.signatureHorizontal == PlaceOptions.GRAVITY_CENTER) {
            mBinding.rgHorizontal.check(mBinding.rbCenter.id)
        } else {
            mBinding.rgHorizontal.check(mBinding.rbRight.id)
        }
        if (MapplsDirectionWidgetSetting.instance.logoSize == PlaceOptions.SIZE_SMALL) {
            mBinding.rgLogoSize.check(R.id.rb_small)
        } else if (MapplsDirectionWidgetSetting.instance.logoSize == PlaceOptions.SIZE_MEDIUM) {
            mBinding.rgLogoSize.check(R.id.rb_medium)
        } else {
            mBinding.rgLogoSize.check(R.id.rb_large)
        }
        if (MapplsDirectionWidgetSetting.instance.location != null) {
            mBinding.etLatitude.setText(MapplsDirectionWidgetSetting.instance.location?.latitude().toString())
            mBinding.etLongitude.setText(MapplsDirectionWidgetSetting.instance.location?.longitude().toString())
        }
        if (MapplsDirectionWidgetSetting.instance.filter != null) {
            mBinding.etFilter.setText(MapplsDirectionWidgetSetting.instance.filter)
        }
        mBinding.cbEnableHistory.isChecked = MapplsDirectionWidgetSetting.instance.isEnableHistory
        if (!MapplsDirectionWidgetSetting.instance.isEnableHistory) {
            mBinding.historyCountLayout.visibility = View.GONE
        } else {
            mBinding.historyCountLayout.visibility = View.VISIBLE
        }
        if (MapplsDirectionWidgetSetting.instance.resource.equals(DirectionsCriteria.RESOURCE_ROUTE, ignoreCase = true)) {
            mBinding.rgResources.check(mBinding.rbRoute.id)
        } else if (MapplsDirectionWidgetSetting.instance.resource.equals(DirectionsCriteria.RESOURCE_ROUTE_ETA, ignoreCase = true)) {
            mBinding.rgResources.check(mBinding.rbRouteEta.id)
        } else if (MapplsDirectionWidgetSetting.instance.resource.equals(DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC, ignoreCase = true)) {
            mBinding.rgResources.check(mBinding.rbRouteTraffic.id)
        }
        if (MapplsDirectionWidgetSetting.instance.profile.equals(DirectionsCriteria.PROFILE_DRIVING, ignoreCase = true)) {
            mBinding.rgProfiles.check(mBinding.rbDriving.id)
        } else if (MapplsDirectionWidgetSetting.instance.profile.equals(DirectionsCriteria.PROFILE_WALKING, ignoreCase = true)) {
            mBinding.rgProfiles.check(mBinding.rbWalking.id)
        } else if (MapplsDirectionWidgetSetting.instance.profile.equals(DirectionsCriteria.PROFILE_BIKING, ignoreCase = true)) {
            mBinding.rgProfiles.check(mBinding.rbBiking.id)
        } else if (MapplsDirectionWidgetSetting.instance.profile.equals(DirectionsCriteria.PROFILE_TRUCKING, ignoreCase = true)) {
            mBinding.rgProfiles.check(mBinding.rbTrucking.id)
        }
        if (MapplsDirectionWidgetSetting.instance.overview.equals(DirectionsCriteria.OVERVIEW_FULL, ignoreCase = true)) {
            mBinding.rgOverviews.check(mBinding.rbFull.id)
        } else if (MapplsDirectionWidgetSetting.instance.overview.equals(DirectionsCriteria.OVERVIEW_FALSE, ignoreCase = true)) {
            mBinding.rgOverviews.check(mBinding.rbNone.id)
        } else if (MapplsDirectionWidgetSetting.instance.overview.equals(DirectionsCriteria.OVERVIEW_SIMPLIFIED, ignoreCase = true)) {
            mBinding.rgOverviews.check(mBinding.rbSimplified.id)
        }
        if (MapplsDirectionWidgetSetting.instance.pod != null) {
            if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_CITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbCity.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_DISTRICT, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbDistrict.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_LOCALITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbLocality.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_STATE, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbState.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_SUB_DISTRICT, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbSubDistrict.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_SUB_LOCALITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbSubLocality.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbSubSubLocality.getId())
            } else if (MapplsDirectionWidgetSetting.instance.pod.equals(AutoSuggestCriteria.POD_VILLAGE, ignoreCase = true)) {
                mBinding.rgPod.check(mBinding.rbVillage.getId())
            }
        } else {
            mBinding.rgPod.clearCheck()
        }
        mBinding.etHint.setText(MapplsDirectionWidgetSetting.instance.hint)
        if (MapplsDirectionWidgetSetting.instance.zoom != null) {
            mBinding.etZoom.setText(MapplsDirectionWidgetSetting.instance.zoom.toString())
        }
        // mBinding.cbEnableTextSearch.setChecked(MapplsPlacePickerSetting.getInstance().isEnableTextSearch());
        if (MapplsDirectionWidgetSetting.instance.backgroundColor == R.color.mappls_demo_white) {
            mBinding.backgroundRG.check(mBinding.rbWhite.getId())
        } else if (MapplsDirectionWidgetSetting.instance.backgroundColor == android.R.color.black) {
            mBinding.backgroundRG.check(mBinding.rbBlack.getId())
        } else if (MapplsDirectionWidgetSetting.instance.backgroundColor == android.R.color.holo_red_light) {
            mBinding.backgroundRG.check(mBinding.rbRed.getId())
        } else if (MapplsDirectionWidgetSetting.instance.backgroundColor == android.R.color.holo_green_dark) {
            mBinding.backgroundRG.check(mBinding.rbGreen.getId())
        } else if (MapplsDirectionWidgetSetting.instance.backgroundColor == android.R.color.holo_blue_bright) {
            mBinding.backgroundRG.check(mBinding.rbBlue.getId())
        }
        if (MapplsDirectionWidgetSetting.instance.toolbarColor == R.color.mappls_demo_white) {
            mBinding.toolbarRG.check(mBinding.rbWhiteToolbar.getId())
        } else if (MapplsDirectionWidgetSetting.instance.toolbarColor == android.R.color.black) {
            mBinding.toolbarRG.check(mBinding.rbBlackToolbar.getId())
        } else if (MapplsDirectionWidgetSetting.instance.toolbarColor == android.R.color.holo_red_light) {
            mBinding.toolbarRG.check(mBinding.rbRedToolbar.getId())
        } else if (MapplsDirectionWidgetSetting.instance.toolbarColor == android.R.color.holo_green_dark) {
            mBinding.toolbarRG.check(mBinding.rbGreenToolbar.getId())
        } else if (MapplsDirectionWidgetSetting.instance.toolbarColor == android.R.color.holo_blue_bright) {
            mBinding.toolbarRG.check(mBinding.rbBlueToolbar.getId())
        }
        mBinding.cbPoiSearch.isChecked = MapplsDirectionWidgetSetting.instance.isShowPOISearch
    }
}