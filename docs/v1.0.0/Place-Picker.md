
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)


# Mappls Place Picker Widget

##  Introduction
Mappls Place Picker Plugin can be used to choose a specific location.


## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy	
implementation 'com.mappls.sdk:place-widget:2.0.0'
~~~

## Add Place Picker

To add the place picker widget
####  java

~~~java
   Intent intent = new PlacePicker.IntentBuilder()  
                .placeOptions(placePickerOptions).build(this);  
   startActivityForResult(intent, 101);
~~~

####  Kotlin

~~~kotlin
  val intent = PlacePicker.IntentBuilder()  
            .placeOptions(placePickerOptions).build(this)  

  startActivityForResult(intent, 101)
~~~
You can use `PlacePickerOptions` to set the following properties:
1. `toolbarColor(Integer)`:  To set the toolbar color of place widget
2. `startingBounds(LatLngBounds)`: To open a map in a bound
3. `statingCameraPosition(CameraPosition)`: To open a map that sets in camera poition you can set zoom, centre, bearing etc.,
4. `includeDeviceLocationButton(Boolean)`: To enable/ disable current location functionality
5. `marker(Integer)`: To change the marker image which is visible in the centre of a map
6. `mapMaxZoom(Double)`: To set maximum zoom level of the map
7. `mapMinZoom(Double)`: To set minimum zoom level of the map
8. `includeSearch(Boolean)`: To provide opions for search locations
9. `searchPlaceOption(PlaceOptions)`: To set all the properties of search widget
   ​

##  Get Result

To get the pick place:
####  java

~~~java
@Override  
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  
       super.onActivityResult(requestCode, resultCode, data);  
       if (requestCode == 101 && resultCode == RESULT_OK) {  
        Place place = PlacePicker.getPlace(data);  
  }  
}
~~~

####  Kotlin

~~~kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
      super.onActivityResult(requestCode, resultCode, data)  
      if(requestCode == 101 && resultCode == Activity.RESULT_OK) {  
            val place: Place? = PlacePicker.getPlace(data!!)            
    }  
}
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