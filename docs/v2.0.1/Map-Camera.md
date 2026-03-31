[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# [Map Camera]()
The Mappls Maps SDK camera represents the user’s viewpoint over the map. It provides flexible options to set, update, and animate the camera position, as well as listen for camera movement events and retrieve the current camera state at any time.

## [Camera position]()
The Mappls Maps SDK for Android gives you full control over the map camera's position and behavior, enabling you to create highly customized and interactive map experiences. The camera is defined by several key properties:

- `target`: The geographic coordinates (latitude and longitude) that the camera focuses on.

- `bearing`: The map’s rotation, representing the direction the camera is facing. A bearing of 0° points north, while increasing values rotate the map clockwise to indicate a different orientation.

- `tilt`: The angle between the camera and the ground plane. A tilt of 0° provides a top-down view, while higher values (e.g., 60°) offer a more angled, horizon-facing perspective.

- `zoom`: Controls how close the camera is to the map content. Lower zoom levels (e.g., 1) show large areas like continents, mid-range values (e.g., 11) show city-level detail, and higher zoom levels reveal streets, buildings, and points of interest.

- `padding`: Specifies the insets from the map's edges, affecting where the target appears within the visible viewport. Padding is useful for adjusting camera focus without moving the map center visually.

### [Set the Camera Position on Map]()

- Using `setCameraPosition`
    #### Kotlin
    ```kotlin
    val cameraPosition = CameraPosition.Builder().tilt(0.0).zoom(3.0).target(LatLng(28.61, 77.23)).build()
    mapplsMap.cameraPosition = cameraPosition
    ```
    #### Java
    ```java
    CameraPosition cameraPosition = new CameraPosition.Builder().zoom(3.0).bearing(0.0).tilt(0.0).target(new LatLng(28.61, 77.23)).build();
    mapplsMap.setCameraPosition(cameraPosition);
    ```

- Using `moveCamera` or `animateCamera` or `easeCamera`
    #### Kotlin
    ```kotlin
    val cameraPosition = CameraPosition.Builder().tilt(0.0).zoom(3.0).target(LatLng(28.61, 77.23)).build()
    mapplsMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    ```
    #### Java
    ```java
    CameraPosition cameraPosition = new CameraPosition.Builder().zoom(3.0).bearing(0.0).tilt(0.0).target(new LatLng(28.61, 77.23)).build();
    mapplsMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    ```
## [Camera Animations]()
Mappls Map's SDK support three types of Animations:
- Move Camera
- Ease Camera
- Animate Camera

### [Move Camera]()
This method immediately updates the map to the specified CameraPosition without applying any transition or animation, functioning similarly to `setCameraPosition`:

#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
```

### [Ease Camera]()
This method transitions the map to the provided CameraPosition with a grounded animation, offering a smooth and immersive camera movement experience.

#### Kotlin
```kotlin
mapplsMap.easeCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
```
#### Java
```java
mapplsMap.easeCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
```

### [Animate Camera]()
This method transitions the map to the specified CameraPosition with a flight animation, simulating a smooth, high-altitude movement for a more dynamic visual experience.


#### Kotlin
```kotlin
mapplsMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
```
#### Java
```java
mapplsMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
```

>> Note: When using `easeCamera` and `animateCamera`, you need to wait until the camera movement is complete before calling new camera control functions. To detect when the camera movement finishes, you can use `CancelableCallback`.

#### Kotlin
```kotlin
mapplsMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), object: MapplsMap.CancelableCallback {
    override fun onCancel() {
        // Cancel Due to other camera movement invoke
    }

    override fun onFinish() {
        // Finish camera movement
    }
})
```

#### Java
```java
mapplsMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new MapplsMap.CancelableCallback() {
    @Override
    public void onCancel() {
        // Cancel Due to other camera movement invoke
    }
    @Override
    public void onFinish() {
        // Finish camera movement
    }
});
```

**Map SDK support :  More Controls on Camera [Here](./Camera-Control.md)**


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
