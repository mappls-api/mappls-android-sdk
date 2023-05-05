package com.mappls.sdk.demo.kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.model.FeaturesList
import com.mappls.sdk.demo.kotlin.activity.*
import com.mappls.sdk.demo.kotlin.adapter.MapFeatureListAdapter
import java.util.*

class MapFeaturesFragmentKt : Fragment(), MapFeatureListAdapter.AdapterOnClick {
    lateinit var featureRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_mapfeatures_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        featureRecycleView = view.findViewById(R.id.addfeaturesRecyclerView)
        init()
    }


    private fun init() {
        val featuresArrayList = ArrayList<FeaturesList>()
        featuresArrayList.add(FeaturesList("Map Fragment", "Way to add Map in Fragment"))
        featuresArrayList.add(FeaturesList("Map Long Click", "Location camera options for render and tracking modes"))
        featuresArrayList.add(FeaturesList("Map Tap", "Long press on map and get Latitude Longitude"))
        featuresArrayList.add(FeaturesList("Map Gestures", "Gestures detection for map view"))
        featuresArrayList.add(FeaturesList("Map Styles", "To change and update Mappls Styles"))
        featuresArrayList.add(FeaturesList("Map Traffic", "To show Mappls Traffic"))
        featuresArrayList.add(FeaturesList("Place Click", "Get POI Click Event"))
        featureRecycleView.layoutManager = LinearLayoutManager(context)
        var mapFeaturesListAdapter = MapFeatureListAdapter(featuresArrayList, this)

        featureRecycleView.adapter = mapFeaturesListAdapter


    }

    override fun onClick(position: Int) {
        if (position == 0) {
            val mapFragmentIntent = Intent(context, MapFragmentActivity::class.java)
            startActivity(mapFragmentIntent)
        } else if (position == 1) {
            val mapClickIntent = Intent(context, MapLongClickActivity::class.java)
            startActivity(mapClickIntent)
        } else if (position == 2) {
            val mapLongClickIntent = Intent(context, MapClickActivity::class.java)
            startActivity(mapLongClickIntent)
        } else if (position == 3) {
            val gestureIntent = Intent(context, GesturesActivity::class.java)
            startActivity(gestureIntent)
        } else if (position == 4) {
            val styleIntent = Intent(context, StyleActivity::class.java)
            startActivity(styleIntent)
        }else if (position == 5) {
            val trafficIntent = Intent(context, TrafficActivity::class.java)
            startActivity(trafficIntent)
        }else if(position==6){
            var placeClick = Intent(context,PlaceClickActivity::class.java)
            startActivity(placeClick)

        }
    }
}


