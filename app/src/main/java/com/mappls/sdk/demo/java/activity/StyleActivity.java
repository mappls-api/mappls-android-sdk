package com.mappls.sdk.demo.java.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.StyleActivityBinding;
import com.mappls.sdk.demo.java.adapter.StyleAdapter;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.MapplsMapConfiguration;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.style.OnStyleLoadListener;

import timber.log.Timber;

public class StyleActivity extends AppCompatActivity implements OnMapReadyCallback {

    private StyleActivityBinding mBinding;
    private BottomSheetBehavior<RelativeLayout> bottomSheetBehavior;
    private StyleAdapter adapter;
    private MapplsMap mapplsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.style_activity);
        mBinding.mapView.onCreate(savedInstanceState);

        mBinding.mapView.getMapAsync(this);
        bottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBinding.rvStyle.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StyleAdapter();
        mBinding.rvStyle.setAdapter(adapter);
        mBinding.saveLastStyle.setChecked(MapplsMapConfiguration.getInstance().isShowLastSelectedStyle());

        mBinding.saveLastStyle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MapplsMapConfiguration.getInstance().setShowLastSelectedStyle(isChecked);
            }
        });

        adapter.setOnStyleSelectListener(style -> {
            if(mapplsMap != null) {
                mapplsMap.setMapplsStyle(style.getName(), new OnStyleLoadListener() {
                    @Override
                    public void onError(String error) {
                        Toast.makeText(StyleActivity.this, error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStyleLoaded(Style styles) {
                        Toast.makeText(StyleActivity.this, "onStyleLoaded", Toast.LENGTH_SHORT).show();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });
            }
        });
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
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
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
    public void onMapReady(MapplsMap mapplsMap) {
        Timber.tag("onMapReady").e("SUCCESS");
        this.mapplsMap = mapplsMap;
        if (mapplsMap.getUiSettings() != null) {
            mapplsMap.getUiSettings().setLogoMargins(0, 0, 0, 200);
        }
        mapplsMap.enableTraffic(true);
        this.mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.6466772,76.8130614), 12));
        if (adapter != null) {
            Timber.e(new Gson().toJson(this.mapplsMap.getMapplsAvailableStyles()));
            adapter.setStyleList(this.mapplsMap.getMapplsAvailableStyles());
        }
    }

    @Override
    public void onMapError(int i, String s) {
        Timber.tag("onMapError").e(i + "------" + s);
    }
}