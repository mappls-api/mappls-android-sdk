package com.mappls.sdk.demo.model

import android.app.Activity
import androidx.annotation.DrawableRes

data class SubCategoryModel(
    val activity: Class<*>,
    val name: String,
    val description: String,
    @DrawableRes
    val icon: Int
)
