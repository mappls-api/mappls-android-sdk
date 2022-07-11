package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.kotlin.adapter.InteractiveLayerAdapter
import com.mappls.sdk.maps.InteractiveLayer
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.covid.InteractiveItemDetails
import com.mappls.sdk.maps.geometry.LatLng

class InteractiveLayerActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mapView: MapView
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var mapplsMap: MapplsMap? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: InteractiveLayerAdapter? = null
    private var toggleInfoWindow: SwitchCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interactive_layer)
        mapView = findViewById<MapView>(R.id.map_view) as MapView
        mapView.onCreate(savedInstanceState)
        val view: View = findViewById(R.id.bottomSheet)
        toggleInfoWindow = findViewById(R.id.toggle_info_window)
        toggleInfoWindow?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(mapplsMap != null) {
                    mapplsMap?.showInteractiveLayerInfoWindow(isChecked)
                }
            }

        })
        bottomSheetBehavior = BottomSheetBehavior.from(view)
        bottomSheetBehavior?.isHideable = false
        bottomSheetBehavior?.peekHeight = 200
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        recyclerView = findViewById(R.id.rv_interactive_layer)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = InteractiveLayerAdapter()
        recyclerView?.adapter = adapter
        adapter?.setOnLayerSelected(object : InteractiveLayerAdapter.OnLayerSelected {
            override fun onLayerSelected(interactiveLayer: InteractiveLayer?, isSelected: Boolean) {
                if (isSelected) {
                    mapplsMap?.showInteractiveLayer(interactiveLayer)
                } else {
                    mapplsMap?.hideInteractiveLayer(interactiveLayer)
                }
            }

        })


        mapView.getMapAsync(this)
    }

    override fun onMapError(p0: Int, p1: String?) {}

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        mapplsMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.0, 77.0), 5.0))
        mapplsMap.uiSettings?.setLogoMargins(0, 0, 0, 200)

        mapplsMap.setOnInteractiveLayerClickListener(object : MapplsMap.OnInteractiveLayerClickListener {
            override fun onInteractiveLayerClicked(interactiveItemDetails: InteractiveItemDetails?) {

            }

        })
        mapplsMap.showInteractiveLayerInfoWindow(toggleInfoWindow?.isChecked ?: false)

        mapplsMap.getInteractiveLayer(object: MapplsMap.InteractiveLayerLoadingListener {
            override fun onLayersLoaded(list: List<InteractiveLayer>?) {
                adapter?.setCovidLayers(list)
            }

        })

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
