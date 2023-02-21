
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Feedback API

If you want to send feedback for search and selection user made you can use this api and it will improve your search result.

#### Java
~~~java
MapplsFeedback feedback = MapplsFeedback.builder()  
        .typedKeyword("Map")  
        .mapplsPin("mmi000")  
        .locationName("Map")
        .index(1)  
        .longitude(77.0)  
        .latitude(28.0)  
        .userName(userName)  
        .appVersion(BuildConfig.VERSION_NAME)  
        .build();  
MapplsFeedbackManager.newInstance(feedback).call(new OnResponseCallback<Void>() {  
    @Override  
    public void onSuccess(Void response) {  
        //Handle response  
    }  
  
    @Override  
    public void onError(int code, String message) {  
        //Handle error  
    }  
});
~~~
#### Kotlin
~~~kotlin
val feedback = MapplsFeedback.builder()
    .typedKeyword("Map")
    .mapplsPin("mmi000")
    .locationName("Map")
    .index(1)
    .longitude(77.0)
    .latitude(28.0)
    .userName(userName)
    .appVersion(BuildConfig.VERSION_NAME)
    .build()
MapplsFeedbackManager.newInstance(feedback).call(object : OnResponseCallback<Void?> {
    override fun onSuccess(response: Void?) {
        //Success  
    }

    override fun onError(code: Int, message: String) {
        //Handle Error  
    }
})
~~~
### Request Parameters

#### Mandatory Parameter
1.  `typedKeyword (String)` : The string that was searched. Must be 2 characters or more.
2. `mapplsPin (String)` : eLoc of the location that was selected. Must be exactly 6 characters.
3. `index (Integer)` : the index of the selected object that was returned from the search.
4. `userName (String)` : the username of the user that’s logged in.
5. `appVersion (String)` : the version of the app that was used to make the request.

#### Optional Parameter
1. `longitude (Double)` : the longitude of the location from where the search is made. The longitude must be a double value, must not start with 0.
2.  `latitude (Double)` : the latitude of the location from where the search is made. The latitude must be a double value, must not start with 0 and must not be larger than the longitude.
3.  `locationName (String )` : name of the location that was selected.

### Response Code (as HTTP response code)

#### Success:

1.  201: To denote that the feedback was successfully created.

#### Client side issues:

1.  400: Bad Request, User made an error while creating a valid request.
2.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3.  403: Forbidden, Developer’s key has hit its daily/hourly limit.

#### Server-Side Issues:

1.  500: Internal Server Error, the request caused an error in our systems.
2.  503: Service Unavailable, during our maintenance break or server downtimes.

### Response Messages (as HTTP response message)

1.  201: Feedback submitted.
2.  400: Something’s just not right with the request.
3.  401: Access Denied.
4.  403: Services for this key has been suspended due to daily/hourly transactions limit.
5.  500: Something went wrong.
6.  503: Maintenance Break.

### Response parameters
The response of this API would be empty. Success would be denoted by the response codes and error would be denoted with the response codes.

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
