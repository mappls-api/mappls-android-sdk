package com.mappls.sdk.demo.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.utils.CheckInternet;
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.camera.CameraPosition;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.predictive.distance.MapplsPredictiveDistance;
import com.mappls.sdk.services.api.predictive.distance.MapplsPredictiveDistanceManager;
import com.mappls.sdk.services.api.predictive.distance.PredictiveDistanceCriteria;
import com.mappls.sdk.services.api.predictive.distance.models.PredictiveDistanceResponse;
import com.mappls.sdk.services.api.predictive.distance.models.PredictiveDistanceResults;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CEINFO on 26-02-2019.
 */

public class PredictiveDistanceActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private TransparentProgressDialog transparentProgressDialog;
    private TextView tvDistance, tvDuration;
    private LinearLayout directionDetailsLayout;

    private FloatingActionButton floatingActionButton;
    private String mDestination="28.551087,77.257373";
    private String mSource ="28.582864,77.234230";
    private String waypoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_activity);
        mapView = findViewById(R.id.map_view);
        floatingActionButton = findViewById(R.id.edit_btn);
        directionDetailsLayout = findViewById(R.id.distance_details_layout);
        tvDistance = findViewById(R.id.tv_distance);
        tvDuration = findViewById(R.id.tv_duration);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        transparentProgressDialog = new TransparentProgressDialog(this, R.drawable.circle_loader, "");
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,InputActivity.class);
            intent.putExtra("origin", mSource);
            intent.putExtra("destination", mDestination);
            intent.putExtra("waypoints", waypoints);
            startActivityForResult(intent,501);
        });
    }

    @Override
    public void onMapReady(MapplsMap mapplsMap) {




        mapplsMap.setCameraPosition(setCameraAndTilt());

       /* List<Point> coordinatesPoint = new ArrayList<Point>();
        coordinatesPoint.add(Point.fromLngLat(77.257373, 28.551087));
        coordinatesPoint.add(Point.fromLngLat(77.234230, 28.582864));*/
        if (CheckInternet.isNetworkAvailable(PredictiveDistanceActivity.this)) {
            calculateDistance(null,null);
        } else {
            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapError(int i, String s) {
    }

    private CameraPosition setCameraAndTilt() {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(
                28.551087, 77.257373)).zoom(14).tilt(0).build();
        return cameraPosition;
    }

    private void progressDialogShow() {
        transparentProgressDialog.show();
    }

    private void progressDialogHide() {
        transparentProgressDialog.dismiss();
    }

    private void calculateDistance(List<Point> pointList, List<String> mapplsPins) {
        progressDialogShow();
        MapplsPredictiveDistance.Builder mapplsPredictiveDistance = MapplsPredictiveDistance.builder();

            if (mSource!=null){
                if (!mSource.contains(",")){
                    mapplsPredictiveDistance.addSource(mSource);
                }else {
                    Point point =Point.fromLngLat(Double.parseDouble(mSource.split(",")[1]),Double.parseDouble(mSource.split(",")[0]));
                    mapplsPredictiveDistance.addSource(point);
                }
            }

                 if (mapplsPins!=null&&mapplsPins.size()>0){
                     mapplsPredictiveDistance.sourceList(mapplsPins);
                 }
                 if (pointList!=null&&pointList.size()>0){
                     mapplsPredictiveDistance.sources(pointList);
                 }
        if (mDestination!=null){
            if (!mDestination.contains(",")){
                mapplsPredictiveDistance.addDestination(mDestination);
            }else {
                Point point =Point.fromLngLat(Double.parseDouble(mDestination.split(",")[1]),Double.parseDouble(mDestination.split(",")[0]));
                mapplsPredictiveDistance.addDestination(point);
            }
        }
        mapplsPredictiveDistance.profile(PredictiveDistanceCriteria.PROFILE_DRIVING);
//                .resource(DirectionsCriteria.RESOURCE_DISTANCE_ETA);
        MapplsPredictiveDistanceManager.newInstance(mapplsPredictiveDistance.build()).call(new OnResponseCallback<PredictiveDistanceResponse>() {
            @Override
            public void onSuccess(PredictiveDistanceResponse predictiveDistanceResponse) {
                progressDialogHide();
                if (predictiveDistanceResponse != null) {
                    PredictiveDistanceResults predictiveDistanceResults = predictiveDistanceResponse.getSourcesToTargets().get(0).get(0);

                    if (predictiveDistanceResults != null) {
                        updateData(predictiveDistanceResults);
                    } else {
                        Toast.makeText(PredictiveDistanceActivity.this, "Failed: " + predictiveDistanceResponse.getUnits(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                progressDialogHide();
                Toast.makeText(PredictiveDistanceActivity.this, "Failed: " + s, Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void updateData(PredictiveDistanceResults distanceResults) {

        directionDetailsLayout.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        tvDuration.setText("(" + getFormattedDuration(distanceResults.getTime()) + ")");
        tvDistance.setText(getFormattedDistance(distanceResults.getDistance()));
    }

    /**
     * Get Formatted Distance
     *
     * @param distance route distance
     * @return distance in Kms if distance > 1000 otherwise in mtr
     */
    private String getFormattedDistance(double distance) {

        if (distance < 1) {
            return (distance * 1000)+ "mtr.";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(distance) + "Km.";
    }

    /**
     * Get Formatted Duration
     *
     * @param duration route duration
     * @return formatted duration
     */
    private String getFormattedDuration(double duration) {
        long min = (long) (duration % 3600 / 60);
        long hours = (long) (duration % 86400 / 3600);
        long days = (long) (duration / 86400);
        if (days > 0L) {
            return days + " " + (days > 1L ? "Days" : "Day") + " " + hours + " " + "hr" + (min > 0L ? " " + min + " " + "min." : "");
        } else {
            return hours > 0L ? hours + " " + "hr" + (min > 0L ? " " + min + " " + "min" : "") : min + " " + "min.";
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==501&&resultCode==RESULT_OK){

            List<String> mapplsPinList= new ArrayList<>();
            List<Point> points = new ArrayList<>();
            if (data.hasExtra("origin")){
               mSource = data.getStringExtra("origin");

            }
            if (data.hasExtra("waypoints")){
               String wayPoints=  data.getStringExtra("waypoints");
                if (!wayPoints.contains(";")){
                    if (!wayPoints.contains(",")){
                        mapplsPinList.add(wayPoints);
                    }else{
                        Point point = Point.fromLngLat(Double.parseDouble(wayPoints.split(",")[1]),Double.parseDouble(wayPoints.split(",")[0]));
                        points.add(point);
                    }
                }else {
                    String [] wayPointsArray = wayPoints.split(";");
                    for (String value :wayPointsArray){
                        if (!value.contains(",")){
                            mapplsPinList.add(value);
                        }else{
                            Point point = Point.fromLngLat(Double.parseDouble(value.split(",")[1]),Double.parseDouble(value.split(",")[0]));
                           points.add(point);
                        }
                    }
                }
                this.waypoints = wayPoints;
            }
            if (data.hasExtra("destination")){
                mDestination = data.getStringExtra("destination");
            }

            calculateDistance(points,mapplsPinList);
        }
    }
}
