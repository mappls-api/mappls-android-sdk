package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.settings.MapplsPlaceWidgetSetting;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.annotations.MarkerOptions;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.autosuggest.model.ELocation;
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby;
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult;

import java.util.ArrayList;

public class FullModeFragmentAutocompleteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private MapplsMap mapplsMap;
    private TextView search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_mode_fragment);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapplsMap != null) {
                    PlaceOptions placeOptions = PlaceOptions.builder()
                            .debounce(MapplsPlaceWidgetSetting.getInstance().getDeBounce())
                            .location(MapplsPlaceWidgetSetting.getInstance().getLocation())
                            .filter(MapplsPlaceWidgetSetting.getInstance().getFilter())
                            .hint(MapplsPlaceWidgetSetting.getInstance().getHint())
                            .enableTextSearch(MapplsPlaceWidgetSetting.getInstance().isEnableTextSearch())
                            .pod(MapplsPlaceWidgetSetting.getInstance().getPod())
                            .saveHistory(MapplsPlaceWidgetSetting.getInstance().isEnableHistory())
                            .isShowCurrentLocation(MapplsPlaceWidgetSetting.getInstance().isEnableLocation())
                            .attributionHorizontalAlignment(MapplsPlaceWidgetSetting.getInstance().getSignatureVertical())
                            .attributionVerticalAlignment(MapplsPlaceWidgetSetting.getInstance().getSignatureHorizontal())
                            .logoSize(MapplsPlaceWidgetSetting.getInstance().getLogoSize())
                            .backgroundColor(getResources().getColor(MapplsPlaceWidgetSetting.getInstance().getBackgroundColor()))
                            .toolbarColor(getResources().getColor(MapplsPlaceWidgetSetting.getInstance().getToolbarColor()))
                            .hyperLocal(MapplsPlaceWidgetSetting.getInstance().isEnableHyperLocal())
                            .bridge(MapplsPlaceWidgetSetting.getInstance().isEnableBridge())
                            .build();
                    PlaceAutocompleteFragment placeAutocompleteFragment;
                    if (MapplsPlaceWidgetSetting.getInstance().isDefault()) {
                        placeAutocompleteFragment = PlaceAutocompleteFragment.newInstance();
                    } else {
                        placeAutocompleteFragment = PlaceAutocompleteFragment.newInstance(placeOptions);
                    }
                    placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                        @Override
                        public void onPlaceSelected(ELocation eLocation) {

                            search.setText(eLocation.placeName);
                            if (mapplsMap != null) {
                                mapplsMap.clear();
                                if (eLocation.latitude != null && eLocation.longitude != null) {
                                    LatLng latLng = new LatLng(eLocation.latitude, eLocation.longitude);
                                    mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                                    mapplsMap.addMarker(new MarkerOptions().position(latLng).title(eLocation.placeName).snippet(eLocation.placeAddress));
                                }
                                getSupportFragmentManager().popBackStack(PlaceAutocompleteFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                        }

                        @Override
                        public void onCancel() {
                            getSupportFragmentManager().popBackStack(PlaceAutocompleteFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

                        @Override
                        public void requestForCurrentLocation() {
                            Toast.makeText(FullModeFragmentAutocompleteActivity.this, "Please provide current location", Toast.LENGTH_SHORT).show();

                        }
                    });
                    placeAutocompleteFragment.setSuggestedSearchSelectionListener(suggestedSearchAtlas -> {
                        callHateOs(suggestedSearchAtlas.hyperLink);
                        getSupportFragmentManager().popBackStack(PlaceAutocompleteFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    });


                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment.class.getSimpleName())
                            .addToBackStack(PlaceAutocompleteFragment.class.getSimpleName())
                            .commit();
                } else {
                    Toast.makeText(FullModeFragmentAutocompleteActivity.this, "Please wait map is loading", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(FullModeFragmentAutocompleteActivity.this, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(FullModeFragmentAutocompleteActivity.this, s, Toast.LENGTH_SHORT).show();

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
