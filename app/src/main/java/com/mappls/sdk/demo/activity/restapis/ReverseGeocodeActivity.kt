package com.mappls.sdk.demo.activity.restapis

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.GeocodeActivity
import com.mappls.sdk.demo.databinding.ActivityReverseGeocodeBinding
import com.mappls.sdk.demo.settings.fragment.NearbySettingsFragment
import com.mappls.sdk.demo.settings.fragment.ReverseGeocodeSettingFragment
import com.mappls.sdk.demo.settings.model.MapplsReverseGeocodeApiSettings
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.PlaceResponse
import com.mappls.sdk.services.api.reversegeocode.MapplsReverseGeoCode
import com.mappls.sdk.services.api.reversegeocode.MapplsReverseGeoCodeManager
import com.mappls.sdk.services.utils.ErrorCodes

class ReverseGeocodeActivity : AppCompatActivity(), OnMapReadyCallback, MapplsMap.OnMapLongClickListener {

    private lateinit var mBinding: ActivityReverseGeocodeBinding
    private var mMapplsMap: MapplsMap? = null
    private var mReverseGeocodeManager: MapplsReverseGeoCodeManager? = null
    private var showResponse = false
    private var hasResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityReverseGeocodeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.revGeocodeHeader.headerTitle.text = "Reverse Geocode"
        mBinding.revGeocodeHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.revGeocodeHeader.ivSettings.visibility = View.GONE
//        mBinding.revGeocodeHeader.ivSettings.setOnClickListener {
//            replaceFragment(ReverseGeocodeSettingFragment())
//        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.searchLatEt.setText("22.553147478403194")
        mBinding.searchLngEt.setText("77.23388671875")
        mBinding.btnRevGeocodeSearch.setOnClickListener {
            searchLatLng()
        }
        showResponse(showResponse)
        mBinding.revGeocodeShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.revGeocodeShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }

    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.revGeocodeShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.revGeocodeShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.revGeocodeResultLayout.visibility = View.VISIBLE
                mBinding.revGeocodeJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.revGeocodeJsonResponseSv.visibility = View.VISIBLE
                mBinding.revGeocodeResultLayout.visibility = View.GONE
            }
        }
    }

    private fun searchLatLng() {
        var latitude = mBinding.searchLatEt.text.toString()
        var longitude = mBinding.searchLngEt.text.toString()
        if(Utils.validateLatitude(latitude) && Utils.validateLatitude(longitude)) {
            mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude.toDouble(), longitude.toDouble()), 16.0))
            callReverseGeocode(latitude.toDouble(), longitude.toDouble())
        } else {
            Toast.makeText(this, "Invalid Coordinates", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callReverseGeocode(lat: Double, lng: Double) {
        mMapplsMap?.clear()
        mBinding.revGeocodeJsonResponse.text = ""
        mBinding.revGeocodeJsonResponseSv.visibility = View.GONE
        mBinding.revGeocodeResultLayout.visibility = View.GONE
        mReverseGeocodeManager?.cancel()
        val builder = MapplsReverseGeoCode.builder()
            .setLocation(lat, lng)
//        if(MapplsReverseGeocodeApiSettings.instance.lang != null) {
//            builder.lang(MapplsReverseGeocodeApiSettings.instance.lang)
//        }
        mReverseGeocodeManager = MapplsReverseGeoCodeManager.newInstance(builder.build())
        mReverseGeocodeManager?.call(object: OnResponseCallback<PlaceResponse> {
            override fun onSuccess(placeResponse: PlaceResponse?) {
                mBinding.revGeocodeJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(placeResponse)
                hasResponse = true
                if((placeResponse?.places?.size ?: 0) > 0) {
                    mMapplsMap?.addMarker(MarkerOptions().position(LatLng(lat, lng)))
                    mBinding.tvRevGeocodeAddress.text = placeResponse?.places?.get(0)?.formattedAddress ?: ""
                } else {
                    Toast.makeText(this@ReverseGeocodeActivity, "Null Response", Toast.LENGTH_SHORT).show()
                }
                if(showResponse) {
                    mBinding.revGeocodeJsonResponseSv.visibility = View.VISIBLE
                    mBinding.revGeocodeResultLayout.visibility = View.GONE
                } else {
                    mBinding.revGeocodeResultLayout.visibility = View.VISIBLE
                    mBinding.revGeocodeJsonResponseSv.visibility = View.GONE
                }
            }

            override fun onError(p0: Int, p1: String?) {
                if(p0 != ErrorCodes.CANCEL_CALL) {
                    Toast.makeText(this@ReverseGeocodeActivity, "$p0 ----- $p1", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.revGeocodeFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
        mMapplsMap?.removeOnMapLongClickListener(this)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap

        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
        mapplsMap.addOnMapLongClickListener(this)
        searchLatLng()
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    override fun onMapLongClick(p0: LatLng): Boolean {
        mBinding.searchLatEt.setText(p0.latitude.toString())
        mBinding.searchLngEt.setText(p0.longitude.toString())
        callReverseGeocode(p0.latitude, p0.longitude)
        return false
    }
}