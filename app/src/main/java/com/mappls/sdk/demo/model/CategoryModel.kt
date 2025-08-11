package com.mappls.sdk.demo.model

import androidx.annotation.DrawableRes

data class CategoryModel(
    val type: Int,
    val name: String,
    @DrawableRes
    val icon: Int
)
