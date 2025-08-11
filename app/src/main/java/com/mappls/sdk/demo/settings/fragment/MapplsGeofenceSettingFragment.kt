package com.mappls.sdk.demo.settings.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mappls.sdk.demo.activity.widgets.GeoFenceActivity
import com.mappls.sdk.demo.databinding.FragmentMapplsGeofenceSettingBinding
import com.mappls.sdk.demo.settings.model.AutoCompleteWidgetSetting
import com.mappls.sdk.demo.settings.model.MapplsGeofenceSetting


class MapplsGeofenceSettingFragment : Fragment() {

    private lateinit var mBinding:FragmentMapplsGeofenceSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMapplsGeofenceSettingBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.settingsHeader.headerTitle.text = "GeoFence Setting"
        mBinding.settingsHeader.headerBack.setOnClickListener {
            (requireActivity() as GeoFenceActivity).popBackStack(this)
        }
        setValues()

        mBinding.btnCircleOutlineWidth.setOnClickListener {
            if((!TextUtils.isEmpty(mBinding.etCircleOutlineWidth.text.toString().trim()))) {
                MapplsGeofenceSetting.instance.circleOutlineWidth = mBinding.etCircleOutlineWidth.text.toString().trim().toFloat()
            }
        }
        mBinding.etCircleFillColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.circleFillColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsGeofenceSetting.instance.circleFillColor = selectedColor
                    mBinding.etCircleFillColor.setText(
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
        mBinding.etCircleFillOutlineColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.circleFillOutlineColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsGeofenceSetting.instance.circleFillOutlineColor = selectedColor
                    mBinding.etCircleFillOutlineColor.setText(
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
        mBinding.etDraggingLineColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.draggingLineColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok"
                ) { dialog, selectedColor, allColors -> // changeBackgroundColor(selectedColor);
                    MapplsGeofenceSetting.instance.draggingLineColor = selectedColor
                    mBinding.etDraggingLineColor.setText(
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
        mBinding.btnMaxRadius.setOnClickListener { v ->
            if ((!TextUtils.isEmpty(
                    mBinding.etMaxRadius.text.toString().trim()
                ))
            ) {
                MapplsGeofenceSetting.instance.maxRadius = (mBinding.etMaxRadius.text.toString().trim().toInt())
            }
        }

        mBinding.btnMinRadius.setOnClickListener { v ->
            if ((!TextUtils.isEmpty(
                    mBinding.etMinRadius.text.toString().trim()
                ))
            ) {
                MapplsGeofenceSetting.instance
                    .minRadius = (mBinding.etMinRadius.text.toString().trim().toInt())
            }
        }
        mBinding.etPolygonDrawingLineColor.setOnClickListener { v ->
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.polygonDrawingLineColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                    MapplsGeofenceSetting.instance.polygonDrawingLineColor = selectedColor
                    mBinding.etPolygonDrawingLineColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "cancel"
                ) { dialog: DialogInterface?, which: Int -> }
                .build()
                .show()
        }
        mBinding.etPolygonFillColor.setOnClickListener { v ->
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.polygonFillColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                    MapplsGeofenceSetting.instance.polygonFillColor = selectedColor
                    mBinding.etPolygonFillColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "cancel"
                ) { dialog: DialogInterface?, which: Int -> }
                .build()
                .show()
        }
        mBinding.etPolygonFillOutlineColor.setOnClickListener { v ->
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.polygonFillOutlineColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                    MapplsGeofenceSetting.instance.polygonFillOutlineColor = selectedColor
                    mBinding.etPolygonFillOutlineColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "cancel"
                ) { dialog: DialogInterface?, which: Int -> }
                .build()
                .show()
        }
        mBinding.btnPolygonOutlineWidth.setOnClickListener { v ->
            if((!TextUtils.isEmpty(mBinding.etPolygonOutlineWidth.text.toString().trim()))) {
                MapplsGeofenceSetting.instance.polygonOutlineWidth = mBinding.etPolygonOutlineWidth.text.toString().trim().toFloat()
            }
        }
        mBinding.swShowSeekBar.setOnCheckedChangeListener { _, isCheck ->
            MapplsGeofenceSetting.instance.showSeekBar = isCheck
        }
        mBinding.etSeekbarPrimaryColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.seekbarPrimaryColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                    MapplsGeofenceSetting.instance.seekbarPrimaryColor = selectedColor
                    mBinding.etSeekbarPrimaryColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "cancel"
                ) { dialog: DialogInterface?, which: Int -> }
                .build()
                .show()
        }
        mBinding.etSeekbarSecondaryColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(MapplsGeofenceSetting.instance.seekbarSecondaryColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { dialog: DialogInterface, selectedColor: Int, allColors: Array<Int?>? ->
                    MapplsGeofenceSetting.instance.seekbarSecondaryColor = selectedColor
                    mBinding.etSeekbarSecondaryColor.setText(
                        String.format(
                            "#%06X",
                            (0xFFFFFF and selectedColor)
                        )
                    )
                    dialog.dismiss()
                }
                .setNegativeButton(
                    "cancel"
                ) { dialog: DialogInterface?, which: Int -> }
                .build()
                .show()
        }
        mBinding.btnSeekbarCornerRadius.setOnClickListener {
            if((!TextUtils.isEmpty(mBinding.etSeekbarCornerRadius.text.toString().trim()))) {
                MapplsGeofenceSetting.instance.seekbarCornerRadius = mBinding.etSeekbarCornerRadius.getText().toString().trim().toFloat()
            }

        }

    }

    fun setValues(){
        mBinding.etCircleOutlineWidth.setText(MapplsGeofenceSetting.instance.circleOutlineWidth.toString() ?: "")
        mBinding.etCircleFillColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.circleFillColor))
        mBinding.etCircleFillOutlineColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.circleFillOutlineColor))
        mBinding.etDraggingLineColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.draggingLineColor))
        mBinding.etMaxRadius.setText(MapplsGeofenceSetting.instance.maxRadius.toString() ?: "")
        mBinding.etMinRadius.setText(MapplsGeofenceSetting.instance.minRadius.toString() ?: "")
        mBinding.etPolygonDrawingLineColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.polygonDrawingLineColor))
        mBinding.etPolygonFillColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.polygonFillColor))
        mBinding.etPolygonFillOutlineColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.polygonFillOutlineColor))
        mBinding.etPolygonOutlineWidth.setText(MapplsGeofenceSetting.instance.polygonOutlineWidth.toString() ?: "")
        mBinding.swShowSeekBar.isChecked = MapplsGeofenceSetting.instance.showSeekBar
        mBinding.etSeekbarPrimaryColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.seekbarPrimaryColor))
        mBinding.etSeekbarSecondaryColor.setText(String.format("#%06X", MapplsGeofenceSetting.instance.seekbarSecondaryColor))
        mBinding.etSeekbarCornerRadius.setText(MapplsGeofenceSetting.instance.seekbarCornerRadius.toString() ?: "")

    }
}