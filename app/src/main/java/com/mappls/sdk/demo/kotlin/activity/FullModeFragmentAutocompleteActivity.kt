package com.mappls.sdk.demo.kotlin.activity

import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
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
import com.mappls.sdk.plugins.places.autocomplete.model.MapplsFavoritePlace
import com.mappls.sdk.plugins.places.autocomplete.model.PlaceOptions
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment
import com.mappls.sdk.plugins.places.autocomplete.ui.PlaceSelectionListener
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult


class FullModeFragmentAutocompleteActivity : AppCompatActivity(), OnMapReadyCallback {

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
                    .enableTextSearch(MapplsPlaceWidgetSetting.instance.isEnableTextSearch)
                    .pod(MapplsPlaceWidgetSetting.instance.pod)
                    .saveHistory(MapplsPlaceWidgetSetting.instance.isEnableHistory)
                    .isShowCurrentLocation(MapplsPlaceWidgetSetting.instance.isEnableLocation)
                    .attributionHorizontalAlignment(MapplsPlaceWidgetSetting.instance.signatureVertical)
                    .attributionVerticalAlignment(MapplsPlaceWidgetSetting.instance.signatureHorizontal)
                    .logoSize(MapplsPlaceWidgetSetting.instance.logoSize)
                    .backgroundColor(resources.getColor(MapplsPlaceWidgetSetting.instance.backgroundColor))
                    .bridge(MapplsPlaceWidgetSetting.instance.isBridgeEnable)
                    .hyperLocal(MapplsPlaceWidgetSetting.instance.isHyperLocalEnable)
                    .toolbarColor(resources.getColor(MapplsPlaceWidgetSetting.instance.toolbarColor))
                    .build()

                val placeAutocompleteFragment: PlaceAutocompleteFragment =
                    if (MapplsPlaceWidgetSetting.instance.isDefault) {
                        PlaceAutocompleteFragment.newInstance()
                    } else {
                        PlaceAutocompleteFragment.newInstance(placeOptions)
                    }
                placeAutocompleteFragment.setOnPlaceSelectedListener(object :
                    PlaceSelectionListener {
                    override fun onCancel() {
                        supportFragmentManager.popBackStack(
                            PlaceAutocompleteFragment::class.java.simpleName,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }

                    override fun requestForCurrentLocation() {
                        Toast.makeText(
                            this@FullModeFragmentAutocompleteActivity,
                            "Please provide current location",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onPlaceSelected(eLocation: ELocation?) {
                        if (mapplsMap != null) {
                            mapplsMap?.clear()
                            if (eLocation?.latitude != null && eLocation.longitude != null) {
                                val latLng = LatLng(
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
                            } else {
                                mapplsMap?.addMarker(
                                    MarkerOptions().mapplsPin(eLocation?.mapplsPin)
                                        .title(eLocation?.placeName)
                                        .snippet(eLocation?.placeAddress)
                                )
                            }
                            search.text = eLocation?.placeName
                            supportFragmentManager.popBackStack(
                                PlaceAutocompleteFragment::class.java.simpleName,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                            )
                            if(MapplsPlaceWidgetSetting.instance.isEnableShowFavorite) {
                                if (eLocation != null) {
                                    addFavoriteDialog(eLocation)
                                }
                            }
                        }
                    }

                    override fun onFavoritePlaceSelected(p0: MapplsFavoritePlace?) {
                        if(p0?.latitude != null && p0.longitude != null) {
                            val latLng =
                                LatLng(
                                    p0.longitude,
                                    p0.longitude
                                )
                            mapplsMap?.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    latLng,
                                    12.0
                                )
                            )
                            mapplsMap?.addMarker(
                                MarkerOptions().position(latLng).setTitle(p0.placeName)
                                    .setSnippet(p0.placeAddress)
                            )
                        }
                        search.text = p0?.placeName
                        supportFragmentManager.popBackStack(
                            PlaceAutocompleteFragment::class.java.simpleName,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }

                })
                placeAutocompleteFragment.setSuggestedSearchSelectionListener {
                    callHateOs(it.hyperLink)
                }
                supportFragmentManager.beginTransaction().add(
                    R.id.fragment_container,
                    placeAutocompleteFragment,
                    PlaceAutocompleteFragment::class.java.simpleName
                )
                    .addToBackStack(PlaceAutocompleteFragment::class.java.simpleName)
                    .commit()
            } else {
                Toast.makeText(this, "Please wait map is loading", Toast.LENGTH_SHORT).show()
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
                val favoritePlace = MapplsFavoritePlace(
                    eLocation.placeName,
                    eLocation.placeAddress,
                    eLocation.latitude,
                    eLocation.longitude
                )
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
                    Toast.makeText(
                        this@FullModeFragmentAutocompleteActivity,
                        "Not able to get value, Try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(
                    this@FullModeFragmentAutocompleteActivity,
                    p1 ?: "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
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
