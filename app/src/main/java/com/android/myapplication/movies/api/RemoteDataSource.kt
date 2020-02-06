package com.android.myapplication.movies.api

import NETWORK_TIMEOUT
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.myapplication.movies.AppExecutors
import com.android.myapplication.popularmovies.api.model.Movie
import java.util.concurrent.TimeUnit

public class RemoteDataSource (private val appExecutors: AppExecutors) {
    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    fun searchMovieApi(){
        val handler = appExecutors.networkIO.submit(Runnable {
            //Retrieve data from REST api
        })

        appExecutors.networkIO.schedule(Runnable {
            handler.cancel(true)
        },NETWORK_TIMEOUT,TimeUnit.MILLISECONDS)
    }
}