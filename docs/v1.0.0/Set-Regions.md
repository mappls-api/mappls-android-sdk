
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)


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

## Our many happy customers:

![](https://www.mapmyindia.com/api/img/logos1/PhonePe.png)![](https://www.mapmyindia.com/api/img/logos1/Arya-Omnitalk.png)![](https://www.mapmyindia.com/api/img/logos1/delhivery.png)![](https://www.mapmyindia.com/api/img/logos1/hdfc.png)![](https://www.mapmyindia.com/api/img/logos1/TVS.png)![](https://www.mapmyindia.com/api/img/logos1/Paytm.png)![](https://www.mapmyindia.com/api/img/logos1/FastTrackz.png)![](https://www.mapmyindia.com/api/img/logos1/ICICI-Pru.png)![](https://www.mapmyindia.com/api/img/logos1/LeanBox.png)![](https://www.mapmyindia.com/api/img/logos1/MFS.png)![](https://www.mapmyindia.com/api/img/logos1/TTSL.png)![](https://www.mapmyindia.com/api/img/logos1/Novire.png)![](https://www.mapmyindia.com/api/img/logos1/OLX.png)![](https://www.mapmyindia.com/api/img/logos1/sun-telematics.png)![](https://www.mapmyindia.com/api/img/logos1/Sensel.png)![](https://www.mapmyindia.com/api/img/logos1/TATA-MOTORS.png)![](https://www.mapmyindia.com/api/img/logos1/Wipro.png)![](https://www.mapmyindia.com/api/img/logos1/Xamarin.png)

<br>

For any queries and support, please contact:

[<img src="https://mmi-api-team.s3.amazonaws.com/Mappls-SDKs/Resources/mappls-logo.png" height="40"/> </p>](https://about.mappls.com/api/)    
Email us at [apisupport@mappls.com](mailto:apisupport@mappls.com)

![](https://www.mapmyindia.com/api/img/icons/stack-overflow.png)    
[Stack Overflow](https://stackoverflow.com/questions/tagged/mappls-api)    
Ask a question under the mappls-api

![](https://www.mapmyindia.com/api/img/icons/support.png)    
[Support](https://about.mappls.com/contact/)    
Need support? contact us!

![](https://www.mapmyindia.com/api/img/icons/blog.png)    
[Blog](http://www.mapmyindia.com/blog/)    
Read about the latest updates & customer stories

> © Copyright 2022. CE Info Systems Ltd. All Rights Reserved. | [Terms & Conditions](https://about.mappls.com/api/terms-&-conditions).