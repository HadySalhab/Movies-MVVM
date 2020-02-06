package com.android.myapplication.popularmovies.api.responses.inner

import com.android.myapplication.popularmovies.api.model.Review
import com.google.gson.annotations.SerializedName

class ReviewsResponse {
    @SerializedName("results")
    val reviews:List<Review>?=null
}