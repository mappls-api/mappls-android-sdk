package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDirectionPluginBinding
import com.mappls.sdk.direction.ui.DirectionCallback
import com.mappls.sdk.direction.ui.DirectionFragment
import com.mappls.sdk.direction.ui.model.DirectionOptions
import com.mappls.sdk.direction.ui.model.DirectionPoint
import com.mappls.sdk.services.api.directions.models.DirectionsResponse


class DirectionPluginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDirectionPluginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_direction_plugin)

        mBinding.btnOpenDirectionFragmentUi.setOnClickListener { v ->


            val options = DirectionOptions.builder().build()

            val directionFragment = DirectionFragment.newInstance(options)
            supportFragmentManager.
            beginTransaction().
            add(R.id.container, directionFragment, DirectionFragment::class.java.simpleName).
            addToBackStack(null).
            commit()

            directionFragment.setDirectionCallback(object : DirectionCallback {
                override fun onCancel() {
                    supportFragmentManager.beginTransaction().remove(directionFragment).commit()
                }

                override fun onStartNavigation(directionPoint: DirectionPoint, directionPoint1: DirectionPoint, list: List<DirectionPoint>, directionsResponse: DirectionsResponse, i: Int) {
                    Toast.makeText(this@DirectionPluginActivity, "On Navigation Start", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}