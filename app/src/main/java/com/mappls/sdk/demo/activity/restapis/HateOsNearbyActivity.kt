package com.mappls.sdk.demo.activity.restapis

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.restapis.AutoSuggestActivity
import com.mappls.sdk.demo.activity.restapis.NearbyActivity
import com.mappls.sdk.demo.adapter.AutoSuggestAdapter
import com.mappls.sdk.demo.databinding.ActivityAutoSuggestBinding
import com.mappls.sdk.demo.model.SearchResultModel
import com.mappls.sdk.demo.utils.SearchType
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.maps.location.LocationComponentActivationOptions
import com.mappls.sdk.maps.location.engine.LocationEngineCallback
import com.mappls.sdk.maps.location.engine.LocationEngineRequest
import com.mappls.sdk.maps.location.engine.LocationEngineResult
import com.mappls.sdk.maps.location.modes.CameraMode
import com.mappls.sdk.maps.location.modes.RenderMode
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.MapplsAutoSuggest
import com.mappls.sdk.services.api.autosuggest.MapplsAutosuggestManager
import com.mappls.sdk.services.api.autosuggest.model.AutoSuggestAtlasResponse
import com.mappls.sdk.services.api.autosuggest.model.SuggestedSearchAtlas
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.utils.ErrorCodes
import kotlinx.coroutines.launch
import java.lang.Exception

