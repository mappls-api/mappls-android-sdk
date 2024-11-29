[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Vector Map Android SDK

**Easy To Integrate Maps & Location APIs & SDKs For Web & Mobile Applications**

Powered with India's most comprehensive and robust mapping functionalities.

1. You can get your api key to be used in this document here: [https://apis.mappls.com/console/](https://apis.mappls.com/console/)

2. The sample code is provided to help you understand the basic functionality of Mappls maps & REST APIs working on **Android** native development platform.

4. Explore through [200 + nations with Global Search](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md) with **Global Search, Routing and Mapping APIs & SDKs** by Mappls.

## [Getting Started]()

Mappls Maps SDK for Android lets you easily add Mappls Maps and web services to your own Android application. The SDK for Android supports API 16+. You can have a look at the map and features you will get in your own app by using the Mappls Maps SDK for Android.

Through customized tiles, you can add different map layers to your application and add bunch of controls and gestures to enhance map usability thus creating potent map based solutions for your customers. The SDK handles downloading of map tiles and their display along with a bunch of controls and native gestures.

## [API Usage]()

Your Mappls Maps SDK usage needs a set of license keys (get them  [here](https://apis.mappls.com/console/)) and is governed by the API  [terms and conditions](https://about.mappls.com/api/terms-&-conditions). As part of the terms and conditions,  **you cannot remove or hide the Mappls logo and copyright information** in your project.

The allowed SDK hits are described on the user [dashboard](https://apis.mappls.com/console) page. Note that your usage is shared between platforms, so the API hits you make from a web application, Android app or an iOS app all add up to your allowed daily limit.


## [Setup your project]()

Follow these steps to add the SDK to your project –

-   Create a new project in Android Studio

**For older Build versions (i.e, Before gradle  v7.0.0)**
-  Add Mappls repository in your project level `build.gradle`
~~~groovy  
 allprojects {  
    repositories {  
  
        maven {  
            url 'https://maven.mappls.com/repository/mappls/'  
        }  
    }  
}  
~~~  
**For Newer Build Versions (i.e, After gradle v7.0.0)**
- Add Mappls repository in your `settings.gradle`
```groovy  
dependencyResolutionManagement {  
//   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)  
  repositories {  
        google()  
        mavenCentral()  
        maven {  
            url 'https://maven.mappls.com/repository/mappls/'  
        }  
    }  
   }  
}  
```
-   Add below dependency in your app-level `build.gradle`

```groovy
implementation 'com.mappls.sdk:mappls-android-sdk:8.2.6'
```
- Add these permissions in your project
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.INTERNET"/>
```
### [Add Java 8 Support to the project]()

*add following lines in your app module's build.gradle*

```groovy
compileOptions {
    sourceCompatibility 1.8
    targetCompatibility 1.8
}
  ```

### [Add your API keys to the SDK]()
*Add your API keys to the SDK (in your application's onCreate() or before using map)*

#### Java
```java
MapplsAccountManager.getInstance().setRestAPIKey("SET_REST_API_KEY");
MapplsAccountManager.getInstance().setMapSDKKey("SET_MAP_KEY");
MapplsAccountManager.getInstance().setAtlasClientId("SET_CLIENT_ID");
MapplsAccountManager.getInstance().setAtlasClientSecret("SET_CLIENT_SECRET");
Mappls.getInstance(getApplicationContext());
```
#### Kotlin
```kotlin
MapplsAccountManager.getInstance().restAPIKey = "SET_REST_API_KEY"
MapplsAccountManager.getInstance().mapSDKKey = "SET_MAP_KEY"
MapplsAccountManager.getInstance().atlasClientId = "SET_CLIENT_ID"
MapplsAccountManager.getInstance().atlasClientSecret = "SET_CLIENT_SECRET"
Mappls.getInstance(applicationContext)
```
*You cannot use the Mappls Map Mobile SDK without these function calls. You will find your keys in your API Dashboard.*


## [Add a Mappls Map to your application]()

### Using Map View

This section describes how to add a basic map by using a view.

```xml
<com.mappls.sdk.maps.MapView  
  android:id="@+id/map_view"  
  android:layout_width="match_parent"  
  android:layout_height="match_parent"/>
```
##### NOTE: All the lifecycle methods that need to be overridden:

Initialize the mapView
#### Java
```java
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.base_layout);
   mapView = findViewById(R.id.map_view);
   mapView.onCreate(savedInstanceState);
}

@Override
protected void onStart() {
   super.onStart();
   mapView.onStart();
}

@Override
protected void onStop() {
   super.onStop();
   mapView.onStop();
}

@Override
protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
}

@Override
protected void onPause() {
    super.onPause();
    mapView.onPause();
}

@Override
protected void onResume() {
   super.onResume();
   mapView.onResume();
}

@Override
public void onLowMemory() {
   super.onLowMemory();
   mapView.onLowMemory();
}

@Override
protected void onSaveInstanceState(Bundle outState) {
   super.onSaveInstanceState(outState);
   mapView.onSaveInstanceState(outState);
}
```

#### Kotlin
~~~kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.base_layout)
    mapView = findViewById(R.id.map_view)
    mapView.onCreate(savedInstanceState)
}

override fun onStart() {
    super.onStart()
    mapView.onStart()
}

override fun onResume() {
    super.onResume()
    mapView.onResume()
}

override fun onPause() {
    super.onPause()
    mapView.onPause()
}

override fun onStop() {
    super.onStop()
    mapView.onStop()
}

override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
}

override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
}

override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
}
~~~

### Using Map Fragment

This section describes how to add a basic map by using a fragment as a map container; however, you can use a view instead.

#### Steps to follow

1. Add a `SupportMapFragment` object to the activity that will handle the map.
2. Set the layout file as the content view.
3. If you added the fragment statically, get a handle to the fragment.

### Add a SupportMapFragment object
You can add a `SupportMapFragment` object to your app statically or dynamically. The simplest way is to add it statically. If you add the fragment dynamically, you can perform additional actions on the fragment, such as removing and replacing it at runtime.

### To add a fragment Statically
In the layout file of the activity that will handle the map:

1. Add a fragment element.
2. Add the name declaration `xmlns:map="http://schemas.android.com/apk/res-auto"`. This enables the use of `maps` custom XML attributes.
3. In the fragment element, set the `android:name` attribute to `com.mappls.sdk.maps.SupportMapFragment`.
4. In the fragment element, add the `android:id` attribute and set it to the the R.id.map resource ID `(@+id/map)`.

For example, here's a complete layout file that includes a `fragment` element:
 
```xml
<?xml version="1.0" encoding="utf-8"?>
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:map="http://schemas.android.com/apk/res-auto"
  android:name="com.mappls.sdk.maps.SupportMapFragment"
  android:id="@+id/map"
  android:layout_width="match_parent"
  android:layout_height="match_parent"/>
```

## [Map Interactions]()

The Mappls Maps Android SDK allows you to define interactions that you can activate on the map to enable gestures and click events. The following interactions are supported –

### [Zoom Controls]()

The map supports the familiar two-finger pinch and zooms to change zoom level as well as double tap to zoom in. Set zoom to 4 for country level display and 18 for house number display. In this SDK the camera position plays an important role

And following operations can be performed using the CameraPosition

### [Target]()

The target is single latitude and longitude coordinate that the camera centers it on. Changing the camera's target will move the camera to the inputted coordinates. The target is a LatLng object. The target coordinate is always  _at the center of the viewport_.

### [Tilt]()

Tilt is the camera's angle from the nadir (directly facing the Earth) and uses unit degrees. The camera's minimum (default) tilt is 0 degrees, and the maximum tilt is 60. Tilt levels use six decimal point of precision, which enables you to restrict/set/lock a map's bearing with extreme precision.

The map camera tilt can also adjust by placing two fingertips on the map and moving both fingers up and down in parallel at the same time or

### [Bearing]()

Bearing represents the direction that the camera is pointing in and measured in degrees  _clockwise from north_.

The camera's default bearing is 0 degrees (i.e. "true north") causing the map compass to hide until the camera bearing becomes a non-zero value. Bearing levels use six decimal point precision, which enables you to restrict/set/lock a map's bearing with extreme precision. In addition to programmatically adjusting the camera bearing, the user can place two fingertips on the map and rotate their fingers.

### [Zoom]()

Zoom controls scale of the map and consumes any value between 0 and 22. At zoom level 0, viewport shows continents and other world features. A middle value of 11 will show city level details.At a higher zoom level, map will begin to show buildings and points of interest. Camera can zoom in following ways:

-   Pinch motion two fingers to zoom in and out.
-   Quickly tap twice on the map with a single finger to zoom in.
-   Quickly tap twice on the map with a single finger and hold your finger down on the screen after the second tap.
-   Then slide the finger up to zoom out and down to zoom out.

Sdk provides a OnMapReadyCallback, implements this callback and override it's onMapReady() and set the Camera position inside this method
#### Java
```java
CameraPosition position = new CameraPosition.Builder()
        .target(new LatLng(22.8978, 77.3245)) // Sets the new camera position
        .zoom(14) // Sets the zoom to level 14
        .tilt(45) // Set the camera tilt to 45 degrees
        .build();
mapplsMap.setCameraPosition(position)
```

#### Kotlin
```kotlin
val cameraPosition = CameraPosition.Builder()
    .target(LatLng(22.8978, 77.3245))
    .zoom(10.0)
    .tilt(0.0)
    .build()
mapplsMap?.cameraPosition = cameraPosition
```
##### Sdk allows various method to Move, ease,animate Camera to a particular location  :
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.8978,77.3245),14);
mapplsMap.easeCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.8978,77.3245),14);
mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.8978,77.3245),14);
```
#### Kotlin
~~~kotlin
mapplsMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.8978,77.3245), 14.0))
mapplsMap?.easeCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.8978,77.3245), 14.0))
mapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.8978,77.3245), 14.0))
~~~

