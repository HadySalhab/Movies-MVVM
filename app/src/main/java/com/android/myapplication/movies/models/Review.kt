package com.android.myapplication.popularmovies.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Review(
    @SerializedName("id")
    val id:String=UUID.randomUUID().toString(),
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("url")
    val url:String?=null
)