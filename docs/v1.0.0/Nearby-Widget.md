
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)


# Mappls Nearby Search Widget - Advanced Details

Mappls Nearby Search Widget is highly configurable widget, it provides options to customized almost everything.

For installation / basic information [click here](./Nearby-Widget.md)

## [Advanced Configurations](#Advanced-Configurations)

To set advanced configurations in Nearby Search widget.
### Using MapplsNearbyFragment
#### Java
~~~java
MapplsNearbyFragment nearbyFragment = MapplsNearbyFragment.newInstance(nearbyOptions);
                        //OR
MapplsNearbyFragment nearbyFragment = MapplsNearbyFragment.newInstance(nearbyUiOptions);
                       //OR
MapplsNearbyFragment nearbyFragment = MapplsNearbyFragment.newInstance(nearbyOption,nearbyUiOptions);                       
nearbyFragment.setCategoryList(categories);                       
getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, nearbyFragment, MapplsNearbyFragment.class.getSimpleName())  
        .commit();
~~~
#### Kotlin
~~~kotlin
val nearbyFragment: MapplsNearbyFragment= MapplsNearbyFragment.newInstance(nearbyOptions)
                  //OR
val nearbyFragment: MapplsNearbyFragment= MapplsNearbyFragment.newInstance(nearbyUIOptions)  
                 //OR
val nearbyFragment: MapplsNearbyFragment= MapplsNearbyFragment.newInstance(nearbyOptions,nearbyUIOptions)                                 
nearbyFragment.setCategoryList(categories)

supportFragmentManager.beginTransaction().add(R.id.fragment_container, nearbyFragment, MapplsNearbyFragment::class.java.simpleName)  
        .commit()
~~~

### Using MapplsNearbyActivity
#### Java
~~~java
Intent intent = new MapplsNearbyWidget.IntentBuilder().nearbyUIOptions(nearbyUIOptions).nearbyOptions(nearbyOptions).setCategoryList(categoryList).build(this);   
 startActivityForResult(intent, 101); 
~~~
#### Kotlin
~~~kotlin
val intent = MapplsNearbyWidget.IntentBuilder().nearbyUIOptions(nearbyUIOptions).nearbyOptions(nearbyOptions).setCategoryList(categoryList).build(this)   
startActivityForResult(intent, 101)  
~~~

### [CategoryCode](#CategoryCode)

This class is used to set the information for the nearby categories to show in Nearby Widget.
It contains the following properties in constructor:
1. `category (String)`: Name of the category that display on a view
2. `icon(Integer)`: To show icon of category
3. `categoryCode (List<String>)`: List of category codes
4. `markerIcon (Integer)`: Marker icon to display on a map
5. `isSelected (Boolean)`: To set the category is selected or not

### [NearbyUIOption](#NearbyUIOption)
This class is used to set the UI configurations of the Nearby Widget
It contains the following properties:
1. `toolbarColor(Integer)`:  To set the toolbar color of nearby result screen
2. `backgroundColor(Integer)`: To set the background color of nearby category selection screen
3. `nearbyToolbarIcon(Integer)`: To set the toolbar icon on category selection screen
4. `toolbarTintColor(Integer)`: To set text and icon color of toolbar of nearby selection screen
5. `nearbyToolbarColor(Integer)`: To set the toolbar color of nearby category selection screen
6. `nearbyToolbarTintColor(Integer)`: To set the toolbar text color of nearby category selection screen
7. `locationDetailsBackgroundColor(Integer)`: To set background color of location detail view
8. `locationDetailsInfoLabelText(String)`: To set location detail information text
9. `locationDetailInfoTextColor(Integer)`: To set location detail information text color
10. `locationDetailFormattedAddressTextColor(Integer)`: To set selected location Address text color
11. `changeLocationButtonTextColor(Integer)`: To set change location button Text color
12. `useCurrentLocationButtonTextColor(Integer)`: To set use current location Button text color
13. `selectedCategoryBackgroundColor(Integer)`: To set selected category item background color
14. `selectedCategoryTextColor(Integer)`: To set selected category item text color
15. `selectedCategoryTintColor(Integer)`: To set selected category item icon color
16. `categoryBackgroundColor(Integer)`: To set category item background color
17. `categoryTextColor(Integer)`: To set Category item text color
18. `categoryTintColor(Integer)`: To set Category item icon color
19. `submitButtonColor(Integer)`: To set background color of submit button
20. `submitButtonTextColor(Integer)`: To set submit button text color
21. `submitButtonText(String)`: To set submit button text
22. `paginationBackgroundColor(Integer)`: To set background color of page selection view
23.  `tabTextColor(Integer)`: To set tab text color
24. `tabBackgroundColor(Integer)`: To set tab background color
25. `selectedTabTextColor(Integer)`: To set selected tab text color
26. `tabIndicatorColor(Integer)`: To set selected tab indicator color
27. `tabIconTint(Integer)`: To set tab icon color (can use selector color)
28. `placeNameTextColor(Integer)`: To set result item place name text color
29. `distanceTextColor(Integer)`: To set result item distance text color
30.  `placeAddressTextColor(Integer)`: To set result item address text color
31. `refLocationIcon(Integer)`: To set reference location marker icon
32. `refLocationCircleColor(Integer)`: To set circle color around reference location
33. `refLocationCircleAlpha(Float)`: To set circle alpha for reference location

### [NearbyOption](#NearbyOption)
This class is used to set the properties related to nearby search
It contains the following properties:
1. `radius(Integer)`: provides the range of distance to search over(default: 1000, min: 500, max: 10000)
2. `sortBy(String)`: provides configured sorting operations for the client on cloud.**Below are the available sorts:**
    -   **NearbyCriteria.DISTANCE_ASCENDING**
    -  **NearbyCriteria.DISTANCE_DESCENDING** will sort the data on distance basis.
    -   **NearbyCriteria.NAME_ASCENDING**
    -   **NearbyCriteria.NAME_DESCENDING** will sort the data on alphabetically basis.
3. `searchBy(String)`: provides configurable search operations for the client on cloud. **Below are the available sorts:​**
    - **NearbyCriteria.DISTANCE**
    - **NearbyCriteria.IMPORTANCE** - will search data in order of prominence of the place.
4. `bounds(String)`: Allows the developer to send in map bounds to provide a nearby search within the bounds.   
   {e.g. (bounds("28.56812,77.240519;28.532790,77.290854"))
5. `pod(String)`: it takes in the place type code which helps in restricting the results to certain chosen type. Access to this parameter is controlled from the backend. This parameter if provided will override any values provided in `keywords` request param.  
   **Below mentioned are the codes for the pod**
    - NearbyCriteria.POD_SUB_LOCALITY
    - NearbyCriteria.POD_LOCALITY
    - NearbyCriteria.POD_CITY
    - NearbyCriteria.POD_STATE
6. `filter(String)`: This parameter helps you get a specific type of EV charging Station
    - `filter` = model:(string);brandId:(string);plugType:(string)
7. `richData(Boolean)`:  Rich Data related to POI
8. `userName(String)`: Use to set the user name


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