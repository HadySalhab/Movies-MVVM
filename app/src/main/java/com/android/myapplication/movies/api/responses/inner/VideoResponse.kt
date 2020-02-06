package com.android.myapplication.popularmovies.api.responses.inner

import com.android.myapplication.popularmovies.api.model.Video
import com.google.gson.annotations.SerializedName

class VideoResponse {
    @SerializedName("results")
    val videos:List<Video>?=null
}