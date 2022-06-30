
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="80"/> </p>](https://www.mapmyindia.com/api)

# Mappls Driving Range Plugin for Android

## [Introduction](#Introduction)

The Mappls Driving Range Plugin allows you to plot driving range area to drive based on time or distance on Mappls vector map component.

The plugin offers the following basic functionalities:

- Get and Plot driving range as polygon on map.
- Update driving range on map.
- Clear already plotted driving range on map.

**This can be done by following simple steps.**


## [Implementation](#Implementation)

- Add below dependency in your app-level build.gradle
~~~groovy	
implementation 'com.mappls.sdk:driving-range-plugin:1.0.0'
~~~


## [Initialise Plugin](#Initialise-Plugin)

### [MapplsDrivingRangePlugin](#MapplsDrivingRangePlugin)

`MapplsDrivingRangePlugin` is the main class which is required to be initialized to use different functionalities of plugin.

It allows you to plot driving range area based on time or distance.
#### Java
~~~java
MapplsDrivingRangePlugin mapplsDrivingRangePlugin = new MapplsDrivingRangePlugin(mapView, mapplsMap);
~~~
#### Kotlin
```kotlin
var mapplsDrivingRangePlugin = MapplsDrivingRangePlugin(mapView, mapplsMap)
```

## [Plot Driving Range](#Plot-Driving-Range)

A function `drawDrivingRange` of instance of `MapplsDrivingRangePlugin` will be used to get driving range and plot on map. This function will accept an instance of `MapplsDrivingRangeOption` as request to get driving range.

Below is code for reference:
#### Java
~~~java
MapplsDrivingRangeContour rangeContour = MapplsDrivingRangeContour.builder()  
        .value(50)  
        .color("ff0000")  
        .build();  
List<MapplsDrivingRangeContour> list = new ArrayList<>();  
list.add(rangeContour);  
MapplsDrivingRangeOption option = MapplsDrivingRangeOption.builder()  
        .location(location)  
        .rangeTypeInfo(  
                MapplsDrivingRangeTypeInfo.builder()  
                        .rangeType(DrivingRangeCriteria.RANGE_TYPE_TIME)  
                        .contours(list)  
                        .build()  
        ).build(); 
mapplsDrivingRangePlugin.drawDrivingRange(option)
           
           //Or with callback
mapplsDrivingRangePlugin.drawDrivingRange(option, new IDrivingRangeListener() {  
    @Override  
  public void onSuccess() {  
          //Success
    }  
  
    @Override  
  public void onFailure(int code, @NonNull String message) {  
		  // Failure
    }  
});
~~~
#### Kotlin
```kotlin
val range = MapplsDrivingRangeContour.builder()  
    .value(50)  
    .color("ff0000")  
    .build();  
val option = MapplsDrivingRangeOption.builder()  
    .location(location)  
    .rangeTypeInfo(  
        MapplsDrivingRangeTypeInfo.builder()  
            .rangeType(DrivingRangeCriteria.RANGE_TYPE_TIME)  
            .contours(listOf(range))  
            .build()  
    ).build()
mapplsDrivingRangePlugin?.drawDrivingRange(option)

                 //OR with callback
mapplsDrivingRangePlugin?.drawDrivingRange(option, object :  
    IDrivingRangeListener {  
    override fun onSuccess() {  
        Toast.makeText(this@DrivingRangeActivity, "Success", Toast.LENGTH_SHORT).show()  
    }  
  
    override fun onFailure(code: Int, message: String) {  
        Toast.makeText(this@DrivingRangeActivity, message, Toast.LENGTH_SHORT).show()  
    }  
})
```
### [Request Parameters](#Request-Parameters)

**MapplsDrivingRangeOption**

It is a structure that specifies the criteria for request for getting polygons as range to drive based on time or distance.

It contains following properties.

- `name[String]`: It is used for name of the driving range request. If name is specified, the name is returned with the response.

- `location[Point](Mandatory)`: It is center point for range area that will surrounds the roads which can be reached from this point in specified time range(s).

- `drivingProfile[String]`:  Driving profile for routing engine. Default value is `auto`.

- `speedTypeInfo[MapplsDrivingRangeSpeed]`: It is used to calculate the polygon.  Default value is `MapplsDrivingRangeSpeed.optimal()`'

- `rangeTypeInfo[MapplsDrivingRangeTypeInfo](Mandatory)`:- It is used to specify the type of range which is used to calculate the polygon.

- `isForPolygons[Boolean]`:  It is used to determine whether to return geojson polygons or linestrings. The default is true.

- `showLocations[Boolean]`: It indicating whether the input locations should be returned as MultiPoint features. The default is false. It returns
  -  one point feature for the exact input coordinates
  -  one point feature for the coordinates of the network node it snapped to.

- `denoise[Float]`: A floating point value from 0 to 1 (default of 1) which will be used to remove smaller contours. Default value is 0.5.

- `generalize[Float]`: A floating point value in meters used as the tolerance for Douglas-Peucker generalization. Default value is 1.2


**MapplsDrivingRangeTypeInfo**

It contains following properties.

- `rangeType[String]`: It specify the type of range which is used to calculate the polygon. Possible values are:
  - DrivingRangeCriteria.RANGE_TYPE_TIME (Default): It means it takes time in min.
  - DrivingRangeCriteria.RANGE_TYPE_DISTANCE: It means it takes distance in Km.

- `contours[List<MapplsDrivingRangeContour>]`: Each contour object is combination of value and colorHexString.

**MapplsDrivingRangeContour**

It contains following properties.

- `value(Integer)`: value taken as time or distance defined in instance of `MapplsDrivingRangeTypeInfo`.
  - If type of range is time in minutes(default = 15, maximum = 80)
  - If type of range is distance in kilometers(default = 1, maximum = 80)

- `color[String]`: It specify the color for the output of the contour. Pass a hex value, such as `ff0000` for red.


**MapplsDrivingRangeSpeed**

- For optimal Speed type.
~~~java
MapplsDrivingRangeSpeed.optimal();
~~~

- For predictive speed type with current timestamp
~~~java
MapplsDrivingRangeSpeed.predictiveSpeedFromCurrentTime();
~~~

- For predictive speed type with custom timestamp:
~~~java
MapplsDrivingRangeSpeed.predictiveSpeedFromCustomTime(time)
~~~


## [Additional Features](#Additional-Features)

We have provided some features with the plugin which will help to further enhance the experience.

### [Auto Fit Bounds](#Auto-Fit-Bounds)

To fit the bounds of the map for plotted driving range, user has to set camera.
#### Java
~~~java
mapplsDrivingRangePlugin.isSetMapBoundForDrivingRange(false);
~~~
#### Kotlin
```kotlin
mapplsDrivingRangePlugin?.isSetMapBoundForDrivingRange(false)
```
On setting it `true`, plugin will set camera to fit bound. It's default value is `true`.


### [Clear Driving Range](#Clear-Driving-Range)

Plotted driving range on Map can be removed by calling function `clearDrivingRangeFromMap`.

Refer to the code below:
#### Java
~~~java
mapplsDrivingRangePlugin.clearDrivingRangeFromMap();
~~~

#### Kotlin
```kotlin
mapplsDrivingRangePlugin?.clearDrivingRangeFromMap()
```


<br><br><br>


[<p align="center"> <img src="https://www.mapmyindia.com/api/img/icons/stack-overflow.png"/> ](https://stackoverflow.com/questions/tagged/mappls-api)[![](https://www.mapmyindia.com/api/img/icons/blog.png)](https://about.mappls.com/blog/)[![](https://www.mapmyindia.com/api/img/icons/gethub.png)](https://github.com/mappls-api)[<img src="https://mmi-api-team.s3.ap-south-1.amazonaws.com/API-Team/npm-logo.one-third%5B1%5D.png" height="40"/> </p>](https://www.npmjs.com/org/mapmyindia) 

[<p align="center"> <img src="https://www.mapmyindia.com/june-newsletter/icon4.png"/> ](https://www.facebook.com/Mapplsofficial)[![](https://www.mapmyindia.com/june-newsletter/icon2.png)](https://twitter.com/mappls)[![](https://www.mapmyindia.com/newsletter/2017/aug/llinkedin.png)](https://www.linkedin.com/company/mappls/)[![](https://www.mapmyindia.com/june-newsletter/icon3.png)](https://www.youtube.com/channel/UCAWvWsh-dZLLeUU7_J9HiOA)

<div align="center">@ Copyright 2022 CE Info Systems Pvt. Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://www.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://www.mappls.com/pdf/mappls-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://www.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://www.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
