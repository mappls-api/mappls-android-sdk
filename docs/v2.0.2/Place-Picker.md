
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Place Picker Widget

##  Introduction
The **Mappls Place Picker Widget** provides a simple and intuitive way for users to select a location on the map.

The widget offers built-in support for map interaction, search, current location access, camera customization, and marker positioning, allowing developers to integrate location selection workflows with minimal effort.


## Add the dependency
Add below dependency in app level build.gradle file
#### Kotlin (build.gradle.kts)
```kotlin
// When using the BoM, you don't specify versions in Mappls library dependencies
implementation("com.mappls.sdk:place-widget")

//Or Add Dependency with Version
implementation("com.mappls.sdk:place-widget:3.0.1")
```
#### Groovy (build.gradle)
```groovy
// When using the BoM, you don't specify versions in Mappls library dependencies
implementation 'com.mappls.sdk:place-widget'

//Or Add Dependency with Version
implementation("com.mappls.sdk:place-widget:3.0.1")
```

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

# PlacePickerOptions

Use `PlacePickerOptions` to customize the appearance and behavior of the Place Picker widget.

| Property | Description |
|-----------|-------------|
| `toolbarColor(Integer)` | Sets the toolbar color of the Place Picker widget. |
| `startingBounds(LatLngBounds)` | Opens the map within the specified geographic bounds. |
| `statingCameraPosition(CameraPosition)` | Sets the initial camera position, including zoom level, center coordinate, bearing, and tilt. |
| `includeDeviceLocationButton(Boolean)` | Enables or disables the current location button. |
| `marker(Integer)` | Sets a custom marker drawable displayed at the center of the map. |
| `mapMaxZoom(Double)` | Sets the maximum zoom level allowed on the map. |
| `mapMinZoom(Double)` | Sets the minimum zoom level allowed on the map. |
| `includeSearch(Boolean)` | Enables or disables the search functionality within the widget. |
| `searchPlaceOption(PlaceOptions)` | Configures the behavior and appearance of the integrated search widget. |

## Example Configuration

### Kotlin

```kotlin
val placePickerOptions = PlacePickerOptions.builder()
    .toolbarColor(Color.parseColor("#3F51B5"))
    .includeDeviceLocationButton(true)
    .includeSearch(true)
    .mapMinZoom(4.0)
    .mapMaxZoom(18.0)
    .build()
```

### Java

```java
PlacePickerOptions placePickerOptions = PlacePickerOptions.builder()
        .toolbarColor(Color.parseColor("#3F51B5"))
        .includeDeviceLocationButton(true)
        .includeSearch(true)
        .mapMinZoom(4.0)
        .mapMaxZoom(18.0)
        .build();
```

---

# Retrieving the Selected Place
Once the user selects a location and returns from the Place Picker, retrieve the selected `Place` object.

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




<div align="center">@ Copyright 2026 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
