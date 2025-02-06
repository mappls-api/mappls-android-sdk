[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Route Report Summary
This API allows you to get the reports falls on the route on the basis of input routeId.
It allows to get Traffic, Road Condition and Safety reports in the response.

#### Java
~~~java
MapplsRouteSummary mapplsRouteSummary = MapplsRouteSummary.builder()
        .routeId(routeId)
        .build();
MapplsRouteSummaryManager.newInstance(mapplsRouteSummary).call(new OnResponseCallback<RouteReportSummaryResponse>() {
    @Override
    public void onSuccess(RouteReportSummaryResponse routeReportSummaryResponse) {
        //Handle Success Response
    }

    @Override
    public void onError(int i, String s) {
        //Handle Failure
    }
});
~~~

#### Kotlin
~~~kotlin
val mapplsRouteSummary = MapplsRouteSummary.builder()
        .routeId(routeId)
        .build()
MapplsRouteSummaryManager.newInstance(mapplsRouteSummary)
    .call(object : OnResponseCallback<RouteReportSummaryResponse?> {
        override fun onSuccess(routeReportSummaryResponse: RouteReportSummaryResponse?) {
            //Handle Success Response
        }

        override fun onError(i: Int, s: String) {
            //Handle Failure
        }
})
~~~
### Request Parameters
#### Mandatory Parameter
1. `routeId(String)`: The alphanumeric routeId of the route.

#### Optional Parameter
1. `routeIdx(Integer)`: provide routeIdx to get the report on selected route only. By default response for all routes are returned.
2. `currentNode(String)`: accepts the nodeId to get reports on the required node only
3. `isGroup(Integer)`: This parameter enables to get the reports group by category. To the get the grouped response set the value to 1. By default reports are sorted in response as per route navigation direction.
4. `categories(String...)`: provide list of category ids to get the reports of the required categories. Parent and childCategory are accepted.

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
1. `routes(List<RouteReport>)`: Array of Reports.

#### Route Report Response Object
1. `index(Long)`: The index of the report lying along the route.
2. `routeId(String)`: 
3. `reportDetails(List<ReportDetails>)`: 

#### Report Details Response Object
1. `id(String)`: Unique identifier of the report.
2. `parentCategory(String)`: Parent category name of the report.
3. `childCategory(String)`: Child category name of the report.
4. `address(String)`: Place address of the report.
5. `latitude(Double)`: Latitude of the report.
6. `longitude(Double)`: Longitude of the report
7. `nodeIdx(Long)`: Node index of route. Only available with nodeId input parameter.
8. `status(String)`: Status of the report i.e. Published, Unpublished.
9. `addedByName(String)`: User name of the user added the report.
10. `addedBy(String)`: User id of the user added the report.
11. `expiry(Long)`: Expiry time of the report in epoch format.
12. `usersCount(Long)`: Number of the users reported the report
13. `description(String)`: Description about the report.
14. `parentCategoryId(Integer)`: Parent category code of the report.
15. `childCategoryId(Integer)`: Child category code of the report.
16. `userProfileIcon(String)`: Profile icon image name of the the user added the report.

**To get Report Icons**
1. `getReportIcon(pixel)`: Complete url of Reported category Icon. Below are the possible values of pixel:
    - `ReportCriteria.ICON_24_PX`
    - `ReportCriteria.ICON_36_PX`
    - `ReportCriteria.ICON_48_PX`
    - `ReportCriteria.ICON_54_PX`
2. `getParentReportIcon(pixel)`: Complete url of Parent Catoegory Icon. Below are the possible values of pixel:
    - `ReportCriteria.ICON_24_PX`
    - `ReportCriteria.ICON_36_PX`
    - `ReportCriteria.ICON_48_PX`
    - `ReportCriteria.ICON_54_PX`

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
