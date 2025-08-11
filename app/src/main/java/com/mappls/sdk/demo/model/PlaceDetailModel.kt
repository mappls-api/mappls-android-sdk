package com.mappls.sdk.demo.model

data class PlaceDetailModel(
    var type: Int,
    var title: String,
    var value: String
) {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
    }
}
