package com.android.myapplication.popularmovies.api.responses.inner

import com.android.myapplication.popularmovies.api.model.Cast
import com.google.gson.annotations.SerializedName

class CreditsResponse {
    @SerializedName("cast")
    val casts:List<Cast>?=null
}