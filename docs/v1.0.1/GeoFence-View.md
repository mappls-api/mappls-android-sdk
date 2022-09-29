
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Mappls GeoFence View

## Introduction

A ready to use UI Widget to create/edit GeoFence in an Android application.

## Add the dependency
Add below dependency in app level build.gradle file
~~~groovy
implementation 'com.mappls.sdk:geofence-ui:1.0.0'
~~~

## Add GeoFence View to your application
You can add GeoFence view in two ways

1. Using XML
2. Using Java

### Using XML

~~~xml
<com.mappls.sdk.geofence.ui.views.GeoFenceView  
  android:id="@+id/geo_fence_view" 
  android:layout_width="match_parent"  
  android:layout_height="match_parent" />
~~~
We can set the following properties:

1. **mappls_geoFence_circleButtonDrawable:** To change the circle Button Drawable

2. **mappls_geoFence_polygonButtonDrawable:** To change the polygon selector button drawable

3. **mappls_geoFence_circleFillColor:** To change Circle Fill colors

4. **mappls_geoFence_circleFillOutlineColor:** To change circle Outline color

5. **mappls_geoFence_circleCenterDrawable:** To change the image of the center of circle

6. **mappls_geoFence_handToolDrawable:** To change action button icon which is visible when polygon is selected

7. **mappls_geoFence_polygonMidPointDrawable:**  To change midpoint icon of the polygon boundaries

8. **mappls_geoFence_polygonEdgeDrawable:** To change polygon corners icon

9. **mappls_geoFence_polygonIntersectionDrawable:** To change polygon intersection point icon of polygon

10. **mappls_geoFence_polygonFillColor:** To change fill color of polygon

11. **mappls_geoFence_polygonFillOutlineColor:** To change outline color of polygon

12. **mappls_geoFence_seekBarThumbDrawable:** To change seekbar thumb icon

13. **mappls_geoFence_seekBarProgressPrimaryColor:** To change seekbar Progress bar primary color

14. **mappls_geoFence_seekBarProgressSecondaryColor:** To change seekbar Progress bar secondary color

15. **mappls_geoFence_seekBarProgressRadius:** To change seekbar corner radius

16. **mappls_geoFence_deleteButtonDrawable:** To change drawable of Delete button icon

17. **mappls_geoFence_showSeekbar**: If its value is false than it hides the seekbar and if its true than it show seekbar

18. **mappls_geoFence_showToolView**: If its value is false than it hides the geofence shape toggle buttons and if its true than it show geofence shape toggle buttons

19. **mappls_geoFence_showActionButton**: If its value is false than it hides the polygon draw enable/disable button and if its true than it show polygon draw enable/disable button

20. **mappls_geoFence_showUI:** If its value is false than it hide all control buttons and if its true than it show all control buttons
21. **mappls_geoFence_showDeleteButton:** If its value is false than it hides polygon delete button.
22. **mappls_geoFence_polygonDrawingBackgroundColor:** To change the color of Polygon drawing board color.
23. **mappls_geoFence_showPolygonMidPointIcon:** To hide polygon mid points icon
24. **mappls_geoFence_polygonOutlineWidth:** To set the polygon outline width
25. **mappls_geoFence_circleOutlineWidth:** To change the circle outline width
26. **mappls_geoFence_polygonDrawingLineColor:** To set the polygon sketch drawing line.
27. **mappls_geoFence_draggingLineColor:** To change the dragging line of Polygon edges and circle radius changing line.
28. **mappls_geoFence_minRadius:** To set minimum radius of circle.
29. **mappls_geoFence_maxRadius:** To set maximum radius of circle
30. **mappls_geoFence_radiusInterval:** To set the step size of radius changing
31. **mappls_geoFence_showPolygonCentreIcon:** To show/hide centre point of the Polygon
32. **mappls_geoFence_polygonCentreDrawable:** To set the drawable for centre point of the Polygon

### Using Java
~~~java
//To create with default ui and styles
geoFenceView = GeoFenceView(this)

//Or If you want to change the properties of view
geoFenceView = GeoFenceView(this, GeoFenceOptions.builder().showUI(false).build())
~~~

GeoFenceOptions has following methods to change the Properties:

1. **cicleButtonDrawable(int):** To change the circle Button drawable

2. **polygonButtonDrawable(int):** To change the polygon selector button drawable

3. **circleFillColor(int):** To change Circle Fill colors

4. **circleFillOutlineColor(int):** To change circle Outline color

5.  **circleCentreDrawable(int):** To change the image of the center of circle

6. **actionButtonDrawable(int):** To change action button icon which is visible when polygon is selected

7. **polygonMidPointDrawable(int):**  To change midpoint icon of the polygon boundaries

8. **polygonEdgeDrawable(int):** To change polygon corners icon

9. **polygonIntersectionDrawable(int):** To change polygon intersection point icon of polygon

10. **polygonFillColor(int):** To change fill color of polygon

11. **polygonFillOutlineColor(int):** To change outline color of polygon

12. **seekbarThumbDrawable(int):** To change seekbar thumb icon

13. **seekbarPrimaryColor(int):** To change seekbar Progress bar primary color

14. **seekbarSecondaryColor(int):** To change seekbar Progress bar secondary color

15. **seekbarCornerRadius(int):** To change seekbar corner radius

