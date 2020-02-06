package com.android.myapplication.popularmovies.api.model

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("name")
    val name:String?=null,
    @SerializedName("profile_path")
    val profilePath:String?=null,
    @SerializedName("job")
    val job:String?=null
)