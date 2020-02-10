package com.android.myapplication.popularmovies.api.responses

import com.android.myapplication.popularmovies.api.model.Genre
import com.android.myapplication.popularmovies.api.model.Movie
import com.android.myapplication.popularmovies.api.responses.inner.CreditsResponse
import com.android.myapplication.popularmovies.api.responses.inner.ReviewsResponse
import com.android.myapplication.popularmovies.api.responses.inner.VideoResponse
import com.google.gson.annotations.SerializedName

//response for specific movie detail request
data class MovieDetailsResponse(

    @SerializedName("")
    val movie: Movie? = null,

    @SerializedName("videos")
    val videoResponse: VideoResponse? = null,

    @SerializedName("reviews")
    val reviewResponse: ReviewsResponse? = null,

    @SerializedName("credits")
    val creditsResponse: CreditsResponse? = null
)