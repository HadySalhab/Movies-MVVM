package com.android.myapplication.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse

class DetailActivityViewModel(private val repository: MoviesRepository) :ViewModel(){

    val detailMovie: LiveData<MovieDetailsResponse> = repository.detailMovie
    fun getDetails(id:Long){
        repository.getDetails(id)
    }
}