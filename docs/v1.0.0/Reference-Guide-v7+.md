[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Refrence Guide to update SDK from v7+ to v8.0.0
  
## SDK Setup  
  
Update the maven url  
#### Old SDK  
~~~groovy  
maven {    
  url 'https://maven.mapmyindia.com/repository/mapmyindia/' }  
~~~  
#### New SDK  
~~~groovy  
maven {    
  url 'https://maven.mappls.com/repository/mappls/' }  
~~~  
  
## SDK Initialisation  
#### Old SDK  
~~~java  
MapmyIndiaAccountManager.getInstance().setRestAPIKey("");    
MapmyIndiaAccountManager.getInstance().setMapSDKKey("");    
MapmyIndiaAccountManager.getInstance().setAtlasClientId("");    
MapmyIndiaAccountManager.getInstance().setAtlasClientSecret("");    
MapmyIndia.getInstance(this);  
~~~  
  
#### New SDK  
~~~java  
MapplsAccountManager.getInstance().setRestAPIKey("");    
MapplsAccountManager.getInstance().setMapSDKKey("");    
MapplsAccountManager.getInstance().setAtlasClientId("");    
MapplsAccountManager.getInstance().setAtlasClientSecret("");    
Mappls.getInstance(this);  
~~~  
  
## Map SDK    
### Update dependency   
update dependency from:  
~~~groovy  
implementation 'com.mapmyindia.sdk:mapmyindia-android-sdk:7.0.3'  
~~~  
to  
~~~groovy  
implementation 'com.mappls.sdk:mappls-android-sdk:8.0.0'  
~~~  
   
### Change Imports    
1. Change imports `com.mapmyindia.sdk.maps.*` to `com.mappls.sdk.maps.*`    
2. Change imports `com.mapmyindia.sdk.geojson.*` to `com.mappls.sdk.geojson.*`    
3. Change imports `com.mapmyindia.sdk.turf.*` to `com.mappls.sdk.turf.*`    
4. Change imports `com.mapmyindia.sdk.gestures.*` to `com.mappls.sdk.gestures.*`    
    
### Change Classes    
1. Change class `MapmyIndiaMap` to `MapplsMap`    
2. Change class `CameraELocUpdateFactory` to `CameraMapplsPinUpdateFactory`  
    
### Change in XML    
#### Old SDK    
~~~xml    
<com.mapmyindia.sdk.maps.MapView      
  android:id="@+id/map_view"      
  android:layout_width="match_parent"      
android:layout_height="match_parent"/> ~~~    
    
#### New SDK    
~~~xml    
 <com.mappls.sdk.maps.MapView    
  android:id="@+id/map_view"    
  android:layout_width="match_parent"    
  android:layout_height="match_parent" />  
~~~    
    
***Change all attributes from mapmyindia_maps_ to mappls_maps_***   
### Add marker using Mappls Pin (eLoc)  
#### Old SDK  
~~~java  
mapmyIndiaMap.addMarker(new MarkerOptions().eLoc("MMI000"))  
~~~  
  
#### New SDK  
~~~java  
mapplsMap.addMarker(new MarkerOptions().mapplsPin("MMI000"))  
~~~  
    
### Set Camera using ELoc    
#### Old SDK    
~~~java    
mapmyIndiaMap.moveCamera(CameraELocUpdateFactory.newELocZoom("MMI000", 14));    
mapmyIndiaMap.easeCamera(CameraELocUpdateFactory.newELocZoom("MMI000", 14));    
mapmyIndiaMap.animateCamera(CameraELocUpdateFactory.newELocZoom("MMI000", 14));    
~~~    
#### New SDK    
~~~java    
mapplsMap.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
mapplsMap.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
~~~    
### Set Camera to Particular Eloc Bounds    
#### Old SDK    
~~~java    
mapmyIndiaMap.moveCamera(CameraELocUpdateFactory.newELocBounds(eLocs, 10 , 100, 10, 10));    
mapmyIndiaMap.easeCamera(CameraELocUpdateFactory.newELocBounds(eLocs, 10 , 100, 10, 10));    
mapmyIndiaMap.animateCamera(CameraELocUpdateFactory.newELocBounds(eLocs, 10 , 100, 10, 10));     
~~~    
#### New SDK    
~~~java    
mapplsMap.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));   
mapplsMap.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));  
mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));  
~~~  
    
    
## Rest API    
  
### Change imports  
Change import from `com.mmi.services.*` to `com.mappls.sdk.services.*`  
  
### AutoSuggest    
#### Old SDK    
~~~java    
MapmyIndiaAutoSuggest mapmyIndiaAutoSuggest = MapmyIndiaAutoSuggest.builder()      
        .query(searchString)      
        .build();      
