
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)

# MapplsPin Strategy

## Add Marker
To add marker using mappls pin:
~~~java
mapplsMap.addMarker(new MarkerOptions().mapplsPin(mapplsPin));
~~~

To get the callback that marker is added or not:
~~~java
mapplsMap.addMarker(new MarkerOptions().mapplsPin(mapplsPin), new OnMarkerAddedListener() {  
    @Override  
  public void onSuccess() {  
        //On Marker Added Successfully  
    }  
  
    @Override  
  public void onFailure() {  
	  //Failure
    }  
});
~~~


## To set Camera to particular eLoc

##### Sdk allows various method to Move, ease,animate Camera to a particular location :

#### Java
~~~java
mapplsMap.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
mapplsMap.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));
~~~
#### Kotlin
~~~kotlin
mapplsMap?.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
mapplsMap?.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));  
mapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom("MMI000", 14));
~~~
## To set Camera to particular eLoc Bound

##### Sdk allows various method to Move, ease,animate Camera to a particular eLoc Bound :

#### Java
~~~java
mapplsMap.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(eLocs, 10 , 100, 10, 10));  
mapplsMap.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(eLocs, 10 , 100, 10, 10));  
mapplsMap.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(eLocs, 10 , 100, 10, 10));
~~~
#### Kotlin
~~~kotlin
mapplsMap?.moveCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(eLocs, 10 , 100, 10, 10));  
mapplsMap?.easeCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(eLocs, 10 , 100, 10, 10));  
mapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(eLocs, 10 , 100, 10, 10));
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