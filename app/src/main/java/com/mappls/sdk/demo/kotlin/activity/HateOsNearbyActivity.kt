package com.mappls.sdk.demo.kotlin.activity

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.adapter.AutoSuggestSearchesAdapter
import com.mappls.sdk.demo.java.adapter.NearByAdapter
import com.mappls.sdk.demo.java.utils.TransparentProgressDialog
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.MapplsAutoSuggest
import com.mappls.sdk.services.api.autosuggest.MapplsAutosuggestManager
import com.mappls.sdk.services.api.autosuggest.model.AutoSuggestAtlasResponse
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearby
import com.mappls.sdk.services.api.hateaosnearby.MapplsHateosNearbyManager
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResponse
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult
import java.util.*

class HateOsNearbyActivity : AppCompatActivity(), OnMapReadyCallback,TextView.OnEditorActionListener {

    private lateinit var mapplsMap: MapplsMap
    private lateinit var autoSuggestText: EditText
    private lateinit var mapView: MapView
    private lateinit var transparentProgressDialog: TransparentProgressDialog
    private lateinit var nearbyRecyclerView: RecyclerView
    private  lateinit var autoSuggestRecyclerView:RecyclerView
    private var count = 0
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hate_os_nearby)
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        initReferences()
        initListeners()


        floatingActionButton = findViewById(R.id.marker_list)
        floatingActionButton.setImageResource(R.drawable.location_pointer)
        count = 0
        floatingActionButton.setOnClickListener { v: View? ->
            if (count == 0) {
                count = 1
                floatingActionButton.setImageResource(R.drawable.listing_option)
                nearbyRecyclerView.visibility = View.GONE
            } else {
                count = 0
                floatingActionButton.setImageResource(R.drawable.location_pointer)
                nearbyRecyclerView.visibility = View.VISIBLE
            }
        }

        transparentProgressDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
    }

    private fun initListeners() {
        autoSuggestText.setOnEditorActionListener(this)
    }

    private fun initReferences() {
        autoSuggestText = findViewById(R.id.auto_suggest)
        nearbyRecyclerView = findViewById<RecyclerView>(R.id.nearByRecyclerview)
        autoSuggestRecyclerView = findViewById<RecyclerView>(R.id.auto_suggest_recyclerView)
        mLayoutManager = LinearLayoutManager(this)
        nearbyRecyclerView.layoutManager = mLayoutManager
        autoSuggestRecyclerView.layoutManager = LinearLayoutManager(this)
        autoSuggestRecyclerView.visibility = View.GONE
    }

    private fun progressDialogShow() {
        transparentProgressDialog.show()
    }

    private fun progressDialogHide() {
        transparentProgressDialog.dismiss()
    }


    private fun callAutoSuggestApi(searchString: String) {
        progressDialogShow()
        val autoSuggest = MapplsAutoSuggest.builder()
                .query(searchString)
                .bridge(true)
                .build()
        MapplsAutosuggestManager.newInstance(autoSuggest).call(object : OnResponseCallback<AutoSuggestAtlasResponse> {
            override fun onSuccess(p0: AutoSuggestAtlasResponse?) {
                val suggestedSearches =p0?.suggestedSearches
                if (suggestedSearches?.size?:0 > 0) {
                    autoSuggestRecyclerView.visibility = View.VISIBLE
                    val autoSuggestAdapter = AutoSuggestSearchesAdapter(suggestedSearches, AutoSuggestSearchesAdapter.SuggestedSearches { hyperlink: String? ->
                        callHateOs(hyperlink!!)
                        autoSuggestRecyclerView.visibility = View.GONE
                    })
                    autoSuggestRecyclerView.adapter = autoSuggestAdapter
                } else {
                    progressDialogHide()
                    Toast.makeText(this@HateOsNearbyActivity, "No hyperlinks found...", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                progressDialogHide()
                showToast(p1?:"Something went wrong")
            }
        })
    }

    private fun callHateOs(hyperlink: String) {
        val hateOs = MapplsHateosNearby.builder()
                .hyperlink(hyperlink)
                .build()
        MapplsHateosNearbyManager.newInstance(hateOs).call(object : OnResponseCallback<NearbyAtlasResponse> {
            override fun onSuccess(nearbyAtlasResponse: NearbyAtlasResponse?) {
                if (nearbyAtlasResponse != null) {
                    val nearByList = nearbyAtlasResponse.suggestedLocations
                    if (nearByList.size > 0) {
                        addMarker(nearByList)
                    }
                } else {
                    Toast.makeText(this@HateOsNearbyActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
                progressDialogHide()
            }

            override fun onError(p0: Int, p1: String?) {
                progressDialogHide()
                showToast(p1?:"Something went wrong")
            }

        })
    }

    private fun addMarker(nearByList: ArrayList<NearbyAtlasResult>) {
        mapplsMap.clear()
        for (marker in nearByList) {
            mapplsMap.addMarker(MarkerOptions().position(LatLng(marker.getLatitude(), marker.getLongitude())).title(marker.getPlaceName()))
        }
        nearbyRecyclerView.adapter = NearByAdapter(nearByList)
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    override fun onMapError(p0: Int, p1: String?) {
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap


        mapplsMap.setPadding(20, 20, 20, 20)


        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                22.553147478403194,
                77.23388671875), 4.0))
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callAutoSuggestApi(v!!.text.toString())
            autoSuggestText.clearFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(autoSuggestText.windowToken, 0)
            return true
        }
        return false
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