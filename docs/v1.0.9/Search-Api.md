
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Search API's
The following search services are available as part of the SDK bundled by default –

## [Auto Suggest]()
The Autosuggest API helps users to complete queries faster by adding intelligent search capabilities to your web or mobile app. This API returns a list of results as well as suggested queries as the user types in the search field.
#### Java
~~~java  
MapplsAutoSuggest autoSuggest = MapplsAutoSuggest.builder()    
        .query(searchText)    
		.build();
    //To call in Foreground Thread
 MapplsAutosuggestManager.newInstance(autoSuggest).call(new OnResponseCallback<AutoSuggestAtlasResponse>() {    
    @Override    
  public void onSuccess(AutoSuggestAtlasResponse response) {    
        //handle response     
  }    
    
    @Override    
  public void onError(int code, String message) {    
       //handle Error  
 } });  
          //OR
          //To call in Background Thread
ApiResponse<AutoSuggestAtlasResponse> responseApiResponse = MapplsAutosuggestManager.newInstance(autosuggest).executeCall();  
~~~  
#### Kotlin

~~~kotlin  
val autoSuggest =MapplsAutoSuggest.builder()    
        .query(searchText)    
		.build() 
    //To call in Foreground Thread
MapplsAutosuggestManager.newInstance(autoSuggest).call(object : OnResponseCallback<AutoSuggestAtlasResponse> {    
     override fun onSuccess(response: AutoSuggestAtlasResponse) {    
        //handle response    
    }    
    override fun onError(code: Int, message: String) {    
       //handle Error  
 }        
})  
      //OR
      //To call in Background Thread
val responseApiResponse = MapplsAutosuggestManager.newInstance(autoSuggest).executeCall(); 
~~~  

### Request Parameters
#### Mandatory Parameter
1. ``query(String)`` : e.g. Shoes, Coffee, Versace, Gucci, H&M, Adidas, Starbucks, B130 {POI, House Number, keyword, tag}.
#### Optional Parameters
1. ``setLocation(latitude, longitude)``: e.g. setLocation(28.454,77.435).
2. `zoom (double)` : takes the zoom level of the current scope of the map (min: 4, max: 18).
3. ``tokenizeAddress(boolean)``: provides the different address attributes in a structured object.
4. `pod` (string) = it takes in the place type code which helps in restricting the results to certain chosen type.**Below mentioned are the codes for the pod**
	- AutoSuggestCriteria.POD_SUB_LOCALITY
	- AutoSuggestCriteria.POD_LOCALITY
	- AutoSuggestCriteria.POD_CITY
	- AutoSuggestCriteria.POD_VILLAGE
	- AutoSuggestCriteria.POD_SUB_DISTRICT
	- AutoSuggestCriteria.POD_DISTRICT
	- AutoSuggestCriteria.POD_STATE
	- AutoSuggestCriteria.POD_SUB_SUB_LOCALITY
