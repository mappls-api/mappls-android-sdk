[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Trip Cost Estimation

## [Introduction](#Introduction)
Trip Cost Estimation method provides total estimated cost for a route including tolls. User would still get the response for tolls  present on the route in absence of inputs given for fuel cost estimation .

Major features are 
1. Toll on route - It enable user to get the information about tolls on the set route, along with that user get value added information like total number of tolls, total cost on the basis of vehicle types, distance, duration, Individal toll details like Toll Name, Toll Cost, address, coordinates, distance, duration and so on.

**_Note_** : Default parameter is dependent upon route profile selection
 2AxlesAuto for Driving, 2AxlesTruck for Trucking, 2AxlesMoto for Biking 

2. Fuel Cost Estimation in a trip - Provisioning is done to populate estimated trip cost by passing specific params as input, API Provides/calculates the total estimated cost of any trip which include fuel cost and toll costs.

## [Implementation](Implemenation)

#### Java
~~~java
MapplsCostEstimation mapplsCostEstimation = MapplsCostEstimation.builder()  
        .routeId(routeId)  
        .build(); 
        //To call in Foreground Thread 
MapplsCostEstimationManager.newInstance(mapplsCostEstimation).call(new OnResponseCallback<CostEstimationResponse>() {  
    @Override  
  public void onSuccess(CostEstimationResponse response) {  
         //handle response
  }  
  
    @Override  
  public void onError(int code, String message) {  
        //handle Error
  }  
});
              //OR
              //To call in Background Thread
ApiResponse<CostEstimationResponse> response = MapplsCostEstimationManager.newInstance(mapplsCostEstimation).executeCall(); 
~~~

#### Kotlin
~~~kotlin
val mapplsCostEstimation = MapplsCostEstimation.builder()  
        .routeId(routeId)  
        .build();  
        //To call in Foreground Thread 
MapplsCostEstimationManager.newInstance(mapplsCostEstimation).call(object: OnResponseCallback<CostEstimationResponse> {  
      
            override fun onSuccess(response: CostEstimationResponse) {
                //handle response
            }
           override fun onError(code: Int, message: String) {
               //handle Error
            }
})              //OR
              //To call in Background Thread
val response = MapplsCostEstimationManager.newInstance(mapplsCostEstimation).executeCall(); 
~~~

## [Request Parameters](#Request_Parameter)

The bold one are mandatory, and the italic one are optional.  

### [a. Mandatory Parameters](#a_Mandatory_Parameters)

1.	`routeId(String)` : A unique Id created by passing Start and End Location Coordinates.


### [b. Optional Parameters](#b_Optional_Parameters) 

1. `vehicleType(String)`: Vehicle type accepted values are 
	- `CostEstimationCriteria.VEHICLE_TYPE_AUTO` : 2AxlesAuto 
	- `CostEstimationCriteria.VEHICLE_TYPE_BUS` :  2AxlesBus
	- `CostEstimationCriteria.VEHICLE_TYPE_LCV` :  2AxlesLCV 
	- `CostEstimationCriteria.VEHICLE_TYPE_MOTO` :  2AxlesMoto 
	- `CostEstimationCriteria.VEHICLE_TYPE_TRUCK` :  2AxlesTruck
	- `CostEstimationCriteria.VEHICLE_TYPE_HCMEME` :  2AxlesHCMEME
	- `CostEstimationCriteria.VEHICLE_TYPE_THREE_AXLES` :  3Axles
	- `CostEstimationCriteria.VEHICLE_TYPE_FOUR_AXLES` :  4Axles
	- `CostEstimationCriteria.VEHICLE_TYPE_FIVE_AXLES` :  5Axles
	- `CostEstimationCriteria.VEHICLE_TYPE_SIX_AXLES` :  6Axles
	- `CostEstimationCriteria.VEHICLE_TYPE_SEVEN_AXLES` :  7Axles

**Note** : Default parameter is dependent upon route profile selection
 2AxlesAuto for Driving, 2AxlesTruck for Trucking, 2AxlesMoto for Biking 
 
2. `vehicleFuelType(String)` : Fuel type of vehicle accepted values are :
	 - `CostEstimationCriteria.VEHICLE_FUEL_TYPE_PETROL`
	 - `CostEstimationCriteria.VEHICLE_FUEL_TYPE_ELECTRIC`
	 - `CostEstimationCriteria.VEHICLE_FUEL_TYPE_DIESEL`
	 - `CostEstimationCriteria.VEHICLE_FUEL_TYPE_CNG`
3. `fuelEfficiency(Integer)` : Value defined to current efficiency of any vehicle
4. `fuelEfficiencyUnit(String)` : Unit of Fuel based on fuel type accepted values are:
	- `CostEstimationCriteria.UNIT_KM_P_L`
	- `CostEstimationCriteria.UNIT_KM_P_UNIT`
	- `CostEstimationCriteria.UNIT_KM_P_KG`
5. `fuelPrice(Double)` : Price of fuel 
6. `isTollEnabled(Boolean)` : This parameter is to poupulate/restrict toll data, possible Values  are "true" & "false", Default value is set as "false"

**Note**: Claim provision is mandatory to get response/results.

## [Response Parameters](#Response-Parameters)

1. `hasTolls` (Boolean) : Returns true if tolls are available in the given route else false.
2. `totalTolls` (Integer): No of tolls available in the given route.
3. `locations` (`MapplsLocation`) : It contains the array of start, end and way point of route.
4. `departureTime` (Long) : The departure time of the user and default value is current timestamp.
5. `currency`(String) :  Currency and default value is INR.
6. `country` (String) : Country name and default value is India.
7. `vehicleType` (String) : Types of Vehicle and accepted values are 2AxlesMoto, 2AxlesAuto, 2AxlesBus, 2AxlesHCMEME, 2AxlesLCV, 3Axles, 4Axles, 5Axles, 6Axles, 7Axles.
8. `totalTollCost` (Integer) : Total toll cost on basis of tolls falling on the route. 
9. `url` (String) : Url of the route and it is still in process to provide more meaning full response.
10. `distance` (Double) : The estimated distance of the route and the unit is meter.
11. `duration` (Double) : The estimated travel time of the route and the unit is seconds.
12. `tolls`(List<`CostEstimationTollDetail`>): Array of the toll objects fall on the route.
13. `fuelEfficiency`(String): Value defined to current efficiency of any vehicle
14. `totalFuelCost`(Integer): Total fuel cost calculated by formula (distance X Price)/mileage
15. `fuelPrice`(Double) : Price of fuel ; Default is set in case of no selection is "Petrol : 106, Diesel: 100, CNG: 80, EV: 10"
16. `vehicleFuelType`(String) : Provided vehcile fuel type
17. `totalTripcostEstimate`(Double) : Total estimated cost of trip[Toll+Fuel] This parameter only appears in case of routes having tolls

#### CostEstimationTollDetail response Parameter
1. `mapplsPin` (String) : Unique Identifier of toll.
2.  `tollName` (String) : Name of toll.
3. `latitude`(Double) : Latitude of toll.
4. `longitude`(Double) : Longitude of toll.
5. `node`(Double) : Node id of the route where the toll attached.
6. `tollGrpId` (Integer) : Toll group Id.
7. `road` (String) : Name of the road.
8. `roadType` (String) : Type of road.
9. `address` (string) : Address of Toll Plaza.
10. `state` (String) : State Name.
11. `lanes` (Integer) : No of lanes on the toll location.
12. `agency` (string) : Toll maintaining agency such as NHAI, MCD.
13. `type` (String) : Type of Toll and values are barrier and ticket.
14. `averageWaitTimeRange` (Double) : Average Wait time range.
15. `nodeIdx` (Integer) : Node index of the toll on the route.
16. `payment` (List<`String`>) : Mode of payment such as cash, paytm etc.
17. `cost` (Integer) : Cost of toll as per selected vehicle.
18. `emergency` (String) : Emergency contact information if available.
19. `amenities` (List<`Strin`>) : Amenities at toll plaza if available.
20. `distance` (Double): Indvidual estimated distance.
21. `duration` (Double): Indvidual estimated duration.

**_Note_** : To fetch all the parameteres in response user has to provsion all the sub template values. 

#### MapplsLocation Response Parameters
1. `latitude` (Double) : latitude of the start, end or waypoint.
2.  `longitude` (Double) : longitude of the start, end or waypoint.

For more details about response templates, please contact apisupport@mappls.com.


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




<div align="center">@ Copyright 2024 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>

