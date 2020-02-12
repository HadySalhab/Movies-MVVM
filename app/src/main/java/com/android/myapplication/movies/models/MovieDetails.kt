package com.android.myapplication.movies.models

import com.android.myapplication.popularmovies.api.model.Cast
import com.android.myapplication.popularmovies.api.model.Review
import com.android.myapplication.popularmovies.api.model.Video

data class MovieDetails(
    val trailers: List<Video>? = null,
    val reviews: List<Review>? = null,
    val casts: List<Cast>? = null
)