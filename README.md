[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Map Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Android Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [https://about.mappls.com/api/signup](https://about.mappls.com/api/signup)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [200+ countries & territories](https://github.com/MapmyIndia/mapmyindia-rest-api/blob/master/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Documentation History]()

| Version                         | Supported SDK Version                                                                                                                                                                                                                                                                                               | 
|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [v1.0.12](../v1.0.12/README.md) | - Map SDK v8.2.9 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.3.2 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v2.3.0 <br/> - Nearby UI Widget v1.0.2 <br/> - Annotation Plugin v1.0.1 <br/> - Driving Range Plugin v1.2.0 <br/> - Raster Catalogue v1.0.0 <br/> - Feedback UI v3.0.1 |
| [v1.0.11](../v1.0.11/README.md) | - Map SDK v8.2.6 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.3.2 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v2.3.0 <br/> - Nearby UI Widget v1.0.2 <br/> - Annotation Plugin v1.0.1 <br/> - Driving Range Plugin v1.2.0 <br/> - Raster Catalogue v1.0.0 <br/> - Feedback UI v3.0.1 |
| [v1.0.10](docs/v1.0.10/README.md) | - Map SDK v8.2.6 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.3.1 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v2.2.0 <br/> - Nearby UI Widget v1.0.2 <br/> - Annotation Plugin v1.0.1 <br/> - Driving Range Plugin v1.2.0 <br/> - Raster Catalogue v1.0.0 |

For more details of previous documentation versions , [click here](docs/v1.0.12/Doc-Version-History.md).

## [Version History]()

| Version | Last Updated      | Author | Release Note                                                                                                                                                                                         | 
|---------|-------------------| ---- |------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| v8.2.9  | 05 May, 2025     | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added Ev Routing Api. </br> - Added Method to clear Map Cache                                                                                    |
| v8.2.8  | 08 Apr, 2025     | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Implemented GPS priority in location settings to ensure location is obtained exclusively from GPS, disregarding network-based providers. </br> - Updated to latest DIGIPIN logic.                                                                                    |
| v8.2.7  | 03 Feb, 2025     | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added `routeId` in Route Report Response </br> - Added `radioType` in Geolocation Api Request </br> - Added `accuracy` in Geolocation Api Response </br> - Added `filter` in Poi Along the Route Api Request </br> - Added `hourOfOperation`, `longDescription`, `shortDescription`, `richInfo` and `partnersFlag` in Poi Along the Route Api Response  </br> - Revamped Smart Trip Planning Api </br> - Added `DigipinUtility` for getting DIGIPIN from Coordinates and vice versa </br> - Added Entry Coordinates in Reverse Geocode Response </br> - Added Internal Retry </br> - Bug Fixes and Security Enhancement                                                                                          |
| v8.2.6  | 14 Nov, 2024     | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Bug Fixes & Improvements                                                                                           |
| v8.2.5  | 10 Oct, 2024     | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added EV Smart Trip Planner Api                                                                                            |


For more details of previous versions , [click here](docs/v1.0.12/Version-History.md).


## [Table Of Content]()
- [Vector Android Map](docs/v1.0.12/Getting-Started.md)
    * [Getting Started](docs/v1.0.12/Getting-Started.md#getting-started)
    * [Setup your project](docs/v1.0.12/Getting-Started.md#setup-your-project)
    * [Add your API keys to the SDK](docs/v1.0.12/Getting-Started.md#add-your-api-keys-to-the-sdk)
    * [Add a Mappls Map to your application](docs/v1.0.12/Getting-Started.md#add-a-mappls-map-to-your-application)
    * [Map Interactions](docs/v1.0.12/Getting-Started.md#map-interactions)
    * [Map Events](docs/v1.0.12/Getting-Started.md#map-events)
    * [Map Click/Long Press](docs/v1.0.12/Getting-Started.md#map-clicklong-press)
    * [Map Overlays](docs/v1.0.12/Getting-Started.md#map-overlays)
        - [Add A Marker](docs/v1.0.12/Getting-Started.md#add-a-marker)
        - [Remove A Marker](docs/v1.0.12/Getting-Started.md#remove-a-marker)
        - [Customize A Marker](docs/v1.0.12/Getting-Started.md#customize-a-marker)
        - [Add A Polyline](docs/v1.0.12/Getting-Started.md#add-a-polyline)
        - [Remove Polyline](docs/v1.0.12/Getting-Started.md#remove-polyline)
        - [Add A Polygon](docs/v1.0.12/Getting-Started.md#add-a-polygon)
        - [Remove Polygon](docs/v1.0.12/Getting-Started.md#remove-polygon)
    * [Show User Location](docs/v1.0.12/Getting-Started.md#show-user-location)
    * [Calculate distance between two points](docs/v1.0.12/Getting-Started.md#calculate-distance-between-two-points)
    * [Proguard](docs/v1.0.12/Getting-Started.md#proguard)
- [Map UI Settings](docs/v1.0.12/Map-UI-Settings.md)
    * [Compass Settings](docs/v1.0.12/Map-UI-Settings.md#compass-settings)
    * [Enable/Disable Zoom on double tap](docs/v1.0.12/Map-UI-Settings.md#enabledisable-zoom-on-double-tap)
    * [Logo Settings](docs/v1.0.12/Map-UI-Settings.md#logo-settings)
    * [Enable/Disable Map Rotation Gessture](docs/v1.0.12/Map-UI-Settings.md#enable-disable-map-rotation-gesture)
    * [Enable/Disable Map Scrolling Gesture](docs/v1.0.12/Map-UI-Settings.md#enabledisable-map-scrolling-gesture)
    * [Enable/Disable Map Tilt Gesture](docs/v1.0.12/Map-UI-Settings.md#enable-disable-map-tilt-gesture)
    * [Enable/Disable Zoom Gestures](docs/v1.0.12/Map-UI-Settings.md#enabledisable-zoom-gesture)
 - [Mappls DIGIPIN](docs/v1.0.12/DIGIPIN.md)
- [Mappls Annotation Plugin](docs/v1.0.12/AnnotationPlugin.md)
- [Set Country Regions](docs/v1.0.12/Set-Regions.md)
- [Set Mappls Map Style](docs/v1.0.12/Map-Style.md)
    * [List of Available Styles](docs/v1.0.12/Map-Style.md#list-of-available-styles)
    * [Set Mappls Style](docs/v1.0.12/Map-Style.md#set-mappls-style)
    * [To enable/disable last selected style](docs/v1.0.12/Map-Style.md#to-enabledisable-last-selected-style)
    * [Get selected style](docs/v1.0.12/Map-Style.md#get-selected-style)
- [Raster Catalogue](docs/v1.0.12/raster_catalogue.md)
- [Mappls Traffic Vector Overlay](docs/v1.0.12/Traffic-Vector-Overlay.md)
- [REST API Kit](docs/v1.0.12/Search-Api.md)
    * [Search API's](docs/v1.0.12/Search-Api.md)
        - [Auto Suggest](docs/v1.0.12/Search-Api.md#auto-suggest)
        - [Geocoding](docs/v1.0.12/Search-Api.md#geocoding)
        - [Reverse Geocoding](docs/v1.0.12/Search-Api.md#reverse-geocoding)
        - [Nearby Places](docs/v1.0.12/Search-Api.md#nearby-places)
        - [Place Detail](docs/v1.0.12/Search-Api.md#place-details)
        - [POI Along the Route](docs/v1.0.12/Search-Api.md#poi-along-the-route)
    * [Routes & Navigation API](docs/v1.0.12/Routing-API.md)
        - [Routing API](docs/v1.0.12/Routing-API.md#routing-api)
        - [Driving Distance Matrix API](docs/v1.0.12/Routing-API.md#driving-distance-matrix-api)
    * [Predictive Routing API](docs/v1.0.12/Predictive-Route-APIs.md)
      - [Predictive Routing API](docs/v1.0.12/Predictive-Route-APIs.md#predictive-routing-api)
      - [Predictive Driving Distance API](docs/v1.0.12/Predictive-Route-APIs.md#predictive-distance)
    * [Feedback API](docs/v1.0.12/Feedback.md)
    * [Nearby Reports](docs/v1.0.12/Nearby-Report.md)
    * [Weather API](docs/v1.0.12/Weather-API.md)
    * [Trip Cost Estimation API](docs/v1.0.12/trip-cost-estimation.md)
    * [Geolocation API](docs/v1.0.12/Geolocation.md)
    * [Route Report Summary API](docs/v1.0.12/Route-Report-Summary.md)
- [Mappls GeoAnalytics](docs/v1.0.12/Geoanalytics.md)
- [Place Autocomple Widget](docs/v1.0.12/Place-Autocomplete.md)
    * [PlaceAutocompleteFragment](docs/v1.0.12/Place-Autocomplete.md#placeautocompletefragment)
    * [PlaceAutocompleteActivity](docs/v1.0.12/Place-Autocomplete.md#placeautocompleteactivity)
- [Mappls GeoFence View](docs/v1.0.12/GeoFence-View.md)
- [Mappls Place Picker](docs/v1.0.12/Place-Picker.md)
- [Mappls Scalebar Plugin](docs/v1.0.12/Scalebar-Plugin.md)
- [Mappls Pin Strategy](docs/v1.0.12/MapplsPinStrategy.md)
- [Mappls Direction Widget](docs/v1.0.12/Direction-Widget.md)
- [Mappls Feedback Widget](docs/v1.0.12/FeedbackUI.md)
    * [Introduction](docs/v1.0.12/FeedbackUI.md)
    * [Using FeedbackFragment](docs/v1.0.12/FeedbackUI.md#using-feedbackfragment)
    * [Using FeedbackActivity](docs/v1.0.12/FeedbackUI.md#using-feedbackactivity)
- [Mappls Nearby Search Widget](docs/v1.0.12/Nearby-Widget.md)
    * [Introduction](docs/v1.0.12/Nearby-Widget.md#introduction)
    * [Adding Credentials](docs/v1.0.12/Nearby-Widget.md#step-2----adding-credentials)
    * [Launching Nearby Widget](docs/v1.0.12/Nearby-Widget.md#step-3----launching-nearby-widget)
        - [MapplsNearbyFragment](docs/v1.0.12/Nearby-Widget.md#mapplsnearbyfragment)
        - [MapplsNearbyActivity](docs/v1.0.12/Nearby-Widget.md#mapplsnearbyactivity)
- [Driving Range Plugin](docs/v1.0.12/Driving-Range-Plugin.md)
    - [Introduction](docs/v1.0.12/Driving-Range-Plugin.md#introduction)
    - [Implementation](docs/v1.0.12/Driving-Range-Plugin.md#implementation)
    - [Initialise Plugin](docs/v1.0.12/Driving-Range-Plugin.md#initialise-plugin)
    - [Plot Driving Range](docs/v1.0.12/Driving-Range-Plugin.md#plot-driving-range)
    - [Additional Features](docs/v1.0.12/Driving-Range-Plugin.md#additional-features)
- [SDK Error Codes](docs/v1.0.12/SDK-Error-code.md)
- [Version History](docs/v1.0.12/Version-History.md)
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
