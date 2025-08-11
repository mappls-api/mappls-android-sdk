package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.PlaceDetailAdapter.HeaderViewHolder
import com.mappls.sdk.demo.adapter.PlaceDetailAdapter.ItemViewHolder
import com.mappls.sdk.demo.databinding.GeoFenceDetailsLayoutBinding
import com.mappls.sdk.demo.databinding.HeaderPlaceDetailLayoutBinding
import com.mappls.sdk.demo.model.GeofenceDetailModel
import com.mappls.sdk.demo.model.PlaceDetailModel

class GeoFenceDetailAdapter : RecyclerView.Adapter<GeoFenceDetailAdapter.ViewHolder>() {

    private var geofenceDetailList: MutableList<GeofenceDetailModel> = mutableListOf()
    private var onGeoFenceChangeListener: OnGeoFenceChangeListener? = null

    fun setGeofenceDetailList(list: List<GeofenceDetailModel>) {
        geofenceDetailList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setOnGeoFenceChangeListener(listener: OnGeoFenceChangeListener) {
        this.onGeoFenceChangeListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.geo_fence_details_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = geofenceDetailList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val geofenceDetail = geofenceDetailList[position]
        holder.tvLabel.text = geofenceDetail.gfLabel
        // Remove listener before setting checked to prevent unwanted callbacks
        holder.swStatus.setOnCheckedChangeListener(null)
        holder.swStatus.isChecked = geofenceDetail.active

        holder.ivEdit.setOnClickListener {
            onGeoFenceChangeListener?.onEditGeoFence(geofenceDetail)
        }

        holder.ivDelete.setOnClickListener {
            onGeoFenceChangeListener?.onRemoveGeofence(geofenceDetail)
        }

        holder.swStatus.setOnCheckedChangeListener { _, isChecked ->
            geofenceDetailList[position].active = isChecked
            // Post notifyDataSetChanged to ensure UI updates on main thread
            holder.swStatus.post {
                notifyDataSetChanged()
            }
            onGeoFenceChangeListener?.onGeoFenceStatusChange()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = itemView.findViewById(R.id.tv_geofence_label)
        val swStatus: SwitchCompat = itemView.findViewById(R.id.sw_status)
        val ivEdit: ImageView = itemView.findViewById(R.id.iv_edit_geofence)
        val ivDelete: ImageView = itemView.findViewById(R.id.iv_delete_geofence)
    }

    interface OnGeoFenceChangeListener {
        fun onGeoFenceStatusChange()
        fun onEditGeoFence(geofenceDetail: GeofenceDetailModel)
        fun onRemoveGeofence(geofenceDetail: GeofenceDetailModel)
    }
}

