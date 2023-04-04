package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityDirectionWidgetBinding;
import com.mappls.sdk.demo.java.settings.MapplsDirectionWidgetSetting;
import com.mappls.sdk.direction.ui.DirectionCallback;
import com.mappls.sdk.direction.ui.DirectionFragment;
import com.mappls.sdk.direction.ui.model.DirectionOptions;
import com.mappls.sdk.direction.ui.model.DirectionPoint;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.services.api.directions.models.DirectionsResponse;

import java.util.List;

public class DirectionWidgetActivity extends AppCompatActivity {

    private ActivityDirectionWidgetBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_direction_widget);

        DirectionOptions.Builder optionsBuilder = DirectionOptions.builder();
        if (!MapplsDirectionWidgetSetting.getInstance().isDefault()){

            PlaceOptions options = PlaceOptions.builder()
                    .zoom(MapplsDirectionWidgetSetting.getInstance().getZoom())
                    .hint(MapplsDirectionWidgetSetting.getInstance().getHint())
                    .location(MapplsDirectionWidgetSetting.getInstance().getLocation())
                    .filter(MapplsDirectionWidgetSetting.getInstance().getFilter())
                    .saveHistory(MapplsDirectionWidgetSetting.getInstance().isEnableHistory())
                    .pod(MapplsDirectionWidgetSetting.getInstance().getPod())
                    .attributionHorizontalAlignment(MapplsDirectionWidgetSetting.getInstance().getSignatureVertical())
                    .attributionVerticalAlignment(MapplsDirectionWidgetSetting.getInstance().getSignatureHorizontal())
                    .logoSize(MapplsDirectionWidgetSetting.getInstance().getLogoSize())
                    .tokenizeAddress(MapplsDirectionWidgetSetting.getInstance().isTokenizeAddress())
                    .historyCount(MapplsDirectionWidgetSetting.getInstance().getHistoryCount())
                    .backgroundColor(getResources().getColor(MapplsDirectionWidgetSetting.getInstance().getBackgroundColor()))
                    .toolbarColor(getResources().getColor(MapplsDirectionWidgetSetting.getInstance().getToolbarColor()))
                    .build();


            optionsBuilder.searchPlaceOption(options)
                    .showDefaultMap(true)
                    .showStartNavigation(MapplsDirectionWidgetSetting.getInstance().isShowStartNavigation())
                    .steps(MapplsDirectionWidgetSetting.getInstance().isSteps())
                    .resource(MapplsDirectionWidgetSetting.getInstance().getResource())
                    .profile(MapplsDirectionWidgetSetting.getInstance().getProfile())
                    .excludes(MapplsDirectionWidgetSetting.getInstance().getExcludes())
                    .overview(MapplsDirectionWidgetSetting.getInstance().getOverview())
            .showAlternative(MapplsDirectionWidgetSetting.getInstance().isShowAlternative())
            .searchAlongRoute(MapplsDirectionWidgetSetting.getInstance().isShowPOISearch());
        }

        DirectionFragment directionFragment = DirectionFragment.newInstance(optionsBuilder.build());



        getSupportFragmentManager().
                beginTransaction().
                add(mBinding.fragmentConatiner.getId(), directionFragment, DirectionFragment.class.getSimpleName()).
                commit();

        directionFragment.setDirectionCallback(new DirectionCallback() {
            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onStartNavigation(DirectionPoint directionPoint, DirectionPoint directionPoint1, List<DirectionPoint> list, DirectionsResponse directionsResponse, int i) {
                Toast.makeText(DirectionWidgetActivity.this, "On Navigation Start", Toast.LENGTH_SHORT).show();
            }
        });
    }
}