package com.mappls.sdk.demo.kotlin.settingscreen

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityGeofenceWidgetSettingsBinding
import com.mappls.sdk.demo.kotlin.settings.MapplsGeofenceSetting

class GeofenceWidgetSettingsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityGeofenceWidgetSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_geofence_widget_settings)
        initSettings()
        initCallbacks()
    }

    private fun initSettings() {

        mBinding.cbDefault.isChecked = MapplsGeofenceSetting.instance.isDefault
        mBinding.disableView.visibility = if (MapplsGeofenceSetting.instance.isDefault) View.VISIBLE else View.GONE
        mBinding.etCircleOutlineWidth.setText("${MapplsGeofenceSetting.instance.circleOutlineWidth}")
        mBinding.circleFillColorTv.text = String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.circleFillColor)
        mBinding.circleFillOutlineColorTv.text = String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.circleFillOutlineColor)
        mBinding.draggingLineColorTv.text = String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.draggingLineColor)
        mBinding.etMaxRadius.setText(MapplsGeofenceSetting.instance.maxRadius.toString())
        mBinding.etMinRadius.setText(MapplsGeofenceSetting.instance.minRadius.toString())
        mBinding.showSeekbarBtn.isChecked = MapplsGeofenceSetting.instance.showSeekBar
        mBinding.seekbarPrimaryColorTv.text = String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.seekbarPrimaryColor)
        mBinding.seekbarSecondaryColorTv.text = String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.seekbarPrimaryColor)
        mBinding.polygonDrawingLineColorTv.setText(String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.polygonDrawingLineColor))
        mBinding.polygonFillColorTv.setText(String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.polygonFillColor))
        mBinding.polygonFillOutlineColorTv.setText(String.format("#%06X", 0xFFFFFF and MapplsGeofenceSetting.instance.polygonFillOutlineColor))
        mBinding.etPolygonOutlineWidth.setText(MapplsGeofenceSetting.instance.polygonOutlineWidth.toString() + "")
        mBinding.simplifyWhenIntersectingPolygonDetectedBtn.setChecked(MapplsGeofenceSetting.instance.isSimplifyWhenIntersectingPolygonDetected)
        mBinding.isPolygonBtn.setChecked(MapplsGeofenceSetting.instance.isPolygon)
        mBinding.etSeekbarCornerRadius.setText(MapplsGeofenceSetting.instance.seekbarCornerRadius.toString() + "")
    }

    private fun initCallbacks() {
        mBinding.cbDefault.setOnCheckedChangeListener { buttonView, isChecked ->
            MapplsGeofenceSetting.instance.isDefault = isChecked
            if (isChecked) {
                mBinding.disableView.setVisibility(View.VISIBLE)
            } else {
                mBinding.disableView.setVisibility(View.GONE)
            }
        }
        mBinding.showSeekbarBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsGeofenceSetting.instance.showSeekBar = isChecked }
        mBinding.btnSaveCircleOutlineWidth.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etCircleOutlineWidth.getText().toString().trim())) {
                MapplsGeofenceSetting.instance.circleOutlineWidth = mBinding.etCircleOutlineWidth.getText().toString().trim().toFloat()
            }
        }

        mBinding.btnSeekbarCornerRadius.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etSeekbarCornerRadius.text.toString().trim())) {
               MapplsGeofenceSetting.instance.seekbarCornerRadius = mBinding.etSeekbarCornerRadius.text.toString().trim().toFloat()
            }
        }
        mBinding.circleFillColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.circleFillColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok") { dialog, selectedColor, allColors ->
                        MapplsGeofenceSetting.instance.circleFillColor = selectedColor
                        mBinding.circleFillColorTv.setText(String.format("#%06X", 0xFFFFFF and selectedColor))
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog, which -> }
                    .build()
                    .show()
        }
        mBinding.circleFillOutlineColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.circleFillOutlineColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.circleFillOutlineColor = selectedColor
                        mBinding.circleFillOutlineColorTv.setText(String.format("#%06X", 0xFFFFFF and selectedColor))
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }
        mBinding.draggingLineColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.draggingLineColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.draggingLineColor = selectedColor
                        mBinding.draggingLineColorTv.setText(String.format("#%06X", 0xFFFFFF and selectedColor))
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }
        mBinding.btnSaveMaxRadius.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etMaxRadius.getText().toString().trim())) {
                MapplsGeofenceSetting.instance.maxRadius = mBinding.etMaxRadius.getText().toString().trim().toInt()
            }
        }
        mBinding.btnSaveMinRadius.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etMinRadius.getText().toString().trim())) {
                MapplsGeofenceSetting.instance.minRadius = mBinding.etMinRadius.getText().toString().trim().toInt()
            }
        }
        mBinding.polygonDrawingLineColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.polygonDrawingLineColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.polygonDrawingLineColor = selectedColor
                        mBinding.polygonDrawingLineColorTv.setText(String.format("#%06X", 0xFFFFFF and selectedColor))
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }
        mBinding.polygonFillColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.polygonFillColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.polygonFillColor = selectedColor
                        mBinding.polygonFillColorTv.setText(String.format("#%06X", 0xFFFFFF and selectedColor))
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }
        mBinding.polygonFillOutlineColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.polygonFillOutlineColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.polygonFillOutlineColor = selectedColor
                        mBinding.polygonFillOutlineColorTv.setText(String.format("#%06X", 0xFFFFFF and selectedColor))
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }

        mBinding.seekbarPrimaryColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.seekbarPrimaryColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.seekbarPrimaryColor = selectedColor
                        mBinding.seekbarPrimaryColorTv.text = String.format("#%06X", 0xFFFFFF and selectedColor)
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }

        mBinding.seekbarSecondaryColorLayout.setOnClickListener { v ->
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.instance.seekbarSecondaryColor)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok") { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                        MapplsGeofenceSetting.instance.seekbarSecondaryColor = selectedColor
                        mBinding.seekbarSecondaryColorTv.text = String.format("#%06X", 0xFFFFFF and selectedColor)
                        dialog.dismiss()
                    }
                    .setNegativeButton("cancel") { dialog: DialogInterface?, which: Int -> }
                    .build()
                    .show()
        }
        mBinding.btnPolygonOutlineWidth.setOnClickListener { v ->
            if (!TextUtils.isEmpty(mBinding.etPolygonOutlineWidth.getText().toString().trim())) {
                MapplsGeofenceSetting.instance.polygonOutlineWidth = mBinding.etPolygonOutlineWidth.getText().toString().trim().toFloat()
            }
        }
        mBinding.simplifyWhenIntersectingPolygonDetectedBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsGeofenceSetting.instance.isSimplifyWhenIntersectingPolygonDetected = isChecked }
        mBinding.isPolygonBtn.setOnCheckedChangeListener { buttonView, isChecked -> MapplsGeofenceSetting.instance.isPolygon = isChecked }
    }
}