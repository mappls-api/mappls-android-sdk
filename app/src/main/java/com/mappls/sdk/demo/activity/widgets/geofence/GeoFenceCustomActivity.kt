package com.mappls.sdk.demo.activity.widgets.geofence

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityGeoFenceCustomBinding
import com.mappls.sdk.demo.model.GeofenceDetailModel
import com.mappls.sdk.geofence.ui.GeoFence
import com.mappls.sdk.geofence.ui.GeoFenceType
import com.mappls.sdk.geofence.ui.listeners.GeoFenceViewCallback
import com.mappls.sdk.geofence.ui.util.Orientation
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.geometry.LatLng


class GeoFenceCustomActivity : AppCompatActivity(), GeoFenceViewCallback {
    lateinit var mBinding : ActivityGeoFenceCustomBinding
    var geoFence: GeoFence? = null
    var geofenceDetail: GeofenceDetailModel? = null
    private val mapplsMap: MapplsMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (intent != null && intent.hasExtra("Geofence")) {
            geofenceDetail = Gson().fromJson<GeofenceDetailModel>(
                intent.getStringExtra("Geofence"),
                GeofenceDetailModel::class.java
            )
        }
        mBinding = ActivityGeoFenceCustomBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.geoFenceCustomHeader.headerTitle.text = "Custom GeoFence"
        mBinding.geoFenceCustomHeader.headerBack.setOnClickListener {
            finish()
        }
        if (geofenceDetail == null) {
            geoFence = GeoFence()
            geoFence!!.geoFenceType = GeoFenceType.CIRCLE
            geoFence!!.circleCenter = LatLng(24.6496185, 77.3062072)
            geoFence!!.circleRadius = (200)
        } else {
            geoFence = GeoFence()
            if (geofenceDetail!!.gfType.equals(GeofenceDetailModel.TYPE_POLYGON, ignoreCase = true)) {
                geoFence!!.geoFenceType = GeoFenceType.POLYGON
                val pointList: MutableList<MutableList<Point>> = ArrayList()
                pointList.add(geofenceDetail?.getGPS()!!.toMutableList());
                geoFence!!.polygon = pointList

            } else {
                geoFence!!.geoFenceType = GeoFenceType.CIRCLE
                geoFence!!.circleCenter = geofenceDetail!!.circleCentre
                geoFence!!.circleRadius = (geofenceDetail!!.circleRadius)
            }
        }
        mBinding.idSeekBar.max = mBinding.geoFenceView.maxProgress
        mBinding.geoFenceView.geoFence = geoFence
        mBinding.geoFenceView.setCameraPadding(20, 20, 20, 20)

        mBinding.btnApply.setOnClickListener(View.OnClickListener {
            val geoFence = mBinding.geoFenceView.geoFence
            if (geoFence != null) {
                if (geofenceDetail == null) {
                    geofenceDetail = GeofenceDetailModel()
                    geofenceDetail!!.active = false
                }
                if (geoFence.geoFenceType == GeoFenceType.POLYGON) {
                    geofenceDetail!!.gfType = GeofenceDetailModel.TYPE_POLYGON
                    if (geoFence.polygon == null) {
                        Toast.makeText(
                            this@GeoFenceCustomActivity,
                            "Please draw Polygon first",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@OnClickListener
                    }
                    geofenceDetail!!.active = true
                    geofenceDetail!!.setGPS(geoFence.polygon!![0])
                } else {
                    geofenceDetail!!.active = true
                    geofenceDetail!!.gfType = GeofenceDetailModel.TYPE_CIRCLE
                    geofenceDetail!!.circleCentre = geoFence.circleCenter
                    geofenceDetail!!.circleRadius = geoFence.circleRadius
                }

                val geoFenceIntent = Gson().toJson(geofenceDetail)
                val intent = Intent()
                intent.putExtra("GEOFENCE", geoFenceIntent)
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        mBinding.geoFenceView.onCreate(savedInstanceState)
        mBinding.geoFenceView.setGeoFenceViewCallback(this)


        mBinding.geoFenceView.convertPointsToClockWise(Orientation.CLOCKWISE)

        mBinding.geoFenceView.simplifyWhenIntersectingPolygonDetected(true)
        mBinding.idSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mBinding.geoFenceView.onRadiusChange(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mBinding.geoFenceView.radiusChangeFinish(seekBar.progress)
            }
        })
        mBinding.cbAction.setOnCheckedChangeListener { buttonView, isChecked ->
            mBinding.geoFenceView.enablePolygonDrawing(
                isChecked
            )
        }
        mBinding.idCircleButton.setOnClickListener { mBinding.geoFenceView.drawCircleGeoFence() }
        mBinding.idPolygonButton.setOnClickListener { mBinding.geoFenceView.drawPolygonGeofence() }

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

    override fun onGeoFenceReady(p0: MapplsMap?) {
        mBinding.toolsView.visibility = View.VISIBLE;
    }

    override fun geoFenceType(geoFenceType: GeoFenceType?) {
        if (geoFenceType == GeoFenceType.POLYGON) {
            mBinding.cbAction.visibility = View.VISIBLE;
        } else {
            mBinding.cbAction.visibility = View.GONE;
        }
        mBinding.seekBarView.visibility =
            if (geoFenceType == GeoFenceType.POLYGON) View.GONE else View.VISIBLE

        mBinding.cbAction.isChecked = geoFenceType == GeoFenceType.POLYGON;
        mBinding.ivButtonCircle.isSelected = geoFenceType == GeoFenceType.CIRCLE;
        mBinding.ivButtonPolygon.isSelected = geoFenceType == GeoFenceType.POLYGON;

        if (geoFenceType == GeoFenceType.POLYGON) {
            if (mBinding.geoFenceView.geoFence.polygon != null) {
                mBinding.cbAction.visibility = View.GONE;
            }
        } else {
            if (mBinding.geoFenceView.geoFence.circleRadius != null) {
                mBinding.idSeekBar.progress = mBinding.geoFenceView.progress;
                mBinding.geoFenceView.radiusChangeFinish(mBinding.idSeekBar.progress);
            }

        }
    }

    override fun onCircleRadiusChanging(radius: Int) {
        mBinding.idSeekBarValue.text = "${radius} m";
    }

    override fun onUpdateGeoFence(p0: GeoFence?) {
        if (geoFence!!.geoFenceType == GeoFenceType.POLYGON) {
            mBinding.cbAction.visibility = View.GONE
            mBinding.cbAction.isChecked = false

        }

    }

    override fun hasIntersectionPoints() {

    }

}