package com.mappls.sdk.demo.activity.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.location.LocationComponentActivationOptions
import com.mappls.sdk.maps.location.LocationComponentOptions
import com.mappls.sdk.maps.location.engine.LocationEngineCallback
import com.mappls.sdk.maps.location.engine.LocationEngineRequest
import com.mappls.sdk.maps.location.engine.LocationEngineResult
import com.mappls.sdk.maps.location.modes.RenderMode
import java.lang.Exception

class CustomCurrentLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityBaseMapBinding
    private var mMapplsMap: MapplsMap? = null

    private val callback = object: LocationEngineCallback<LocationEngineResult> {
        override fun onSuccess(locationEngineResult: LocationEngineResult?) {
            val location = locationEngineResult?.lastLocation
            if(location != null) {
                mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 16.0))
            }
        }

        override fun onFailure(p0: Exception) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityBaseMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.baseMapHeader.headerTitle.setText(R.string.customize_current_location_title)
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
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
        mMapplsMap?.locationComponent?.locationEngine?.removeLocationUpdates(callback)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    22.553147478403194,
                    77.23388671875
                ), 4.0
            )
        )
        mapplsMap.getStyle {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return@getStyle
            }
            val locationComponent = mapplsMap.locationComponent
            val options = LocationComponentOptions.builder(this)
                .trackingGesturesManagement(true)
                .accuracyColor(R.color.mappls_demo_colorPrimary)
                .foregroundDrawable(R.drawable.current_location_marker)
                .backgroundDrawable(R.drawable.current_location_marker)
                .build()
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, it)
                .locationComponentOptions(options).build())
            locationComponent.isLocationComponentEnabled = true
            locationComponent.renderMode = RenderMode.COMPASS
            val request = LocationEngineRequest.Builder(1000)
                .build()
            locationComponent.locationEngine?.requestLocationUpdates(request, callback, Looper.getMainLooper())
            locationComponent.locationEngine?.getLastLocation(callback)
        }
    }

    override fun onMapError(code: Int, message: String?) {

    }
}