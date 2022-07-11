package com.mappls.sdk.demo.kotlin.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.utils.CheckInternet
import com.mappls.sdk.demo.kotlin.adapter.AutoSuggestAdapter
import com.mappls.sdk.demo.kotlin.utility.TransparentProgressDialog
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.SupportMapFragment
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.autosuggest.MapplsAutoSuggest
import com.mappls.sdk.services.api.autosuggest.MapplsAutosuggestManager
import com.mappls.sdk.services.api.autosuggest.model.AutoSuggestAtlasResponse
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import com.mappls.sdk.services.api.textsearch.MapplsTextSearch
import com.mappls.sdk.services.api.textsearch.MapplsTextSearchManager

/**
 * Created by CEINFO on 26-02-2019.
 */
class AutoSuggestActivity : AppCompatActivity(), OnMapReadyCallback, TextWatcher,TextView.OnEditorActionListener {

    lateinit var mapplsMap: MapplsMap
    private lateinit var autoSuggestText: EditText
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    lateinit var handler: Handler
    private var mLayoutManager: androidx.recyclerview.widget.LinearLayoutManager? = null
    private lateinit var transparentDialog: TransparentProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autosuggest_activity)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        Toast.makeText(this,"kotlin",Toast.LENGTH_SHORT).show()
        initReference()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap


        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                22.553147478403194,
                77.23388671875), 4.0))
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        handler.postDelayed(Runnable {
            recyclerView.visibility = View.GONE
            if (s?.length?:0 < 3)
                recyclerView.adapter = null

            if (s != null && s.toString().trim { it <= ' ' }.length < 2) {
                recyclerView.adapter = null
                return@Runnable
            }

            if (s?.length?:0 > 2) {
                if (CheckInternet.isNetworkAvailable(this@AutoSuggestActivity)) {
                    callAutoSuggestApi(s.toString())
                } else {
                    showToast(getString(R.string.pleaseCheckInternetConnection))
                }
            }
        }, 300)
    }

    override fun onMapError(p0: Int, p1: String?) {}

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    private fun initReference() {
        recyclerView = findViewById(R.id.recyclerview)
        autoSuggestText = findViewById(R.id.auto_suggest)

        mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@AutoSuggestActivity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.visibility = View.GONE



        handler = Handler(Looper.getMainLooper())
        autoSuggestText.addTextChangedListener(this)
        autoSuggestText.setOnEditorActionListener(this)
    }

    fun selectedPlace(eLocation: ELocation) {
        val add:String="Latitude: " + eLocation.latitude + " longitude: " + eLocation.longitude
        addMarker(eLocation.latitude.toDouble(), eLocation.longitude.toDouble())
        showToast(add)
    }



    private fun addMarker(latitude: Double, longitude: Double) {
        mapplsMap.clear()
        mapplsMap.addMarker(MarkerOptions().position(LatLng(
                latitude, longitude)))
    }

    @Synchronized
    private fun callAutoSuggestApi(searchString: String) {

        val autoSuggest = MapplsAutoSuggest.builder()
                .query(searchString)
                .tokenizeAddress(true)
                .build()

        MapplsAutosuggestManager.newInstance(autoSuggest).call(object : OnResponseCallback<AutoSuggestAtlasResponse> {
            override fun onSuccess(p0: AutoSuggestAtlasResponse?) {
                if (p0 != null) {
                    val suggestedList =p0.suggestedLocations
                    if (suggestedList.size > 0) {
                        recyclerView.visibility = View.VISIBLE
                        val autoSuggestAdapter = AutoSuggestAdapter(suggestedList, object : AutoSuggestAdapter.PlaceData {
                            override fun dataOfPlace(eLocation: ELocation) {
                                selectedPlace(eLocation)
                                recyclerView.visibility = View.GONE
                            }
                        })
                        recyclerView.adapter = autoSuggestAdapter
                    }
                } else {
                    Toast.makeText(this@AutoSuggestActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@AutoSuggestActivity, p1, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callTextSearchApi(searchString: String) {
        val textSearch = MapplsTextSearch.builder()
                .query(searchString)
                .build()
        MapplsTextSearchManager.newInstance(textSearch).call(object : OnResponseCallback<AutoSuggestAtlasResponse> {
            override fun onSuccess(p0: AutoSuggestAtlasResponse?) {
                if (p0 != null) {
                    val suggestedList = p0.suggestedLocations
                    if (suggestedList.size > 0) {
                        recyclerView.visibility = View.VISIBLE
                        val autoSuggestAdapter = AutoSuggestAdapter(suggestedList, object : AutoSuggestAdapter.PlaceData {
                            override fun dataOfPlace(eLocation: ELocation) {
                                selectedPlace(eLocation)
                                recyclerView.visibility = View.GONE
                            }
                        })
                        recyclerView.adapter = autoSuggestAdapter
                    }
                } else {
                    Toast.makeText(this@AutoSuggestActivity, "Not able to get value, Try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@AutoSuggestActivity, p1, Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun show() {
        transparentDialog = TransparentProgressDialog(this, R.drawable.circle_loader, "")
        transparentDialog.show()
    }

    fun hide() {
        transparentDialog.dismiss()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callTextSearchApi(v!!.text.toString())
            autoSuggestText.clearFocus()
            val inputMethodManger =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManger.hideSoftInputFromWindow(autoSuggestText.windowToken, 0)

        }
        return false
    }

}