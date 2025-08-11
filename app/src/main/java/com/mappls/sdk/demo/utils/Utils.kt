package com.mappls.sdk.demo.utils

import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.animation.CarAnimationActivity
import com.mappls.sdk.demo.activity.animation.MarkerRotationActivity
import com.mappls.sdk.demo.activity.animation.TrackingActivity
import com.mappls.sdk.demo.activity.camera.CameraActivity
import com.mappls.sdk.demo.activity.camera.LocationCameraActivity
import com.mappls.sdk.demo.activity.camera.MapplsPinCameraActivity
import com.mappls.sdk.demo.activity.widgets.DirectionWidgetActivity
import com.mappls.sdk.demo.activity.widgets.PlacePickerActivity
import com.mappls.sdk.demo.activity.events.GestureActivity
import com.mappls.sdk.demo.activity.events.MapClickActivity
import com.mappls.sdk.demo.activity.events.MapFragmentActivity
import com.mappls.sdk.demo.activity.events.MapLongClickActivity
import com.mappls.sdk.demo.activity.events.PlaceClickActivity
import com.mappls.sdk.demo.activity.events.StyleActivity
import com.mappls.sdk.demo.activity.events.TrafficActivity
import com.mappls.sdk.demo.activity.layers.GeoAnalyticsActivity
import com.mappls.sdk.demo.activity.utility.DirectionStepActivity
import com.mappls.sdk.demo.activity.layers.HeatMapActivity
import com.mappls.sdk.demo.activity.layers.IndoorActivity
import com.mappls.sdk.demo.activity.layers.ScalebarActivity
import com.mappls.sdk.demo.activity.marker.AddCustomInfoWindowActivity
import com.mappls.sdk.demo.activity.marker.AddMarkerActivity
import com.mappls.sdk.demo.activity.marker.AddMultipleMarkerActivity
import com.mappls.sdk.demo.activity.marker.ClusterActivity
import com.mappls.sdk.demo.activity.marker.CustomClusterActivity
import com.mappls.sdk.demo.activity.marker.MarkerDraggingActivity
import com.mappls.sdk.demo.activity.marker.MarkerViewActivity
import com.mappls.sdk.demo.activity.polyline.AddPolylineActivity
import com.mappls.sdk.demo.activity.polyline.GradientPolylineActivity
import com.mappls.sdk.demo.activity.polyline.SemiCirclePolylineActivity
import com.mappls.sdk.demo.activity.polyline.SnakeMotionPolylineActivity
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.activity.restapis.DirectionsActivity
import com.mappls.sdk.demo.activity.restapis.DistanceActivity
import com.mappls.sdk.demo.activity.layers.DrivingRangeActivity
import com.mappls.sdk.demo.activity.location.CurrentLocationActivity
import com.mappls.sdk.demo.activity.location.CustomCurrentLocationActivity
import com.mappls.sdk.demo.activity.marker.AddCustomMarkerActivity
import com.mappls.sdk.demo.activity.marker.MapplsPinCustomMarkerActivity
import com.mappls.sdk.demo.activity.marker.MapplsPinMarkerActivity
import com.mappls.sdk.demo.activity.polyline.PolygonActivity
import com.mappls.sdk.demo.activity.restapis.GeocodeActivity
import com.mappls.sdk.demo.activity.restapis.HateOsNearbyActivity
import com.mappls.sdk.demo.activity.restapis.NearbyActivity
import com.mappls.sdk.demo.activity.restapis.NearbyReportActivity
import com.mappls.sdk.demo.activity.restapis.PlaceDetailActivity
import com.mappls.sdk.demo.activity.restapis.PoiAlongRouteActivity
import com.mappls.sdk.demo.activity.restapis.PredectiveDistanceActivity
import com.mappls.sdk.demo.activity.restapis.PredictiveDirectionActivity
import com.mappls.sdk.demo.activity.restapis.ReverseGeocodeActivity
import com.mappls.sdk.demo.activity.utility.DigipinActivity
import com.mappls.sdk.demo.activity.widgets.AutoCompleteWidgetActivity
import com.mappls.sdk.demo.activity.widgets.FeedbackUiActivity
import com.mappls.sdk.demo.activity.widgets.GeoFenceActivity
import com.mappls.sdk.demo.activity.widgets.NearbyWidgetActivity
import com.mappls.sdk.demo.model.CategoryModel
import com.mappls.sdk.demo.model.GeoAnalyticsDataModel
import com.mappls.sdk.demo.model.SubCategoryModel
import com.mappls.sdk.demo.plugin.HeatMapPlugin.HeatMapOption
import com.mappls.sdk.geoanalytics.GeoAnalyticsAppearanceOption
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsRequest
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsType
import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.Point

