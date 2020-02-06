package com.android.myapplication.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.myapplication.popularmovies.api.model.Movie

class MovieListViewModel :ViewModel(){

    private val _movieList:MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList:LiveData<List<Movie>>
    get() = _movieList

}