## [Map Events]()

##### **The SDK allows you to listen to certain events on the map. It sets a callback that is invoked when camera movement has started.**
#### Java
```java
mapplsMap.addOnCameraMoveStartedListener(new MapplsMap.OnCameraMoveStartedListener() {
private final String[] REASONS = {
        "REASON_API_GESTURE",
        "REASON_DEVELOPER_ANIMATION",
        "REASON_API_ANIMATION"};

@Override
public void onCameraMoveStarted(int reason) {
        String string = String.format(Locale.US, "OnCameraMoveStarted: %s", REASONS[reason - 1]);
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
    }
});
```

#### Kotlin
~~~kotlin
mapplsMap.addOnCameraMoveStartedListener(object : MapplsMap.OnCameraMoveStartedListener {
    private val REASONS: Array<String> = arrayOf(
        "REASON_API_GESTURE",
        "REASON_DEVELOPER_ANIMATION",
        "REASON_API_ANIMATION"
    )
    override fun onCameraMoveStarted(i: Int) {
        var string: String = String.format("OnCameraMoveStarted: %s", REASONS[i - 1])
        Toast.makeText(this@MainActivity, string, Toast.LENGTH_SHORT).show()
    }
})
~~~
##### It sets a callback that is invoked when camera movement was cancelled.
#### Java
```java
mapplsMap.addOnCameraMoveCancelListener(new MapplsMap.OnCameraMoveCanceledListener() {
@Override
public void onCameraMoveCanceled() {
        Toast.makeText(MainActivity.this, "onCameraMoveCanceled", Toast.LENGTH_LONG).show();
   }
});
```

