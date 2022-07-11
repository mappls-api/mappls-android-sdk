package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.utils.SemiCirclePointsListHelper;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.geometry.LatLngBounds;
import com.mappls.sdk.maps.style.sources.GeoJsonOptions;
import com.mappls.sdk.plugin.annotation.LineManager;
import com.mappls.sdk.plugin.annotation.LineOptions;

import java.util.List;


public class SemiCirclePolylineActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private List<LatLng> listOfLatLng;
    private Button btnRemove;
    private Button btnAdd;
    private LineManager lineManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semi_circle_polyline);

        mapView = findViewById(R.id.map_view);

        btnRemove = findViewById(R.id.remove);
        btnAdd = findViewById(R.id.add);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lineManager != null) {
                    lineManager.clearAll();
                    btnAdd.setVisibility(View.VISIBLE);
                    btnRemove.setVisibility(View.GONE);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lineManager != null) {
                    LineOptions lineOptions = new LineOptions()
                            .points(listOfLatLng)
                            .lineColor("#FF0000")
                            .lineWidth(4f);
                    lineManager.create(lineOptions);
                    btnRemove.setVisibility(View.VISIBLE);
                    btnAdd.setVisibility(View.GONE);
                }
            }
        });

        listOfLatLng = SemiCirclePointsListHelper.showCurvedPolyline(new LatLng(28.7039, 77.101318), new LatLng(28.704248, 77.102370), 0.5);
    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .includes(listOfLatLng)
                .build();

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100));

        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                lineManager = new LineManager(mapView, mapplsMap, style, new GeoJsonOptions().withLineMetrics(true).withBuffer(2));
                LineOptions lineOptions = new LineOptions()
                        .points(listOfLatLng)
                        .lineColor("#FF0000")
                        .lineWidth(4f);

                lineManager.setLineDasharray(new Float[]{4f, 6f});
                lineManager.create(lineOptions);

                btnRemove.setVisibility(View.VISIBLE);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(lineManager != null) {
            lineManager.onDestroy();
        }
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
