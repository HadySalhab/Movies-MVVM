package com.android.myapplication.movies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.myapplication.popularmovies.api.model.Movie

class MoviesRepository{
    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>>
        get() = _movieList
}