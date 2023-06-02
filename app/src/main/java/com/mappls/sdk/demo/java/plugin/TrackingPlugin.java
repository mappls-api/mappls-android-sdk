package com.mappls.sdk.demo.java.plugin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.geojson.Feature;
import com.mappls.sdk.geojson.LineString;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.geojson.utils.PolylineUtils;
import com.mappls.sdk.maps.MapView;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.Style;
import com.mappls.sdk.maps.camera.CameraUpdateFactory;
import com.mappls.sdk.maps.geometry.LatLng;
import com.mappls.sdk.maps.geometry.LatLngBounds;
import com.mappls.sdk.maps.style.layers.LineLayer;
import com.mappls.sdk.maps.style.layers.Property;
import com.mappls.sdk.maps.style.layers.SymbolLayer;
import com.mappls.sdk.maps.style.sources.GeoJsonSource;
import com.mappls.sdk.maps.utils.BitmapUtils;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.directions.DirectionsCriteria;
import com.mappls.sdk.services.api.directions.MapplsDirectionManager;
import com.mappls.sdk.services.api.directions.MapplsDirections;
import com.mappls.sdk.services.api.directions.models.DirectionsResponse;
import com.mappls.sdk.services.api.directions.models.DirectionsRoute;
import com.mappls.sdk.services.utils.Constants;
import com.mappls.sdk.turf.TurfMeasurement;

import static com.mappls.sdk.maps.style.expressions.Expression.get;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.iconImage;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.iconRotate;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.iconRotationAlignment;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineCap;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineColor;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineJoin;
import static com.mappls.sdk.maps.style.layers.PropertyFactory.lineWidth;

import java.util.ArrayList;
import java.util.List;


/**
 ** Created by Saksham on 01-05-2021.
 **/
public class TrackingPlugin implements MapView.OnDidFinishLoadingStyleListener {


    private static final String PROPERTY_BEARING = "bearing";
    private static final String CAR = "car";
    private static final String CAR_LAYER = "car-layer";
    private static final String CAR_SOURCE = "car-source";

    private static final String POLYLINE_LAYER = "polyline-layer";
    private static final String POLYLINE_SOURCE = "polyline-source";

    private MapplsMap mapplsMap;
    private MapView mMapView;
    private Car car;
    private Feature markerFeature;
    private Feature polylineFeature;
    private boolean isClearAllCallBacks;

    @Override
    public void onDidFinishLoadingStyle() {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.addImage(CAR, BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(mMapView.getContext(), R.drawable.ic_bike_icon_grey)));

            }
        });
        updatePolylineSource();
        updateMarkerSource();
    }

    public TrackingPlugin(MapView mapView, MapplsMap mapplsMap) {
        this.mapplsMap = mapplsMap;
        this.mMapView = mapView;
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.addImage(CAR, BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(mapView.getContext(), R.drawable.ic_bike_icon_grey)));
                initialisePolylineLayerAndSource(style);
                initialiseMarkerLayerAndSource(style);
            }
        });

        mapView.addOnDidFinishLoadingStyleListener(this);
    }

    public void addMarker(Point point) {
        markerFeature = Feature.fromGeometry(point);
        markerFeature.addNumberProperty(PROPERTY_BEARING, 0);
        updateMarkerSource();
    }

    public void updatePolyline(DirectionsRoute route) {
        if (route != null && route.geometry() != null) {
            polylineFeature = Feature.fromGeometry(LineString.fromPolyline(route.geometry(), Constants.PRECISION_6));
            updatePolylineSource();
        }
    }

    /**
     * Animate Marker from current point to next point
     */
    public void animateCar(Point start, Point end) {
        isClearAllCallBacks = false;
        LatLng startLatLng = new LatLng(start.latitude(), start.longitude());
        LatLng nextLatLng = new LatLng(end.latitude(), end.longitude());
        car = new Car(startLatLng, nextLatLng);
       callTravelledRoute(start);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new LatLngEvaluator(), startLatLng, nextLatLng);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!isClearAllCallBacks) {

                    if (car.current == null || !car.current.equals(nextLatLng)) {
                        LatLng latLng = (LatLng) animation.getAnimatedValue();
                        markerFeature = Feature.fromGeometry(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
                        car.current = latLng;
                        markerFeature.addNumberProperty(PROPERTY_BEARING, Car.getBearing(car.current, car.next));

                        updateMarkerSource();
                    }
                }
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (!isClearAllCallBacks) {
                    markerFeature.properties().addProperty(PROPERTY_BEARING, Car.getBearing(car.current, car.next));
                }
            }
        });
