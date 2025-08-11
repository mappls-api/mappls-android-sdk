package com.mappls.sdk.demo.activity.utility

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.StepsAdapter
import com.mappls.sdk.demo.databinding.ActivityDirectionStepBinding
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.directions.DirectionsCriteria
import com.mappls.sdk.services.api.directions.MapplsDirectionManager
import com.mappls.sdk.services.api.directions.MapplsDirections
import com.mappls.sdk.services.api.directions.models.DirectionsResponse
import com.mappls.sdk.services.api.directions.models.LegStep
import com.mappls.sdk.services.utils.ErrorCodes
import kotlinx.coroutines.launch

class DirectionStepActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityDirectionStepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityDirectionStepBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.topHeader.headerTitle.text = "Direction Steps"
        mBinding.topHeader.ivSettings.visibility = View.GONE
        mBinding.topHeader.headerBack.setOnClickListener {
            finish()
        }

        val mapplsDirections: MapplsDirections = MapplsDirections.builder()
            .origin(Point.fromLngLat(73.041932, 19.018686))
            .destination(Point.fromLngLat(73.040028, 19.019499))
            .profile(DirectionsCriteria.PROFILE_DRIVING)
            .resource(DirectionsCriteria.RESOURCE_ROUTE)
            .steps(true)
            .alternatives(false)
            .overview(DirectionsCriteria.OVERVIEW_FULL).build()
        mBinding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                MapplsDirectionManager.newInstance(mapplsDirections)
                    .call(object : OnResponseCallback<DirectionsResponse?> {
                        override fun onSuccess(directionsResponse: DirectionsResponse?) {
                            if (directionsResponse != null) {
                                val results = directionsResponse.routes()
                                mBinding.progressBar.visibility = View.GONE
                                if (results.size > 0) {
                                    val routeLegList = results[0].legs()
                                    val legSteps: MutableList<LegStep> = ArrayList()
                                    for (routeLeg in routeLegList!!) {
                                        legSteps.addAll(routeLeg.steps()!!)
                                    }
                                    mBinding.recycler.layoutManager =
                                        LinearLayoutManager(this@DirectionStepActivity)
                                    mBinding.recycler.adapter = StepsAdapter(legSteps)

                                }
                            }
                        }

                        override fun onError(i: Int, s: String) {
                            if (i != ErrorCodes.CANCEL_CALL) {
                                mBinding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@DirectionStepActivity,
                                    "$i ---- $s",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            mBinding.progressBar.visibility = View.GONE
                        }
                    })
            } catch (e: Exception) {
                mBinding.progressBar.visibility = View.GONE
                Toast.makeText(this@DirectionStepActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}