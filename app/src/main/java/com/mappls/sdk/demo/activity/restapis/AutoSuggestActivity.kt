package com.mappls.sdk.demo.activity.restapis

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import com.mappls.sdk.demo.adapter.AutoSuggestAdapter
import com.mappls.sdk.demo.databinding.ActivityAutoSuggestBinding
import com.mappls.sdk.demo.model.SearchResultModel
import com.mappls.sdk.demo.settings.fragment.AutosuggestSettingsFragment
import com.mappls.sdk.demo.settings.model.MapplsAutosuggestApiSettings
import com.mappls.sdk.demo.utils.SearchType
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraMapplsPinUpdateFactory
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
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
import com.mappls.sdk.services.api.textsearch.MapplsTextSearch
import com.mappls.sdk.services.api.textsearch.MapplsTextSearchManager
import com.mappls.sdk.services.utils.ErrorCodes
import kotlinx.coroutines.launch
import java.lang.Exception

class AutoSuggestActivity : AppCompatActivity(), OnMapReadyCallback, TextWatcher, TextView.OnEditorActionListener, LocationEngineCallback<LocationEngineResult> {

    private lateinit var mBinding: ActivityAutoSuggestBinding
    private var autosuggestManager: MapplsAutosuggestManager? = null
    private var textSearchManager: MapplsTextSearchManager? = null
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

