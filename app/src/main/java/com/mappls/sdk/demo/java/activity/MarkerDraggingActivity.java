package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.utils.BitmapUtils;
import com.mappls.sdk.plugin.annotation.OnSymbolDragListener;
import com.mappls.sdk.plugin.annotation.Symbol;
import com.mappls.sdk.plugin.annotation.SymbolManager;
import com.mappls.sdk.plugin.annotation.SymbolOptions;

public class MarkerDraggingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapplsMap mapplsMap;
    private LatLng latLng = new LatLng(28.705436, 77.100462);
    private SymbolManager symbolManager;

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
      this.mapplsMap = mapplsMap;
      mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        initMarker();
    }

    private void initMarker() {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                symbolManager = new SymbolManager(mapView, mapplsMap, style);
                SymbolOptions symbolOptions = new SymbolOptions()
                        .position(latLng)
                        .icon(BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(MarkerDraggingActivity.this, R.drawable.placeholder)))
                        .draggable(true);
                symbolManager.setIconIgnorePlacement(false);
                symbolManager.setIconAllowOverlap(true);
                symbolManager.create(symbolOptions);
                symbolManager.addDragListener(new OnSymbolDragListener() {
                    @Override
                    public void onAnnotationDragStarted(Symbol symbol) {

                    }

                    @Override
                    public void onAnnotationDrag(Symbol symbol) {

                    }

                    @Override
                    public void onAnnotationDragFinished(Symbol symbol) {
                        Toast.makeText(MarkerDraggingActivity.this, symbol.getPosition().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
        mapView.onDestroy();
        if(symbolManager != null) {
            symbolManager.onDestroy();
        }
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
