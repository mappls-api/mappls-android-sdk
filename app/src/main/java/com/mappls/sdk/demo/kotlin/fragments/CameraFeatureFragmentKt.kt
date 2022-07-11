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

class CameraFeatureFragmentKt : Fragment(), MapFeatureListAdapter.AdapterOnClick {
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
        var i = 0
        val featuresArrayList = ArrayList<FeaturesList>()
        featuresArrayList.add(FeaturesList("Camera Features", "Animate, Move or Ease Camera Position"))
        featuresArrayList.add(FeaturesList("Location Camera Options", "Long press on map and get Latitude Longitude"))
        featuresArrayList.add(FeaturesList("Camera Features in ELoc", "Animate, Move or Ease Camera Position using eloc"))
        featureRecycleView.layoutManager = LinearLayoutManager(context)
        var mapFeaturesListAdapter: MapFeatureListAdapter = MapFeatureListAdapter(featuresArrayList,this)

        featureRecycleView.adapter = mapFeaturesListAdapter


    }

    override fun onClick(position: Int) {
        if(position==0){
            var cameraFeatureIntent = Intent(context, CameraActivity::class.java)
            startActivity(cameraFeatureIntent)

        }else if(position==1){
            var locationCameraIntent = Intent(context, LocationCameraActivity::class.java)
            startActivity(locationCameraIntent)

        }else if(position==2){
            var eLocCameraIntent : Intent = Intent(context, MapplsPinCameraActivity::class.java)
            startActivity(eLocCameraIntent)

        }

    }

}

