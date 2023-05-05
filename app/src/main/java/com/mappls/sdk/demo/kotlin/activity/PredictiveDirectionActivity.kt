package com.mappls.sdk.demo.kotlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDirectionLayoutBinding
import com.mappls.sdk.demo.java.activity.InputActivity
import com.mappls.sdk.demo.java.utils.CheckInternet
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog
import com.mappls.sdk.demo.kotlin.plugin.DirectionPolylinePlugin
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.geojson.utils.PolylineUtils
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.predictive.*
import com.mappls.sdk.services.api.predictive.directions.MapplsPredictiveDirectionManager
import com.mappls.sdk.services.api.predictive.directions.MapplsPredictiveDirections
import com.mappls.sdk.services.api.predictive.directions.model.PredictiveDirectionSummary
import com.mappls.sdk.services.api.predictive.directions.model.PredictiveDirectionsResponse
import com.mappls.sdk.services.utils.Constants.*
import java.text.DecimalFormat
import java.util.*

/**
 * Created by CEINFO on 26-02-2019.
 */
class PredictiveDirectionActivity : AppCompatActivity(), OnMapReadyCallback, MapplsMap.OnMapLongClickListener{

    private var mapplsMap: MapplsMap? = null
    private var transparentProgressDialog: TransparentProgressDialog? = null

