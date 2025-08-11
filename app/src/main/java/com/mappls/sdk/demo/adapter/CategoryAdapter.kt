package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.databinding.CategoryTypeLayoutBinding
import com.mappls.sdk.demo.model.CategoryModel

class CategoryAdapter(private val categoryModels: List<CategoryModel>, private val onCategoryClick: (CategoryModel) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: CategoryTypeLayoutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val mBinding = CategoryTypeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return categoryModels.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val model = categoryModels[holder.adapterPosition]
        holder.binding.categoryTypeIv.setImageResource(model.icon)
        holder.binding.categoryTypeName.text = model.name
        holder.binding.root.setOnClickListener {
            onCategoryClick.invoke(model)
        }
    }
}