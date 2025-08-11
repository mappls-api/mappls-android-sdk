package com.mappls.sdk.demo.activity.marker

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.demo.model.ClusterModelItem
import com.mappls.sdk.demo.plugin.ClusterMarkerPlugin
import com.mappls.sdk.demo.plugin.CustomClusterMarkerPlugin
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds

class CustomClusterActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mBinding: ActivityBaseMapBinding
    private val latLngList = listOf(
        ClusterModelItem("car", LatLng(28.551635, 77.268805)),
        ClusterModelItem("car", LatLng(28.551041, 77.267979)),
        ClusterModelItem("car", LatLng(28.552115, 77.265833)),
        ClusterModelItem("car", LatLng(28.559786, 77.238859)),
        ClusterModelItem("car", LatLng(28.561535, 77.233345)),
        ClusterModelItem("car", LatLng(28.562469, 77.235072)),
        ClusterModelItem("car", LatLng(28.435931, 77.304689)),
        ClusterModelItem("pin", LatLng(28.436214, 77.304936)),
        ClusterModelItem("pin", LatLng(28.438827, 77.308337)),
        ClusterModelItem("pin", LatLng(28.489028, 77.091252)),
        ClusterModelItem("pin", LatLng(28.486831, 77.094492)),
        ClusterModelItem("pin", LatLng(28.486491, 77.094374)),
        ClusterModelItem("pin", LatLng(28.491510, 77.082149)),
        ClusterModelItem("pin", LatLng(28.474800, 77.065233)),
        ClusterModelItem("pin", LatLng(28.471245, 77.072722)),
        ClusterModelItem("pin", LatLng(28.458440, 77.073179))
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityBaseMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.baseMapHeader.headerTitle.text = "Custom Cluster"
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)


    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mapplsMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                LatLngBounds.Builder().includes(latLngList.map { it.mPosition }).build(), 20, 20, 20, 20
            )
        )
        val customClusterMarkerPlugin = CustomClusterMarkerPlugin(mBinding.mapView, mapplsMap)
        customClusterMarkerPlugin.setMarkers(latLngList)

    }

    override fun onMapError(p0: Int, p1: String?) {

    }


    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
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

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }
}