    private var profile: String = DirectionsCriteria.PROFILE_DRIVING
    private var speedType: MapplsDirectionSpeedType = MapplsDirectionSpeedTypeOptimal()
    private var directionPolylinePlugin: DirectionPolylinePlugin? = null
    private lateinit var mBinding: ActivityDirectionLayoutBinding
    private lateinit var floatingActionButton: FloatingActionButton
    private var mDestination = "MMI000"
    private var mSource = "28.594475,77.202432"
    private var wayPoints: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_direction_layout)
        mBinding.rbWithoutTraffic.setText("Optimal")
        mBinding.rbWithRouteEta.setText("Predictive")
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
        floatingActionButton = findViewById(R.id.edit_btn)
        floatingActionButton.setOnClickListener { v: View? ->
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("origin", mSource);
            intent.putExtra("destination", mDestination);
            intent.putExtra("waypoints", wayPoints);
            startActivityForResult(intent, 500)
        }


        mBinding.tabLayoutProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (mapplsMap == null) {
                    if (mBinding.tabLayoutProfile.getTabAt(0) != null) {
                        (mBinding.tabLayoutProfile.getTabAt(0))!!.select()
                        return
                    }
                }

                when (tab!!.position) {
                    0 -> {
                        profile = DirectionsCriteria.PROFILE_DRIVING
                        mBinding.rgResourceType.visibility = View.VISIBLE
                    }

                    1 -> {
                        profile = DirectionsCriteria.PROFILE_BIKING
                        mBinding.rgResourceType.check(R.id.rb_without_traffic)
                        mBinding.rgResourceType.visibility = View.GONE
                    }

                    2 -> {
                        profile = DirectionsCriteria.PROFILE_WALKING
                        mBinding.rgResourceType.check(R.id.rb_without_traffic)
                        mBinding.rgResourceType.visibility = View.GONE
                    }
                }

                getDirections()
            }


            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        mBinding.rgResourceType.setOnCheckedChangeListener { radioGroup, _ ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rb_without_traffic -> speedType = MapplsDirectionSpeedTypeOptimal()

                R.id.rb_with_route_eta -> speedType = MapplsDirectionSpeedTypePredictive(
                    MapplsDirectionDateTimeCurrent())

                R.id.rb_with_traffic -> speedType = MapplsDirectionSpeedTypeTraffic()
            }

            getDirections()
        }
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap





        mapplsMap.setPadding(20, 20, 20, 20)
        mapplsMap.addOnMapLongClickListener(this)
        mapplsMap.cameraPosition = setCameraAndTilt()
        if (CheckInternet.isNetworkAvailable(this)) {
            getDirections()
        } else {
            Toast.makeText(this, getString(R.string.pleaseCheckInternetConnection), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set Camera Position
     *
     * @return camera position
     */
    private fun setCameraAndTilt(): CameraPosition {
        return CameraPosition.Builder().target(LatLng(
                28.551087, 77.257373)).zoom(14.0).tilt(0.0).build()
    }

    /**
     * Show Progress Dialog
     */
    private fun progressDialogShow() {
        transparentProgressDialog!!.show()
    }

    /**
     * Hide Progress dialog
     */
    private fun progressDialogHide() {
        transparentProgressDialog!!.dismiss()
    }

    /**
     * Get Directions
     */
    @SuppressLint("WrongConstant")
    private fun getDirections() {
        progressDialogShow()
        val dest: Any = if (!mDestination.contains(",")) mDestination else Point.fromLngLat(mDestination.split(",").toTypedArray()[1].toDouble(), mDestination.split(",").toTypedArray()[0].toDouble())
        val src: Any = if (!mSource.contains(",")) mSource else Point.fromLngLat(mSource.split(",").toTypedArray()[1].toDouble(), mSource.split(",").toTypedArray()[0].toDouble())
        val mapplsPredictiveDirections = MapplsPredictiveDirections.builder()

//        val builder = MapplsDirections.builder()

        if (src is String) {
            mapplsPredictiveDirections.origin(src.toString())
        } else {
            mapplsPredictiveDirections.origin((src as Point))
        }

        if (dest is String) {
            mapplsPredictiveDirections.destination(dest.toString())
        } else {
            mapplsPredictiveDirections.destination((dest as Point))
        }

        if (wayPoints != null) {
            if (!wayPoints!!.contains(";")) {
                if (!wayPoints!!.contains(",")) {
                    Log.e("taf", wayPoints!!)
                    mapplsPredictiveDirections.addWaypoint(wayPoints!!)
                } else {
                    val point = Point.fromLngLat(wayPoints!!.split(",").toTypedArray()[1].toDouble(), wayPoints!!.split(",").toTypedArray()[0].toDouble())
                    mapplsPredictiveDirections.addWaypoint(point)
                }
            } else {
                val wayPointsArray = wayPoints!!.split(";").toTypedArray()
                for (value in wayPointsArray) {
                    if (!value.contains(",")) {
                        mapplsPredictiveDirections.addWaypoint(value)
                    } else {
                        val point = Point.fromLngLat(value.split(",").toTypedArray()[1].toDouble(), value.split(",").toTypedArray()[0].toDouble())
                        mapplsPredictiveDirections.addWaypoint(point)
                    }
                }
            }
        }
        mapplsPredictiveDirections.profile(profile)
        mapplsPredictiveDirections.speedType(speedType)
        MapplsPredictiveDirectionManager.newInstance(mapplsPredictiveDirections.build()).call(object : OnResponseCallback<PredictiveDirectionsResponse>  {

             override fun onError(p0: Int, p1: String?) {
                 progressDialogHide()
                 Toast.makeText(this@PredictiveDirectionActivity, p1, Toast.LENGTH_LONG).show()
             }

            override fun onSuccess(predictivedirectionresponse: PredictiveDirectionsResponse?) {
                if (predictivedirectionresponse != null) {
                    val results = predictivedirectionresponse.trip
                    mapplsMap!!.clear()
                    if (results != null) {
                        val directionsLegs = results.legs
                        if (directionsLegs != null && directionsLegs.size > 0) {
                            val points: MutableList<Point> = ArrayList()
                            for (directionsLeg in directionsLegs) {
                                points.addAll(PolylineUtils.decode(directionsLeg.shape,
                                    PRECISION_6))
                            }
                            drawPath(points)
                            updateData(predictivedirectionresponse.trip.summary)
                        }
                    }

                    val directionsWaypoints = predictivedirectionresponse.trip.locations
                    if (directionsWaypoints != null && directionsWaypoints.size > 0) {
                        for (directionsWaypoint in directionsWaypoints) {
                            mapplsMap!!.addMarker(MarkerOptions().position(LatLng(directionsWaypoint.latitude,
                                directionsWaypoint.longitude)))
                        }
                    }
                 }
                 progressDialogHide()
            }
        })
    }

    /**
     * Update Route data
     *
     * @param route route data
     */


    private fun updateData(route: PredictiveDirectionSummary) {
        mBinding.directionDetailsLayout.visibility = View.VISIBLE
        mBinding.editBtn.visibility=View.VISIBLE
        mBinding.tvDuration.text = "(" + getFormattedDuration(route.time) + ")"
        mBinding.tvDistance.text = getFormattedDistance(route.length)
    }

    /**
     * Get Formatted Distance
     *
     * @param distance route distance
     * @return distance in Kms if distance > 1000 otherwise in mtr
     */
    private fun getFormattedDistance(distance: Double): String {
        return if (distance < 1) ("" + distance * 1000 + "mtr.")
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
        return if (days > 0L) "" + days + " " + (if (days > 1L) "Days" else "Day") + " " + hour + " hr" + (if(min>0L) " " + min + " min" else "")
        else  {
            if(hour > 0L) "" + hour + " hr" + (if(min > 0L) " " + min + "min" else "") else "" + min + "min"
        }
    }

    /**
     * Add polyline along the points
     *
     * @param waypoints route points
     */
    private fun drawPath(waypoints: List<Point>) {
        val listOfLatlang = ArrayList<LatLng>()
        for (point in waypoints) {
            listOfLatlang.add(LatLng(point.latitude(), point.longitude()))
        }

        if(directionPolylinePlugin == null) {
            directionPolylinePlugin = DirectionPolylinePlugin(mapplsMap!!, mBinding.mapView, profile)
            directionPolylinePlugin!!.createPolyline(listOfLatlang)
        } else {
            directionPolylinePlugin!!.updatePolyline(profile, listOfLatlang)
        }

//        mapplsMap?.addPolyline(PolylineOptions().addAll(listOfLatlang).color(Color.parseColor("#3bb2d0")).width(4f))
        val latLngBounds = LatLngBounds.Builder().includes(listOfLatlang).build()
        mapplsMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 30))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 500 && resultCode == RESULT_OK) {
            if (data!!.hasExtra("destination")) {
                mDestination = data.getStringExtra("destination")!!
            }
            if (data.hasExtra("origin")) {
                mSource = data.getStringExtra("origin")!!
            }
            if (data.hasExtra("waypoints")) {
                wayPoints = data.getStringExtra("waypoints")
            }
            getDirections()
        }
    }

    override fun onMapLongClick(latlng: LatLng): Boolean {
       val alertDialog = AlertDialog.Builder(this);
        alertDialog.setMessage("Select Point as Source or Destination");

        alertDialog.setPositiveButton("Source") { dialog, which ->

            mSource = "${latlng.latitude},${latlng.longitude}"
            getDirections(); }

        alertDialog.setNegativeButton("Destination") { dialog, which ->

            mDestination = "${latlng.latitude},${latlng.longitude}"
            getDirections(); }
        alertDialog.setNeutralButton("Waypoint") { dialog, which ->

           if (TextUtils.isEmpty(wayPoints)){
               wayPoints = "${latlng.latitude},${latlng.longitude}"
           }else{
               wayPoints = wayPoints+";"+ "${latlng.latitude},${latlng.longitude}"
           }
            getDirections(); }

        alertDialog.setCancelable(true);
        alertDialog.show();
        return false
    }
}