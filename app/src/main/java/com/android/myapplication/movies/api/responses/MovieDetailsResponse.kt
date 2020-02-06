package com.android.myapplication.popularmovies.api.responses

import com.android.myapplication.popularmovies.api.model.Genre
import com.android.myapplication.popularmovies.api.responses.inner.CreditsResponse
import com.android.myapplication.popularmovies.api.responses.inner.ReviewsResponse
import com.android.myapplication.popularmovies.api.responses.inner.VideoResponse
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,

    @SerializedName("vote_count")
    var voteCount: Int = 0,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("revenue")
    val revenue: Long = 0,

    @SerializedName("budget")
    val budget: Long = 0,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("runtime")
    val runtime:Int=0,

    @SerializedName("videos")
    val videoResponse: VideoResponse? = null,

    @SerializedName("reviews")
    val reviewResponse: ReviewsResponse? = null,

    @SerializedName("credits")
    val creditsResponse: CreditsResponse? = null
)