package com.mappls.sdk.demo.activity.layers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugin.scalebar.ScaleBarOptions
import com.mappls.sdk.plugin.scalebar.ScaleBarPlugin

class ScalebarActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMapplsMap: MapplsMap? = null
    private lateinit var mBinding:ActivityBaseMapBinding

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
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.scalebar_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap
        val cameraPosition = CameraPosition.Builder().target(
            LatLng(
                25.321684, 82.987289
            )
        ).zoom(10.0).tilt(0.0).build()
        mapplsMap.cameraPosition = cameraPosition
        val scaleBarPlugin = ScaleBarPlugin(mBinding.mapView, mapplsMap)
        val scaleBarOptions: ScaleBarOptions = ScaleBarOptions(this)
            .setTextColor(android.R.color.black)
            .setTextSize(40f)
            .setBarHeight(5f)
            .setBorderWidth(2f)
            .setRefreshInterval(15)
            .setMarginTop(R.dimen.scalebar_top_margin)
            .setMarginLeft(R.dimen.scalebar_left_margin)
            .setTextBarMargin(15f)
            .setMaxWidthRatio(0.5f)
            .setShowTextBorder(true)
            .setTextBorderWidth(5f)

        scaleBarPlugin.create(scaleBarOptions)
    }

    override fun onMapError(code: Int, message: String?) {
        Toast.makeText(this, "$code --- $message", Toast.LENGTH_SHORT).show()
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
}