[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Map Overlays
The Map SDK supports the following overlays using Annotations:

- Marker
- Polyline
- Polygon



## [Marker]()
A Marker is an annotation that displays an icon at a specific geographical location on the map. By default, a marker uses a predefined icon, but you can customize it by using the IconFactory to create an icon from any provided image. To add a marker, specify a LatLng position and call addMarker. Since the marker icon is centered on this position, it’s common to add padding around the image to align it visually as needed.

Markers are interactive by design — they respond to click events out of the box and are often paired with event listeners to show InfoWindows. An InfoWindow appears automatically when the marker has either a title or snippet assigned, providing additional context or details.


### [Add Marker]()
To add the marker with default icon
#### Kotlin
```kotlin
val marker = mapplsMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)))
```
#### Java
```java
Marker marker = mapplsMap.addMarker(new MarkerOptions().position(LatLng(latitude, longitude)));
```

### [Add Marker with Infowindow]()
To add the marker with infowindow on click of marker
#### Kotlin
```kotlin
mapplsMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title("Title").snippet("Description"))
```
#### Java
```java
mapplsMap.addMarker(new MarkerOptions().position(LatLng(latitude, longitude)).title("Title").snippet("Description"));
```

### [Custom Marker]()
To add the marker with custom Icon
#### Kotlin
```kotlin
mapplsMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).icon(IconFactory.getInstance(context).fromResource(R.drawable.map_marker_icon)))
```
#### Java
```java
mapplsMap.addMarker(new MarkerOptions().position(LatLng(latitude, longitude)).icon(IconFactory.getInstance(context).fromResource(R.drawable.map_marker_icon)));
```

### [Custom InfoWindow]()
To add custom Info window
#### Kotlin
```kotlin
mapplsMap.setInfoWindowAdapter(object: MapplsMap.InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker): View? {
        val view: View? = getLayoutInflater().inflate(R.layout.layout, null)
        val textView: TextView = view?.findViewById(R.id.text)!!
        textView.text = marker.title
        return view
    }
})
```
#### Java
```java
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

### [Remove Marker]()
To remove the marker
#### Kotlin
```kotlin
mapplsMap.removeMarker(marker)
```
#### Java
```java
mapplsMap.removeMarker(marker);
```

## [Polyline]()
A Polyline is a map feature that represents an open series of connected coordinates drawn as a continuous line.

### [Add polyline]()
To draw a polyline on Map
#### Kotlin
```kotlin
val polyline = mapplsMap.addPolyline(PolylineOptions()
    .addAll(points)
    .color(Color.parseColor("#3bb2d0"))
    .width(2f))
```
#### Java
```java
Polyline polyline = mapplsMap.addPolyline(new PolylineOptions()
        .addAll(points)//list of LatLng   
        .color(Color.parseColor("#3bb2d0"))
        .width(2));
```

### [Remove Polyline]()
To remove polyline from Map
#### Kotlin
```kotlin
mapplsMap.removePolyline(polyLine)
```
#### Java
```java
mapplsMap.removePolyline(polyline);
```

## [Polygon]()
A Polygon is a closed shape formed by connecting a series of coordinates, creating a filled area on the map.

### [Add Polygon]()
To add a polygon on Map
#### Kotlin
```kotlin
val polygon = mapplsMap.addPolygon(PolygonOptions()
    .addAll(polygon)
    .fillColor(Color.parseColor("#3bb2d0")))
```
#### Java
```java
Polygon polygon = mapplsMap.addPolygon(new PolygonOptions()
    .addAll(polygon)//list of LatLng.  
    .fillColor(Color.parseColor("#3bb2d0")));
```

### [Remove Polygon]()
To remove a polygon
#### Kotlin
```kotlin
mapplsMap.removePolygon(polygon)
```
#### Java
```java
mapplsMap.removePolygon(polygon);
```

## [Clear All Overlay]()
To clear all `Marker`, `Polyline` & `Polygon`
#### Kotlin
```kotlin
mapplsMap.clear()
```
#### Java
```java
mapplsMap.clear();
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




<div align="center">@ Copyright 2025 CE Info Systems Ltd. All Rights Reserved.</div>    

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>    

<div align="center">Customer Care: +91-9999333223</div>
