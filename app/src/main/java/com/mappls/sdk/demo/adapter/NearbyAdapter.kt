package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.databinding.LayoutNearbyItemBinding
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult

class NearbyAdapter(private val onCategoryClick: (NearbyAtlasResult) -> Unit): RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder>() {

    private var nearbyResultList: List<NearbyAtlasResult> = listOf()

    fun setNearbySearchResults(nearbyResultList: List<NearbyAtlasResult>) {
        this.nearbyResultList = nearbyResultList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NearbyViewHolder {
        val binding = LayoutNearbyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearbyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NearbyViewHolder,
        position: Int
    ) {
        val model = nearbyResultList[holder.adapterPosition]
        holder.binding.tvPlaceName.setText(model.placeName)
        holder.binding.tvPlaceAddress.setText(model.placeAddress)
        holder.binding.root.setOnClickListener {
            onCategoryClick.invoke(model)
        }
    }

    override fun getItemCount(): Int {
        return nearbyResultList.size
    }

    class NearbyViewHolder(val binding: LayoutNearbyItemBinding): ViewHolder(binding.root)
}