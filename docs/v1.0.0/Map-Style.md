
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)


# [Set Mappls Map style](#Set-Mappls-Map-style)

Mappls offers a range of preset styles to rendering the map. The user has to retrieve a list of styles for a specific account.   
The listing api would help in rendering specific style as well as facilitate the switching of style themes.

From the below reference code it would become quite clear that user has to specify style names and not URLs to use them.   
A default style is set for all account users to start with.   
To know more about available styles, kindly contact apisupport@mappls.com


## [List of Available Styles](#list-of-available-styles)

To get the list of available styles:

#### Java
~~~java  
List<MapplsStyle> styleList = mapplsMap.getMapplsAvailableStyles();  
~~~  

#### Kotlin
~~~kotlin  
val styleList = mapplsMap.getMapplsAvailableStyles() 
~~~  

`MapplsStyle` contains below parameters:

1. `description(String)`: Description of the style
2. `displayName(string)`: Generic Name of style mostly used in Mappls content.
3. `imageUrl(String)`: Preview Image of style
4. `name(String)`: Name of style used to change the style.

## [Set Mappls Style](#set-mappls-style)
To set Mappls Maps style reference code is below:
#### Java
~~~java  
mapplsMap.setMapplsStyle(name, new OnStyleLoadListener() {    
       @Override    
       public void onError(String error) {    
            Toast.makeText(MapActivity.this, error, Toast.LENGTH_SHORT).show();    
       }    
    
       @Override    
       public void onStyleLoaded(Style styles) {     
           Toast.makeText(MapActivity.this, "onStyleLoaded", Toast.LENGTH_SHORT).show();    
    
} });  
 //OR
 mapplsMap.setMapplsStyle(name);  
~~~  

#### Kotlin
~~~kotlin  
mapplsMap.setMapplsStyle(style, object : OnStyleLoadListener {  
 override fun onError(p0: String?) {
  Toast.makeText(this@MainActivity, p0, Toast.LENGTH_LONG).show()
 }  
 override fun onStyleLoaded(style: Style) {
  Toast.makeText(this@MainActivity, "Style loaded Successfully", Toast.LENGTH_LONG).show() 
  } 
  }) 
 //OR
 mapplsMap.setMapplsStyle(name)  
~~~  

## [To enable/disable last selected style](#To-enable-last-selected-style)
To enable/disable loading of last selected style:

#### Java
~~~java  
MapplsMapConfiguration.getInstance().setShowLastSelectedStyle(false); //true is enable & false is disable(default value is true) 
~~~
#### Kotlin
~~~kotlin  
MapplsMapConfiguration.getInstance().setShowLastSelectedStyle(false) //true is enable & false is disable(default value is true)  
~~~  

## [Get Selected style](#get-selected-style)
To get the currently selected style name:
#### Java
~~~java  
mapplsMap.getStyle().getMapplsStyle();  
~~~  
#### Kotlin
~~~kotlin  
mapplsMap.style.getMapplsStyle() 
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