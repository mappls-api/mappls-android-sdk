package com.mappls.sdk.demo.kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityGeoAnalyticsBinding
import com.mappls.sdk.demo.kotlin.adapter.GeoAnalyticsAdapter
import com.mappls.sdk.demo.kotlin.model.GeoAnalyticsModel
import com.mappls.sdk.geoanalytics.GeoAnalyticsAppearanceOption
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsPlugin
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsRequest
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsType
import com.mappls.sdk.geoanalytics.listing.MapplsGeoAnalyticsList
import com.mappls.sdk.geoanalytics.listing.MapplsGeoAnalyticsListManager
import com.mappls.sdk.geoanalytics.listing.model.GeoAnalyticsListResponse
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.geometry.LatLngBounds
import com.mappls.sdk.services.api.OnResponseCallback
import timber.log.Timber

class GeoAnalyticsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityGeoAnalyticsBinding
    private val models: MutableList<GeoAnalyticsModel> = mutableListOf()
    private var adapter: GeoAnalyticsAdapter? = null
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var mapplsMap: MapplsMap? = null
    private var geoAnalyticsPlugin: MapplsGeoAnalyticsPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_geo_analytics)
        mBinding.mapView.onCreate(savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(mBinding.bottomSheet)
        bottomSheetBehavior!!.isHideable = false
        bottomSheetBehavior!!.peekHeight = 200
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

        mBinding.rvGeoanalytics.layoutManager = LinearLayoutManager(this)
        adapter = GeoAnalyticsAdapter()
        mBinding.rvGeoanalytics.adapter = adapter
        mBinding.mapView.getMapAsync(this)
        adapter?.setOnLayerSelected(object : GeoAnalyticsAdapter.OnLayerSelected {
            override fun onLayerSelected(
                geoAnalyticsModel: GeoAnalyticsModel,
                isChecked: Boolean
            ) {
                if (geoAnalyticsPlugin != null) {
                    if (isChecked) {
                        geoAnalyticsPlugin!!.showGeoAnalytics(
                            geoAnalyticsModel.type,
                            geoAnalyticsModel.params
                        )
                    } else {
                        geoAnalyticsPlugin!!.removeGeoAnalytics(geoAnalyticsModel.type)
                    }
                }
                if (isChecked) {
                    val mapplsGeoAnalyticsList = MapplsGeoAnalyticsList.builder()
                        .api(geoAnalyticsModel.type.getName())
                        .attributes("b_box")
                        .geoBound(*geoAnalyticsModel.geoBound)
                        .geoBoundType(geoAnalyticsModel.geoboundType)
                        .build()
                    MapplsGeoAnalyticsListManager.newInstance(mapplsGeoAnalyticsList)
                        .call(object : OnResponseCallback<GeoAnalyticsListResponse> {
                            override fun onSuccess(geoAnalyticsListResponse: GeoAnalyticsListResponse) {
                                val result = geoAnalyticsListResponse.results
                                if (result != null && result.getAttrValues != null && result.getAttrValues.size > 0) {
                                    val latLngList: MutableList<LatLng> =
                                        ArrayList()
                                    for (value in result.getAttrValues) {
                                        if (value.getAttrValues != null && value.getAttrValues.size > 0) {
                                            for (map in value.getAttrValues) {
                                                val bBox = map["b_box"] as String?
                                                val truncateBox =
                                                    bBox!!.substring(4, bBox!!.length - 1)
                                                val start =
                                                    truncateBox.split(",".toRegex())
                                                        .toTypedArray()[0]
                                                val last =
                                                    truncateBox.split(",".toRegex())
                                                        .toTypedArray()[1]
                                                latLngList.add(
                                                    LatLng(
                                                        start.split(" ".toRegex())
                                                            .toTypedArray()[1].toDouble(),
                                                        start.split(" ".toRegex())
                                                            .toTypedArray()[0].toDouble()
                                                    )
                                                )
                                                latLngList.add(
                                                    LatLng(
                                                        last.split(" ".toRegex())
                                                            .toTypedArray()[1].toDouble(),
                                                        last.split(" ".toRegex())
                                                            .toTypedArray()[0].toDouble()
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    if (!mBinding.mapView.isDestroyed && mapplsMap != null) {
                                        mapplsMap!!.animateCamera(
                                            CameraUpdateFactory.newLatLngBounds(
                                                LatLngBounds.Builder().includes(latLngList).build(),
                                                12
                                            )
                                        )
                                    }
                                }
                            }

                            override fun onError(i: Int, s: String) {}
                        })
                }
            }

        })


    }

    private fun init() {
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.STATE,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme")
                    .geoBound("HARYANA", "UTTAR PRADESH", "ANDHRA PRADESH", "KERALA")
                    .propertyNames("stt_nme", "stt_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("42a5f4").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("HARYANA", "UTTAR PRADESH", "ANDHRA PRADESH", "KERALA")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.DISTRICT,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("BIHAR", "UTTARAKHAND")
                    .propertyNames("dst_nme", "dst_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("8bc34a").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("BIHAR", "UTTARAKHAND")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.SUB_DISTRICT,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("HIMACHAL PRADESH", "TRIPURA")
                    .propertyNames("sdb_nme", "sdb_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("ffa000").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("HIMACHAL PRADESH", "TRIPURA")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.WARD,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("ward_no").geoBound("0001")
                    .propertyNames("ward_no", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("ff5722").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "ward_no",
                arrayOf("0001")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.LOCALITY,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("DELHI")
                    .propertyNames("loc_nme", "loc_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("00695c").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("DELHI")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.PANCHAYAT,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("ASSAM")
                    .propertyNames("pnc_nme", "pnc_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("b71c1c").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("ASSAM")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.BLOCK,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("DELHI")
                    .propertyNames("blk_nme", "blk_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("3f51b5").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("DELHI")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.PINCODE,
                MapplsGeoAnalyticsRequest.builder().geoboundType("stt_nme")
                    .geoBound("KARNATAKA")
                    .propertyNames("pincode").style(
                        GeoAnalyticsAppearanceOption().fillColor("00bcd4").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("KARNATAKA")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.TOWN,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("PUNJAB")
                    .propertyNames("twn_nme", "twn_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("9ccc65").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("PUNJAB")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.CITY,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("TAMIL NADU")
                    .propertyNames("city_nme", "city_id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("78909c").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("TAMIL NADU")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.VILLAGE,
                MapplsGeoAnalyticsRequest.builder().attribute("t_p").query(">0")
                    .geoboundType("stt_nme").geoBound("GOA")
                    .propertyNames("vil_nme", "id", "t_p").style(
                        GeoAnalyticsAppearanceOption().fillColor("f06292").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("GOA")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.SUB_LOCALITY,
                MapplsGeoAnalyticsRequest.builder().geoboundType("stt_nme")
                    .geoBound("UTTAR PRADESH", "BIHAR")
                    .propertyNames("subl_nme", "subl_id").style(
                        GeoAnalyticsAppearanceOption().fillColor("f06292").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("UTTAR PRADESH", "BIHAR")
            )
        )
        models.add(
            GeoAnalyticsModel(
                MapplsGeoAnalyticsType.SUB_SUB_LOCALITY,
                MapplsGeoAnalyticsRequest.builder().geoboundType("stt_nme")
                    .geoBound("UTTAR PRADESH", "BIHAR")
                    .propertyNames("sslc_nme", "sslc_id").style(
                        GeoAnalyticsAppearanceOption().fillColor("f06292").fillOpacity(0.5)
                            .strokeColor("000000").labelSize(10).strokeWidth(0.0)
                            .labelColor("000000")
                    ).build(),
                "stt_nme",
                arrayOf("UTTAR PRADESH", "BIHAR")
            )
        )
        adapter?.setGeoAnalyticsModels(models)
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
        mBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.mapView.onLowMemory()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mapplsMap = mapplsMap
        mapplsMap.uiSettings?.setLogoMargins(0, 0, 0, 200)
        geoAnalyticsPlugin = MapplsGeoAnalyticsPlugin(mBinding.mapView, mapplsMap)
        init()
    }

    override fun onMapError(i: Int, s: String) {
        Timber.e("$i-----$s")
    }
}