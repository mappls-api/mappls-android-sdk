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
import com.mappls.sdk.demo.activity.restapis.PredectiveDistanceActivity
import com.mappls.sdk.demo.activity.restapis.PredictiveDirectionActivity
import com.mappls.sdk.demo.databinding.FragmentDistanceSettingsBinding
import com.mappls.sdk.demo.databinding.FragmentPredectiveDistanceSettingsBinding
import com.mappls.sdk.demo.settings.model.MapplsPredectiveDirectionsApiSettings
import com.mappls.sdk.demo.settings.model.MapplsPredectiveDistanceApiSettings
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class PredectiveDistanceSettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentPredectiveDistanceSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPredectiveDistanceSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.predectiveDistanceSettingsHeader.headerTitle.text = "Predective Distance Setting"
        mBinding.predectiveDistanceSettingsHeader.headerBack.setOnClickListener {
            (requireActivity() as PredectiveDistanceActivity).popBackStack(this)
        }
        setValues()
        mBinding.btnDirectionOriginLocation.setOnClickListener {
            val origin = mBinding.etDirectionOriginLocation.text.toString()
            if(origin.isNotEmpty()) {
                MapplsPredectiveDistanceApiSettings.instance.origin = origin
            } else {
                Toast.makeText(requireContext(), "Please enter Origin Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDirectionDestinationLocation.setOnClickListener {
            val destination = mBinding.etDirectionDestinationLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsPredectiveDistanceApiSettings.instance.destination = destination
            } else {
                Toast.makeText(requireContext(), "Please enter Destination Location", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.btnDirectionWaypointLocation.setOnClickListener {
            val destination = mBinding.etDirectionWaypointLocation.text.toString()
            if(destination.isNotEmpty()) {
                MapplsPredectiveDistanceApiSettings.instance.wayPoints = destination
            } else {
                MapplsPredectiveDirectionsApiSettings.instance.wayPoints = null
            }
        }
        mBinding.rgDateTime.setOnCheckedChangeListener { rg, isChecked ->
            MapplsPredectiveDistanceApiSettings.instance.isCurrentTime = (rg.checkedRadioButtonId == mBinding.rbDateTimeCurrent.id)
            mBinding.layoutSpecifiedTime.visibility = if(rg.checkedRadioButtonId == mBinding.rbDateTimeCurrent.id) View.GONE else View.VISIBLE
        }
        mBinding.rgTimeFor.setOnCheckedChangeListener { rg, isChecked ->
            MapplsPredectiveDistanceApiSettings.instance.locationType = if(rg.checkedRadioButtonId == mBinding.rbTimeForArrival.id)
                DirectionsCriteria.SPECIFIED_ARRIVAL else DirectionsCriteria.SPECIFIED_DEPARTURE
        }
        mBinding.tvPredictiveTime.setOnClickListener {
            // Get current date and time
            val calendar = Calendar.getInstance()
            if(MapplsPredectiveDistanceApiSettings.instance.specifiedTime != null && MapplsPredectiveDistanceApiSettings.instance.specifiedTime!! > System.currentTimeMillis()) {
                calendar.timeInMillis = MapplsPredectiveDistanceApiSettings.instance.specifiedTime!!
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
                            MapplsPredectiveDistanceApiSettings.instance.specifiedTime = selectedTimeInMillis
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
        mBinding.etDirectionOriginLocation.setText(MapplsPredectiveDistanceApiSettings.instance.origin)
        mBinding.etDirectionDestinationLocation.setText(MapplsPredectiveDistanceApiSettings.instance.destination)
        mBinding.etDirectionWaypointLocation.setText(MapplsPredectiveDistanceApiSettings.instance.wayPoints ?: "")
        mBinding.rgDateTime.check(if(MapplsPredectiveDistanceApiSettings.instance.isCurrentTime) mBinding.rbDateTimeCurrent.id else mBinding.rbDateTimeSpecified.id)
        mBinding.layoutSpecifiedTime.visibility = if(MapplsPredectiveDistanceApiSettings.instance.isCurrentTime) View.GONE else View.VISIBLE
        mBinding.rgTimeFor.check(if(MapplsPredectiveDistanceApiSettings.instance.locationType == DirectionsCriteria.SPECIFIED_ARRIVAL) mBinding.rbTimeForArrival.id else mBinding.rbDateTimeDeparture.id)
        if(MapplsPredectiveDistanceApiSettings.instance.specifiedTime != null && MapplsPredectiveDistanceApiSettings.instance.specifiedTime!! > System.currentTimeMillis()) {
            val date = Date(MapplsPredectiveDistanceApiSettings.instance.specifiedTime!!)

            mBinding.tvPredictiveTime.setText(SimpleDateFormat("dd/MM/yyyy HH:mm").format(date))
        }
    }
}