16. **deleteButtonDrawable(int):** To change drawable of Delete button icon

17. **showSeekBar(boolean)**: If its value is false than it hides the seekbar and if its true than it show seekbar

18. **showToolsButton(boolean)**: If its value is false than it hides the geofence shape toggle buttons and if its true than it show geofence shape toggle buttons

19. **showActionButton(boolean)**: If its value is false than it hides the polygon draw enable/disable button and if its true than it show polygon draw enable/disable button

20. **showUI(boolean):** If its value is false than it hide all control buttons and if its true than it show all control buttons
21. **showDeleteButton(boolean):** If its value is false than it hides polygon delete button.
22. **polygonDrawingBackgroundColor(int):** To change the color of Polygon drawing board color.
23. **showPolygonMidPointIcon(boolean):** To hide polygon mid points icon
24. **polygonOutlineWidth(Float):** To set the polygon outline width
25. **circleOutlineWidth(Float):** To change the circle outline width
26. **polygonDrawingLineColor(int):** To set the polygon sketch drawing line.
27. **draggingLineColor(int):** To change the dragging line of Polygon edges and circle radius changing line.
28. **minRadius(int):** To set minimum radius of circle.
29. **maxRadius(int):** To set maximum radius of circle
30. **radiusInterval(int):** To set the step size of radius changing
31. **showPolygonCentreIcon(boolean):** To show/hide centre point of the Polygon
32. **polygonCentreDrawable(int):** To set the drawable for centre point of the Polygon

## Initialise geofence view
~~~kotlin
  override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
  
  
        setContentView(R.layout.activity_home)  
        geoFenceView = findViewById<GeoFenceView>(R.id.geo_fence_view)  

	    geoFenceView.onCreate(savedInstanceState)  
        geoFenceView.setGeoFenceViewCallback(this)  
        val geoFence = GeoFence()  
        geoFence.geoFenceType = GeoFenceType.CIRCLE  
        geoFence.circleCenter = LatLng(25.4358, 81.8463)  
        geoFence.circleRadius = 200  
        geoFenceView.geoFence = geoFence  
  
     
    } 
      
    override fun onResume() {  
        super.onResume()  
        geoFenceView.onResume()  
    }  
  
    override fun onStop() {  
        super.onStop()  
        geoFenceView.onStop()  
    }  
  
    override fun onStart() {  
        super.onStart()  
        geoFenceView.onStart()  
    }  
  
    override fun onLowMemory() {  
        super.onLowMemory()  
        geoFenceView.onLowMemory()  
    }  
  
    override fun onDestroy() {  
        super.onDestroy()  
        geoFenceView.onDestroy()  
    }  
  
    override fun onSaveInstanceState(outState: Bundle) {  
        super.onSaveInstanceState(outState)  
        geoFenceView.onSaveInstanceState(outState)  
    }
~~~

## Callbacks getting from GeoFence View

Implement from GeoFenceViewCallback interface
1. **void onGeoFenceReady(MapplsMap mapplsMap):** When map is loaded successfully
2. **void geoFenceType(GeoFenceType geofenceType):** When toggle between geofence type
3. **void onCircleRadiusChanging(int radius):**  When radius of the circle changes
5. **void onUpdateGeoFence(GeoFence geoFence):** On update geofence data
6. **void hasIntersectionPoints():** When Polygon is intersecting

## To change the circle Radius
On circle radius changing:
~~~kotlin
geoFenceView.onRadiusChange(progress)
~~~
On circle radius change finish
~~~kotlin
geoFenceView.radiusChangeFinish(seekBar?.progress!!)
~~~

## Toggle draw polygon enable/disable
~~~kotlin
geoFenceView.enablePolygonDrawing(isChecked)
~~~
## Toggle Geofence type
To draw circle Geofence
~~~kotlin
geoFenceView.drawCircleGeoFence()
~~~

To draw polygon Geofence
~~~kotlin
geoFenceView.drawPolygonGeofence()
~~~

To draw Rectangle Geofence
~~~kotlin
geoFenceView.drawQuadrilateralGeofence()
~~~
## Restrict number of points in a polygon

~~~java
geofenceView.setMaxAllowedEdgesInAPolygon(12);
~~~

## Callback when user tries to add more than restricted points in a polygon

~~~java
geoFenceView.setOnPolygonReachedMaxPointListener(new OnPolygonReachedMaxPointListener() {
            @Override
            public void onReachedMaxPoint() {
                   //show a message to the user
            }
        })
~~~

## Auto correct self intersecting polygon

~~~java
geofenceView.simplifyWhenIntersectingPolygonDetected(true);
~~~

## Set Normal/Rectangle Polygon
~~~java
geoFence.setPolygonType(PolygonType.QUADRILATERAL);
             //Normal
geoFence.setPolygonType(PolygonType.NORMAL);
~~~

## get center of plotted polygon

~~~java
geofence.getPolygonCentre()
~~~


## get Polygon points in clockwise/anti-clockwise directions

~~~java
geoFenceView.convertPointsToClockWise(Orientation.CLOCKWISE);
~~~

## To set Camera Padding for zoom on change geofence
~~~java
geoFenceView.setCameraPadding(left, top, right, bottom);
~~~
## To disable zoom on change geofence
~~~java
geoFenceView.shouldAllowAutoZoom(false)
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




<div align="center">@ Copyright 2022 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
