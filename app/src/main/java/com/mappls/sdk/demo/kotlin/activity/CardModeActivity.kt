package com.mappls.sdk.demo.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.kotlin.settings.MapplsPlaceWidgetSetting
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.location.permissions.PermissionsManager
import com.mappls.sdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import com.mappls.sdk.services.api.autosuggest.model.SuggestedSearchAtlas
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult
import java.util.ArrayList


class CardModeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var mapplsMap: MapplsMap? = null
    private var permissionManager: PermissionsManager? = null


    private lateinit var search: TextView

    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_mode_fragment)
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        search = findViewById(R.id.search)
        search.setOnClickListener {
            if (mapplsMap != null) {
                val placeOptions: PlaceOptions = PlaceOptions.builder()
                    .location(MapplsPlaceWidgetSetting.instance.location)
                    .filter(MapplsPlaceWidgetSetting.instance.filter)
                    .hint(MapplsPlaceWidgetSetting.instance.hint)
                    .saveHistory(MapplsPlaceWidgetSetting.instance.isEnableHistory)
                    .enableTextSearch(MapplsPlaceWidgetSetting.instance.isEnableTextSearch)
                    .pod(MapplsPlaceWidgetSetting.instance.pod)
                    .attributionHorizontalAlignment(MapplsPlaceWidgetSetting.instance.signatureVertical)
                    .attributionVerticalAlignment(MapplsPlaceWidgetSetting.instance.signatureHorizontal)
                    .logoSize(MapplsPlaceWidgetSetting.instance.logoSize)
                    .backgroundColor(Color.parseColor("#00FFFFFF"))
                    .toolbarColor(resources.getColor(MapplsPlaceWidgetSetting.instance.toolbarColor))
                    .bridge(MapplsPlaceWidgetSetting.instance.isBridgeEnable)
                    .hyperLocal(MapplsPlaceWidgetSetting.instance.isHyperLocalEnable)
                    .build(PlaceOptions.MODE_CARDS)

                val builder = PlaceAutocomplete.IntentBuilder()
                if (!MapplsPlaceWidgetSetting.instance.isDefault) {
                    builder.placeOptions(placeOptions)
                } else {
                    builder.placeOptions(PlaceOptions.builder().build(PlaceOptions.MODE_CARDS))
                }
                val placeAutocomplete = builder.build(this@CardModeActivity)
                startActivityForResult(placeAutocomplete, 101)
            } else {
                Toast.makeText(this, "Please wait map is loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                val eLocation: ELocation? = PlaceAutocomplete.getPlace(data)
                if (eLocation != null) {
                    if (mapplsMap != null) {
                        mapplsMap?.clear()
                        val latLng =
                            LatLng(
                                eLocation.latitude?.toDouble()!!,
                                eLocation.longitude?.toDouble()!!
                            )
                        mapplsMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng,
                                12.0
                            )
                        )
                        mapplsMap?.addMarker(
                            MarkerOptions().position(latLng).setTitle(eLocation.placeName)
                                .setSnippet(eLocation.placeAddress)
                        )
                    }
                    search.text = eLocation.placeName
                } else {
                    val suggestedSearch: SuggestedSearchAtlas? = PlaceAutocomplete.getSuggestedSearch(data)
                    if(suggestedSearch != null) {
                        callHateOs(suggestedSearch.hyperLink)
                    }
                }
            }
        }
    }
    private fun callHateOs(hyperlink: String) {
        val hateOs = MapplsHateosNearby.builder()
            .hyperlink(hyperlink)
            .build()
        MapplsHateosNearbyManager.newInstance(hateOs).call(object :
            OnResponseCallback<NearbyAtlasResponse> {
            override fun onSuccess(nearbyAtlasResponse: NearbyAtlasResponse?) {
                if (nearbyAtlasResponse != null) {
                    val nearByList = nearbyAtlasResponse.suggestedLocations
                    if (nearByList.size > 0) {
                        addMarker(nearByList)
                    }
                } else {
                    Toast.makeText(this@CardModeActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@CardModeActivity, p1?:"Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addMarker(nearByList: ArrayList<NearbyAtlasResult>) {
        mapplsMap?.clear()
        for (marker in nearByList) {
            if (marker.getLatitude() != null && marker.getLongitude() != null) {
                mapplsMap?.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            marker.getLatitude(),
                            marker.getLongitude()
                        )
                    ).title(marker.getPlaceName())
                )
            } else {
                mapplsMap?.addMarker(
                    MarkerOptions().mapplsPin(
                        marker.mapplsPin
                    ).title(marker.getPlaceName())
                )
            }
        }
    }

    override fun onMapError(p0: Int, p1: String?) {}

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap

        mapplsMap.setMinZoomPreference(4.0)
        mapplsMap.setMaxZoomPreference(18.0)

        mapplsMap.cameraPosition =
            CameraPosition.Builder().target(LatLng(28.0, 77.0)).zoom(4.0).build()
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()

        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()

        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()

        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

}

