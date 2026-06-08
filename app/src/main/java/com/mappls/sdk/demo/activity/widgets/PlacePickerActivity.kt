package com.mappls.sdk.demo.activity.widgets

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityPlacePickerBinding
import com.mappls.sdk.demo.settings.fragment.PlacePickerSettingsFragment
import com.mappls.sdk.demo.settings.model.MapplsPlacePickerSettings
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.plugins.places.placepicker.PlacePicker
import com.mappls.sdk.plugins.places.placepicker.model.PlacePickerOptions


class PlacePickerActivity : AppCompatActivity() {

    lateinit var mBinding:ActivityPlacePickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityPlacePickerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.topHeader.headerTitle.text = "Place Picker"
        mBinding.topHeader.ivSettings.visibility = View.VISIBLE
        mBinding.topHeader.ivSettings.setOnClickListener {
            val fragment = PlacePickerSettingsFragment()
            replaceFragment(fragment)
        }
        mBinding.topHeader.headerBack.setOnClickListener { finish() }

        mBinding.placePicker.setOnClickListener {
            val intentBuilder = PlacePicker.IntentBuilder()
            val options = PlaceOptions.builder()
                .hint(MapplsPlacePickerSettings.instance.hint)
                .location(MapplsPlacePickerSettings.instance.location)
                .filter(MapplsPlacePickerSettings.instance.filter)
                .saveHistory(MapplsPlacePickerSettings.instance.enableHistory)
                .pod(MapplsPlacePickerSettings.instance.pod)
                .attributionHorizontalAlignment(MapplsPlacePickerSettings.instance.signatureVertical)
                .attributionVerticalAlignment(MapplsPlacePickerSettings.instance.signatureHorizontal)
                .logoSize(MapplsPlacePickerSettings.instance.logoSize)
                .tokenizeAddress(MapplsPlacePickerSettings.instance.tokenizeAddress)
                .historyCount(MapplsPlacePickerSettings.instance.historyCount)
                .backgroundColor(
                    resources.getColor(
                        MapplsPlacePickerSettings.instance.backgroundColor
                    )
                )
                .toolbarColor(
                    resources.getColor(
                        MapplsPlacePickerSettings.instance.toolbarColor
                    )
                )
                .build()

            intentBuilder.placeOptions(
                PlacePickerOptions.builder()
                    .toolbarColor(
                        resources.getColor(
                            MapplsPlacePickerSettings.instance.pickerToolbarColor
                        )
                    )
                    .includeDeviceLocationButton(
                        MapplsPlacePickerSettings.instance.includeDeviceLocation
                    )
                    .includeSearch(MapplsPlacePickerSettings.instance.includeSearch)
                    .searchPlaceOption(options)
                    .statingCameraPosition(
                        CameraPosition.Builder()
                            .target(LatLng(25.321684, 82.987289)).zoom(16.0).build()
                    )
                    .build()
            )

            val intent = intentBuilder.build(this@PlacePickerActivity)
            startActivityForResult(intent, 101)

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