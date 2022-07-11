package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.gestures.AndroidGesturesManager;
import com.mappls.sdk.gestures.MoveGestureDetector;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;

public class GesturesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapplsMap mapplsMap;
    private AndroidGesturesManager androidGesturesManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        androidGesturesManager = new AndroidGesturesManager(mapView.getContext(), false);
        addTouchGesture();
    }

    private void addTouchGesture() {

        if(androidGesturesManager != null) {


            mapView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    androidGesturesManager.onTouchEvent(event);
                    return false;
                }


            });
            androidGesturesManager.setMoveGestureListener(new MoveGestureDetector.OnMoveGestureListener() {
                @Override
                public boolean onMoveBegin(MoveGestureDetector detector) {

                    if (mapplsMap != null && detector.getPointersCount() == 1) {
                        LatLng latLng = mapplsMap.getProjection().fromScreenLocation(detector.getFocalPoint());
                        Toast.makeText(GesturesActivity.this, "onMoveBegin: " + latLng.toString(), Toast.LENGTH_SHORT).show();

                    }

                    return true;
                }

                @Override
                public boolean onMove(MoveGestureDetector detector, float distanceX, float distanceY) {
                    if (mapplsMap != null && detector.getPointersCount() == 1) {
                        LatLng latLng = mapplsMap.getProjection().fromScreenLocation(detector.getFocalPoint());
                        Toast.makeText(GesturesActivity.this, "onMove: " + latLng.toString(), Toast.LENGTH_SHORT).show();


                    }
                    return false;
                }

                @Override
                public void onMoveEnd(MoveGestureDetector detector, float velocityX, float velocityY) {
                    if (mapplsMap != null) {
                        LatLng latLng = mapplsMap.getProjection().fromScreenLocation(detector.getFocalPoint());
                        Toast.makeText(GesturesActivity.this, "onMoveEnd: " + latLng.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28, 77), 14));


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
