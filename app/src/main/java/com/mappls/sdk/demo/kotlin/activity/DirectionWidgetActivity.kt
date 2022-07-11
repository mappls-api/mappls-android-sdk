package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDirectionWidgetBinding
import com.mappls.sdk.demo.kotlin.settings.MapplsDirectionWidgetSetting
import com.mappls.sdk.direction.ui.DirectionCallback
import com.mappls.sdk.direction.ui.DirectionFragment
import com.mappls.sdk.direction.ui.model.DirectionOptions
import com.mappls.sdk.direction.ui.model.DirectionPoint
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.directions.models.DirectionsResponse

class DirectionWidgetActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDirectionWidgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_direction_widget)
        val optionsBuilder = DirectionOptions.builder()
        if (!MapplsDirectionWidgetSetting.instance.isDefault) {
            val options = PlaceOptions.builder()
                .zoom(MapplsDirectionWidgetSetting.instance.zoom)
                .hint(MapplsDirectionWidgetSetting.instance.hint)
                .location(MapplsDirectionWidgetSetting.instance.location)
                .filter(MapplsDirectionWidgetSetting.instance.filter)
                .saveHistory(MapplsDirectionWidgetSetting.instance.isEnableHistory)
                .pod(MapplsDirectionWidgetSetting.instance.pod)
                .attributionHorizontalAlignment(MapplsDirectionWidgetSetting.instance.signatureVertical)
                .attributionVerticalAlignment(MapplsDirectionWidgetSetting.instance.signatureHorizontal)
                .logoSize(MapplsDirectionWidgetSetting.instance.logoSize)
                .tokenizeAddress(MapplsDirectionWidgetSetting.instance.isTokenizeAddress)
                .historyCount(MapplsDirectionWidgetSetting.instance.historyCount)
                .backgroundColor(resources.getColor(MapplsDirectionWidgetSetting.instance.backgroundColor))
                .toolbarColor(resources.getColor(MapplsDirectionWidgetSetting.instance.toolbarColor))
                .build()
            optionsBuilder.searchPlaceOption(options)
                .showStartNavigation(MapplsDirectionWidgetSetting.instance.isShowStartNavigation)
                .steps(MapplsDirectionWidgetSetting.instance.isSteps)
                .resource(MapplsDirectionWidgetSetting.instance.resource)
                .profile(MapplsDirectionWidgetSetting.instance.profile)
                .excludes(MapplsDirectionWidgetSetting.instance.excludes)
                .overview(MapplsDirectionWidgetSetting.instance.overview)
                .showAlternative(MapplsDirectionWidgetSetting.instance.isShowAlternative)
                .searchAlongRoute(MapplsDirectionWidgetSetting.instance.isShowPOISearch)
        }

        val directionFragment = DirectionFragment.newInstance(optionsBuilder.build())
        supportFragmentManager.beginTransaction().add(
            mBinding.fragmentConatiner.id,
            directionFragment,
            DirectionFragment::class.java.simpleName
        ).commit()

        directionFragment.setDirectionCallback(object : DirectionCallback {
            override fun onCancel() {
                finish()
            }

            override fun onStartNavigation(
                directionPoint: DirectionPoint,
                directionPoint1: DirectionPoint,
                list: List<DirectionPoint>,
                directionsResponse: DirectionsResponse,
                i: Int
            ) {
                Toast.makeText(
                    this@DirectionWidgetActivity,
                    "On Navigation Start",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}