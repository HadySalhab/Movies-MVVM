package com.android.myapplication.popularmovies.api.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name:String
)