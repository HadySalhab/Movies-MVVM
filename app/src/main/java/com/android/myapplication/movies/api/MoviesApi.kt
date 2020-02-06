package com.android.myapplication.movies.api

import android.text.TextUtils
import androidx.lifecycle.LiveData
import com.android.myapplication.movies.BuildConfig
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse
import com.android.myapplication.popularmovies.api.responses.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun geUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MoviesResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String? = null,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int,
        @Query("append_to_response") details: String = "videos,credits,reviews",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<MovieDetailsResponse>

}