package com.mappls.sdk.demo.activity.widgets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDirectionWidgetBinding
import com.mappls.sdk.demo.settings.fragment.DirectionWidgetSettingFragment
import com.mappls.sdk.demo.settings.model.MapplsDirectionWidgetSetting
import com.mappls.sdk.direction.ui.DirectionCallback
import com.mappls.sdk.direction.ui.DirectionFragment
import com.mappls.sdk.direction.ui.model.DirectionOptions
import com.mappls.sdk.direction.ui.model.DirectionPoint
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.directions.models.DirectionsResponse


class DirectionWidgetActivity : AppCompatActivity() {
    lateinit var mBinding:ActivityDirectionWidgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityDirectionWidgetBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.topHeader.headerTitle.text = "Direction Widget"
        mBinding.topHeader.ivSettings.visibility = View.VISIBLE
        mBinding.topHeader.ivSettings.setOnClickListener {
            val fragment = DirectionWidgetSettingFragment()
            replaceFragment(fragment)
        }
        mBinding.topHeader.headerBack.setOnClickListener { finish() }

        val optionsBuilder = DirectionOptions.builder()
        val options = PlaceOptions.builder()

                .zoom(MapplsDirectionWidgetSetting.instance.zoom)
                .hint(MapplsDirectionWidgetSetting.instance.hint)
                .location(MapplsDirectionWidgetSetting.instance.location)
                .filter(MapplsDirectionWidgetSetting.instance.filter)
                .saveHistory(MapplsDirectionWidgetSetting.instance.enableHistory)
                .pod(MapplsDirectionWidgetSetting.instance.pod)
                .attributionHorizontalAlignment(
                    MapplsDirectionWidgetSetting.instance.signatureVertical
                )
                .attributionVerticalAlignment(
                    MapplsDirectionWidgetSetting.instance.signatureHorizontal
                )
                .logoSize(MapplsDirectionWidgetSetting.instance.logoSize)
                .tokenizeAddress(MapplsDirectionWidgetSetting.instance.tokenizeAddress)
                .historyCount(MapplsDirectionWidgetSetting.instance.historyCount)
                .backgroundColor(MapplsDirectionWidgetSetting.instance.backgroundColor)
                .toolbarColor(MapplsDirectionWidgetSetting.instance.toolbarColor)
//                .toolbarTintColor(MapplsDirectionWidgetSetting.instance.toolbarTintColor)
//                .resultBackgroundColor(
//                    MapplsDirectionWidgetSetting.instance.backgroundColor
//                )
//                .placeNameTextColor(
//                    MapplsDirectionWidgetSetting.instance.placeNameTextColor
//                )
//                .attributionBackgroundColor(
//                    MapplsDirectionWidgetSetting.instance.backgroundColor
//                )
//                .addressTextColor(MapplsDirectionWidgetSetting.instance.addressTextColor)
//                .poweredByTextColor(
//                    MapplsDirectionWidgetSetting.instance.addressTextColor
//                )
                .build()
        optionsBuilder.searchPlaceOption(options)
            .showDefaultMap(true)

            .showStartNavigation(MapplsDirectionWidgetSetting.instance.showStartNavigation)
            .showAlternative(MapplsDirectionWidgetSetting.instance.showAlternative)
            .searchAlongRoute(MapplsDirectionWidgetSetting.instance.isShowPOISearch)
            .steps(MapplsDirectionWidgetSetting.instance.steps)

            .resource(MapplsDirectionWidgetSetting.instance.resource)
            .profile(MapplsDirectionWidgetSetting.instance.profile)
            .excludes(MapplsDirectionWidgetSetting.instance.excludes)
            .overview(MapplsDirectionWidgetSetting.instance.overview)


        mBinding.directionWidget.setOnClickListener {
                val directionFragment = DirectionFragment.newInstance(optionsBuilder.build())
            replaceFragment(directionFragment)
//                supportFragmentManager.beginTransaction().add(mBinding.transitFragmentContainer.id, directionFragment, DirectionFragment::class.java.simpleName).commit()
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
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.transitFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}