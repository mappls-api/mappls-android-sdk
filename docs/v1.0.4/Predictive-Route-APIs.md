

[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)


# Predictive Routing APIs

## [Predictive Routing API]()

Calculate optimal routes between two or more specified locations. Support live and predictive traffic to calculate ETAs based on arrival or departure time.
#### Java
~~~java  
MapplsPredictiveDirections mapplsPredictiveDirections = MapplsPredictiveDirections.builder() 
	 .origin(startPoint)
	 .destination(destinationPoint)
	 .build();  
MapplsPredictiveDirectionManager.newInstance(mapplsPredictiveDirections).call(new OnResponseCallback<PredictiveDirectionsResponse>() {
	@Override  
	public void onSuccess(PredictiveDirectionsResponse response) {  
		 //Handle Response      
	}  
  
	@Override  
	public void onError(int code, String message) {  
	 //Handle Error      
	}  
 });
 ~~~  

#### Kotlin
~~~kotlin  
val mapplsPredictiveDirections = MapplsPredictiveDirections.builder()  
      .origin(startPoint)
      .destination(destinationPoint)
      .build()  
MapplsPredictiveDirectionManager.newInstance(mapplsPredictiveDirections).call(object : OnResponseCallback<PredictiveDirectionsResponse> {  
	 override fun onSuccess(response: PredictiveDirectionsResponse) {
	    //handle response 
	 }
	 override fun onError(code: Int, message: String) {
	  //handle Error 
	 }
})  
~~~  
### Request Parameters
#### Mandatory Parameter
1. `origin(Point)`: pass the origin point
	- origin can also takes *mappls pin* in parameter eg., `origin("17ZUL7")`
2. `destination(Point)`: pass the destination point
	- destination can also takes *mappls pin* in parameter eg., `destination("MMI000")`
#### Optional Parameter
1. `profile(String)`: Profile for routing engine. **Below are the available profile:**
	- `PredictiveDirectionsCriteria.PROFILE_DRIVING` **(Default)**:Meant for car routing
	- `PredictiveDirectionsCriteria.PROFILE_BIKING`:Meant for two-wheeler routing.
	- `PredictiveDirectionsCriteria.PROFILE_WALKING`:  Meant for pedestrian and hiking routing. This profile shows the shortest path by using sidewalks and trails.
	- `PredictiveDirectionsCriteria.PROFILE_TRUCKING`: Meant for Truck routing.
2. `speedType(MapplsDirectionSpeedType)`: To specify the type of speed. **Below are the available speed types:**
- `new MapplsDirectionSpeedTypeOptimal()`**(Default)**
- `new MapplsDirectionSpeedTypeTraffic()`
- `new MapplsDirectionSpeedTypePredictive(MapplsDirectionDateTime)`  
  _If value is predictive then date and time are required at the location and if value is optimal then date and time are not required._

- `new MapplsDirectionDateTimeCurrent()`: Current departure time.
	- `new MapplsDirectionDateTimeSpecified(PredictiveDirectionsCriteria.SPECIFIED_DEPARTURE, timeInMillis)`: Specified departure time.
	- `new MapplsDirectionDateTimeSpecified(PredictiveDirectionsCriteria.SPECIFIED_ARRIVAL, timeInMillis)`: Specified arrival time.
3. `heading(Integer)`: Preferred direction of travel for the start from the location. This can be useful for mobile routing where a vehicle is traveling in a specific direction along a road, and the route should start in that direction. The heading is indicated in degrees from north in a clockwise direction, where north is 0°, east is 90°, south is 180°, and west is 270°._`(ex.heading(100))`_
4. `headingTolerance(Integer)`: How close in degrees a given street's angle must be in order for it to be considered as in the same direction of the heading parameter. The default value is 60 degrees. _`(ex.heading(45).headingTolerance(45))`_
5. `preferredSide(String)`: If the location is not offset from the road centerline or is closest to an intersection this option has no effect. Otherwise the determined side of street is used to determine whether or not the location should be visited from the same, opposite or either side of the road with respect to the side of the road the given locale drives on. In Germany (driving on the right side of the road), passing a value of same will only allow you to leave from or arrive at a location such that the location will be on your right. In Australia (driving on the left side of the road), passing a value of same will force the location to be on your left. A value of opposite will enforce arriving/departing from a location on the opposite side of the road from that which you would be driving on while a value of either will make no attempt limit the side of street that is available for the route.* **Below are the available values:**
	- `PredictiveDirectionsCriteria.PREFERRED_SIDE_SAME`
	- `PredictiveDirectionsCriteria.PREFERRED_SIDE_EITHER`
	- `PredictiveDirectionsCriteria.PREFERRED_SIDE_OPPOSITE`
