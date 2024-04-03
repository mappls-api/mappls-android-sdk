
[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)


# Setting Regions - limiting geographic area

Source data can often contain more information than what's needed in your map.  
For example, an administrative places dataset may contain data for a country OR set of countries OR contain places for the entire world; when you may only need political boundaries for North America or the USA.  
In this scenario, you can specify a region in SDK to limit the geographic extent of the data that will be tiled.

As of now, **the tiles for global maps are enabled by default** for all users as a means for users to visually experience our international maps.

Search and Routing APIs are available for the entire world as well; but they are enabled by default for India only for all customers.  
If any customer wishes to use our Search and Routing Stacks of SDKs & APIs in their apps, please contact our API Support team.

Why limit access?

By reducing the amount of data querying & transference required, you can reduce your API transaction costs and also reduce the amount of time it takes for your job to process.

Now, what you as a developer need to do is to specify a region in our APIs/SDKs from the list of globally applicable region values and that's it !! [See Country List](https://github.com/mappls-api/mappls-rest-apis/blob/main/docs/countryISO.md)

To set the region, refer to the below code. Default is India ( "IND")

```java  
MapplsAccountManager.getInstance().setRegion("KWT");  
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




<div align="center">@ Copyright 2024 CE Info Systems Ltd. All Rights Reserved.</div>

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>

<div align="center">Customer Care: +91-9999333223</div>
