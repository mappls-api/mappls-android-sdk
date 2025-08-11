## **Important Note** – Please read first

> The [main](https://github.com/mappls-api/mappls-android-sdk/tree/main) branch contains the documentation for releases using the updated Authorization & Authentication mechanism introduced in August 2025.  
> If you wish to use the releases that use the legacy authentication method based on OAuth 2.0, please refer to the [auth-legacy](https://github.com/mappls-api/mappls-android-sdk/tree/auth-legacy) branch.


<br>

[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Android Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [https://about.mappls.com/api/signup](https://about.mappls.com/api/signup)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [200+ countries & territories](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Documentation History]()

| Version                         | Supported SDK Version                                                                                                                                                                                                                                                                                               | 
|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [v2.0.0](../v2.0.0/README.md) | - Map SDK v9.0.0 <br/> - Geo Analytics v2.0.0 <br/> - Place Search Widget v3.0.0 <br/> - GeoFence Widget v2.0.0 <br/> - Scalebar Plugin v2.0.0 <br/> - Direction Widget v3.0.0 <br/> - Nearby UI Widget v2.0.0 <br/> - Annotation Plugin v2.0.0 <br/> - Driving Range Plugin v2.0.0 <br/> - Raster Catalogue v2.0.0 <br/> - Feedback UI v4.0.0 |

For more details of previous documentation versions , [click here](./Doc-Version-History.md).


## [Table Of Content]()
- [Mappls Android SDK](./README.md)
- [Getting Started](./Getting-Started.md)
   - [Add Mappls SDK](./Add-Mappls-SDK.md)
    * [Add Mappls Repository](./Add-Mappls-SDK.md#add-mappls-repository)
    * [Adding Mappls Configuration file](./Add-Mappls-SDK.md#adding-mappls-configuration-file)
    * [Import Mappls BoM](./Add-Mappls-SDK.md#import-mappls-bom)
- [Mappls Map SDK](./Mappls-Map-SDK.md)
    - [Version History](./Mappls-Map-SDK.md#version-history)
    - [Add Mappls Map Dependency](./Mappls-Map-SDK.md#add-mappls-map-dependency)
    - [Initialize Mappls Map SDK](./Mappls-Map-SDK.md#initialize-mappls-map-sdk)
    - [Adding Mappls Map](./Add-Mappls-Map.md)
        * [Using MapView](./Add-Mappls-Map.md#using-mapview)
        * [Using Support Map Fragment](./Add-Mappls-Map.md#using-support-map-fragment)
    - [Map Gestures](./Map-Gestures.md)  
        * [Target](./Map-Gestures.md#target)
        * [Tilt](./Map-Gestures.md#tilt)
        * [Bearing](./Map-Gestures.md#bearing)
        * [Zoom](./Map-Gestures.md#zoom)    
    - [Map Camera](./Map-Camera.md)
        * [Camera Position](./Map-Camera.md#camera-position)
        * [Camera Animations](./Map-Camera.md#camera-animations)
            - [Move Camera](./Map-Camera.md#move-camera)
            - [Ease Camera](./Map-Camera.md#ease-camera)
            - [Animate Camera](./Map-Camera.md#animate-camera)
    - [Camera Controls](./Camera-Control.md)
        * [Move To Target](./Camera-Control.md#move-to-target)
        * [Move To Target with Zoom](./Camera-Control.md#move-to-target-with-zoom)
        * [Move To Target with Padding](./Camera-Control.md#move-to-target-with-zoom)
        * [Move To Bound](./Camera-Control.md#move-to-bound)
        * [Zoom To](./Camera-Control.md#zoom-to)
        * [Zoom By](./Camera-Control.md#zoom-by)
        * [Zoom In](./Camera-Control.md#zoom-in)
        * [Zoom Out](./Camera-Control.md#zoom-out)
        * [Bearing To](./Camera-Control.md#bearing-to)
        * [Tilt To](./Camera-Control.md#tilt-to)
        * [Padding To](./Camera-Control.md#padding-to)
    - [Map Events](./Map-Events.md)
        * [Map Click Event](./Map-Events.md#map-click-event)
        * [Map Long Click Event](./Map-Events.md#map-long-click-event)
        * [Camera Events](./Map-Events.md#camera-events)
            - [Camera Movement Ended](./Map-Events.md#camera-movement-ended)
            - [Camera Movement Canceled](./Map-Events.md#camera-movement-canceled)
            - [Camera Movement Started](./Map-Events.md#camera-movement-started)
            - [Camera Movement Changes](./Map-Events.md#camera-movement-changes)
    - [Map Overlays](./Map-Overlays.md)
        * [Marker](./Map-Overlays.md#marker)
            - [Add Marker](./Map-Overlays.md#add-marker)
            - [Add Marker with Infowindow](./Map-Overlays.md#add-marker-with-infowindow)
            - [Custom Marker](./Map-Overlays.md#custom-marker)
            - [Custom InfoWindow](./Map-Overlays.md#custom-infowindow)
            - [Remove Marker](./Map-Overlays.md#remove-marker)
        * [Polyline](./Map-Overlays.md#polyline)
            - [Add polyline](./Map-Overlays.md#add-polyline)
            - [Remove Polyline](./Map-Overlays.md#remove-polyline)
        * [Polygon](./Map-Overlays.md#polygon)
            - [Add Polygon](./Map-Overlays.md#add-polygon)
            - [Remove Polygon](./Map-Overlays.md#remove-polygon)
        * [Clear All Overlay](./Map-Overlays.md#clear-all-overlay)
    - [Show Current Location](./Show-Current-Location.md)
        * [Activate Location Component](./Show-Current-Location.md#activate-location-component)
        * [Check Location Component is Activated](./Show-Current-Location.md#check-location-component-is-activated)
        * [Show/Hide Current Location On Map](./Show-Current-Location.md#showhide-current-location-on-map)
        * [Change Location Icon](./Show-Current-Location.md#change-location-icon)
        * [Get Location Engine](./Show-Current-Location.md#get-location-engine)
        * [Get Last Location](./Show-Current-Location.md#get-last-location)
        * [Location Update Callback](./Show-Current-Location.md#location-update-callback)
        * [Camera Mode](./Show-Current-Location.md#camera-mode)
        * [Render Mode](./Show-Current-Location.md#render-mode)
    - [Map UI Settings](./Map-UI-Settings.md)
        * [Compass Settings](./Map-UI-Settings.md#compass-settings)
            - [Enable/ Disable compass](./Map-UI-Settings.md#enable-disable-compass)
            - [Enable/Disable Fading compass when facing north](./Map-UI-Settings.md#enabledisable-fading-compass-when-facing-north)
            - [Gravity of Compass](./Map-UI-Settings.md#gravity-of-compass)
            - [Margins of compass](./Map-UI-Settings.md#margins-of-compass)
        * [Logo Settings](./Map-UI-Settings.md#logo-settings)
            - [Gravity of Logo](./Map-UI-Settings.md#gravity-of-logo)
            - [Margin of Logo](./Map-UI-Settings.md#margin-of-logo)
        * [Enable/Disable Double Tap Zoom](./Map-UI-Settings.md#enabledisable-double-tap-zoom)
        * [Enable/ Disable Map Rotation Gesture](./Map-UI-Settings.md#enable-disable-map-rotation-gesture)
        * [Enable/Disable Map Scrolling Gesture](./Map-UI-Settings.md#enabledisable-map-scrolling-gesture)
        * [Enable/ Disable Map Tilt Gesture](./Map-UI-Settings.md#enable-disable-map-tilt-gesture)
        * [Enable/Disable Zoom Gesture](./Map-UI-Settings.md#enabledisable-zoom-gesture)
    - [Mappls Map style](./Map-Style.md)
        * [List of Available Styles](./Map-Style.md#list-of-available-styles)
        * [Set Mappls Style](./Map-Style.md#set-mappls-style)
        * [To enable/disable last selected style](./Map-Style.md#to-enabledisable-last-selected-style)
        * [Get Selected style](./Map-Style.md#get-selected-style)
    - [Mappls Traffic Overlay](./Traffic-Vector-Overlay.md)
    - [SDK All Methods List](./sdk_methods.md)
    - [SDK Error Codes](./SDK-Error-code.md)
- [Mappls Rest Apis](./Rest-Apis.md)
    - [Search Api](./Search-Api.md)
        * [Autosuggest](./Search-Api.md#auto-suggest)
        * [Geocoding](./Search-Api.md#geocoding)
        * [Reverse Geocoding](./Search-Api.md#reverse-geocoding)
        * [Nearby Places](./Search-Api.md#nearby-places)
        * [Place Details](./Search-Api.md#place-details)
        * [POI Along the Route](./Search-Api.md#poi-along-the-route)
    - [Routes & Navigation API](./Routing-API.md)
        * [Routing API](./Routing-API.md#routing-api)
        * [Driving Distance Matrix API](./Routing-API.md#driving-distance-matrix-api)
    - [Predictive Routing APIs](./Predictive-Route-APIs.md)
        * [Predictive Routing API](./Predictive-Route-APIs.md#predictive-routing-api)
        * [Predictive Distance](./Predictive-Route-APIs.md#predictive-distance)
    - [Nearby Reports](./Nearby-Report.md)
    - [Weather API](./Weather-API.md)
    - [Trip Cost Estimation](./Trip-Cost-Estimation.md)
    - [Mappls Geolocation API](./Geolocation.md)
    - [Route Report Summary](./Route-Report-Summary.md)
    - [Set Country Regions](./Set-Regions.md)
    - [Mappls DIGIPIN](./DIGIPIN.md)
- [Annotation Plugin](./AnnotationPlugin.md)
- [Mappls Scalebar Plugin](./Scalebar-Plugin.md)
- [Raster Catalogue](./Raster-Catalogue.md)
- [Mappls GeoAnalytics](./Geoanalytics.md)
- [Place Autocomplete](./Place-Autocomplete.md)
    - [PlaceAutocompleteFragment](./Place-Autocomplete.md#placeautocompletefragment)
    - [PlaceAutocompleteActivity](./Place-Autocomplete.md#placeautocompleteactivity)
- [Place Picker](./Place-Picker.md)
- [Direction Widget](./Direction-Widget.md)
- [Mappls Feedback UI](./FeedbackUI.md)
    * [Using FeedbackFragment](./FeedbackUI.md#using-feedbackfragment)
    * [Using FeedbackActivity](./FeedbackUI.md#using-feedbackactivity)
- [Mappls Nearby Search Widget](./Nearby-Widget.md)
    * [Using MapplsNearbyFragment](./Nearby-Widget.md#using-mapplsnearbyfragment)
    * [Using MapplsNearbyActivity](./Nearby-Widget.md#using-mapplsnearbyactivity)
- [Mappls GeoFence View](./GeoFence-View.md)
- [Mappls BOM to library version mapping](./Bom-Library-Mapping.md)
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
