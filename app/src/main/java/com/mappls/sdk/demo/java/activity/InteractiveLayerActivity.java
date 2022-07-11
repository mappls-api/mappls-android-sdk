package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.adapter.InteractiveLayerAdapter;
import com.mappls.sdk.maps.InteractiveLayer;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.covid.InteractiveItemDetails;
import com.mappls.sdk.maps.geometry.LatLng;

import java.util.List;


public class InteractiveLayerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private BottomSheetBehavior bottomSheetBehavior;
    private MapplsMap mapplsMap;
    private RecyclerView recyclerView;
    private InteractiveLayerAdapter adapter;
    private SwitchCompat toggleInfoWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_layer);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        View view = findViewById(R.id.bottomSheet);
        toggleInfoWindow = findViewById(R.id.toggle_info_window);
        toggleInfoWindow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mapplsMap != null) {
                    mapplsMap.showInteractiveLayerInfoWindow(isChecked);
                }
            }
        });
        bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        recyclerView = findViewById(R.id.rv_interactive_layer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InteractiveLayerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnLayerSelected(new InteractiveLayerAdapter.OnLayerSelected() {
            @Override
            public void onLayerSelected(InteractiveLayer interactiveLayer, boolean isSelected) {
                if(isSelected) {
                    mapplsMap.showInteractiveLayer(interactiveLayer);
                } else {
                    mapplsMap.hideInteractiveLayer(interactiveLayer);
                }
            }
        });


    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28, 77), 5));
        if (mapplsMap.getUiSettings() != null) {
            mapplsMap.getUiSettings().setLogoMargins(0, 0, 0, 200);
        }

        mapplsMap.setOnInteractiveLayerClickListener(new MapplsMap.OnInteractiveLayerClickListener() {
            @Override
            public void onInteractiveLayerClicked(InteractiveItemDetails interactiveItemDetails) {

            }
        });
        mapplsMap.showInteractiveLayerInfoWindow(toggleInfoWindow.isChecked());

        mapplsMap.getInteractiveLayer(new MapplsMap.InteractiveLayerLoadingListener() {
            @Override
            public void onLayersLoaded(List<InteractiveLayer> list) {
                adapter.setCovidLayers(list);
            }
        });

    }

    @Override
    public void onMapError(int i, String s) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null)
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
