package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.plugin.scalebar.ScaleBarOptions;
import com.mappls.sdk.plugin.scalebar.ScaleBarPlugin;

public class ScalebarActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {

        /* this is done for animating/moving camera to particular position */
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                25.321684, 82.987289)).zoom(10).tilt(0).build();
        mapplsMap.setCameraPosition(cameraPosition);

        ScaleBarPlugin scaleBarPlugin = new ScaleBarPlugin(mapView, mapplsMap);
        ScaleBarOptions scaleBarOptions = new ScaleBarOptions(this)
                .setTextColor(android.R.color.black)
                .setTextSize(40f)
                .setBarHeight(5f)
                .setBorderWidth(2f)
                .setRefreshInterval(15)
                .setMarginTop(R.dimen.scalebar_top_margin)
                .setMarginLeft(R.dimen.scalebar_left_margin)
                .setTextBarMargin(15f)
                .setMaxWidthRatio(0.5f)
                .setShowTextBorder(true)
                .setTextBorderWidth(5f);
        scaleBarPlugin.create(scaleBarOptions);



    }

    @Override
    public void onMapError(int i, String s) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mapView != null)
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mapView != null)
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mapView != null)
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
