package com.mappls.sdk.demo.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.kotlin.settings.MapplsPlacePickerSetting
import com.mappls.sdk.demo.kotlin.settingscreen.PlacePickerSettingsActivity
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.plugins.places.placepicker.PlacePicker
import com.mappls.sdk.plugins.places.placepicker.model.PlacePickerOptions
import com.mappls.sdk.services.api.Place

class PickerActivity : AppCompatActivity() {

    private lateinit var tvSelectedPlace: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_picker)
        tvSelectedPlace = findViewById(R.id.selected_place)
        val button: Button = findViewById(R.id.place_picker)
        button.setOnClickListener {
            val intentBuilder = PlacePicker.IntentBuilder()

            if (!MapplsPlacePickerSetting.instance.isDefault) {
                val options = PlaceOptions.builder()
                        .zoom(MapplsPlacePickerSetting.instance.zoom)
                        .hint(MapplsPlacePickerSetting.instance.hint)
                        .location(MapplsPlacePickerSetting.instance.location)
                        .filter(MapplsPlacePickerSetting.instance.filter)
                        .saveHistory(MapplsPlacePickerSetting.instance.isEnableHistory)
                        .pod(MapplsPlacePickerSetting.instance.pod)
                        .attributionHorizontalAlignment(MapplsPlacePickerSetting.instance.signatureVertical)
                        .attributionVerticalAlignment(MapplsPlacePickerSetting.instance.signatureHorizontal)
                        .logoSize(MapplsPlacePickerSetting.instance.logoSize)
                        .tokenizeAddress(MapplsPlacePickerSetting.instance.isTokenizeAddress)
                        .historyCount(MapplsPlacePickerSetting.instance.historyCount)
                        .backgroundColor(resources.getColor(MapplsPlacePickerSetting.instance.backgroundColor))
                        .toolbarColor(resources.getColor(MapplsPlacePickerSetting.instance.toolbarColor))
                        .build()
                intentBuilder.placeOptions(PlacePickerOptions.builder()
                        .toolbarColor(MapplsPlacePickerSetting.instance.pickerToolbarColor)
                        .includeDeviceLocationButton(MapplsPlacePickerSetting.instance.isIncludeDeviceLocation)
                        .includeSearch(MapplsPlacePickerSetting.instance.isIncludeSearch)
                        .searchPlaceOption(options)
                        .statingCameraPosition(CameraPosition.Builder()
                                .target(LatLng(27.00, 75.0)).zoom(16.0).build())
                        .build())
            } else {
                intentBuilder.placeOptions(PlacePickerOptions.builder()
                        .statingCameraPosition(CameraPosition.Builder()
                                .target(LatLng(27.00, 75.0)).zoom(16.0).build())
                        .build())
            }

            val intent = intentBuilder.build(this@PickerActivity)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val place: Place? = PlacePicker.getPlace(data!!)
            tvSelectedPlace.setText(place?.formattedAddress)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.widget_setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.widget_setting) {
            startActivity(Intent(this, PlacePickerSettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}