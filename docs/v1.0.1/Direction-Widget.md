[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Direction Widget

A ready to use Fragment to show the Routes in an Android platform.  It offers the following basic functionalities:

1. Takes support of Mappls Place search for searching locations of origin, destinations and via points.
2. It allows to use origin and destinations in Mappls digital address (semicolon separated) mapplsPin or WGS 84 geographical coordinates both.
3.  The ability to set the vehicle profile like driving, and biking.
4. Easily set the resource for traffic and ETA information.

For more details, please contact apisupport@mappls.com.

## Add the dependency

Add below dependency in your app-level build.gradle
~~~groovy	
implementation 'com.mappls.sdk:direction-ui:1.0.3'
~~~

### Add your API keys to the SDK

_Add your API keys to the SDK (in your application's onCreate() or before using map)_
#### Java
```java	
MapplsAccountManager.getInstance().setRestAPIKey(getRestAPIKey());  	
MapplsAccountManager.getInstance().setMapSDKKey(getMapSDKKey());  		
MapplsAccountManager.getInstance().setAtlasClientId(getAtlasClientId());  	
MapplsAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());  	
```	
#### Kotlin	
```kotlin	
MapplsAccountManager.getInstance().restAPIKey = getRestAPIKey()  	
MapplsAccountManager.getInstance().mapSDKKey = getMapSDKKey()  		
MapplsAccountManager.getInstance().atlasClientId = getAtlasClientId()  	
MapplsAccountManager.getInstance().atlasClientSecret = getAtlasClientSecret()	
```	

### Add Direction Widget
  
#### Java  
```java  
DirectionFragment directionFragment = DirectionFragment.newInstance();  
  
getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, directionFragment, DirectionFragment.class.getSimpleName())    
        .commit();  
  
                            //OR  
DirectionFragment directionFragment = DirectionFragment.newInstance(directionOptions);  
  
getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, directionFragment, DirectionFragment.class.getSimpleName())    
        .commit();  
```  
#### Kotlin
~~~kotlin  
val directionFragment: DirectionFragment = DirectionFragment.newInstance()  
supportFragmentManager.beginTransaction().add(R.id.fragment_container, directionFragment, DirectionFragment::class.java.simpleName)    
        .commit()  
                                 
                               //OR  
val directionFragment: DirectionFragment = DirectionFragment.newInstance(directionOptions)  
supportFragmentManager.beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment::class.java.simpleName)    
        .commit()                                 
~~~  

You can use `DirectionOptions` to set the properties of direction widget:

1. `resource(String)`:  **Below are the available resource:**
  - DirectionsCriteria.RESOURCE_ROUTE **(Default)**: to calculate a route & its duration without considering traffic conditions.
  - DirectionsCriteria.RESOURCE_ROUTE_ETA get the updated duration of a route considering live traffic; Applicable for India only "region=ind" and "rtype=1" is not supported. This is different from route_traffic; since this doesn't search for a route considering traffic, it only applies delays to the default route.
  - DirectionsCriteria.RESOURCE_ROUTE_TRAFFIC:    
    to search for routes considering live traffic; Applicable for India only “region=ind” and “rtype=1” is not supported
2. `showAlternative(Boolean)`: Show alternative routes.
3. `profile(String)`: **Below are the available profile:**
  - DirectionsCriteria.PROFILE_DRIVING **(Default)**:Meant for car routing
  - DirectionsCriteria.PROFILE_WALKING:  Meant for pedestrian routing. Routing with this profile is restricted to route_adv only. region & rtype request parameters are not supported in pedestrian routing
  - DirectionsCriteria.PROFILE_BIKING:Meant for two-wheeler routing. Routing with this profile is restricted to route_adv only. region & rtype request parameters are not supported in two-wheeler routing.
  - DirectionsCriteria.PROFILE_TRUCKING:Meant for Truck routing. Routing with this profile is restricted to route_adv only. region & rtype request parameters are not supported in truck routing.
4. `overview(String)`:  Add overview geometry either full, simplified according to highest zoom level it could be display on, or not at all. **Below are the available value:**
  - DirectionsCriteria.OVERVIEW_FULL
  - DirectionsCriteria.OVERVIEW_FALSE
  - DirectionsCriteria.OVERVIEW_SIMPLIFIED
5. `steps(Boolean)`: Return route steps for each route leg. Possible values are true/false. By default it will be used as false.
6. `excludes(List<String>)` : Additive list of road classes to avoid, order does not matter. **Below are the available value:**
  - DirectionsCriteria.EXCLUDE_FERRY
  - DirectionsCriteria.EXCLUDE_MOTORWAY
  - DirectionsCriteria.EXCLUDE_TOLL
7. `showStartNavigation(Boolean)`: To show the Start Navigation button if the origin is current location.
8. `showDefaultMap(Boolean)`: To add the option to show default map
9. `destination(DirectionPoint)`: You can use `DirectionPoint` to pass the destination in direction widget:
  - `setDirection(Point, String, String)`: It takes coordinate, place name and place address
  - `setDirection(String, String, String)`: It takes mappls pin, place name and place address
10. `searchPlaceOption(PlaceOptions)`: To set the properties of search widget.
11. `showAddWaypointOption(Boolean)`: To show/hide add waypoint functionality.

## Additional Parameter - Search Along The Route

`searchAlongRoute(Boolean)` : An easy, ready to use UI has been introduced to search pois Along the route with default categories list.Default is true.

To access this parameter , please contact [API Support](mailto:apisupport@mappls.com) This parameter takes the encoded route along which POIs will be searched.

This parameter is further having configurable options listed below.

- alongRouteBuffer (Integer) : 200, _// Buffer of the road. Minimum value is 25m, maximum is 1000m and default is 25m_

CategoryCode:
This class is used to set the information for the poi categories to show in Widget. It contains the following properties in constructor:

- category (String): Name of the category that display on a view
- icon(Integer): To show icon of category
- categoryCode (List<String>): List of category codes
- markerIcon (Integer): Marker icon to display on a map
- isSelected (Boolean): To set the category is selected or not.

```java
List<CategoryCode> categoryCodes = new ArrayList<>();  
List<String> coffeeCatgories = new ArrayList<>();  
coffeeCatgories.add("FODCOF");  
categoryCodes.add(new CategoryCode("Coffee", R.drawable.icon_coffee,coffeeCatgories, R.drawable.ic_map_coffee));  
DirectionOptions directionOptions = DirectionOptions.builder()    
        .alongRouteBuffer(300)  
        .searchAlongRoute(true)  
        .build();  
directionFragment = DirectionFragment.newInstance(directionOptions);  
directionFragment.setCategoryCodes(categoryCodes);  
addFragment(R.id.container, directionFragment, false, false);
```



### To pass the MapView
~~~java  
directionFragment.provideMap(MapView)  
~~~  

### Callbacks getting from Direction Fagment
Implement from DirectionCallback interface:

~~~java  
directionFragment.setDirectionCallback(new DirectionCallback() {    
    @Override    
  public void onCancel() {    
        //on Click of back button  
  }    
    
    @Override    
  public void onStartNavigation(DirectionPoint origin, DirectionPoint destination, List<DirectionPoint> waypoints, DirectionsResponse directionsResponse, int selectedIndex) {    
        //Get the origin, destination, waypoints, directionsResponse and the selected Index  
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
