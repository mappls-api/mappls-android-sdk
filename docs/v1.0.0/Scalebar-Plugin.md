[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

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
