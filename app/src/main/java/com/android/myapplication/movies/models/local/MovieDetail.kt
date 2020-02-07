package com.android.myapplication.movies.models.local

import com.android.myapplication.popularmovies.api.model.Genre
import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val id: Long = 0,
    val originalTitle: String? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = 0.0,
    var voteCount: Int = 0,
    val releaseDate: String? = null,
    val revenue: Long = 0,
    val budget: Long = 0,
    val genres: List<Genre>? = null,
    val status: String? = null,
    val runtime: Int = 0
)