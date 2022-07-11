package com.mappls.sdk.demo.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.LayoutGeofenceUiActivityBinding
import com.mappls.sdk.demo.kotlin.model.GeofenceDetail
import com.mappls.sdk.geofence.ui.GeoFence
import com.mappls.sdk.geofence.ui.GeoFenceType
import com.mappls.sdk.geofence.ui.listeners.GeoFenceViewCallback
import com.mappls.sdk.geofence.ui.util.Orientation
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.geometry.LatLng

class GeoFenceCustomActivity: AppCompatActivity(), GeoFenceViewCallback {


    private lateinit var mBinding: LayoutGeofenceUiActivityBinding
    var geoFence: GeoFence? = null
    var geofenceDetail: GeofenceDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent != null && intent.hasExtra("Geofence")) {
            geofenceDetail = Gson().fromJson(intent.getStringExtra("Geofence"), GeofenceDetail::class.java)
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.layout_geofence_ui_activity);
        if(geofenceDetail == null) {
            geoFence = GeoFence();
            geoFence?.geoFenceType = GeoFenceType.CIRCLE
            geoFence?.circleCenter = LatLng(24.6496185, 77.3062072)
            geoFence?.circleRadius = 200
        } else {
            geoFence = GeoFence()
            if(geofenceDetail?.gfType?.equals(GeofenceDetail.TYPE_POLYGON, ignoreCase = true)!!) {
                geoFence?.geoFenceType = GeoFenceType.POLYGON
                val pointList: MutableList<List<Point>> = ArrayList()
                pointList.add(geofenceDetail?.polygonPoints!!)
                geoFence?.polygon = pointList
            } else {
                geoFence?.geoFenceType = GeoFenceType.CIRCLE
                geoFence?.circleCenter = geofenceDetail?.circleCentre
                geoFence?.circleRadius = geofenceDetail?.circleRadius
            }
        }
            mBinding.idSeekBar.max = mBinding.geoFenceView.maxProgress
            mBinding.geoFenceView.geoFence = geoFence
            mBinding.geoFenceView.setCameraPadding(20, 20, 20, 20)

            mBinding.btnApply.setOnClickListener {
                val geoFence = mBinding.geoFenceView.geoFence

                if(geoFence != null) {
                    if (geofenceDetail == null) {
                        geofenceDetail = GeofenceDetail()
                        geofenceDetail?.active = false
                    }

                    if(geoFence.geoFenceType == GeoFenceType.POLYGON) {
                        geofenceDetail?.gfType = GeofenceDetail.TYPE_POLYGON
                        if(geoFence.polygon == null) {
                            Toast.makeText(this, "Please draw Polygon first", Toast.LENGTH_SHORT).show();
                            return@setOnClickListener
                        }
                        geofenceDetail?.polygonPoints = geoFence.polygon?.get(0)

                    } else {
                        geofenceDetail?.gfType = GeofenceDetail.TYPE_CIRCLE
                        geofenceDetail?.circleCentre = geoFence.circleCenter
                        geofenceDetail?.circleRadius = geoFence.circleRadius
                    }

                    val geoFenceIntent = Gson().toJson(geofenceDetail)
                    val intent = Intent()
                    intent.putExtra("GEOFENCE", geoFenceIntent)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

        mBinding.geoFenceView.onCreate(savedInstanceState)
        mBinding.geoFenceView.setGeoFenceViewCallback(this@GeoFenceCustomActivity)


        mBinding.geoFenceView.convertPointsToClockWise(Orientation.CLOCKWISE)

        mBinding.geoFenceView.simplifyWhenIntersectingPolygonDetected(true)

        mBinding.idSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mBinding.geoFenceView.onRadiusChange(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mBinding.geoFenceView.radiusChangeFinish(seekBar.progress)
            }
        })

        mBinding.cbAction.setOnCheckedChangeListener { buttonView, isChecked -> mBinding.geoFenceView.enablePolygonDrawing(isChecked) }
        mBinding.idCircleButton.setOnClickListener { mBinding.geoFenceView.drawCircleGeoFence() }
        mBinding.idPolygonButton.setOnClickListener { mBinding.geoFenceView.drawPolygonGeofence() }
    }

    override fun onCircleRadiusChanging(radius: Int) {
        mBinding.idSeekBarValue.text = "$radius m"
    }

    override fun onUpdateGeoFence(geoFence: GeoFence?) {
        if (geoFence?.geoFenceType == GeoFenceType.CIRCLE) {
            mBinding.cbAction.visibility = View.GONE
            mBinding.cbAction.isChecked = false

        }


    }

    override fun onGeoFenceReady(p0: MapplsMap?) {
        mBinding.toolsView.visibility = View.VISIBLE
    }

    override fun hasIntersectionPoints() {
    }

    override fun geoFenceType(geoFenceType: GeoFenceType) {
        if (geoFenceType == GeoFenceType.POLYGON) {
            mBinding.cbAction.visibility = View.VISIBLE
        } else {
            mBinding.cbAction.visibility = View.GONE
        }
        mBinding.seekBarView.visibility = if (geoFenceType == GeoFenceType.POLYGON) View.GONE else View.VISIBLE

        mBinding.cbAction.isChecked = geoFenceType == GeoFenceType.POLYGON
        mBinding.ivButtonCircle.isSelected = geoFenceType == GeoFenceType.CIRCLE
        mBinding.ivButtonPolygon.isSelected = geoFenceType == GeoFenceType.POLYGON
        if (geoFenceType == GeoFenceType.POLYGON) {
            if (mBinding.geoFenceView.geoFence.polygon != null) {
                mBinding.cbAction.visibility = View.GONE
            }
        } else {
            if (mBinding.geoFenceView.geoFence.circleRadius != null) {
                mBinding.idSeekBar.progress = mBinding.geoFenceView.progress
                mBinding.geoFenceView.radiusChangeFinish(mBinding.idSeekBar.progress)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        mBinding.geoFenceView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.geoFenceView.onResume()
    }


    override fun onPause() {
        super.onPause()
        mBinding.geoFenceView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.geoFenceView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.geoFenceView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.geoFenceView.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mBinding.geoFenceView.onStop()
    }

}