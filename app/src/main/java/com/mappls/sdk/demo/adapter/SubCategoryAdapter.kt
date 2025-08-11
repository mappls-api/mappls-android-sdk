package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.databinding.SubCategoryLayoutBinding
import com.mappls.sdk.demo.model.SubCategoryModel

class SubCategoryAdapter(private val categoryModels: List<SubCategoryModel>, private val onSubCategoryClick: (SubCategoryModel) -> Unit): RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    class SubCategoryViewHolder(val binding: SubCategoryLayoutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val mBinding = SubCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCategoryViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return categoryModels.size
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val model = categoryModels[holder.adapterPosition]

        holder.binding.ivSubCategory.setImageResource(model.icon)
        holder.binding.tvSubCategoryName.text = model.name
        holder.binding.tvSubCategoryDesc.text = model.description
        holder.binding.root.setOnClickListener {
            onSubCategoryClick.invoke(model)
        }
    }
}