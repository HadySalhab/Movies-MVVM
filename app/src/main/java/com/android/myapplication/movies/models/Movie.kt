package com.android.myapplication.popularmovies.api.model

import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("title")
    val title: String?=null,

    @SerializedName("poster_path")
    val posterPath: String?=null
)