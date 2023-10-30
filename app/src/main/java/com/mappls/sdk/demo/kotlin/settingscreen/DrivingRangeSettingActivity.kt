package com.mappls.sdk.demo.kotlin.settingscreen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDrivingRangeSettingBinding
import com.mappls.sdk.demo.java.utils.InputFilterMinMax
import com.mappls.sdk.demo.kotlin.settings.MapplsDrivingRangeSetting
import com.mappls.sdk.drivingrange.DrivingRangeCriteria
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.plugins.places.autocomplete.model.MapplsFavoritePlace
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import java.util.*

class DrivingRangeSettingActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDrivingRangeSettingBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_driving_range_setting)
        mBinding.etLatitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-90, 90))
        mBinding.etLongitude.filters = arrayOf<InputFilter>(InputFilterMinMax(-180, 180))
        mBinding.etContourValue.filters = arrayOf(InputFilterMinMax(1, 500))
        mBinding.etDenoise.filters = arrayOf(InputFilterMinMax(0, 1))
        mBinding.datePicker.minDate = System.currentTimeMillis()
        initialiseView()
        initCallback()
    }

    private fun initCallback() {
        mBinding.cbCurrentLocation.setOnCheckedChangeListener { _, isChecked ->
            MapplsDrivingRangeSetting.instance.isUsingCurrentLocation = isChecked
        }
        mBinding.btnSaveLocation.setOnClickListener {
            if (!TextUtils.isEmpty(
                    mBinding.etLatitude.text.toString().trim()
                ) && !TextUtils.isEmpty(mBinding.etLongitude.text.toString().trim())
            ) {
                MapplsDrivingRangeSetting.instance.location = Point.fromLngLat(
                    mBinding.etLongitude.text.toString().trim().toDouble(),
                    mBinding.etLatitude.text.toString().trim().toDouble()
                )
            }
        }
        mBinding.btnSearch.setOnClickListener {
            val placeAutocompleteFragment: PlaceAutocompleteFragment =
                PlaceAutocompleteFragment.newInstance(
                    PlaceOptions.builder().backgroundColor(Color.WHITE)
                        .build(PlaceOptions.MODE_CARDS)
                )
            placeAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(eLocation: ELocation) {
                    if (eLocation.latitude != null && eLocation.longitude != null) {
                        mBinding.etLongitude.setText("${eLocation.longitude}")
                        mBinding.etLatitude.setText("${eLocation.latitude}")
                    }
                    if (!TextUtils.isEmpty(
                            mBinding.etLatitude.text.toString().trim()
                        ) && !TextUtils.isEmpty(mBinding.etLongitude.text.toString().trim())
                    ) {
                        MapplsDrivingRangeSetting.instance.location = Point.fromLngLat(
                            mBinding.etLongitude.text.toString().trim().toDouble(),
                            mBinding.etLatitude.text.toString().trim().toDouble()
                        )
                    }
                    supportFragmentManager.popBackStack(
                        PlaceAutocompleteFragment::class.java.simpleName,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }

                override fun onFavoritePlaceSelected(p0: MapplsFavoritePlace?) {

                }

                override fun onCancel() {
                    supportFragmentManager.popBackStack(
                        PlaceAutocompleteFragment::class.java.simpleName,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }

                override fun requestForCurrentLocation() {
                    Toast.makeText(this@DrivingRangeSettingActivity,
                        "Please provide current location",
                        Toast.LENGTH_SHORT).show()

                }
            })


            supportFragmentManager.beginTransaction().add(
                    R.id.setting_layout, placeAutocompleteFragment,
                    PlaceAutocompleteFragment::class.java.simpleName
                )
                .addToBackStack(PlaceAutocompleteFragment::class.java.simpleName)
                .commit()
        }
        mBinding.rgRangeType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_distance) {
                MapplsDrivingRangeSetting.instance.rangeType =
                    DrivingRangeCriteria.RANGE_TYPE_DISTANCE
            } else {
                MapplsDrivingRangeSetting.instance.rangeType =
                    DrivingRangeCriteria.RANGE_TYPE_TIME
            }
        }
        mBinding.btnSaveContourValue.setOnClickListener {
            if (!TextUtils.isEmpty(
                    mBinding.etContourValue.text.toString().trim()
                )
            ) {
                MapplsDrivingRangeSetting.instance.contourValue =
                    mBinding.etContourValue.text.toString().toInt()
            }
        }
        mBinding.contourColorRG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_contour_red -> {
                    MapplsDrivingRangeSetting.instance.contourColor = "ff0000"
                }
                R.id.rb_contour_green -> {
                    MapplsDrivingRangeSetting.instance.contourColor = "00ff00"
                }
                else -> {
                    MapplsDrivingRangeSetting.instance.contourColor = "0000ff"
                }
            }
        }
        mBinding.btnDrivingProfile.setOnClickListener {
            if (!TextUtils.isEmpty(
                    mBinding.etDrivingProfile.text.toString().trim()
                )
            ) {
                MapplsDrivingRangeSetting.instance.drivingProfile =
                    mBinding.etDrivingProfile.text.toString()
            }
        }
        mBinding.showLocationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            MapplsDrivingRangeSetting.instance.isShowLocations = isChecked
        }
        mBinding.showPolygonSwitch.setOnCheckedChangeListener { _, isChecked ->
            MapplsDrivingRangeSetting.instance.isForPolygon = isChecked
        }
        mBinding.btnDenoise.setOnClickListener {
            if (!TextUtils.isEmpty(
                    mBinding.etDenoise.text.toString().trim()
                )
            ) {
                MapplsDrivingRangeSetting.instance.denoise =
                    mBinding.etDenoise.text.toString().toFloat()
            }
        }
        mBinding.btnGeneralize.setOnClickListener {
            if (!TextUtils.isEmpty(
                    mBinding.etGeneralize.text.toString().trim()
                )
            ) {
                MapplsDrivingRangeSetting.instance.generalize =
                    mBinding.etGeneralize.text.toString().toFloat()
            }
        }
        mBinding.rgSpeedType.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.rb_optimal) {
                MapplsDrivingRangeSetting.instance.speedType = MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL
            } else {
                MapplsDrivingRangeSetting.instance.speedType = MapplsDrivingRangeSetting.SPEED_TYPE_PREDECTIVE
            }

            mBinding.layoutPredective.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL) View.GONE else View.VISIBLE)
            mBinding.datePicker.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE)
            mBinding.timePicker.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE)
        }
        mBinding.rgPredectiveType.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.rb_current) {
                MapplsDrivingRangeSetting.instance.predectiveType = MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT
            } else {
                MapplsDrivingRangeSetting.instance.predectiveType = MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CUSTOM
            }

            mBinding.layoutPredective.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL) View.GONE else View.VISIBLE)
            mBinding.datePicker.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE)
            mBinding.timePicker.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE)
        }

        mBinding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(mBinding.datePicker.year, mBinding.datePicker.month, mBinding.datePicker.dayOfMonth, hourOfDay, minute)
            MapplsDrivingRangeSetting.instance.time = calendar.timeInMillis
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initialiseView() {
        mBinding.cbCurrentLocation.isChecked =
            MapplsDrivingRangeSetting.instance.isUsingCurrentLocation
        mBinding.etLatitude.setText("${MapplsDrivingRangeSetting.instance.location.latitude()}")
        mBinding.etLongitude.setText("${MapplsDrivingRangeSetting.instance.location.longitude()}")
        mBinding.rbDistance.isChecked =
            MapplsDrivingRangeSetting.instance.rangeType.equals(DrivingRangeCriteria.RANGE_TYPE_DISTANCE)
        mBinding.rbTime.isChecked =
            MapplsDrivingRangeSetting.instance.rangeType.equals(DrivingRangeCriteria.RANGE_TYPE_TIME)
        mBinding.etContourValue.setText("${MapplsDrivingRangeSetting.instance.contourValue}")
        when (MapplsDrivingRangeSetting.instance.contourColor) {
            "00ff00" -> {
                mBinding.rbContourGreen.isChecked = true
            }
            "0000ff" -> {
                mBinding.rbContourBlue.isChecked = true
            }
            else -> {
                mBinding.rbContourRed.isChecked = true
            }
        }
        mBinding.etDrivingProfile.setText(MapplsDrivingRangeSetting.instance.drivingProfile)
        mBinding.showLocationsSwitch.isChecked = MapplsDrivingRangeSetting.instance.isShowLocations
        mBinding.showPolygonSwitch.isChecked = MapplsDrivingRangeSetting.instance.isForPolygon
        mBinding.etDenoise.setText("${MapplsDrivingRangeSetting.instance.denoise}")
        mBinding.etGeneralize.setText("${MapplsDrivingRangeSetting.instance.generalize}")
        mBinding.rgSpeedType.check(if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL) R.id.rb_optimal else R.id.rb_predective)
        mBinding.rgPredectiveType.check(if(MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) R.id.rb_current else R.id.rb_custom)
        mBinding.layoutPredective.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL) View.GONE else View.VISIBLE)
        mBinding.datePicker.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE)
        mBinding.timePicker.visibility = (if(MapplsDrivingRangeSetting.instance.speedType == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.instance.predectiveType == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE)
        val date = Date(MapplsDrivingRangeSetting.instance.time)
        val calender = Calendar.getInstance()
        calender.time = date
        mBinding.datePicker.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, monthOfYear, dayOfMonth, mBinding.timePicker.hour, mBinding.timePicker.minute)
            MapplsDrivingRangeSetting.instance.time = calendar.timeInMillis
        }
        mBinding.timePicker.hour = calender.get(Calendar.HOUR_OF_DAY)
        mBinding.timePicker.minute = calender.get(Calendar.MINUTE)
    }
}