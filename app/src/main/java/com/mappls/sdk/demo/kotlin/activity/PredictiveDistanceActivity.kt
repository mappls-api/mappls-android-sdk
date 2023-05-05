package com.mappls.sdk.demo.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.DistanceActivityBinding
import com.mappls.sdk.demo.java.activity.InputActivity
import com.mappls.sdk.demo.java.utils.CheckInternet
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.predictive.distance.MapplsPredictiveDistance
import com.mappls.sdk.services.api.predictive.distance.MapplsPredictiveDistanceManager
import com.mappls.sdk.services.api.predictive.distance.PredictiveDistanceCriteria
import com.mappls.sdk.services.api.predictive.distance.models.PredictiveDistanceResponse
import com.mappls.sdk.services.api.predictive.distance.models.PredictiveDistanceResults

import java.text.DecimalFormat
import java.util.*

/**
 * Created by CEINFO on 26-02-2019.
 */
class PredictiveDistanceActivity : AppCompatActivity(), OnMapReadyCallback {
    private var transparentProgressDialog: TransparentProgressDialog? = null
    private lateinit var mBinding: DistanceActivityBinding

    private var mDestination:String? = "28.551087,77.257373"
    private var mSource :String?= "28.582864,77.234230"
    private var waypoints: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.distance_activity)
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
        mBinding.editBtn.setOnClickListener { v: View? ->
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("origin", mSource);
            intent.putExtra("destination", mDestination);
            intent.putExtra("waypoints", waypoints);
            startActivityForResult(intent, 501)
        }
    }

    override fun onMapReady(mapplsMap: MapplsMap) {


        mapplsMap.setPadding(20, 20, 20, 20)

        mapplsMap.cameraPosition = setCameraAndTilt()
      /*  val coordinatesPoint = ArrayList<Point>()
        coordinatesPoint.add(Point.fromLngLat(77.257373, 28.551087))
        coordinatesPoint.add(Point.fromLngLat(77.234230, 28.582864))*/
        if (CheckInternet.isNetworkAvailable(this@PredictiveDistanceActivity)) {
            calculateDistance(null, null)
        } else {
            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapError(i: Int, s: String) {}

    private fun setCameraAndTilt(): CameraPosition {
        return CameraPosition.Builder().target(LatLng(
                28.551087, 77.257373)).zoom(14.0).tilt(0.0).build()
    }

    private fun progressDialogShow() {
        transparentProgressDialog!!.show()
    }

    private fun progressDialogHide() {
        transparentProgressDialog!!.dismiss()
    }

    private fun calculateDistance(pointList: List<Point>?, mapplsPinList: MutableList<String?>?) {
        progressDialogShow()

        val mapplsPredictiveDistance = MapplsPredictiveDistance.builder()
        if (mSource != null) {
            if (!mSource!!.contains(",")) {
                mapplsPredictiveDistance.addSource(mSource)
            } else {
                val point = Point.fromLngLat(mSource!!.split(",").toTypedArray()[1].toDouble(), mSource!!.split(",").toTypedArray()[0].toDouble())
                mapplsPredictiveDistance.addSource(point)
            }
        }

        if (mapplsPinList != null && mapplsPinList.isNotEmpty()) {
            mapplsPredictiveDistance.sourceList(mapplsPinList)
        }
        if (pointList != null && pointList.isNotEmpty()) {
            mapplsPredictiveDistance.sources(pointList)
        }
        if (mDestination != null) {
            if (!mDestination!!.contains(",")) {
                mapplsPredictiveDistance.addDestination(mDestination)
            } else {
                val point = Point.fromLngLat(mDestination!!.split(",").toTypedArray()[1].toDouble(), mDestination!!.split(",").toTypedArray()[0].toDouble())
                mapplsPredictiveDistance.addDestination(point)
            }
        }
        mapplsPredictiveDistance.profile(PredictiveDistanceCriteria.PROFILE_DRIVING)
//                .resource(DirectionsCriteria.RESOURCE_DISTANCE_ETA)
        MapplsPredictiveDistanceManager.newInstance(mapplsPredictiveDistance.build()).call(object : OnResponseCallback<PredictiveDistanceResponse> {

            override fun onError(p0: Int, p1: String?) {
                progressDialogHide()
            }

            override fun onSuccess(predictiveDistanceResponse: PredictiveDistanceResponse?) {
                progressDialogHide()

                val distanceList = predictiveDistanceResponse?.sourcesToTargets?.get(0)?.get(0)

                if (distanceList != null) {
                    val distances = distanceList.distance
                    updateData(distanceList)
                    Toast.makeText(this@PredictiveDistanceActivity, "Distance: " + distances!!, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PredictiveDistanceActivity, "Failed: " + predictiveDistanceResponse?.units, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun updateData(distanceList: PredictiveDistanceResults) {
        mBinding.distanceDetailsLayout.visibility = View.VISIBLE
        mBinding.editBtn.visibility = View.VISIBLE
        mBinding.tvDuration.text = "(" + getFormattedDuration(distanceList.time.toDouble()) + ")"
        mBinding.tvDistance.text = getFormattedDistance(distanceList.distance)
    }

    /**
     * Get Formatted Distance
     *
     * @param distance route distance
     * @return distance in Kms if distance > 1000 otherwise in mtr
     */
    private fun getFormattedDistance(distance: Double): String {
        return if (distance < 1) ("" + (distance * 1000) + "mtr.")
        else {
            val deimalFormatter = DecimalFormat("#.#")
            deimalFormatter.format(distance) + "Km."
        }
    }

    /**
     * Get Formatted Duration
     *
     * @param duration route duration
     * @return formatted duration
     */
    private fun getFormattedDuration(duration: Double): String {
        val min: Long = (duration % 3600 / 60).toLong()
        val hour: Long = (duration % 86400 / 3600).toLong()
        val days: Long = (duration / 86400).toLong()
        return if (days > 0L) "" + days + " " + (if (days > 1L) "Days" else "Day") + " " + hour + " hr" + (if (min > 0L) " " + min + " min" else "")
        else {
            if (hour > 0L) "" + hour + " hr" + (if (min > 0L) " " + min + "min" else "") else "" + min + "min"
        }
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 501 && resultCode == RESULT_OK) {
            val mapplsPinList: MutableList<String?> = ArrayList()
            val points: MutableList<Point> = ArrayList()
            if (data!!.hasExtra("origin")) {
                mSource = data.getStringExtra("origin")
            }
            if (data.hasExtra("waypoints")) {
                val wayPoints = data.getStringExtra("waypoints")
                if (!wayPoints!!.contains(";")) {
                    if (!wayPoints.contains(",")) {
                        mapplsPinList.add(wayPoints)
                    } else {
                        val point = Point.fromLngLat(wayPoints.split(",").toTypedArray()[1].toDouble(), wayPoints.split(",").toTypedArray()[0].toDouble())
                        points.add(point)
                    }
                } else {
                    val wayPointsArray = wayPoints.split(";").toTypedArray()
                    for (value in wayPointsArray) {
                        if (!value.contains(",")) {
                            mapplsPinList.add(value)
                        } else {
                            val point = Point.fromLngLat(value.split(",").toTypedArray()[1].toDouble(), value.split(",").toTypedArray()[0].toDouble())
                            points.add(point)
                        }
                    }
                }
                this.waypoints = wayPoints
            }
            if (data.hasExtra("destination")) {
                mDestination = data.getStringExtra("destination")
            }
            calculateDistance(points, mapplsPinList)
        }
    }
}
