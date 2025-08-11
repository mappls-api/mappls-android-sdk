package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.adapter.CategoryAdapter.CategoryViewHolder
import com.mappls.sdk.demo.databinding.CategoryTypeLayoutBinding
import com.mappls.sdk.demo.databinding.HeaderPlaceDetailLayoutBinding
import com.mappls.sdk.demo.databinding.ItemPlaceDetailLayoutBinding
import com.mappls.sdk.demo.model.PlaceDetailModel

class PlaceDetailAdapter(
private val placeDetailModels: MutableList<PlaceDetailModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HeaderViewHolder(val binding: HeaderPlaceDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemViewHolder(val binding: ItemPlaceDetailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return placeDetailModels[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == PlaceDetailModel.TYPE_HEADER) {
            val mBinding = HeaderPlaceDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HeaderViewHolder(mBinding)
        } else {
            val mBinding = ItemPlaceDetailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ItemViewHolder(mBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = placeDetailModels[position]
        when (holder) {
            is HeaderViewHolder -> {
                holder.binding.tvTitle.text = model.title
            }
            is ItemViewHolder -> {
                holder.binding.tvTitle.text = model.title
                holder.binding.tvValue.text = model.value
            }
        }
    }

    override fun getItemCount(): Int = placeDetailModels.size
}
