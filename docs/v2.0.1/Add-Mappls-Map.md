[<img src="https://about.mappls.com/images/mappls-b-logo.svg" height="60"/> </p>](https://www.mapmyindia.com/api)
# Adding Mappls Map
There are two ways to add the Mappls Map to your Android project. Following options are the way to include Mappls Map in your project: 
    - Using `MapView`
    - Using `SupportMapFragment`

## [Using MapView]()
- Add the Map In your layout
    ```xml
    <com.mappls.sdk.maps.MapView
        android:id="@+id/map_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    ```
- Handle the Lifecycle of Activity/Fragment
    #### Kotlin
    ```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //...
        mBinding.mapView.onCreate(savedInstanceState)
        //...
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
    }
    ```
    #### Java
    ```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //..
        mBinding.mapView.onCreate(savedInstanceState);
        //..
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.mapView.onDestroy();
    }
    ```
   >><b> Note: In case of Fragment call `mBinding.mapView.onDestroy()` in `onDestroyView()` callback insted of `onDestroy()`</b>
- Add the Map Initialization event callback
    #### Kotlin
    ```kotlin
    mBinding.mapView.getMapAsync(object: OnMapReadyCallback {
        override fun onMapReady(mapplsMap: MapplsMap) {
            //Map Initialise Successfully
        }

        override fun onMapError(code: Int, message: String?) {
            //Error in Initialising Map 
        }
    })
    ```
    #### Java
    ```java
    mBinding.mapView.getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull MapplsMap mapplsMap) {
            //Map Initialise Successfully  
        }
        @Override
        public void onMapError(int i, String s) {
            //Error in Initialising Map
        }
    });
    ```
## [Using Support Map Fragment]()
- Add Map Fragment in Layout
    ```xml
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/support_map_fragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.mappls.sdk.maps.SupportMapFragment" />
    ```
- Add the Map Initialization event callback
    #### Kotlin
    ```kotlin
    mBinding.supportMapFragment.getFragment<SupportMapFragment>().getMapAsync(object: OnMapReadyCallback {
        override fun onMapReady(mapplsMap: MapplsMap) {
            //Map Initialise Successfully  
        }

        override fun onMapError(code: Int, message: String?) {
            //Error in Initialising Map        
        }
    })
    ```
    #### Java
    ```java
    ((SupportMapFragment)mBinding.supportMapFragment.getFragment()).getMapAsync(new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull MapplsMap mapplsMap) {
            //Map Initialise Successfully      
        }

        @Override
        public void onMapError(int i, String s) {
            //Error in Initialising Map
        }
    });
    ```
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




<div align="center">@ Copyright 2025 CE Info Systems Ltd. All Rights Reserved.</div>    

<div align="center"> <a href="https://about.mappls.com/api/terms-&-conditions">Terms & Conditions</a> | <a href="https://about.mappls.com/about/privacy-policy">Privacy Policy</a> | <a href="https://about.mappls.com/pdf/mapmyIndia-sustainability-policy-healt-labour-rules-supplir-sustainability.pdf">Supplier Sustainability Policy</a> | <a href="https://about.mappls.com/pdf/Health-Safety-Management.pdf">Health & Safety Policy</a> | <a href="https://about.mappls.com/pdf/Environment-Sustainability-Policy-CSR-Report.pdf">Environmental Policy & CSR Report</a>    

<div align="center">Customer Care: +91-9999333223</div>
