package com.mappls.sdk.demo.java.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.settings.MapplsPlaceWidgetSetting;
import com.mappls.sdk.demo.java.utils.AddFavoriteManager;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.annotations.MarkerOptions;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mappls.sdk.plugins.places.autocomplete.model.MapplsFavoritePlace;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.autosuggest.model.ELocation;
import com.mappls.sdk.services.api.autosuggest.model.SuggestedSearchAtlas;
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby;
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult;

import java.util.ArrayList;

public class FullModeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private MapplsMap mapplsMap;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_mode_fragment);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        textView = findViewById(R.id.search);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapplsMap != null) {
                    PlaceOptions placeOptions = PlaceOptions.builder()
                            .favoritePlaces(AddFavoriteManager.getInstance().getList())
                            .historyCount(MapplsPlaceWidgetSetting.getInstance().getHistoryCount())
                            .debounce(MapplsPlaceWidgetSetting.getInstance().getDeBounce())
                            .location(MapplsPlaceWidgetSetting.getInstance().getLocation())
                            .filter(MapplsPlaceWidgetSetting.getInstance().getFilter())
                            .saveHistory(MapplsPlaceWidgetSetting.getInstance().isEnableHistory())
                            .isShowCurrentLocation(MapplsPlaceWidgetSetting.getInstance().isEnableLocation())
                            .pod(MapplsPlaceWidgetSetting.getInstance().getPod())
                            .hint(MapplsPlaceWidgetSetting.getInstance().getHint())
                            .enableTextSearch(MapplsPlaceWidgetSetting.getInstance().isEnableTextSearch())
                            .attributionHorizontalAlignment(MapplsPlaceWidgetSetting.getInstance().getSignatureVertical())
                            .attributionVerticalAlignment(MapplsPlaceWidgetSetting.getInstance().getSignatureHorizontal())
                            .logoSize(MapplsPlaceWidgetSetting.getInstance().getLogoSize())
                            .backgroundColor(getResources().getColor(MapplsPlaceWidgetSetting.getInstance().getBackgroundColor()))
                            .toolbarColor(getResources().getColor(MapplsPlaceWidgetSetting.getInstance().getToolbarColor()))
                            .hyperLocal(MapplsPlaceWidgetSetting.getInstance().isEnableHyperLocal())
                            .bridge(MapplsPlaceWidgetSetting.getInstance().isEnableBridge())
                            .build();

                    PlaceAutocomplete.IntentBuilder builder = new PlaceAutocomplete.IntentBuilder();
                    if(!MapplsPlaceWidgetSetting.getInstance().isDefault()) {
                        builder.placeOptions(placeOptions);
                    }
                    Intent placeAutocomplete = builder.build(FullModeActivity.this);

                    startActivityForResult(placeAutocomplete, 101);
                } else {
                    Toast.makeText(FullModeActivity.this, "Please wait map is loading", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void callHateOs(String hyperlink) {
        MapplsHateosNearby hateosNearby = MapplsHateosNearby.builder()
                .hyperlink(hyperlink)
                .build();
        MapplsHateosNearbyManager.newInstance(hateosNearby).call(new OnResponseCallback<NearbyAtlasResponse>() {
            @Override
            public void onSuccess(NearbyAtlasResponse nearbyAtlasResponse) {
                if (nearbyAtlasResponse != null) {
                    ArrayList<NearbyAtlasResult> nearByList = nearbyAtlasResponse.getSuggestedLocations();
                    if (nearByList.size() > 0) {
                        addMarker(nearByList);
                    }
                } else {
                    Toast.makeText(FullModeActivity.this, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(FullModeActivity.this, s, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addMarker(ArrayList<NearbyAtlasResult> nearByList) {
        mapplsMap.clear();
        for (NearbyAtlasResult marker : nearByList) {
            if (marker.getLatitude() != null && marker.getLongitude() != null) {
                mapplsMap.addMarker(new MarkerOptions().position(new LatLng(marker.getLatitude(), marker.getLongitude())).title(marker.getPlaceName()));
            } else {
                mapplsMap.addMarker(new MarkerOptions().mapplsPin(marker.mapplsPin).title(marker.getPlaceName()));
            }
        }

    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;



        mapplsMap.setMinZoomPreference(4);
        mapplsMap.setMaxZoomPreference(18);
        mapplsMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(28, 77)).zoom(4).build());
//        mapplsMap.addMarker(new MarkerOptions().mapplsPin(marker.mapplsPin).title(marker.getPlaceName()));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    ELocation eLocation = PlaceAutocomplete.getPlace(data);
                    if (eLocation != null) {
                        if (mapplsMap != null) {
                            textView.setText(eLocation.placeName);
                            mapplsMap.clear();
                            LatLng latLng = new LatLng(eLocation.latitude, eLocation.longitude);
                            mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                            mapplsMap.addMarker(new MarkerOptions().position(latLng).title(eLocation.placeName).snippet(eLocation.placeAddress));
                            if (MapplsPlaceWidgetSetting.getInstance().isEnableShowFavorite()) {
                                addFavoriteDialog(eLocation);
                            }

                        }
                    } else if (PlaceAutocomplete.getSuggestedSearch(data) != null) {
                        SuggestedSearchAtlas suggestedSearchAtlas = PlaceAutocomplete.getSuggestedSearch(data);
                        callHateOs(suggestedSearchAtlas.hyperLink);
                    } else if (PlaceAutocomplete.getFavoritePlace(data) != null) {
                        MapplsFavoritePlace favoritePlace = PlaceAutocomplete.getFavoritePlace(data);
                        textView.setText(favoritePlace.getPlaceName());
                        mapplsMap.clear();
                        LatLng latLng = new LatLng(favoritePlace.getLatitude(), favoritePlace.getLongitude());
                        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mapplsMap.addMarker(new MarkerOptions().position(latLng).title(favoritePlace.getPlaceName()).snippet(favoritePlace.getPlaceAddress()));
                    } else if (PlaceAutocomplete.isRequestForCurrentLocation(data)) {
                        Toast.makeText(FullModeActivity.this, "Please provide current location", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void addFavoriteDialog(ELocation eLocation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to add this place as favorite ?");
        builder.setTitle("Add Favorite");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            MapplsFavoritePlace favoritePlace = new MapplsFavoritePlace(eLocation.placeName, eLocation.placeAddress, eLocation.latitude, eLocation.longitude);
            favoritePlace.setMapplsPin(eLocation.mapplsPin);
            AddFavoriteManager.getInstance().addToArray(favoritePlace);
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {

            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onMapError(int i, String s) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
