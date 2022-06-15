
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)
##  Mappls  Scalebar  Plugin

### Introduction
Mappls Scalebar  Plugin can be used to add a scale bar on map view to determine distance based on zoom level.

Initalize ScaleBarPlugin in  onMapReady()

## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy	
implementation 'com.mappls.sdk:scalebar-plugin:1.0.0'
~~~

####  Java

~~~java
   ScaleBarPlugin scaleBarPlugin = new ScaleBarPlugin(mapView, mapplsMap);  
   ScaleBarOptions scaleBarOptions = new ScaleBarOptions(this)  
        .setTextColor(android.R.color.black)  
        .setTextSize(40f)  
        .setBarHeight(5f)
        
  scaleBarPlugin.create(scaleBarOptions);
~~~

####  Kotlin

~~~kotlin
  val scaleBarPlugin = ScaleBarPlugin(mapView, mapplsMap!!)  
  val scalebarOptions = ScaleBarOptions(this)  
        .setTextColor(android.R.color.black)  
        .setTextSize(40f)  
        .setBarHeight(5f)
        
  scaleBarPlugin.create(scalebarOptions)
~~~

You can use `ScaleBarOptions` to set the following properties:

*  `setPrimaryColor(Integer)`  :  To  set  primary  color  of  scalebar
*  `setSecondaryColor(Integer)`:  To  set  secondary  color  of  scalebar
*  `setTextColor(Integer)`:  To  set  the  text  color  of  text  in  scalebar
*  `setTextSize(float)`:  To  set  the  text  size  in  scalebar
*  `setBarHeight(float)`:  To  set  the  height  of  scalebar
*  `setBorderWidth(float)`:  To  set  border  width  of  scalebar
*  `setRefreshInterval(Integer)`:  To  set  refresh  interval  of  scalebar
*  `setMarginTop(Integer)`:  To  set  top  margin  of  scalebar
*  `setMarginLeft(Integer)`:  To  set  left  margin  of  scalebar
*  `setTextBarMargin(float)`:  To  set  margin  between  text  and  scalebar
*  `setMaxWidthRatio(float)`  :  To  set  width  ratio  of  scalebar
*  `setShowTextBorder(Boolean)`:  To  show  text  border  or  not
*  `setMetricUnit(Boolean)  `:  To  use  metric  unit
*  `setTextBorderWidth(float)`:  To  set  border  width  of  text

<br><br><br>

## Our many happy customers:

![](https://www.mapmyindia.com/api/img/logos1/PhonePe.png)![](https://www.mapmyindia.com/api/img/logos1/Arya-Omnitalk.png)![](https://www.mapmyindia.com/api/img/logos1/delhivery.png)![](https://www.mapmyindia.com/api/img/logos1/hdfc.png)![](https://www.mapmyindia.com/api/img/logos1/TVS.png)![](https://www.mapmyindia.com/api/img/logos1/Paytm.png)![](https://www.mapmyindia.com/api/img/logos1/FastTrackz.png)![](https://www.mapmyindia.com/api/img/logos1/ICICI-Pru.png)![](https://www.mapmyindia.com/api/img/logos1/LeanBox.png)![](https://www.mapmyindia.com/api/img/logos1/MFS.png)![](https://www.mapmyindia.com/api/img/logos1/TTSL.png)![](https://www.mapmyindia.com/api/img/logos1/Novire.png)![](https://www.mapmyindia.com/api/img/logos1/OLX.png)![](https://www.mapmyindia.com/api/img/logos1/sun-telematics.png)![](https://www.mapmyindia.com/api/img/logos1/Sensel.png)![](https://www.mapmyindia.com/api/img/logos1/TATA-MOTORS.png)![](https://www.mapmyindia.com/api/img/logos1/Wipro.png)![](https://www.mapmyindia.com/api/img/logos1/Xamarin.png)

<br>

For any queries and support, please contact:

[<img src="https://mmi-api-team.s3.amazonaws.com/Mappls-SDKs/Resources/mappls-logo.png" height="40"/> </p>](https://about.mappls.com/api/)    
Email us at [apisupport@mappls.com](mailto:apisupport@mappls.com)

![](https://www.mapmyindia.com/api/img/icons/stack-overflow.png)    
[Stack Overflow](https://stackoverflow.com/questions/tagged/mappls-api)    
Ask a question under the mappls-api

![](https://www.mapmyindia.com/api/img/icons/support.png)    
[Support](https://about.mappls.com/contact/)    
Need support? contact us!

![](https://www.mapmyindia.com/api/img/icons/blog.png)    
[Blog](http://www.mapmyindia.com/blog/)    
Read about the latest updates & customer stories

> © Copyright 2022. CE Info Systems Ltd. All Rights Reserved. | [Terms & Conditions](https://about.mappls.com/api/terms-&-conditions).