#### Kotlin
~~~kotlin
mapplsMap.addOnCameraMoveCancelListener(object : MapplsMap.OnCameraMoveCanceledListener {
    override fun onCameraMoveCanceled() {
        Toast.makeText(this@MainActivity, "onCameraMoveCanceled", Toast.LENGTH_SHORT).show()
    }
})
~~~
##### It sets a callback that is invoked when camera movement has ended.
#### Java
```java
mapplsMap.addOnCameraIdleListener(new MapplsMap.OnCameraIdleListener() {
@Override
public void onCameraIdle() {
        Toast.makeText(MainActivity.this, "onCameraIdle", Toast.LENGTH_LONG).show();
     }
});
```
#### Kotlin
~~~kotlin
mapplsMap.addOnCameraIdleListener(object : MapplsMap.OnCameraIdleListener {
    override fun onCameraIdle() {
        Toast.makeText(this@MainActivity, "onCameraIdle", Toast.LENGTH_SHORT).show()
   }
})
~~~
### [Map Click/Long Press]()

If you want to respond to a user tapping on a point on the map, you can use a MapEventsOverlay which you need to add on the map as an Overlay.

It sets a callback that's invoked when the user clicks on the map view.
#### Java
```java
mapplsMap.addOnMapClickListener(new MapplsMap.OnMapClickListener() {
@Override
public boolean onMapClick(@NonNull LatLng point) {
        String string = String.format(Locale.US, "User clicked at: %s", point.toString())
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
        return false;
        }
        });
```
####  Kotlin
~~~kotlin
mapplsMap.addOnMapClickListener(object: MapplsMap.OnMapClickListener {
    override fun onMapClick(latLng: LatLng): Boolean {
        val string: String = String.format("User clicked at: %s", latLng.toString())
        Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
        return false
    }
})
~~~
##### Sets a callback that's invoked when the user long clicks on the map view.
#### Java
```java
mapplsMap.addOnMapLongClickListener(new MapplsMap.OnMapLongClickListener() {
@Override
public boolean onMapLongClick(@NonNull LatLng point) {
        String string = String.format(Locale.US, "User long clicked at: %s", point.toString());
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
        return false;
   }
});
```

