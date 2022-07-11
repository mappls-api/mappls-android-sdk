package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.databinding.BaseLayoutBinding;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.plugin.TrackingPlugin;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.geojson.utils.PolylineUtils;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.geometry.LatLngBounds;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.directions.DirectionsCriteria;
import com.mappls.sdk.services.api.directions.MapplsDirectionManager;
import com.mappls.sdk.services.api.directions.MapplsDirections;
import com.mappls.sdk.services.api.directions.models.DirectionsResponse;
import com.mappls.sdk.services.api.directions.models.DirectionsRoute;
import com.mappls.sdk.services.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int MARKER_TRANSLATION_COMPLETE = 125;
    private final Handler trackingHandler = new Handler(Looper.getMainLooper());
    private BaseLayoutBinding mBinding;
    private List<Point> travelledPoints;
    private MapplsMap mapplsMap;
    private TrackingPlugin trackingPlugin;
    private int index = 0;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendMessageToBackgroundHandler();

            trackingHandler.postDelayed(runnable, 3000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.base_layout);
        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.mapView.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
        if (travelledPoints != null) {
            trackingHandler.post(runnable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
        trackingHandler.removeCallbacks(runnable);
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
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        this.mapplsMap.setMaxZoomPreference(16);
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                trackingPlugin = new TrackingPlugin(mBinding.mapView, TrackingActivity.this.mapplsMap);
                callRouteETA();
            }
        });

    }


    private void sendMessageToBackgroundHandler() {
        try {
            // GeoPoint nextLocation = mOrderTrackingActivity.getLocationBlockingQueue().take();
            if (index < travelledPoints.size() - 1) {
                trackingPlugin.animateCar(travelledPoints.get(index), travelledPoints.get(index + 1));
                index++;
                callTravelledRoute();

            } else {
                trackingHandler.removeCallbacks(runnable);
//                                Toast.makeText(this, "Route END", Toast.LENGTH_SHORT).show();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void callRouteETA() {
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.15183700, 72.93374500), 12));
        MapplsDirections.Builder builder = MapplsDirections.builder()
                .steps(true)
                .origin(Point.fromLngLat(72.93374500, 19.15183700))
                .destination(Point.fromLngLat(72.9344, 19.1478))
                .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)
                .overview(DirectionsCriteria.OVERVIEW_SIMPLIFIED);
        MapplsDirectionManager.newInstance(builder.build()).call(new OnResponseCallback<DirectionsResponse>() {
            @Override
            public void onSuccess(DirectionsResponse directionsResponse) {
                Log.e("TAG", directionsResponse.toJson());

                if (directionsResponse != null && directionsResponse.routes() != null && directionsResponse.routes().size() > 0) {
                    DirectionsRoute directionsRoute = directionsResponse.routes().get(0);
                    if (directionsRoute != null && directionsRoute.geometry() != null) {
                        travelledPoints = PolylineUtils.decode(directionsRoute.geometry(), Constants.PRECISION_6);
                        startTracking();
                    }
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });


    }

    private void startTracking() {

        trackingPlugin.addMarker(travelledPoints.get(0));
        trackingHandler.post(runnable);
    }

    private void callTravelledRoute() {
        MapplsDirections mapplsDirections = MapplsDirections.builder()
                .origin(travelledPoints.get(index))
                .destination(Point.fromLngLat(72.9344, 19.1478))
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .steps(true)
                .routeType(DirectionsCriteria.ROUTE_TYPE_SHORTEST)
                .resource(DirectionsCriteria.RESOURCE_ROUTE)
                .build();
        MapplsDirectionManager.newInstance(mapplsDirections).call(new OnResponseCallback<DirectionsResponse>() {
            @Override
            public void onSuccess(DirectionsResponse directionsResponse) {
                if (directionsResponse != null && directionsResponse.routes() != null && directionsResponse.routes().size() > 0) {
                    DirectionsRoute directionsRoute = directionsResponse.routes().get(0);
                    if (directionsRoute != null && directionsRoute.geometry() != null) {
                        trackingPlugin.updatePolyline(directionsRoute);
                        List<Point> remainingPath = PolylineUtils.decode(directionsRoute.geometry(), Constants.PRECISION_6);
                        List<LatLng> latLngList = new ArrayList<>();
                        for (Point point : remainingPath) {
                            latLngList.add(new LatLng(point.latitude(), point.longitude()));
                        }
                        if (latLngList.size() > 0) {
                            if (latLngList.size() == 1) {
                                mapplsMap.easeCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(0), 12));
                            } else {
                                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                                        .includes(latLngList)
                                        .build();
                                mapplsMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 180, 0, 180, 0));
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onMapError(int i, String s) {

    }
}