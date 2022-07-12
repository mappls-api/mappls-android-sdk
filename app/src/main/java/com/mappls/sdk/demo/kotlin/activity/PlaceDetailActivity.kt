package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityPlaceDetailBinding
import com.mappls.sdk.demo.kotlin.adapter.PlaceDetailAdapter
import com.mappls.sdk.demo.kotlin.model.PlaceDetailModel
import com.mappls.sdk.services.api.OnResponseCallback
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetail
import com.mappls.sdk.services.api.placedetail.MapplsPlaceDetailManager
import com.mappls.sdk.services.api.placedetail.model.PlaceDetailResponse
import java.util.*

/**
 ** Created by Saksham on 26-11-2020.
 **/

class PlaceDetailActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPlaceDetailBinding
    private var adapter: PlaceDetailAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_place_detail)
        adapter = PlaceDetailAdapter()
        mBinding.rvPlaceDetail.layoutManager = LinearLayoutManager(this)
        mBinding.rvPlaceDetail.adapter = adapter
        mBinding.btnSearch.setOnClickListener {
            val mapplsPin: String = mBinding.etMapplsPin.text.toString()
            if (mapplsPin.isNotEmpty()) {
                callMapplsPin(mapplsPin)
            } else {
                Toast.makeText(this@PlaceDetailActivity, "Please add Mappls Pin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callMapplsPin(mapplsPin: String) {
        mBinding.progressBar.visibility = View.VISIBLE
        val placeDetail = MapplsPlaceDetail.builder()
                .mapplsPin(mapplsPin)
                .build()
        MapplsPlaceDetailManager.newInstance(placeDetail).call(object : OnResponseCallback<PlaceDetailResponse> {
            override fun onSuccess(placeDetailResponse: PlaceDetailResponse?) {
                if (placeDetailResponse != null) {
                    setValues(placeDetailResponse)
                } else {
                    Toast.makeText(this@PlaceDetailActivity, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(p0: Int, p1: String?) {
                Toast.makeText(this@PlaceDetailActivity, p1, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setValues(placeDetailResponse: PlaceDetailResponse) {
        val placeDetailModels: MutableList<PlaceDetailModel> = ArrayList()
        addItem(placeDetailModels, "Mappls Pin", placeDetailResponse.mapplsPin)
        addItem(placeDetailModels, "Latitude", placeDetailResponse.latitude.toString() + "")
        addItem(placeDetailModels, "Longitude", placeDetailResponse.longitude.toString() + "")
        addItem(placeDetailModels, "Place Name", placeDetailResponse.placeName)
        addItem(placeDetailModels, "Place Address", placeDetailResponse.address)
        addItem(placeDetailModels, "City", placeDetailResponse.city)
        addItem(placeDetailModels, "House Name", placeDetailResponse.houseName)
        addItem(placeDetailModels, "District", placeDetailResponse.district)
        addItem(placeDetailModels, "House Number", placeDetailResponse.houseNumber)
        addItem(placeDetailModels, "Locality", placeDetailResponse.locality)
        addItem(placeDetailModels, "Pin code", placeDetailResponse.pincode)
        addItem(placeDetailModels, "POI", placeDetailResponse.poi)
        addItem(placeDetailModels, "State", placeDetailResponse.state)
        addItem(placeDetailModels, "Street", placeDetailResponse.street)
        addItem(placeDetailModels, "Sub District", placeDetailResponse.subDistrict)
        adapter?.setPlaceDetailModels(placeDetailModels)
    }

    private fun addItem(placeDetailModels: MutableList<PlaceDetailModel>, title: String, value: String?) {
        if (value != null) {
            placeDetailModels.add(PlaceDetailModel(PlaceDetailModel.TYPE_ITEM, title, value))
        }
    }
}