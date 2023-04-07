


[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Map Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Android Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [https://about.mappls.com/api/signup](https://about.mappls.com/api/signup)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [200+ countries & territories](https://github.com/MapmyIndia/mapmyindia-rest-api/blob/master/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Documentation History](#Documentation-History)

| Version                         | Supported SDK Version                                                                                                                                                                                                                                                                                                  |
|---------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [v1.0.3](docs/v1.0.3/README.md) | - Map SDK v8.0.6 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.2 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.1.1 <br/> - Nearby UI Widget v1.0.2 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0 <br/> - Raster Catalogue v0.0.1    | 
| [v1.0.2](docs/v1.0.2/README.md) | - Map SDK v8.0.5 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.2 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.0.4 <br/> - Nearby UI Widget v1.0.1 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0 <br/> - Raster Catalogue v0.0.1    |
| [v1.0.1](docs/v1.0.1/README.md) | - Map SDK v8.0.3 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.0 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.0.2 <br/> - Nearby UI Widget v1.0.0 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0 <br/> - GeoAnalytics Plugin v1.0.0 |   
For more details of previous documentation versions , [click here](docs/v1.0.3/Doc-Version-History.md).

## [Version History](#Version-History)

| Version | Last Updated | Author |  Release Note| 
| ---- | ---- | ---- | ---- | 
| v8.0.6  | 03 April, 2023 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added Geolocation API  <br/> - Added `routeIndex` request parameter in Trip Cost Estimation API. <br/> - Added `lang` request parameter  in Reverse Geocode API.  <br/> - Bug Fixes & Improvements |  
| v8.0.5  | 06 February, 2023 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added Error Codes while initialising the map  <br/> - Added Reset Compass Engine  <br/> - Bug Fixes & Improvements | 
| v8.0.4  | 18 October 2022   | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added PlaceClickListener on map which provides `mapplsPin`<br/> - Bug Fixes & Improvements                         | 
| v8.0.3  | 26 September 2022 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added Weather API                                                                                                  | 
| v8.0.2  | 28 August 2022    | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added Android auto support                                                                                         |   

For more details of previous versions , [click here](docs/v1.0.3/Version-History.md).


## [Table Of Content](#Table-Of-Content)
- [Vector Android Map](docs/v1.0.3/Getting-Started.md)
    * [Getting Started](docs/v1.0.3/Getting-Started.md#getting-started)
    * [Setup your project](docs/v1.0.3/Getting-Started.md#setup-your-project)
    * [Add your API keys to the SDK](docs/v1.0.3/Getting-Started.md#add-your-api-keys-to-the-sdk)
    * [Add a Mappls Map to your application](docs/v1.0.3/Getting-Started.md#add-a-mappls-map-to-your-application)
    * [Map Interactions](docs/v1.0.3/Getting-Started.md#map-interactions)
    * [Map Events](docs/v1.0.3/Getting-Started.md#map-events)
    * [Map Click/Long Press](docs/v1.0.3/Getting-Started.md#map-click-long-press)
    * [Map Overlays](docs/v1.0.3/Getting-Started.md#map-overlays)
        - [Add A Marker](docs/v1.0.3/Getting-Started.md#add-a-marker)
        - [Remove A Marker](docs/v1.0.3/Getting-Started.md#remove-a-marker)
        - [Customize A Marker](docs/v1.0.3/Getting-Started.md#customize-a-marker)
        - [Add A Polyline](docs/v1.0.3/Getting-Started.md#add-a-polyline)
        - [Remove Polyline](docs/v1.0.3/Getting-Started.md#remove-polyline)
        - [Add A Polygon](docs/v1.0.3/Getting-Started.md#add-a-polygon)
        - [Remove Polygon](docs/v1.0.3/Getting-Started.md#remove-polygon)
    * [Show User Location](docs/v1.0.3/Getting-Started.md#show-user-location)
    * [Calculate distance between two points](docs/v1.0.3/Getting-Started.md#calculate-distance-between-points)
    * [Proguard](docs/v1.0.3/Getting-Started.md#proguard)
- [Map UI Settings](docs/v1.0.3/Map-UI-Settings.md)
    * [Compass Settings](docs/v1.0.3/Map-UI-Settings.md#Compass-Settings)
    * [Enable/Disable Zoom on double tap](docs/v1.0.3/Map-UI-Settings.md#Enable-disable-zoom)
    * [Logo Settings](docs/v1.0.3/Map-UI-Settings.md/#Logo-settings)
    * [Enable/Disable Map Rotation Gessture](docs/v1.0.3/Map-UI-Settings.md#Enable-disable-rotation)
    * [Enable/Disable Map Scrolling Gesture](docs/v1.0.3/Map-UI-Settings.md#Enable-disable-scrolling)
    * [Enable/Disable Map Tilt Gesture](docs/v1.0.3/Map-UI-Settings.md#Enable-disable-tilt)
    * [Enable/Disable Zoom Gestures](docs/v1.0.3/Map-UI-Settings.md#Enable-disable-zoom-gesture)
- [Mappls Annotation Plugin](docs/v1.0.3/AnnotationPlugin.md)
- [Set Country Regions](docs/v1.0.3/Set-Regions.md)
- [Set Mappls Map Style](docs/v1.0.3/Map-Style.md)
    * [List of Available Styles](docs/v1.0.3/Map-Style.md#list-of-available-styles)
    * [Set Mappls Style](docs/v1.0.3/Map-Style.md#set-mapmyindia-style)
    * [To enable/disable last selected style](docs/v1.0.3/Map-Style.md#To-enable-last-selected-style)
    * [Get selected style](docs/v1.0.3/Map-Style.md#get-selected-style)
- [Raster Catalogue](docs/v1.0.3/raster_catalogue.md#raster_catalogue)
- [Mappls Traffic Vector Overlay](docs/v1.0.3/Traffic-Vector-Overlay.md)
- REST API Kit
    * [Search API's](docs/v1.0.3/Search-Api.md)
        - [Auto Suggest](docs/v1.0.3/Search-Api.md#auto-suggest)
        - [Geocoding](docs/v1.0.3/Search-Api.md#geocoding)
        - [Reverse Geocoding](docs/v1.0.3/Search-Api.md#reverse-geocoding)
        - [Nearby Places](docs/v1.0.3/Search-Api.md#nearby-places)
        - [Place Detail](docs/v1.0.3/Search-Api.md#place-detail)
        - [POI Along the Route](docs/v1.0.3/Search-Api.md#poi-along-route)

    * [Routes & Navigation API](docs/v1.0.3/Routing-API.md)
        - [Routing API](docs/v1.0.3/Routing-API.md#routing-api)
        - [Driving Distance Matrix API](docs/v1.0.3/Routing-API.md#distance-api)
    * [Feedback API](docs/v1.0.3/Feedback.md)
    * [Nearby Reports](docs/v1.0.3/Nearby-Report.md)
    * [Weather API](docs/v1.0.3/Weather-API.md)
    * [Trip Cost Estimation API](docs/v1.0.3/trip-cost-estimation.md)
    * [Geolocation API](docs/v1.0.3/Geolocation.md)
- [Mappls GeoAnalytics](docs/v1.0.3/Geoanalytics.md)
- [Place Autocomple Widget](docs/v1.0.3/Place-Autocomplete.md)
    * [PlaceAutocompleteFragment](docs/v1.0.3/Place-Autocomplete.md#place-autocomplete-fragment)
    * [PlaceAutocompleteActivity](docs/v1.0.3/Place-Autocomplete.md#place-autocomplete-activity)
- [Mappls Interactive Layer](docs/v1.0.3/Interactive-Layer.md)
- [Mappls GeoFence View](docs/v1.0.3/GeoFence-View.md)
- [Mappls Safety Strip](docs/v1.0.3/Safety-Strip.md)
- [Mappls Place Picker](docs/v1.0.3/Place-Picker.md)
- [Mappls Scalebar Plugin](docs/v1.0.3/Scalebar-Plugin.md)
- [Mappls Pin Strategy](docs/v1.0.3/MapplsPinStrategy.md)
- [Mappls Direction Widget](docs/v1.0.3/Direction-Widget.md)
- [Mappls Nearby Search Widget](docs/v1.0.3/Nearby-Widget.md)
    * [Introduction](docs/v1.0.3/Nearby-Widget.md#Introduction)
    * [Adding Credentials](docs/v1.0.3/Nearby-Widget.md#Adding-Credentials)
    * [Launching Nearby Widget](docs/v1.0.3/Nearby-Widget.md#Launching-Nearby-Widget)
        - [MapplsNearbyFragment](docs/v1.0.3/Nearby-Widget.md#nearby-fragment)
        - [MapplsNearbyActivity](docs/v1.0.3/Nearby-Widget.md#nearby-activity)
- [Driving Range Plugin](docs/v1.0.3/Driving-Range-Plugin.md)
    - [Introduction](docs/v1.0.3/Driving-Range-Plugin.md#Introduction)
    - [Implementation](docs/v1.0.3/Driving-Range-Plugin.md#Implementation)
    - [Initialise Plugin](docs/v1.0.3/Driving-Range-Plugin.md#Initialise-Plugin)
    - [Plot Driving Range](docs/v1.0.3/Driving-Range-Plugin.md#Plot-Driving-Range)
    - [Additional Features](docs/v1.0.3/Driving-Range-Plugin.md#Additional-Features)
- [SDK Error Codes](docs/v1.0.3/SDK-Error-code.md)
- [Version History](docs/v1.0.3/Version-History.md)
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




<div align="center">@ Copyright 2023 CE Info Systems Ltd. All Rights Reserved.</div>      

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>      

<div align="center">Customer Care: +91-9999333223</div>