        mBinding.autosuggestHeader.headerTitle.text = "Autosuggest"
        mBinding.autosuggestHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.autosuggestHeader.ivSettings.visibility = View.GONE
//        mBinding.autosuggestHeader.ivSettings.setOnClickListener {
//            val fragment = AutosuggestSettingsFragment()
//            replaceFragment(fragment)
//        }
        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.searchEt.addTextChangedListener(this)
        mBinding.searchEt.setOnEditorActionListener(this)
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
            if(it.type == SearchType.SEARCH) {
                val eLocation = it.eLocation
                if (eLocation?.latitude != null && eLocation.longitude != null) {
                    mMapplsMap?.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                eLocation.latitude,
                                eLocation.longitude
                            )
                        )
                    )
                    mMapplsMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                eLocation.latitude,
                                eLocation.longitude
                            ), 12.0
                        )
                    )
                } else if(eLocation?.mapplsPin != null) {
                    mMapplsMap?.addMarker(MarkerOptions().mapplsPin(eLocation.mapplsPin))
                    mMapplsMap?.animateCamera(
                        CameraMapplsPinUpdateFactory.newMapplsPinZoom(
                            eLocation.mapplsPin,
                            12.0
                        )
                    )
                }
            } else {
                Toast.makeText(this, "You click on Suggested Search", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.autosuggestRv.adapter = mAdapter
        mBinding.autosuggestRv.layoutManager = LinearLayoutManager(this)
    }

    private fun showResponse(showResponse: Boolean) {
        this.showResponse = showResponse
        mBinding.autosuggestShowHideResponseLayout.tvShowResponse.isEnabled = !showResponse
        mBinding.autosuggestShowHideResponseLayout.tvShowData.isEnabled = showResponse
        if(hasResponse) {
            if(!showResponse) {
                mBinding.autosuggestRv.visibility = View.VISIBLE
                mBinding.autosuggestJsonResponseSv.visibility = View.GONE
            } else {
                mBinding.autosuggestJsonResponseSv.visibility = View.VISIBLE
                mBinding.autosuggestRv.visibility = View.GONE
            }
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.autosuggestFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
        mapplsMap.getStyle {
            enableCurrentLocation(it)
        }
    }

    private fun enableCurrentLocation(style: Style) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
                LocationEngineRequest.Builder(5000).setDisplacement(500f).build(), this, Looper.getMainLooper())
        }
    }

    override fun onMapError(p0: Int, p1: String?) {

    }

    private fun callAutoSuggest(s: CharSequence?) {
        hasResponse = false
        textSearchManager?.cancel()
        autosuggestManager?.cancel()
        mBinding.progressbar.visibility = View.VISIBLE
        mAdapter.setSearchResults(listOf())
        mBinding.autosuggestJsonResponse.text = ""
        mBinding.autosuggestRv.visibility = View.GONE
        mBinding.autosuggestJsonResponseSv.visibility = View.GONE
        if((s?.length?:0) > 2) {
            val autoSuggestBuilder = MapplsAutoSuggest.builder()
                .query(s.toString())
//            if(MapplsAutosuggestApiSettings.instance.enableCurrentLocation) {
//                currentLocation?.let {
//                    autoSuggestBuilder.setLocation(it.latitude, it.longitude)
//                }
//            } else {
//                val customLocation = MapplsAutosuggestApiSettings.instance.customLocation
//                customLocation ?.let {
//                    autoSuggestBuilder.setLocation(it.latitude, it.longitude)
//                }
//            }
//            if(MapplsAutosuggestApiSettings.instance.tokenizeAddress) {
//                autoSuggestBuilder.tokenizeAddress(MapplsAutosuggestApiSettings.instance.tokenizeAddress)
//            }
//            if(MapplsAutosuggestApiSettings.instance.bridge) {
//                autoSuggestBuilder.bridge(MapplsAutosuggestApiSettings.instance.bridge)
//            }
//            if(MapplsAutosuggestApiSettings.instance.enableMapCenter) {
//                mMapplsMap?.cameraPosition?.target?.let {
//                    autoSuggestBuilder.setMapCentre(it.latitude, it.longitude)
//                }
//            }
//            if(MapplsAutosuggestApiSettings.instance.isPrimary != null) {
//                autoSuggestBuilder.isPrimary(MapplsAutosuggestApiSettings.instance.isPrimary)
//            }
//            if(MapplsAutosuggestApiSettings.instance.zoom != null) {
//                autoSuggestBuilder.zoom(MapplsAutosuggestApiSettings.instance.zoom)
//            }
//            if(MapplsAutosuggestApiSettings.instance.pod != null) {
//                autoSuggestBuilder.pod(MapplsAutosuggestApiSettings.instance.pod)
//            }
//            if(MapplsAutosuggestApiSettings.instance.filter != null) {
//                autoSuggestBuilder.filter(MapplsAutosuggestApiSettings.instance.filter)
//            }
//            if(MapplsAutosuggestApiSettings.instance.hyperLocal) {
//                autoSuggestBuilder.hyperLocal(MapplsAutosuggestApiSettings.instance.hyperLocal)
//            }
//            if(MapplsAutosuggestApiSettings.instance.explain) {
//                autoSuggestBuilder.explain(MapplsAutosuggestApiSettings.instance.explain)
//            }
//            if(MapplsAutosuggestApiSettings.instance.responseLang != null) {
//                autoSuggestBuilder.responseLang(MapplsAutosuggestApiSettings.instance.responseLang)
//            }

            autosuggestManager = MapplsAutosuggestManager.newInstance(autoSuggestBuilder.build())
            autosuggestManager?.call(object: OnResponseCallback<AutoSuggestAtlasResponse> {
                override fun onSuccess(autoSuggestAtlasResponse: AutoSuggestAtlasResponse?) {
                    mBinding.autosuggestJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(autoSuggestAtlasResponse)
                    mBinding.progressbar.visibility = View.GONE
                    if(((autoSuggestAtlasResponse?.suggestedLocations?.size?:0) > 0) || ((autoSuggestAtlasResponse?.suggestedSearches?.size?:0) > 0)) {
                        hasResponse = true

                        if(!showResponse) {
                            mBinding.autosuggestRv.visibility = View.VISIBLE
                        } else {
                            mBinding.autosuggestJsonResponseSv.visibility = View.VISIBLE
                        }
                        val searchResults = mutableListOf<SearchResultModel>()
                        searchResults.addAll((autoSuggestAtlasResponse?.suggestedSearches?:listOf()).map { SearchResultModel(
                            SearchType.SUGGESTED_SEARCH, suggestedSearchAtlas = it) })
                        searchResults.addAll((autoSuggestAtlasResponse?.suggestedLocations?:listOf()).map { SearchResultModel(
                            SearchType.SEARCH, eLocation = it) })

                        mAdapter.setSearchResults(searchResults)

                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    if(p0 != ErrorCodes.CANCEL_CALL) {
                        mBinding.progressbar.visibility = View.GONE
                        Toast.makeText(this@AutoSuggestActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
        }
    }

    private fun callTextSearch(s: CharSequence?) {
        hasResponse = false
        textSearchManager?.cancel()
        autosuggestManager?.cancel()
        mAdapter.setSearchResults(listOf())
        mBinding.autosuggestRv.visibility = View.GONE
        mBinding.progressbar.visibility = View.VISIBLE
        mBinding.autosuggestJsonResponse.text = ""
        mBinding.autosuggestJsonResponseSv.visibility = View.GONE
        if((s?.length?:0) > 2) {
            val textSearchBuilder = MapplsTextSearch.builder()
                .query(s.toString())
//            if(MapplsAutosuggestApiSettings.instance.enableCurrentLocation) {
//                currentLocation?.let {
//                    textSearchBuilder.setLocation(it.latitude, it.longitude)
//                }
//            } else {
//                val customLocation = MapplsAutosuggestApiSettings.instance.customLocation
//                customLocation ?.let {
//                    textSearchBuilder.setLocation(it.latitude, it.longitude)
//                }
//            }
//            if(MapplsAutosuggestApiSettings.instance.bridge) {
//                textSearchBuilder.bridge(MapplsAutosuggestApiSettings.instance.bridge)
//            }
//            if(MapplsAutosuggestApiSettings.instance.filter != null) {
//                textSearchBuilder.filter(MapplsAutosuggestApiSettings.instance.filter)
//            }
//            if(MapplsAutosuggestApiSettings.instance.explain) {
//                textSearchBuilder.explain(MapplsAutosuggestApiSettings.instance.explain)
//            }
            textSearchManager = MapplsTextSearchManager.newInstance(textSearchBuilder.build())
            textSearchManager?.call(object: OnResponseCallback<AutoSuggestAtlasResponse> {
                override fun onSuccess(autoSuggestAtlasResponse: AutoSuggestAtlasResponse?) {
                    mBinding.autosuggestJsonResponse.text = GsonBuilder().setPrettyPrinting().create().toJson(autoSuggestAtlasResponse)
                    if((autoSuggestAtlasResponse?.suggestedLocations?.size?:0) > 0) {
                        hasResponse = true
                        mBinding.progressbar.visibility = View.GONE
                        if(!showResponse) {
                            mBinding.autosuggestRv.visibility = View.VISIBLE
                        } else {
                            mBinding.autosuggestJsonResponseSv.visibility = View.VISIBLE
                        }
                        val searchResults = mutableListOf<SearchResultModel>()
                        searchResults.addAll((autoSuggestAtlasResponse?.suggestedSearches?:listOf()).map { SearchResultModel(
                            SearchType.SUGGESTED_SEARCH, suggestedSearchAtlas = it) })
                        searchResults.addAll((autoSuggestAtlasResponse?.suggestedLocations?:listOf()).map { SearchResultModel(
                            SearchType.SEARCH, eLocation = it) })

                        mAdapter.setSearchResults(searchResults)
                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    if(p0 != ErrorCodes.CANCEL_CALL) {
                        mBinding.progressbar.visibility = View.GONE
                        Toast.makeText(this@AutoSuggestActivity, "$p0 ---- $p1", Toast.LENGTH_SHORT)
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
        lifecycleScope.launch{
//            delay(300)
            callAutoSuggest(s)
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onEditorAction(
        v: TextView?,
        actionId: Int,
        event: KeyEvent?
    ): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callTextSearch(v!!.text.toString())
        }
        return false
    }

    override fun onSuccess(p0: LocationEngineResult?) {
        if(p0?.lastLocation != null) {
            this.currentLocation = p0.lastLocation
        }
    }

    override fun onFailure(p0: Exception) {

    }
}