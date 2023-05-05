package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityTrafficBinding;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.geometry.LatLng;

public class TrafficActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityTrafficBinding mBinding;
    private MapplsMap mapplsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_traffic);
        mBinding.mapView.getMapAsync(this);
        mBinding.showTraffic.setChecked(false);
        mBinding.freeflow.setClickable(false);
        mBinding.nonfreeflow.setClickable(false);
        mBinding.closure.setClickable(false);
        mBinding.stopIcon.setClickable(false);
        mBinding.freeflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mapplsMap != null) {
                    if (mapplsMap.isEnableTraffic()) {
                        mapplsMap.enableTrafficFreeFlow(isChecked);
                    }
                }
            }
        });
        mBinding.nonfreeflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mapplsMap != null) {
                    if(mapplsMap.isEnableTraffic()) {
                        mapplsMap.enableTrafficNonFreeFlow(isChecked);
                    }
                }
            }
        });
        mBinding.closure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mapplsMap != null) {
                    if(mapplsMap.isEnableTraffic()) {
                        mapplsMap.enableTrafficClosure(isChecked);
                    }
                }
            }
        });
        mBinding.stopIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mapplsMap != null) {
                    if(mapplsMap.isEnableTraffic()) {
                        mapplsMap.enableTrafficStopIcon(isChecked);
                    }
                }
            }
        });
        mBinding.showTraffic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mapplsMap != null) {
                    mapplsMap.enableTraffic(isChecked);

                    mBinding.freeflow.setClickable(isChecked);
                    mBinding.nonfreeflow.setClickable(isChecked);
                    mBinding.closure.setClickable(isChecked);
                    mBinding.stopIcon.setClickable(isChecked);

                    if(isChecked) {
                        mBinding.freeflow.setChecked(mapplsMap.isEnableTrafficFreeFlow());
                        mBinding.nonfreeflow.setChecked(mapplsMap.isEnableTrafficNonFreeFlow());
                        mBinding.closure.setChecked(mapplsMap.isEnableTrafficClosure());
                        mBinding.stopIcon.setChecked(mapplsMap.isEnableTrafficStopIcon());
                    } else {
                        mBinding.freeflow.setChecked(false);
                        mBinding.nonfreeflow.setChecked(false);
                        mBinding.closure.setChecked(false);
                        mBinding.stopIcon.setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        mBinding.trafficBtnLayout.setVisibility(View.VISIBLE);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                25.321684, 82.987289)).zoom(15.0).tilt(0).build();
        mapplsMap.setCameraPosition(cameraPosition);
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