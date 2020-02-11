package com.android.myapplication.movies.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.myapplication.movies.persistence.PreferencesStorage
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.util.Category
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.popularmovies.api.model.Movie

const val QUERY_EXHAUSTED = "No Available Data"

class MovieListViewModel(private val repository: MoviesRepository, val app: Application) :
    AndroidViewModel(app) {
    enum class ListViewState {
        GET,
        SEARCH
    }

    companion object {
        private const val TAG = "MovieListViewModel"
    }

    var isQueryExhausted: Boolean = false
    var isPerformingQuery: Boolean = false
    var query: String? = PreferencesStorage.getStoredQuery(app.applicationContext)
    var category: Category = PreferencesStorage.getStoredCategory(app.applicationContext)

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int>
        get() = _pageNumber


    private val _movies = MediatorLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    init {
        _pageNumber.value = 1
    }

    //for first page
    fun getList() {
        if (!isPerformingQuery) {
            isQueryExhausted = false
            isPerformingQuery = true
            executeRequest()
        }
    }

    fun getNextPage() {
        if (!isQueryExhausted && !isPerformingQuery) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            executeRequest()
        }
    }

    private fun executeRequest() {
        val repositorySource: LiveData<Resource<List<Movie>>>
        if (query != null) {
            repositorySource = repository.searchListMovie(pageNumber.value!!, query!!)
        } else {
            repositorySource = repository.getListMovie(pageNumber.value!!, this.category)
        }
        registerMediatorLiveData(repositorySource)
    }


    fun registerMediatorLiveData(repositorySource: LiveData<Resource<List<Movie>>>) {
        _movies.addSource(repositorySource) { resourceListMovie ->
            if (resourceListMovie != null) {
                _movies.value = resourceListMovie
                //success is reached, no more loading , aka no more Performing query
                if (resourceListMovie is Resource.Success) {
                    isPerformingQuery = false
                    //checking if empty data returned when in success state => EmptyApiResponse
                    if (resourceListMovie.data.isNullOrEmpty()) {
                        isQueryExhausted = true
                    }
                    _movies.removeSource(repositorySource)
                    //error is reached, no more loading , aka no more Performing query
                } else if (resourceListMovie is Resource.Error) {
                    isPerformingQuery = false
                    _movies.removeSource(repositorySource)
                }
            } else {
                isPerformingQuery = false
                _movies.removeSource(repositorySource)
            }


        }
    }

    fun resetPageNumber() {
        _pageNumber.value = 1
    }
}