6. `searchCutoff(Integer)`: The cutoff at which we will assume the input is too far away from civilisation to be worth correlating to the nearest graph elements.
7. `avoidLocations(Point...)`: A set of locations to exclude or avoid within a route can be specified using a JSON array of avoid_locations. The avoid_locations have the same format as the locations list. At a minimum each avoid location must include latitude and longitude. The avoid_locations are mapped to the closest road or roads and these roads are excluded from the route path computation.Any route formed on the avoid_locations will return an alternative route and if no alternative is found then "no route" is returned.
8. `avoidPolygons(Point...)`: One or multiple exterior rings of polygons in the form of nested JSON arrays, e.g. [[[lon1, lat1], [lon2,lat2]],[[lon1,lat1],[lon2,lat2]]]. Roads intersecting these rings will be avoided during path finding. If you only need to avoid a few specific roads, it's much more efficient to use avoid_locations. API will close open rings (i.e. copy the first coordinate to the last position).
9. `routeName(String)`: Name your route request. If id is specified, the naming will be sent thru to the response._`(ex.id(MapmyIndia))`_
10. `useFerry(Double)`: 1.  This value indicates the willingness to take ferries. This is a range of values between 0 and 1. Values near 0 attempt to avoid ferries and values near 1 will favor ferries. The default value is 0.5. Note that sometimes ferries are required to complete a route so values of 0 are not guaranteed to avoid ferries entirely._`(ex.useFerry(0))`_
11. `useHighway(Double)`: This value indicates the willingness to take highways. This is a range of values between 0 and 1. Values near 0 attempt to avoid highways and values near 1 will favor highways. The default value is 1.0. Note that sometimes highways are required to complete a route so values of 0 are not guaranteed to avoid highways entirely._`(ex.use_highways(0))`_
12. `excludeTunnel(Boolean)`: whether to exclude roads marked as tunnels.
13. `excludeBridge(Boolean)`: whether to exclude roads marked as bridges.
14. `avoidTolls(Boolean)`: This value indicates the willingness to take roads with tolls. This is a range of values true and false. Values true attempt to avoid tolls and values false will not attempt to avoid them.
15. `alternatives(Integer)`: A number denoting how many alternate routes should be provided. There may be no alternates or less alternates than the user specifies. Alternates are not yet supported on multipoint routes (that is, routes with more than 2 locations).

The following options are available for truck profile:
~~~  
- height(Double)  - The height of the truck (in meters).  
- width(Double)  - The width of the truck (in meters).  
- length(Double)  - The length of the truck (in meters). Default is 21.64.  
- weight(Double)  - The weight of the truck (in metric tons). Default is 21.77.  
- axleLoad(Double)  - The axle load of the truck (in metric tons). Default is 9.07.  
- hazmat(Boolean)  - A value indicating if the truck is carrying hazardous materials. Default false.  
~~~  


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

### Response Messages (as HTTP response message)
1. 200: Success.
2. 204: No matches were found for the provided query.
3. 400: Something’s just not right with the request.
4. 401: Access Denied.
5. 403: Services for this key has been suspended due to daily/hourly transactions limit.
6. 500: Something went wrong.
7. 503: Maintenance Break.
### Response Parameters
1. `trip(PredictiveDirectionsTrip)` : he route results are returned as a trip. This is a JSON object that contains details about the trip, including locations, a summary with basic information about the entire trip, and a list of legs.
2. `id(String)`: Name your route request. If id is specified, the naming will be sent thru to the response.
3. `alternates(List<PredictiveDirectionsResponse>)`: Array of PredictiveDirectionsTrip objects representing all alternative routes

#### PredictiveDirectionsTrip Response result parameters
1. `summary(PredictiveDirectionSummary)`: Summary of the route
2. `statusMessage(String)`: description of the status (e.g. "Found route between points")
3. `legs(List<PredictiveDirectionsLeg>)`: array of route object.
4. `locations(List<PredictiveDirectionLocation>)`: array of location objects.
5. `language(String)`: "en-US"
6. `units(String)`: unit of distance(e.g. "kilometers")

#### PredictiveDirectionSummary Response result parameters
1. `minimumLongitude(Double)`: Minimum latitude of a bounding box containing the route.
2. `maximumLatitude(Double)`: Maximum latitude of a bounding box containing the route.
3. `maximumLongitude(Double)`: Maximum longitude of a bounding box containing the route.
4. `length(Double)`: Distance traveled for the entire trip. Units are kilometers
5. `time(Double)`: Estimated elapsed time to complete the trip.
6. `minimumLatitude(Double)`: Minimum latitude of a bounding box containing the route.

