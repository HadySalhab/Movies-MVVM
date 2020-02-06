package com.android.myapplication.movies.api

import NETWORK_TIMEOUT
import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.myapplication.movies.AppExecutors
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.popularmovies.api.model.Movie
import com.android.myapplication.popularmovies.api.responses.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

public class RemoteDataSource(
    private val appExecutors: AppExecutors,
    private val moviesApi: MoviesApi
) {
    companion object {
        private const val TAG = "RemoteDataSource"
    }

    private var retrieveMoviesRunnable: RetrieveMoviesRunnable? = null
    private var searchMovieRunnable: SearchMovieRunnable? = null
    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    fun getMovies(pageNumber: Int, sortBy: Categories) {
        reset()
        retrieveMoviesRunnable = RetrieveMoviesRunnable(pageNumber = pageNumber, sortBy = sortBy)

        val handler = appExecutors.networkIO.submit(retrieveMoviesRunnable)

        appExecutors.networkIO.schedule(Runnable {
            handler.cancel(true)
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    fun searchMovies(pageNumber: Int, query: String) {
        reset()
        searchMovieRunnable = SearchMovieRunnable(pageNumber = pageNumber, query = query)

        val handler = appExecutors.networkIO.submit(searchMovieRunnable)

        appExecutors.networkIO.schedule(Runnable {
            handler.cancel(true)
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    //resetting the task if it already exists
    private fun reset() {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null
        }
    }

    //this inner class handle the retrieval of movies
    private inner class RetrieveMoviesRunnable(
        private val pageNumber: Int,
        private val sortBy: Categories
    ) : Runnable {
        var cancelRequest = false
        override fun run() {
            Log.d(TAG, "run: ${Thread.currentThread().name}")
            lateinit var response: Response<MoviesResponse>
            try {
                when (sortBy) {
                    Categories.POPULAR -> {
                        response = getPopularMovies(pageNumber).execute()
                    }
                    Categories.TOP_RATED -> {
                        response = getTopRatedMovies(pageNumber).execute()
                    }
                    Categories.UPCOMING -> {
                        response = getUpcomingMovies(pageNumber).execute()
                    }
                }
                if (cancelRequest) {
                    //IF User cancel the request, should return
                    return
                }
                //success
                if (response.code() == 200) {
                    //get movies from the response body
                    val fetchedMovies = response.body()?.movies

                    if (pageNumber == 1) {
                        //post the movies to livedata, to update the ui
                        _movieList.postValue(fetchedMovies)
                    } else {
                        //if page>1, we want to append the new fetched movies to the current movies, instead of replacing the current one
                        val currentMovies = _movieList.value?.toMutableList()
                        fetchedMovies?.forEach { movie ->
                            currentMovies?.add(movie)
                        }
                        _movieList.postValue(currentMovies)
                    }
                } else {
                    //code!=200 (not OK)
                    val error = response.errorBody()
                    Log.e(TAG, "run: response error= ${error}")
                    _movieList.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _movieList.postValue(null)
            }
        }

        private fun getPopularMovies(page: Int): Call<MoviesResponse> {
            return moviesApi.getPopularMovies(page = page)
        }

        private fun getTopRatedMovies(page: Int): Call<MoviesResponse> {
            return moviesApi.getTopRatedMovies(page = page)
        }

        private fun getUpcomingMovies(page: Int): Call<MoviesResponse> {
            return moviesApi.geUpcomingMovies(page)
        }

        //METHOD to handle the cancel flag
        fun cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request")
            cancelRequest = true
        }
    }


    private inner class SearchMovieRunnable(private val pageNumber: Int, private val query: String) :
        Runnable {
        var cancelRequest = false
        override fun run() {
            Log.d(TAG, "run: ${Thread.currentThread().name}")
            try {
                val response: Response<MoviesResponse> = moviesApi.searchMovies(query = query, page = pageNumber).execute()
                if (cancelRequest) {
                    //IF User cancel the request, should return
                    return
                }
                //success
                if (response.code() == 200) {
                    //get movies from the response body
                    val fetchedMovies = response.body()?.movies

                    if (pageNumber == 1) {
                        //post the movies to livedata, to update the ui
                        _movieList.postValue(fetchedMovies)
                    } else {
                        //if page>1, we want to append the new fetched movies to the current movies, instead of replacing the current one
                        val currentMovies = _movieList.value?.toMutableList()
                        fetchedMovies?.forEach { movie ->
                            currentMovies?.add(movie)
                        }
                        _movieList.postValue(currentMovies)
                    }
                } else {
                    //code!=200 (not OK)
                    val error = response.errorBody()
                    Log.e(TAG, "run: response error= ${error}")
                    _movieList.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _movieList.postValue(null)
            }
        }
    }
}