#### Kotlin
~~~kotlin
mapplsMap.addOnMapLongClickListener(object: MapplsMap.OnMapLongClickListener {
    override fun onMapLongClick(latLng: LatLng): Boolean {
        var string: String = String.format("User long clicked at: %s", latLng.toString())
        Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
        return false
    }
})
~~~
## [Map Overlays]()

### [Add A Marker]()

##### Add markers to the map by following these steps –
#### Java
```java
MarkerOptions markerOptions = new MarkerOptions().position(point).icon(IconFactory.getInstance(this).fromResource(R.drawable.ic_android));
markerOptions.setTitle("Marker");
markerOptions.setSnippet("This is a Marker");
Marker marker = mapplsMap.addMarker(markerOptions);
```
#### Kotlin
~~~kotlin
val markerOptions: MarkerOptions = MarkerOptions().position(point).icon(IconFactory.getInstance(this).fromResource(R.drawable.ic_android))
markerOptions.title= "Marker"
markerOptions.snippet = "This is a Marker"
mapplsMap?.addMarker(markerOptions)
~~~
### [Remove A Marker]()
#### Java
```java
mapplsMap.removeMarker(marker)
```
#### Kotlin
~~~kotlin
mapplsMap?.removeMarker(marker)
~~~
### [Customize A Marker]()
#### Java
```java
MarkerOptions markerOptions = new MarkerOptions().position(point).icon(IconFactory.getInstance(context).fromResource(R.drawable.ic_android));
Marker marker = mapplsMap.addMarker(markerOptions);
marker.setTitle("title");
mapplsMap.setInfoWindowAdapter(new MapplsMap.InfoWindowAdapter() {
	@Nullable
	@Override
	public View getInfoWindow(@NonNull Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.layout, null);
        TextView text = view.findViewById(R.id.text);
        text.setText(marker.getTitle());
        return view;
    }
});
```
#### Kotlin
~~~kotlin
val markerOptions: MarkerOptions = MarkerOptions().position(point).icon(IconFactory.getInstance(context).fromResource(R.drawable.ic_android))
markerOptions.title= "Marker"
mapplsMap?.addMarker(markerOptions)
mapplsMap?.setInfoWindowAdapter {
    val view: View? = getLayoutInflater().inflate(R.layout.layout, null)
    val textView: TextView = view?.findViewById(R.id.text)!!
    textView.text = it.title
    return@setInfoWindowAdapter view
}
~~~
### [Add A Polyline]()
##### Draw polyline on the map
#### Java
```java
mapplsMap.addPolyline(new PolylineOptions()
        .addAll(points)//list of LatLng   
        .color(Color.parseColor("#3bb2d0"))
        .width(2));
```
#### Kotlin
~~~kotlin
mapplsMap.addPolyline(PolylineOptions()
    .addAll(points)
    .color(Color.parseColor("#3bb2d0"))
    .width(2f))
