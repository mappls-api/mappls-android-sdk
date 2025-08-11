package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mappls.sdk.demo.databinding.ReportCategoryItemLayoutBinding
import com.mappls.sdk.services.api.event.catmaster.model.ParentCategory
import com.mappls.sdk.services.api.event.route.ReportCriteria

class ReportCategoryAdapter(private val parentCategories: List<ParentCategory>): RecyclerView.Adapter<ReportCategoryAdapter.ReportCategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportCategoryViewHolder {

        return ReportCategoryViewHolder(ReportCategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ReportCategoryViewHolder,
        position: Int
    ) {
       val category = parentCategories.get(holder.adapterPosition)
        holder.binding.tvCategoryName.setText(category.name)
        Glide.with(holder.binding.getRoot().getContext())
            .load(category.getCategoryIcon(ReportCriteria.ICON_24_PX)).into(holder.binding.ivCategoryIcon)
    }

    override fun getItemCount(): Int {
        return parentCategories.size
    }

    class ReportCategoryViewHolder(val binding: ReportCategoryItemLayoutBinding): ViewHolder(binding.root)

}