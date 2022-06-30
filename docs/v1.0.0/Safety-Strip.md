
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# COVID-19 Safety Status on Mappls Map

## Introduction

It is a guide to display a user's safety status for COVID-19 on a map. It will show whether user is in a containment zone or not if yes how much is distance from current location.

Following this guide it will be possible to display user's safety status on Mappls Map  in the form of a safety strip depending upon the user's current location.

A method need to be called to check safety status. SDK has inbuild view with button which can be used to call that method.


## Getting Started

### Get Safety Status

After loading of Map, a mthod can be called to get safety status.  `showCurrentLocationSafety`  is the method which helps to find user's safety status. On successfully fetching of status a safety strip will be shown on map.

#### Java
~~~java
mapplsMap.showCurrentLocationSafety();
~~~

#### Kotlin
~~~kotlin
mapplsMap?.showCurrentLocationSafety()
~~~


### Safety Strip

On successfully receiving status based on location from server, a view will be shown on map with safety information like distance, containment zone location etc.

### Safety Strip Position

By default safety status strip will be shown on top of map.

#### Java
~~~java
mapplsMap.getUiSettings().setSafetyStripGravity(Gravity.TOP);
~~~

#### Kotlin
~~~kotlin
mapplsMap?.uiSettings?.safetyStripGravity = Gravity.TOP
~~~
### Safety Strip Margins
By default margins of safety status is zero.

#### Java
~~~java
mapplsMap.getUiSettings().setSafetyStripMargins(left, top, right, bottom);
~~~

#### Kotlin
~~~kotlin
mapplsMap?.uiSettings?.setSafetyStripMargins(left, top, right, bottom)
~~~
### Hide Safety Status

#### Java
~~~java
mapplsMap.getUiSettings().hideSafetyStrip();
~~~
#### Kotin
~~~kotlin
mapplsMap?.uiSettings?.hideSafetyStrip()
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
