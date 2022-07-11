package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivitySemiCirclePolylineBinding
import com.mappls.sdk.demo.kotlin.utility.SemiCirclePointsListHelper
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.maps.style.sources.GeoJsonOptions
import com.mappls.sdk.plugin.annotation.LineManager
import com.mappls.sdk.plugin.annotation.LineOptions

/**
 * Created by Saksham on 20/9/19.
 */
class SemiCirclePolylineActivity : AppCompatActivity(), OnMapReadyCallback {

    private var lineManager: LineManager? = null
    private var listOfLatLng: List<LatLng>? = null
    private lateinit var mBinding: ActivitySemiCirclePolylineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_semi_circle_polyline)

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.remove.setOnClickListener(View.OnClickListener {
            lineManager?.clearAll()
            mBinding.remove.visibility = View.GONE
            mBinding.add.visibility = View.VISIBLE
        })

        mBinding.add.setOnClickListener{
            val lineOptions: LineOptions = LineOptions()
                    .points(listOfLatLng)
                    .lineColor("#FF0000")
                    .lineWidth(4f)
            lineManager?.create(lineOptions)
            mBinding.add.visibility = View.GONE
            mBinding.remove.visibility = View.VISIBLE
        }

        listOfLatLng = SemiCirclePointsListHelper.showCurvedPolyline(LatLng(28.7039, 77.101318), LatLng(28.704248, 77.102370), 0.5)
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        val latLngBounds = LatLngBounds.Builder()
                .includes(listOfLatLng!!)
                .build()

        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100))
        mapplsMap.getStyle {
            lineManager = LineManager(mBinding.mapView, mapplsMap, it, GeoJsonOptions().withLineMetrics(true).withBuffer(2))
            lineManager?.lineDasharray = arrayOf(4f, 6f)
            val lineOptions: LineOptions = LineOptions()
                    .points(listOfLatLng)
                    .lineColor("#FF0000")
                    .lineWidth(4f)
            lineManager?.create(lineOptions)

            mBinding.remove.visibility = View.VISIBLE
        }



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