5. `filter`(string)  = This parameter helps you restrict the result either by mentioning a bounded area or to certain mapplspin (6 digit code to any poi, locality, city, etc.) below mentioned are the various types:
	- `filter` = bounds: lat1, lng1; lat2, lng2 (latitude, longitude) {e.g. filter("bounds: 28.598882, 77.212407; 28.467375, 77.353513")
	- `filter` = cop: {mapplspin} (string) {e.g. filter("cop:YMCZ0J")
6. `hyperlocal`(Boolean): This parameter lets the search give results that are hyper-localized to the reference location passed in the location parameter. This means that nearby results are given a higher ranking than results far from the reference location. Highly prominent results will still appear in the search results, however they will be lower in the list of results. This parameter will work ONLY in conjunction with the location parameter.

### Response Code (as HTTP response code)
#### Success:
1. 200: To denote a successful API call.
2. 204: To denote the API was a success but no results were found.
#### Client side issues:
1. 400: Bad Request, User made an error while creating a valid request.
2. 401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3. 403: Forbidden, Developer’s key has hit its daily/hourly limit.
#### Server-Side Issues:
1. 500: Internal Server Error, the request caused an error in our systems.
2. 503: Service Unavailable, during our maintenance break or server downtimes.

###  Response Messages (as HTTP response message)
1. 200: Success.
2. 204: No matches were found for the provided query.
3. 400: Something’s just not right with the request.
4. 401: Access Denied.
5. 403: Services for this key has been suspended due to daily/hourly transactions limit.
6. 500: Something went wrong.
7. 503: Maintenance Break.
### Response parameters
1. `suggestedLocations`(ArrayList`<ELocation>`): A List of the suggested location
2. `userAddedLocations`(ArrayList`<ELocation>`): List of user added locations
3. `suggestedSearches`(ArrayList`<SuggestedSearchAtlas>`): List of suggestion related to your search.

#### ELocation Response result parameters:
1. `mapplsPin`(String): Mappls Pin of the location 6-char alphanumeric.
2. `placeAddress`(String): Address of the location.
3. `latitude`(Double): Latitude of the location.
4. `longitude`(Double): longitude of the location.
5. `type`(String): type of location POI or Country or City
6. `placeName`(String): Name of the location.
7. `user`(String): Name of the user who add the place
8. `index`(Integer): the order where this result should be placed
9. `distance`(Double): aerial distance in meters from reference location
10. `addressTokens`(AddressToken)
##### AddressToken parameters:
1. `houseNumber`(String): house number of the location.
2. `houseName`(String): house name of the location.
3. `poi`(String): name of the POI (if applicable)
4. `street`(String): name of the street. (if applicable)
5. `subSubLocality`(String): the sub-sub-locality to which the location belongs. (if applicable)
6. `subLocality`(String): the sub-locality to which the location belongs. (if applicable)
7. `locality`(String): the locality to which the location belongs. (if applicable)
8. `village`(String): the village to which the location belongs. (if applicable)
9. `subDistrict`(String): the sub-district to which the location belongs. (if applicable)
10. `district`(String): the district to which the location belongs. (if applicable)
11. `city`(String): the city to which the location belongs. (if applicable)
12. `state`(String): the state to which the location belongs. (if applicable)
13. `pincode`(String): the PIN code to which the location belongs. (if applicable)
####  SuggestedSearchAtlas Response Result Parameters
1. `keyword` (string): what the user typed in.
2. `identifier` (string): what did the API use for it to qualify it as a suggested search request
3. `location` (string): the name of the location to which the nearby will run in context to.
4. `hyperlink` (string): the ready-made link for the nearby API pre-initialized with all default parameters and location with code to search for.


## [Geocoding]()
Our Geocoding API converts real addresses into these geographic coordinates (latitude/longitude) to be placed on a map, be it for any street, area, postal code, POI or a house number etc.
#### Java
~~~java
MapplsGeoCoding geoCoding = MapplsGeoCoding.builder()  
         .setAddress("Delhi")  
         .build();  
         //To call in Foreground Thread
MapplsGeoCodingManager.newInstance(geoCoding).call(new OnResponseCallback<GeoCodeResponse>() {  
    @Override  
  public void onSuccess(GeoCodeResponse response) {  
        //handle response   
  }  
  
    @Override  
  public void onError(int code, String message) {  
         //handle Error
  }  
});
            //OR
            //To call in Background Thread
ApiResponse<GeoCodeResponse> response = MapplsGeoCodingManager.newInstance(mapplsGeoCoding).executeCall();           
~~~

#### Kotlin
~~~kotlin
val geoCoding = MapplsGeoCoding.builder()  
        .setAddress("Delhi")  
        .build()
         //To call in Foreground Thread 
MapplsGeoCodingManager.newInstance(geoCoding).call(object : OnResponseCallback<GeoCodeResponse> {  
            override fun onSuccess(response: GeoCodeResponse) {  
                //handle response  
            }  
            override fun onError(code: Int, message: String) {  
                //handle Error 
            }  
        })
            //OR
            //To call in Background Thread
val response = MapplsGeoCodingManager.newInstance(mapplsGeoCoding).executeCall()
~~~
### Request Parameters
#### Mandatory Parameter
1. ``setAddress(String)`` : address to be geocoded e.g. 237 Okhla industrial estate phase 3 new delhi, delhi 110020.
#### Optional Parameters
1.  ``itemCount(Integer)``: parameter can be set to get maximum number of result items from the API (default: 1).
2. ``podFilter(String)``This parameter can be used to set admin level restriction. The result will be either the given admin level or equivalent admin or higher in the hierarchy.  **Below mentioned are the codes for the pod:**
	- GeoCodingCriteria.POD_HOUSE_NUMBER
	- GeoCodingCriteria.POD_HOUSE_NAME
	- GeoCodingCriteria.POD_POINT_OF_INTEREST
	- GeoCodingCriteria.POD_STREET
	- GeoCodingCriteria.POD_SUB_SUB_LOCALITY
	- GeoCodingCriteria.POD_VILLAGE
	- GeoCodingCriteria.POD_SUB_LOCALITY
	- GeoCodingCriteria.POD_SUB_DISTRICT
	- GeoCodingCriteria.POD_LOCALITY
	- GeoCodingCriteria.POD_CITY
	- GeoCodingCriteria.POD_DISTRICT
	- GeoCodingCriteria.POD_PINCODE
	- GeoCodingCriteria.POD_STATE
3. ``bias(String)``: This parameter can be used to set Urban or Rural bias. A positive value sets the Urban bias and a negative value sets Rural bias. **Below mentioned are the codes for the bias:**
	- GeoCodingCriteria.BIAS_DEFAULT (No bias)
	- GeoCodingCriteria.BIAS_RURAL
	- GeoCodingCriteria.BIAS_URBAN
4. ``bound(String)``: This parameter can be used to set admin boundary, which means geocoding will be done within the given admin. The allowed admin bounds are Sub-District, District, City, State and Pincode. The parameter accepts the admin eLoc as value.

**Note:** Please note that podFilter & bound parameters are mutually exclusive. They cannot be used together in an API call.
### Response Code {as HTTP response code}
#### Success:
1.  200: To denote a successful API call.
2.  204: To denote the API was a success but no results were found.
#### Client side issues:
1.  400: Bad Request, User made an error while creating a valid request.
2.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3.  403: Forbidden, Developer’s key has hit its daily/hourly limit.
#### Server-Side Issues:
1.  500: Internal Server Error, the request caused an error in our systems.
2.  503: Service Unavailable, during our maintenance break or server downtimes.
###  Response Messages (as HTTP response message)
1.  200: Success.
2.  204: No matches were found for the provided query.
3.  400: Something’s just not right with the request.
4.  401: Access Denied.
5.  403: Services for this key has been suspended due to daily/hourly transactions limit.
6.  500: Something went wrong.
7.  503: Maintenance Break.
### Response Parameters
1. ``GeoCodeResponse``(List``<GeoCode>``): All the details regarding place.
#### GeoCode response Result parameters
1. ``houseNumber`` (String): the houseNumber of the address/location
2. ``houseName`` (String):  houseName of the address/location
3.  `poi`(string): the point of interest or famous landmark nearby the location
4.  `street`(string): the street or road of the location
5.  `subsubLocality`(string): the subSubLocality of the location
6.  `subLocality`(string): the subLocality of the location
7.  `locality`(string): the locality of the location
8.  `village`(string): the village of the location
9.  `subDistrict`(string): the subDistrict of the location
10.  `district`(string): the district of the location
11.  `city`(string): the city of the location
12.  ``state``(string): the state of the location
13.  `pincode`(string): the pincode of the location
14.  `formatted`Address(string): the general protocol following address
15.  `mapplsPin`(string): mappls pin of the particular location
16.  `latitude`(double): the latitude for the location.
17.  `longitude`(double): the longitude for the location.
18.  `geocodeLevel`(string): the best matched address component.

## [Reverse Geocoding]()
Reverse Geocoding is a process to give the closest matching address to a provided geographical coordinates (latitude/longitude). Mappls reverse geocoding API provides real addresses along with nearest popular landmark for any such geo-positions on the map.

#### Java
~~~java
MapplsReverseGeoCode reverseGeoCode = MapplsReverseGeoCode.builder()  
        .setLocation(28,77)  
        .build();  
        //To call in foreground thread
MapplsReverseGeoCodeManager.newInstance(reverseGeoCode).call(new OnResponseCallback<PlaceResponse>() {  
    @Override  
  public void onSuccess(PlaceResponse response) {  
        //Handle Response
  }  
  
    @Override  
  public void onError(int code, String message) {  
        //Handle Error
  }  
});
                  //OR
                  //To call in Background Thread
ApiResponse<PlaceResponse> response = MapplsReverseGeoCodeManager.newInstance(reverseGeoCode).executeCall();
~~~

#### Kotlin
~~~kotlin
val reverseGeoCode = MapplsReverseGeoCode.builder()  
        .setLocation(28.0, 77.0)  
        .build()
        //To call in foreground thread
MapplsReverseGeoCodeManager.newInstance(reverseGeoCode).call(object : OnResponseCallback<PlaceResponse> {  
  
            override fun onSuccess(response: PlaceResponse) {  
                //handle response  
            }  
            override fun onError(code: Int, message: String) {  
                //handle Error 
            }  
        })
                  //OR
                  //To call in Background Thread
val response = MapplsReverseGeoCodeManager.newInstance(reverseGeoCode).executeCall()
~~~
### Request Parameters
#### Mandatory Parameter
1. ``setLocation(latitude, longitude)``: the latitude and longitude of the location for which the address is required.
#### Optional Parameter
1. ``lang(String)``:  This parameter accepts the "hi" (ISO 639-1 Language Code for Hindi) as a value.

### Response Code (as HTTP response code)
#### Success:
1.  200: To denote a successful API call.
2.  204: To denote the API was a success but no results were found.
#### Client side issues:
1.  400: Bad Request, User made an error while creating a valid request.
2.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3.  403: Forbidden, Developer’s key has hit its daily/hourly limit.
#### Server-Side Issues:
1.  500: Internal Server Error, the request caused an error in our systems.
2.  503: Service Unavailable, during our maintenance break or server downtimes.

###  Response Messages (as HTTP response message)
1.  200: Success.
2.  204: No matches were found for the provided query.
3.  400: Something’s just not right with the request.
4.  401: Access Denied.
5.  403: Services for this key has been suspended due to daily/hourly transactions limit.
6.  500: Something went wrong.
7.  503: Maintenance Break.


### Response parameters
1.  `responseCode`(Long): Response codes of the api.
2.  `version`(String): Version of the Api.
3.  `places`(List`<Places>`): All the details regarding place.
#### Place Response result parameters:
1.  `houseNumber`(String): the houseNumber of the address/location
2.  `houseName`(String): houseName of the address/location
3.  `poi`(String): the point of interest or famous landmark nearby the location
4. `poiDist`(String): distance from nearest POI in metres.
5.  `street`(String): the street or road of the location
6. `streetDist`(String): distance from nearest Street in metres.
7.  `subsubLocality`(String): the subSubLocality of the location
8.  `subLocality`(String): the subLocality of the location
9.  `locality`(String): the locality of the location
10.  `village`(String): the village of the location
11.  `subDistrict`(String): the subDistrict of the location
12.  `district`(String): the district of the location
13.  `city`(String): the city of the location
14.  `state`(String): the state of the location
15.  `pincode`(String): the pincode of the location
16.  `formatted`Address(String): the general protocol following address
17.  `lat`(Double): the latitude for the location.
18.  `lng`(Double): the longitude for the location.
19.  `area`(String): the area of the location.


## [Nearby Places]()
Nearby Places API, enables you to add discovery and search of nearby POIs by searching for a generic keyword used to describe a category of places or via the unique code assigned to that category.
#### Java
~~~java
MapplsNearby nearby = MapplsNearby.builder()
        .setLocation(28,77)  
        .keyword("Parking")  
        .build();  
        //To call in Foreground Thread
MapplsNearbyManager.newInstance(nearby).call(new OnResponseCallback<NearbyAtlasResponse>() {  
    @Override  
  public void onSuccess(NearbyAtlasResponse response) {  
        //Handle Response
  }  
  
    @Override  
  public void onError(int code, String message) {  
       //Handle Error
  }  
});
            //OR
            //To call in Background Thread
ApiResponse<NearbyAtlasResponse> response = MapplsNearbyManager.newInstance(nearby).executeCall();;           
~~~
#### Kotlin
~~~kotlin
val nearby = MapplsNearby.builder()  
        .keyword("Parking")  
        .setLocation(28.0, 77.0)  
        .build() 
        //To call in Foreground Thread 
MapplsNearbyManager.newInstance(nearby).call(object : OnResponseCallback<NearbyAtlasResponse> {  
  
            override fun onSuccess(response: NearbyAtlasResponse) {  
                //handle response  
            }  
            override fun onError(code: Int, message: String) {  
                //handle Error 
            }  
        })
            //OR
            //To call in Background Thread
val response = MapplsNearbyManager.newInstance(nearby).executeCall()
~~~
### Request Parameters
#### Mandatory Parameter
1. ``keyword(String)``: performs search on the basis of provided keyword For eg: coffee, EV Charging Station etc
2. ``setLocation(latitude, longitude)``:Provide the location around which the search will be performed
	- setLocation also support mappls pin e.g., ``setLocation("MMI000")``

#### Optional Parameters
1. ``sort(String)``: provides configured sorting operations for the client on cloud.**Below are the available sorts:**
	-   **NearbyCriteria.DISTANCE_ASCENDING**
	-  **NearbyCriteria.DISTANCE_DESCENDING**  will sort the data on distance basis.

	-   **NearbyCriteria.NAME_ASCENDING**
	-   **NearbyCriteria.NAME_DESCENDING**  will sort the data on alphabetically basis.

2. ``page (integer)``: provides number of the page to provide results from.
3. ``radius(integer)``: provides the range of distance to search over(default: 1000, min: 500, max: 10000)
4.  ``bounds(String)``: Allows the developer to send in map bounds to provide a nearby search within the bounds.
	{e.g. (bounds("28.56812,77.240519;28.532790,77.290854"))
5. ``userName(String)``: Use to set the user name
6. ``richData(Boolean)``: rich data related to poi
7. `pod (string)` : it takes in the place type code which helps in restricting the results to certain chosen type. Access to this parameter is controlled from the backend. This parameter if provided will override any values provided in `keywords` request param.
   **Below mentioned are the codes for the pod**
	-   NearbyCriteria.POD_SUB_LOCALITY
	-   NearbyCriteria.POD_LOCALITY
	-   NearbyCriteria.POD_CITY
	-   NearbyCriteria.POD_VILLAGE
8.  `filter(String)` : This parameter helps you get a specific type of EV charging Station
	-   `filter` = model:(string);brandId:(string);plugType:(string)



### Response Code (as HTTP response code)
#### Success:
1.  200: To denote a successful API call.
2.  204: To denote the API was a success but no results were found.
#### Client side issues:
1.  400: Bad Request, User made an error while creating a valid request.
2.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3.  403: Forbidden, Developer’s key has hit its daily/hourly limit.
#### Server-Side Issues:
1.  500: Internal Server Error, the request caused an error in our systems.
2.  503: Service Unavailable, during our maintenance break or server downtimes.

###  Response Messages (as HTTP response message)
1.  200: Success.
2.  204: No matches were found for the provided query.
3.  400: Something’s just not right with the request.
4.  401: Access Denied.
5.  403: Services for this key has been suspended due to daily/hourly transactions limit.
6.  500: Something went wrong.
7.  503: Maintenance Break.
###

### 	Response Parameter
1. `suggestedLocations`(ArrayList`<NearbyAtlasResult>`): List of nearby places
#### NearbyAtlasResult Response Result Parameters
1. ``distance`` (integer): provides the distance from the provided location bias in meters.
2.  ``eLoc`` (string): Place Id of the location 6-char alphanumeric.
3.  ``email`` (string): Email for contact.
4.  ``entryLatitude`` (double): latitude of the entrance of the location.
5.  ``entryLongitude`` (double): longitude of the entrance of the location.
6.  ``keywords`` ( [ string ] ): provides an array of matched keywords or codes.
7.  ``landlineNo`` (string): Email for contact.
8.  ``latitude`` (double): Latitude of the location.
9.  ``longitude`` (double): longitude of the location.
10.  ``mobileNo`` : Phone number for contact.
11.  ``orderIndex`` (integer): the order where this result should be placed
12.  ``placeAddress`` (string): Address of the location.
13.  ``placeName`` (string): Name of the location.
14. ``city``(string): City of the location
15. ``state``(string): State of the location
16. ``pincode``(string): Pincode of the location
17. ``richInfo``(Map):  To show advance information of location
18.  ``type`` (string): Type of location POI or Country or City.
19.  ``hourOfOperation``(string): The hours of operation of the POI in a day.
20.  ``addressTokens``(AddressToken)
21. ``pageInfo``(PageInfo)
##### AddressToken parameters:
1. `houseNumber`(String): house number of the location.
2. `houseName`(String): house name of the location.
3. `poi`(String): name of the POI (if applicable)
4. `street`(String): name of the street. (if applicable)
5. `subSubLocality`(String): the sub-sub-locality to which the location belongs. (if applicable)
6. `subLocality`(String): the sub-locality to which the location belongs. (if applicable)
7. `locality`(String): the locality to which the location belongs. (if applicable)
8. `village`(String): the village to which the location belongs. (if applicable)
9. `subDistrict`(String): the sub-district to which the location belongs. (if applicable)
10. `district`(String): the district to which the location belongs. (if applicable)
11. `city`(String): the city to which the location belongs. (if applicable)
12. `state`(String): the state to which the location belongs. (if applicable)
13. `pincode`(String): the PIN code to which the location belongs. (if applicable)
##### PageInfo parametrs:
1. ``pageCount``(integer): The number of pages with results.
2. ``totalHits``(integer): Total number of places in the results.
3.   `totalPages`  (integer): Total number of pages as per page size and no of results.
4.   `pageSize`  (integer): The number of results per page.


##  [Place Details]()
Mappls Place Details is a simple, standardized and precise pan-India digital address system. Every location has been assigned a unique digital address or an eLoc. The eLoc API can be used to extract the details of a place with the help of its eLoc i.e. a 6 digit code or a place_id.
#### Java
~~~java
MapplsPlaceDetail placeDetail = MapplsPlaceDetail.builder()  
        .mapplsPin("mmi000")  
        .build();  
        //To call in Foreground Thread
MapplsPlaceDetailManager.newInstance(placeDetail).call(new OnResponseCallback<PlaceDetailResponse>() {  
    @Override  
  public void onSuccess(PlaceDetailResponse response) {  
        //Handle Response  
  }  
  
    @Override  
  public void onError(int code, String message) {  
       //Handle Error
  }  
});
              //OR
              //To call in Background Thread
ApiResponse<PlaceDetailResponse> response = MapplsPlaceDetailManager.newInstance(placeDetail).executeCall(); 
~~~
#### Kotlin
~~~kotlin
val placeDetail = MapplsPlaceDetail.builder()  
        .mapplsPin("mmi000")  
        .build()  
        //To call in Foreground Thread
MapplsPlaceDetailManager.newInstance(placeDetail).call(object : OnResponseCallback<PlaceDetailResponse> {  
  
            override fun onSuccess(response: PlaceDetailResponse) {  
                //handle response  
            }  
            override fun onError(code: Int, message: String) {  
                //handle Error 
            }  
        })
              //OR
              //To call in Background Thread
val response = MapplsPlaceDetailManager.newInstance(placeDetail).executeCall()
~~~
### Request Parameter
#### Mandatory Parameter
1. `mapplsPin(String)`: the id or eLoc of the place whose details are required. The 6-digit alphanumeric  
   code for any location. (e.g. mmi000).

### Response Code (as HTTP response code)
#### Success:
1.  200: To denote a successful API call.
2.  204: To denote the API was a success but no results were found.
#### Client side issues:
1.  400: Bad Request, User made an error while creating a valid request.
2.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3.  403: Forbidden, Developer’s key has hit its daily/hourly limit.
#### Server-Side Issues:
1.  500: Internal Server Error, the request caused an error in our systems.
2.  503: Service Unavailable, during our maintenance break or server downtimes.

###  Response Messages (as HTTP response message)
1.  200: Success.
2.  204: No matches were found for the provided query.
3.  400: Something’s just not right with the request.
4.  401: Access Denied.
5.  403: Services for this key has been suspended due to daily/hourly transactions limit.
6.  500: Something went wrong.
7.  503: Maintenance Break.


### Place result parameter

1. `mapplsPin` (string) : 6 characters alphanumeric unique identifier
2. `latitude` (double): The latitude of the location.
3. `longitude` (double): The longitude of the location.

**Note: Not all response parameters are available by default. These parameters are restricted and available as per the discussed use case. For details, please contact Mappls API support.**


## [POI Along the Route]()
With POI Along the Route API user will be able to get the details of POIs of a particular category along his set route. The main focus of this API is to provide convenience to the user and help him in locating the place of his interest on his set route.

#### Java
~~~java
MapplsPOIAlongRoute poiAlongRoute = MapplsPOIAlongRoute.builder()  
        .category(catCode)
        .path(path)  
        .build();  
        //To call in Foreground Thread
MapplsPOIAlongRouteManager.newInstance(poiAlongRoute).call(new OnResponseCallback<POIAlongRouteResponse>() {  
    @Override  
  public void onSuccess(POIAlongRouteResponse response) {  
        //Handle Response  
  }  
  
    @Override  
  public void onError(int code, String message) {  
        //Handle Error
  }  
});
            //OR
            //To call in Background Thread
ApiResponse<POIAlongRouteResponse> response = MapplsPOIAlongRouteManager.newInstance(poiAlongRoute).executeCall(); 
~~~
#### Kotlin
~~~kotlin
val poiAlongRoute = MapplsPOIAlongRoute.builder()  
        .category(catCode)  
        .path(path)  
        .build()
        //To call in Foreground Thread 
MapplsPOIAlongRouteManager.newInstance(poiAlongRoute).call(object : OnResponseCallback<POIAlongRouteResponse> {  
  
            override fun onSuccess(response: POIAlongRouteResponse) {  
                //handle response  
            }
            override fun onError(code: Int, message: String) {  
                //handle Error 
            }   
        })
            //OR
            //To call in Background Thread
val response = MapplsPOIAlongRouteManager.newInstance(poiAlongRoute).executeCall() 
~~~
### Request Parameter
#### Mandatory Parameters
1. ``category(String)``: The POI category code to be searched. Only one category input supported.
2. ``path(String)``: This parameter takes the encoded route along which POIs to be searched.

#### Optional Parameters
1. ``buffer(Integer)``:  Buffer of the road. Minimum value is `25`, maximum is `1000` and default is `25`.
2. ``geometries(String)``: Type of geometry encoding.**Below are the available geometries:**
	- POICriteria.GEOMETRY_POLYLINE5
	- POICriteria.GEOMETRY_POLYLINE6 **(Default)**
	- POICriteria.GEOMETRY_BASE64
3. ``page(Integer)``: Used for pagination. By default, a request returns maximum `10` results and to get the next `10` or so on pass the page value accordingly. Default is 1.
4. ``sort(Boolean)``: Gets the sorted POIs along route.

### Response Codes( as HTTP response code)

#### Success

1.  200: To denote a successful API call.
2.  204: To denote the API was a success but no results were found.

#### Client-Side Issues

3.  400: Bad Request, User made an error while creating a valid request.
4.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
5.  403: Forbidden, Developer’s key has hit its daily/hourly limit. Server-Side Issues:
6.  500: Internal Server Error, the request caused an error in our systems.
7.  503: Service Unavailable, during our maintenance break or server down-times.

### Response Message (as HTTP response message)

1.  200: Success.
2.  204: No matches we’re found for the provided query.
3.  400: Something’s just not right with the request.
4.  401: Access Denied.
5.  403: Services for this key has been suspended due to daily/hourly transactions limit.
6.  500: Something went wrong.
7.  503: Maintenance Break.

### Response Parameters
1. ``suggestedPOIs``(List <``SuggestedPOI``>):  List of Suggested POI location

#### Suggested POI Response parameters
1. ``distance``(Integer): distance of the POI.
2. ``mapplsPin``(String): mapplsPin of the POI.
3. ``poi``(String): Name of the POI.
4. ``subSubLocality``(String): Sub sub locality of the POI.
5. ``subLocality``(String): Sub locality of the POI.
6. ``locality``(String): Locality of the POI.
7. ``city``(String): City of the POI.
8. ``subDistrict``(String): Sub district of the POI.
9.  ``district``(String): District of the POI.
10. ``state``(String): State of the POI.
11. ``popularName``(String): Popular name of the POI.
12. ``address``(String): Address of the POI.
13. ``telNo``(String): Telephone number of the POI.
14. ``email``(String): Email of the POI.
15. ``website``(String): Website of the POI.
16. ``longitude``(Double): Longitude of the POI.
17. ``latitude``(Double): Latitude of the POI.
18. ``eLng``(Double): Entry longitude of the POI.
19. ``eLat``(Double): Entry latitude of the POI.
20. ``brandCode``(String): Brand id of the POI.


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




<div align="center">@ Copyright 2023 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
