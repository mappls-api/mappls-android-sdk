

[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)


# Place Autocomplete Widget

## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy
implementation 'com.mappls.sdk:place-widget:2.0.3'
~~~

### Add your API keys to the SDK

_Add your API keys to the SDK (in your application's onCreate() or before using map)_
#### Java
~~~java	
MapplsAccountManager.getInstance().setRestAPIKey(getRestAPIKey());  	
MapplsAccountManager.getInstance().setMapSDKKey(getMapSDKKey());  		
MapplsAccountManager.getInstance().setAtlasClientId(getAtlasClientId());  	
MapplsAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());  	
~~~
#### Kotlin
~~~kotlin	
MapplsAccountManager.getInstance().restAPIKey = getRestAPIKey()  	
MapplsAccountManager.getInstance().mapSDKKey = getMapSDKKey()  		
MapplsAccountManager.getInstance().atlasClientId = getAtlasClientId()  	
MapplsAccountManager.getInstance().atlasClientSecret = getAtlasClientSecret()	
~~~


## Add PlaceAutocomplete Widget
There are two ways to implement `PlaceAutocomplete` widget:
- Using` PlaceAutocompleteFragment`
- Using `PlaceAutocompleteActivity`

## [PlaceAutocompleteFragment]()
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
- `debounce(Integer)`: This means that the the search apis is hit only debounce value. This is made to control the api hits from SDK parameter. It takes values in milliseconds. Minimum value is 0 and Maximum value is 1500.

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

## [PlaceAutocompleteActivity]()
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
