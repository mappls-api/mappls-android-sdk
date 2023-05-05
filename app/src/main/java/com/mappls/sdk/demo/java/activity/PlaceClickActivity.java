package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityPlaceClickBinding;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.OnPlaceClickListener;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetail;
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetailManager;
import com.mappls.sdk.services.api.placedetail.model.PlaceDetailResponse;

public class PlaceClickActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityPlaceClickBinding mBinding;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottom_sheet;
    private TextView address,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_click);
        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.mapView.getMapAsync(this);
        bottom_sheet = findViewById(R.id.place_click_bottom_sheet);
        title = findViewById(R.id.tv_title);
        address = findViewById(R.id.tv_address);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        showHideBottomSheet(false);
    }


    @Override
    public void onMapReady(@NonNull MapplsMap mapplsMap) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                28.551087, 77.257373)).zoom(12).tilt(0).build();
        mapplsMap.setCameraPosition(cameraPosition);
        mapplsMap.setOnPlaceClickListener(new OnPlaceClickListener() {
            @Override
            public boolean onPlaceClick(@Nullable String s) {
                if (s != null) {
                    Toast.makeText(PlaceClickActivity.this, s, Toast.LENGTH_LONG).show();
                    callMapplsPin(s);
                }
                return false;
            }
        });

    }

    private void callMapplsPin(String mapplsPin) {
        MapplsPlaceDetail placeDetail = MapplsPlaceDetail.builder()
                .mapplsPin(mapplsPin)
                .build();
        MapplsPlaceDetailManager.newInstance(placeDetail).call(new OnResponseCallback<PlaceDetailResponse>() {
            @Override
            public void onSuccess(PlaceDetailResponse placeDetailResponse) {
                if(placeDetailResponse != null) {
                    showHideBottomSheet(true);
                    title.setText(placeDetailResponse.getPlaceName());
                    address.setText(placeDetailResponse.getAddress());
                } else {
                    Toast.makeText(PlaceClickActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(PlaceClickActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showHideBottomSheet(boolean showHide) {
        if (showHide) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onMapError(int i, String s) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
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
    protected void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }
}