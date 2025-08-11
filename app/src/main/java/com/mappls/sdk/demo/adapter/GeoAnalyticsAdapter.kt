package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mappls.sdk.demo.databinding.GeoAnalyticsLayoutBinding
import com.mappls.sdk.demo.model.GeoAnalyticsDataModel
import com.mappls.sdk.geoanalytics.MapplsGeoAnalyticsType

class GeoAnalyticsAdapter(private val geoAnalyticsDataList: List<GeoAnalyticsDataModel>, private  val callBack: (GeoAnalyticsDataModel, Boolean) -> Unit)
    : RecyclerView.Adapter<GeoAnalyticsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val mBinding = GeoAnalyticsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val model = geoAnalyticsDataList[holder.adapterPosition]
        holder.mBinding.swShowGeoAnalytics.text = getText(model.geoAnalticsType)
        holder.mBinding.swShowGeoAnalytics.setOnCheckedChangeListener { sw, isCheck ->
            callBack.invoke(model, isCheck)
        }
    }

    private fun getText(type: MapplsGeoAnalyticsType): String {
        return when(type) {
            MapplsGeoAnalyticsType.STATE -> "State"
            MapplsGeoAnalyticsType.DISTRICT -> "District"
            MapplsGeoAnalyticsType.SUB_DISTRICT -> "Sub District"
            MapplsGeoAnalyticsType.WARD -> "Ward"
            MapplsGeoAnalyticsType.LOCALITY -> "Locality"
            MapplsGeoAnalyticsType.PANCHAYAT -> "Panchayat"
            MapplsGeoAnalyticsType.BLOCK -> "Block"
            MapplsGeoAnalyticsType.PINCODE -> "Pincode"
            MapplsGeoAnalyticsType.TOWN -> "Town"
            MapplsGeoAnalyticsType.CITY -> "City"
            MapplsGeoAnalyticsType.VILLAGE -> "Village"
            MapplsGeoAnalyticsType.SUB_LOCALITY -> "Sub Locality"
            MapplsGeoAnalyticsType.SUB_SUB_LOCALITY -> "Sub Sub Locality"

            else ->
                type.name
        }
    }

    override fun getItemCount(): Int = geoAnalyticsDataList.size

    inner class ViewHolder(val mBinding: GeoAnalyticsLayoutBinding): RecyclerView.ViewHolder(mBinding.root)

}