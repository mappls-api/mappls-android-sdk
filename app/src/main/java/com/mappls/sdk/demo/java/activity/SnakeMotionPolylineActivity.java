package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.plugin.SnakePolyLinePlugin;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.geojson.utils.PolylineUtils;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
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

public class SnakeMotionPolylineActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapplsMap mapplsMap;

    private SnakePolyLinePlugin snakePolyLinePlugin;
    private static final Point ORIGIN_POINT = Point.fromLngLat(77.2667594,28.5506561);

    private static final Point DESTINATION_POINT = Point.fromLngLat(77.101318,28.703900);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_motion_polyline);
        mapView = findViewById(R.id.map_view);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        snakePolyLinePlugin = new SnakePolyLinePlugin(mapView, mapplsMap);
// Add a source and LineLayer for the snaking directions route line


        getDirectionRoute();


    }

    private void getDirectionRoute() {
        MapplsDirections mapplsDirections = MapplsDirections.builder()
                .origin(ORIGIN_POINT)
                .steps(true)
                .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .destination(DESTINATION_POINT)
                .build();
        MapplsDirectionManager.newInstance(mapplsDirections).call(new OnResponseCallback<DirectionsResponse>() {
            @Override
            public void onSuccess(DirectionsResponse directionsResponse) {
                if(directionsResponse != null) {
                    DirectionsRoute currentRoute = directionsResponse.routes().get(0);
                    List<Point> points = PolylineUtils.decode(currentRoute.geometry(), Constants.PRECISION_6);
                    List<LatLng> latLngs = new ArrayList<>();
                    for (Point point : points) {
                        latLngs.add(new LatLng(point.latitude(), point.longitude()));
                    }
                    LatLngBounds latLngBounds = new LatLngBounds.Builder()
                            .includes(latLngs)
                            .build();
                    mapplsMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10, 10, 10, 10));

                    if (snakePolyLinePlugin != null) {
                        snakePolyLinePlugin.create(currentRoute.legs().get(0).steps());
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

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        if (snakePolyLinePlugin != null) {
            snakePolyLinePlugin.removeCallback();
        }
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
