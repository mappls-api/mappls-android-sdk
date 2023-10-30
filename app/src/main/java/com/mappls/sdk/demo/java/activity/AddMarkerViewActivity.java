package com.mappls.sdk.demo.java.activity;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.BaseLayoutBinding;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.annotations.Marker;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.utils.BitmapUtils;
import com.mappls.sdk.plugin.annotation.OnSymbolClickListener;
import com.mappls.sdk.plugin.annotation.Symbol;
import com.mappls.sdk.plugin.annotation.SymbolManager;
import com.mappls.sdk.plugin.annotation.SymbolOptions;
import com.mappls.sdk.plugin.markerview.MarkerView;
import com.mappls.sdk.plugin.markerview.MarkerViewManager;

import org.json.JSONObject;


public class AddMarkerViewActivity extends AppCompatActivity implements OnMapReadyCallback, OnSymbolClickListener {
    private BaseLayoutBinding mBinding;
    private MarkerView markerView;
    private MarkerViewManager markerViewManager;
    private SymbolManager symbolManager;
    private View customView;
    private boolean showInfowindow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.base_layout);
        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapplsMap mapplsMap) {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                // Initialize the MarkerViewManager
                style.addImage("icon_image", BitmapUtils.getBitmapFromDrawable(
                        ContextCompat.getDrawable(
                                AddMarkerViewActivity.this,
                                R.drawable.placeholder
                        )
                ));
                if(symbolManager == null) {
                    symbolManager = new SymbolManager(mBinding.mapView, mapplsMap, style);
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("place_name", "This is Infowindow");
                SymbolOptions symbolOptions = new SymbolOptions()
                        .position(new LatLng(25.321684, 82.987289))
                        .icon("icon_image")
                        .data(jsonObject)
                        .iconSize(1f);
                symbolManager.setIconAllowOverlap(true);
                symbolManager.setIconIgnorePlacement(true);
                symbolManager.create(symbolOptions);
                symbolManager.addClickListener(AddMarkerViewActivity.this);
                markerViewManager = new MarkerViewManager(mBinding.mapView, mapplsMap);

                mapplsMap.addOnMapClickListener(new MapplsMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        // Hide the custom layout when the map is clicked
                        if (showInfowindow) {
                            markerViewManager.removeMarker(markerView);
                            return true; // Consume the event
                        }
                        return false; // Allow the event to propagate
                    }
                });
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                        25.321684, 82.987289)).zoom(8).tilt(0).build();
                mapplsMap.setCameraPosition(cameraPosition);

            }
        });

    }

    @Override
    public void onMapError(int i, String s) {

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
        if (markerViewManager != null) {
            markerViewManager.onDestroy();
        }
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
    public boolean onAnnotationClick(Symbol symbol) {
//        View customView = LayoutInflater.from(AddMarkerViewActivity.this).inflate(
//                R.layout.marker_view_bubble, null);
//        customView.setVisibility(View.GONE);
        if (!showInfowindow) {
            showInfowindow = true;
            View customView = LayoutInflater.from(AddMarkerViewActivity.this).inflate(
                    R.layout.marker_view_bubble, null);
            customView.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

            // Set the View's TextViews with content
            TextView titleTextView = customView.findViewById(R.id.marker_window_title);
            if(symbol != null && symbol.getData() != null && symbol.getData().getAsJsonObject().has("place_name")) {
                titleTextView.setText(symbol.getData().getAsJsonObject().getAsJsonPrimitive("place_name").getAsString());
            }

            Button button1 = customView.findViewById(R.id.marker_window_button);
            Button button2 = customView.findViewById(R.id.marker_window_button1);
            Button button3 = customView.findViewById(R.id.marker_window_button2);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddMarkerViewActivity.this, "Button 1", Toast.LENGTH_SHORT).show();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddMarkerViewActivity.this, "Button 2", Toast.LENGTH_SHORT).show();
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddMarkerViewActivity.this, "Button 3", Toast.LENGTH_SHORT).show();
                }
            });
            markerView = new MarkerView(symbol.getPosition(), customView);
            markerViewManager.addMarker(markerView);
        }else {
            markerViewManager.removeMarker(markerView);
            showInfowindow = false;
        }
        return true;
    }

}