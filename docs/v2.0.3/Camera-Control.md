[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# [Camera Controls]()
There are multiple ways to control the Camera Position in Mappls SDKs. Following are the methods tha you can use:

## [Move To Target]()
This method allows to move the camera towards the target location. 
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
```

## [Move To Target with Zoom]()
This method allows to move the camera towards the target location with a fixed zoom.

#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoom))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
```

## [Move To Target with Padding]()
This method allows to move the camera towards the target location with padding.

#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngPadding(LatLng(latitude, longitude), left, top, right, bottom))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngPadding(new LatLng(latitude, longitude), left, top, right, bottom));
```

## [Move To Bound]()
This method allows to move the camera towards the target location with view bounds.

#### Kotlin
```kotlin
val latLngBounds = LatLngBounds.Builder()
    .include(LatLng(lat1, lng1))
    .include(LatLng(lat2, lng2))
    .build()
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, left, top, right, bottom))
```
#### Java
```java
LatLngBounds latLngBounds = new LatLngBounds.Builder()
            .include(new LatLng(lat1, lng1))
            .include(new LatLng(lat2, lng2))
            .build();
mapplsMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, left, top, right, bottom));
```

## [Zoom To]()
This method allows to zoom the camera to a particular zoom-level. Example, 18/14 etc.
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.zoomTo(zoom))          
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
```

## [Zoom By]()
This method allows to zoom the camera to a particular zoom by some amount of zoom level
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.zoomBy(2.0))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.zoomBy(2.0));
```

## [Zoom In]()
This method allows to zoom in.
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.zoomIn())
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.zoomIn()); 
```

## [Zoom Out]()
This method allows to zoom out.
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.zoomOut())
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.zoomOut());
```

## [Bearing To]()
This method allows to zoom with respect to the bearing.
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.bearingTo(bearing))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.bearingTo(bearing));
```
## [Tilt To]()
This method allows you to tilt the camera to specefic tilt in degrees eg.,20.
#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.tiltTo(tilt))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.tiltTo(tilt));
```

## [Padding To]()
This method allows to move the camera towards the  location with padding.

#### Kotlin
```kotlin
mapplsMap.moveCamera(CameraUpdateFactory.paddingTo(left, top, right, bottom))
```
#### Java
```java
mapplsMap.moveCamera(CameraUpdateFactory.paddingTo(left, top, right, bottom));
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
