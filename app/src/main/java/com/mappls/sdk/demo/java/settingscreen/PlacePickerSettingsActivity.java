package com.mappls.sdk.demo.java.settingscreen;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityPlacePickerSettingsBinding;
import com.mappls.sdk.demo.java.settings.MapplsPlacePickerSetting;
import com.mappls.sdk.demo.java.utils.InputFilterMinMax;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria;

public class PlacePickerSettingsActivity extends AppCompatActivity {
    private ActivityPlacePickerSettingsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_picker_settings);
        mBinding.etLatitude.setFilters(new InputFilter[]{new InputFilterMinMax(-90, 90)});
        mBinding.etLongitude.setFilters(new InputFilter[]{new InputFilterMinMax(-180, 180)});
        initSettings();
        initCallback();
    }

    private void initCallback() {

        mBinding.pickerToolbarColor.setText(String.format("#%06X", (0xFFFFFF & MapplsPlacePickerSetting.getInstance().getPickerToolbarColor())));
        mBinding.includeDeviceLocation.setChecked(MapplsPlacePickerSetting.getInstance().isIncludeDeviceLocation());
        mBinding.includeSearchButton.setChecked(MapplsPlacePickerSetting.getInstance().isIncludeSearch());
        mBinding.tokenizeAddressBtn.setChecked(MapplsPlacePickerSetting.getInstance().isTokenizeAddress());

        mBinding.cbDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsPlacePickerSetting.getInstance().setDefault(isChecked);
            if (isChecked) {
                mBinding.disableView.setVisibility(View.VISIBLE);
            } else {
                mBinding.disableView.setVisibility(View.GONE);
            }
        });

        mBinding.rgVertical.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == mBinding.rbTop.getId()) {
                MapplsPlacePickerSetting.getInstance().setSignatureVertical(PlaceOptions.GRAVITY_TOP);
            } else {

                MapplsPlacePickerSetting.getInstance().setSignatureVertical(PlaceOptions.GRAVITY_BOTTOM);
            }
        });

        mBinding.rgHorizontal.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_left) {
                MapplsPlacePickerSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_LEFT);
            } else if (checkedId == R.id.rb_center) {
                MapplsPlacePickerSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_CENTER);
            } else if (checkedId == R.id.rb_right) {
                MapplsPlacePickerSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_RIGHT);
            }
        });

        mBinding.rgLogoSize.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_small) {
                MapplsPlacePickerSetting.getInstance().setLogoSize(PlaceOptions.SIZE_SMALL);
            } else if (checkedId == R.id.rb_medium) {
                MapplsPlacePickerSetting.getInstance().setLogoSize(PlaceOptions.SIZE_MEDIUM);
            } else if (checkedId == R.id.rb_large) {
                MapplsPlacePickerSetting.getInstance().setLogoSize(PlaceOptions.SIZE_LARGE);
            }
        });

        mBinding.btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!TextUtils.isEmpty(mBinding.etLatitude.getText().toString().trim())) && (!TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim()))) {
                    MapplsPlacePickerSetting.getInstance().setLocation(Point.fromLngLat(Double.parseDouble(mBinding.etLongitude.getText().toString().trim()), Double.parseDouble(mBinding.etLatitude.getText().toString().trim())));
                } else {
                    MapplsPlacePickerSetting.getInstance().setLocation(null);
                }
                Toast.makeText(PlacePickerSettingsActivity.this, "Location save successfully", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.btnSaveHistoryCount.setOnClickListener(v -> {
            if((!TextUtils.isEmpty(mBinding.etHistoryCount.getText().toString()))) {
                MapplsPlacePickerSetting.getInstance().setHistoryCount(Integer.parseInt(mBinding.etHistoryCount.getText().toString()));
            } else {
                MapplsPlacePickerSetting.getInstance().setHistoryCount(null);
            }
            Toast.makeText(PlacePickerSettingsActivity.this, "History Count save successfully", Toast.LENGTH_SHORT).show();
        });

        mBinding.btnSaveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mBinding.etFilter.getText().toString().trim())) {
                    MapplsPlacePickerSetting.getInstance().setFilter(mBinding.etFilter.getText().toString().trim());
                } else {
                    MapplsPlacePickerSetting.getInstance().setFilter(null);
                }
                Toast.makeText(PlacePickerSettingsActivity.this, "Filter save successfully", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.cbEnableHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlacePickerSetting.getInstance().setEnableHistory(isChecked);
               if (isChecked){
                   mBinding.historyCountLayout.setVisibility(View.VISIBLE);
               }else {
                   mBinding.historyCountLayout.setVisibility(View.GONE);
               }
            }
        });
        mBinding.btnPodClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.rgPod.clearCheck();
                MapplsPlacePickerSetting.getInstance().setPod(null);
            }
        });
        mBinding.rgPod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_city) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_CITY);
                } else if (checkedId == R.id.rb_state) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_STATE);
                } else if (checkedId == R.id.rb_sub_district) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_DISTRICT);
                }else if (checkedId == R.id.rb_district) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_DISTRICT);
                }else if (checkedId == R.id.rb_sub_locality) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_LOCALITY);
                }else if (checkedId == R.id.rb_sub_sub_locality) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY);
                }else if (checkedId == R.id.rb_locality) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_LOCALITY);
                }else if (checkedId == R.id.rb_village) {
                    MapplsPlacePickerSetting.getInstance().setPod(AutoSuggestCriteria.POD_VILLAGE);
                }
            }
        });


        mBinding.btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mBinding.etHint.getText().toString().trim())) {
                    MapplsPlacePickerSetting.getInstance().setHint(mBinding.etHint.getText().toString().trim());
                    Toast.makeText(PlacePickerSettingsActivity.this, "Hint save successfully", Toast.LENGTH_SHORT).show();
                } else {
                    mBinding.etHint.setText(MapplsPlacePickerSetting.getInstance().getHint());
                    Toast.makeText(PlacePickerSettingsActivity.this, "Hint is mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBinding.btnZoom.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(mBinding.etZoom.getText().toString().trim())) {
                MapplsPlacePickerSetting.getInstance().setZoom(Double.parseDouble(mBinding.etZoom.getText().toString().trim()));
                Toast.makeText(PlacePickerSettingsActivity.this, "zoom save successfully", Toast.LENGTH_SHORT).show();
            }
        });

       /* mBinding.cbEnableTextSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlacePickerSetting.getInstance().setEnableTextSearch(isChecked);
            }
        });*/
        mBinding.backgroundRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_white) {
                    MapplsPlacePickerSetting.getInstance().setBackgroundColor(android.R.color.white);
                } else if (checkedId == R.id.rb_black) {
                    MapplsPlacePickerSetting.getInstance().setBackgroundColor(android.R.color.black);
                } else if (checkedId == R.id.rb_red) {
                    MapplsPlacePickerSetting.getInstance().setBackgroundColor(android.R.color.holo_red_light);
                }else if (checkedId == R.id.rb_green) {
                    MapplsPlacePickerSetting.getInstance().setBackgroundColor(android.R.color.holo_green_dark);
                }else if (checkedId == R.id.rb_blue) {
                    MapplsPlacePickerSetting.getInstance().setBackgroundColor(android.R.color.holo_blue_bright);
                }
            }
        });
        mBinding.toolbarRG.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_white_toolbar) {
                MapplsPlacePickerSetting.getInstance().setToolbarColor(android.R.color.white);
            } else if (checkedId == R.id.rb_black_toolbar) {
                MapplsPlacePickerSetting.getInstance().setToolbarColor(android.R.color.black);
            } else if (checkedId == R.id.rb_red_toolbar) {
                MapplsPlacePickerSetting.getInstance().setToolbarColor(android.R.color.holo_red_light);
            }else if (checkedId == R.id.rb_green_toolbar) {
                MapplsPlacePickerSetting.getInstance().setToolbarColor(android.R.color.holo_green_dark);
            }else if (checkedId == R.id.rb_blue_toolbar) {
                MapplsPlacePickerSetting.getInstance().setToolbarColor(android.R.color.holo_blue_bright);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void initSettings() {

       mBinding.toolbarColorLayout.setOnClickListener(v -> {
           ColorPickerDialogBuilder
                   .with(this)
                   .setTitle("Choose color")
                   .initialColor(MapplsPlacePickerSetting.getInstance().getPickerToolbarColor())
                   .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                   .density(12)
                   .setPositiveButton("ok", new ColorPickerClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                          // changeBackgroundColor(selectedColor);
                           MapplsPlacePickerSetting.getInstance().setPickerToolbarColor(selectedColor);
                           mBinding.pickerToolbarColor.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
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

        mBinding.includeDeviceLocation.setOnCheckedChangeListener((buttonView, isChecked) -> {
             MapplsPlacePickerSetting.getInstance().setIncludeDeviceLocation(isChecked);
        });
        mBinding.includeSearchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsPlacePickerSetting.getInstance().setIncludeSearch(isChecked);
        });
      mBinding.tokenizeAddressBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
          MapplsPlacePickerSetting.getInstance().setTokenizeAddress(isChecked);
      });

        mBinding.cbDefault.setChecked(MapplsPlacePickerSetting.getInstance().isDefault());
        mBinding.disableView.setVisibility(MapplsPlacePickerSetting.getInstance().isDefault() ? View.VISIBLE : View.GONE);

        if (MapplsPlacePickerSetting.getInstance().getHistoryCount()!=null){
            mBinding.etHistoryCount.setText(MapplsPlacePickerSetting.getInstance().getHistoryCount().toString());
        }

        if (MapplsPlacePickerSetting.getInstance().getSignatureVertical() == PlaceOptions.GRAVITY_TOP) {
            mBinding.rgVertical.check(mBinding.rbTop.getId());
        } else {
            mBinding.rgVertical.check(mBinding.rbBottom.getId());
        }

        if (MapplsPlacePickerSetting.getInstance().getSignatureHorizontal() == PlaceOptions.GRAVITY_LEFT) {
            mBinding.rgHorizontal.check(mBinding.rbLeft.getId());
        } else if (MapplsPlacePickerSetting.getInstance().getSignatureHorizontal() == PlaceOptions.GRAVITY_CENTER) {
            mBinding.rgHorizontal.check(mBinding.rbCenter.getId());
        } else {
            mBinding.rgHorizontal.check(mBinding.rbRight.getId());
        }

        if (MapplsPlacePickerSetting.getInstance().getLogoSize() == PlaceOptions.SIZE_SMALL) {
            mBinding.rgLogoSize.check(R.id.rb_small);
        } else if (MapplsPlacePickerSetting.getInstance().getLogoSize() == PlaceOptions.SIZE_MEDIUM) {
            mBinding.rgLogoSize.check(R.id.rb_medium);
        } else {
            mBinding.rgLogoSize.check(R.id.rb_large);
        }

        if(MapplsPlacePickerSetting.getInstance().getLocation() != null) {
            mBinding.etLatitude.setText(String.valueOf(MapplsPlacePickerSetting.getInstance().getLocation().latitude()));
            mBinding.etLongitude.setText(String.valueOf(MapplsPlacePickerSetting.getInstance().getLocation().longitude()));
        }
        if(MapplsPlacePickerSetting.getInstance().getFilter() != null) {
            mBinding.etFilter.setText(MapplsPlacePickerSetting.getInstance().getFilter());
        }

        mBinding.cbEnableHistory.setChecked(MapplsPlacePickerSetting.getInstance().isEnableHistory());
        if (!MapplsPlacePickerSetting.getInstance().isEnableHistory()){
            mBinding.historyCountLayout.setVisibility(View.GONE);
        }else {
            mBinding.historyCountLayout.setVisibility(View.VISIBLE);
        }

        if(MapplsPlacePickerSetting.getInstance().getPod() != null) {
            if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_CITY)) {
                mBinding.rgPod.check(mBinding.rbCity.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_DISTRICT)) {
                mBinding.rgPod.check(mBinding.rbDistrict.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbLocality.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_STATE)) {
                mBinding.rgPod.check(mBinding.rbState.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_DISTRICT)) {
                mBinding.rgPod.check(mBinding.rbSubDistrict.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbSubLocality.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbSubSubLocality.getId());
            } else if(MapplsPlacePickerSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_VILLAGE)) {
                mBinding.rgPod.check(mBinding.rbVillage.getId());
            }
        } else {
            mBinding.rgPod.clearCheck();
        }

        mBinding.etHint.setText(MapplsPlacePickerSetting.getInstance().getHint());
        if (MapplsPlacePickerSetting.getInstance().getZoom()!=null){
            mBinding.etZoom.setText(MapplsPlacePickerSetting.getInstance().getZoom().toString());

        }
       // mBinding.cbEnableTextSearch.setChecked(MapplsPlacePickerSetting.getInstance().isEnableTextSearch());


        if (MapplsPlacePickerSetting.getInstance().getBackgroundColor()== android.R.color.white){
            mBinding.backgroundRG.check(mBinding.rbWhite.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getBackgroundColor()== android.R.color.black){
            mBinding.backgroundRG.check(mBinding.rbBlack.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getBackgroundColor()== android.R.color.holo_red_light){
            mBinding.backgroundRG.check(mBinding.rbRed.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getBackgroundColor()== android.R.color.holo_green_dark){
            mBinding.backgroundRG.check(mBinding.rbGreen.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getBackgroundColor()== android.R.color.holo_blue_bright){
            mBinding.backgroundRG.check(mBinding.rbBlue.getId());
        }


        if (MapplsPlacePickerSetting.getInstance().getToolbarColor()== android.R.color.white){
            mBinding.toolbarRG.check(mBinding.rbWhiteToolbar.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getToolbarColor()== android.R.color.black){
            mBinding.toolbarRG.check(mBinding.rbBlackToolbar.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getToolbarColor()== android.R.color.holo_red_light){
            mBinding.toolbarRG.check(mBinding.rbRedToolbar.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getToolbarColor()== android.R.color.holo_green_dark){
            mBinding.toolbarRG.check(mBinding.rbGreenToolbar.getId());
        }else  if (MapplsPlacePickerSetting.getInstance().getToolbarColor()== android.R.color.holo_blue_bright){
            mBinding.toolbarRG.check(mBinding.rbBlueToolbar.getId());
        }
    }

}