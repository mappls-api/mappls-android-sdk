package com.mappls.sdk.demo.java.activity;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.mappls.sdk.demo.databinding.ActivityNearbyReportBinding;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.annotations.MarkerOptions;
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.autosuggest.model.ELocation;
import com.mappls.sdk.services.api.event.nearby.MapplsNearbyReport;
import com.mappls.sdk.services.api.event.nearby.MapplsNearbyReportManager;
import com.mappls.sdk.services.api.event.nearby.model.NearbyReport;
import com.mappls.sdk.services.api.event.nearby.model.NearbyReportResponse;

public class NearbyReportActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityNearbyReportBinding mBinding;
    private MapplsMap mapplsMap;
    private TransparentProgressDialog transparentProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nearby_report);
        transparentProgressDialog = new TransparentProgressDialog(this, R.drawable.circle_loader, "");

        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.mapView.getMapAsync(this);
        mBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapplsMap != null) {
                    PlaceOptions placeOptions = PlaceOptions.builder()
                            .backgroundColor(Color.WHITE)
                            .build();
                    PlaceAutocompleteFragment placeAutocompleteFragment = PlaceAutocompleteFragment.newInstance(placeOptions);
                    placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                        @Override
                        public void onPlaceSelected(ELocation eLocation) {
                            if(mapplsMap != null) {
                                if (eLocation.latitude != null && eLocation.longitude != null) {
                                    mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(eLocation.latitude, eLocation.longitude), 14));
                                } else {
                                    mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom(eLocation.getMapplsPin(), 14));
                                }
                            }
                            getSupportFragmentManager().popBackStack(PlaceAutocompleteFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

                        @Override
                        public void onCancel() {
                            getSupportFragmentManager().popBackStack(PlaceAutocompleteFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

                        @Override
                        public void requestForCurrentLocation() {

                        }
                    });
                    getSupportFragmentManager().beginTransaction().add(mBinding.fragmentContainer.getId(), placeAutocompleteFragment, PlaceAutocompleteFragment.class.getSimpleName())
                            .addToBackStack(PlaceAutocompleteFragment.class.getSimpleName())
                            .commit();
                }
            }
        });
        mBinding.tvNearbyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int top = mBinding.selectionBox.getTop() - mBinding.mapView.getTop();
                int left = mBinding.selectionBox.getLeft() - mBinding.mapView.getLeft();
                int bottom = top + mBinding.selectionBox.getHeight();
                int right = left + mBinding.selectionBox.getWidth();
                if (mapplsMap != null) {
                    LatLng topLeft = mapplsMap.getProjection().fromScreenLocation(new PointF(left, top));
                    LatLng rightBottom = mapplsMap.getProjection().fromScreenLocation(new PointF(right, bottom));

                    mapplsMap.clear();
                    MapplsNearbyReport mapplsNearbyReport = MapplsNearbyReport.builder()
                            .topLeft(Point.fromLngLat(topLeft.getLongitude(), topLeft.getLatitude()))
                            .bottomRight(Point.fromLngLat(rightBottom.getLongitude(), rightBottom.getLatitude()))
                            .build();
                    progressDialogShow();
                    MapplsNearbyReportManager.newInstance(mapplsNearbyReport).call(new OnResponseCallback<NearbyReportResponse>() {
                        @Override
                        public void onSuccess(NearbyReportResponse nearbyReportResponse) {
                            if (nearbyReportResponse != null && nearbyReportResponse.getReports() != null && nearbyReportResponse.getReports().size() > 0) {
                                for(NearbyReport nearbyReport: nearbyReportResponse.getReports()) {
                                    if(mapplsMap != null) {
                                        mapplsMap.addMarker(new MarkerOptions().position(new LatLng(nearbyReport.getLatitude(), nearbyReport.getLongitude())).title(nearbyReport.getCategory()));
                                    }
                                }
                            } else {
                                Toast.makeText(NearbyReportActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                            }
                            progressDialogHide();
                        }

                        @Override
                        public void onError(int i, String s) {
                            progressDialogHide();
                            Toast.makeText(NearbyReportActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    protected void progressDialogShow() {
        transparentProgressDialog.show();
    }

    protected void progressDialogHide() {
        transparentProgressDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        if (mapplsMap.getUiSettings() != null) {
            mapplsMap.getUiSettings().setLogoMargins(0, 0, 0, 100);
        }
        mapplsMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(28.550716, 77.268928)).zoom(12).build());
        mBinding.tvNearbyReport.setVisibility(View.VISIBLE);
        mBinding.selectionBox.setVisibility(View.VISIBLE);
        mBinding.search.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapError(int i, String s) {

    }
}