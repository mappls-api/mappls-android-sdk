![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)  
  
# Refrence Guide to update SDK from v6+ to v8.0.0  
## Map SDK  
### Change Imports  
1. Change imports `com.mapbox.mapboxsdk.*` to `com.mappls.sdk.maps.*`  
2. Change imports `com.mapbox.mapboxsdk.maps.*` to `com.mappls.sdk.maps.*`  
3. Change imports `com.mapbox.geojson.*` to `com.mappls.sdk.geojson.*`  
4. Change imports `com.mapbox.turf.*` to `com.mappls.sdk.turf.*`  
5. Change imports `com.mapbox.android.gestures.*` to `com.mappls.sdk.gestures.*`  
  
### Change Classes  
1. Change class `MapboxMap` to `MapplsMap`  
  
### Change in XML  
#### Old SDK  
~~~xml  
<com.mapbox.mapboxsdk.maps.MapView    
  android:id="@+id/map_view"    
  android:layout_width="match_parent"    
  android:layout_height="match_parent"/>  
~~~  
  
#### New SDK  
~~~xml  
<com.mappls.sdk.maps.MapView    
  android:id="@+id/map_view"    
  android:layout_width="match_parent"    
  android:layout_height="match_parent"/>  
~~~  
  
**Change all attributes from mapbox_ to mappls_maps_**  
  
### Set Camera using ELoc  
#### Old SDK  
~~~java  
mapmyIndiaMap.moveCamera("MMI000", 14);  
mapmyIndiaMap.easeCamera("MMI000", 14);  
mapmyIndiaMap.animateCamera("MMI000", 14);  
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
mapmyIndiaMap.moveCamera(eLocList, 10, 10, 10, 10);  
mapmyIndiaMap.easeCamera(eLocList, 10, 10, 10, 10);  
mapmyIndiaMap.animateCamera(eLocList, 10, 10, 10, 10);  
~~~  
#### New SDK  
~~~java  
mapplsMap.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));  
mapplsMap.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));  
mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(mapplsPins, 10 , 100, 10, 10));  
~~~  
### Map Click/Long click  
#### Old SDK  
~~~java  
//Map click  
mapmyIndiaMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {    
  @Override    
public void onMapClick(@NonNull LatLng point) {    
      
  }    
});  
//Map long click  
mapmyIndiaMap.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {    
  @Override    
public void onMapLongClick(@NonNull LatLng point) {    
      
  }    
});  
~~~  
#### New SDK  
~~~java  
//Map click  
mapplsMap.addOnMapClickListener(new MapplsMap.OnMapClickListener() {    
    @Override    
public boolean onMapClick(@NonNull LatLng latLng) {    
    return false;    
 }    
});  
//Map long click  
mapplsMap.addOnMapLongClickListener(new MapplsMap.OnMapLongClickListener() {    
    @Override    
public boolean onMapLongClick(@NonNull LatLng latLng) {     
        return false;    
  }    
});  
~~~  
  
### Current Location  
#### Old SDK  
~~~java  
//Current Location Activation LocationComponentOptions options = LocationComponentOptions.builder(this)  
                .foregroundDrawable(R.drawable.location_pointer)  
                .build();  
// Get an instance of the component LocationComponent  
locationComponent = mapmyIndiaMaps.getLocationComponent();  
// Activate with options  
locationComponent.activateLocationComponent(this, options);  
//LocationChange Listener  
LocationEngineListener listener = new LocationEngineListener() {  
       
@Override public void onConnected() {    
    
}    
    
@Override public void onLocationChanged(Location location) {    
     
   }  
};  
//Add Location Change listener  
locationEngine.addLocationEngineListener(this);  
//Remove Location Change listener  
locationEngine.removeLocationEngineListener(this);  
//Request Location Update  
locationEngine.requestLocationUpdates();  
//Remove location update  
locationEngine.removeLocationUpdates();  
~~~  
#### New SDK  
~~~java  
//Current Location Activation LocationComponentOptions options = LocationComponentOptions.builder(this)   
                .foregroundDrawable(R.drawable.location_pointer)    
                .build();    
// Get an instance of the component LocationComponent    
  locationComponent = mapmyIndiaMap.getLocationComponent();    
  LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions.builder(this, style)    
                .locationComponentOptions(options)    
                .build();    