//        (long) (7 * car.current.distanceTo(car.next))
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }

    /**
     * callTravelledRoute added by priyanka
     */
    private void callTravelledRoute(Point start) {
        MapplsDirections mapplsDirections = MapplsDirections.builder()
                .origin(start)
                .destination(Point.fromLngLat(72.9344, 19.1478))
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .steps(true)
                .routeType(DirectionsCriteria.ROUTE_TYPE_SHORTEST)
                .resource(DirectionsCriteria.RESOURCE_ROUTE)
                .build();
        MapplsDirectionManager.newInstance(mapplsDirections).call(new OnResponseCallback<DirectionsResponse>() {
            @Override
            public void onSuccess(DirectionsResponse directionsResponse) {
                if (directionsResponse != null && directionsResponse.routes() != null && directionsResponse.routes().size() > 0) {
                    DirectionsRoute directionsRoute = directionsResponse.routes().get(0);
                    if (directionsRoute != null && directionsRoute.geometry() != null) {
                        updatePolyline(directionsRoute);
                        List<Point> remainingPath = PolylineUtils.decode(directionsRoute.geometry(), Constants.PRECISION_6);
                        List<LatLng> latLngList = new ArrayList<>();
                        for (Point point : remainingPath) {
                            latLngList.add(new LatLng(point.latitude(), point.longitude()));
                        }
                        if (latLngList.size() > 0) {
                            if (latLngList.size() == 1) {
                                mapplsMap.easeCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(0), 12));
                            } else {
                                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                                        .includes(latLngList)
                                        .build();
                                mapplsMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 180, 0, 180, 0));
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    private void updateMarkerSource() {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if (style.getSource(CAR_SOURCE) == null) {
                    initialiseMarkerLayerAndSource(style);
                    return;
                }
                GeoJsonSource source = (GeoJsonSource) style.getSource(CAR_SOURCE);
                source.setGeoJson(markerFeature);
            }
        });

    }

    private void updatePolylineSource() {
        mapplsMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if (style.getSource(POLYLINE_SOURCE) == null) {
                    initialisePolylineLayerAndSource(style);
                    return;
                }
                GeoJsonSource source = (GeoJsonSource) style.getSource(POLYLINE_SOURCE);
                source.setGeoJson(polylineFeature);
            }
        });

    }


    /**
     * Initialise the marker
     */
    private void initialiseMarkerLayerAndSource(Style style) {
        if (style.getSource(CAR_SOURCE) == null) {
            if (markerFeature == null) {
                style.addSource(new GeoJsonSource(CAR_SOURCE));
            } else {
                style.addSource(new GeoJsonSource(CAR_SOURCE, markerFeature));
            }
        }
        if (style.getLayer(CAR_LAYER) == null) {
            //Symbol layer for car
            SymbolLayer symbolLayer = new SymbolLayer(CAR_LAYER, CAR_SOURCE);
            symbolLayer.withProperties(
                    iconImage(CAR),
                    iconRotate(get(PROPERTY_BEARING)),
                    iconAllowOverlap(true),
                    iconIgnorePlacement(true),
                    iconRotationAlignment(Property.ICON_ROTATION_ALIGNMENT_MAP)

            );
            style.addLayerAbove(symbolLayer, POLYLINE_LAYER);
        }

    }

    private void initialisePolylineLayerAndSource(Style style) {
        if (style.getSource(POLYLINE_SOURCE) == null) {
            style.addSource(new GeoJsonSource(POLYLINE_SOURCE));
        }
        if (style.getLayer(POLYLINE_LAYER) == null) {
            LineLayer lineLayer = new LineLayer(POLYLINE_LAYER, POLYLINE_SOURCE);
            lineLayer.setProperties(
                    lineCap(Property.LINE_CAP_ROUND),
                    lineJoin(Property.LINE_JOIN_ROUND),
                    lineColor(Color.BLACK),
                    lineWidth(4f)
            );
            style.addLayer(lineLayer);
        }

    }


    /**
     * Evaluator for LatLng pairs
     */
    private static class LatLngEvaluator implements TypeEvaluator<LatLng> {

        private LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }


    private static class Car {
        private LatLng next;
        private LatLng current;

        Car(LatLng current, LatLng next) {

            this.current = current;
            this.next = next;
        }



        /**
         * Get the bearing between two points
         *
         * @param from Current point
         * @param to   Next Point
         * @return bearing value in degree
         */
        private static float getBearing(LatLng from, LatLng to) {
            return (float) TurfMeasurement.bearing(
                    Point.fromLngLat(from.getLongitude(), from.getLatitude()),
                    Point.fromLngLat(to.getLongitude(), to.getLatitude())
            );
        }
    }
}
