package com.android.myapplication.popularmovies.api.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("name")
    val name:String?=null,
    @SerializedName("profile_path")
    val profilePath:String?=null,
    @SerializedName("character")
    val character:String?=null
)