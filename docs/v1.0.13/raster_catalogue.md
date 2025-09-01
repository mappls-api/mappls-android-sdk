
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# DigitalSky Airspace Layers

## Introduction
This is an easy & FREE to integrate DigitalSky Airspace zones layers widget by Mappls.

The drone airspace map is an interactive map of India that demarcates the yellow and red zones across the country. 

The airspace map may be modified by authorised entities from time to time.
Anyone planning to operate a drone should mandatorily check the latest airspace map for any changes in zone boundaries.
## Add the dependency

Add below dependency in your app-level build.gradle
~~~groovy	
implementation 'com.mappls.sdk:raster-catalogue-plugin:1.0.0'
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

## Available Raster  Catalogue Layer
1. `MapplsRasterCatalogueType.INTERNATIONAL_BOUNDARY_25_KM`
2. `MapplsRasterCatalogueType.AIRPORT_8_TO_12_KM_YELLOW`
3. `MapplsRasterCatalogueType.AIRPORT_5_TO_8_KM`
4. `MapplsRasterCatalogueType.AIRPORT_0_T0_5_KM`

## Initialise the plugin

#### Java
~~~java
MapplsRasterCataloguePlugin mapplsRasterCataloguePlugin = new MapplsRasterCataloguePlugin(mapView, mapplsMap);
~~~
#### Kotlin
~~~kotlin
val mapplsRasterCataloguePlugin = MapplsRasterCataloguePlugin(mapView, mapplsMap)
~~~
## Add Raster Catalogue layer
#### Java
~~~java
mapplsRasterCataloguePlugin.addRasterCatalogueLayer(MapplsRasterCatalogueType.AIRPORT_0_T0_5_KM);
~~~
#### Kotlin
~~~kotlin
mapplsRasterCataloguePlugin?.addRasterCatalogueLayer(MapplsRasterCatalogueType.AIRPORT_0_T0_5_KM)
~~~
## Remove Raster Catalogue Layer
#### Java
~~~java
mapplsRasterCataloguePlugin.removeRasterCatalogueLayer(MapplsRasterCatalogueType.AIRPORT_0_T0_5_KM);
~~~
#### Kotlin
~~~kotlin
mapplsRasterCataloguePlugin?.removeRasterCatalogueLayer(MapplsRasterCatalogueType.AIRPORT_0_T0_5_KM)
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




<div align="center">@ Copyright 2025 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>

