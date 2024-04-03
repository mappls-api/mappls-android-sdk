
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Place Picker Widget

##  Introduction
Mappls Place Picker Plugin can be used to choose a specific location.


## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy	
implementation 'com.mappls.sdk:place-widget:2.2.0'
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




<div align="center">@ Copyright 2023 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
