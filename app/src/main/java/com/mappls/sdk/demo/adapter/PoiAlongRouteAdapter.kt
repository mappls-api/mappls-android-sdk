package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.databinding.LayoutNearbyItemBinding
import com.mappls.sdk.services.api.alongroute.models.SuggestedPOI

class PoiAlongRouteAdapter(private val onCategoryClick: (SuggestedPOI) -> Unit): RecyclerView.Adapter<PoiAlongRouteAdapter.PoiAlongRouteViewHolder>() {

    private var nearbyResultList: List<SuggestedPOI> = listOf()

    fun setPoiAlongRouteSearchResults(nearbyResultList: List<SuggestedPOI>) {
        this.nearbyResultList = nearbyResultList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PoiAlongRouteViewHolder {
        val binding = LayoutNearbyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PoiAlongRouteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PoiAlongRouteViewHolder,
        position: Int
    ) {
        val model = nearbyResultList[holder.adapterPosition]
        holder.binding.tvPlaceName.setText(model.poi)
        holder.binding.tvPlaceAddress.setText(model.address)
        holder.binding.root.setOnClickListener {
            onCategoryClick.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return nearbyResultList.size
    }

    class PoiAlongRouteViewHolder(val binding: LayoutNearbyItemBinding): ViewHolder(binding.root)
}