package com.mappls.sdk.demo.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.settings.MapplsPlacePickerSetting;
import com.mappls.sdk.demo.java.settingscreen.PlacePickerSettingsActivity;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.plugins.places.placepicker.PlacePicker;
import com.mappls.sdk.plugins.places.placepicker.model.PlacePickerOptions;
import com.mappls.sdk.services.api.Place;

public class PickerActivity extends AppCompatActivity {
    private TextView tvSelectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        tvSelectedPlace = findViewById(R.id.selected_place);
        Button button = findViewById(R.id.place_picker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder =  new PlacePicker.IntentBuilder();

                if (!MapplsPlacePickerSetting.getInstance().isDefault()){

                    PlaceOptions options = PlaceOptions.builder()
                            .zoom(MapplsPlacePickerSetting.getInstance().getZoom())
                            .hint(MapplsPlacePickerSetting.getInstance().getHint())
                            .location(MapplsPlacePickerSetting.getInstance().getLocation())
                            .filter(MapplsPlacePickerSetting.getInstance().getFilter())
                            .saveHistory(MapplsPlacePickerSetting.getInstance().isEnableHistory())
                            .pod(MapplsPlacePickerSetting.getInstance().getPod())
                            .attributionHorizontalAlignment(MapplsPlacePickerSetting.getInstance().getSignatureVertical())
                            .attributionVerticalAlignment(MapplsPlacePickerSetting.getInstance().getSignatureHorizontal())
                            .logoSize(MapplsPlacePickerSetting.getInstance().getLogoSize())
                            .tokenizeAddress(MapplsPlacePickerSetting.getInstance().isTokenizeAddress())
                            .historyCount(MapplsPlacePickerSetting.getInstance().getHistoryCount())
                            .backgroundColor(getResources().getColor(MapplsPlacePickerSetting.getInstance().getBackgroundColor()))
                            .toolbarColor(getResources().getColor(MapplsPlacePickerSetting.getInstance().getToolbarColor()))
                            .build();

                    intentBuilder.placeOptions(PlacePickerOptions.builder()
                            .toolbarColor(MapplsPlacePickerSetting.getInstance().getPickerToolbarColor())
                            .includeDeviceLocationButton(MapplsPlacePickerSetting.getInstance().isIncludeDeviceLocation())
                            .includeSearch(MapplsPlacePickerSetting.getInstance().isIncludeSearch())
                            .searchPlaceOption(options)
                            .statingCameraPosition(new CameraPosition.Builder()
                                    .target(new LatLng(27.00, 75.0)).zoom(16).build())
                            .build());
                }else {
                    intentBuilder.placeOptions(PlacePickerOptions.builder()
                            .statingCameraPosition(new CameraPosition.Builder()
                                    .target(new LatLng(27.00, 75.0)).zoom(16).build())
                            .build());
                }

                Intent intent = intentBuilder.build(PickerActivity.this);
                startActivityForResult(intent, 101);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {

            Place place = PlacePicker.getPlace(data);
            tvSelectedPlace.setText(place.getFormattedAddress());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.widget_setting_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.widget_setting) {
            startActivity(new Intent(this, PlacePickerSettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);

    }
}
