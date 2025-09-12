## **Important Note** – Please read first

> The [main](https://github.com/mappls-api/mappls-android-sdk/tree/main) branch contains the documentation for releases using the updated Authorization & Authentication mechanism introduced in August 2025.  
> If you wish to use the releases that use the legacy authentication method based on OAuth 2.0, please refer to the [auth-legacy](https://github.com/mappls-api/mappls-android-sdk/tree/auth-legacy) branch.
<br>

[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Android Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [Mappls Dashboard](https://auth.mappls.com/console)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [200+ countries & territories](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Documentation History]()

| Version                         | Supported SDK Version                                                                                                                                                                                                                                                                                               | 
|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [v2.0.0](docs/v2.0.0/README.md) | - Map SDK v9.0.0 <br/> - Geo Analytics v2.0.0 <br/> - Place Search Widget v3.0.0 <br/> - GeoFence Widget v2.0.0 <br/> - Scalebar Plugin v2.0.0 <br/> - Direction Widget v3.0.0 <br/> - Nearby UI Widget v2.0.0 <br/> - Annotation Plugin v2.0.0 <br/> - Driving Range Plugin v2.0.0 <br/> - Raster Catalogue v2.0.0 <br/> - Feedback UI v4.0.0 |

For more details of previous documentation versions , [click here](docs/v2.0.0/Doc-Version-History.md).


## [Table Of Content]()

- [Getting Started](docs/v2.0.0/Getting-Started.md)
    * [Add Mappls Repository](docs/v2.0.0/Add-Mappls-SDK.md#add-mappls-repository)
    * [Adding Mappls Configuration file](docs/v2.0.0/Add-Mappls-SDK.md#adding-mappls-configuration-file)
    * [Import Mappls BoM](docs/v2.0.0/Add-Mappls-SDK.md#import-mappls-bom)
- [Mappls Map SDK](docs/v2.0.0/Mappls-Map-SDK.md)
    - [Version History](docs/v2.0.0/Mappls-Map-SDK.md#version-history)
    - [Add Mappls Map Dependency](docs/v2.0.0/Mappls-Map-SDK.md#add-mappls-map-dependency)
    - [Initialize Mappls Map SDK](docs/v2.0.0/Mappls-Map-SDK.md#initialize-mappls-map-sdk)
    - [Adding Mappls Map](docs/v2.0.0/Add-Mappls-Map.md)
        * [Using MapView](docs/v2.0.0/Add-Mappls-Map.md#using-mapview)
        * [Using Support Map Fragment](docs/v2.0.0/Add-Mappls-Map.md#using-support-map-fragment)
    - [Map Gestures](docs/v2.0.0/Map-Gestures.md)  
        * [Target](docs/v2.0.0/Map-Gestures.md#target)
        * [Tilt](docs/v2.0.0/Map-Gestures.md#tilt)
        * [Bearing](docs/v2.0.0/Map-Gestures.md#bearing)
        * [Zoom](docs/v2.0.0/Map-Gestures.md#zoom)    
    - [Map Camera](docs/v2.0.0/Map-Camera.md)
        * [Camera Position](docs/v2.0.0/Map-Camera.md#camera-position)
        * [Camera Animations](docs/v2.0.0/Map-Camera.md#camera-animations)
            - [Move Camera](docs/v2.0.0/Map-Camera.md#move-camera)
            - [Ease Camera](docs/v2.0.0/Map-Camera.md#ease-camera)
            - [Animate Camera](docs/v2.0.0/Map-Camera.md#animate-camera)
    - [Camera Controls](docs/v2.0.0/Camera-Control.md)
        * [Move To Target](docs/v2.0.0/Camera-Control.md#move-to-target)
        * [Move To Target with Zoom](docs/v2.0.0/Camera-Control.md#move-to-target-with-zoom)
        * [Move To Target with Padding](docs/v2.0.0/Camera-Control.md#move-to-target-with-zoom)
        * [Move To Bound](docs/v2.0.0/Camera-Control.md#move-to-bound)
        * [Zoom To](docs/v2.0.0/Camera-Control.md#zoom-to)
        * [Zoom By](docs/v2.0.0/Camera-Control.md#zoom-by)
        * [Zoom In](docs/v2.0.0/Camera-Control.md#zoom-in)
        * [Zoom Out](docs/v2.0.0/Camera-Control.md#zoom-out)
        * [Bearing To](docs/v2.0.0/Camera-Control.md#bearing-to)
        * [Tilt To](docs/v2.0.0/Camera-Control.md#tilt-to)
        * [Padding To](docs/v2.0.0/Camera-Control.md#padding-to)
    - [Map Events](docs/v2.0.0/Map-Events.md)
        * [Map Click Event](docs/v2.0.0/Map-Events.md#map-click-event)
        * [Map Long Click Event](docs/v2.0.0/Map-Events.md#map-long-click-event)
        * [Camera Events](docs/v2.0.0/Map-Events.md#camera-events)
            - [Camera Movement Ended](docs/v2.0.0/Map-Events.md#camera-movement-ended)
            - [Camera Movement Canceled](docs/v2.0.0/Map-Events.md#camera-movement-canceled)
            - [Camera Movement Started](docs/v2.0.0/Map-Events.md#camera-movement-started)
            - [Camera Movement Changes](docs/v2.0.0/Map-Events.md#camera-movement-changes)
    - [Map Overlays](docs/v2.0.0/Map-Overlays.md)
        * [Marker](docs/v2.0.0/Map-Overlays.md#marker)
            - [Add Marker](docs/v2.0.0/Map-Overlays.md#add-marker)
            - [Add Marker with Infowindow](docs/v2.0.0/Map-Overlays.md#add-marker-with-infowindow)
            - [Custom Marker](docs/v2.0.0/Map-Overlays.md#custom-marker)
            - [Custom InfoWindow](docs/v2.0.0/Map-Overlays.md#custom-infowindow)
            - [Remove Marker](docs/v2.0.0/Map-Overlays.md#remove-marker)
        * [Polyline](docs/v2.0.0/Map-Overlays.md#polyline)
            - [Add polyline](docs/v2.0.0/Map-Overlays.md#add-polyline)
            - [Remove Polyline](docs/v2.0.0/Map-Overlays.md#remove-polyline)
        * [Polygon](docs/v2.0.0/Map-Overlays.md#polygon)
            - [Add Polygon](docs/v2.0.0/Map-Overlays.md#add-polygon)
            - [Remove Polygon](docs/v2.0.0/Map-Overlays.md#remove-polygon)
        * [Clear All Overlay](docs/v2.0.0/Map-Overlays.md#clear-all-overlay)
    - [Show Current Location](docs/v2.0.0/Show-Current-Location.md)
        * [Activate Location Component](docs/v2.0.0/Show-Current-Location.md#activate-location-component)
        * [Check Location Component is Activated](docs/v2.0.0/Show-Current-Location.md#check-location-component-is-activated)
        * [Show/Hide Current Location On Map](./Show-Current-Location.md#showhide-current-location-on-map)
        * [Change Location Icon](docs/v2.0.0/Show-Current-Location.md#change-location-icon)
        * [Get Location Engine](docs/v2.0.0/Show-Current-Location.md#get-location-engine)
        * [Get Last Location](docs/v2.0.0/Show-Current-Location.md#get-last-location)
        * [Location Update Callback](docs/v2.0.0/Show-Current-Location.md#location-update-callback)
        * [Camera Mode](docs/v2.0.0/Show-Current-Location.md#camera-mode)
        * [Render Mode](docs/v2.0.0/Show-Current-Location.md#render-mode)
    - [Map UI Settings](docs/v2.0.0/Map-UI-Settings.md)
        * [Compass Settings](docs/v2.0.0/Map-UI-Settings.md#compass-settings)
            - [Enable/ Disable compass](docs/v2.0.0/Map-UI-Settings.md#enable-disable-compass)
            - [Enable/Disable Fading compass when facing north](docs/v2.0.0/Map-UI-Settings.md#enabledisable-fading-compass-when-facing-north)
            - [Gravity of Compass](docs/v2.0.0/Map-UI-Settings.md#gravity-of-compass)
            - [Margins of compass](docs/v2.0.0/Map-UI-Settings.md#margins-of-compass)
        * [Logo Settings](docs/v2.0.0/Map-UI-Settings.md#logo-settings)
            - [Gravity of Logo](docs/v2.0.0/Map-UI-Settings.md#gravity-of-logo)
            - [Margin of Logo](docs/v2.0.0/Map-UI-Settings.md#margin-of-logo)
        * [Enable/Disable Double Tap Zoom](docs/v2.0.0/Map-UI-Settings.md#enabledisable-double-tap-zoom)
        * [Enable/ Disable Map Rotation Gesture](docs/v2.0.0/Map-UI-Settings.md#enable-disable-map-rotation-gesture)
        * [Enable/Disable Map Scrolling Gesture](docs/v2.0.0/Map-UI-Settings.md#enabledisable-map-scrolling-gesture)
        * [Enable/ Disable Map Tilt Gesture](docs/v2.0.0/Map-UI-Settings.md#enable-disable-map-tilt-gesture)
        * [Enable/Disable Zoom Gesture](docs/v2.0.0/Map-UI-Settings.md#enabledisable-zoom-gesture)
    - [Mappls Map style](docs/v2.0.0/Map-Style.md)
        * [List of Available Styles](docs/v2.0.0/Map-Style.md#list-of-available-styles)
        * [Set Mappls Style](docs/v2.0.0/Map-Style.md#set-mappls-style)
        * [To enable/disable last selected style](docs/v2.0.0/Map-Style.md#to-enabledisable-last-selected-style)
        * [Get Selected style](docs/v2.0.0/Map-Style.md#get-selected-style)
    - [Mappls Traffic Overlay](docs/v2.0.0/Traffic-Vector-Overlay.md)
    - [SDK All Methods List](docs/v2.0.0/sdk_methods.md)
    - [SDK Error Codes](docs/v2.0.0/SDK-Error-code.md)
- [Mappls Rest Apis](docs/v2.0.0/Rest-Apis.md)
    - [Search Api](docs/v2.0.0/Search-Api.md)
        * [Autosuggest](docs/v2.0.0/Search-Api.md#auto-suggest)
        * [Geocoding](docs/v2.0.0/Search-Api.md#geocoding)
        * [Reverse Geocoding](docs/v2.0.0/Search-Api.md#reverse-geocoding)
        * [Nearby Places](docs/v2.0.0/Search-Api.md#nearby-places)
        * [Place Details](docs/v2.0.0/Search-Api.md#place-details)
        * [POI Along the Route](docs/v2.0.0/Search-Api.md#poi-along-the-route)
    - [Routes & Navigation API](docs/v2.0.0/Routing-API.md)
        * [Routing API](docs/v2.0.0/Routing-API.md#routing-api)
        * [Driving Distance Matrix API](docs/v2.0.0/Routing-API.md#driving-distance-matrix-api)
    - [Predictive Routing APIs](docs/v2.0.0/Predictive-Route-APIs.md)
        * [Predictive Routing API](docs/v2.0.0/Predictive-Route-APIs.md#predictive-routing-api)
        * [Predictive Distance](docs/v2.0.0/Predictive-Route-APIs.md#predictive-distance)
    - [Nearby Reports](docs/v2.0.0/Nearby-Report.md)
    - [Weather API](docs/v2.0.0/Weather-API.md)
    - [Trip Cost Estimation](docs/v2.0.0/Trip-Cost-Estimation.md)
    - [Mappls Geolocation API](docs/v2.0.0/Geolocation.md)
    - [Route Report Summary](docs/v2.0.0/Route-Report-Summary.md)
    - [Set Country Regions](docs/v2.0.0/Set-Regions.md)
    - [Mappls DIGIPIN](docs/v2.0.0/DIGIPIN.md)
- [Annotation Plugin](docs/v2.0.0/AnnotationPlugin.md)
- [Mappls Scalebar Plugin](docs/v2.0.0/Scalebar-Plugin.md)
- [Raster Catalogue](docs/v2.0.0/Raster-Catalogue.md)
- [Mappls GeoAnalytics](docs/v2.0.0/Geoanalytics.md)
- [Place Autocomplete](docs/v2.0.0/Place-Autocomplete.md)
    - [PlaceAutocompleteFragment](docs/v2.0.0/Place-Autocomplete.md#placeautocompletefragment)
    - [PlaceAutocompleteActivity](docs/v2.0.0/Place-Autocomplete.md#placeautocompleteactivity)
- [Place Picker](docs/v2.0.0/Place-Picker.md)
- [Direction Widget](docs/v2.0.0/Direction-Widget.md)
- [Mappls Feedback UI](docs/v2.0.0/FeedbackUI.md)
    * [Using FeedbackFragment](docs/v2.0.0/FeedbackUI.md#using-feedbackfragment)
    * [Using FeedbackActivity](docs/v2.0.0/FeedbackUI.md#using-feedbackactivity)
- [Mappls Nearby Search Widget](docs/v2.0.0/Nearby-Widget.md)
    * [Using MapplsNearbyFragment](docs/v2.0.0/Nearby-Widget.md#using-mapplsnearbyfragment)
    * [Using MapplsNearbyActivity](docs/v2.0.0/Nearby-Widget.md#using-mapplsnearbyactivity)
- [Mappls GeoFence View](docs/v2.0.0/GeoFence-View.md)
- [Mappls BOM to library version mapping](docs/v2.0.0/Bom-Library-Mapping.md)
- [Country List](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md)

<br><br><br>

For any queries and support, please contact:

[<img src="https://about.mappls.com/images/mappls-logo.svg" height="40"/> </p>](https://about.mappls.com/api/)    
Email us at [apisupport@mappls.com](mailto:apisupport@mappls.com)


![](https://www.mapmyindia.com/api/img/icons/support.png)    
[Support](https://about.mappls.com/contact/)    
Need support? contact us!

<br></br>    
<br></br>

[<p align="center"> <img src="https://www.mapmyindia.com/api/img/icons/stack-overflow.png"/> ](https://stackoverflow.com/questions/tagged/mappls-api)[![](https://www.mapmyindia.com/api/img/icons/blog.png)](https://about.mappls.com/blog/)[![](https://www.mapmyindia.com/api/img/icons/gethub.png)](https://github.com/Mappls-api)[<img src="https://mmi-api-team.s3.ap-south-1.amazonaws.com/API-Team/npm-logo.one-third%5B1%5D.png" height="40"/> </p>](https://www.npmjs.com/org/mapmyindia)



[<p align="center"> <img src="https://www.mapmyindia.com/june-newsletter/icon4.png"/> ](https://www.facebook.com/Mapplsofficial)[![](https://www.mapmyindia.com/june-newsletter/icon2.png)](https://twitter.com/mappls)[![](https://www.mapmyindia.com/newsletter/2017/aug/llinkedin.png)](https://www.linkedin.com/company/mappls/)[![](https://www.mapmyindia.com/june-newsletter/icon3.png)](https://www.youtube.com/channel/UCAWvWsh-dZLLeUU7_J9HiOA)




<div align="center">@ Copyright 2025 CE Info Systems Ltd. All Rights Reserved.</div>    

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>    

<div align="center">Customer Care: +91-9999333223</div>
