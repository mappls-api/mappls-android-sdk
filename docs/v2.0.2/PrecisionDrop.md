
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Precision Drop SDK

##  Introduction
The **Precision Drop SDK** enhances the Place Picker experience by enabling **high-accuracy location selection** with support for **building footprint visualization** and **building entry-point snapping**.

When premium features are enabled for your project, users can:

- Highlight building boundaries when selecting a location.
- Snap selected coordinates to the nearest building entrance.
- Customize the appearance of building footprints and entry-point markers.
- Improve last-mile delivery, navigation, and address selection accuracy.

---
## Add the Dependency
Add the Place Widget dependency to your app-level Gradle file.

### Kotlin (`build.gradle.kts`)

```kotlin
// Using Mappls BoM
implementation("com.mappls.sdk:place-widget")

// Or specify a version explicitly
implementation("com.mappls.sdk:place-widget:3.0.1")
```

### Groovy (`build.gradle`)

```groovy
// Using Mappls BoM
implementation 'com.mappls.sdk:place-widget'

// Or specify a version explicitly
implementation 'com.mappls.sdk:place-widget:3.0.1'
```

---

## Add Place Picker

Configure and launch the Place Picker with Precision Drop options.

## Kotlin

```kotlin
val placePickerOptions = PlacePickerOptions.builder()
    .buildingFootprintsEnabled(false)
    .buildingAppearanceFillColor("#FFFF44")
    .buildingAppearanceFillOpacity(0.3)
    .buildingAppearanceStrokeOpacity(1.0)
    .buildingAppearanceStrokeWidth(2)
    .buildingAppearanceStrokeColor("#FFFF44")
    .entryCoordinateCircleColor("#0000FF")
    .entryCoordinateSnapEnable(false)
    .entryCoordinateSnappingRadius(50.0)
    .showEntryCoordinate(false)
    .entryCoordinateCircleRadius(8)
    .build()

val intent = PlacePicker.IntentBuilder()
    .placeOptions(placePickerOptions)
    .build(this)

startActivityForResult(intent, 101)
```

## Java

```java
PlacePickerOptions placePickerOptions = PlacePickerOptions.builder()
        .buildingFootprintsEnabled(false)
        .buildingAppearanceFillColor("#FFFF44")
        .buildingAppearanceFillOpacity(0.3)
        .buildingAppearanceStrokeOpacity(1.0)
        .buildingAppearanceStrokeWidth(2)
        .buildingAppearanceStrokeColor("#FFFF44")
        .entryCoordinateCircleColor("#0000FF")
        .entryCoordinateSnapEnable(false)
        .entryCoordinateSnappingRadius(50.0)
        .showEntryCoordinate(false)
        .entryCoordinateCircleRadius(8)
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

## Building Footprint Highlighting

When a selected location lies within a mapped building, the Place Picker can automatically highlight that building footprint on the map.

### Benefits

- Helps users identify the exact building being selected.
- Improves address accuracy.
- Enhances delivery and navigation workflows.
- Provides visual confirmation of the selected building.

---

## Entry Coordinate Snapping

A building's geometric center is often not the most practical access point.

With **Entry Coordinate Snapping**, the selected coordinate can automatically snap to the nearest valid building entrance or access point.

### Benefits

- Improves last-mile delivery accuracy.
- Provides realistic navigation destinations.
- Enhances ride-hailing pickup and drop-off experiences.
- Supports logistics and fleet management use cases.

---

# Configuration Properties

## Building Footprint Properties

These properties control the visibility and appearance of building footprints.

| Property | Description |
|-----------|-------------|
| `buildingFootprintsEnabled(Boolean)` | Enables or disables building footprint visualization around the selected location. |
| `buildingAppearanceFillColor(String)` | Sets the fill color of the building footprint polygon (for example: `#4285F4`). |
| `buildingAppearanceFillOpacity(Double)` | Sets the fill transparency. Values range from `0.0` (fully transparent) to `1.0` (fully opaque). |
| `buildingAppearanceStrokeColor(String)` | Sets the border color of the building footprint. |
| `buildingAppearanceStrokeWidth(Integer)` | Sets the border width in pixels. |
| `buildingAppearanceStrokeOpacity(Double)` | Sets the border transparency. Values range from `0.0` (fully transparent) to `1.0` (fully opaque). |

### Example

```kotlin
.buildingFootprintsEnabled(true)
.buildingAppearanceFillColor("#4285F4")
.buildingAppearanceFillOpacity(0.3)
.buildingAppearanceStrokeColor("#1A73E8")
.buildingAppearanceStrokeWidth(2)
.buildingAppearanceStrokeOpacity(1.0)
```

---

## Entry Coordinate Snapping Properties

These properties control building entry-point snapping and visualization.

| Property | Description |
|-----------|-------------|
| `entryCoordinateSnapEnable(Boolean)` | Enables snapping to the nearest valid building entry/access point. |
| `showEntryCoordinate(Boolean)` | Controls whether the snapped entry coordinate marker is displayed on the map. |
| `entryCoordinateCircleColor(String)` | Sets the color of the entry coordinate marker. |
| `entryCoordinateCircleRadius(Integer)` | Sets the radius of the entry coordinate marker in pixels. |
| `entryCoordinateSnappingRadius(Double)` | Defines the maximum search radius (in meters) within which a valid entry coordinate will be identified. |

### Example

```kotlin
.entryCoordinateSnapEnable(true)
.showEntryCoordinate(true)
.entryCoordinateCircleColor("#FF0000")
.entryCoordinateCircleRadius(8)
.entryCoordinateSnappingRadius(50.0)
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
| Building Footprints | Highlights the building corresponding to the selected location. |
| Custom Styling | Configure building fill color, border color, opacity, and stroke width. |
| Entry Coordinate Snapping | Automatically snaps selected coordinates to a building entrance. |
| Entry Marker Visualization | Display and customize the snapped entry point marker. |
| Precision Location Selection | Improves location accuracy for delivery, logistics, and navigation use cases. |

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