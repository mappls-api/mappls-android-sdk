package com.mappls.sdk.demo.activity.layers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityBaseMapBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.IconFactory
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.utils.BitmapUtils.getBitmapFromDrawable

class IndoorActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityBaseMapBinding

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
        mBinding.baseMapHeader.headerTitle.text = getString(R.string.indoor_map_title)
        mBinding.baseMapHeader.ivSettings.visibility = View.GONE
        mBinding.baseMapHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
    }

    override fun onMapReady(map: MapplsMap) {
        map.uiSettings?.isLayerControlEnabled = true
        map.setMinZoomPreference(4.5)
        map.setMaxZoomPreference(18.5)
        map.getStyle {
            if (it.isFullyLoaded) {
                val iconFactory = IconFactory.getInstance(this)
                val bitmap = getBitmapFromDrawable(
                    ContextCompat.getDrawable(
                        this@IndoorActivity,
                        R.drawable.mappls_map_demo_marker
                    )
                )

                val icon = bitmap?.let { it1 -> iconFactory.fromBitmap(it1) }
                map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            28.5425071, 77.1560724
                        )
                    ).icon(icon)
                )
                /* this is done for animating/moving camera to particular position */
                val cameraPosition = CameraPosition.Builder().target(
                    LatLng(
                        28.5425071, 77.1560724
                    )
                ).zoom(16.0).tilt(0.0).build()
                map.cameraPosition = cameraPosition
            }
        }


    }

    override fun onMapError(code: Int, message: String?) {
        Toast.makeText(this, "$code --- $message", Toast.LENGTH_SHORT).show()
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