class HateOsNearbyActivity : AppCompatActivity(), OnMapReadyCallback, TextWatcher,
    LocationEngineCallback<LocationEngineResult> {

    private lateinit var mBinding: ActivityAutoSuggestBinding
    private var autosuggestManager: MapplsAutosuggestManager? = null
    private var hateosNearbyManager: MapplsHateosNearbyManager? = null
    private lateinit var mAdapter: AutoSuggestAdapter
    private var showResponse = false
    private var hasResponse = false
    private var mMapplsMap: MapplsMap? = null
    private var currentLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityAutoSuggestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.autosuggestHeader.headerTitle.text = "HateOs Nearby"
        mBinding.autosuggestHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.searchEt.addTextChangedListener(this)
        showResponse(showResponse)
        mBinding.autosuggestShowHideResponseLayout.tvShowResponse.setOnClickListener {
            showResponse(true)
        }
        mBinding.autosuggestShowHideResponseLayout.tvShowData.setOnClickListener {
            showResponse(false)
        }
        mAdapter = AutoSuggestAdapter() {
            hasResponse = false
            mAdapter.setSearchResults(listOf())
            mBinding.autosuggestJsonResponse.text = ""
            mBinding.autosuggestRv.visibility = View.GONE
            mBinding.autosuggestJsonResponseSv.visibility = View.GONE
            mMapplsMap?.clear()
            if (it.type == SearchType.SUGGESTED_SEARCH) {
                callHateOsNearby(it.suggestedSearchAtlas)
            } else {
                Toast.makeText(this, "You click on Search Item", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.autosuggestRv.adapter = mAdapter
        mBinding.autosuggestRv.layoutManager = LinearLayoutManager(this)
    }

    private fun callHateOsNearby(search: SuggestedSearchAtlas?) {
        if(search != null) {
            mBinding.progressbar.visibility = View.VISIBLE
            hasResponse = false
            mBinding.autosuggestJsonResponse.text = ""
            mBinding.autosuggestRv.visibility = View.GONE
            mBinding.autosuggestJsonResponseSv.visibility = View.GONE
            autosuggestManager?.cancel()
            hateosNearbyManager?.cancel()
            mBinding.autosuggestRv.visibility = View.GONE
            val builder = MapplsHateosNearby.builder()
                .hyperlink(search.hyperLink)
            hateosNearbyManager = MapplsHateosNearbyManager.newInstance(builder.build())
            hateosNearbyManager?.call(object: OnResponseCallback<NearbyAtlasResponse> {
                override fun onSuccess(nearbyAtlasResponse: NearbyAtlasResponse?) {
                    mBinding.progressbar.visibility = View.GONE
                    mBinding.autosuggestJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(nearbyAtlasResponse)
                    nearbyAtlasResponse?.suggestedLocations?.let {
                        hasResponse = true
                        var isLatLngAvailable = false
                        it.forEach {
                            if(it.latitude != null && it.longitude != null) {
                                isLatLngAvailable = true
                                mMapplsMap?.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                            } else {
                                mMapplsMap?.addMarker(MarkerOptions().mapplsPin(it.mapplsPin))
                            }
                        }
                        if(isLatLngAvailable) {
                            mMapplsMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(
                                LatLngBounds.Builder().includes(it.map { LatLng(it.latitude, it.longitude) }).build(),
                                20, 20, 20, 20
                            ))
                        } else {
                            mMapplsMap?.animateCamera(CameraMapplsPinUpdateFactory.newMapplsPinBounds(it.map { it.mapplsPin },
                                20, 20, 20, 20
                            ))
                        }
                        if(showResponse) {
                            mBinding.autosuggestJsonResponseSv.visibility = View.VISIBLE
                        }
                    } ?: run {
                        mBinding.progressbar.visibility = View.GONE
                        Toast.makeText(this@HateOsNearbyActivity, "Response is null", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    if (p0 != ErrorCodes.CANCEL_CALL) {
                        mBinding.progressbar.visibility = View.GONE
                        Toast.makeText(this@HateOsNearbyActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
        }
    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.autosuggestShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.autosuggestShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if (hasResponse) {
            if (!showResponse) {
                mBinding.autosuggestJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.autosuggestJsonResponseSv.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.searchEt.removeTextChangedListener(this)
        mMapplsMap?.locationComponent?.locationEngine?.removeLocationUpdates(this)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mMapplsMap = mapplsMap
        mapplsMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    22.553147478403194,
                    77.23388671875
                ), 4.0
            )
        )
        mapplsMap.getStyle {
            enableCurrentLocation(it)
        }
    }

    private fun enableCurrentLocation(style: Style) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, style)
                    .build()
            mMapplsMap?.locationComponent?.activateLocationComponent(
                locationComponentActivationOptions
            )
            mMapplsMap?.locationComponent?.isLocationComponentEnabled = true
            mMapplsMap?.locationComponent?.renderMode = RenderMode.COMPASS
            mMapplsMap?.locationComponent?.cameraMode = CameraMode.NONE
            mMapplsMap?.locationComponent?.locationEngine?.requestLocationUpdates(
                LocationEngineRequest.Builder(5000).setDisplacement(500f).build(),
                this,
                Looper.getMainLooper()
            )
        }
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    private fun callAutoSuggest(s: CharSequence?) {
        hasResponse = false
        autosuggestManager?.cancel()
        hateosNearbyManager?.cancel()
        mAdapter.setSearchResults(listOf())
        mBinding.autosuggestJsonResponse.text = ""
        mBinding.autosuggestRv.visibility = View.GONE
        mBinding.autosuggestJsonResponseSv.visibility = View.GONE
        mBinding.progressbar.visibility = View.VISIBLE
        if ((s?.length ?: 0) > 2) {
            val autoSuggestBuilder = MapplsAutoSuggest.builder()
                .query(s.toString())
                .bridge(true)
            currentLocation?.let {
                autoSuggestBuilder.setLocation(it.latitude, it.longitude)
            } ?: run {
                Toast.makeText(this@HateOsNearbyActivity, "Please wait for Current Location", Toast.LENGTH_SHORT).show()
                return
            }
            mBinding.autosuggestRv.visibility = View.GONE

            autosuggestManager = MapplsAutosuggestManager.newInstance(autoSuggestBuilder.build())
            autosuggestManager?.call(object : OnResponseCallback<AutoSuggestAtlasResponse> {
                override fun onSuccess(autoSuggestAtlasResponse: AutoSuggestAtlasResponse?) {

                    if (((autoSuggestAtlasResponse?.suggestedLocations?.size
                            ?: 0) > 0) || ((autoSuggestAtlasResponse?.suggestedSearches?.size
                            ?: 0) > 0)
                    ) {
                        mBinding.progressbar.visibility = View.GONE
                        mBinding.autosuggestRv.visibility = View.VISIBLE
                        val searchResults = mutableListOf<SearchResultModel>()
                        searchResults.addAll(
                            (autoSuggestAtlasResponse?.suggestedSearches ?: listOf()).map {
                                SearchResultModel(
                                    SearchType.SUGGESTED_SEARCH, suggestedSearchAtlas = it
                                )
                            })
                        searchResults.addAll(
                            (autoSuggestAtlasResponse?.suggestedLocations ?: listOf()).map {
                                SearchResultModel(
                                    SearchType.SEARCH, eLocation = it
                                )
                            })

                        mAdapter.setSearchResults(searchResults)

                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    if (p0 != ErrorCodes.CANCEL_CALL) {
                        mBinding.progressbar.visibility = View.GONE
                        Toast.makeText(this@HateOsNearbyActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
        }
    }


    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {


    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
//        lifecycleScope.cancel()
        lifecycleScope.launch {
//            delay(300)
            callAutoSuggest(s)
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onSuccess(p0: LocationEngineResult?) {
        if(p0?.lastLocation != null) {
            this.currentLocation = p0.lastLocation
        }
    }

    override fun onFailure(p0: Exception) {

    }
}