MapmyIndiaAutosuggestManager.newInstance(mapmyIndiaAutoSuggest).call(new OnResponseCallback<AutoSuggestAtlasResponse>() {      
    @Override public void onSuccess(AutoSuggestAtlasResponse autoSuggestAtlasResponse) {      
          //handle response     
 }      
      
    @Override public void onError(int i, String s) {      
      
    }      
});    
~~~    
#### New SDK    
~~~java    
MapplsAutoSuggest mapplsAutoSuggest = MapplsAutoSuggest.builder()    
        .query(searchString)    
        .build();      
MapplsAutosuggestManager.newInstance(mapplsAutoSuggest).call(new OnResponseCallback<AutoSuggestAtlasResponse>() {      
    @Override public void onSuccess(AutoSuggestAtlasResponse autoSuggestAtlasResponse) {      
          //handle response     
 }      
      
    @Override public void onError(int i, String s) {      
      
    }      
});    
~~~    
### Geocoding API    
#### Old SDK    
~~~java    
MapmyIndiaGeoCoding mapmyIndiaGeoCoding = MapmyIndiaGeoCoding.builder()      
        .setAddress("Delhi")      
        .build();      
MapmyIndiaGeoCodingManager.newInstance(mapmyIndiaGeoCoding).call(new OnResponseCallback<GeoCodeResponse>() {      
    @Override public void onSuccess(GeoCodeResponse geoCodeResponse) {      
              
    }      
      
    @Override public void onError(int i, String s) {      
      
    }      
});     
~~~    
#### New SDK    
~~~java    
MapplsGeoCoding mapplsGeoCoding = MapplsGeoCoding.builder()      
        .setAddress("Delhi")      
        .build();      
MapplsGeoCodingManager.newInstance(mapplsGeoCoding).call(new OnResponseCallback<GeoCodeResponse>() {      
    @Override public void onSuccess(GeoCodeResponse geoCodeResponse) {      
              
    }      
      
    @Override public void onError(int i, String s) {      
      
    }      
});    
~~~    
### Reverse Geocode    
#### Old SDK    
~~~java    
MapmyIndiaReverseGeoCode mapmyIndiaReverseGeoCode = MapmyIndiaReverseGeoCode.builder()        
        .setLocation(28,77)        
        .build();        
MapmyIndiaReverseGeoCodeManager.newInstance(mapmyIndiaReverseGeoCode).call(new OnResponseCallback<PlaceResponse>() {        
    @Override public void onSuccess(PlaceResponse response) {        
        //Handle Response      
 }        
        
    @Override public void onError(int code, String message) {        
        //Handle Error      
 }        
});     
~~~    
#### New SDK    
~~~java    
MapplsReverseGeoCode mapplsReverseGeoCode = MapplsReverseGeoCode.builder()        
        .setLocation(28,77)        
        .build();        
MapplsReverseGeoCodeManager.newInstance(mapplsReverseGeoCode).call(new OnResponseCallback<PlaceResponse>() {        
    @Override public void onSuccess(PlaceResponse response) {        
        //Handle Response      
 }        
        
    @Override public void onError(int code, String message) {        
        //Handle Error      
 }        
});    
~~~    
    
### Nearby API    
#### Old SDK    
~~~java    
MapmyIndiaNearby mapmyIndiaNearby = MapmyIndiaNearby.builder()      
        .setLocation(28,77)        
        .keyword("Parking")        
        .build();        
MapmyIndiaNearbyManager.newInstance(mapmyIndiaNearby).call(new OnResponseCallback<NearbyAtlasResponse>() {        
    @Override public void onSuccess(NearbyAtlasResponse response) {        
        //Handle Response      
 }        
        
    @Override public void onError(int code, String message) {        
       //Handle Error      
 }        
});      
~~~    
#### New SDK    
~~~java    
MapplsNearby mapplsNearby = MapplsNearby.builder()      
        .setLocation(28,77)        
        .keyword("Parking")        
        .build();        
MapplsNearbyManager.newInstance(mapplsNearby).call(new OnResponseCallback<NearbyAtlasResponse>() {        
    @Override public void onSuccess(NearbyAtlasResponse response) {        
        //Handle Response      
 }        
        
    @Override public void onError(int code, String message) {        
       //Handle Error      
 }        
});    
~~~    
### Place Detail    
#### Old SDK    
~~~java    
MapmyIndiaPlaceDetail mapmyIndiaPlaceDetail = MapmyIndiaPlaceDetail.builder()        
        .eLoc("mmi000")        
        .build();        
MapmyIndiaPlaceDetailManager.newInstance(mapmyIndiaPlaceDetail).call(new OnResponseCallback<PlaceDetailResponse>() {        
    @Override public void onSuccess(PlaceDetailResponse response) {        
        //Handle Response        
 }        
        
    @Override public void onError(int code, String message) {        
       //Handle Error      
 }        
});      
~~~    
#### New SDK    
~~~java    
MapplsPlaceDetail mapplsPlaceDetail = MapplsPlaceDetail.builder()        
        .mapplsPin("mmi000")        
        .build();        
