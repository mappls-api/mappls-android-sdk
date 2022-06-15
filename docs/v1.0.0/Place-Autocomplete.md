
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)


# Place Autocomplete Widget

## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy
implementation 'com.mappls.sdk:place-widget:2.0.0'
~~~

### Add your API keys to the SDK

_Add your API keys to the SDK (in your application's onCreate() or before using map)_
#### Java
```java	
MapplsAccountManager.getInstance().setRestAPIKey(getRestAPIKey());  	
MapplsAccountManager.getInstance().setMapSDKKey(getMapSDKKey());  		
MapplsAccountManager.getInstance().setAtlasClientId(getAtlasClientId());  	
MapplsAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());  	
```	
#### Kotlin	
```kotlin	
MapplsAccountManager.getInstance().restAPIKey = getRestAPIKey()  	
MapplsAccountManager.getInstance().mapSDKKey = getMapSDKKey()  		
MapplsAccountManager.getInstance().atlasClientId = getAtlasClientId()  	
MapplsAccountManager.getInstance().atlasClientSecret = getAtlasClientSecret()	
```	


## Add PlaceAutocomplete Widget
There are two ways to implement `PlaceAutocomplete` widget:
- Using` PlaceAutocompleteFragment`
- Using `PlaceAutocompleteActivity`

## [PlaceAutocompleteFragment](#place-autocomplete-fragment)
 Add PlaceAutocompleteFragment in your activity
 #### Java
```java
PlaceAutocompleteFragment placeAutocompleteFragment = PlaceAutocompleteFragment.newInstance();

getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment.class.getSimpleName())  
        .commit();

                            //OR
PlaceAutocompleteFragment placeAutocompleteFragment = PlaceAutocompleteFragment.newInstance(placeOptions);

getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment.class.getSimpleName())  
        .commit();
```
#### Kotlin
~~~kotlin
val placeAutocompleteFragment: PlaceAutocompleteFragment = PlaceAutocompleteFragment.newInstance(placeOptions)
supportFragmentManager.beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment::class.java.simpleName)  
        .commit()
                               
                               //OR
val placeAutocompleteFragment: PlaceAutocompleteFragment = PlaceAutocompleteFragment.newInstance()
supportFragmentManager.beginTransaction().add(R.id.fragment_container, placeAutocompleteFragment, PlaceAutocompleteFragment::class.java.simpleName)  
        .commit()                               
~~~
You can use `PlaceOptions` to set the properties of the widget:
- `location(Point)`: set location around which your search will appear
- `filter(String)`: this parameter helps you restrict the result either by mentioning a bounded area or to certain mappls pin (6 digit code to any poi, locality, city, etc.), below mentioned are the both types:

  -   `filter`  = bounds: lat1, lng1; lat2, lng2 (latitude, longitude) {e.g. filter("bounds: 28.598882, 77.212407; 28.467375, 77.353513")
  -   `filter`  = cop: {mapplsPin} (string) {e.g. filter("cop:YMCZ0J")
- `historyCount(Integer)`: Maximum number of history results appear
- `zoom(Double)`: takes the zoom level of the current scope of the map (min: 4, max: 18).
- `saveHistory(Boolean)`: If it sets to `true` it shows the history selected data
- `pod(String)`: 1.  it takes in the place type code which helps in restricting the results to certain chosen type.**Below mentioned are the codes for the pod**

  -   AutoSuggestCriteria.POD_SUB_LOCALITY
  -   AutoSuggestCriteria.POD_LOCALITY
  -   AutoSuggestCriteria.POD_CITY
  -   AutoSuggestCriteria.POD_VILLAGE
  -   AutoSuggestCriteria.POD_SUB_DISTRICT
  -   AutoSuggestCriteria.POD_DISTRICT
  -   AutoSuggestCriteria.POD_STATE
  -   AutoSuggestCriteria.POD_SUB_SUB_LOCALITY
- `tokenizeAddress(Boolean)`: provides the different address attributes in a structured object.
- `backgroundColor(int)`: to set the background color of the widget
- `toolbarColor(int)`: to set the toolbar color of the widget.
- `hint(String)`: To set the hint on the Search view of the widget.
- `attributionVerticalAlignment(int)`: To set the vertical alignment for attribution. **Below mentioned are the values:**
  - PlaceOptions.GRAVITY_LEFT
  - PlaceOptions.GRAVITY_CENTER
  - PlaceOptions.GRAVITY_RIGHT
- `attributionHorizontalAlignment(int)`: To set the horizontal alignment for attribution. **Below mentioned are the values:**
  - PlaceOptions.GRAVITY_TOP
  - PlaceOptions.GRAVITY_BOTTOM'

- `logoSize(int)`: To set the logo size. **Below mentioned are the values:**
  - PlaceOptions.SIZE_SMALL
  - PlaceOptions.SIZE_MEDIUM
  - PlaceOptions.SIZE_LARGE

- `bridge(boolean)`:  initiates a bridge to be created to provide applicable nearby API searches in the `SuggestedSearchAtlas` response object. This optional parameter & its related effect on response is not applicable for regions apart from India (IND).

**Note: To set the view mode pass PlaceOptions.MODE_FULLSCREEN or PlaceOptions.MODE_CARDS in build(int) method of the PlaceOptions.Builder class**


To set the selected place use PlaceSelectionListener interface:
#### Java
~~~java
placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {  
    @Override  
    public void onPlaceSelected(ELocation eLocation) {  
       // Select place
    }  
  
    @Override  
    public void onCancel() {  
       //on click of back button
    }  
});
~~~
#### Kotlin
~~~kotlin
placeAutocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {  
    override fun onCancel() {  
          //on click of back button
    }  
  
    override fun onPlaceSelected(eLocation: ELocation?) {  
         // Select place
    }  
  
})
~~~

## [PlaceAutocompleteActivity](#place-autocomplete-activity)
Add PlaceAutocompleteActivity in your activity:
#### Java
~~~java
Intent placeAutocomplete = new PlaceAutocomplete.IntentBuilder()  
        .placeOptions(placeOptions)  
        .build(this);  
  
startActivityForResult(placeAutocomplete, REQUEST_CODE);
~~~
#### Kotlin
~~~kotlin
val placeAutocompleteActivity: Intent = PlaceAutocomplete.IntentBuilder()  
        .placeOptions(placeOptions)  
        .build(this)  
startActivityForResult(placeAutocompleteActivity, REQUEST_CODE)
~~~

To get the selected place:
#### Java
~~~java
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  
    super.onActivityResult(requestCode, resultCode, data);  
    if(requestCode == REQUEST_CODE) {  
        if(resultCode == Activity.RESULT_OK) {  
            if(data != null) {  
                ELocation eLocation = PlaceAutocomplete.getPlace(data);  
            }  
        }  
    }  
}
~~~

#### Kotlin
~~~kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  
    super.onActivityResult(requestCode, resultCode, data)  
    if(requestCode == REQUEST_CODE) {  
        if(resultCode == Activity.RESULT_OK) {  
            val eLocation :ELocation? = PlaceAutocomplete.getPlace(data)
        }        
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