object Utils {

    val EMPTY_FEATURE = FeatureCollection.fromFeatures(listOf())

    fun getCategoryList() = listOf(
        CategoryModel(0, "Map Events", R.drawable.map_event_icon),
        CategoryModel(1, "Map Layers", R.drawable.map_layer_icon),
        CategoryModel(2, "Camera", R.drawable.map_camera_icon),
        CategoryModel(3, "Marker", R.drawable.map_marker_icon),
        CategoryModel(4, "Location", R.drawable.map_location_icon),
        CategoryModel(5, "Polyline", R.drawable.map_polyline_icon),
        CategoryModel(6, "Rest Api Call", R.drawable.map_api_icon),
        CategoryModel(7, "Animation", R.drawable.map_animation_icon),
        CategoryModel(8, "Custom Widgets", R.drawable.map_custom_widget_icon),
        CategoryModel(9, "Utilities", R.drawable.map_custom_widget_icon)
    )

    fun getSubCategoryList(type: Int): List<SubCategoryModel> {
        return when (type) {
            0 -> {
                mapEvents()
            }
            1 -> {
                mapLayers()
            }
            2 -> {
                mapCamera()
            }
            3 -> {
                mapMarker()
            }
            4 -> {
                mapLocation()
            }
            5 -> {
                mapPolyline()
            }
            6 -> {
                restApiCalls()
            }
            7 -> {
                mapAnimation()
            }
            8 -> {
                customWidget()
            }
            9 -> {
                utilities()
            }

            else -> {
                listOf()
            }
        }
    }

