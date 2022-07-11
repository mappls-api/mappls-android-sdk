package com.mappls.sdk.demo.java.settingscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityDirectionWidgetSettingBinding;
import com.mappls.sdk.demo.java.settings.MapplsDirectionWidgetSetting;
import com.mappls.sdk.demo.java.utils.InputFilterMinMax;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.services.api.autosuggest.AutoSuggestCriteria;
import com.mappls.sdk.services.api.directions.DirectionsCriteria;

import java.util.ArrayList;
import java.util.List;

public class DirectionWidgetSettingActivity extends AppCompatActivity {

    private ActivityDirectionWidgetSettingBinding mBinding;
    private List<String> excludes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_direction_widget_setting);
        mBinding.etLatitude.setFilters(new InputFilter[]{new InputFilterMinMax(-90, 90)});
        mBinding.etLongitude.setFilters(new InputFilter[]{new InputFilterMinMax(-180, 180)});
        initSettings();
        initCallback();
    }

    private void initCallback() {
        if (MapplsDirectionWidgetSetting.getInstance().getExcludes() == null) {
            excludes = new ArrayList<>();
        } else {
            excludes = MapplsDirectionWidgetSetting.getInstance().getExcludes();
            for (String item : excludes) {
                if (item.equalsIgnoreCase(DirectionsCriteria.EXCLUDE_FERRY)) {
                    mBinding.cbFerry.setChecked(true);
                } else if (item.equalsIgnoreCase(DirectionsCriteria.EXCLUDE_MOTORWAY)) {
                    mBinding.cbMotorway.setChecked(true);
                } else if (item.equalsIgnoreCase(DirectionsCriteria.EXCLUDE_TOLL)) {
                    mBinding.cbToll.setChecked(true);
                }
            }
        }
        mBinding.showAlternativeBtn.setChecked(MapplsDirectionWidgetSetting.getInstance().isShowAlternative());
        mBinding.showStepsBtn.setChecked(MapplsDirectionWidgetSetting.getInstance().isSteps());
        mBinding.showStartNavigationBtn.setChecked(MapplsDirectionWidgetSetting.getInstance().isShowStartNavigation());
        mBinding.tokenizeAddressBtn.setChecked(MapplsDirectionWidgetSetting.getInstance().isTokenizeAddress());


        mBinding.cbDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsDirectionWidgetSetting.getInstance().setDefault(isChecked);
            if (isChecked) {
                mBinding.disableView.setVisibility(View.VISIBLE);
            } else {
                mBinding.disableView.setVisibility(View.GONE);
            }
        });


        mBinding.cbFerry.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                excludes.add(DirectionsCriteria.EXCLUDE_FERRY);
            } else {
                excludes.remove(DirectionsCriteria.EXCLUDE_FERRY);
            }
            MapplsDirectionWidgetSetting.getInstance().setExcludes(excludes);
        });
        mBinding.cbToll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                excludes.add(DirectionsCriteria.EXCLUDE_TOLL);
            } else {
                excludes.remove(DirectionsCriteria.EXCLUDE_TOLL);
            }
            MapplsDirectionWidgetSetting.getInstance().setExcludes(excludes);
        });
        mBinding.cbMotorway.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                excludes.add(DirectionsCriteria.EXCLUDE_MOTORWAY);
            } else {
                excludes.remove(DirectionsCriteria.EXCLUDE_MOTORWAY);
            }
            MapplsDirectionWidgetSetting.getInstance().setExcludes(excludes);
        });

        mBinding.rgVertical.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == mBinding.rbTop.getId()) {
                MapplsDirectionWidgetSetting.getInstance().setSignatureVertical(PlaceOptions.GRAVITY_TOP);
            } else {

                MapplsDirectionWidgetSetting.getInstance().setSignatureVertical(PlaceOptions.GRAVITY_BOTTOM);
            }
        });

        mBinding.rgHorizontal.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_left:
                    MapplsDirectionWidgetSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_LEFT);
                    break;
                case R.id.rb_center:
                    MapplsDirectionWidgetSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_CENTER);
                    break;
                case R.id.rb_right:
                    MapplsDirectionWidgetSetting.getInstance().setSignatureHorizontal(PlaceOptions.GRAVITY_RIGHT);
                    break;
            }
        });

        mBinding.rgLogoSize.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_small:
                    MapplsDirectionWidgetSetting.getInstance().setLogoSize(PlaceOptions.SIZE_SMALL);
                    break;
                case R.id.rb_medium:
                    MapplsDirectionWidgetSetting.getInstance().setLogoSize(PlaceOptions.SIZE_MEDIUM);
                    break;
                case R.id.rb_large:
                    MapplsDirectionWidgetSetting.getInstance().setLogoSize(PlaceOptions.SIZE_LARGE);
                    break;
            }
        });

        mBinding.btnSaveLocation.setOnClickListener(v -> {
            if ((!TextUtils.isEmpty(mBinding.etLatitude.getText().toString().trim())) && (!TextUtils.isEmpty(mBinding.etLongitude.getText().toString().trim()))) {
                MapplsDirectionWidgetSetting.getInstance().setLocation(Point.fromLngLat(Double.parseDouble(mBinding.etLongitude.getText().toString().trim()), Double.parseDouble(mBinding.etLatitude.getText().toString().trim())));
            } else {
                MapplsDirectionWidgetSetting.getInstance().setLocation(null);
            }
            Toast.makeText(DirectionWidgetSettingActivity.this, "Location save successfully", Toast.LENGTH_SHORT).show();
        });

        mBinding.btnSaveHistoryCount.setOnClickListener(v -> {
            if ((!TextUtils.isEmpty(mBinding.etHistoryCount.getText().toString()))) {
                MapplsDirectionWidgetSetting.getInstance().setHistoryCount(Integer.parseInt(mBinding.etHistoryCount.getText().toString()));
            } else {
                MapplsDirectionWidgetSetting.getInstance().setHistoryCount(null);
            }
            Toast.makeText(DirectionWidgetSettingActivity.this, "History Count save successfully", Toast.LENGTH_SHORT).show();
        });

        mBinding.btnSaveFilter.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mBinding.etFilter.getText().toString().trim())) {
                MapplsDirectionWidgetSetting.getInstance().setFilter(mBinding.etFilter.getText().toString().trim());
            } else {
                MapplsDirectionWidgetSetting.getInstance().setFilter(null);
            }
            Toast.makeText(DirectionWidgetSettingActivity.this, "Filter save successfully", Toast.LENGTH_SHORT).show();
        });

        mBinding.cbEnableHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsDirectionWidgetSetting.getInstance().setEnableHistory(isChecked);
            if (isChecked) {
                mBinding.historyCountLayout.setVisibility(View.VISIBLE);
            } else {
                mBinding.historyCountLayout.setVisibility(View.GONE);
            }

        });
        mBinding.btnPodClear.setOnClickListener(v -> {
            mBinding.rgPod.clearCheck();
            MapplsDirectionWidgetSetting.getInstance().setPod(null);
        });

        mBinding.rgResources.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_route:
                    MapplsDirectionWidgetSetting.getInstance().setResource(DirectionsCriteria.RESOURCE_ROUTE);
                    break;
                case R.id.rb_route_eta:
                    MapplsDirectionWidgetSetting.getInstance().setResource(DirectionsCriteria.RESOURCE_ROUTE_ETA);
                    break;
                case R.id.rb_route_traffic:
                    MapplsDirectionWidgetSetting.getInstance().setResource(DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC);
                    break;
            }
        });
        mBinding.rgProfiles.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_driving:
                    MapplsDirectionWidgetSetting.getInstance().setProfile(DirectionsCriteria.PROFILE_DRIVING);
                    break;
                case R.id.rb_walking:
                    MapplsDirectionWidgetSetting.getInstance().setProfile(DirectionsCriteria.PROFILE_WALKING);
                    break;
                case R.id.rb_biking:
                    MapplsDirectionWidgetSetting.getInstance().setProfile(DirectionsCriteria.PROFILE_BIKING);
                    break;
                case R.id.rb_trucking:
                    MapplsDirectionWidgetSetting.getInstance().setProfile(DirectionsCriteria.PROFILE_TRUCKING);
                    break;
            }
        });
        mBinding.rgOverviews.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_full:
                    MapplsDirectionWidgetSetting.getInstance().setOverview(DirectionsCriteria.OVERVIEW_FULL);
                    break;
                case R.id.rb_none:
                    MapplsDirectionWidgetSetting.getInstance().setOverview(DirectionsCriteria.OVERVIEW_FALSE);
                    break;
                case R.id.rb_simplified:
                    MapplsDirectionWidgetSetting.getInstance().setOverview(DirectionsCriteria.OVERVIEW_SIMPLIFIED);
                    break;
            }
        });

        mBinding.rgPod.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_city:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_CITY);
                    break;
                case R.id.rb_state:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_STATE);
                    break;
                case R.id.rb_sub_district:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_DISTRICT);
                    break;
                case R.id.rb_district:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_DISTRICT);
                    break;
                case R.id.rb_sub_locality:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_LOCALITY);
                    break;
                case R.id.rb_sub_sub_locality:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY);
                    break;
                case R.id.rb_locality:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_LOCALITY);
                    break;
                case R.id.rb_village:
                    MapplsDirectionWidgetSetting.getInstance().setPod(AutoSuggestCriteria.POD_VILLAGE);
                    break;
            }
        });


        mBinding.btnHint.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mBinding.etHint.getText().toString().trim())) {
                MapplsDirectionWidgetSetting.getInstance().setHint(mBinding.etHint.getText().toString().trim());
                Toast.makeText(DirectionWidgetSettingActivity.this, "Hint save successfully", Toast.LENGTH_SHORT).show();
            } else {
                mBinding.etHint.setText(MapplsDirectionWidgetSetting.getInstance().getHint());
                Toast.makeText(DirectionWidgetSettingActivity.this, "Hint is mandatory", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btnZoom.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mBinding.etZoom.getText().toString().trim())) {
                MapplsDirectionWidgetSetting.getInstance().setZoom(Double.parseDouble(mBinding.etZoom.getText().toString().trim()));
                Toast.makeText(DirectionWidgetSettingActivity.this, "zoom save successfully", Toast.LENGTH_SHORT).show();
            }
        });


        mBinding.backgroundRG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_white:
                    MapplsDirectionWidgetSetting.getInstance().setBackgroundColor(android.R.color.white);
                    break;
                case R.id.rb_black:
                    MapplsDirectionWidgetSetting.getInstance().setBackgroundColor(android.R.color.black);
                    break;
                case R.id.rb_red:
                    MapplsDirectionWidgetSetting.getInstance().setBackgroundColor(android.R.color.holo_red_light);
                    break;
                case R.id.rb_green:
                    MapplsDirectionWidgetSetting.getInstance().setBackgroundColor(android.R.color.holo_green_dark);
                    break;
                case R.id.rb_blue:
                    MapplsDirectionWidgetSetting.getInstance().setBackgroundColor(android.R.color.holo_blue_bright);
                    break;
            }
        });
        mBinding.toolbarRG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_white_toolbar:
                    MapplsDirectionWidgetSetting.getInstance().setToolbarColor(android.R.color.white);
                    break;
                case R.id.rb_black_toolbar:
                    MapplsDirectionWidgetSetting.getInstance().setToolbarColor(android.R.color.black);
                    break;
                case R.id.rb_red_toolbar:
                    MapplsDirectionWidgetSetting.getInstance().setToolbarColor(android.R.color.holo_red_light);
                    break;
                case R.id.rb_green_toolbar:
                    MapplsDirectionWidgetSetting.getInstance().setToolbarColor(android.R.color.holo_green_dark);
                    break;
                case R.id.rb_blue_toolbar:
                    MapplsDirectionWidgetSetting.getInstance().setToolbarColor(android.R.color.holo_blue_bright);
                    break;
            }
        });
        mBinding.cbPoiSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsDirectionWidgetSetting.getInstance().setShowPOISearch(isChecked);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void initSettings() {


        mBinding.tokenizeAddressBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsDirectionWidgetSetting.getInstance().setTokenizeAddress(isChecked);
        });
        mBinding.showStartNavigationBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsDirectionWidgetSetting.getInstance().setShowStartNavigation(isChecked);
        });
        mBinding.showStepsBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsDirectionWidgetSetting.getInstance().setSteps(isChecked);
        });
        mBinding.showAlternativeBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsDirectionWidgetSetting.getInstance().setShowAlternative(isChecked);
        });

        mBinding.cbDefault.setChecked(MapplsDirectionWidgetSetting.getInstance().isDefault());
        mBinding.disableView.setVisibility(MapplsDirectionWidgetSetting.getInstance().isDefault() ? View.VISIBLE : View.GONE);

        if (MapplsDirectionWidgetSetting.getInstance().getHistoryCount() != null) {
            mBinding.etHistoryCount.setText(MapplsDirectionWidgetSetting.getInstance().getHistoryCount().toString());
        }

        if (MapplsDirectionWidgetSetting.getInstance().getSignatureVertical() == PlaceOptions.GRAVITY_TOP) {
            mBinding.rgVertical.check(mBinding.rbTop.getId());
        } else {
            mBinding.rgVertical.check(mBinding.rbBottom.getId());
        }

        if (MapplsDirectionWidgetSetting.getInstance().getSignatureHorizontal() == PlaceOptions.GRAVITY_LEFT) {
            mBinding.rgHorizontal.check(mBinding.rbLeft.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getSignatureHorizontal() == PlaceOptions.GRAVITY_CENTER) {
            mBinding.rgHorizontal.check(mBinding.rbCenter.getId());
        } else {
            mBinding.rgHorizontal.check(mBinding.rbRight.getId());
        }

        if (MapplsDirectionWidgetSetting.getInstance().getLogoSize() == PlaceOptions.SIZE_SMALL) {
            mBinding.rgLogoSize.check(R.id.rb_small);
        } else if (MapplsDirectionWidgetSetting.getInstance().getLogoSize() == PlaceOptions.SIZE_MEDIUM) {
            mBinding.rgLogoSize.check(R.id.rb_medium);
        } else {
            mBinding.rgLogoSize.check(R.id.rb_large);
        }

        if (MapplsDirectionWidgetSetting.getInstance().getLocation() != null) {
            mBinding.etLatitude.setText(String.valueOf(MapplsDirectionWidgetSetting.getInstance().getLocation().latitude()));
            mBinding.etLongitude.setText(String.valueOf(MapplsDirectionWidgetSetting.getInstance().getLocation().longitude()));
        }
        if (MapplsDirectionWidgetSetting.getInstance().getFilter() != null) {
            mBinding.etFilter.setText(MapplsDirectionWidgetSetting.getInstance().getFilter());
        }

        mBinding.cbEnableHistory.setChecked(MapplsDirectionWidgetSetting.getInstance().isEnableHistory());
        if (!MapplsDirectionWidgetSetting.getInstance().isEnableHistory()) {
            mBinding.historyCountLayout.setVisibility(View.GONE);
        } else {
            mBinding.historyCountLayout.setVisibility(View.VISIBLE);
        }

        if (MapplsDirectionWidgetSetting.getInstance().getResource() != null) {
            if (MapplsDirectionWidgetSetting.getInstance().getResource().equalsIgnoreCase(DirectionsCriteria.RESOURCE_ROUTE)) {
                mBinding.rgResources.check(mBinding.rbRoute.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getResource().equalsIgnoreCase(DirectionsCriteria.RESOURCE_ROUTE_ETA)) {
                mBinding.rgResources.check(mBinding.rbRouteEta.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getResource().equalsIgnoreCase(DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC)) {
                mBinding.rgResources.check(mBinding.rbRouteTraffic.getId());
            }
        }

        if (MapplsDirectionWidgetSetting.getInstance().getProfile() != null) {
            if (MapplsDirectionWidgetSetting.getInstance().getProfile().equalsIgnoreCase(DirectionsCriteria.PROFILE_DRIVING)) {
                mBinding.rgProfiles.check(mBinding.rbDriving.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getProfile().equalsIgnoreCase(DirectionsCriteria.PROFILE_WALKING)) {
                mBinding.rgProfiles.check(mBinding.rbWalking.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getProfile().equalsIgnoreCase(DirectionsCriteria.PROFILE_BIKING)) {
                mBinding.rgProfiles.check(mBinding.rbBiking.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getProfile().equalsIgnoreCase(DirectionsCriteria.PROFILE_TRUCKING)) {
                mBinding.rgProfiles.check(mBinding.rbTrucking.getId());
            }
        }

        if (MapplsDirectionWidgetSetting.getInstance().getOverview() != null) {
            if (MapplsDirectionWidgetSetting.getInstance().getOverview().equalsIgnoreCase(DirectionsCriteria.OVERVIEW_FULL)) {
                mBinding.rgOverviews.check(mBinding.rbFull.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getOverview().equalsIgnoreCase(DirectionsCriteria.OVERVIEW_FALSE)) {
                mBinding.rgOverviews.check(mBinding.rbNone.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getOverview().equalsIgnoreCase(DirectionsCriteria.OVERVIEW_SIMPLIFIED)) {
                mBinding.rgOverviews.check(mBinding.rbSimplified.getId());
            }
        }

        if (MapplsDirectionWidgetSetting.getInstance().getPod() != null) {
            if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_CITY)) {
                mBinding.rgPod.check(mBinding.rbCity.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_DISTRICT)) {
                mBinding.rgPod.check(mBinding.rbDistrict.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbLocality.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_STATE)) {
                mBinding.rgPod.check(mBinding.rbState.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_DISTRICT)) {
                mBinding.rgPod.check(mBinding.rbSubDistrict.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbSubLocality.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_SUB_SUB_LOCALITY)) {
                mBinding.rgPod.check(mBinding.rbSubSubLocality.getId());
            } else if (MapplsDirectionWidgetSetting.getInstance().getPod().equalsIgnoreCase(AutoSuggestCriteria.POD_VILLAGE)) {
                mBinding.rgPod.check(mBinding.rbVillage.getId());
            }
        } else {
            mBinding.rgPod.clearCheck();
        }

        mBinding.etHint.setText(MapplsDirectionWidgetSetting.getInstance().getHint());
        if (MapplsDirectionWidgetSetting.getInstance().getZoom() != null) {
            mBinding.etZoom.setText(MapplsDirectionWidgetSetting.getInstance().getZoom().toString());

        }
        // mBinding.cbEnableTextSearch.setChecked(MapplsPlacePickerSetting.getInstance().isEnableTextSearch());


        if (MapplsDirectionWidgetSetting.getInstance().getBackgroundColor() == android.R.color.white) {
            mBinding.backgroundRG.check(mBinding.rbWhite.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getBackgroundColor() == android.R.color.black) {
            mBinding.backgroundRG.check(mBinding.rbBlack.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getBackgroundColor() == android.R.color.holo_red_light) {
            mBinding.backgroundRG.check(mBinding.rbRed.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getBackgroundColor() == android.R.color.holo_green_dark) {
            mBinding.backgroundRG.check(mBinding.rbGreen.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getBackgroundColor() == android.R.color.holo_blue_bright) {
            mBinding.backgroundRG.check(mBinding.rbBlue.getId());
        }


        if (MapplsDirectionWidgetSetting.getInstance().getToolbarColor() == android.R.color.white) {
            mBinding.toolbarRG.check(mBinding.rbWhiteToolbar.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getToolbarColor() == android.R.color.black) {
            mBinding.toolbarRG.check(mBinding.rbBlackToolbar.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getToolbarColor() == android.R.color.holo_red_light) {
            mBinding.toolbarRG.check(mBinding.rbRedToolbar.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getToolbarColor() == android.R.color.holo_green_dark) {
            mBinding.toolbarRG.check(mBinding.rbGreenToolbar.getId());
        } else if (MapplsDirectionWidgetSetting.getInstance().getToolbarColor() == android.R.color.holo_blue_bright) {
            mBinding.toolbarRG.check(mBinding.rbBlueToolbar.getId());
        }
        mBinding.cbPoiSearch.setChecked(MapplsDirectionWidgetSetting.getInstance().isShowPOISearch());
    }

}