package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mappls.sdk.demo.databinding.StyleAdapterItemBinding
import com.mappls.sdk.maps.style.model.MapplsStyle

class StyleAdapter(private val listStyles: List<MapplsStyle>, private val onStyleClick: (MapplsStyle)-> Unit): RecyclerView.Adapter<StyleAdapter.StyleViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StyleViewHolder {
        return StyleViewHolder(StyleAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: StyleViewHolder,
        position: Int
    ) {
        val item = listStyles[holder.adapterPosition]
        holder.binding.tvStyleName.text = item.displayName
        holder.binding.tvStyleDescription.text = item.description
        Glide.with(holder.binding.getRoot().context)
            .load(item.imageUrl).into(holder.binding.ivStyleIcon)
        holder.binding.root.setOnClickListener {
            onStyleClick.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return listStyles.size
    }


    class StyleViewHolder(val binding: StyleAdapterItemBinding): ViewHolder(binding.root)
}