package com.mappls.sdk.demo.java.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.plugin.AnimatedCarPlugin;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.annotations.PolylineOptions;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.geometry.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class CarAnimationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private List<LatLng> listOfLatlang = new ArrayList<>();
    int index = 0;
    private AnimatedCarPlugin animatedCarPlugin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_animation);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        listOfLatlang.add(new LatLng(22.30977, 73.23646));
        listOfLatlang.add(new LatLng(22.30977, 73.23646));
        listOfLatlang.add(new LatLng(22.3098, 73.23641));
        listOfLatlang.add(new LatLng(22.30984, 73.23638));
        listOfLatlang.add(new LatLng(22.30988, 73.23638));
        listOfLatlang.add(new LatLng(22.30991, 73.23638));
        listOfLatlang.add(new LatLng(22.30993, 73.23639));
        listOfLatlang.add(new LatLng(22.30996, 73.23642));
        listOfLatlang.add(new LatLng(22.30999, 73.23651));
        listOfLatlang.add(new LatLng(22.30997, 73.23655));
        listOfLatlang.add(new LatLng(22.30994, 73.23659));
        listOfLatlang.add(new LatLng(22.30988, 73.23662));
        listOfLatlang.add(new LatLng(22.30982, 73.2366));
        listOfLatlang.add(new LatLng(22.30981, 73.23659));
        listOfLatlang.add(new LatLng(22.30962, 73.23674));
        listOfLatlang.add(new LatLng(22.30889, 73.2372));
        listOfLatlang.add(new LatLng(22.30815, 73.23772));
        listOfLatlang.add(new LatLng(22.3076, 73.23803));
        listOfLatlang.add(new LatLng(22.30705, 73.23834));
        listOfLatlang.add(new LatLng(22.30678, 73.23848));
        listOfLatlang.add(new LatLng(22.30672, 73.23834));
        listOfLatlang.add(new LatLng(22.30701, 73.2382));
        listOfLatlang.add(new LatLng( 22.30779, 73.23777));
        listOfLatlang.add(new LatLng(22.30797, 73.23766));
        listOfLatlang.add(new LatLng(22.30811, 73.23757));
        listOfLatlang.add(new LatLng( 22.30847, 73.23734));
    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .includes(listOfLatlang)
                .build();

//        this.mapplsMap = mapplsMap;
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                animatedCarPlugin = new AnimatedCarPlugin(getApplicationContext(), mapView, mapplsMap);
                animatedCarPlugin.addMainCar(listOfLatlang.get(index), true);
                animatedCarPlugin.animateCar();

                animatedCarPlugin.setOnUpdateNextPoint(new AnimatedCarPlugin.OnUpdatePoint() {
                    @Override
                    public void updateNextPoint() {
                        if (index < listOfLatlang.size() - 1)
                            index = index + 1;

                        animatedCarPlugin.updateNextPoint(listOfLatlang.get(index));
                        animatedCarPlugin.animateCar();
                    }
                });
            }
        });

      mapplsMap.addPolyline(new PolylineOptions().addAll(listOfLatlang).color(Color.parseColor("#3bb2d0")).width(4));
      mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100));

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
        if (animatedCarPlugin != null)
            animatedCarPlugin.clearAllCallBacks();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animatedCarPlugin != null)
            animatedCarPlugin.addAllCallBacks();
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
