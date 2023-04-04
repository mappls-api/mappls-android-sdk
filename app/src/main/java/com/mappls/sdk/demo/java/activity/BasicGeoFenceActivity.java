package com.mappls.sdk.demo.java.activity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.LayoutBasicGeofenceBinding;
import com.mappls.sdk.demo.java.settings.MapplsGeofenceSetting;
import com.mappls.sdk.geofence.ui.GeoFence;
import com.mappls.sdk.geofence.ui.GeoFenceType;
import com.mappls.sdk.geofence.ui.listeners.GeoFenceViewCallback;
import com.mappls.sdk.geofence.ui.views.GeoFenceOptions;
import com.mappls.sdk.geofence.ui.views.GeoFenceView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.geometry.LatLng;

public class BasicGeoFenceActivity extends AppCompatActivity implements GeoFenceViewCallback {
    LayoutBasicGeofenceBinding mBinding;
    GeoFenceView geofenceView;
    GeoFence geoFence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.layout_basic_geofence);
        geoFence = new GeoFence();
        if (MapplsGeofenceSetting.getInstance().isDefault()){
            geofenceView = new GeoFenceView(this);
            geoFence.setGeoFenceType(GeoFenceType.CIRCLE);
            geoFence.setCircleCenter(new LatLng(24.6496185, 77.3062072));
            geoFence.setCircleRadius(200);

        }else {
            GeoFenceOptions.Builder geoFenceOptions = GeoFenceOptions.builder();
            geoFenceOptions.circleOutlineWidth(MapplsGeofenceSetting.getInstance().getCircleOutlineWidth());
            geoFenceOptions.circleFillColor(MapplsGeofenceSetting.getInstance().getCircleFillColor());
            geoFenceOptions.circleFillOutlineColor(MapplsGeofenceSetting.getInstance().getCircleFillOutlineColor());
            geoFenceOptions.draggingLineColor(MapplsGeofenceSetting.getInstance().getDraggingLineColor());
            geoFenceOptions.maxRadius(MapplsGeofenceSetting.getInstance().getMaxRadius());
            geoFenceOptions.minRadius(MapplsGeofenceSetting.getInstance().getMinRadius());
            geoFenceOptions.polygonDrawingLineColor(MapplsGeofenceSetting.getInstance().getPolygonDrawingLineColor());
            geoFenceOptions.polygonFillColor(MapplsGeofenceSetting.getInstance().getPolygonFillColor());
            geoFenceOptions.polygonFillOutlineColor(MapplsGeofenceSetting.getInstance().getPolygonFillOutlineColor());
            geoFenceOptions.polygonOutlineWidth(MapplsGeofenceSetting.getInstance().getPolygonOutlineWidth());
            geoFenceOptions.showSeekBar(MapplsGeofenceSetting.getInstance().isShowSeekBar());
            geoFenceOptions.seekbarPrimaryColor(MapplsGeofenceSetting.getInstance().getSeekbarPrimaryColor());
            geoFenceOptions.seekbarSecondaryColor(MapplsGeofenceSetting.getInstance().getSeekbarSecondaryColor());
            geoFenceOptions.seekbarCornerRadius(MapplsGeofenceSetting.getInstance().getSeekbarCornerRadius());

            geofenceView = new GeoFenceView(this,geoFenceOptions.build());
            geoFence.setCircleRadius(200);
            geoFence.setGeoFenceType(MapplsGeofenceSetting.getInstance().isPolygon() ? GeoFenceType.POLYGON: GeoFenceType.CIRCLE);
            geoFence.setCircleCenter(new LatLng(24.6496185, 77.3062072));

            geofenceView.simplifyWhenIntersectingPolygonDetected(MapplsGeofenceSetting.getInstance().isSimplifyWhenIntersectingPolygonDetected());

        }


        geofenceView.setGeoFence(geoFence);
        geofenceView.onCreate(savedInstanceState);
        mBinding.rootLayout.addView(geofenceView);
        geofenceView.setGeoFenceViewCallback(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        geofenceView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        geofenceView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        geofenceView.onPause();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        geofenceView.onLowMemory();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        geofenceView.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        geofenceView.onDestroy();

    }

    @Override
    public void onGeoFenceReady(MapplsMap mapplsMap) {

    }

    @Override
    public void geoFenceType(GeoFenceType geoFenceType) {

    }

    @Override
    public void onCircleRadiusChanging(int i) {

    }

    @Override
    public void onUpdateGeoFence(GeoFence geoFence) {


    }

    @Override
    public void hasIntersectionPoints() {

    }
}


