# Precision Drop Venue SDK

## Introduction

The **Precision Drop Venue SDK** extends the Place Picker experience by enabling **high-accuracy location selection within complex venues and indoor environments**.

When enabled, the SDK can visualize venue structures and venue entry points, helping users accurately select locations within campuses, malls, airports, hospitals, office complexes, and other large venues.

### Key Benefits

- High-precision location selection within complex venues.
- Venue footprint visualization for better context.
- Venue entry point selection and snapping.
- Improved address and destination accuracy.
- Enhanced user experience for navigation and last-mile delivery use cases.
- Support for indoor and venue-aware location picking.

---

## Add the Dependency

Add the Place Widget dependency to your app-level Gradle file.

### Kotlin (`build.gradle.kts`)

```kotlin
// Using Mappls BoM
implementation("com.mappls.sdk:place-widget")

// Or specify a version explicitly
implementation("com.mappls.sdk:place-widget:3.0.2")
```

### Groovy (`build.gradle`)

```groovy
// Using Mappls BoM
implementation 'com.mappls.sdk:place-widget'

// Or specify a version explicitly
implementation 'com.mappls.sdk:place-widget:3.0.2'
```

---

# Add Place Picker

Configure and launch the Place Picker with Venue SDK options.

## Kotlin

```kotlin
val placePickerOptions = PlacePickerOptions.builder()
    .venueFootprintsEnabled(true)
    .venueAppearanceFillColor("#7782E3")
    .venueAppearanceFillOpacity(0.35)
    .venueAppearanceStrokeColor("#7782E3")
    .venueAppearanceStrokeWidth(2)
    .venueAppearanceStrokeOpacity(0.5)

    .showVenueEntryCoordinate(true)
    .venueEntryCoordinateCircleColor("#FFB84D")
    .venueEntryCoordinateCircleStrokeColor("#DE9424")
    .venueEntryCoordinateCircleRadius(8.0f)
    .venueEntryCoordinateCircleStrokeWidth(2.0f)
    .venueEntryCoordinateSnapEnable(true)
    .venueEntryCoordinateSnappingRadius(50)
    .venueEntryCoordinateSnappingZoomEnable(true)
    .build()


val intent = PlacePicker.IntentBuilder()
    .placeOptions(placePickerOptions)
    .build(this)

startActivityForResult(intent, 101)
```

## Java

```java
PlacePickerOptions placePickerOptions = PlacePickerOptions.builder()
        .venueFootprintsEnabled(true)
        .venueAppearanceFillColor("#7782E3")
        .venueAppearanceFillOpacity(0.35)
        .venueAppearanceStrokeColor("#7782E3")
        .venueAppearanceStrokeWidth(2)
        .venueAppearanceStrokeOpacity(0.5)

        .showVenueEntryCoordinate(true)
        .venueEntryCoordinateCircleColor("#FFB84D")
        .venueEntryCoordinateCircleStrokeColor("#DE9424")
        .venueEntryCoordinateCircleRadius(8.0f)
        .venueEntryCoordinateCircleStrokeWidth(2.0f)
        .venueEntryCoordinateSnapEnable(true)
        .venueEntryCoordinateSnappingRadius(50)
        .venueEntryCoordinateSnappingZoomEnable(true)
        .build();

Intent intent = new PlacePicker.IntentBuilder()
        .placeOptions(placePickerOptions)
        .build(this);

startActivityForResult(intent, 101);
```

---

# Premium Features
> The Place Picker widget has the capability to showcase certain premium features if they are provisioned within your project, like:

---

## Venue Footprint Highlighting

When a selected point falls within a mapped venue, the Place Picker can automatically highlight the corresponding venue footprint.

This provides users with clear visual feedback about the venue area being selected.

### Benefits

- Improves location selection accuracy.
- Helps users identify the exact venue being selected.
- Provides visual confirmation of selected destinations.
- Supports large campuses and multi-structure complexes.


---

# Venue Footprint Configuration

These properties control the visibility and appearance of venue footprints displayed in the Place Picker.

| Property | Description |
|-----------|-------------|
| `venueFootprintsEnabled(Boolean)` | Enables or disables venue footprint visualization around the selected location. |
| `venueAppearanceFillColor(String)` | Sets the fill color of the venue footprint polygon (for example: `#7782E3`). |
| `venueAppearanceFillOpacity(Double)` | Sets the fill transparency. Values range from `0.0` (fully transparent) to `1.0` (fully opaque). |
| `venueAppearanceStrokeColor(String)` | Sets the border color of the venue footprint. |
| `venueAppearanceStrokeWidth(Integer)` | Sets the border width in pixels. |
| `venueAppearanceStrokeOpacity(Double)` | Sets the border transparency. Values range from `0.0` (fully transparent) to `1.0` (fully opaque). |

