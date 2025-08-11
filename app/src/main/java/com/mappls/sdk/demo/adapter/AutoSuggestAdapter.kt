package com.mappls.sdk.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mappls.sdk.demo.databinding.SearchResultLayoutBinding
import com.mappls.sdk.demo.databinding.SuggestedSearchResultLayoutBinding
import com.mappls.sdk.demo.model.SearchResultModel
import com.mappls.sdk.demo.utils.SearchType
import com.mappls.sdk.plugin.directions.DirectionFormatter
import com.mappls.sdk.services.api.autosuggest.model.ELocation

class AutoSuggestAdapter(private val onCategoryClick: (SearchResultModel) -> Unit): RecyclerView.Adapter<ViewHolder>() {
    private var searchResultList: List<SearchResultModel> = listOf()


    fun setSearchResults(eLocations: List<SearchResultModel>) {
        this.searchResultList = eLocations
        notifyDataSetChanged()
    }

    class SearchViewHolder(val binding: SearchResultLayoutBinding): ViewHolder(binding.root)
    class SuggestedViewHolder(val binding: SuggestedSearchResultLayoutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 0) {
            val mBinding = SearchResultLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SearchViewHolder(mBinding)
        } else {
            val mBinding = SuggestedSearchResultLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SuggestedViewHolder(mBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if(searchResultList[position].type == SearchType.SEARCH) 0 else 1
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = searchResultList[holder.adapterPosition]
        if(model.type == SearchType.SEARCH) {
            val eLocation = model.eLocation
            (holder as SearchViewHolder).binding.tvPlaceName.text = eLocation?.placeName
            if(eLocation?.distance != null) {
                holder.binding.tvDistance.text = DirectionFormatter.getFormattedDistance(eLocation.distance).toString()
            }else{
                holder.binding.tvDistance.visibility = View.GONE
            }
            holder.binding.tvPlaceAddress.text ="${eLocation?.placeAddress}"
//        holder.binding.categoryTypeName.text = model.name
            holder.binding.root.setOnClickListener {
              onCategoryClick.invoke(model)
            }
        } else {
            val suggestedSearch = model.suggestedSearchAtlas
            (holder as SuggestedViewHolder).binding.tvSuggestedResult.text = suggestedSearch?.searchStringToShow
            holder.binding.root.setOnClickListener {
                onCategoryClick.invoke(model)
            }
        }
    }
}