    private fun utilities(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            DirectionStepActivity::class.java,
            "Direction Steps",
            "Add Direction steps",
            R.drawable.fragment_events_icon
        ),
        SubCategoryModel(
            DigipinActivity::class.java,
        "Get Digipin",
        "Get Digipin",
        R.drawable.fragment_events_icon
    )
    )

    private fun customWidget(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            AutoCompleteWidgetActivity::class.java,
            "Place Autocomplete Widget",
            "Location search functionality and UI to search a place",
            R.drawable.auto_suggest_api_icon
        ),
        SubCategoryModel(
            PlacePickerActivity::class.java,
            "Place Picker",
            "Place Picker to search and choose a specific location",
            R.drawable.auto_suggest_api_icon
        ),
        SubCategoryModel(
            DirectionWidgetActivity::class.java,
            "DirectionWidget",
            "DirectionWidget to show Route on map",
            R.drawable.auto_suggest_api_icon
        ),
        SubCategoryModel(
            GeoFenceActivity::class.java,
            "GeoFence",
            "Highly customizable UI widget to create/edit geofence widget",
            R.drawable.auto_suggest_api_icon
        ),
        SubCategoryModel(
            NearbyWidgetActivity::class.java,
            "Nearby Widget",
            "Mappls Nearby Widget to search nearby result on map",
            R.drawable.auto_suggest_api_icon
        ),
        SubCategoryModel(
            FeedbackUiActivity::class.java,
            "Feedback Widget",
            "Mappls Feedback Widget to give Feedback",
            R.drawable.auto_suggest_api_icon
        ),
    )

    private fun mapAnimation(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            CarAnimationActivity::class.java,
            "Animate Car",
            "Animation of an car icon on predefined route",
            R.drawable.car_animation_icon
        ),
        SubCategoryModel(
            MarkerRotationActivity::class.java,
            "Marker Rotation and Transition",
            "Rotation of a marker by given degree and animate the marker to a new position",
            R.drawable.car_animation_icon
        ),
        SubCategoryModel(
            TrackingActivity::class.java,
            "Tracking Sample",
            "Track a vehicle on the map",
            R.drawable.tracking_sample_icon
        ),
    )

    private fun restApiCalls(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            AutoSuggestActivity::class.java,
            "Autosuggest",
            "API call request for Autosuggest, displaying results of the searched place",
            R.drawable.auto_suggest_api_icon
        ),
        SubCategoryModel(
            GeocodeActivity::class.java,
            "Geocode",
            "API Call request for Geocode, displaying results of the results",
            R.drawable.geocode_api_icon
        ),
        SubCategoryModel(
            ReverseGeocodeActivity::class.java,
            "Reverse Geocode",
            "API Call request for Rev-Geocode, displaying its results",
            R.drawable.rev_geocode_api_icon
        ),
        SubCategoryModel(
            NearbyActivity::class.java,
            "Nearby",
            "API Call request for NearBy API, displaying its results",
            R.drawable.nearby_api_icon
        ),
        SubCategoryModel(
            DirectionsActivity::class.java,
            "Get Direction",
            "API Call request for get direction , displaying its results",
            R.drawable.direction_api_icon
        ),
        SubCategoryModel(
            DistanceActivity::class.java,
            "Get Distance",
            "API Call request for get distance, displaying its results",
            R.drawable.distance_api_icon
        ),
        SubCategoryModel(
            PredictiveDirectionActivity::class.java,
            "Get Predictive Direction",
            "API Call request for get predictive direction, displaying its results",
            R.drawable.predective_direction_api_icon
        ),
        SubCategoryModel(
            PredectiveDistanceActivity::class.java,
            "Get Predictive Distance",
            "API Call request for get distance, displaying its results",
            R.drawable.predective_distance_api_icon
        ),

        SubCategoryModel(
            HateOsNearbyActivity::class.java,
            "Hateos NearBy API",
            "API Call request for Hateos NearBy API, displaying its results",
            R.drawable.hate_os_nearby_api_icon
        ),
        SubCategoryModel(
            PoiAlongRouteActivity::class.java,
            "POI Along the Route",
            "API Call request for POI Along the Route, displaying its results",
            R.drawable.poi_along_route_api_icon
        ),
        SubCategoryModel(
            PlaceDetailActivity::class.java,
            "Place Detail",
            "API Call request for Place detail, displaying its results",
            R.drawable.place_detail_api_icon
        ),
        SubCategoryModel(
            NearbyReportActivity::class.java,
            "NearBy Report",
            "API Call request for NearBy Report, displaying its results",
            R.drawable.nearby_report_api_icon
        ),
    )

    private fun mapPolyline(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            AddPolylineActivity::class.java,
            "Draw Polyline",
            "Draw a Polyline with the given list of location coordinates (Lat/Long)",
            R.drawable.draw_polyline_icon
        ),
        SubCategoryModel(
            GradientPolylineActivity::class.java,
            "Polyline with gradient color",
            "Draw a Polyline with the given list of location coordinates (Lat/Long) with color gradient",
            R.drawable.draw_polyline_icon
        ),
        SubCategoryModel(
            SemiCirclePolylineActivity::class.java,
            "Semicircle polyline",
            "Functionality to draw a Semi circle polyline on map",
            R.drawable.semi_circle_icon
        ),
        SubCategoryModel(
            SnakeMotionPolylineActivity::class.java,
            "SnakeMotion Polyline",
            "Functionality to draw snake motion polyline on map",
            R.drawable.semi_circle_icon
        ),
        SubCategoryModel(
            PolygonActivity::class.java,
            "Draw Polygon",
            "Functionality to draw and plot a polygon on the map",
            R.drawable.semi_circle_icon
        ),
    )

    private fun mapLocation(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            CurrentLocationActivity::class.java,
            "Current Location",
            "Camera options for Location rendering and tracking modes",
            R.drawable.current_location_icon
        ),
        SubCategoryModel(
            CustomCurrentLocationActivity::class.java,
            "Customize Current Location Icon",
            "Functionality to change default current location",
            R.drawable.custom_location_icon
        )
    )

    private fun mapMarker(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            AddMarkerActivity::class.java,
            "Add a Marker",
            "Add a marker and visualize it on map",
            R.drawable.add_marker_icon
        ),
        SubCategoryModel(
            AddCustomMarkerActivity::class.java,
            "Add Custom Marker",
            "Add a custom marker and visualize it on map",
            R.drawable.custom_marker_icon
        ),
        SubCategoryModel(
            AddCustomInfoWindowActivity::class.java,
            "Add Custom Info Window",
            "Add a custom information window",
            R.drawable.custom_info_window_icon
        ),
        SubCategoryModel(
            MarkerDraggingActivity::class.java,
            "Marker Dragging",
            "Marker dragging functionality",
            R.drawable.custom_info_window_icon
        ),
        SubCategoryModel(
            AddMultipleMarkerActivity::class.java,
            "Add Multiple Marker",
            "Add Multiple Marker on map",
            R.drawable.custom_info_window_icon
        ),
        SubCategoryModel(
            MapplsPinMarkerActivity::class.java,
            "Add Marker Using Mappls Pin",
            "Add a marker using mappls pin functionality",
            R.drawable.custom_info_window_icon
        ),
        SubCategoryModel(
            MapplsPinCustomMarkerActivity::class.java,
            "Add Custom Marker Using Mappls Pin",
            "Add a custom marker using mappls pin functionality",
            R.drawable.custom_info_window_icon
        ),
        SubCategoryModel(
            MarkerViewActivity::class.java,
            "Add Marker View",
            "Add an marker view (Info window) to your markers",
            R.drawable.custom_info_window_icon
        ),
        SubCategoryModel(
            ClusterActivity::class.java,
            "Cluster Marker",
            "Add cluster markers on a map",
            R.drawable.custom_info_window_icon
        ),
