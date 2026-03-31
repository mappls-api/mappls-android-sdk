[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# [Map Events]()
The SDK provides event listeners that let you monitor and respond to various map interactions and state changes, such as camera movements, marker clicks, or gesture inputs.

## [Map Click Event]()
This event fires whenever the user performs a tap or click on the map surface, allowing you to respond to user interactions.

#### Kotlin
```kotlin
val callback = object: MapplsMap.OnMapClickListener {
    override fun onMapClick(p0: LatLng): Boolean {
        return false
    }
}
// To add Listener
mapplsMap.addOnMapClickListener(callback)

//To remove Listener
mapplsMap.removeOnMapClickListener(callback)
```
#### Java
```java
MapplsMap.OnMapClickListener callback = new MapplsMap.OnMapClickListener() {
    @Override
    public boolean onMapClick(@NonNull LatLng latLng) {
        return false; 
    }
}
// To add Listener
mapplsMap.addOnMapClickListener(callback);

//To remove Listener
mapplsMap.removeOnMapClickListener(callback);
```

## [Map Long Click Event]()
This event is fired when the user long-presses on the map surface, enabling custom actions or interactions based on long-click input.


#### Kotlin
```kotlin
val callback = object: MapplsMap.OnMapLongClickListener {
    override fun onMapLongClick(p0: LatLng): Boolean {
        return false
    }
}
// To add Listener
mapplsMap.addOnMapLongClickListener(callback)

//To remove Listener
mapplsMap.removeOnMapLongClickListener(callback)
```
#### Java
```java
MapplsMap.OnMapLongClickListener callback = new MapplsMap.OnMapLongClickListener() {
    @Override
    public boolean onMapLongClick(@NonNull LatLng latLng) {
        return false; 
    }
}
// To add Listener
mapplsMap.addOnMapLongClickListener(callback);

//To remove Listener
mapplsMap.removeOnMapLongClickListener(callback);
```

## [Camera Events]()
The SDK provides several camera events, including:

### [Camera Movement Ended]()
Triggered when the camera movement completes, this callback indicates that all animations or user-driven camera changes have finished.

#### Kotlin
```kotlin
val callback = object: MapplsMap.OnCameraIdleListener {
    override fun onCameraIdle() {
                
    }
}
// To add Listener
mapplsMap.addOnCameraIdleListener(callback)

//To remove Listener
mapplsMap.removeOnCameraIdleListener(callback)
```
#### Java
```java
MapplsMap.OnCameraIdleListener callback = new MapplsMap.OnCameraIdleListener() {
    @Override
    public void onCameraIdle() {
                        
    }
};
// To add Listener
mapplsMap.addOnCameraIdleListener(callback);

//To remove Listener
mapplsMap.removeOnCameraIdleListener(callback);
```

### [Camera Movement Canceled]()
Triggered when a camera movement is cancelled, this callback lets you respond to aborted or interrupted map camera animations or gestures.

#### Kotlin
```kotlin
val callback = object: MapplsMap.OnCameraMoveCanceledListener {
    override fun onCameraMoveCanceled() {
        
    }
}
// To add Listener
mapplsMap.addOnCameraMoveCancelListener(callback)

//To remove Listener
mapplsMap.removeOnCameraMoveCancelListener(callback)
```
#### Java
```java
MapplsMap.OnCameraMoveCanceledListener callback = new MapplsMap.OnCameraMoveCanceledListener() {
    @Override
    public void onCameraMoveCanceled() {
    }
};
// To add Listener
mapplsMap.addOnCameraMoveCancelListener(callback);

//To remove Listener
mapplsMap.removeOnCameraMoveCancelListener(callback);
```

### [Camera Movement Started]()
Triggered when camera movement starts, this callback helps identify the initiation of a map transition, whether due to gestures, animations, or code-driven changes.

#### Kotlin
```kotlin
val callback = object : MapplsMap.OnCameraMoveStartedListener {
    override fun onCameraMoveStarted(reason: Int) {
    }
}
// To add Listener
mapplsMap.addOnCameraMoveStartedListener(callback)

//To remove Listener
mapplsMap.removeOnCameraMoveStartedListener(callback)
```

#### Java
```java
MapplsMap.OnCameraMoveStartedListener callback = new MapplsMap.OnCameraMoveStartedListener() {
    @Override
    public void onCameraMoveStarted(int reason) {
        
    }
};
// To add Listener
mapplsMap.addOnCameraMoveStartedListener(callback);

//To remove Listener
mapplsMap.removeOnCameraMoveStartedListener(callback);
```
Values of reason parameter:
- `REASON_API_GESTURE`
- `REASON_DEVELOPER_ANIMATION`
- `REASON_API_ANIMATION`

### [Camera Movement Changes]()
Invoked when there is a change in camera movement, this callback allows you to monitor transitions such as pan, zoom, tilt, or bearing updates on the map.

#### Kotlin
```kotlin
val callback = object : MapplsMap.OnCameraMoveListener {
    override fun onCameraMove() {
        
    }
}
// To add Listener
mapplsMap.addOnCameraMoveListener(callback)

//To remove Listener
mapplsMap.removeOnCameraMoveListener(callback)
```
#### Java
```java
MapplsMap.OnCameraMoveListener callback = new MapplsMap.OnCameraMoveListener() {
    @Override
    public void onCameraMove() {
        
    }
};
// To add Listener
mapplsMap.addOnCameraMoveListener(callback);

//To remove Listener
mapplsMap.removeOnCameraMoveListener(callback);
```

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




<div align="center">@ Copyright 2026 CE Info Systems Ltd. All Rights Reserved.</div>    

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>    

<div align="center">Customer Care: +91-9999333223</div>
