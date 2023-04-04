package com.mappls.sdk.demo.java.activity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityDrivingRangeBinding;
import com.mappls.sdk.demo.java.settings.MapplsDrivingRangeSetting;
import com.mappls.sdk.drivingrange.IDrivingRangeListener;
import com.mappls.sdk.drivingrange.MapplsDrivingRangeContour;
import com.mappls.sdk.drivingrange.MapplsDrivingRangeOption;
import com.mappls.sdk.drivingrange.MapplsDrivingRangePlugin;
import com.mappls.sdk.drivingrange.MapplsDrivingRangeSpeed;
import com.mappls.sdk.drivingrange.MapplsDrivingRangeTypeInfo;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.location.LocationComponentActivationOptions;
import com.mappls.sdk.maps.location.LocationComponentOptions;
import com.mappls.sdk.maps.location.engine.LocationEngineCallback;
import com.mappls.sdk.maps.location.engine.LocationEngineResult;
import com.mappls.sdk.maps.location.permissions.PermissionsListener;
import com.mappls.sdk.maps.location.permissions.PermissionsManager;

import java.util.ArrayList;
import java.util.List;

public class DrivingRangeActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {

    private PermissionsManager mPermissionsManager;
    private MapplsMap mMapplsMap;
    private MapplsDrivingRangePlugin mapplsDrivingRangePlugin;
    private ActivityDrivingRangeBinding mBinding;
    private boolean isLocationCall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_driving_range);
        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapplsMap mapplsMap) {
        this.mMapplsMap = mapplsMap;
        mapplsDrivingRangePlugin = new MapplsDrivingRangePlugin(mBinding.mapView, mapplsMap);
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            mapplsMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocation(style);
                }
            });

        } else {
            mPermissionsManager = new PermissionsManager(this);
            mPermissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void enableLocation(Style style) {
        LocationComponentOptions options = LocationComponentOptions.builder(this)
                .trackingGesturesManagement(true)
                .build();
        LocationComponentActivationOptions locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, style)
                        .locationComponentOptions(options)
                        .build();
        mMapplsMap.getLocationComponent().activateLocationComponent(locationComponentActivationOptions);
        mMapplsMap.getLocationComponent().setLocationComponentEnabled(true);
        if(MapplsDrivingRangeSetting.getInstance().isUsingCurrentLocation()) {
            mMapplsMap.getLocationComponent().getLocationEngine().getLastLocation(new LocationEngineCallback<LocationEngineResult>() {
                @Override
                public void onSuccess(LocationEngineResult locationEngineResult) {
                    if(locationEngineResult == null || locationEngineResult.getLastLocation() == null) {
                        return;
                    }
                    if (isLocationCall) {
                        return;
                    }
                    isLocationCall = true;
                    Location location = locationEngineResult.getLastLocation();
                    drawDrivingRange(Point.fromLngLat(location.getLongitude(), location.getLatitude()));
                }

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            if(!isLocationCall) {
                Location location = mMapplsMap.getLocationComponent().getLastKnownLocation();
                if(location != null) {
                    isLocationCall = true;
                    drawDrivingRange(Point.fromLngLat(location.getLongitude(), location.getLatitude()));
                }
            }
        } else {
            drawDrivingRange(MapplsDrivingRangeSetting.getInstance().getLocation());
        }
    }

    private void drawDrivingRange(Point location) {
        if(mMapplsMap == null) {
            return;
        }
        mMapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude(), location.longitude()), 12));
        MapplsDrivingRangeSpeed speed;
        if(MapplsDrivingRangeSetting.getInstance().getSpeedType() == MapplsDrivingRangeSetting.SPEED_TYPE_OPTIMAL) {
            speed = MapplsDrivingRangeSpeed.optimal();
        } else if(MapplsDrivingRangeSetting.getInstance().getPredectiveType() == MapplsDrivingRangeSetting.PREDECTIVE_TYPE_CURRENT) {
            speed = MapplsDrivingRangeSpeed.predictiveSpeedFromCurrentTime();
        } else {
            speed = MapplsDrivingRangeSpeed.predictiveSpeedFromCustomTime(MapplsDrivingRangeSetting.getInstance().getTime());
        }
        List<MapplsDrivingRangeContour> contourList = new ArrayList<>();
        contourList.add(MapplsDrivingRangeContour.builder()
                .value(MapplsDrivingRangeSetting.getInstance().getContourValue())
                .color(MapplsDrivingRangeSetting.getInstance().getContourColor())
                .build());
        MapplsDrivingRangeTypeInfo rangeTypeInfo = MapplsDrivingRangeTypeInfo.builder()
                .rangeType(MapplsDrivingRangeSetting.getInstance().getRangeType())
                .contours(contourList)
                .build();
        MapplsDrivingRangeOption option = MapplsDrivingRangeOption.builder()
                .location(location)
                .rangeTypeInfo(rangeTypeInfo)
                .drivingProfile(MapplsDrivingRangeSetting.getInstance().getDrivingProfile())
                .showLocations(MapplsDrivingRangeSetting.getInstance().isShowLocations())
                .isForPolygons(MapplsDrivingRangeSetting.getInstance().isForPolygon())
                .denoise(MapplsDrivingRangeSetting.getInstance().getDenoise())
                .generalize(MapplsDrivingRangeSetting.getInstance().getGeneralize())
                .speedTypeInfo(speed)
                .build();
        mapplsDrivingRangePlugin.drawDrivingRange(option, new IDrivingRangeListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(DrivingRangeActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                Toast.makeText(DrivingRangeActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapError(int i, String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public void onExplanationNeeded(List<String> list) {

    }

    @Override
    public void onPermissionResult(boolean b) {
        if (b) {
            if (mMapplsMap != null) {
                mMapplsMap.getStyle(new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocation(style);
                    }
                });
            }
        }
    }
}