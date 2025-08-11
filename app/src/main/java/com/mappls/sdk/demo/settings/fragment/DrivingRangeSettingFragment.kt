package com.mappls.sdk.demo.settings.fragment



import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.layers.DrivingRangeActivity
import com.mappls.sdk.demo.databinding.FragmentDrivingRangeSettingBinding
import com.mappls.sdk.demo.settings.model.MapplsDrivingRangeApiSetting
import com.mappls.sdk.drivingrange.DrivingRangeCriteria
import com.mappls.sdk.geojson.Point
import java.util.Calendar
import java.util.Date


class DrivingRangeSettingFragment : Fragment() {

    private lateinit var mBinding: FragmentDrivingRangeSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDrivingRangeSettingBinding.inflate(layoutInflater)
       return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.settingsHeader.headerTitle.text = "Driving Range Setting"
        mBinding.settingsHeader.headerBack.setOnClickListener {
            (requireActivity() as DrivingRangeActivity).popBackStack(this)
        }
        setValue()
         mBinding.cbCurrentLocation.setOnCheckedChangeListener { p0, isChecked ->
             MapplsDrivingRangeApiSetting.instance.isUsingCurrentLocation = (isChecked);
         };
        mBinding.btnLocation.setOnClickListener {
            val locationText = mBinding.etLocation.text.toString()

            if(locationText.isNotEmpty()) {
                val coords = locationText.split(",").map { it.trim().toDouble() }
                if (coords.size == 2) {
                    val point = Point.fromLngLat(coords[0], coords[1]) // Note: Point takes longitude first
                    MapplsDrivingRangeApiSetting.instance.location = point
                }
            } else {
                Toast.makeText(requireContext(), "Please enter Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDrivingProfile.setOnClickListener {
            val drivingProfile = mBinding.etDrivingProfile.text.toString()
            if(drivingProfile.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.drivingProfile = drivingProfile
            } else {
                Toast.makeText(requireContext(), "Please enter drivingProfile", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnValue.setOnClickListener {
            val value = mBinding.etValue.text.toString()
            if(value.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.value = value.toInt()
            } else {
                Toast.makeText(requireContext(), "Please enter value", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.btnColor.setOnClickListener {
            val color = mBinding.etColor.text.toString()
            if(color.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.color = color
            } else {
                Toast.makeText(requireContext(), "Please enter color", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.rgRangeType.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDrivingRangeApiSetting.instance.rangeType = when(rg.checkedRadioButtonId) {
                mBinding.rbTime.id -> {
                    DrivingRangeCriteria.RANGE_TYPE_TIME
                }
                mBinding.rbDistance.id -> {
                    DrivingRangeCriteria.RANGE_TYPE_DISTANCE
                }
                else ->
                    DrivingRangeCriteria.RANGE_TYPE_TIME
            }
        }
        mBinding.rgSpeedType.setOnCheckedChangeListener { rg, isChecked ->
            MapplsDrivingRangeApiSetting.instance.speedType = when(rg.checkedRadioButtonId) {
                mBinding.rbOptimal.id -> {
                    MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL
                }
                mBinding.rbPredective.id -> {
                    MapplsDrivingRangeApiSetting.SPEED_TYPE_PREDECTIVE
                }
                else ->
                    MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL
            }
        }
        mBinding.rgPredectiveType.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_current) {
                MapplsDrivingRangeApiSetting.instance.predectiveType = (MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT)
            } else {
                MapplsDrivingRangeApiSetting.instance.predectiveType = (MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CUSTOM)
            }
            mBinding.predectiveTypeLayout.visibility = if (MapplsDrivingRangeApiSetting.instance.speedType ==  MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL) View.GONE else View.VISIBLE
            mBinding.datePicker.visibility = if (MapplsDrivingRangeApiSetting.instance.speedType ==  MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeApiSetting.instance.predectiveType ==MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT) View.GONE else View.VISIBLE

            mBinding.timePicker.visibility = if (MapplsDrivingRangeApiSetting.instance.speedType ==  MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeApiSetting.instance.predectiveType ==MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT
            ) View.GONE else View.VISIBLE
        }
        mBinding.swIsForPolygons.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDrivingRangeApiSetting.instance.isForPolygons = isChecked
        }
        mBinding.swShowLocations.setOnCheckedChangeListener { sw, isChecked ->
            MapplsDrivingRangeApiSetting.instance.showLocations = isChecked
        }

        mBinding.btnLineWidth.setOnClickListener {
            val lineWidth = mBinding.etLineWidth.text.toString()
            if(lineWidth.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.lineWidth = lineWidth.toFloat()
            } else {
                Toast.makeText(requireContext(), "Please enter lineWidth", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnOutlineColor.setOnClickListener {
            val outLineColor = mBinding.etOutlineColor.text.toString()
            if(outLineColor.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.outlineColor = outLineColor
            } else {
                Toast.makeText(requireContext(), "Please enter outLineColor", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.btnGeneralize.setOnClickListener {
            val generalize = mBinding.etGeneralize.text.toString()
            if(generalize.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.generalize = generalize.toFloat()
            } else {
                Toast.makeText(requireContext(), "Please enter Generalize", Toast.LENGTH_SHORT).show()
            }
        }


        mBinding.btnDenoise.setOnClickListener {
            val denoise = mBinding.etDenoise.text.toString()
            if(denoise.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.denoise = denoise.toFloat()
            } else {
                Toast.makeText(requireContext(), "Please enter Denoise", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.btnName.setOnClickListener {
            val name = mBinding.etName.text.toString()
            if(name.isNotEmpty()) {
                MapplsDrivingRangeApiSetting.instance.name = name
            } else {
                Toast.makeText(requireContext(), "Please enter Name", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(
                mBinding.datePicker.year,
                mBinding.datePicker.month,
                mBinding.datePicker.dayOfMonth,
                hourOfDay,
                minute
            )
            MapplsDrivingRangeApiSetting.instance.time = calendar.timeInMillis
        }
    }

    private fun setValue() {
        mBinding.cbCurrentLocation.setChecked(MapplsDrivingRangeApiSetting.instance.isUsingCurrentLocation)
        val location = MapplsDrivingRangeApiSetting.instance.location
        mBinding.etLocation.setText("${location.longitude()},${location.latitude()}")
        mBinding.etDrivingProfile.setText(MapplsDrivingRangeApiSetting.instance.drivingProfile)
        mBinding.etValue.setText(MapplsDrivingRangeApiSetting.instance.value.toString() ?: "")
        mBinding.etColor.setText(MapplsDrivingRangeApiSetting.instance.color)
        when(MapplsDrivingRangeApiSetting.instance.rangeType) {
            DrivingRangeCriteria.RANGE_TYPE_TIME -> {
                mBinding.rgRangeType.check(mBinding.rbTime.id)
            }
            DrivingRangeCriteria.RANGE_TYPE_DISTANCE -> {
                mBinding.rgRangeType.check(mBinding.rbDistance.id)
            }

        }
        mBinding.swIsForPolygons.isChecked = MapplsDrivingRangeApiSetting.instance.isForPolygons
        mBinding.swShowLocations.isChecked = MapplsDrivingRangeApiSetting.instance.showLocations
        mBinding.etLineWidth.text = Editable.Factory.getInstance().newEditable(MapplsDrivingRangeApiSetting.instance.lineWidth.toString() ?: "")
        mBinding.etOutlineColor.text = Editable.Factory.getInstance().newEditable(MapplsDrivingRangeApiSetting.instance.outlineColor?.toString()?:"")
        mBinding.etGeneralize.text = Editable.Factory.getInstance().newEditable(MapplsDrivingRangeApiSetting.instance.generalize?.toString() ?:"")
        mBinding.etDenoise.text = Editable.Factory.getInstance().newEditable(MapplsDrivingRangeApiSetting.instance.denoise?.toString() ?:"")
        mBinding.etName.text =  Editable.Factory.getInstance().newEditable(MapplsDrivingRangeApiSetting.instance.name?.toString() ?:"")
        mBinding.rgSpeedType.check(if (MapplsDrivingRangeApiSetting.instance
                    .speedType == MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL
            ) R.id.rb_optimal else R.id.rb_predective
        )
        mBinding.rgPredectiveType.check(
            if (MapplsDrivingRangeApiSetting.instance
                    .predectiveType == MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT
            ) R.id.rb_current else R.id.rb_custom
        )
        mBinding.predectiveTypeLayout.visibility = if (MapplsDrivingRangeApiSetting.instance.speedType == MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL) View.GONE else View.VISIBLE
        mBinding.datePicker.visibility = if (MapplsDrivingRangeApiSetting.instance
                .speedType == MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeApiSetting.instance
                .predectiveType == MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT
        ) View.GONE else View.VISIBLE
        mBinding.timePicker.setVisibility(
            if (MapplsDrivingRangeApiSetting.instance
                    .speedType == MapplsDrivingRangeApiSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeApiSetting.instance
                    .predectiveType == MapplsDrivingRangeApiSetting.PREDECTIVE_TYPE_CURRENT
            ) View.GONE else View.VISIBLE
        )
        val date = Date(MapplsDrivingRangeApiSetting.instance.time)
        val calender = Calendar.getInstance()
        calender.setTime(date)
        mBinding.datePicker.init(
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(
                    year,
                    monthOfYear,
                    dayOfMonth,
                    mBinding.timePicker.hour,
                    mBinding.timePicker.minute
                )
                MapplsDrivingRangeApiSetting.instance.time = calendar.timeInMillis
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.timePicker.hour = calender.get(Calendar.HOUR_OF_DAY)
            mBinding.timePicker.minute = calender.get(Calendar.MINUTE)
        }

    }




}