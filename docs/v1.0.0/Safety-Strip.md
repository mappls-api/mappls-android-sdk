
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)

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