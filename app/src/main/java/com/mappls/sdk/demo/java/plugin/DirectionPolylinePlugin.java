package com.mappls.sdk.demo.java.plugin;

import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineCap;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineColor;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineDasharray;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineJoin;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineWidth;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.mappls.sdk.geojson.Feature;
import com.mappls.sdk.geojson.FeatureCollection;
import com.mappls.sdk.geojson.LineString;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.style.layers.Layer;
import com.mappls.sdk.maps.style.layers.LineLayer;
import com.mappls.sdk.maps.style.layers.Property;
import com.mappls.sdk.maps.style.sources.GeoJsonOptions;
import com.mappls.sdk.maps.style.sources.GeoJsonSource;
import com.mappls.sdk.maps.utils.ColorUtils;
import com.mappls.sdk.services.api.directions.DirectionsCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saksham on 19/9/19.
 */
public class DirectionPolylinePlugin implements MapView.OnDidFinishLoadingStyleListener {
    private static final String UPPER_SOURCE_ID = "line-source-upper-id";

    private MapplsMap mapplsMap;

    private FeatureCollection featureCollection;
    private static final String LAYER_ID = "line-layer-upper-id";
    private List<LatLng> latLngs;

    private float widthDash = 5f;
    private float gapDash = 5f;

    private String directionsCriteria;

    private GeoJsonSource polylineSource;
    private LineLayer lineLayer;

    public DirectionPolylinePlugin(MapplsMap mapplsMap, MapView mapView, String directionsCriteria) {
        this.mapplsMap = mapplsMap;
        this.directionsCriteria = directionsCriteria;

        updateSource();
        mapView.addOnDidFinishLoadingStyleListener(this);
    }

    /**
     * Add polyline features and set polyline property for walk and other
     *
     * @param latLngs list of points
     */
    public void createPolyline(List<LatLng> latLngs) {
        this.latLngs = latLngs;
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                List<Point> points = new ArrayList<>();
                for (LatLng latLng : latLngs) {
                    points.add(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
                }
                Feature features = Feature.fromGeometry(LineString.fromLngLats(points));
                Layer layer = style.getLayer(LAYER_ID);
                if (layer != null) {
                    if (directionsCriteria.equalsIgnoreCase(DirectionsCriteria.PROFILE_WALKING)) {

                        layer.setProperties(lineDasharray(new Float[]{widthDash, gapDash}),
                                lineColor(Color.BLACK));
                    } else {
                        layer.setProperties(lineDasharray(new Float[]{}),
                                lineColor(ColorUtils.colorToRgbaString(Color.parseColor("#3bb2d0"))));
                    }
                }
                featureCollection = FeatureCollection.fromFeature(features);
                initSources(featureCollection);
            }
        });

    }

    /**
     * Update polyline features and set polyline property for walk and other
     *
     * @param directionsCriteria {"foot", "biking", "driving"}
     * @param latLngs            list of points
     */
    public void updatePolyline(String directionsCriteria, List<LatLng> latLngs) {
        this.directionsCriteria = directionsCriteria;
        List<Point> points = new ArrayList<>();
        for (LatLng latLng : latLngs) {
            points.add(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
        }
        Feature features = Feature.fromGeometry(LineString.fromLngLats(points));
        if (directionsCriteria.equalsIgnoreCase(DirectionsCriteria.PROFILE_WALKING)) {
            lineLayer.setProperties(lineDasharray(new Float[]{widthDash, gapDash}),
                    lineColor(Color.BLACK));
        } else {
            lineLayer.setProperties(lineDasharray(new Float[]{}),
                    lineColor(ColorUtils.colorToRgbaString(Color.parseColor("#3bb2d0"))));
        }

        featureCollection = FeatureCollection.fromFeature(features);
        updateSource();
    }

    /**
     * Add various sources to the map.
     */
    private void initSources(@NonNull FeatureCollection featureCollection) {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if (style.getSource(UPPER_SOURCE_ID) == null) {
                    style.addSource(polylineSource = new GeoJsonSource(UPPER_SOURCE_ID, featureCollection,
                            new GeoJsonOptions().withLineMetrics(true).withBuffer(2)));
                }
            }
        });

    }

    /**
     * Update Source and GeoJson properties
     */
    private void updateSource() {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                GeoJsonSource source = (GeoJsonSource) style.getSource(UPPER_SOURCE_ID);
                if (source == null) {
                    create(style);
                    return;
                }
                if (featureCollection != null) {
                    polylineSource.setGeoJson(featureCollection);
                }
            }
        });

    }

    /**
     * Add Line layer on map
     */
    private void create(Style style) {
        if (style.getLayer(LAYER_ID) == null) {
            style.addLayer(lineLayer = new LineLayer(LAYER_ID, UPPER_SOURCE_ID).withProperties(
                    lineCap(Property.LINE_CAP_ROUND),
                    lineJoin(Property.LINE_JOIN_ROUND),
                    lineWidth(5f)));


            if (directionsCriteria.equalsIgnoreCase(DirectionsCriteria.PROFILE_WALKING)) {
                lineLayer.setProperties(lineDasharray(new Float[]{gapDash, widthDash}));
            }
        }
    }


    @Override
    public void onDidFinishLoadingStyle() {
        updateSource();
        createPolyline(latLngs);
    }
}