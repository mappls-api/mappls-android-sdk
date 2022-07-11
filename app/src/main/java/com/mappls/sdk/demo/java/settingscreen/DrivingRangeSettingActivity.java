package com.mappls.sdk.demo.java.settingscreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityDrivingRangeSettingBinding;
import com.mappls.sdk.demo.java.settings.MapplsDrivingRangeSetting;
import com.mappls.sdk.demo.java.utils.InputFilterMinMax;
import com.mappls.sdk.drivingrange.DrivingRangeCriteria;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.mappls.sdk.services.api.autosuggest.model.ELocation;

import java.util.Calendar;
import java.util.Date;

public class DrivingRangeSettingActivity extends AppCompatActivity {

    private ActivityDrivingRangeSettingBinding mBinding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_driving_range_setting);
        mBinding.etLatitude.setFilters(new InputFilter[]{new InputFilterMinMax(-90, 90)});
        mBinding.etLongitude.setFilters(new InputFilter[]{new InputFilterMinMax(-180, 180)});
        mBinding.etContourValue.setFilters(new InputFilter[]{new InputFilterMinMax(1, 500)});
        mBinding.etDenoise.setFilters(new InputFilter[]{new InputFilterMinMax(0, 1)});
        mBinding.datePicker.setMinDate(System.currentTimeMillis());
        initialiseView();
        initCallback();
    }

    private void initCallback() {
        mBinding.cbCurrentLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsDrivingRangeSetting.getInstance().setUsingCurrentLocation(isChecked);
            }
        });
        mBinding.btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(
                        mBinding.etLatitude.getText().toString().trim()
                ) && !TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim())
                ) {
                    MapplsDrivingRangeSetting.getInstance().setLocation(Point.fromLngLat(
                            Double.parseDouble(mBinding.etLongitude.getText().toString().trim()),
                            Double.parseDouble(mBinding.etLatitude.getText().toString().trim())
                    ));
                }


            }
        });
        mBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceAutocompleteFragment placeAutocompleteFragment =
                        PlaceAutocompleteFragment.newInstance(
                                PlaceOptions.builder().backgroundColor(Color.WHITE)
                                        .build(PlaceOptions.MODE_CARDS)
                        );
                placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(ELocation eLocation) {
                        if (eLocation.latitude != null && eLocation.longitude != null) {
                            mBinding.etLongitude.setText(eLocation.longitude + "");
                            mBinding.etLatitude.setText(eLocation.latitude + "");
                        }
                        if (!TextUtils.isEmpty(
                                mBinding.etLatitude.getText().toString().trim()
                        ) && !TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim())
                        ) {
                            MapplsDrivingRangeSetting.getInstance().setLocation(Point.fromLngLat(
                                    Double.parseDouble(mBinding.etLongitude.getText().toString().trim()),
                                    Double.parseDouble(mBinding.etLatitude.getText().toString().trim())
                            ));
                        }
                        getSupportFragmentManager().popBackStack(
                                PlaceAutocompleteFragment.class.getSimpleName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                        );
                    }

                    @Override
                    public void onCancel() {

                        getSupportFragmentManager().popBackStack(
                                PlaceAutocompleteFragment.class.getSimpleName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                        );
                    }
                });
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.setting_layout, placeAutocompleteFragment, PlaceAutocompleteFragment.class.getSimpleName())
                        .addToBackStack(PlaceAutocompleteFragment.class.getSimpleName())
                        .commit();
            }
        });
        mBinding.rgRangeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_distance) {
                    MapplsDrivingRangeSetting.getInstance().setRangeType(DrivingRangeCriteria.RANGE_TYPE_DISTANCE);
                } else {
                    MapplsDrivingRangeSetting.getInstance().setRangeType(DrivingRangeCriteria.RANGE_TYPE_TIME);
                }
            }
        });
        mBinding.btnSaveContourValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mBinding.etContourValue.getText().toString().trim())) {
                    MapplsDrivingRangeSetting.getInstance().setContourValue(Integer.parseInt(mBinding.etContourValue.getText().toString()));
                }
            }
        });
        mBinding.contourColorRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_contour_red:
                        MapplsDrivingRangeSetting.getInstance().setContourColor("ff0000");
                        break;
                    case R.id.rb_contour_green:
                        MapplsDrivingRangeSetting.getInstance().setContourColor("00ff00");
                        break;
                    default:
                        MapplsDrivingRangeSetting.getInstance().setContourColor("0000ff");
                        break;
                }
            }
        });
        mBinding.btnDrivingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(
                        mBinding.etDrivingProfile.getText().toString().trim()
                )
                ) {
                    MapplsDrivingRangeSetting.getInstance().setDrivingProfile(mBinding.etDrivingProfile.getText().toString());
                }
            }
        });
        mBinding.showLocationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsDrivingRangeSetting.getInstance().setShowLocations(isChecked);
            }
        });
        mBinding.showPolygonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsDrivingRangeSetting.getInstance().setForPolygon(isChecked);
            }
        });
        mBinding.btnDenoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(
                        mBinding.etDenoise.getText().toString().trim()
                )
                ) {
                    MapplsDrivingRangeSetting.getInstance().setDenoise(Float.parseFloat(mBinding.etDenoise.getText().toString()));
                }
            }
        });
        mBinding.btnGeneralize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(
                        mBinding.etGeneralize.getText().toString().trim()
                )
                ) {
                    MapplsDrivingRangeSetting.getInstance().setGeneralize(Float.parseFloat(mBinding.etGeneralize.getText().toString()));
                }
            }
        });
        mBinding.rgSpeedType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_optimal) {
                    MapplsDrivingRangeSetting.getInstance().setSpeedType(MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL);
                } else {
                    MapplsDrivingRangeSetting.getInstance().setSpeedType(MapplsDrivingRangeSetting.SPEED_TYPE_PREDECTIVE);
                }

                mBinding.layoutPredective.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL ? View.GONE : View.VISIBLE);
                mBinding.datePicker.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT ? View.GONE : View.VISIBLE);
                mBinding.timePicker.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT ? View.GONE : View.VISIBLE);
            }
        });
        mBinding.rgPredectiveType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_current) {
                    MapplsDrivingRangeSetting.getInstance().setPredectiveType(MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT);
                } else {
                    MapplsDrivingRangeSetting.getInstance().setPredectiveType(MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CUSTOM);
                }
                mBinding.layoutPredective.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL ? View.GONE : View.VISIBLE);
                mBinding.datePicker.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT ? View.GONE : View.VISIBLE);
                mBinding.timePicker.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT ? View.GONE : View.VISIBLE);
            }
        });
        mBinding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(mBinding.datePicker.getYear(), mBinding.datePicker.getMonth(), mBinding.datePicker.getDayOfMonth(), hourOfDay, minute);
                MapplsDrivingRangeSetting.getInstance().setTime(calendar.getTimeInMillis());

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initialiseView() {
        mBinding.cbCurrentLocation.setChecked(MapplsDrivingRangeSetting.getInstance().isUsingCurrentLocation());
        mBinding.etLatitude.setText("" +MapplsDrivingRangeSetting.getInstance().getLocation().latitude());
        mBinding.etLongitude.setText("" +MapplsDrivingRangeSetting.getInstance().getLocation().longitude());
        mBinding.rbDistance.setChecked(MapplsDrivingRangeSetting.getInstance().getRangeType().equals(DrivingRangeCriteria.RANGE_TYPE_DISTANCE));
        mBinding.rbTime.setChecked(MapplsDrivingRangeSetting.getInstance().getRangeType().equals(DrivingRangeCriteria.RANGE_TYPE_TIME));
        mBinding.etContourValue.setText("" + MapplsDrivingRangeSetting.getInstance().getContourValue());
        switch (MapplsDrivingRangeSetting.getInstance().getContourColor()) {
            case "00ff00":
                mBinding.rbContourGreen.setChecked(true);
            break;
            case "0000ff":
                mBinding.rbContourBlue.setChecked(true);
                break;

            default:
                mBinding.rbContourRed.setChecked(true);
            break;
        }
        mBinding.etDrivingProfile.setText(MapplsDrivingRangeSetting.getInstance().getDrivingProfile());
        mBinding.showLocationsSwitch.setChecked(MapplsDrivingRangeSetting.getInstance().isShowLocations());
        mBinding.showPolygonSwitch.setChecked(MapplsDrivingRangeSetting.getInstance().isForPolygon());
        mBinding.etDenoise.setText(""+MapplsDrivingRangeSetting.getInstance().getDenoise());
        mBinding.etGeneralize.setText(""+MapplsDrivingRangeSetting.getInstance().getGeneralize());
        mBinding.rgSpeedType.check(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL? R.id.rb_optimal :R.id.rb_predective);
        mBinding.rgPredectiveType.check(MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT? R.id.rb_current :R.id.rb_custom);
        mBinding.layoutPredective.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL? View.GONE :View.VISIBLE);
        mBinding.datePicker.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT? View.GONE :View.VISIBLE);
        mBinding.timePicker.setVisibility(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL || MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT? View.GONE :View.VISIBLE);
        Date date = new Date(MapplsDrivingRangeSetting.getInstance().getTime());
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        mBinding.datePicker.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth, mBinding.timePicker.getHour(), mBinding.timePicker.getMinute());
                MapplsDrivingRangeSetting.getInstance().setTime(calendar.getTimeInMillis());
            }
        });
        mBinding.timePicker.setHour(calender.get(Calendar.HOUR_OF_DAY));
        mBinding.timePicker.setMinute(calender.get(Calendar.MINUTE));
    }
}