//        SubCategoryModel(
//            CustomClusterActivity::class.java,
//            "Add Custom Cluster",
//            "Add Custom Cluster on map",
//            R.drawable.custom_info_window_icon
//        ),
    )

    private fun mapCamera(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            CameraActivity::class.java,
            "Camera Feature",
            "Explore camera features like Move Camera, Ease Camera & Animate Camera",
            R.drawable.camera_feature_icon
        ),
        SubCategoryModel(
            LocationCameraActivity::class.java,
            "Location Camera Options",
            "To change the current location icon and tracking mode",
            R.drawable.location_camera_icon
        ),
        SubCategoryModel(
            MapplsPinCameraActivity::class.java,
            "Camera Features in Mappls Pin",
            "Animate, Move or ease camera using mappls pin",
            R.drawable.mappls_pin_camera_icon
        )
    )

    private fun mapLayers(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            HeatMapActivity::class.java,
            "Heat Map",
            "Add and visualize data in Heat Style",
            R.drawable.heat_map_layer_icon
        ),
        SubCategoryModel(
            IndoorActivity::class.java,
            "Indoor Map",
            "Mappls Indoor Widget to focus on multi-storey buildings structure and floor wise data on map.",
            R.drawable.indoor_layer_icon
        ),
        SubCategoryModel(
            ScalebarActivity::class.java,
            "ScaleBar",
            "Visual tool that represents the relationship between map distances and real-world distances, helping users interpret spatial data accurately.",
            R.drawable.scalebar_layer_icon
        ),
        SubCategoryModel(
            GeoAnalyticsActivity::class.java,
            "GeoAnalytics Plugin",
            "Visualize Administrative Layers on Map as WMS Layers Available with Mappls Database.",
            R.drawable.scalebar_layer_icon
        ),
        SubCategoryModel(
            DrivingRangeActivity::class.java,
            "Driving Range Plugin",
            "A driving range or isochrone of Mappls represents the area that can be reached within a specific time or distance from a starting point, often visualized as a polygon on a map.",
            R.drawable.scalebar_layer_icon
        ),
    )

    private fun mapEvents(): List<SubCategoryModel> = listOf(
        SubCategoryModel(
            MapFragmentActivity::class.java,
            "Map Fragment",
            "Add Map in Fragment",
            R.drawable.fragment_events_icon
        ),
        SubCategoryModel(
            MapClickActivity::class.java,
            "Map Tap",
            "Single tap event on map",
            R.drawable.click_event_icon
        ),
        SubCategoryModel(
            MapLongClickActivity::class.java,
            "Map Long Tap",
            "Long Press event on map",
            R.drawable.long_click_event_icon
        ),
        SubCategoryModel(
            GestureActivity::class.java,
            "Map Gesture",
            "Map events and Map panning",
            R.drawable.long_click_event_icon
        ),
        SubCategoryModel(
            StyleActivity::class.java,
            "Map Style",
            "Check out the diverse map styles Mappls Offer",
            R.drawable.long_click_event_icon
        ),
        SubCategoryModel(
            TrafficActivity::class.java,
            "Map Traffic",
            "Visualize Traffic services on Map",
            R.drawable.long_click_event_icon
        ),
        SubCategoryModel(
            PlaceClickActivity::class.java,
            "Place Tap",
            "Get POI Click Event with the details of the Place",
            R.drawable.long_click_event_icon
        ),
    )

    fun getSubCategoryTitle(type: Int) = when (type) {
        0 -> "Map Events"
        1 -> "Map Layers"
        2 -> "Camera"
        3 -> "Marker"
        4 -> "Location"
        5 -> "Polyline"
        6 -> "Rest Api Call"
        7 -> "Animation"
        8 -> "Custom Widgets"
        9 -> "Utilities"
        else -> ""
    }

    fun validateLatitude(value: String): Boolean {
        val latitude = value.toDoubleOrNull()
        if (latitude == null) {
            return false
        }
        return -90 <= latitude && latitude <= 90.0
    }

    fun validateLongitude(value: String): Boolean {

        val longitude = value.toDoubleOrNull()
        if (longitude == null) {
            return false
        }
        return -180 <= longitude && longitude <= 180
    }

    fun getHeatMapOptionsList() = listOf<HeatMapOption>(
        HeatMapOption(Point.fromLngLat(77.5129, 28.1016), 2.3),
        HeatMapOption(Point.fromLngLat(77.5132, 28.1021), 2.0),
        HeatMapOption(Point.fromLngLat(76.4048, 28.1224), 1.7),
        HeatMapOption(Point.fromLngLat(77.3597, 28.0781), 1.6),
        HeatMapOption(Point.fromLngLat(77.3597, 28.0781), 1.6),
        HeatMapOption(Point.fromLngLat(74.789, 28.1725), 2.4),
        HeatMapOption(Point.fromLngLat(74.2832, 28.674242), 1.3),
        HeatMapOption(Point.fromLngLat(80.8244288, 24.6778728), 2.4),
        HeatMapOption(Point.fromLngLat(81.637417, 24.6778728), 3.2),
        HeatMapOption(Point.fromLngLat(77.7372706, 27.8302397), 1.2),
        HeatMapOption(Point.fromLngLat(79.2973292, 28.3729432), 4.2),
        HeatMapOption(Point.fromLngLat(79.7477686, 26.2351935), 2.13),
        HeatMapOption(Point.fromLngLat(80.1762354, 26.1760509), 3.2),
        HeatMapOption(Point.fromLngLat(79.7367823, 27.333618), 1.2),
        HeatMapOption(Point.fromLngLat(80.9452784, 26.628706), 5.2),
        HeatMapOption(Point.fromLngLat(78.6930811, 28.9032672), 3.2),
        HeatMapOption(Point.fromLngLat(78.73702641, 29.3255866), 4.0),
        HeatMapOption(Point.fromLngLat(79.4181788, 29.5838759), 5.0)
    )

    fun getGeoAnalyticsList() = listOf(
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.STATE,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme")
                .geoBound("HARYANA", "UTTAR PRADESH", "ANDHRA PRADESH", "KERALA")
                .propertyNames("stt_nme", "stt_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("42a5f4").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("HARYANA", "UTTAR PRADESH", "ANDHRA PRADESH", "KERALA")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.DISTRICT,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("BIHAR", "UTTARAKHAND")
                .propertyNames("dst_nme", "dst_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("8bc34a").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("BIHAR", "UTTARAKHAND")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.SUB_DISTRICT,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("HIMACHAL PRADESH", "TRIPURA")
                .propertyNames("sdb_nme", "sdb_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("ffa000").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("HIMACHAL PRADESH", "TRIPURA")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.WARD,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("ward_no").geoBound("0001")
                .propertyNames("ward_no", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("ff5722").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "ward_no",
            arrayOf("0001")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.LOCALITY,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("DELHI")
                .propertyNames("loc_nme", "loc_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("00695c").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("DELHI")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.PANCHAYAT,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("ASSAM")
                .propertyNames("pnc_nme", "pnc_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("b71c1c").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("ASSAM")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.BLOCK,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("DELHI")
                .propertyNames("blk_nme", "blk_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("3f51b5").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("DELHI")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.PINCODE,
            MapplsGeoAnalyticsRequest.builder().geoboundType("stt_nme")
                .geoBound("KARNATAKA")
                .propertyNames("pincode").style(
                    GeoAnalyticsAppearanceOption().fillColor("00bcd4").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("KARNATAKA")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.TOWN,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("PUNJAB")
                .propertyNames("twn_nme", "twn_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("9ccc65").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("PUNJAB")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.CITY,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("TAMIL NADU")
                .propertyNames("city_nme", "city_id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("78909c").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("TAMIL NADU")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.VILLAGE,
            MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                .geoboundType("stt_nme").geoBound("GOA")
                .propertyNames("vil_nme", "id", "t_p").style(
                    GeoAnalyticsAppearanceOption().fillColor("f06292").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("GOA")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.SUB_LOCALITY,
            MapplsGeoAnalyticsRequest.builder().geoboundType("stt_nme")
                .geoBound("UTTAR PRADESH", "BIHAR")
                .propertyNames("subl_nme", "subl_id").style(
                    GeoAnalyticsAppearanceOption().fillColor("f06292").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("UTTAR PRADESH", "BIHAR")
        ),
        GeoAnalyticsDataModel(
            MapplsGeoAnalyticsType.SUB_SUB_LOCALITY,
            MapplsGeoAnalyticsRequest.builder().geoboundType("stt_nme")
                .geoBound("UTTAR PRADESH", "BIHAR")
                .propertyNames("sslc_nme", "sslc_id").style(
                    GeoAnalyticsAppearanceOption().fillColor("f06292").fillOpacity(0.5)
                        .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                        .labelColor("000000")
                ).build(),
            "stt_nme",
            arrayOf("UTTAR PRADESH", "BIHAR")
        )
    )


}