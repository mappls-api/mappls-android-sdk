[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls Feedback Android SDK

## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy
implementation 'com.mappls.sdk:feedback-ui:3.0.0'
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

## Add Feedback Widget
There are two ways to implement `Feedback UI` widget:
- Using` FeedbackFragment`
- Using `FeedbackActivity`

## [Using FeedbackFragment]()
Add FeedbackFragment in your activity

#### Java
~~~java
FeedbackOptions feedbackOption =FeedbackOptions.builder()
        .latitude(<Latitude>)
        .longitude(<Longitude>)
        .build();
FeedbackFragment feedbackFragment = FeedbackFragment.newInstance(feedbackOption);

getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, feedbackFragment, FeedbackFragment.class.getSimpleName())  
        .commit();
~~~

#### Kotlin
~~~kotlin
val feedbackOption: FeedbackOptions = FeedbackOptions.builder()
   .latitude(< Latitude >)
   .longitude(< Longitude >)
   .build()

val feedbackFragment: FeedbackFragment = FeedbackFragment.newInstance(feedbackOption)
supportFragmentManager.beginTransaction().add(R.id.fragment_container, feedbackFragment, FeedbackFragment::class.java.simpleName)  
        .commit()  
~~~

To get the event of Report Submit use `FeedbackCallback` interface:

#### Java
~~~java
feedbackFragment.setFeedbackCallback(new FeedbackCallback() {
    @Override
    public void onSubmittedReport() {
        // On Submitted Report Succssfully
    }

    @Override
    public void onCancel() {
        //on click of Cancel button
    }
});
~~~
#### Kotlin
~~~kotlin
feedbackFragment.setFeedbackCallback(object : FeedbackCallback {
  override fun onCancel() {
    //on click of Cancel button
  }

  override fun onSubmittedReport() {
    // On Submitted Report Succssfully
  }
})
~~~

## [Using FeedbackActivity]()
Add FeedbackActivity in your activity:

#### Java
~~~java
FeedbackOptions builder=FeedbackOptions.builder()
        .placeName(<name of place>)
        .latitude(<Latitude>)
        .longitude(<Longitude>)
        .build();
Intent intent = new MapplsFeedback.IntentBuilder().feedbackOptions(feedbackOptions)
                        .build(this);
startActivityForResult(intent, REQUEST_CODE);
~~~

#### Kotlin

~~~kotlin
val builder: FeedbackOptions = FeedbackOptions.builder()
   .placeName(< name of place >)
   .latitude(< Latitude >)
   .longitude(< Longitude >)
.build()

val intent = MapplsFeedback.IntentBuilder().feedbackOptions(feedbackOptions)
         .build(this);
startActivityForResult(intent, REQUEST_CODE);
~~~

To get the event of Report Submit:
#### Java
~~~java
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == REQUEST_CODE) {
        if(resultCode == Activity.RESULT_OK) {
            //Report Submit Successfully
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
            //Report Submit Successfully
    }
  }
}
~~~

You can use `FeedbackOptions` to set the properties of the widget:

#### Mandatory Parameters

1. `mapplsPin(string)`: Mappls Pin of the place.The 6-digit alphanumeric code for any location.
2. `latitude(Double)` : Latitude of the user’s location.
3. `longitude(Double)`: longitude of the user’s location.
** Either the eloc or latitude & Longitude will be served. **


#### Optional Parameters

1. `placeName(string)` : Name of the place where the event is taking place. It should be derived on
   the basis of Mappls Pin and coordinates.
2. `theme(Integer)`: To change the theme of the UI. Below are the available value:
    - FeedbackOptions.THEME_DAY (Default)
    - FeedbackOptions.THEME_NIGHT
3. `showStepProgress(boolean)`: To show/hide Circular Progress of  steps
4. `showCompletedProgress(boolean)`: To show/hide Completed Progress text
5. `utc(Long)`: Date time in unix timestamp format.
6. `flag(Integer)`: If navroute is active then 1 else 0.
7. `expiry(Long)`: Date time in unix timestamp format to set expiry for the report.
8. `accuracy(Integer)`: Accuracy of user's location.
9. `altitude(Double)`: Altitude of the user’s location, in meters.
10. `bearing(Integer)`: Bearing of the user’s location, in degrees.
11. `quality(String)`: Quality of user's location.
12. `speed(String)`: User's speed in kilometers.

<br>

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