#### PredictiveDirectionsLeg Response result parameters
1. `summary(PredictiveDirectionSummary)`: Summary of the route
2. `shape(String)`: geometry of the route.
3. `maneuvers(List<PredictiveDirectionManeuver>)`: array of maneuver object

#### PredictiveDirectionManeuver result parameters
1. `verbalMultiCue(Boolean)`: True if the verbal_pre_transition_instruction has been appended with the verbal instruction of the next maneuver.
2. `beginShapeIndex(Integer)`: Index into the list of shape points for the start of the maneuver.
3. `travelMode(String)`: Travel mode "drive" & "pedestrian"
4. `instruction(String)`: Written maneuver instruction. Describes the maneuver, such as "Turn right onto Main Street".
5. `length(Double)`: Maneuver length in the units specified.
6. `endShapeIndex(integer)`: Index into the list of shape points for the end of the maneuver.
7. `verbalPostTransitionInstruction(String)`: -   Text suitable for use as a verbal message immediately after the maneuver transition. For example "Continue for 100 meters".
8. `time(Double)`: Estimated time along the maneuver in seconds.
9. `type(Integer)`: Type of maneuver. See below for a list.
	- kNone = 0;
	- kStart = 1;
	- kStartRight = 2;
	- kStartLeft = 3;
	- kDestination = 4;
	- kDestinationRight = 5;
	- kDestinationLeft = 6;
	- kBecomes = 7;
	- kContinue = 8;
	- kSlightRight = 9;
	- kRight = 10;
	- kSharpRight = 11;
	- kUturnRight = 12;
	- kUturnLeft = 13;
	- kSharpLeft = 14;
	- kLeft = 15;
	- kSlightLeft = 16;
	- kRampStraight = 17;
	- kRampRight = 18;
	- kRampLeft = 19;
	- kExitRight = 20;
	- kExitLeft = 21;
	- kStayStraight = 22;
	- kStayRight = 23;
	- kStayLeft = 24;
	- kMerge = 25;
	- kRoundaboutEnter = 26;
	- kRoundaboutExit = 27;
	- kFerryEnter = 28;
	- kFerryExit = 29;
	- kTransit = 30;
	- kTransitTransfer = 31;
	- kTransitRemainOn = 32;
	- kTransitConnectionStart = 33;
	- kTransitConnectionTransfer = 34;
	- kTransitConnectionDestination = 35;
	- kPostTransitConnectionDestination = 36;
	- kMergeRight = 37;
	- kMergeLeft = 38;
10. `verbalPreTransitionInstruction(String)`: Text suitable for use as a verbal message immediately prior to the maneuver transition. For example "Walk west".
11. `travelType(String)`: Travel type for driving "car", Travel type for biking "motorcycle", Travel type for trucking "tractor_trailer", Travel type for walking "foot"
12. `roundaboutExitCount(Integer)`: The spoke to exit roundabout after entering.
13. `sign(String)`: Contains the interchange guide information at a road junction associated with this maneuver. See below for details.
- exit_number_elements
- exit_branch_elements
- exit_toward_elements
- exit_name_elements
14. `verbalTransitionAlertInstruction(String)`: Text suitable for use as a verbal alert in a navigation application. The transition alert instruction will prepare the user for the forthcoming transition. For example: "Turn right".
15. `streetNames(List<String>`: List of street names that are consistent along the entire nonobvious maneuver.

#### PredictiveDirectionLocation Response result parameter
1. `originalIndex(Integer)`
2. `city(String)`
3. `longitude(Double)`
4. `type(String)`
5. `latitude(Double)`

## [Predictive Distance]()

This service computes time and distance between a set of locations and returns them to you in the resulting matrix table.

The distance can return a row matrix, a column matrix, or a general matrix of computed time and distance, depending on your input for the source and target parameters. The general case is a row ordered matrix with the time and distance from each source location to each target location. A row vector is considered a one_to_many distance-time matrix where there is one source location and multiple target locations, time and distance from the source location to all target locations is returned. A column matrix represents a many_to_one distance-time matrix where there are many sources and one target. Another special case is when the source location list is the same as the target location list. API also supports many_to_many distance-time matrix, this special case is often used as the input to optimized routing problems.

#### Java
~~~java
MapplsPredictiveDistance mapplsPredictiveDistance = MapplsPredictiveDistance.builder()    
        .addSource(source)    
        .addDestination(destination)    
        .build();
