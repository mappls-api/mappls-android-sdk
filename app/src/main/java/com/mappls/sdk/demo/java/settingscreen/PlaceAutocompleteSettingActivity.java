package com.mappls.sdk.demo.java.settingscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityPlaceAutocompleteSettingBinding;
import com.mappls.sdk.demo.java.settings.MapplsPlaceWidgetSetting;
import com.mappls.sdk.demo.java.utils.InputFilterMinMax;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria;

public class PlaceAutocompleteSettingActivity extends AppCompatActivity {

    private ActivityPlaceAutocompleteSettingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_autocomplete_setting);
        mBinding.etLatitude.setFilters(new InputFilter[]{new InputFilterMinMax(-90, 90)});
        mBinding.etLongitude.setFilters(new InputFilter[]{new InputFilterMinMax(-180, 180)});
        initSettings();
        initCallback();
    }

    private void initCallback() {
        mBinding.cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setDefault(isChecked);
                if (isChecked) {
                    mBinding.disableView.setVisibility(View.VISIBLE);
                } else {
                    mBinding.disableView.setVisibility(View.GONE);
                }
            }
        });

        mBinding.rgVertical.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mBinding.rbTop.getId()) {
                    MapplsPlaceWidgetSetting.getInstance().setSignatureVertical(PlaceOptions.GRAVITY_TOP);
                } else {

                    MapplsPlaceWidgetSetting.getInstance().setSignatureVertical(PlaceOptions.GRAVITY_BOTTOM);
                }
            }
        });

        mBinding.rgHorizontal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_left) {
                    MapplsPlaceWidgetSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_LEFT);
                } else if (checkedId == R.id.rb_center) {
                    MapplsPlaceWidgetSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_CENTER);
                } else if (checkedId == R.id.rb_right) {
                    MapplsPlaceWidgetSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_RIGHT);
                }
            }
        });

        mBinding.rgLogoSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_small) {
                    MapplsPlaceWidgetSetting.getInstance().setLogoSize(PlaceOptions.SIZE_SMALL);
                } else if (checkedId == R.id.rb_medium) {
                    MapplsPlaceWidgetSetting.getInstance().setLogoSize(PlaceOptions.SIZE_MEDIUM);
                } else if (checkedId == R.id.rb_large) {
                    MapplsPlaceWidgetSetting.getInstance().setLogoSize(PlaceOptions.SIZE_LARGE);
                }
            }
        });

        mBinding.btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!TextUtils.isEmpty(mBinding.etLatitude.getText().toString().trim())) && (!TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim()))) {
                    MapplsPlaceWidgetSetting.getInstance().setLocation(Point.fromLngLat(Double.parseDouble(mBinding.etLongitude.getText().toString().trim()), Double.parseDouble(mBinding.etLatitude.getText().toString().trim())));
                } else {
                    MapplsPlaceWidgetSetting.getInstance().setLocation(null);
                }
                Toast.makeText(PlaceAutocompleteSettingActivity.this, "Location save successfully", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.btnSaveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mBinding.etFilter.getText().toString().trim())) {
                    MapplsPlaceWidgetSetting.getInstance().setFilter(mBinding.etFilter.getText().toString().trim());
                } else {
                    MapplsPlaceWidgetSetting.getInstance().setFilter(null);
                }
                Toast.makeText(PlaceAutocompleteSettingActivity.this, "Filter save successfully", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.cbEnableHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setEnableHistory(isChecked);
                mBinding.etHistoryCount.setEnabled(isChecked);
            }
        });
        mBinding.enableLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setEnableLocation(isChecked);

            }
        });
        mBinding.btnPodClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.rgPod.clearCheck();
                MapplsPlaceWidgetSetting.getInstance().setPod(null);
            }
        });
        mBinding.rgPod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_city) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_CITY);
                } else if (checkedId == R.id.rb_state) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_STATE);
                } else if (checkedId == R.id.rb_sub_district) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_DISTRICT);
                } else if (checkedId == R.id.rb_district) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_DISTRICT);
                } else if (checkedId == R.id.rb_sub_locality) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_LOCALITY);
                } else if (checkedId == R.id.rb_sub_sub_locality) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY);
                } else if (checkedId == R.id.rb_locality) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_LOCALITY);
                } else if (checkedId == R.id.rb_village) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_VILLAGE);
                } else if (checkedId == R.id.rb_poi) {
                    MapplsPlaceWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_POI);
                }
            }
        });


        mBinding.btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mBinding.etHint.getText().toString().trim())) {
                    MapplsPlaceWidgetSetting.getInstance().setHint(mBinding.etHint.getText().toString().trim());
                    Toast.makeText(PlaceAutocompleteSettingActivity.this, "Hint save successfully", Toast.LENGTH_SHORT).show();
                } else {
                    mBinding.etHint.setText(MapplsPlaceWidgetSetting.getInstance().getHint());
                    Toast.makeText(PlaceAutocompleteSettingActivity.this, "Hint is mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBinding.cbEnableTextSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setEnableTextSearch(isChecked);
            }
        });
        mBinding.backgroundRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_white) {
                    MapplsPlaceWidgetSetting.getInstance().setBackgroundColor(android.R.color.white);
                } else if (checkedId == R.id.rb_black) {
                    MapplsPlaceWidgetSetting.getInstance().setBackgroundColor(android.R.color.black);
                } else if (checkedId == R.id.rb_red) {
                    MapplsPlaceWidgetSetting.getInstance().setBackgroundColor(android.R.color.holo_red_light);
                } else if (checkedId == R.id.rb_green) {
                    MapplsPlaceWidgetSetting.getInstance().setBackgroundColor(android.R.color.holo_green_dark);
                } else if (checkedId == R.id.rb_blue) {
                    MapplsPlaceWidgetSetting.getInstance().setBackgroundColor(android.R.color.holo_blue_bright);
                }
            }
        });
        mBinding.toolbarRG.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_white_toolbar) {
                MapplsPlaceWidgetSetting.getInstance().setToolbarColor(android.R.color.white);
            } else if (checkedId == R.id.rb_black_toolbar) {
                MapplsPlaceWidgetSetting.getInstance().setToolbarColor(android.R.color.black);
            } else if (checkedId == R.id.rb_red_toolbar) {
                MapplsPlaceWidgetSetting.getInstance().setToolbarColor(android.R.color.holo_red_light);
            } else if (checkedId == R.id.rb_green_toolbar) {
                MapplsPlaceWidgetSetting.getInstance().setToolbarColor(android.R.color.holo_green_dark);
            } else if (checkedId == R.id.rb_blue_toolbar) {
                MapplsPlaceWidgetSetting.getInstance().setToolbarColor(android.R.color.holo_blue_bright);
            }
        });
        mBinding.cbEnableBridge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setEnableBridge(isChecked);
            }
        });
        mBinding.cbEnableHyperlocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setEnableHyperLocal(isChecked);
            }
        });
        mBinding.cbEnableFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsPlaceWidgetSetting.getInstance().setEnableShowFavorite(isChecked);
            }
        });
        mBinding.etDBounce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    int value = Integer.parseInt(s.toString());
                    if (value > 1500) {
                        Toast.makeText(PlaceAutocompleteSettingActivity.this, "Maximum value should be 1500", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(PlaceAutocompleteSettingActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
                        MapplsPlaceWidgetSetting.getInstance().setDeBounce(value);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mBinding.etHistoryCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    int value = Integer.parseInt(s.toString());
                    MapplsPlaceWidgetSetting.getInstance().setHistoryCount(value);
//                    if (value > 1500) {
//                        Toast.makeText(PlaceAutocompleteSettingActivity.this, "Maximum value should be 1500", Toast.LENGTH_SHORT).show();
//                    } else {
////                        Toast.makeText(PlaceAutocompleteSettingActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
//                        MapplsPlaceWidgetSetting.getInstance().setHistoryCount(value);
//                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void initSettings() {
        mBinding.cbDefault.setChecked(MapplsPlaceWidgetSetting.getInstance().isDefault());
        mBinding.disableView.setVisibility(MapplsPlaceWidgetSetting.getInstance().isDefault() ? View.VISIBLE : View.GONE);
       mBinding.etHistoryCount.setEnabled(MapplsPlaceWidgetSetting.getInstance().isEnableHistory());

        if (MapplsPlaceWidgetSetting.getInstance().getSignatureVertical() == PlaceOptions.GRAVITY_TOP) {
            mBinding.rgVertical.check(mBinding.rbTop.getId());
        } else {
            mBinding.rgVertical.check(mBinding.rbBottom.getId());
        }

        if (MapplsPlaceWidgetSetting.getInstance().getSignatureHorizontal() == PlaceOptions.GRAVITY_LEFT) {
            mBinding.rgHorizontal.check(mBinding.rbLeft.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getSignatureHorizontal() == PlaceOptions.GRAVITY_CENTER) {
            mBinding.rgHorizontal.check(mBinding.rbCenter.getId());
        } else {
            mBinding.rgHorizontal.check(mBinding.rbRight.getId());
        }

        if (MapplsPlaceWidgetSetting.getInstance().getLogoSize() == PlaceOptions.SIZE_SMALL) {
            mBinding.rgLogoSize.check(R.id.rb_small);
        } else if (MapplsPlaceWidgetSetting.getInstance().getLogoSize() == PlaceOptions.SIZE_MEDIUM) {
            mBinding.rgLogoSize.check(R.id.rb_medium);
        } else {
            mBinding.rgLogoSize.check(R.id.rb_large);
        }

        if (MapplsPlaceWidgetSetting.getInstance().getLocation() != null) {
            mBinding.etLatitude.setText(String.valueOf(MapplsPlaceWidgetSetting.getInstance().getLocation().latitude()));
            mBinding.etLongitude.setText(String.valueOf(MapplsPlaceWidgetSetting.getInstance().getLocation().longitude()));
        }
        if (MapplsPlaceWidgetSetting.getInstance().getFilter() != null) {
            mBinding.etFilter.setText(MapplsPlaceWidgetSetting.getInstance().getFilter());
        }

        mBinding.cbEnableHistory.setChecked(MapplsPlaceWidgetSetting.getInstance().isEnableHistory());
        mBinding.enableLocation.setChecked(MapplsPlaceWidgetSetting.getInstance().isEnableLocation());

        if (MapplsPlaceWidgetSetting.getInstance().getPod() != null) {
            if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_CITY)) {
                mBinding.rgPod.check(mBinding.rbCity.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_DISTRICT)) {
                mBinding.rgPod.check(mBinding.rbDistrict.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbLocality.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_STATE)) {
                mBinding.rgPod.check(mBinding.rbState.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_DISTRICT)) {
                mBinding.rgPod.check(mBinding.rbSubDistrict.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbSubLocality.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbSubSubLocality.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_VILLAGE)) {
                mBinding.rgPod.check(mBinding.rbVillage.getId());
            } else if (MapplsPlaceWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_POI)) {
                mBinding.rgPod.check(mBinding.rbPoi.getId());
            }
        } else {
            mBinding.rgPod.clearCheck();
        }

        mBinding.etHint.setText(MapplsPlaceWidgetSetting.getInstance().getHint());
        mBinding.cbEnableTextSearch.setChecked(MapplsPlaceWidgetSetting.getInstance().isEnableTextSearch());


        if (MapplsPlaceWidgetSetting.getInstance().getBackgroundColor() == android.R.color.white) {
            mBinding.backgroundRG.check(mBinding.rbWhite.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getBackgroundColor() == android.R.color.black) {
            mBinding.backgroundRG.check(mBinding.rbBlack.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getBackgroundColor() == android.R.color.holo_red_light) {
            mBinding.backgroundRG.check(mBinding.rbRed.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getBackgroundColor() == android.R.color.holo_green_dark) {
            mBinding.backgroundRG.check(mBinding.rbGreen.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getBackgroundColor() == android.R.color.holo_blue_bright) {
            mBinding.backgroundRG.check(mBinding.rbBlue.getId());
        }


        if (MapplsPlaceWidgetSetting.getInstance().getToolbarColor() == android.R.color.white) {
            mBinding.toolbarRG.check(mBinding.rbWhiteToolbar.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getToolbarColor() == android.R.color.black) {
            mBinding.toolbarRG.check(mBinding.rbBlackToolbar.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getToolbarColor() == android.R.color.holo_red_light) {
            mBinding.toolbarRG.check(mBinding.rbRedToolbar.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getToolbarColor() == android.R.color.holo_green_dark) {
            mBinding.toolbarRG.check(mBinding.rbGreenToolbar.getId());
        } else if (MapplsPlaceWidgetSetting.getInstance().getToolbarColor() == android.R.color.holo_blue_bright) {
            mBinding.toolbarRG.check(mBinding.rbBlueToolbar.getId());
        }
        mBinding.cbEnableBridge.setChecked(MapplsPlaceWidgetSetting.getInstance().isEnableBridge());
        mBinding.cbEnableHyperlocal.setChecked(MapplsPlaceWidgetSetting.getInstance().isEnableHyperLocal());
        mBinding.etDBounce.setText(String.valueOf(MapplsPlaceWidgetSetting.getInstance().getDeBounce()));
        mBinding.etHistoryCount.setText(String.valueOf(MapplsPlaceWidgetSetting.getInstance().getHistoryCount()));
        mBinding.cbEnableFavorite.setChecked(MapplsPlaceWidgetSetting.getInstance().isEnableShowFavorite());

    }
}