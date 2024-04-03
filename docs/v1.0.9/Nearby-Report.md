
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Nearby Reports
Mappls Reports APIs enable the user to report any incidents at any place with predefined categories and
get that information on the Map and during Navigation.

Our SDK allows you the developers to get smooth way to access these reports.

**This feature is available from v6.8.15**

#### Java
~~~java
MapplsNearbyReport nearbyReport = MapplsNearbyReport.builder()  
        .topLeft(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()))  
        .bottomRight(Point.fromLngLat(latLng2.getLongitude(), latLng2.getLatitude()))  
        .build();  
        //To call in Foreground Thread
MapplsNearbyReportManager.newInstance(nearbyReport).call(new OnResponseCallback<NearbyReportResponse>() {  
    @Override  
  public void onSuccess(NearbyReportResponse nearbyReportResponse) {  
        
    }  
  
    @Override  
  public void onError(int code, String message) {  
        // Request failed with code and message   
 }  
});
			  //OR
        //To call in Background Thread
ApiResponse<NearbyReportResponse> response = MapplsNearbyReportManager.newInstance(nearbyReport).executeCall(); 
~~~

### Request Parameters
#### Mandatory Parameter
1. ``topLeft(Point)``: Point with top left latitude longitude
2. ``bottomRight(Point)``:Point with bottom right latitude longitude

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
#### NearbyReport Response Result Parameters
1. ``id``         (String): id of the report
2.  ``createdOn``  (Long): Timestamp when event created
3.  ``latitude``  (Double): latitude of the report.
4.  ``longitude`` (Double): longitude of the report.
5.  ``category``  (String): report category.


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