~~~
### [Remove Polyline]()
To remove a polyline from map:
#### Java
```java
mapplsMap.removePolyline(polyline);
```
#### Kotlin
~~~kotlin
mapplsMap.removePolyline(polyLine!!)
~~~
### [Add A Polygon]()

##### Draw a polygon on the map
#### Java
```java
mapplsMap.addPolygon(new PolygonOptions()
        .addAll(polygon)//list of LatLng.  
        .fillColor(Color.parseColor("#3bb2d0")));
```
#### Kotlin
~~~kotlin
mapplsMap.addPolygon(PolygonOptions()
    .addAll(polygon)
    .fillColor(Color.parseColor("#3bb2d0")))
~~~
### [Remove Polygon]()
To remove a polygon from map:
#### Java
```java
mapplsMap.removePolygon(polygon);
```
#### Kotlin
~~~kotlin
mapplsMap.removePolygon(polygon!!)
~~~
## [Show User Location]()

### Show the current user location

#### Java
```java
LocationComponentOptions options = LocationComponentOptions.builder(context)
        .trackingGesturesManagement(true)
        .accuracyColor(ContextCompat.getColor(this, R.color.colorAccent))
        .build();
// Get an instance of the component LocationComponent  
locationComponent = mapplsMap.getLocationComponent();
LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions.builder(this, style)
        .locationComponentOptions(options)
        .build();
// Activate with options  
locationComponent.activateLocationComponent(context, locationComponentActivationOptions ;
// Enable to make component visible  
locationComponent.setLocationComponentEnabled(true);
locationEngine = locationComponent.getLocationEngine();
```

#### Kotlin
~~~kotlin
val options: LocationComponentOptions = LocationComponentOptions.builder(context)
    .trackingGesturesManagement(true)
    .accuracyColor(ContextCompat.getColor(this, R.color.colorAccent))
    .build()
locationComponent = mapplsMap.locationComponent
val locationComponentActivationOptions = LocationComponentActivationOptions.builder(this, style)
    .locationComponentOptions(options)
    .build()
locationComponent.activateLocationComponent(locationComponentActivationOptions)
locationComponent.isLocationComponentEnabled = true
~~~

### Get Last Known Location
##### Java
~~~java
Location location = locationComponent.getLastKnownLocation()
~~~
##### Kotlin
~~~kotlin
val location = locationComponent?.lastKnownLocation
~~~

