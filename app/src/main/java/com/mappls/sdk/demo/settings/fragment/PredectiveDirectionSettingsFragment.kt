package com.mappls.sdk.demo.settings.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.DirectionsActivity
import com.mappls.sdk.demo.activity.restapis.PredictiveDirectionActivity
import com.mappls.sdk.demo.databinding.FragmentPredectiveDirectionSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsDirectionsApiSettings
import com.mappls.sdk.demo.settings.model.MapplsPredectiveDirectionsApiSettings
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class PredectiveDirectionSettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentPredectiveDirectionSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPredectiveDirectionSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.predectiveDirectionSettingsHeader.headerTitle.text = "Predective Directions Setting"
        mBinding.predectiveDirectionSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as PredictiveDirectionActivity).popBackStack(this)
        }
        setValues()
        mBinding.btnDirectionOriginLocation.setOnClickListener {
            val origin = mBinding.etDirectionOriginLocation.text.toString()
            if(origin.isNotEmpty()) {
                MapplsPredectiveDirectionsApiSettings.instance.origin = origin
            } else {
                Toast.makeText(requireContext(), "Please enter Origin Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDirectionDestinationLocation.setOnClickListener {
            val destination = mBinding.etDirectionDestinationLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsPredectiveDirectionsApiSettings.instance.destination = destination
            } else {
                Toast.makeText(requireContext(), "Please enter Destination Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDirectionWaypointLocation.setOnClickListener {
            val destination = mBinding.etDirectionWaypointLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsPredectiveDirectionsApiSettings.instance.wayPoints = destination
            } else {
                MapplsPredectiveDirectionsApiSettings.instance.wayPoints = null
            }
        }
        mBinding.rgOverview.setOnCheckedChangeListener { rg, isChecked ->
            MapplsPredectiveDirectionsApiSettings.instance.overview = when(rg.checkedRadioButtonId) {
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
        mBinding.swDirectionAlternative.setOnCheckedChangeListener { sw, isChecked ->
            MapplsPredectiveDirectionsApiSettings.instance.isAlternatives = isChecked
        }
        mBinding.rgGeometry.setOnCheckedChangeListener { rg, isChecked ->
            MapplsPredectiveDirectionsApiSettings.instance.geometries = when(rg.checkedRadioButtonId) {
                mBinding.rbGeometryPolylineFive.id -> {
                    DirectionsCriteria.GEOMETRY_POLYLINE
                }
                mBinding.rbGeometryPolylineSix.id -> {
                    DirectionsCriteria.GEOMETRY_POLYLINE6
                }
                mBinding.rbGeometryCoordiates.id -> {
                    DirectionsCriteria.GEOMETRY_COORDINATES
                }
                else ->
                    DirectionsCriteria.GEOMETRY_POLYLINE6
            }
        }
        mBinding.swDirectionSteps.setOnCheckedChangeListener { sw, isChecked ->
            MapplsPredectiveDirectionsApiSettings.instance.steps = isChecked
        }
        mBinding.rgDateTime.setOnCheckedChangeListener { rg, isChecked ->
            MapplsPredectiveDirectionsApiSettings.instance.isCurrentTime = (rg.checkedRadioButtonId == mBinding.rbDateTimeCurrent.id)
            mBinding.layoutSpecifiedTime.visibility = if(rg.checkedRadioButtonId == mBinding.rbDateTimeCurrent.id) View.GONE else View.VISIBLE
        }
        mBinding.rgTimeFor.setOnCheckedChangeListener { rg, isChecked ->
            MapplsPredectiveDirectionsApiSettings.instance.locationType = if(rg.checkedRadioButtonId == mBinding.rbTimeForArrival.id)
                DirectionsCriteria.SPECIFIED_ARRIVAL else DirectionsCriteria.SPECIFIED_DEPARTURE
        }
        mBinding.tvPredictiveTime.setOnClickListener {
            // Get current date and time
            val calendar = Calendar.getInstance()
            if(MapplsPredectiveDirectionsApiSettings.instance.specifiedTime != null && MapplsPredectiveDirectionsApiSettings.instance.specifiedTime!! > System.currentTimeMillis()) {
                calendar.timeInMillis = MapplsPredectiveDirectionsApiSettings.instance.specifiedTime!!
            }
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, selectedYear, selectedMonth, selectedDay ->
//                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear" // Month is 0-indexed

                    // After selecting a date, show the TimePickerDialog
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, selectedHour, selectedMinute ->
                            val selectedCalendar = Calendar.getInstance()
                            selectedCalendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, 0)

                            // Get the selected time in milliseconds
                            val selectedTimeInMillis = selectedCalendar.timeInMillis
                            MapplsPredectiveDirectionsApiSettings.instance.specifiedTime = selectedTimeInMillis
                            val date = Date(selectedTimeInMillis)

                            mBinding.tvPredictiveTime.setText(SimpleDateFormat("dd/MM/yyyy HH:mm").format(date))
                        },
                        hour,
                        minute,
                        true // 24-hour format
                    )
                    timePickerDialog.show()
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun setValues() {
        mBinding.etDirectionOriginLocation.setText(MapplsPredectiveDirectionsApiSettings.instance.origin)
        mBinding.etDirectionDestinationLocation.setText(MapplsPredectiveDirectionsApiSettings.instance.destination)
        mBinding.etDirectionWaypointLocation.setText(MapplsPredectiveDirectionsApiSettings.instance.wayPoints ?: "")
        when(MapplsPredectiveDirectionsApiSettings.instance.overview) {
            DirectionsCriteria.OVERVIEW_FULL ->
                mBinding.rgOverview.check(mBinding.rbOverviewFull.id)
            DirectionsCriteria.OVERVIEW_SIMPLIFIED ->
                mBinding.rgOverview.check(mBinding.rbOverviewSimplified.id)
            DirectionsCriteria.OVERVIEW_FALSE ->
                mBinding.rgOverview.check(mBinding.rbOverviewFalse.id)
        }
        mBinding.swDirectionAlternative.isChecked = MapplsPredectiveDirectionsApiSettings.instance.isAlternatives
        when(MapplsPredectiveDirectionsApiSettings.instance.geometries) {
            DirectionsCriteria.GEOMETRY_POLYLINE -> {
                mBinding.rgGeometry.check(mBinding.rbGeometryPolylineFive.id)
            }
            DirectionsCriteria.GEOMETRY_POLYLINE6 -> {
                mBinding.rgGeometry.check(mBinding.rbGeometryPolylineSix.id)
            }
            DirectionsCriteria.GEOMETRY_COORDINATES -> {
                mBinding.rgGeometry.check(mBinding.rbGeometryCoordiates.id)
            }
        }
        mBinding.swDirectionSteps.isChecked = MapplsPredectiveDirectionsApiSettings.instance.steps
        mBinding.rgDateTime.check(if(MapplsPredectiveDirectionsApiSettings.instance.isCurrentTime) mBinding.rbDateTimeCurrent.id else mBinding.rbDateTimeSpecified.id)
        mBinding.layoutSpecifiedTime.visibility = if(MapplsPredectiveDirectionsApiSettings.instance.isCurrentTime) View.GONE else View.VISIBLE
        mBinding.rgTimeFor.check(if(MapplsPredectiveDirectionsApiSettings.instance.locationType == DirectionsCriteria.SPECIFIED_ARRIVAL) mBinding.rbTimeForArrival.id else mBinding.rbDateTimeDeparture.id)
        if(MapplsPredectiveDirectionsApiSettings.instance.specifiedTime != null && MapplsPredectiveDirectionsApiSettings.instance.specifiedTime!! > System.currentTimeMillis()) {
            val date = Date(MapplsPredectiveDirectionsApiSettings.instance.specifiedTime!!)

            mBinding.tvPredictiveTime.setText(SimpleDateFormat("dd/MM/yyyy HH:mm").format(date))
        }
    }

}