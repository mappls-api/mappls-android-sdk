
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)


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
23. `tabTextColor(Integer)`: To set tab text color
24. `tabBackgroundColor(Integer)`: To set tab background color
25. `selectedTabTextColor(Integer)`: To set selected tab text color
26. `tabIndicatorColor(Integer)`: To set selected tab indicator color
27. `tabIconTint(Integer)`: To set tab icon color (can use selector color)
28. `placeNameTextColor(Integer)`: To set result item place name text color
29. `distanceTextColor(Integer)`: To set result item distance text color
30. `placeAddressTextColor(Integer)`: To set result item address text color
31. `refLocationIcon(Integer)`: To set reference location marker icon
32. `refLocationCircleColor(Integer)`: To set circle color around reference location
33. `refLocationCircleAlpha(Float)`: To set circle alpha for reference location
34. `showDefaultMap(Boolean)`: To add Default map or not

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