Where `location` is the Android `Location` object. For more details please refer the [documentation](https://developer.android.com/reference/android/location/Location)

### Get Location Change Callback
Implement `LocationEngineCallback` and override it's method
~~~java
LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
        .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
        .build();
locationEngine.requestLocationUpdates(request, locationEngineCallback, getMainLooper());

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


// To remove Location Update Listener
@Override
protected void onDestroy() {
  super.onDestroy();
        // Prevent leaks  
        if (locationEngine != null) {
	        locationEngine.removeLocationUpdates(locationEngineCallback);
        }
   }
~~~
#### Kotlin
~~~kotlin
locationEngine = locationComponent.locationEngine!!
val request = LocationEngineRequest.Builder(1000)
    .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
    .build()
locationEngine?.requestLocationUpdates(request, locationEngineCallback, mainLooper)


val locationEngineCallback = object : LocationEngineCallback<LocationEngineResult> {
    override fun onSuccess(result: LocationEngineResult?) {
        if(result?.lastLocation != null) {
            val location = result.lastLocation
        }
    }

    override fun onFailure(e: Exception) {

    }
}
// To remove Location Update Listener
override fun onDestroy() {
    super.onDestroy()
    if (locationEngine != null) {
        locationEngine?.removeLocationUpdates(locationEngineCallback)
    }
}
~~~

### Set Location Camera Mode
It allows developers to set specific camera tracking instructions as the device location changes.
#### Java
~~~java
locationComponent.setCameraMode(CameraMode.TRACKING);
~~~
#### Kotlin
~~~kotlin
locationComponent.cameraMode = CameraMode.TRACKING
~~~
Following are the possible values for `CameraMode`:
- `CameraMode.NONE`: No camera tracking.
- `CameraMode.NONE_COMPASS`: Camera does not track location, but does track compass bearing.
- `CameraMode.NONE_GPS`: Camera does not track location, but does track GPS `Location` bearing.
- `CameraMode.TRACKING`: Camera tracks the device location, no bearing is considered.
- `CameraMode.TRACKING_COMPASS`: Camera tracks the device location, tracking bearing provided by the device compass.
- `CameraMode.TRACKING_GPS`: Camera tracks the device location, with bearing provided by a normalized `Location#getBearing()`.
- `CameraMode.TRACKING_GPS_NORTH`: Camera tracks the device location, with bearing always set to north (0).

### Set Render Mode
The `RenderMode` class contains preset options for the device location image.
#### Java
~~~java
locationComponent.setRenderMode(RenderMode.COMPASS);
~~~
#### Kotlin
~~~kotlin
locationComponent.renderMode = RenderMode.COMPASS
~~~
Following are the possible values for `RenderMode`:
- `RenderMode.NORMAL`: This mode shows the device location, ignoring both compass and GPS bearing (no arrow rendered).
- `RenderMode.COMPASS`: This mode shows the device location, as well as an arrow that is considering the compass of the device.
- `RenderMode.GPS`: This mode shows the device location with the icon bearing updated from the `Location` updates being provided to the `LocationComponent`.

## [Calculate distance between two points]()
To calculate aerial distance between two points:
#### Java
~~~java
LatLng firstLatLng = new LatLng(28, 77);
LatLng secondLatLng = new LatLng(28.67, 77.65);
firstLatLng.distanceTo(secondLatLng);
~~~
#### Kotlin
~~~kotlin
val firstLatLng = LatLng(28.0, 77.0)
val secondLatLng = LatLng(28.67, 77.65)
firstLatLng.distanceTo(secondLatLng)
~~~

### [Proguard]()
```
-keep class com.mappls.sdk.maps.GetStylesResponse {
    <fields>;
    <methods>;
}
-keep class com.mappls.sdk.maps.StyleData {
    <fields>;
    <methods>;
}
```

<br><br><br>



## Our many happy customers

![](https://www.mapmyindia.com/api/img/logos1/PhonePe.png)![](https://about.mappls.com/images/client_logo/amazon-a.svg)![](https://www.mapmyindia.com/api/img/logos1/delhivery.png)![](https://www.mapmyindia.com/api/img/logos1/hdfc.png)![](https://www.mapmyindia.com/api/img/logos1/TVS.png)![](https://www.mapmyindia.com/api/img/logos1/Paytm.png)![](https://about.mappls.com/images/client_logo/mcdonalds-01.svg)![](https://www.mapmyindia.com/api/img/logos1/ICICI-Pru.png)![](https://about.mappls.com/images/client_logo/honda.svg)![](https://www.mapmyindia.com/api/img/logos1/TTSL.png)![](https://www.mapmyindia.com/api/img/logos1/Novire.png)![](https://about.mappls.com/images/client_logo/airtel.svg)![](https://www.mapmyindia.com/api/img/logos1/Sensel.png)![](https://www.mapmyindia.com/api/img/logos1/TATA-MOTORS.png)![](https://www.mapmyindia.com/api/img/logos1/Wipro.png)![](https://about.mappls.com/api/images_page/grofers.svg)  [<img src="https://about.mappls.com/images/original/avis_logo-01.svg" height="30"/>]()    [<img src="https://about.mappls.com/images/client_logo/CBDT.svg" height="60"/>]()   [<img src="https://about.mappls.com/images/client_logo/finance.png" height="60"/> </p>]()

<br>

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




<div align="center">@ Copyright 2024 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