MapplsPredictiveDistanceManager.newInstance(mapplsPredictiveDistance).call(new OnResponseCallback<PredictiveDistanceResponse>() {    
    @Override    
    public void onSuccess(PredictiveDistanceResponse response) {    
        //Handle Response  
    }    
    @Override    
    public void onError(int code, String message) {    
        //Handle Error  
    }
}); 
 ~~~
#### Kotlin
~~~kotlin
val mapplsPredictiveDistance = v.builder()    
        .addSource(source)    
        .addDestination(destination)    
        .build()
MapplsPredictiveDistanceManager.newInstance(mapplsPredictiveDistance).call(object : OnResponseCallback<PredictiveDistanceResponse> {    
    
   override fun onSuccess(response: PredictiveDistanceResponse) {    
      //handle response      
   }    
   override fun onError(code: Int, message: String) {    
      //handle Error      
} }) 
~~~
### Request Parameter
#### Manadatory  Parameter

1.  `sources(List<Point>)`: A List full of Points which define sources.
2. `addSource(Point)`: To pass single source at a time
	- coordinate can also takes mapplsPin eg., `addSource("MMI000")`
3. `sourceList(List<String>)`: To pass the full list of mappls pins
4. `destinations(List<Point>)`: A List full of Points which define targets.
5. `addDestination(Point)`: To pass single target at a time
	- coordinate can also takes mapplsPin eg., `addDestination("MMI000")`
6. `destinationList(List<String>)`: To pass the full list of mappls pins
#### Optional Parameter
1. `profile(String)`: Profile for routing engine. **Below are the available profile:**
	- `PredictiveDistanceCriteria.PROFILE_DRIVING` **(Default)**:Meant for car routing  
	- `PredictiveDistanceCriteria.PROFILE_BIKING`:Meant for two-wheeler routing.  
	- `PredictiveDistanceCriteria.PROFILE_WALKING`:  Meant for pedestrian and hiking routing. This profile shows the shortest path by using sidewalks and trails.
	- `PredictiveDistanceCriteria.PROFILE_TRUCKING`: Meant for Truck routing.
2. `speedType(MapplsDirectionSpeedType)`: To specify the type of speed. **Below are the available speed types:**
	- `new MapplsDirectionSpeedTypeOptimal()`**(Default)**
	- `new MapplsDirectionSpeedTypeTraffic()`
	- `new MapplsDirectionSpeedTypePredictive(MapplsDirectionDateTime)`  
	  _If value is predictive then date and time are required at the location and if value is optimal then date and time are not required._

		- `new MapplsDirectionDateTimeCurrent()`: Current departure time.
		- `new MapplsDirectionDateTimeSpecified(PredictiveDirectionsCriteria.SPECIFIED_DEPARTURE, timeInMillis)`: Specified departure time.
		- `new MapplsDirectionDateTimeSpecified(PredictiveDirectionsCriteria.SPECIFIED_ARRIVAL, timeInMillis)`: Specified arrival time.

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

### Response Messages (as HTTP response message)
1. 200: Success.
2. 204: No matches were found for the provided query.
3. 400: Something’s just not right with the request.
4. 401: Access Denied.
5. 403: Services for this key has been suspended due to daily/hourly transactions limit.
6. 500: Something went wrong.
7. 503: Maintenance Break.
### Response Parameters
1.  `sourcesToTargets(ListList<<PredictiveDistanceResults>>)`: Returns an array of time and distance between the sources and the targets. The array is row-ordered. This means that the time and distance from the first location to all others forms the first row of the array, followed by the time and distance from the second source location to all target locations, etc.
2. `sources(List<List<PredictiveDistanceLocation>>)`: array of source coordinates object.
3.  `units(String)`: Distance units for output. Allowable unit types are mi (miles) and km (kilometers). If no unit type is specified, the units default to kilometers.
4.  `targets(List<List<PredictiveDistanceLocation>>)`:array of target coordinates object.

#### PredictiveDistanceResults Response Result Parameters
1.  `fromIndex(Integer)`: The origin index into the locations array.
2. `distance(Double)`: The computed distance between each set of points. Distance will always be 0.00 for the first element of the distance-time array for one_to_many, the last element in many_to_one, and the first and last elements of many_to_many.
3. `time(Integer)`: Time in seconds. The computed time between each set of points. Time will always be 0 for the first element of the distance-time array for one_to_many, the last element in many_to_one, and the first and last elements of many_to_many.
4. `toIndex(Integer)`: The destination index into the locations array.

#### PredictiveDistanceLocation Response Result Parameters
1. `latitude(Double)`: Latitude of the target in degrees.
2. `longitude(Double)`: Longitude of the target in degrees.

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