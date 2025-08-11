package com.mappls.sdk.demo.activity.restapis

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityGeocodeBinding

import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.geocoding.GeoCodeResponse
import com.mappls.sdk.services.api.geocoding.MapplsGeoCoding
import com.mappls.sdk.services.api.geocoding.MapplsGeoCodingManager
import com.mappls.sdk.services.api.reversegeocode.MapplsReverseGeoCodeManager
import com.mappls.sdk.services.utils.ErrorCodes

class GeocodeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityGeocodeBinding
    private var mMapplsMap: MapplsMap? = null
    private var mGeocodeManager: MapplsGeoCodingManager? = null
    private var showResponse = false
    private var hasResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityGeocodeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.geocodeHeader.headerTitle.text = "Geocode"
        mBinding.geocodeHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.geocodeHeader.ivSettings.visibility = View.GONE
        showResponse(showResponse)
        mBinding.geocodeShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.geocodeShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }
        mBinding.btnGeocodeSearch.setOnClickListener {
            val search = mBinding.searchEt.text.toString()
            if(search.isEmpty()) {
                Toast.makeText(this, "Enter Some Keyword", Toast.LENGTH_SHORT).show()
            } else {
                callGeocode(search)
            }
        }
    }


    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.geocodeShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.geocodeShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.geocodeJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.geocodeJsonResponseSv.visibility = View.VISIBLE
            }
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.geocodeFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun callGeocode(search: String) {
        mMapplsMap?.clear()
        mBinding.geocodeJsonResponse.text = ""
        mBinding.geocodeJsonResponseSv.visibility = View.GONE
        mGeocodeManager?.cancel()
        mBinding.progressBarLayout.progressBar.visibility = View.VISIBLE
        val builder = MapplsGeoCoding.builder()
            .setAddress(search)
//        if(MapplsGeocodeApiSettings.instance.itemCount != null) {
//            builder.itemCount(MapplsGeocodeApiSettings.instance.itemCount)
//        }
//        if(MapplsGeocodeApiSettings.instance.bias != null) {
//            builder.bias(MapplsGeocodeApiSettings.instance.bias)
//        }
//        if(MapplsGeocodeApiSettings.instance.podFilter != null) {
//            builder.podFilter(MapplsGeocodeApiSettings.instance.podFilter)
//        }
//        if(MapplsGeocodeApiSettings.instance.bound != null) {
//            builder.bound(MapplsGeocodeApiSettings.instance.bound)
//        }
//        if(MapplsGeocodeApiSettings.instance.scores) {
//            builder.scores(MapplsGeocodeApiSettings.instance.scores)
//        }
//        if(MapplsGeocodeApiSettings.instance.clientAppName != null) {
//            builder.clientAppName(MapplsGeocodeApiSettings.instance.clientAppName)
//        }
        mGeocodeManager = MapplsGeoCodingManager.newInstance(builder.build())
        mGeocodeManager?.call(object: OnResponseCallback<GeoCodeResponse> {
            override fun onSuccess(geoCodeResponse: GeoCodeResponse?) {
                mBinding.geocodeJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(geoCodeResponse)
                mBinding.progressBarLayout.progressBar.visibility = View.GONE
                hasResponse = true
                if((geoCodeResponse?.results?.size?: 0) > 0) {
                    val response = geoCodeResponse?.results?.get(0)
                    if(response?.latitude != null && response?.longitude != null) {
                        mMapplsMap?.addMarker(MarkerOptions().position(LatLng(response.latitude, response.longitude)))
                        mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(response.latitude, response.longitude), 16.0))
                    } else {
                        mMapplsMap?.addMarker(MarkerOptions().mapplsPin(response?.mapplsPin))
                        mMapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinZoom(response?.mapplsPin!!, 16.0))
                    }
                    if(showResponse) {
                        mBinding.geocodeJsonResponseSv.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(this@GeocodeActivity, "Null Response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    mBinding.progressBarLayout.progressBar.visibility = View.GONE
                    Toast.makeText(this@GeocodeActivity, "$p0 ----- $p1", Toast.LENGTH_SHORT).show()
                }
            }

        })
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

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

}