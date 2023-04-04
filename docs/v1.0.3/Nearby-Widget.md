[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Mappls Nearby Search Widget

## [Introduction](#Introduction)

The Mappls Nearby Widget makes it easy to integrate the functionality to search nearby POIs for selected categories in your Android application. The Nearby Search widget provided as a means to enable radially search for Nearby Places on Mappls Maps.

The widget offers the following basic functionalities:

- Ability to search for nearby places directly with Mappls Maps visual interface.

- A single method to initiate nearby search across all categories of places available on Mappls.

- Ability to get information from Mappls Nearby Search widget through a callback.

This can be done by following simple steps.

	
## [Dependencies](#Dependencies)

- Add below dependency in your app-level build.gradle	
~~~groovy	
   implementation 'com.mappls.sdk:nearby-ui:1.0.2'
~~~

## Step 2 :-  [Adding Credentials](#Adding-Credentials)

Add your API keys to the SDK (in your application's onCreate() or before using map)

#### Java	
```java	
MapplsAccountManager.getInstance().setRestAPIKey(getRestAPIKey());  	
MapplsAccountManager.getInstance().setMapSDKKey(getMapSDKKey());  		
MapplsAccountManager.getInstance().setAtlasClientId(getAtlasClientId());  	
MapplsAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());  	
Mappls.getInstance(applicationContext);
```
#### Kotlin	
```kotlin	
MapplsAccountManager.getInstance().restAPIKey = getRestAPIKey()  	
MapplsAccountManager.getInstance().mapSDKKey = getMapSDKKey()  		
MapplsAccountManager.getInstance().atlasClientId = getAtlasClientId()  	
MapplsAccountManager.getInstance().atlasClientSecret = getAtlasClientSecret()	
Mappls.getInstance(applicationContext)
```

  
## Step 3 :-  [Launching Nearby Widget](#Launching-Nearby-Widget)

There are two ways to implement  `Nearby Search`  widget:

-   Using `MapplsNearbyFragment`
-   Using  `MapplsNearbyActivity`

### [MapplsNearbyFragment](#nearby-fragment)
Add MapplsNearbyFragment in your activity:

#### java
~~~java
MapplsNearbyFragment nearbyFragment = MapplsNearbyFragment.newInstance();

getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, nearbyFragment, MapplsNearbyFragment.class.getSimpleName())  
        .commit();  
~~~

#### Kotlin
~~~kotlin
val nearbyFragment: MapplsNearbyFragment= MapplsNearbyFragment.newInstance()
supportFragmentManager.beginTransaction().add(R.id.fragment_container, nearbyFragment, MapplsNearbyFragment::class.java.simpleName)  
        .commit()
~~~

To get the selected nearby place use IMapplsNearbyCallback interface:
#### Java
~~~java
nearbyFragment.setMapplsNearbyCallback(new IMapplsNearbyCallback() {  
    @Override  
    public void getNearbyCallback(NearbyAtlasResult nearbyAtlasResult) {  
       // Select place
    }    
});
~~~
#### Kotlin
~~~kotlin
nearbyFragment.setMapplsNearbyCallback(object : IMapplsNearbyCallback {  
    override fun getNearbyCallback(nearbyCallback: NearbyAtlasResult) {  
        //Select place  
    }  
})
~~~

### [MapplsNearbyActivity](#nearby-activity)
Add MapplsNearbyActivity in your activity:  
####  java  
~~~java  
 Intent intent = new MapplsNearbyWidget.IntentBuilder().build(this);   
 startActivityForResult(intent, 101);  
~~~  
  
####  Kotlin  
  
~~~kotlin  
val intent = MapplsNearbyWidget.IntentBuilder().build(this)   
startActivityForResult(intent, 101)  
~~~  
  
To get the selected POI:  
####  java  
  
~~~java  
@Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {    
       super.onActivityResult(requestCode, resultCode, data);    
       if (requestCode == 101 && resultCode == RESULT_OK) {    
        NearbyAtlasResult result = MapplsNearbyWidget.getNearbyResponse(data);    
} }  
~~~  
  
####  Kotlin  
  
~~~kotlin  
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {    
      super.onActivityResult(requestCode, resultCode, data)    
      if(requestCode == 101 && resultCode == Activity.RESULT_OK) {    
            val place: NearbyAtlasResult? = MapplsNearbyWidget.getNearbyResponse(data!!)              
} }  
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