### Example

```kotlin
.venueFootprintsEnabled(true)
    .venueAppearanceFillColor("#7782E3")
    .venueAppearanceFillOpacity(0.35)
    .venueAppearanceStrokeColor("#7782E3")
    .venueAppearanceStrokeWidth(2)
    .venueAppearanceStrokeOpacity(0.5)
```

---

---

# Venue Entry Coordinate Configuration

Venue entry coordinates help users select precise entry points within large venues such as malls, airports, hospitals, campuses, and office complexes.

| Property | Description |
|-----------|-------------|
| `showVenueEntryCoordinate(Boolean)` | Shows or hides the venue entry coordinate marker. |
| `venueEntryCoordinateCircleColor(String)` | Fill color of the venue entry coordinate circle. |
| `venueEntryCoordinateCircleStrokeColor(String)` | Border color of the venue entry coordinate circle. |
| `venueEntryCoordinateCircleRadius(Float)` | Radius of the entry coordinate circle. |
| `venueEntryCoordinateCircleStrokeWidth(Float)` | Border width of the entry coordinate circle. |
| `venueEntryCoordinateSnapEnable(Boolean)` | Enables snapping the selected point to the nearest venue entry coordinate. |
| `venueEntryCoordinateSnappingRadius(Integer)` | Radius in meters used for venue entry coordinate snapping. |
| `venueEntryCoordinateSnappingZoomEnable(Boolean)` | Enables snapping based on zoom level. |

### Example

```kotlin
.showVenueEntryCoordinate(true)
.venueEntryCoordinateCircleColor("#FFB84D")
.venueEntryCoordinateCircleStrokeColor("#DE9424")
.venueEntryCoordinateCircleRadius(8.0f)
.venueEntryCoordinateCircleStrokeWidth(2.0f)
.venueEntryCoordinateSnapEnable(true)
.venueEntryCoordinateSnappingRadius(50)
.venueEntryCoordinateSnappingZoomEnable(true)
```

---

# Complete Venue Configuration Example

```kotlin
val placePickerOptions = PlacePickerOptions.builder()
    .venueFootprintsEnabled(true)
    .venueAppearanceFillColor("#7782E3")
    .venueAppearanceFillOpacity(0.35)
    .venueAppearanceStrokeColor("#7782E3")
    .venueAppearanceStrokeWidth(2)
    .venueAppearanceStrokeOpacity(0.5)

    .showVenueEntryCoordinate(true)
    .venueEntryCoordinateCircleColor("#FFB84D")
    .venueEntryCoordinateCircleStrokeColor("#DE9424")
    .venueEntryCoordinateCircleRadius(8.0f)
    .venueEntryCoordinateCircleStrokeWidth(2.0f)
    .venueEntryCoordinateSnapEnable(true)
    .venueEntryCoordinateSnappingRadius(50)
    .venueEntryCoordinateSnappingZoomEnable(true)

    .build()
```

---

# Retrieving the Selected Place

After the user selects a location and returns from the Place Picker, retrieve the selected `Place` object.

## Kotlin

```kotlin
override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == 101 &&
        resultCode == Activity.RESULT_OK
    ) {
        val place: Place? = PlacePicker.getPlace(data!!)
    }
}
```

## Java

```java
@Override
protected void onActivityResult(
        int requestCode,
        int resultCode,
        @Nullable Intent data
) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 101 &&
            resultCode == RESULT_OK) {

        Place place = PlacePicker.getPlace(data);
    }
}
```

---

# Feature Summary

| Feature | Description |
|----------|-------------|
| Venue-Aware Location Selection | Select precise locations within large venues and campuses. |
| Venue Footprint Highlighting | Highlights mapped venue boundaries around the selected location. |
| Venue Entry Coordinates | Displays and selects precise venue entry points. |
| Coordinate Snapping | Snaps selection to the nearest mapped venue entry coordinate. |
| Custom Styling | Configure venue colors, opacity, stroke width, and entry marker appearance. |
| Precision Venue Selection | Improves destination accuracy inside complex venues. |
---

<br><br>

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

