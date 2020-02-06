package com.android.myapplication.popularmovies.api.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("url")
    val url:String?=null
)