
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Mappls Map Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Android Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [https://about.mappls.com/api/signup](https://about.mappls.com/api/signup)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [238 nations](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Documentation History](#Documentation-History)

| Version                       | Supported SDK Version    |
|-------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [v1.0.2](../v1.0.1/README.md) | - Map SDK v8.0.5 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.2 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.0.4 <br/> - Nearby UI Widget v1.0.1 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0 <br/> - Raster Catalogue v0.0.1 |  
| [v1.0.1](../v1.0.1/README.md) | - Map SDK v8.0.4 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.1 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.0.3 <br/> - Nearby UI Widget v1.0.0 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0 <br/> - Raster Catalogue v0.0.1 |  
| [v1.0.0](../v1.0.0/README.md) | - Map SDK v8.0.0 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.0 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.0.0 <br/> - Nearby UI Widget v1.0.0 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0                                 |    

Reference to the documentation of Previous SDK versions [here](https://github.com/mappls-api/mapmyindia-maps-vectorSDK-android)


## [Version History](#Version-History)

| Version | Last Updated | Author |  Release Note|
| ---- | ---- | ---- | ---- |
| v8.0.5 | 06 February, 2023 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) |   - Added Error Codes while initialising the map  <br/> - Added Reset Compass Engine  <br/> - Bug Fixes & Improvements | 
| v8.0.4 | 18 October 2022 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) |   - Added PlaceClickListener on map which provides `mapplsPin`<br/> - Bug Fixes & Improvements |  
| v8.0.3 | 26 September 2022 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) |   - Added Weather API  |  
| v8.0.2 | 28 August 2022 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) |   - Added Android auto support  |  
| v8.0.1 | 19 August 2022 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) |   - Added `mapplsPin` in Reverse Geocode <br/> - Bug Fixes|    


For more details of previous versions , [click here](./Version-History.md).

## [Table Of Content](#Table-Of-Content)
- [Vector Android Map](./Getting-Started.md)
    * [Getting Started](./Getting-Started.md#getting-started)
    * [Setup your project](./Getting-Started.md#setup-your-project)
    * [Add your API keys to the SDK](./Getting-Started.md#add-your-api-keys-to-the-sdk)
    * [Add a Mappls Map to your application](./Getting-Started.md#add-a-mappls-map-to-your-application)
    * [Map Interactions](./Getting-Started.md#map-interactions)
    * [Map Events](./Getting-Started.md#map-events)
    * [Map Click/Long Press](./Getting-Started.md#map-click-long-press)
    * [Map Overlays](./Getting-Started.md#map-overlays)
        - [Add A Marker](./Getting-Started.md#add-a-marker)
        - [Remove A Marker](./Getting-Started.md#remove-a-marker)
        - [Customize A Marker](./Getting-Started.md#customize-a-marker)
        - [Add A Polyline](./Getting-Started.md#add-a-polyline)
        - [Remove Polyline](./Getting-Started.md#remove-polyline)
        - [Add A Polygon](./Getting-Started.md#add-a-polygon)
        - [Remove Polygon](./Getting-Started.md#remove-polygon)
    * [Show User Location](./Getting-Started.md#show-user-location)
    * [Calculate distance between two points](./Getting-Started.md#calculate-distance-between-points)
    * [Proguard](./Getting-Started.md#proguard)
- [Map UI Settings](./Map-UI-Settings.md)
    * [Compass Settings](./Map-UI-Settings.md#Compass-Settings)
    * [Enable/Disable Zoom on double tap](./Map-UI-Settings.md#Enable-disable-zoom)
    * [Logo Settings](./Map-UI-Settings.md/#Logo-settings)
    * [Enable/Disable Map Rotation Gessture](./Map-UI-Settings.md#Enable-disable-rotation)
    * [Enable/Disable Map Scrolling Gesture](./Map-UI-Settings.md#Enable-disable-scrolling)
    * [Enable/Disable Map Tilt Gesture](./Map-UI-Settings.md#Enable-disable-tilt)
    * [Enable/Disable Zoom Gestures](./Map-UI-Settings.md#Enable-disable-zoom-gesture)
- [Mappls Annotation Plugin](./AnnotationPlugin.md)
- [Set Country Regions](./Set-Regions.md)
- [Set Mappls Map Style](./Map-Style.md)
    * [List of Available Styles](./Map-Style.md#list-of-available-styles)
    * [Set Mappls Style](./Map-Style.md#set-mapmyindia-style)
    * [To enable/disable last selected style](./Map-Style.md#To-enable-last-selected-style)
    * [Get selected style](./Map-Style.md#get-selected-style)
- [Raster Catalogue](./raster_catalogue.md#raster_catalogue)
- [Mappls Traffic Vector Overlay](./Traffic-Vector-Overlay.md)
- REST API Kit
    * [Search API's](./Search-Api.md)
        - [Auto Suggest](./Search-Api.md#auto-suggest)
        - [Geocoding](./Search-Api.md#geocoding)
        - [Reverse Geocoding](./Search-Api.md#reverse-geocoding)
        - [Nearby Places](./Search-Api.md#nearby-places)
        - [Place Detail](./Search-Api.md#place-detail)
        - [POI Along the Route](./Search-Api.md#poi-along-route)

    * [Routes & Navigation API](./Routing-API.md)
        - [Routing API](./Routing-API.md#routing-api)
        - [Driving Distance Matrix API](./Routing-API.md#distance-api)
    * [Feedback API](./Feedback.md)
    * [Nearby Reports](./Nearby-Report.md)
    * [Weather API](./Weather-API.md)
- [Mappls GeoAnalytics](./Geoanalytics.md)
- [Place Autocomple Widget](./Place-Autocomplete.md)
    * [PlaceAutocompleteFragment](./Place-Autocomplete.md#place-autocomplete-fragment)
    * [PlaceAutocompleteActivity](./Place-Autocomplete.md#place-autocomplete-activity)
- [Mappls Interactive Layer](./Interactive-Layer.md)
- [Mappls GeoFence View](./GeoFence-View.md)
- [Mappls Safety Strip](./Safety-Strip.md)
- [Mappls Place Picker](./Place-Picker.md)
- [Mappls Scalebar Plugin](./Scalebar-Plugin.md)
- [Mappls Pin Strategy](MapplsPinStrategy.md)
- [Mappls Direction Widget](./Direction-Widget.md)
- [Mappls Nearby Search Widget](./Nearby-Widget.md)
    * [Introduction](./Nearby-Widget.md#Introduction)
    * [Adding Credentials](./Nearby-Widget.md#Adding-Credentials)
    * [Launching Nearby Widget](./Nearby-Widget.md#Launching-Nearby-Widget)
        - [MapplsNearbyFragment](./Nearby-Widget.md#nearby-fragment)
        - [MapplsNearbyActivity](./Nearby-Widget.md#nearby-activity)
- [Driving Range Plugin](./Driving-Range-Plugin.md)
    - [Introduction](./Driving-Range-Plugin.md#Introduction)
    - [Implementation](./Driving-Range-Plugin.md#Implementation)
    - [Initialise Plugin](./Driving-Range-Plugin.md#Initialise-Plugin)
    - [Plot Driving Range](./Driving-Range-Plugin.md#Plot-Driving-Range)
    - [Additional Features](./Driving-Range-Plugin.md#Additional-Features)
- [SDK Error Codes](./SDK-Error-code.md)
- [Version History](./Version-History.md)
- [Country List](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md)

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




<div align="center">@ Copyright 2022 CE Info Systems Ltd. All Rights Reserved.</div>  

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>  

<div align="center">Customer Care: +91-9999333223</div>