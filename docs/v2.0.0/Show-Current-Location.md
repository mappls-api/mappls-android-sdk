[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Show Current Location

To display the user's current location on the map, your application must first request and obtain location permissions from the user.

## [Activate Location Component]()
This is required to activate the location component. _This method should be required before calling any locationComponent functions_
#### Kotlin
```kotlin
val locationComponentActivationOptions =
LocationComponentActivationOptions.builder(this, style)
    .build()
val locationComponent = mapplsMap.locationComponent
locationComponent?.activateLocationComponent(locationComponentActivationOptions)
```
#### Java
```java
LocationComponentActivationOptions  locationComponentActivationOptions =
        LocationComponentActivationOptions.builder(this, style)
                .build();
LocationComponent locationComponent = mapplsMap.getLocationComponent();
if(locationComponent != null) {
    locationComponent.activateLocationComponent(locationComponentActivationOptions);
}
```

## [Check Location Component is Activated]()
To check the `LocationComponent` is activated or not. This check should be recomended to use before calling any `LocationComponent` function.
#### Kotlin
```kotlin
val isActivated = locationComponent.isLocationComponentActivated
```
#### Java
```java
boolean isActivated = locationComponent.isLocationComponentActivated();
```

## [Show/Hide Current Location On Map]()
Use this functionality to enable or disable the display of the user's current location on the map by controlling the visibility of the Location Component.


#### Kotlin
```kotlin
locationComponent.isLocationComponentEnabled = true
```
#### Java
```java
locationComponent.setLocationComponentEnabled(true);
```

## [Change Location Icon]()
The properties and location icon can change by `LocationComponentOptions` 
- During Activation of `LocationComponent`
    #### Kotlin
    ```kotlin
    val locationComponentOption = LocationComponentOptions.builder(this)
        .backgroundDrawable(drawable)
        .build()
    val locationComponentActivationOptions =
        LocationComponentActivationOptions.builder(this, style)
            .locationComponentOptions(locationComponentOption)
            .build()
    val locationComponent = mapplsMap.locationComponent
    locationComponent?.activateLocationComponent(locationComponentActivationOptions)
    ```
    #### Java
    ```java
    LocationComponentOptions locationComponentOptions = LocationComponentOptions.builder(DemoActivity.this)
            .backgroundDrawable(drawable)
            .build();
    LocationComponentActivationOptions  locationComponentActivationOptions =
            LocationComponentActivationOptions.builder(DemoActivity.this, style)
                    .locationComponentOptions(locationComponentOptions)
                    .build();
    LocationComponent locationComponent = mapplsMap.getLocationComponent();
    if(locationComponent != null) {
        locationComponent.activateLocationComponent(locationComponentActivationOptions);
    }
    ```
- After Activation of `LocationComponent`
    #### Kotlin
    ```kotlin
    val locationComponentOption = LocationComponentOptions.builder(this)
        .backgroundDrawable(drawable)
        .build()
    locationComponent.applyStyle(locationComponentOptions)
    ```
    #### Java
    ```java
    LocationComponentOptions locationComponentOptions = LocationComponentOptions.builder(DemoActivity.this)
            .backgroundDrawable(drawable)
            .build();
    locationComponent.applyStyle(locationComponentOptions);
    ```
##### LocationComponentOptions.Builder Properties : 

The `LocationComponentOptions.Builder` provides several customizable properties to configure the appearance and behavior of the location indicator on the map:

1. **`accuracyAlpha`** – Sets the **opacity** of the accuracy circle around the user's location.
2. **`accuracyColor`** – Sets the **solid color** used to represent the location accuracy circle.
3. **`backgroundDrawable`** – Sets the **background icon** for the location marker.
4. **`backgroundDrawableStale`** – Sets the background icon for the **stale state** (when GPS is unavailable or outdated).
5. **`foregroundDrawable`** – Sets the **foreground icon** for the active location marker.
6. **`foregroundDrawableStale`** – Sets the **stale foreground icon**, indicating outdated location information.
7. **`gpsDrawable`** – Sets the icon for **navigation state** (`RenderMode.GPS`), typically a directional marker.
8. **`gpsStaleDrawable`** – Sets the **stale GPS icon** for navigation state when location updates are unavailable.
9. **`bearingTintColor`** – Sets the **tint color** applied to the bearing indicator icon.

## [Get Location Engine]()
`LocationEngine` being used for updating the user location
#### Kotlin
```kotlin
val locationEngine = locationComponent?.locationEngine
```
#### Java
```java
LocationEngine locationEngine = locationComponent.getLocationEngine();
```

## [Get Last Location]()
To get the recent current Location available:
 - Using `LocationComponent`
    #### Kotlin
    ```kotlin
    val location = locationComponent.lastKnownLocation
    ```
    #### Java
    ```java
    Location location = locationComponent.getLastKnownLocation();
    ```
 - Using `LocationEngine`
    #### Kotlin
    ```kotlin
    locationEngine?.getLastLocation(object :
        LocationEngineCallback<LocationEngineResult?> {
        override fun onSuccess(locationEngineResult: LocationEngineResult?) {
                val location = locationEngineResult?.lastLocation
        }
        override fun onFailure(e: Exception) {
        }
    })
    ```
    #### Java
    ```java
    locationEngine.getLastLocation(new LocationEngineCallback<LocationEngineResult>() {
        @Override
        public void onSuccess(LocationEngineResult locationEngineResult) {
            if(locationEngineResult != null && locationEngineResult.getLastLocation()) {
                Location location = locationEngineResult.getLastLocation();
            }
        }
        @Override
        public void onFailure(@NonNull Exception e) {
        }
    });
    ```
 >> Note: Where `location` is the Android `Location` object. For more details please refer the [documentation](https://developer.android.com/reference/android/location/Location)

## [Location Update Callback]()
To request continuous location update
#### Kotlin
```kotlin
val locationEngineCallback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult?) {
        if(result?.lastLocation != null) {
            val location = result.lastLocation
        }
    }

    override fun onFailure(e: Exception) {

    }
}
val request = LocationEngineRequest.Builder(1000)
    .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
    .build()
// To add Location Update Listener
locationEngine?.requestLocationUpdates(request, locationEngineCallback, mainLooper)


// To remove Location Update Listener
override fun onDestroy() {
    super.onDestroy()
    if (locationEngine != null) {
        locationEngine?.removeLocationUpdates(locationEngineCallback)
    }
}
```
#### Java
```java
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
        .build();
// To add Location Update Listener
locationEngine.requestLocationUpdates(request, locationEngineCallback, getMainLooper());

// To remove Location Update Listener
@Override
protected void onDestroy() {
  super.onDestroy();
    // Prevent leaks  
    if (locationEngine != null) {
	    locationEngine.removeLocationUpdates(locationEngineCallback);
    }
}
```

## [Camera Mode]()
Contains the variety of camera modes which determine how the camera will track the user location. 
#### Kotlin
```kotlin
locationComponent.cameraMode = CameraMode.NONE
```
#### Java
```java
locationComponent.setCameraMode(CameraMode.NONE);
```
Following are the possible values for `CameraMode`:
- `CameraMode.NONE`: No camera tracking.
- `CameraMode.NONE_COMPASS`: Camera does not track location, but does track compass bearing.
- `CameraMode.NONE_GPS`: Camera does not track location, but does track GPS `Location` bearing.
- `CameraMode.TRACKING`: Camera tracks the device location, no bearing is considered.
- `CameraMode.TRACKING_COMPASS`: Camera tracks the device location, tracking bearing provided by the device compass.
- `CameraMode.TRACKING_GPS`: Camera tracks the device location, with bearing provided by a normalized `Location#getBearing()`.
- `CameraMode.TRACKING_GPS_NORTH`: Camera tracks the device location, with bearing always set to north (0).

>> Note: On Slide the Map or if we call any [Camera Controls Function](./Map-Camera.md) then the Camera Mode is set to `CameraMode.NONE`

## [Render Mode]()
Contains the variety of ways the user location can be rendered on the map.
#### Kotlin
```kotlin
locationComponent.renderMode = RenderMode.NORMAL
```
#### Java
```java
locationComponent.setRenderMode(RenderMode.NORMAL);
```
Following are the possible values for `RenderMode`:
- `RenderMode.NORMAL`: This mode shows the device location, ignoring both compass and GPS bearing (no arrow rendered).
- `RenderMode.COMPASS`: This mode shows the device location, as well as an arrow that is considering the compass of the device.
- `RenderMode.GPS`: This mode shows the device location with the icon bearing updated from the `Location` updates being provided to the `LocationComponent`.


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
