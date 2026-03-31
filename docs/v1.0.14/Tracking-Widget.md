[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Tracking Widget

## Getting started

This advanced tracking plugin, offered by Mappls plugins for android, allows one to track the path traveled with **smooth animation** along the route. The smooth animation by plugin directly depend upon the frequency of the provided information on the current location, time, and speed of the vehicle being tracked to the plugin. _More the Merrier!_

### Add the dependency
- Add below dependency in your app-level build.gradle

#### Kotlin (build.gradle.kts)
```Kotlin
implementation 'com.mappls.sdk:mappls-tracking-plugin:1.0.1'
```
#### Groovy (build.gradle)
```groovy
implementation 'com.mappls.sdk:mappls-tracking-plugin:1.0.1'
```
### Add MapplsTrackingWidget 
#### Kotlin
~~~kotlin
 val trackingOption = TrackingOption.Builder(
                    origin = Point.fromLngLat(77.26890561043258, 28.550947419414012),
                    destination = Point.fromLngLat(77.25988209737588, 28.55373960502866),
                    riderIcon = AppCompatResources.getDrawable(this, R.drawable.bikeicon)!!,
                    destinationIcon = AppCompatResources.getDrawable(this, R.drawable.marker)!!,
                    originIcon = AppCompatResources.getDrawable(this, R.drawable.marker)!!,
                    lineColor = Color.BLUE,
                    directionRouteType = DirectionsCriteria.ROUTE_TYPE_SHORTEST,
                    directionResource = DirectionsCriteria.RESOURCE_ROUTE,
                )

                mapplsTrackingManager = MapplsTrackingManager(mBinding.mapView, mapplsMap, trackingOption.build())
                mapplsTrackingManager?.setOnSegmentCompleteListener { data ->
                    Log.d("formattedJson", Gson().toJson(data))
                }
~~~
#### Java
~~~java
TrackingOption trackingOption = new TrackingOption.Builder(Point.fromLngLat(77.26890561043258, 28.550947419414012), Point.fromLngLat(77.25988209737588, 28.55373960502866))
                .build();

// Create MapplsTrackingManager
MapplsTrackingManager mapplsTrackingManager =
        new MapplsTrackingManager(mBinding.mapView, mapplsMap, trackingOption);

// Set the segment complete listener
 mapplsTrackingManager.setOnSegmentCompleteListener(new Function1<TrackingData, Unit>() {
            @Override
            public Unit invoke(TrackingData trackingData) {
                Log.d("formattedJson", new Gson().toJson(trackingData));
                return Unit.INSTANCE; // <- required
            }
});
~~~
### Mandatory Request Properties
  - `origin(Point)` : route start location . For eg -  { Point.fromLngLat(77.26890561043258, 28.550947419414012) }
  - `destination(Point)` : route end location . For eg -   { Point.fromLngLat(77.25988209737588, 28.55373960502866)}

### Request Parameters
 3. `lineColor(Int)` : To set a lineColor for route draw.
 4. `connectorLineColor(Int)` :  To set a color for draw a connector line from the last point of a route on the road to the actual input destination coordinate.
 5. `enableConnectorLine(Boolean)` : To set boolean value for hide/show connector line from the last route point on road to actual input destination coordinate // default false
  6. `lineWidth(Float)` : To set a width of route line.
  7. `iconSize(Float)` : To set a iconSize of originIcon,viaPointIcon & destinationIcon.
 8. `directionRouteType(Int)` : To set the routeType. Default to "DirectionsCriteria.ROUTE_TYPE_SHORTEST".
  9. `destinationIcon(Drawable)` : To set destination icon.
10. `originIcon(Drawable)` : To set  origin icon set.
11. `riderIcon(Drawable)` : To set  rider icon set.
12.  `directionResource(String)`: To set the route resource. Default to "route_eta".
13.  `routeChangeBuffer(Int)`:  The distance defined for call reroute for the provided current location.
14.  `polylineRefresh(Boolean)`: To remove the route at the same time as the rider progresses along the route.
15.  `latentViz(LatentViz)`: To set the string value for smooth visualization when rider suddenly jumps off-route. Incurs an additional routing call.// [Acceptable values for this is : jump,fly & route]
16. `latentVizRadius(Int)`:

17. `animationSpeed` (Long): This value is speed in metres/sec at which the rider simulation will be started at. The ride simulation will be slowed at a defined rate below this speed after every few metres to enable a very smooth and slow animation till the time a fresh active location is injected tot the widget or max simulation distance is reached.
18.  `fakeSimDistance(Int)`: To set the fake animation distance.
19.  `fakeSpeedMetersPerSecond(Int)`: To set the fake animation speed in MetersPerSecond.
 20.  `viaPoints( MutableList<ViaPoint>)`: To set list of via points.
 21. `lastRiderLocation(Point)`: This parameter is used to pass the last known location (long,lat) coordinates of the rider.
22. `riderIconSize(Float)`: To set a iconSize of riderIcon.
23. `directionProfile(String)`: Below are the available profile:
- DirectionsCriteria.PROFILE_DRIVING (Default):Meant for car routing
- DirectionsCriteria.PROFILE_BIKING:Meant for two-wheeler routing. Routing with this profile is restricted to route_adv only. region & rtype request parameters are not supported in two-wheeler routing.

###  Method calls :- 

- #### For Start Tracking
    #### Kotlin
    ~~~kotlin
    val startmarkerLocation = Point.fromLngLat(
                77.26890561043258,
                28.550947419414012
            )
        mapplsTrackingManager?.startTrackingVehicle(startmarkerLocation)
    ~~~

    #### Java
    ~~~java
    Point startMarkerLocation = Point.fromLngLat(77.26890561043258, 28.550947419414012);
        if (mapplsTrackingManager != null) {
        mapplsTrackingManager.startTrackingVehicle(startMarkerLocation);
        }
    ~~~
- #### For Removing Curved Line
    #### Kotlin
    ~~~kotlin
    mapplsTrackingManager?.removeCurveLine(true)
    ~~~
    #### Java
    ~~~java
    if (mapplsTrackingManager != null) {
        mapplsTrackingManager.removeCurveLine(true);
        }
    ~~~
- #### For Hide/Show Polyline
    #### Kotlin
    ~~~kotlin
    mapplsTrackingManager?.setPolylineVisibility(false)
    ~~~
    #### Java
    ~~~java
    if (mapplsTrackingManager != null) {
        mapplsTrackingManager.setPolylineVisibility(false);
    }
- #### For Enable/Disable FitBound Route
    #### Kotlin
    ~~~kotlin
    mapplsTrackingManager?.setFitBounds(false, 15.0, Padding(0, 0, 0, 0))
    ~~~
    #### Java
    ~~~java
    if (mapplsTrackingManager != null) {
        mapplsTrackingManager.setFitBounds(false, 15.0, new Padding(0, 0, 0, 0));
    }
    ~~~
- #### For removing vaiPoint based on Id
    #### Kotlin
    ~~~kotlin 
    mapplsTrackingManager?.removeViaPointById(<viapointID>) { success ->
    if (success) {
        Toast.makeText(this,"Waypoint Successfully removed",Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(this, "Waypoint Failed to remove", Toast.LENGTH_LONG).show()
    }
    }
    ~~~
    #### Java
    ~~~java 
    mapplsTrackingManager.removeViaPointById("", new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean success) {
                if (success) {
                    Toast.makeText(this, "Waypoint Successfully removed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Waypoint Failed to remove", Toast.LENGTH_LONG).show();
                }
                return Unit.INSTANCE; // <- required
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




<div align="center">@ Copyright 2026 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
