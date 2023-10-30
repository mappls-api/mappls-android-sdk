package com.mappls.sdk.demo.java.plugin;

import static com.mappls.sdk.maps.style.layers.Property.LINE_CAP_ROUND;
import static com.mappls.sdk.maps.style.layers.Property.LINE_JOIN_ROUND;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineCap;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineColor;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineJoin;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineOpacity;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineWidth;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.mappls.sdk.geojson.Feature;
import com.mappls.sdk.geojson.FeatureCollection;
import com.mappls.sdk.geojson.LineString;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.style.layers.LineLayer;
import com.mappls.sdk.maps.style.sources.GeoJsonSource;
import com.mappls.sdk.services.api.directions.models.LegStep;
import com.mappls.sdk.services.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class SnakePolyLinePlugin implements MapView.OnDidFinishLoadingStyleListener {

    private MapView mapView;
    private MapplsMap mapplsMap;
    private static final float NAVIGATION_LINE_WIDTH = 6;
    private static final float NAVIGATION_LINE_OPACITY = .8f;
    private static final String DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID = "DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID";
    private static final String DRIVING_ROUTE_POLYLINE_SOURCE_ID = "DRIVING_ROUTE_POLYLINE_SOURCE_ID";
    private static final int DRAW_SPEED_MILLISECONDS = 500;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<LegStep> legSteps;
    private Runnable runnable;

    public SnakePolyLinePlugin(MapView mapView, MapplsMap mapplsMap) {
        this.mapView = mapView;
        this.mapplsMap = mapplsMap;
        mapView.addOnDidFinishLoadingStyleListener(this);
        initialiseSourceAndLayer();
    }

    private void initialiseSourceAndLayer() {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addSource(style);
                addLayer(style);
            }
        });

    }

    private void addLayer(Style style) {
        if(style.getLayer(DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID) == null) {
            style.addLayer(new LineLayer(DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID,
                    DRIVING_ROUTE_POLYLINE_SOURCE_ID)
                    .withProperties(
                            lineWidth(NAVIGATION_LINE_WIDTH),
                            lineOpacity(NAVIGATION_LINE_OPACITY),
                            lineCap(LINE_CAP_ROUND),
                            lineJoin(LINE_JOIN_ROUND),
                            lineColor(Color.BLUE)
                    ));
        }
    }


    private void addSource(Style style) {
        if(style.getSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID) == null) {
            style.addSource(new GeoJsonSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID));
        }
    }


    public void create(List<LegStep> legSteps) {
        this.legSteps = legSteps;
        // Start the step-by-step process of drawing the route
        runnable = new DrawRouteRunnable(mapplsMap,legSteps, handler);
        handler.postDelayed(runnable, DRAW_SPEED_MILLISECONDS);
    }

    public void removeCallback() {
        if(runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }



    @Override
    public void onDidFinishLoadingStyle() {
        handler.removeCallbacks(runnable);
        runnable = new DrawRouteRunnable(mapplsMap,legSteps, handler);
        handler.postDelayed(runnable, DRAW_SPEED_MILLISECONDS);
    }

    private static class DrawRouteRunnable implements Runnable {
        private MapplsMap mapplsMap;
        private List<LegStep> steps;
        private List<Feature> drivingRoutePolyLineFeatureList;
        private Handler handler;
        private int counterIndex;

        DrawRouteRunnable(MapplsMap mapplsMap, List<LegStep> steps, Handler handler) {
            this.mapplsMap = mapplsMap;
            this.steps = steps;
            this.handler = handler;
            this.counterIndex = 0;
            drivingRoutePolyLineFeatureList = new ArrayList<>();
        }

        @Override
        public void run() {
            if(steps==null)
                return;
            if (  counterIndex < steps.size()) {
                LegStep singleStep = steps.get(counterIndex);
                if (singleStep != null && singleStep.geometry() != null) {
                    LineString lineStringRepresentingSingleStep = LineString.fromPolyline(
                            singleStep.geometry(), Constants.PRECISION_6);
                    Feature featureLineString = Feature.fromGeometry(lineStringRepresentingSingleStep);
                    drivingRoutePolyLineFeatureList.add(featureLineString);
                }
                if (mapplsMap != null) {
                    mapplsMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            GeoJsonSource source = style.getSourceAs(DRIVING_ROUTE_POLYLINE_SOURCE_ID);
                            if (source != null) {
                                source.setGeoJson(FeatureCollection.fromFeatures(drivingRoutePolyLineFeatureList));
                            }
                        }
                    });

                }
                counterIndex++;
                handler.postDelayed(this, DRAW_SPEED_MILLISECONDS);
            }
        }
    }
}
