package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityGradientPolylineBinding
import com.mappls.sdk.demo.kotlin.plugin.GradientPolylinePlugin
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import java.util.*

/**
 * Created by Saksham on 20/9/19.
 */
class GradientPolylineActivity : AppCompatActivity(), OnMapReadyCallback {

    private val listOfLatLng = ArrayList<LatLng>()
    private lateinit var mBinding: ActivityGradientPolylineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_gradient_polyline)

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)

        listOfLatLng.add(LatLng(28.705436, 77.100462))
        listOfLatLng.add(LatLng(28.705191, 77.100784))
        listOfLatLng.add(LatLng(28.704646, 77.101514))
        listOfLatLng.add(LatLng(28.704194, 77.101171))
        listOfLatLng.add(LatLng(28.704083, 77.101066))
        listOfLatLng.add(LatLng(28.703900, 77.101318))

    }

    override fun onMapReady(mapplsMap: MapplsMap) {

        mapplsMap.setPadding(20, 20, 20, 20)
        val latLngBounds = LatLngBounds.Builder()
                .includes(listOfLatLng)
                .build()
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10))
        val animatedPolylinePlugin = GradientPolylinePlugin(mapplsMap, mBinding.mapView)
        animatedPolylinePlugin.createPolyline(listOfLatLng)
        mBinding.btnRemove.setOnClickListener(View.OnClickListener { animatedPolylinePlugin.clear() })


    }

    override fun onMapError(i: Int, s: String) {

    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }
}
