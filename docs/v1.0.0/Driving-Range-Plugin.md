
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)

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