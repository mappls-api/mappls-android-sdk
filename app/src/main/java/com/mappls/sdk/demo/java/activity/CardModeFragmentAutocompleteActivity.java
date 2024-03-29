package com.mappls.sdk.demo.java.activity;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.mappls.sdk.plugins.places.autocomplete.model.MapplsFavoritePlace;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.mappls.sdk.plugins.places.autocomplete.ui.SuggestedSearchSelectionListener;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.autosuggest.model.ELocation;
import com.mappls.sdk.services.api.autosuggest.model.SuggestedSearchAtlas;
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby;
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult;

import java.util.ArrayList;

public class CardModeFragmentAutocompleteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapplsMap mapplsMap;
    private AddFavoriteManager addFavoriteSingleton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }


    private void callAutoComplete() {
        PlaceOptions placeOptions;
        if (MapplsPlaceWidgetSetting.getInstance().isDefault()) {
            placeOptions = PlaceOptions.builder().build(PlaceOptions.MODE_CARDS);
        } else {
            placeOptions = PlaceOptions.builder()
                    .favoritePlaces(AddFavoriteManager.getInstance().getList())
                    .historyCount(MapplsPlaceWidgetSetting.getInstance().getHistoryCount())
                    .debounce(MapplsPlaceWidgetSetting.getInstance().getDeBounce())
                    .location(MapplsPlaceWidgetSetting.getInstance().getLocation())
                    .filter(MapplsPlaceWidgetSetting.getInstance().getFilter())
                    .saveHistory(MapplsPlaceWidgetSetting.getInstance().isEnableHistory())
                    .enableTextSearch(MapplsPlaceWidgetSetting.getInstance().isEnableTextSearch())
                    .isShowCurrentLocation(MapplsPlaceWidgetSetting.getInstance().isEnableLocation())
                    .hint(MapplsPlaceWidgetSetting.getInstance().getHint())
                    .pod(MapplsPlaceWidgetSetting.getInstance().getPod())
                    .attributionHorizontalAlignment(MapplsPlaceWidgetSetting.getInstance().getSignatureVertical())
                    .attributionVerticalAlignment(MapplsPlaceWidgetSetting.getInstance().getSignatureHorizontal())
                    .logoSize(MapplsPlaceWidgetSetting.getInstance().getLogoSize())
                    .bridge(MapplsPlaceWidgetSetting.getInstance().isEnableBridge())
                    .hyperLocal(MapplsPlaceWidgetSetting.getInstance().isEnableHyperLocal())
                    .build(PlaceOptions.MODE_CARDS);
        }

        PlaceAutocompleteFragment placeAutocompleteFragment = PlaceAutocompleteFragment.newInstance(placeOptions);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(ELocation eLocation) {

                if (mapplsMap != null) {
                    mapplsMap.clear();
                    if(eLocation.latitude != null && eLocation.longitude != null){
                        LatLng latLng = new LatLng(eLocation.latitude, eLocation.longitude);
                        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mapplsMap.addMarker(new MarkerOptions().position(latLng).title(eLocation.placeName).snippet(eLocation.placeAddress));
                    }else {
                        mapplsMap.addMarker(new MarkerOptions().mapplsPin(eLocation.mapplsPin).title(eLocation.placeName).snippet(eLocation.placeAddress));
                    }
                    if(MapplsPlaceWidgetSetting.getInstance().isEnableShowFavorite()) {
                        addFavoriteDialog(eLocation);
                    }
                }


            }

            @Override
            public void onFavoritePlaceSelected(MapplsFavoritePlace mapplsFavoritePlace) {
                if (mapplsMap != null) {
                    mapplsMap.clear();
                    if(mapplsFavoritePlace.getLatitude() != null && mapplsFavoritePlace.getLongitude() != null){
                        LatLng latLng = new LatLng(mapplsFavoritePlace.getLatitude(), mapplsFavoritePlace.getLongitude());
                        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mapplsMap.addMarker(new MarkerOptions().position(latLng).title(mapplsFavoritePlace.getPlaceName()).snippet(mapplsFavoritePlace.getPlaceAddress()));
                    }else {
                        mapplsMap.addMarker(new MarkerOptions().mapplsPin(mapplsFavoritePlace.getMapplsPin()).title(mapplsFavoritePlace.getPlaceName()).snippet(mapplsFavoritePlace.getPlaceAddress()));
                    }
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void requestForCurrentLocation() {
                Toast.makeText(CardModeFragmentAutocompleteActivity.this, "Please provide current location", Toast.LENGTH_SHORT).show();

            }
        });

        placeAutocompleteFragment.setSuggestedSearchSelectionListener(new SuggestedSearchSelectionListener() {
            @Override
            public void onSuggestedSearchSelected(SuggestedSearchAtlas suggestedSearchAtlas) {
                callHateOs(suggestedSearchAtlas.hyperLink);
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment.class.getSimpleName())
                .commit();
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
                    Toast.makeText(CardModeFragmentAutocompleteActivity.this, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(CardModeFragmentAutocompleteActivity.this, s, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addFavoriteDialog(ELocation eLocation){
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
        callAutoComplete();


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
