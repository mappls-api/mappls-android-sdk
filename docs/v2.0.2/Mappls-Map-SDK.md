[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Mappls Map SDK
Mappls Map SDK is a powerful and versatile software development kit that enables developers to seamlessly integrate rich, interactive mapping and location-based services into their mobile and web applications. With comprehensive support for detailed maps, routing, geocoding, real-time traffic updates, and advanced spatial analytics, the SDK empowers businesses to create intuitive navigation experiences and location-aware solutions. Its customizable UI components, efficient performance, and extensive API set make it ideal for building applications across various industries including logistics, transportation, travel, and urban planning.

## [Version History]()

| Version | Last Updated | Author | Release Note                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | 
|---------|--------------| ---- |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| v9.0.2  | 02 Jun, 2026 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Added `lang` parameter in Autosuggest & Nearby Response </br> - Added Reverse Geocode Nearby   </br> - Added `validIndication` in `IntersectionLanes` in Routing Response  </br> - Fix issue related to `MapSnaphotter`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| v9.0.1  | 31 Mar, 2026 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Fix `_` support in package Name </br> - Remove `hyperLocal` & `zoom` from Autosugget Request. </br> - Remove `user` & `score` from Autosuggest Response. </br> - Added `searchType` in Autosuggest, Nearby & Reverse Geocode </br> - Added `bounds`, `bridge` and `refLocation` in Poi Along Route Request </br> - Added `suggestedSearchAtlas` in Poi Along Route Response  </br> - Added `actualGeoLevel` in Geocode Request </br> - Added `mapplsPinAdminType` in Geocode Response </br> - Remove `username`, `ignoreAutoExpand` & `includes` from Nearby Request </br> - Added `isVenue` & `venueDetails` in Reverse Geocode Response </br> - Remove `username` from Text Search Request. </br> - Added `tollPass` and `eType` in Routing request. </br> - Added `npToll` in Route Classes, `fromNodeIdx` and `toNodeIdx` in Leg Step & `consumption` in Annotation of Routing Response. </br>  - Fix request key `use_highways` from `use_highway` in Predective Route API </br> - Added Custom Style support |
| v9.0.0  | 06 Jun, 2025 | Mappls API Team ([MA](https://github.com/mdakram)) ([ST](https://github.com/saksham66)) | - Authentication and authorization mechanisms have been revised. </br> - Updated minimum Android version to 21. </br> - Added Support for 16 KB Page Sizes                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |



For more details of previous versions , [click here](./Version-History.md).


## [Add Mappls Map Dependency]()
- Add the Android Mappls Map Dependency in your app level `build.gradle` or `build.gradle.kts` file
    #### Kotlin (build.gradle.kts)
    ```kotlin
    // When using the BoM, you don't specify versions in Mappls library dependencies
    // Add the dependency for the Mappls SDK for Mappls Map SDK
    implementation("com.mappls.sdk:mappls-android-sdk")

    //Or Add Dependency with Version
    implementation("com.mappls.sdk:mappls-android-sdk:9.0.2")
    ```
    #### Groovy (build.gradle)
    ```groovy
    // When using the BoM, you don't specify versions in Mappls library dependencies
    implementation 'com.mappls.sdk:mappls-android-sdk'

    //Or Add Dependency with Version
    implementation("com.mappls.sdk:mappls-android-sdk:9.0.2")
    ```

    

## [Initialize Mappls Map SDK]()
- Initialize Mappls SDK either in Application class or before using Mappls SDK
    #### Kotlin
    ```kotlin
    Mappls.getInstance(this)
    ```
    #### Java
    ```java
    Mappls.getInstance(this);
    ```

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