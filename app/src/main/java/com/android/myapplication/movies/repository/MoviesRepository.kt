package com.android.myapplication.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.myapplication.movies.api.RemoteDataSource
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.popularmovies.api.model.Movie

class MoviesRepository(private val remoteDataSource: RemoteDataSource){
    val movieList: LiveData<List<Movie>> = remoteDataSource.movieList

    fun getMovies(pageNumber:Int,sortBy:Categories){
        remoteDataSource.getMovies(pageNumber,sortBy)
    }
}