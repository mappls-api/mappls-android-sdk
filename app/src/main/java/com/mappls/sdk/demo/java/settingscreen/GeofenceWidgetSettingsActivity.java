package com.mappls.sdk.demo.java.settingscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityGeofenceWidgetSettingsBinding;
import com.mappls.sdk.demo.java.settings.MapplsGeofenceSetting;

public class GeofenceWidgetSettingsActivity extends AppCompatActivity {

    private ActivityGeofenceWidgetSettingsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_geofence_widget_settings);
        initSettings();
        initCallbacks();

    }

    private void  initSettings(){

        mBinding.cbDefault.setChecked(MapplsGeofenceSetting.getInstance().isDefault());
        mBinding.disableView.setVisibility(MapplsGeofenceSetting.getInstance().isDefault() ? View.VISIBLE : View.GONE);
        mBinding.etCircleOutlineWidth.setText(MapplsGeofenceSetting.getInstance().getCircleOutlineWidth()+"");
        mBinding.showSeekbarBtn.setChecked(MapplsGeofenceSetting.getInstance().isShowSeekBar());
        mBinding.seekbarPrimaryColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getSeekbarPrimaryColor())));
        mBinding.seekbarSecondaryColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getSeekbarPrimaryColor())));
        mBinding.circleFillColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getCircleFillColor())));
        mBinding.circleFillOutlineColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getCircleFillOutlineColor())));
        mBinding.draggingLineColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getDraggingLineColor())));
        mBinding.etMaxRadius.setText(MapplsGeofenceSetting.getInstance().getMaxRadius()+"");
        mBinding.etMinRadius.setText(MapplsGeofenceSetting.getInstance().getMinRadius()+"");
        mBinding.polygonDrawingLineColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getPolygonDrawingLineColor())));
        mBinding.polygonFillColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getPolygonFillColor())));
        mBinding.polygonFillOutlineColorTv.setText(String.format("#%06X", (0xFFFFFF & MapplsGeofenceSetting.getInstance().getPolygonFillOutlineColor())));
        mBinding.etPolygonOutlineWidth.setText(MapplsGeofenceSetting.getInstance().getPolygonOutlineWidth()+"");
        mBinding.simplifyWhenIntersectingPolygonDetectedBtn.setChecked(MapplsGeofenceSetting.getInstance().isSimplifyWhenIntersectingPolygonDetected());
        mBinding.isPolygonBtn.setChecked(MapplsGeofenceSetting.getInstance().isPolygon());
        mBinding.etSeekbarCornerRadius.setText(MapplsGeofenceSetting.getInstance().getSeekbarCornerRadius()+"");
    }


    private void initCallbacks(){

        mBinding.cbDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsGeofenceSetting.getInstance().setDefault(isChecked);
            if (isChecked) {
                mBinding.disableView.setVisibility(View.VISIBLE);
            } else {
                mBinding.disableView.setVisibility(View.GONE);
            }
        });

        mBinding.showSeekbarBtn.setOnCheckedChangeListener((buttonView, isChecked) -> MapplsGeofenceSetting.getInstance().setShowSeekBar(isChecked));
        mBinding.btnSaveCircleOutlineWidth.setOnClickListener(v -> {
            if((!TextUtils.isEmpty(mBinding.etCircleOutlineWidth.getText().toString().trim()))) {
                MapplsGeofenceSetting.getInstance().setCircleOutlineWidth(Float.parseFloat(mBinding.etCircleOutlineWidth.getText().toString().trim()));
            }
        });

        mBinding.btnSeekbarCornerRadius.setOnClickListener(v -> {
            if((!TextUtils.isEmpty(mBinding.etSeekbarCornerRadius.getText().toString().trim()))) {
                MapplsGeofenceSetting.getInstance().setSeekbarCornerRadius(Float.parseFloat(mBinding.etSeekbarCornerRadius.getText().toString().trim()));
            }
        });
        mBinding.circleFillColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getCircleFillColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok", new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                            MapplsGeofenceSetting.getInstance().setCircleFillColor(selectedColor);
                            mBinding.circleFillColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .build()
                    .show();
        });

        mBinding.circleFillOutlineColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getCircleFillOutlineColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setCircleFillOutlineColor(selectedColor);
                        mBinding.circleFillOutlineColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });
        mBinding.draggingLineColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getDraggingLineColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setDraggingLineColor(selectedColor);
                        mBinding.draggingLineColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });
        mBinding.seekbarPrimaryColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getSeekbarPrimaryColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setSeekbarPrimaryColor(selectedColor);
                        mBinding.seekbarPrimaryColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });

        mBinding.seekbarSecondaryColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getSeekbarSecondaryColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setSeekbarSecondaryColor(selectedColor);
                        mBinding.seekbarSecondaryColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });

        mBinding.btnSaveMaxRadius.setOnClickListener(v -> {
            if((!TextUtils.isEmpty(mBinding.etMaxRadius.getText().toString().trim()))) {
                MapplsGeofenceSetting.getInstance().setMaxRadius(Integer.parseInt(mBinding.etMaxRadius.getText().toString().trim()));
            }
        });

        mBinding.btnSaveMinRadius.setOnClickListener(v -> {
            if((!TextUtils.isEmpty(mBinding.etMinRadius.getText().toString().trim()))) {
                MapplsGeofenceSetting.getInstance().setMinRadius(Integer.parseInt(mBinding.etMinRadius.getText().toString().trim()));
            }
        });
        mBinding.polygonDrawingLineColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getPolygonDrawingLineColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setPolygonDrawingLineColor(selectedColor);
                        mBinding.polygonDrawingLineColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });
        mBinding.polygonFillColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getPolygonFillColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setPolygonFillColor(selectedColor);
                        mBinding.polygonFillColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });
        mBinding.polygonFillOutlineColorLayout.setOnClickListener(v -> {
            ColorPickerDialogBuilder
                    .with(this)
                    .setTitle("Choose color")
                    .initialColor(MapplsGeofenceSetting.getInstance().getPolygonFillOutlineColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setPositiveButton("ok", (dialog, selectedColor, allColors) -> {
                        MapplsGeofenceSetting.getInstance().setPolygonFillOutlineColor(selectedColor);
                        mBinding.polygonFillOutlineColorTv.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("cancel", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });
        mBinding.btnPolygonOutlineWidth.setOnClickListener(v -> {
            if((!TextUtils.isEmpty(mBinding.etPolygonOutlineWidth.getText().toString().trim()))) {
                MapplsGeofenceSetting.getInstance().setPolygonOutlineWidth(Float.parseFloat(mBinding.etPolygonOutlineWidth.getText().toString().trim()));
            }
        });
        mBinding.simplifyWhenIntersectingPolygonDetectedBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsGeofenceSetting.getInstance().setSimplifyWhenIntersectingPolygonDetected(isChecked);
        });
        mBinding.isPolygonBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsGeofenceSetting.getInstance().setPolygon(isChecked);
        });

    }
}