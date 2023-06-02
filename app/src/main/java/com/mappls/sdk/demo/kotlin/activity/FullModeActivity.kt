package com.mappls.sdk.demo.kotlin.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.utils.AddFavoriteManager
import com.mappls.sdk.demo.kotlin.settings.MapplsPlaceWidgetSetting
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.plugins.places.autocomplete.PlaceAutocomplete
import com.mappls.sdk.plugins.places.autocomplete.model.MapplsFavoritePlace
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import com.mappls.sdk.services.api.autosuggest.model.SuggestedSearchAtlas
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult


class FullModeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var mapplsMap: MapplsMap? = null


    private lateinit var search: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_mode_fragment)
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        search = findViewById(R.id.search)
        search.setOnClickListener {
            if (mapplsMap != null) {
                val placeOptions: PlaceOptions = PlaceOptions.builder()
                    .favoritePlaces(AddFavoriteManager.getInstance().list)
                    .historyCount(MapplsPlaceWidgetSetting.instance.historyCount)
                    .debounce(MapplsPlaceWidgetSetting.instance.deBounce)
                    .location(MapplsPlaceWidgetSetting.instance.location)
                    .filter(MapplsPlaceWidgetSetting.instance.filter)
                    .hint(MapplsPlaceWidgetSetting.instance.hint)
                    .saveHistory(MapplsPlaceWidgetSetting.instance.isEnableHistory)
                    .isShowCurrentLocation(MapplsPlaceWidgetSetting.instance.isEnableLocation)
                    .enableTextSearch(MapplsPlaceWidgetSetting.instance.isEnableTextSearch)
                    .pod(MapplsPlaceWidgetSetting.instance.pod)
                    .attributionHorizontalAlignment(MapplsPlaceWidgetSetting.instance.signatureVertical)
                    .attributionVerticalAlignment(MapplsPlaceWidgetSetting.instance.signatureHorizontal)
                    .logoSize(MapplsPlaceWidgetSetting.instance.logoSize)
                    .backgroundColor(resources.getColor(MapplsPlaceWidgetSetting.instance.backgroundColor))
                    .toolbarColor(resources.getColor(MapplsPlaceWidgetSetting.instance.toolbarColor))
                    .bridge(MapplsPlaceWidgetSetting.instance.isBridgeEnable)
                    .hyperLocal(MapplsPlaceWidgetSetting.instance.isHyperLocalEnable)
                    .build(PlaceOptions.MODE_CARDS)

                val builder = PlaceAutocomplete.IntentBuilder()
                if (!MapplsPlaceWidgetSetting.instance.isDefault) {
                    builder.placeOptions(placeOptions)
                }
                val placeAutocomplete = builder.build(this@FullModeActivity)
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
                    if(MapplsPlaceWidgetSetting.instance.isEnableShowFavorite) {
                        addFavoriteDialog(eLocation)
                    }
                } else if (PlaceAutocomplete.getSuggestedSearch(data) != null) {
                    val suggestedSearch: SuggestedSearchAtlas =
                        PlaceAutocomplete.getSuggestedSearch(data)

                    callHateOs(suggestedSearch.hyperLink)
                } else if (PlaceAutocomplete.getFavoritePlace(data) != null) {
                    val favoritePlace = PlaceAutocomplete.getFavoritePlace(data)
                    if (mapplsMap != null) {
                        mapplsMap?.clear()
                        val latLng =
                            LatLng(
                                favoritePlace.longitude,
                                favoritePlace.longitude?.toDouble()!!
                            )
                        mapplsMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng,
                                12.0
                            )
                        )
                        mapplsMap?.addMarker(
                            MarkerOptions().position(latLng).setTitle(favoritePlace.placeName)
                                .setSnippet(favoritePlace.placeAddress)
                        )
                    }
                    search.text = favoritePlace.placeName
                } else if (PlaceAutocomplete.isRequestForCurrentLocation(data)) {
                    Toast.makeText(
                        this@FullModeActivity,
                        "Please provide current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun addFavoriteDialog(eLocation: ELocation) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to add this place as favorite ?")
        builder.setTitle("Add Favorite")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                val favoritePlace = MapplsFavoritePlace(eLocation.placeName,
                    eLocation.placeAddress,
                    eLocation.latitude,
                    eLocation.longitude)
                favoritePlace.mapplsPin = eLocation.mapplsPin
                AddFavoriteManager.getInstance().addToArray(favoritePlace)
            })
        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int -> dialog.cancel() } as DialogInterface.OnClickListener)
        val alertDialog = builder.create()
        alertDialog.show()
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
                    Toast.makeText(this@FullModeActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@FullModeActivity, p1?:"Something went wrong", Toast.LENGTH_SHORT).show()
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

