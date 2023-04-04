[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)

# Weather API

Mappls SDK enables the developer to access current weather conditions around India with below list paramenters:

1. Air quality index
2. Temperature
3. Humidity
4. Wind
5. Visibility

## Implementation
#### Java
~~~java
MapplsWeather weather = MapplsWeather.builder()  
        .location(28.0, 77.0)  
        .build();  
MapplsWeatherManager.newInstance(weather).call(new OnResponseCallback<WeatherResponse>() {  
    @Override  
  public void onSuccess(WeatherResponse response) {  
       //Handle Response
  }  
  
    @Override  
  public void onError(int code, String message) {  
      //Handle Error  
  }  
});
~~~
#### Kotlin
~~~kotlin
val weather = MapplsWeather.builder()
    .location(28.0, 77.0)
    .build()
MapplsWeatherManager.newInstance(weather).call(object : OnResponseCallback<WeatherResponse> {
    override fun onSuccess(response: WeatherResponse) {
        //Success  
    }

    override fun onError(code: Int, message: String) {
        //Handle Error  
    }
})
~~~
### Request Parameters

#### Mandatory Parameter
1.  `location (Double, Double)`: Location of the place.

#### Optional Parameter
1. `tempUnit (String)`: Unit of temperature. **Below are the available value:**
	- WeatherCriteria.UNIT_CELSIUS: for centigrate(°C)
	- WeatherCriteria.UNIT_FARENHEIT: for farenheit (F)
2.  `theme (String)`: This parameter is used to define the theme of icon. **Below are the available value:**
	- WeatherCriteria.THEME_LIGHT (Default)
	- WeatherCriteria.THEME_DARK 
3.  `size (String )`: This parameter is used to define the size of icon. **Below are the available value:**
	- WeatherCriteria.SIZE_36PX (Default)
	- WeatherCriteria.SIZE_24PX 
4. `unitType (String)`: This parameter defines the unit type on the basis of which weather forecast information is sought for. **Below are the available value:**
	- WeatherCriteria.UNIT_TYPE_DAY
	- WeatherCriteria.UNIT_TYPE_HOUR
5. `unit (Integer)`: This parameter is the value for which forecast information is sought for. Valid values are:
	- For Days
        - `1`
        - `5`
        - `10`
	- For Hours
        - `1`
        - `24`

### Response Code (as HTTP response code)

#### Success:

1. 200: To denote a successful API call.  
2. 204: To denote the API was a success but no results we’re found.

#### Client side issues:

1.  400: Bad Request, User made an error while creating a valid request.
2.  401: Unauthorized, Developer’s key is not allowed to send a request with restricted parameters.
3.  403: Forbidden, Developer’s key has hit its daily/hourly limit.

#### Server-Side Issues:

1.  500: Internal Server Error, the request caused an error in our systems.
2.  503: Service Unavailable, during our maintenance break or server downtimes.

### Response Messages (as HTTP response message)

1. 200: Success. 
2. 204: No matches we’re found for the provided query. 
3. 400: Something’s just not right with the request. 
4. 401: Access Denied. 
5. 403: Services for this key has been suspended due to daily/hourly transactions limit. 
6. 500: Something went wrong. 
7. 503: Maintenance Break.

## Response parameters
1. `data` (`WeatherData`)

#### WeatherData result parameters:
1. `temperature` (`WeatherTemperature`)
    #### WeatherTemperature result parameters:
    1. `value` (`Double`): Temperature value.For eg:31.3
    2. `unit` (`String`): Temperature unit.For eg:"°C"

2. `weatherCondition` (`WeatherCondition`): Weather condition info

    #### WeatherCondition result parameters:
    1. `weatherId` (`Integer`): Id of Weather condition. For eg:5
    2. `weatherText` (`String`): Weather condition info text.For eg:"Hazy sunshine"
    3. `weatherDescription` (`String`): Description of weather For eg: "Total cloud cover between 20%-60%"
    4. `weatherIcon` (`String`): Weather condition icon url.
    5. `realFeelWeatherText` (`String`):Description of weather For eg:"Feels Like 41.0 °C"

    
3. `airQuality` (`AirQuality`): Air Quality info
    #### AirQuality result parameters:
    1. `airQualityIndex` (`Integer`): Give the value of air quality index. For eg:104
    2. `airQualityIndexText` (`String`): Description for AQI given. For eg: "Unhealthy for Sensitive Groups"
    3. `airQualityIndexUnit` (`String`): Gives the values of airquality index unit. For eg: "PM2.5"

4. `humidity` (`Humidity`): Humidity info

    #### Humidity result parameters:
    1. `relHumidity` (`Integer`): Provides the value for humidity. For Eg: 65
    2. `indoorRelHumidity` (`Integer`): Provide value for indoor relative humidity . For eg: 65

5. `wind` (`Wind`): Wind Info

    #### Wind result parameters:
    1. `windSpeed`(`Double`):For Eg:12.8
    2. `windSpeedUnit` (`String`):For Eg: "KM/H"
    3. `windAngle` (`Integer`):For Eg: 338
    4. `windAngleUnit` (`String`): For Eg: "NNW"

6. `visibility` (`WeatherVisibility`): Visibility Info
    #### WeatherVisibility result parameters:
    1. `value` (`Integer`): Visibilty value. For eg: 2
    2. `unit` (`String`): Unit of Visibilty value. For eg: "KM"

7. `forecastData` (List<`ForecastData`>): This feature is available for your solutions to get forecast details. The API is able to populate current weather condition and `forecast` of `1`, `5` & `10` days (depending on input)or hours based forecast for `1` hr and `24` hrs.

    #### ForecastData result parameters:
    1. `hour`(`String`): Value Returned when request is made for hours. For eg: "26/09/2022 18:00",
    2. `date` (`String`): Value Returned when request is made for multiple day/days. For eg: "28/09/2022",
    3. `day` (`String`): Represents day name when request is made for multiple day/days. For eg: "Wednesday".
    4. `temperature` (`WeatherTemperature`):

        -  `minTemperature`(`Double`): Minimum temperature value for the day/night.For eg:31.3 
        -  `minTemperatureUnit` (`String`): Minimum Temperature Unit. For eg:"°C"
        - `maxTemperature` (`Double`): Maximum Temperature value for the day/night.For eg:31.3
        - `maxTemperatureUnit` (`String`): Maximum Temperature Unit. For eg:"°C"

    5. `weatherCondition` (`WeatherCondition`)

        -  `weatherIdDay` (`Integer`): Shows the ID of the day  For Eg: 1
        -  `weatherTextDay` (`String`): Shows the forcast description for the day For Eg: "Sunny"
        -  `weatherIconDay` (`String`): Shows the forcast icon for day For Eg: "1.png"
        -  `weatherIdNight` (`Integer`): Shows the ID of the night  For Eg: 33
        -  `weatherTextNight` (`String`): Shows the forcast description for the night For Eg: "Clear"
        -  `weatherIconNight` (`String`): Shows the forcast icon for the night For Eg: "33.png"
    

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

