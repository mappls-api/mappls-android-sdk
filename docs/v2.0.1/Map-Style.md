[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# [Mappls Map style]()

Mappls offers a variety of preset map styles for rendering your maps. To use these styles, you need to retrieve the list of available styles associated with your account. The listing API helps you both render a specific style and easily switch between different style themes.

From the reference code below, it’s clear that you specify style names, not URLs, when applying styles. Every account starts with a default style to get you up and running quickly.

For a complete list of available styles or further assistance, please contact apisupport@mappls.com.


## [List of Available Styles]()

Explore different map styles Mappls offer [Live Demo](https://about.mappls.com/api/web-sdk/vector-map-example/Maps/mappls-map-style)

To get the list of available styles:
#### Kotlin
```kotlin  
val styleList = mapplsMap.mapplsAvailableStyles
```  
#### Java
```java  
List<MapplsStyle> styleList = mapplsMap.getMapplsAvailableStyles();
``` 
`MapplsStyle` contains below parameters:

1. `displayName(string)`: Generic Name of style mostly used in Mappls content.
2. `imageUrl(String)`: Preview Image of style
3. `name(String)`: Name of style used to change the style.

## [Set Mappls Style]()
To set Mappls Maps style reference code is below:
#### Kotlin
```kotlin  
mapplsMap.setMapplsStyle(style, object : OnStyleLoadListener {
    override fun onError(p0: String?) {
        
    }
    override fun onStyleLoaded(style: Style) {
        
    }
})
//OR
mapplsMap.setMapplsStyle(name)
```
#### Java
```java  
mapplsMap.setMapplsStyle(name, new OnStyleLoadListener() {
    @Override
    public void onError(String error) {
        
    }
    @Override
    public void onStyleLoaded(Style styles) {
        
    } });
//OR
mapplsMap.setMapplsStyle(name);  
```  

## [To enable/disable last selected style]()
To enable/disable loading of last selected style:
#### Kotlin
```kotlin  
MapplsMapConfiguration.getInstance().isShowLastSelectedStyle = false //true is enable & false is disable(default value is true)
``` 
#### Java
```java  
MapplsMapConfiguration.getInstance().setShowLastSelectedStyle(false); //true is enable & false is disable(default value is true) 
```

## [Get Selected style]()
To get the current style name in use:
#### Kotlin
~~~kotlin  
val mapplsStyle = mapplsMap.style?.mapplsStyle
~~~
#### Java
~~~java  
String mapplsStyle = mapplsMap.getStyle().getMapplsStyle(); 
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




<div align="center">@ Copyright 2026 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>