// Activate with options locationComponent.activateLocationComponent(locationComponentActivationOptions);  
//LocationChange Listener  
LocationEngineCallback<LocationEngineResult> locationEngineCallback = new LocationEngineCallback<LocationEngineResult>() {    
    @Override    
public void onSuccess(LocationEngineResult locationEngineResult) {    
        if(locationEngineResult.getLastLocation() != null) {    
            Location location = locationEngineResult.getLastLocation();      
        }    
    }    
    
    @Override    
public void onFailure(@NonNull Exception e) {    
    
    }    
};  
LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)    
        .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)    
        .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();    
 ////Request Location Update & add location change callback  
locationEngine.requestLocationUpdates(request, locationEngineCallback, getMainLooper());  
//Remove location update & callback  
locationEngine.removeLocationUpdates(locationEngineCallback);  
~~~  
  
  
## Rest API  
### AutoSuggest  
#### Old SDK  
~~~java  
MapmyIndiaAutoSuggest.builder()    
        .query(searchText)    
        .build()    
        .enqueueCall(new Callback<AutoSuggestAtlasResponse>() {    
            @Override    
public void onResponse(@NonNull Call<AutoSuggestAtlasResponse> call, @NonNull Response<AutoSuggestAtlasResponse> response) {    
                //handle response   
 }    
    
            @Override    
public void onFailure(@NonNull Call<AutoSuggestAtlasResponse> call, @NonNull Throwable t) {    
                t.printStackTrace();    
            }    
        });  
~~~  
#### New SDK  
~~~java  
MapplsAutoSuggest mapplsAutoSuggest = MapplsAutoSuggest.builder()    
        .query(searchString)    
        .build();    
MapplsAutosuggestManager.newInstance(mapplsAutoSuggest).call(new OnResponseCallback<AutoSuggestAtlasResponse>() {    
    @Override    
public void onSuccess(AutoSuggestAtlasResponse autoSuggestAtlasResponse) {    
          //handle response   
 }    
    
    @Override    
public void onError(int i, String s) {    
    
    }    
});  
~~~  
### Geocoding API  
#### Old SDK  
~~~java  
MapmyIndiaGeoCoding.builder()    
        .setAddress("Delhi")    
        .build()    
        .enqueueCall(new Callback<GeoCodeResponse>() {    
            @Override    
public void onResponse(Call<GeoCodeResponse> call, Response<GeoCodeResponse> response) {    
                //handle response   
 }       
    
            @Override    
public void onFailure(Call<GeoCodeResponse> call, Throwable t) {    
                t.printStackTrace();    
            }    
        });  
~~~  
#### New SDK  
~~~java  
MapplsGeoCoding mapplsGeoCoding = MapplsGeoCoding.builder()    
        .setAddress("Delhi")    
        .build();    
MapplsGeoCodingManager.newInstance(mapplsGeoCoding).call(new OnResponseCallback<GeoCodeResponse>() {    
    @Override    
public void onSuccess(GeoCodeResponse geoCodeResponse) {    
            
    }    
    
    @Override    
public void onError(int i, String s) {    
    
    }    
});  
~~~  
### Reverse Geocode  
#### Old SDK  
~~~java  
MapmyIndiaReverseGeoCode.builder()    
        .setLocation(28, 77)    
        .build()    
        .enqueueCall(new Callback<PlaceResponse>() {    
            @Override    
public void onResponse(Call<PlaceResponse> call,Response<PlaceResponse> response) {    
                //handle response   
 }    
    
            @Override    
public void onFailure(Call<PlaceResponse> call, Throwable t) {    
                t.printStackTrace();    
            }    
        });  
~~~  
#### New SDK  
~~~java  
MapplsReverseGeoCode mapplsReverseGeoCode = MapplsReverseGeoCode.builder()      
        .setLocation(28,77)      
        .build();      
MapplsReverseGeoCodeManager.newInstance(mapplsReverseGeoCode).call(new OnResponseCallback<PlaceResponse>() {      
    @Override      
public void onSuccess(PlaceResponse response) {      
        //Handle Response    
 }      
      
    @Override      
public void onError(int code, String message) {      
        //Handle Error    
 }      
});  
~~~  
  
