package com.android.myapplication.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.myapplication.movies.api.RemoteDataSource
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.popularmovies.api.model.Movie
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse

class MoviesRepository(private val remoteDataSource: RemoteDataSource) {
    private var pageNumber: Int = 1
    private lateinit var categories: Categories
    private var query: String? = null
    val movieList: LiveData<List<Movie>> = remoteDataSource.movieList
    val detailMovieResponse: LiveData<MovieDetailsResponse> = remoteDataSource.detailsResponse
    val retrieveMoviesTimeOut = remoteDataSource.retrieveMoviesTimeOut
    fun getMovies(pageNumber: Int, sortBy: Categories) {
        categories = sortBy
        remoteDataSource.getMovies(pageNumber, sortBy)
    }

    fun getMovieNextPage() {
        if(::categories.isInitialized) {
            getMovies(++this.pageNumber, categories)
        }
    }

    fun searchMovies(pageNumber: Int, query: String) {
        this.pageNumber = pageNumber
        this.query = query
        remoteDataSource.searchMovies(pageNumber, query)
    }

    fun searchNextPage() {
        this.query?.let {
            searchMovies(++this.pageNumber, it)
        }
    }
    fun getDetails(id:Long){
        remoteDataSource.getDetails(id)
    }

}