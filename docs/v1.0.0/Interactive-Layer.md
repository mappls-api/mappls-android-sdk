
![Mappls APIs](https://about.mappls.com/images/mappls-b-logo.svg)

# Mappls Interactive Layer Plugin

## Introduction

In the current scenario, where social distancing is paramount, technology is playing a key role in maintaining daily communication and information transfer. Mappls is serving individuals, companies and the government alike, to spread critical information related to COVID-19 through deeply informative, useful and free-to-integrate geo-visualized COVID-19 Layers in your application.

Simply plug these easy-to-integrate and COVID-19 Layers into your applications, and offer a seamless information experience to your users.

## Get Interactive Layers

To get all interactive layers:
#### Java
~~~java
mapplsMap.getInteractiveLayer(new MapplsMap.InteractiveLayerLoadingListener() {  
    @Override  
    public void onLayersLoaded(List<InteractiveLayer> list) {  
  
    }  
});
~~~

#### Kotlin
~~~kotlin
mapplsMap?.getInteractiveLayer(object: MapplsMap.InteractiveLayerLoadingListener {  
    override fun onLayersLoaded(list: List<InteractiveLayer>?) {  
        
    }  
})
~~~

## Show/ Hide Interactive Layers

To show Interactive Layers:
#### Java
~~~java
mapplsMap.showInteractiveLayer(interactiveLayer);
~~~

#### Kotlin
~~~kotlin
mapplsMap?.showInteractiveLayer(interactiveLayer)
~~~
To hide Interactive Layers:
#### Java
~~~java
mapplsMap.hideInteractiveLayer(interactiveLayer);
~~~
#### Kotlin
~~~kotlin
mapplsMap?.hideInteractiveLayer(interactiveLayer)
~~~

## Enable/ Disable Infowindow
To Enable/Disable info window on click of Interactive Layer

#### Java
~~~java
mapplsMap.showInteractiveLayerInfoWindow(false);
~~~
#### Kotlin
~~~kotlin
mapplsMap?.showInteractiveLayerInfoWindow(false)
~~~

## Get Interactive Layer Details
To get the Interactive Layer Detail when clicked on Layer
#### Java
~~~java
mapplsMap.setOnInteractiveLayerClickListener(new MapplsMap.OnInteractiveLayerClickListener() {  
    @Override  
  public void onInteractiveLayerClicked(InteractiveItemDetails interactiveItemDetails) {  
  
    }  
});
~~~
#### Kotlin
~~~kotlin
  
mapplsMap?.setOnInteractiveLayerClickListener(object : MapplsMap.OnInteractiveLayerClickListener {  
    override fun onInteractiveLayerClicked(interactiveItemDetails: InteractiveItemDetails?) {  
  
    }  
})
~~~

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