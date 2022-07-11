package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.kotlin.adapter.StepsAdapter
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.MapplsDirectionManager
import com.mappls.sdk.services.api.directions.MapplsDirections
import com.mappls.sdk.services.api.directions.models.DirectionsResponse
import com.mappls.sdk.services.api.directions.models.LegStep
import java.util.*

class DirectionStepActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction_step)
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val directions = MapplsDirections.builder()
                .origin(Point.fromLngLat(73.041932, 19.018686))
                .destination(Point.fromLngLat(73.040028, 19.019499))
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .resource(DirectionsCriteria.RESOURCE_ROUTE)
                .steps(true)
                .alternatives(false)
                .overview(DirectionsCriteria.OVERVIEW_FULL).build()
        MapplsDirectionManager.newInstance(directions).call(object: OnResponseCallback<DirectionsResponse>{
            override fun onSuccess(directionsResponse: DirectionsResponse?) {
                if (directionsResponse != null) {
                    val results = directionsResponse.routes()
                    if (results.size ?: 0 > 0) {
                        val routeLegList = results[0]?.legs()
                        val legSteps: MutableList<LegStep> = ArrayList()
                        for (routeLeg in routeLegList!!) {
                            legSteps.addAll(routeLeg.steps()!!)
                        }
                        if (legSteps.size > 0) {
                            recyclerView.adapter = StepsAdapter(legSteps)
                        }
                    }
                }
            }

            override fun onError(p0: Int, p1: String?) {
            }

        })
    }
}