### Nearby API  
#### Old SDK  
~~~java  
MapmyIndiaNearby.builder()    
        .keyword("Parking")    
        .setLocation(28d, 77d)    
        .build()    
        .enqueueCall(new Callback<NearbyAtlasResponse>() {    
            @Override    
public void onResponse(Call<NearbyAtlasResponse> call, Response<NearbyAtlasResponse> response) {    
                //handle response   
 }    
    
            @Override    
public void onFailure(Call<NearbyAtlasResponse> call, Throwable t) {    
                t.printStackTrace();    
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
    @Override      
public void onSuccess(NearbyAtlasResponse response) {      
        //Handle Response    
 }      
      
    @Override      
public void onError(int code, String message) {      
       //Handle Error    
 }      
});  
~~~  

### Place Detail  
#### Old SDK  
~~~java  
MapmyIndiaPlaceDetail.builder()    
        .eLoc("mmi000")    
        .build()    
        .enqueueCall(new Callback<PlaceDetailResponse>() {    
            @Override    
public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {    
                //handle response   
 }    
    
            @Override    
public void onFailure(Call<PlaceDetailResponse> call, Throwable t) {    
                t.printStackTrace();    
            }    
        });  
~~~  
#### New SDK  
~~~java  
MapplsPlaceDetail mapplsPlaceDetail = MapplsPlaceDetail.builder()      
        .mapplsPin("mmi000")      
        .build();      
MapplsPlaceDetailManager.newInstance(mapplsPlaceDetail).call(new OnResponseCallback<PlaceDetailResponse>() {      
    @Override      
public void onSuccess(PlaceDetailResponse response) {      
        //Handle Response      
 }      
      
    @Override      
public void onError(int code, String message) {      
       //Handle Error    
 }      
});  
~~~  
### POI Along the Route  
#### Old SDK  
~~~java  
MapmyIndiaPOIAlongRoute.builder()    
        .category(catCode)    
        .path(path)    
        .build().enqueueCall(new Callback<POIAlongRouteResponse>() {    
            @Override    
public void onResponse(Call<POIAlongRouteResponse> call, Response<POIAlongRouteResponse> response) {    
                //handle response  
  }    
    
            @Override    
public void onFailure(Call<POIAlongRouteResponse> call, Throwable t) {    
               t.printStackTrace();  
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
    @Override      
public void onSuccess(POIAlongRouteResponse response) {      
        //Handle Response      
 }      
      
    @Override      
public void onError(int code, String message) {      
        //Handle Error    
 }      
});  
~~~  
  
### Routing API  
#### Old SDK  
~~~java  
MapmyIndiaDirections.builder()    
        .origin(startPointLocal)    
        .steps(true)    
        .resource(DirectionsCriteria.RESOURCE_ROUTE_ETA)    
        .profile(DirectionsCriteria.PROFILE_DRIVING)    
        .overview(DirectionsCriteria.OVERVIEW_FULL)    
        .destination(endPointLocal)  
        .build()    
        .enqueueCall(new Callback<DirectionsResponse>() {    
            @Override    
public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {    
                //handle response   
 }    
    
            @Override    
public void onFailure(Call<DirectionsResponse> call, Throwable t) {    
                 t.printStackTrace();    
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
    @Override    
public void onSuccess(DirectionsResponse directionsResponse) {    
           
    }    
    
    @Override    
public void onError(int i, String s) {    
           
    }    
});  
~~~  
### Driving Distance Matrix  
#### Old SDK  
~~~java  
MapmyIndiaDistanceMatrix.builder()    
        .profile(DirectionsCriteria.PROFILE_DRIVING)    
        .resource(DirectionsCriteria.RESOURCE_DISTANCE)    
        .coordinate(Point.fromLngLat(80.502113, 8.916787))    
        .coordinate(Point.fromLngLat(28.5505073, 77.2689367))    
        .build()    
        .enqueueCall(new Callback<DistanceResponse>() {    
            @Override    
public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {    
                //handle response   
 }    
    
            @Override    
public void onFailure(Call<DistanceResponse> call, Throwable t) {    
                t.printStackTrace();    
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
    @Override    
public void onSuccess(DistanceResponse distanceResponse) {    
          
    }    
    
    @Override    
public void onError(int i, String s) {    
          
  }    
});  
~~~  

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