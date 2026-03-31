[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Add Mappls SDK

Follow these steps to add the SDK to your project –

## [Add Mappls Repository]()

- Add the Mappls repository to your `settings.gradle` or `settings.gradle.kts` file:
    #### Kotlin (settings.gradle.kts)
    ```kotlin
    pluginManagement {
        repositories {
            maven(url = "https://maven.mappls.com/repository/mappls/")
        }
    }
    dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
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
    implementation(platform("com.mappls.sdk:mappls-bom:2.0.1"))

    // When using the BoM, you don't specify versions in Mappls library dependencies
    ```
    #### Groovy (build.gradle)
    ```groovy
    // Import the Mappls BoM
    implementation platform('com.mappls.sdk:mappls-bom:2.0.1')

    // When using the BoM, you don't specify versions in Mappls library dependencies

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