MapplsPlaceDetailManager.newInstance(mapplsPlaceDetail).call(new OnResponseCallback<PlaceDetailResponse>() {        
    @Override public void onSuccess(PlaceDetailResponse response) {        
        //Handle Response        
 }        
        
    @Override public void onError(int code, String message) {        
       //Handle Error      
 }        
});    
~~~    
### POI Along the Route    
#### Old SDK    
~~~java    
MapmyIndiaPOIAlongRoute poiAlongRoute = MapmyIndiaPOIAlongRoute.builder()        
        .category(catCode)      
        .path(path)        
        .build();        
MapmyIndiaPOIAlongRouteManager.newInstance(poiAlongRoute).call(new OnResponseCallback<POIAlongRouteResponse>() {        
    @Override public void onSuccess(POIAlongRouteResponse response) {        
        //Handle Response        
 }        
        
    @Override public void onError(int code, String message) {        
        //Handle Error      
 }        
});   
~~~    
#### New SDK    
~~~java    
MapplsPOIAlongRoute poiAlongRoute = MapplsPOIAlongRoute.builder()        
        .category(catCode)      
        .path(path)        
        .build();        
MapplsPOIAlongRouteManager.newInstance(poiAlongRoute).call(new OnResponseCallback<POIAlongRouteResponse>() {        
    @Override public void onSuccess(POIAlongRouteResponse response) {        
        //Handle Response        
 }        
        
    @Override public void onError(int code, String message) {        
        //Handle Error      
 }        
});     
~~~    
    
### Routing API    
#### Old SDK    
~~~java    
MapmyIndiaDirections directions = MapmyIndiaDirections.builder()      
        .origin(startPointLocal)      
        .steps(true)      
        .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)      
        .profile(DirectionsCriteria.PROFILE_DRIVING)      
        .overview(DirectionsCriteria.OVERVIEW_FULL)      
        .destination(endPointLocal)    
        .build();    
MapmyIndiaDirectionManager.newInstance(directions).call(new OnResponseCallback<DirectionsResponse>() {      
    @Override public void onSuccess(DirectionsResponse directionsResponse) {      
             
    }      
      
    @Override public void onError(int i, String s) {      
             
    }      
});     
~~~    
#### New SDK    
~~~java    
MapplsDirections directions = MapplsDirections.builder()      
        .origin(startPointLocal)      
        .steps(true)      
        .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)      
        .profile(DirectionsCriteria.PROFILE_DRIVING)      
        .overview(DirectionsCriteria.OVERVIEW_FULL)      
        .destination(endPointLocal)    
        .build();    
MapplsDirectionManager.newInstance(directions).call(new OnResponseCallback<DirectionsResponse>() {      
    @Override public void onSuccess(DirectionsResponse directionsResponse) {      
             
    }      
      
    @Override public void onError(int i, String s) {      
             
    }      
});    
~~~    
### Driving Distance Matrix    
#### Old SDK    
~~~java    
MapmyIndiaDistanceMatrix distanceMatrix = MapmyIndiaDistanceMatrix.builder()      
        .profile(DirectionsCriteria.PROFILE_DRIVING)      
        .resource(DirectionsCriteria.RESOURCE_DISTANCE)      
        .coordinate(Point.fromLngLat(80.502113, 8.916787))      
        .coordinate(Point.fromLngLat(28.5505073, 77.2689367))      
        .build();     
MapmyIndiaDistanceMatrixManager.newInstance(distanceMatrix).call(new OnResponseCallback<DistanceResponse>() {      
    @Override public void onSuccess(DistanceResponse distanceResponse) {      
            
    }      
      
    @Override public void onError(int i, String s) {      
            
  }      
});    
~~~    
    
#### New SDK    
~~~java    
MapplsDistanceMatrix distanceMatrix = MapplsDistanceMatrix.builder()      
        .profile(DirectionsCriteria.PROFILE_DRIVING)      
        .resource(DirectionsCriteria.RESOURCE_DISTANCE)      
        .coordinate(Point.fromLngLat(80.502113, 8.916787))      
        .coordinate(Point.fromLngLat(28.5505073, 77.2689367))      
        .build();     
MapplsDistanceMatrixManager.newInstance(distanceMatrix).call(new OnResponseCallback<DistanceResponse>() {      
    @Override public void onSuccess(DistanceResponse distanceResponse) {      
            
    }      
      
    @Override public void onError(int i, String s) {      
            
  }      
});    
~~~    


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




<div align="center">@ Copyright 2022 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
