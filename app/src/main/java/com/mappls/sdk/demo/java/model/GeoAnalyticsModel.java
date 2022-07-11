package com.mappls.sdk.demo.java.model;


import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsRequest;
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsType;

/**
 * * Created by Saksham on 02-06-2021.
 **/
public class GeoAnalyticsModel {

    private final MapplsGeoAnalyticsType type;
    private final MapplsGeoAnalyticsRequest params;
    private final String geoboundType;
    private final String[] geoBound;

    public GeoAnalyticsModel(MapplsGeoAnalyticsType type, MapplsGeoAnalyticsRequest params, String geoboundType, String[] geoBound) {
        this.type = type;
        this.params = params;
        this.geoboundType = geoboundType;
        this.geoBound = geoBound;
    }

    public MapplsGeoAnalyticsType getType() {
        return type;
    }

    public MapplsGeoAnalyticsRequest getParams() {
        return params;
    }

    public String getGeoboundType() {
        return geoboundType;
    }

    public String[] getGeoBound() {
        return geoBound;
    }
}
