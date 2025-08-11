package com.mappls.sdk.demo.model

import com.mappls.sdk.demo.utils.SearchType
import com.mappls.sdk.services.api.autosuggest.model.ELocation
import com.mappls.sdk.services.api.autosuggest.model.SuggestedSearchAtlas

data class SearchResultModel(
    val type: SearchType,
    val eLocation: ELocation? = null,
    val suggestedSearchAtlas: SuggestedSearchAtlas? = null
)
