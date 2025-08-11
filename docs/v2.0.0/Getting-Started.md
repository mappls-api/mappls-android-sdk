[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Getting Started

The Mappls Maps SDK for Android empowers you to seamlessly integrate powerful, interactive maps and location services into your Android applications. Compatible with API level 21 and above, the SDK gives you access to a robust suite of mapping features and tools designed to deliver a smooth, responsive, and customizable user experience.

With support for custom map tiles, multiple layer options, and a rich set of controls and native gestures, you can build highly dynamic, map-based applications tailored to your users’ needs. The SDK efficiently manages the downloading and rendering of map tiles, letting you focus on crafting innovative and engaging solutions for our customers.



## API Usage

To use the Mappls Maps SDK, you’ll need to obtain a valid set of license keys (available [here](https://apis.mappls.com/console/)) and adhere to the API Terms and Conditions. **Please note that as per the terms, removing or obscuring the Mappls logo and copyright notice from your application is strictly prohibited.**

Your SDK usage limits are detailed on your user [dashboard](https://apis.mappls.com/console), and are shared across all platforms. This means that API requests made from your web, Android, or iOS applications are counted collectively toward your daily usage quota.

## Project Prerequisites
Make sure that your project meets these requirement
- Target API level 21 (Lollipop) or higher
- Uses Gradle version (v8.3.2 or later)
- Uses Java version (v17 or later)
- compileSdkVersion 35 or later
- Uses Kotlin version (v1.8 or later)

## [Authentication](#Authentication)

To initialize and authenticate any **` Mappls SDK `**, you must include **` i.conf `**  and **` i.olf `** files in your project bundle:

You can download the required files from the **[Auth Console](http://auth.mappls.com/console/)**


### How to create an app on Mappls Console
![Mappls Console - How to create apps](/images/TestApp_Android_1.gif)


### How to download config files from App from Console
![Mappls Console - How to create apps](/images/TestApp_Android_2.gif)



### Setup Your Project and Add Mappls Map
## [Add Mappls Repository]()

- Add the Mappls repository to your `settings.gradle` or `settings.gradle.kts` file:
    #### Kotlin (settings.gradle.kts)
    ```kotlin
    pluginManagement {
        repositories {
            maven(url = "https://maven.mappls.com/repository/mappls/")
        }
    }
    ```
    #### Groovy (settings.gradle)
    ```groovy  
    pluginManagement {
        repositories {
            maven {
                url 'https://maven.mappls.com/repository/mappls/'
            }
        }
    }
    dependencyResolutionManagement {  
    repositories {  
            google()  
            mavenCentral()  
            maven {  
                url 'https://maven.mappls.com/repository/mappls/'  
            }  
    }  
    } 
    ```

## [Adding Mappls Configuration file]()

- Download Configuration files for your app (associated with <b>Package Name and Signing Certificate SHA-256</b>)
- Add Configuration files (`<appId>.a.olf` and `<appId>.a.conf`) into the module app-level root directory of your app
- Add Mappls Service Plugin as a dependency in your project level `build.gradle` or `build.gradle.kts`
    #### Kotlin (build.gradle.kts)
    ```kotlin
    buildscript {
        dependencies {
            classpath("com.mappls.services:mappls-services:1.0.0")
            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }
    }
    ```
    #### Groovy (build.gradle)
    ```groovy
    buildscript {
        dependencies {
            classpath 'com.mappls.services:mappls-services:1.0.0'
            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }
    }
    ```
- Add Mappls Services Plugin in your app-level `build.gradle` or `build.gradle.kts` file
    #### Kotlin (build.gradle.kts)
    ```kotlin
    plugins {
        id("com.android.application")
        // Add the Mappls services Gradle plugin    
        id("com.mappls.services.android")
    }
    ```
    #### Groovy (build.gradle)
    ```groovy
    plugins {
        id 'com.android.application'
        // Add the Mappls services Gradle plugin
        id 'com.mappls.services.android'
    }
    ```

## [Import Mappls BoM]()
- Add the Android Mappls Dependency in your app level `build.gradle` or `build.gradle.kts` file
    #### Kotlin (build.gradle.kts)
    ```kotlin
    // Import the Mappls BoM
    implementation(platform("com.mappls.sdk:mappls-bom:2.0.0"))

    // When using the BoM, you don't specify versions in Mappls library dependencies
    ```
    #### Groovy (build.gradle)
    ```groovy
    // Import the Mappls BoM
    implementation platform('com.mappls.sdk:mappls-bom:2.0.0')

    // When using the BoM, you don't specify versions in Mappls library dependencies

    ```

<br><br><br>

## Our many happy customers

![](https://www.mapmyindia.com/api/img/logos1/PhonePe.png)![](https://about.mappls.com/images/client_logo/amazon-a.svg)![](https://www.mapmyindia.com/api/img/logos1/delhivery.png)![](https://www.mapmyindia.com/api/img/logos1/hdfc.png)![](https://www.mapmyindia.com/api/img/logos1/TVS.png)![](https://www.mapmyindia.com/api/img/logos1/Paytm.png)![](https://about.mappls.com/images/client_logo/mcdonalds-01.svg)![](https://www.mapmyindia.com/api/img/logos1/ICICI-Pru.png)![](https://about.mappls.com/images/client_logo/honda.svg)![](https://www.mapmyindia.com/api/img/logos1/TTSL.png)![](https://www.mapmyindia.com/api/img/logos1/Novire.png)![](https://about.mappls.com/images/client_logo/airtel.svg)![](https://www.mapmyindia.com/api/img/logos1/Sensel.png)![](https://www.mapmyindia.com/api/img/logos1/TATA-MOTORS.png)![](https://www.mapmyindia.com/api/img/logos1/Wipro.png)![](https://about.mappls.com/api/images_page/grofers.svg)  [<img src="https://about.mappls.com/images/original/avis_logo-01.svg" height="30"/>]()    [<img src="https://about.mappls.com/images/client_logo/CBDT.svg" height="60"/>]()   [<img src="https://about.mappls.com/images/client_logo/finance.png" height="60"/> </p>]()

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




<div align="center">@ Copyright 2025 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
