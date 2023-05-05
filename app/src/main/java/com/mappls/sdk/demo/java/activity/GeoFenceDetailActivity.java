package com.mappls.sdk.demo.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityGeoFenceDetailBinding;
import com.mappls.sdk.demo.java.adapter.GeoFenceDetailAdapter;
import com.mappls.sdk.demo.java.model.GeofenceDetail;
import com.mappls.sdk.geojson.LineString;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.geojson.Polygon;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.geometry.LatLngBounds;
import com.mappls.sdk.maps.utils.BitmapUtils;
import com.mappls.sdk.plugin.annotation.Fill;
import com.mappls.sdk.plugin.annotation.FillManager;
import com.mappls.sdk.plugin.annotation.FillOptions;
import com.mappls.sdk.plugin.annotation.Line;
import com.mappls.sdk.plugin.annotation.LineManager;
import com.mappls.sdk.plugin.annotation.LineOptions;
import com.mappls.sdk.plugin.annotation.Symbol;
import com.mappls.sdk.plugin.annotation.SymbolManager;
import com.mappls.sdk.plugin.annotation.SymbolOptions;
import com.mappls.sdk.turf.TurfConstants;
import com.mappls.sdk.turf.TurfTransformation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GeoFenceDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static AtomicInteger ID = new AtomicInteger(0);
    private List<GeofenceDetail> geofenceDetails = new ArrayList<>();
    private GeoFenceDetailAdapter geoFenceDetailAdapter = new GeoFenceDetailAdapter();
    private SymbolManager symbolManager;
    private FillManager fillManager;
    private LineManager lineManager;
    private MapplsMap mapplsMap;
    private List<Symbol> symbols = new ArrayList<>();
    private List<Fill> fillList = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    ActivityGeoFenceDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_geo_fence_detail);
        mBinding.mapView.onCreate(savedInstanceState);
        mBinding.rvGeofenceDetail.setLayoutManager(new LinearLayoutManager(GeoFenceDetailActivity.this));
        mBinding.rvGeofenceDetail.setAdapter(geoFenceDetailAdapter);
        geoFenceDetailAdapter.setOnGeoFenceChangeListener(new GeoFenceDetailAdapter.OnGeoFenceChangeListener() {
            @Override
            public void onGeoFenceStatusChange() {
                updateCamera();
            }

            @Override
            public void onEditGeoFence(GeofenceDetail geofenceDetail) {
                Intent intent = new Intent(GeoFenceDetailActivity.this, GeoFenceCustomActivity.class);
                intent.putExtra("Geofence", new Gson().toJson(geofenceDetail));
                startActivityForResult(intent, 101);
            }

            @Override
            public void onRemoveGeofence(GeofenceDetail geofenceDetail) {
                removeGeofence(geofenceDetail);
                geofenceDetails.remove(geofenceDetail);
                geoFenceDetailAdapter.setGeofenceDetailList(geofenceDetails);
            }
        });
        mBinding.mapView.getMapAsync(this);
        mBinding.tvAddGeofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (geofenceDetails.size() >= 5) {
                    Toast.makeText(GeoFenceDetailActivity.this, "You have created 5 Gofences", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivityForResult(new Intent(GeoFenceDetailActivity.this, GeoFenceCustomActivity.class), 101);
            }
        });
    }

    private void removeGeofence(GeofenceDetail geofenceDetail) {
        List<Symbol> removeSymbol = new ArrayList<>();
        for(Symbol symbol: symbols) {
            JsonObject jsonObject = (JsonObject) symbol.getData();
            if(jsonObject != null && jsonObject.has("GeoFencetype")) {
                if(jsonObject.get("GeoFencetype").getAsString().equalsIgnoreCase(geofenceDetail.getGfLabel())) {
                    symbolManager.clear(symbol);
                    removeSymbol.add(symbol);
                }
            }
        }

        symbols.removeAll(removeSymbol);

        List<Fill> removeFill = new ArrayList<>();
        for(Fill fill: fillList) {
            JsonObject jsonObject = (JsonObject) fill.getData();
            if(jsonObject != null && jsonObject.has("GeoFencetype")) {
                if(jsonObject.get("GeoFencetype").getAsString().equalsIgnoreCase(geofenceDetail.getGfLabel())) {
                    fillManager.clear(fill);
                    removeFill.add(fill);
                }
            }
        }

        fillList.removeAll(removeFill);

        List<Line> removeLine = new ArrayList<>();
        for(Line line: lines) {
            JsonObject jsonObject = (JsonObject) line.getData();
            if(jsonObject != null && jsonObject.has("GeoFencetype")) {
                if(jsonObject.get("GeoFencetype").getAsString().equalsIgnoreCase(geofenceDetail.getGfLabel())) {
                    lineManager.clear(line);
                    removeLine.add(line);
                }
            }
        }

        lines.removeAll(removeLine);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                GeofenceDetail newGeofenceDetail = new Gson().fromJson(data.getStringExtra("GEOFENCE"), GeofenceDetail.class);
                mapplsMap.getStyle(new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        if (newGeofenceDetail.getGfLabel() == null) {
                            newGeofenceDetail.setGfLabel("GeoFence" + ID.getAndIncrement());
                            geofenceDetails.add(newGeofenceDetail);
                            geoFenceDetailAdapter.setGeofenceDetailList(geofenceDetails);

                            if (fillManager == null) {
                                fillManager = new FillManager(mBinding.mapView, mapplsMap, style);
                            }
                            if (lineManager == null) {
                                lineManager = new LineManager(mBinding.mapView, mapplsMap, style);
                            }
                            if (symbolManager == null) {
                                symbolManager = new SymbolManager(mBinding.mapView, mapplsMap, style);
                            }
                            addGeoFence(newGeofenceDetail);
                        } else {
                            for (GeofenceDetail geofenceDetail : geofenceDetails) {
                                if (geofenceDetail.getGfLabel().equalsIgnoreCase(newGeofenceDetail.getGfLabel())) {
                                    updateGeoFenceData(geofenceDetail, newGeofenceDetail);
                                    updateGeoFence(geofenceDetail);
                                }
                            }
                        }
                    }
                });

                updateCamera();

            }
        }
    }

    private void updateGeoFence(GeofenceDetail geofenceDetail) {
        List<Symbol> clearSymbols = new ArrayList<>();
        for(Symbol symbol: symbols) {
            JsonObject jsonObject = (JsonObject) symbol.getData();
            if(jsonObject.has("GeoFencetype")) {
                if(jsonObject.get("GeoFencetype").getAsString().equalsIgnoreCase(geofenceDetail.getGfLabel())) {
                    symbolManager.clear(symbol);
                    clearSymbols.add(symbol);
                }
            }
        }
        symbols.removeAll(clearSymbols);
        addIcons(geofenceDetail);
        Polygon polygon;
        if (geofenceDetail.getGfType().equalsIgnoreCase(GeofenceDetail.TYPE_POLYGON)) {
            List<List<Point>> pointsList = new ArrayList<>();

            pointsList.add(geofenceDetail.getGPS());
            polygon = Polygon.fromLngLats(pointsList);
        } else {
            polygon = TurfTransformation.circle(Point.fromLngLat(geofenceDetail.getCircleCentre().getLongitude(), geofenceDetail.getCircleCentre().getLatitude()), geofenceDetail.getCRadius(), TurfConstants.UNIT_METERS);
        }

        for(Fill fill: fillList) {
            JsonObject jsonObject = (JsonObject) fill.getData();
            if(jsonObject.has("GeoFencetype")) {
                if(jsonObject.get("GeoFencetype").getAsString().equalsIgnoreCase(geofenceDetail.getGfLabel())) {
                    fill.setGeometry(polygon);
                    fillManager.update(fill);
                }
            }
        }

        for(Line line: lines) {
            JsonObject jsonObject = (JsonObject) line.getData();
            if(jsonObject.has("GeoFencetype")) {
                if(jsonObject.get("GeoFencetype").getAsString().equalsIgnoreCase(geofenceDetail.getGfLabel())) {
                    line.setGeometry(LineString.fromLngLats(polygon.coordinates().get(0)));
                    lineManager.update(line);
                }
            }
        }
    }

    private void addIcons(GeofenceDetail geofenceDetail) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("GeoFencetype", geofenceDetail.getGfLabel());
        if(geofenceDetail.getGfType().equalsIgnoreCase(GeofenceDetail.TYPE_POLYGON)) {
            addPolygonEdges(geofenceDetail.getGPS(), jsonObject);
        } else {
            SymbolOptions symbolOptions = new SymbolOptions().position(geofenceDetail.getCircleCentre())
                    .icon(BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder)))
                    .data(jsonObject);
            Symbol symbol = symbolManager.create(symbolOptions);
            symbols.add(symbol);
        }
    }

    private void updateGeoFenceData(GeofenceDetail geofenceDetail, GeofenceDetail newGeofenceDetail) {
        geofenceDetail.setGPS(newGeofenceDetail.getGPS());
        geofenceDetail.setActive(newGeofenceDetail.getActive());
        geofenceDetail.setCRadius(newGeofenceDetail.getCRadius());
        geofenceDetail.setCircleCentre(newGeofenceDetail.getCircleCentre());
        geofenceDetail.setGfType(newGeofenceDetail.getGfType());
    }

    private void addGeoFence(GeofenceDetail geofenceDetail) {
        Polygon polygon;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("GeoFencetype", geofenceDetail.getGfLabel());
        if (geofenceDetail.getGfType().equalsIgnoreCase(GeofenceDetail.TYPE_POLYGON)) {
            addPolygonEdges(geofenceDetail.getGPS(), jsonObject);
            List<List<Point>> pointsList = new ArrayList<>();

            pointsList.add(geofenceDetail.getGPS());
            polygon = Polygon.fromLngLats(pointsList);
        } else {
            polygon = TurfTransformation.circle(Point.fromLngLat(geofenceDetail.getCircleCentre().getLongitude(), geofenceDetail.getCircleCentre().getLatitude()), geofenceDetail.getCRadius(), TurfConstants.UNIT_METERS);

            SymbolOptions symbolOptions = new SymbolOptions().position(geofenceDetail.getCircleCentre())
                    .icon(BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder)))
                    .data(jsonObject);
            Symbol symbol = symbolManager.create(symbolOptions);
            symbols.add(symbol);

        }
        List<LatLng> latLngs = new ArrayList<>();
        for (Point point : polygon.coordinates().get(0)) {
            latLngs.add(new LatLng(point.latitude(), point.longitude()));
        }
        FillOptions fillOptions = new FillOptions()
                .fillColor("#D81B60")
                .fillOpacity(0.5f)
                .geometry(polygon)
                .data(jsonObject);

        Fill fill = fillManager.create(fillOptions);
        fillList.add(fill);

        List<Point> points = polygon.coordinates().get(0);
        if (geofenceDetail.getGfType().equalsIgnoreCase(GeofenceDetail.TYPE_CIRCLE)) {
            points.add(points.get(0));
        }
        LineOptions lineOptions = new LineOptions()
                .lineColor("#511050")
                .lineOpacity(0.9f)
                .geometry(LineString.fromLngLats(points))
                .data(jsonObject);
        Line line = lineManager.create(lineOptions);
        lines.add(line);

    }

    private void clear() {
        symbolManager.clearAll();
        fillManager.clearAll();
        lineManager.clearAll();
    }

    private void updateCamera() {
        List<Point> points = new ArrayList<>();
        for (GeofenceDetail geofenceDetail : geofenceDetails) {
            if (geofenceDetail.getActive()) {
                if (geofenceDetail.getGfType().equalsIgnoreCase(GeofenceDetail.TYPE_POLYGON)) {
                    points.addAll(geofenceDetail.getGPS());
                } else {
                    Polygon polygon = TurfTransformation.circle(Point.fromLngLat(geofenceDetail.getCircleCentre().getLongitude(), geofenceDetail.getCircleCentre().getLatitude()), geofenceDetail.getCRadius(), TurfConstants.UNIT_METERS);
                    points.addAll(polygon.coordinates().get(0));

                }
            }
        }
        List<LatLng> latLngBound = new ArrayList<>();
        for (Point point : points) {
            latLngBound.add(new LatLng(point.latitude(), point.longitude()));
        }
        setBounds(latLngBound);
    }

    private void addPolygonEdges(List<Point> points, JsonObject jsonObject) {
        if (symbolManager != null) {

            List<SymbolOptions> symbolOptions = new ArrayList<>();
            for (Point point : points) {
                symbolOptions.add(new SymbolOptions().geometry(point).data(jsonObject).icon(BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(this, com.mappls.sdk.maps.R.drawable.mappls_maps_marker_icon_default))));
            }
            List<Symbol> symbols = symbolManager.create(symbolOptions);
            this.symbols.addAll(symbols);
        }
    }

    private void setBounds(List<LatLng> latLngBound) {
        if (latLngBound.size() == 0) {
            return;
        }
        if (latLngBound.size() == 1) {
            mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngBound.get(0), 16));
        } else {
            mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder()
                    .includes(latLngBound).build(), 70, 70, 70, mBinding.rootBottomSheet.getHeight()));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.mapView.onDestroy();
        if (symbolManager != null) {
            symbolManager.onDestroy();
        }
        if (lineManager != null) {
            lineManager.onDestroy();
        }
        if (fillManager != null) {
            fillManager.onDestroy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }


    @Override
    public void onMapReady(MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;


    }

    @Override
    public void onMapError(int i, String s) {

    }
}