package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.databinding.CategoryTypeLayoutBinding
import com.mappls.sdk.demo.databinding.LayoutLocationModeBinding
import com.mappls.sdk.demo.utils.LocationTypeMode
import com.mappls.sdk.maps.location.modes.CameraMode
import com.mappls.sdk.maps.location.modes.RenderMode


class LocationModeAdapter(
    private var mode: LocationTypeMode?,
    private val callback: (mode: LocationTypeMode, value: Int) -> Unit): RecyclerView.Adapter<LocationModeAdapter.LocationModeViewHolder>() {

    private val cameraModeList = listOf(CameraMode.NONE, CameraMode.NONE_COMPASS, CameraMode.NONE_GPS,
        CameraMode.TRACKING, CameraMode.TRACKING_COMPASS, CameraMode.TRACKING_GPS_NORTH, CameraMode.TRACKING_GPS)

    private val renderModeList = listOf(RenderMode.NORMAL, RenderMode.COMPASS, RenderMode.GPS)

    private var selectedCameraMode: Int = CameraMode.NONE
    private var selectedRenderMode: Int = RenderMode.NORMAL

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationModeViewHolder {
        val mBinding = LayoutLocationModeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationModeViewHolder(mBinding)
    }

    fun setLocationMode(mode: LocationTypeMode?) {
        this.mode = mode
        notifyDataSetChanged()
    }

    fun setSelectedRenderMode(renderMode: Int) {
        this.selectedRenderMode = renderMode
        notifyDataSetChanged()
    }

    fun setSelectedCameraMode(cameraMode: Int) {
        this.selectedCameraMode = cameraMode
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: LocationModeViewHolder,
        position: Int
    ) {
        if(mode == LocationTypeMode.LOCATION_CAMERA_MODE) {
            val cameraMode = cameraModeList[holder.adapterPosition]
            when (cameraMode) {
                CameraMode.NONE -> {
                    holder.binding.tvLocationType.text = "None"
                }
                CameraMode.NONE_COMPASS -> {
                    holder.binding.tvLocationType.text = "None Compass"
                }
                CameraMode.NONE_GPS -> {
                    holder.binding.tvLocationType.text = "None GPS"
                }
                CameraMode.TRACKING -> {
                    holder.binding.tvLocationType.text = "Tracking"
                }
                CameraMode.TRACKING_COMPASS -> {
                    holder.binding.tvLocationType.text = "Tracking Compass"
                }
                CameraMode.TRACKING_GPS_NORTH -> {
                    holder.binding.tvLocationType.text = "Tracking GPS North"
                }
                CameraMode.TRACKING_GPS -> {
                    holder.binding.tvLocationType.text = "Tracking GPS"
                }
            }
            if(selectedCameraMode == cameraMode) {
                holder.binding.ivSelectedLocation.visibility = View.VISIBLE
            } else {
                holder.binding.ivSelectedLocation.visibility = View.GONE
            }
            holder.binding.root.setOnClickListener {
                callback.invoke(LocationTypeMode.LOCATION_CAMERA_MODE, cameraModeList[holder.adapterPosition])
            }
        } else if(mode == LocationTypeMode.LOCATION_RENDER_MODE) {
            val renderMode = renderModeList[holder.adapterPosition]
            when(renderMode) {
                RenderMode.NORMAL -> {
                    holder.binding.tvLocationType.text = "Normal"
                }
                RenderMode.COMPASS -> {
                    holder.binding.tvLocationType.text = "Compass"
                }
                RenderMode.GPS -> {
                    holder.binding.tvLocationType.text = "GPS"
                }
            }
            if(selectedRenderMode == renderMode) {
                holder.binding.ivSelectedLocation.visibility = View.VISIBLE
            } else {
                holder.binding.ivSelectedLocation.visibility = View.GONE
            }
            holder.binding.root.setOnClickListener {
                callback.invoke(
                    LocationTypeMode.LOCATION_RENDER_MODE,
                    renderModeList[holder.adapterPosition]
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if(mode == null) {
            0
        } else if(mode == LocationTypeMode.LOCATION_RENDER_MODE) {
            renderModeList.size
        } else if(mode == LocationTypeMode.LOCATION_CAMERA_MODE) {
            cameraModeList.size
        } else {
            0
        }
    }

    class LocationModeViewHolder(val binding: LayoutLocationModeBinding): ViewHolder(binding.root)



}