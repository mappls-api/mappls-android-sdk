

[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Mappls SDK – Error Code Reference

This document outlines possible error codes and exceptions encountered while using the **Mappls Android SDKs**. Use this guide for debugging integration issues across different SDK modules.



## Core SDK Errors

| Condition | Exception/Error Message |
|----------|--------------------------|
| Mappls Plugin is not applied in `build.gradle` | `Please apply plugin "com.mappls.services.android" in your app/build.gradle` |
| `.olf` file is invalid or corrupted | `Please add a valid .olf configuration file.` |
| Package name or app fingerprint mismatch in `.olf` file | `You are using Invalid Configuration. Please check your Package Name / App Fingerprint & download it from the developer console again.` |
| `.conf` config file is invalid | `Please add a valid .conf configuration file.` |
| Static key is not found (improper app setup or missing plugin) | `Error Code: 1` </br> `Error Message: Static Key Not Found` </br> _No HTTP status code associated_ |
| Invalid payload while creating the payload JSON | `Error Code: 2` </br> `Error Message: Invalid Payload` </br> _No HTTP status code associated_ |
| Missing config data, `appId`, or `payloadPubKey` | `Error Code: 3` </br> `Error Message: Invalid Config` </br> _No HTTP status code associated_ |
| Payload encryption failure | `Error Code: 3` </br> `Error Message: Invalid Payload` </br> _No HTTP status code associated_ |
| Auth1 Token (Bearer accessToken) missing or expired during API call | `Error Code: 5` </br> `Error Message: Authentication Failed` </br> _No HTTP status code associated_ |
| Auth1 Token usage attempted but config lacks access token | `Error Code: 6` </br> `Error Message: This authentication is not supported` </br> _No HTTP status code associated_ |

---

## Rest API SDK Errors

| Condition | Exception/Error Message |
|----------|--------------------------|
| Method not provisioned | `Error Code: 4` </br> `Error Message: Method not Provisioned` </br> _No HTTP status code associated_ |
| API call manually cancelled by user | `Error Code: 9` </br> `Error Message: <Exception message>` </br> _No HTTP status code associated_ |
| No internet connection / Unknown host | `Error Code: 10` </br> `Error Message: <UnknownHost Exception message>` </br> _No HTTP status code associated_ |
| API failure due to an unexpected issue / Invalid response | `Error Code: 11` </br> `Error Message: <Exception message>` </br> _No HTTP status code associated_ |

---

## Map SDK Errors

| Condition | Exception/Error Message |
|----------|--------------------------|
| Config not read properly by Map SDK (improper setup) | `Error Code: 7` </br> `Error Message: Map Config not available` |
| `vectorMap` object or style base URL missing in config | `Error Code: 7` </br> `Error Message: Map Info is not available` |
| Public key or its expiry missing from `vectorMap` object in config | `Error Code: 8` </br> `Error Message: Public Key not found` |

---

---

## Android – Mappls Plugin Errors

| Condition | Exception/Error Message |
|----------|--------------------------|
| `.a.olf` file is missing in the project | `No .a.olf file found. The Mappls Services Plugin cannot function without it.` |
| `.conf` config file is missing | `.conf file not found at: <path>` |

---


### Note
- These errors are **internal SDK exceptions** and may not return standard HTTP status codes.
- Make sure all configuration files are correctly downloaded and integrated from the [Mappls Developer Console](https://developer.mappls.com/).
- Always apply the `com.mappls.services.android` plugin and include required dependencies in your `build.gradle`.
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




<div align="center">@ Copyright 2025 CE Info Systems Ltd. All Rights Reserved.</div>  

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>  

<div align="center">Customer Care: +91-9999333223</div>
