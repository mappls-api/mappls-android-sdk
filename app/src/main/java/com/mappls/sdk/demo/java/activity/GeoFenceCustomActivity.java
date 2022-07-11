package com.mappls.sdk.demo.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.LayoutGeofenceUiActivityBinding;
import com.mappls.sdk.demo.java.model.GeofenceDetail;
import com.mappls.sdk.geofence.ui.GeoFence;

import com.mappls.sdk.geofence.ui.GeoFenceType;
import com.mappls.sdk.geofence.ui.listeners.GeoFenceViewCallback;
import com.mappls.sdk.geofence.ui.listeners.OnPolygonReachedMaxPointListener;
import com.mappls.sdk.geofence.ui.util.Orientation;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;


public class GeoFenceCustomActivity extends AppCompatActivity implements GeoFenceViewCallback {

    LayoutGeofenceUiActivityBinding mBinding;
    GeoFence geoFence;
    GeofenceDetail geofenceDetail;
    private MapplsMap mapplsMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null && getIntent().hasExtra("Geofence")) {
            geofenceDetail = new Gson().fromJson(getIntent().getStringExtra("Geofence"), GeofenceDetail.class);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.layout_geofence_ui_activity);
        if(geofenceDetail == null) {
            geoFence = new GeoFence();
            geoFence.setGeoFenceType(GeoFenceType.CIRCLE);
            geoFence.setCircleCenter(new LatLng(24.6496185, 77.3062072));
            geoFence.setCircleRadius(200);
        } else {
            geoFence = new GeoFence();
            if(geofenceDetail.getGfType().equalsIgnoreCase(GeofenceDetail.TYPE_POLYGON)) {
                geoFence.setGeoFenceType(GeoFenceType.POLYGON);
                List<List<Point>> pointList = new ArrayList<>();
                pointList.add(geofenceDetail.getGPS());
                geoFence.setPolygon(pointList);
            } else {
                geoFence.setGeoFenceType(GeoFenceType.CIRCLE);
                geoFence.setCircleCenter(geofenceDetail.getCircleCentre());
                geoFence.setCircleRadius(geofenceDetail.getCRadius());
            }


        }
        mBinding.idSeekBar.setMax(mBinding.geoFenceView.getMaxProgress());
        mBinding.geoFenceView.setGeoFence(geoFence);
        mBinding.geoFenceView.setCameraPadding(20, 20, 20, 20);

        mBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoFence geoFence = mBinding.geoFenceView.getGeoFence();
                if(geoFence != null) {
                    if(geofenceDetail == null) {
                        geofenceDetail = new GeofenceDetail();
                        geofenceDetail.setActive(false);
                    }
                    if(geoFence.getGeoFenceType() == GeoFenceType.POLYGON) {
                        geofenceDetail.setGfType(GeofenceDetail.TYPE_POLYGON);
                        if(geoFence.getPolygon() == null) {
                            Toast.makeText(GeoFenceCustomActivity.this, "Please draw Polygon first", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        geofenceDetail.setGPS(geoFence.getPolygon().get(0));
                    } else {
                        geofenceDetail.setGfType(GeofenceDetail.TYPE_CIRCLE);
                        geofenceDetail.setCircleCentre(geoFence.getCircleCenter());
                        geofenceDetail.setCRadius(geoFence.getCircleRadius());
                    }

                    String geoFenceIntent = new Gson().toJson(geofenceDetail);
                    Intent intent = new Intent();
                    intent.putExtra("GEOFENCE", geoFenceIntent);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mBinding.geoFenceView.onCreate(savedInstanceState);
        mBinding.geoFenceView.setGeoFenceViewCallback(GeoFenceCustomActivity.this);


        mBinding.geoFenceView.convertPointsToClockWise(Orientation.CLOCKWISE);

        mBinding.geoFenceView.simplifyWhenIntersectingPolygonDetected(true);
        mBinding.idSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mBinding.geoFenceView.onRadiusChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBinding.geoFenceView.radiusChangeFinish(seekBar.getProgress());

            }
        });
        mBinding.cbAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBinding.geoFenceView.enablePolygonDrawing(isChecked);
            }
        });
        mBinding.idCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.geoFenceView.drawCircleGeoFence();
            }
        });
        mBinding.idPolygonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.geoFenceView.drawPolygonGeofence();
            }
        });
    }

    @Override
    public void onGeoFenceReady(MapplsMap mapplsMap) {
        mBinding.toolsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void geoFenceType(GeoFenceType geoFenceType) {
        if (geoFenceType == GeoFenceType.POLYGON) {
            mBinding.cbAction.setVisibility(View.VISIBLE);
        } else {
            mBinding.cbAction.setVisibility(View.GONE);
        }
        mBinding.seekBarView.setVisibility(geoFenceType == GeoFenceType.POLYGON ? View.GONE : View.VISIBLE);

        mBinding.cbAction.setChecked(geoFenceType == GeoFenceType.POLYGON);
        mBinding.ivButtonCircle.setSelected(geoFenceType == GeoFenceType.CIRCLE);
        mBinding.ivButtonPolygon.setSelected(geoFenceType == GeoFenceType.POLYGON);

        if (geoFenceType == GeoFenceType.POLYGON) {
            if (mBinding.geoFenceView.getGeoFence().getPolygon() != null) {
                mBinding.cbAction.setVisibility(View.GONE);
            }
        } else {
            if (mBinding.geoFenceView.getGeoFence().getCircleRadius() != null) {
                mBinding.idSeekBar.setProgress(mBinding.geoFenceView.getProgress());
                mBinding.geoFenceView.radiusChangeFinish(mBinding.idSeekBar.getProgress());
            }
        }
    }

    @Override
    public void onCircleRadiusChanging(int radius) {
        mBinding.idSeekBarValue.setText(radius + " m");
    }

    @Override
    public void onUpdateGeoFence(GeoFence geoFence) {
        if (geoFence.getGeoFenceType() == GeoFenceType.POLYGON) {
            mBinding.cbAction.setVisibility(View.GONE);
            mBinding.cbAction.setChecked(false);

        }

    }

    @Override
    public void hasIntersectionPoints() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.geoFenceView.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.geoFenceView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mBinding.geoFenceView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.geoFenceView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.geoFenceView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.geoFenceView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBinding.geoFenceView.onStop();
    }
}

