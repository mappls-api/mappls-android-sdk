

![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)
# Mappls Map Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Android Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [https://about.mappls.com/api/signup](https://about.mappls.com/api/signup)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [238 nations](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Documentation History](#Documentation-History)

| Version | Supported SDK Version |  
| ---- | ---- |    
| [v1.0.0](../v1.0.0/README.md) | - Map SDK v8.0.0 <br/> - Geo Analytics v1.0.0 <br/> - Place Search Widget v2.0.0 <br/> - GeoFence Widget v1.0.0 <br/> - Scalebar Plugin v1.0.0 <br/> - Direction Widget v1.0.0 <br/> - Nearby UI Widget v1.0.0 <br/> - Annotation Plugin v1.0.0 <br/> - Driving Range Plugin v1.0.0 <br/> - GeoAnalytics Plugin v1.0.0 |  

Reference to the documentation of Previous SDK versions [here](https://github.com/mappls-api/mapmyindia-maps-vectorSDK-android)


## [Version History](#Version-History)

| Version | Last Updated | Author |  Release Note|  
| ---- | ---- | ---- | ---- |  
| v8.0.0 | 14 June 2022 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) |   Initial release  |


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
- [SDK Error Codes](./SDK-Error-code.md)
- [Set Country Regions](./Set-Regions.md)
- [Set Mappls Map Style](./Map-Style.md)
    * [List of Available Styles](./Map-Style.md#list-of-available-styles)
    * [Set Mappls Style](./Map-Style.md#set-mapmyindia-style)
    * [To enable/disable last selected style](./Map-Style.md#To-enable-last-selected-style)
    * [Get selected style](./Map-Style.md#get-selected-style)
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
- [Version History](./Version-History.md)
- [Country List](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md)

<br><br><br>

## Our many happy customers:

![](https://www.mapmyindia.com/api/img/logos1/PhonePe.png)![](https://www.mapmyindia.com/api/img/logos1/Arya-Omnitalk.png)![](https://www.mapmyindia.com/api/img/logos1/delhivery.png)![](https://www.mapmyindia.com/api/img/logos1/hdfc.png)![](https://www.mapmyindia.com/api/img/logos1/TVS.png)![](https://www.mapmyindia.com/api/img/logos1/Paytm.png)![](https://www.mapmyindia.com/api/img/logos1/FastTrackz.png)![](https://www.mapmyindia.com/api/img/logos1/ICICI-Pru.png)![](https://www.mapmyindia.com/api/img/logos1/LeanBox.png)![](https://www.mapmyindia.com/api/img/logos1/MFS.png)![](https://www.mapmyindia.com/api/img/logos1/TTSL.png)![](https://www.mapmyindia.com/api/img/logos1/Novire.png)![](https://www.mapmyindia.com/api/img/logos1/OLX.png)![](https://www.mapmyindia.com/api/img/logos1/sun-telematics.png)![](https://www.mapmyindia.com/api/img/logos1/Sensel.png)![](https://www.mapmyindia.com/api/img/logos1/TATA-MOTORS.png)![](https://www.mapmyindia.com/api/img/logos1/Wipro.png)![](https://www.mapmyindia.com/api/img/logos1/Xamarin.png)

<br>

For any queries and support, please contact:

[<img src="https://mmi-api-team.s3.amazonaws.com/Mappls-SDKs/Resources/mappls-logo.png" height="40"/> </p>](https://about.mappls.com/api/)    
Email us at [apisupport@mappls.com](mailto:apisupport@mappls.com)

![](https://www.mapmyindia.com/api/img/icons/stack-overflow.png)    
[Stack Overflow](https://stackoverflow.com/questions/tagged/mappls-api)    
Ask a question under the mappls-api

![](https://www.mapmyindia.com/api/img/icons/support.png)    
[Support](https://about.mappls.com/contact/)    
Need support? contact us!

![](https://www.mapmyindia.com/api/img/icons/blog.png)    
[Blog](http://www.mapmyindia.com/blog/)    
Read about the latest updates & customer stories

> © Copyright 2022. CE Info Systems Ltd. All Rights Reserved. | [Terms & Conditions](https://about.mappls.com/api/terms-&-conditions).