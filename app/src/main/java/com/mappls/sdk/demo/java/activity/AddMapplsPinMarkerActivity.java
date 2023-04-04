package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityAddMarkerBinding;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.annotations.Marker;
import com.mappls.sdk.maps.annotations.MarkerOptions;
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AddMapplsPinMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapplsMap mapplsMap;
    private ActivityAddMarkerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_marker);
        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.mapView.getMapAsync(this);
        mBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapplsPin = mBinding.etMapplsPin.getText().toString();

                if (!mapplsPin.isEmpty()) {
                    String[] mapplsPinList = mapplsPin.split(",");
                    addMarker(mapplsPinList);
                } else {
                    Toast.makeText(AddMapplsPinMarkerActivity.this, "Please add Mappls Pin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addMarker(String[] mapplsPinList) {
        if (mapplsMap != null) {
            mapplsMap.clear();
            List<MarkerOptions> markerOptions = new ArrayList<>();
            List<String> mapplsPins = new ArrayList<>();

            for(String mapplsPin: mapplsPinList) {
                markerOptions.add(new MarkerOptions().mapplsPin(mapplsPin).title(mapplsPin));
                mapplsPins.add(mapplsPin);
            }

            List<Marker> markers = mapplsMap.addMarkers(markerOptions, new MapplsMap.OnMarkerAddedListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AddMapplsPinMarkerActivity.this, "Marker Added Successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(AddMapplsPinMarkerActivity.this, "Invalid Mappls Pin", Toast.LENGTH_SHORT).show();
                }
            });

            if(mapplsPins.size() > 0) {
                if(mapplsPins.size() == 1) {
                    mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom(mapplsPins.get(0), 16));
                } else {
                    mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));
                }
            }

//            mapplsMap.showMarkers(markers, 10, 100, 10, 10);
        }
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

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        this.mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28, 77), 5));
        mBinding.layoutMapplsPin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapError(int i, String s) {

    }
}