package com.android.myapplication.movies.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.util.Category
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.popularmovies.api.model.Movie

const val QUERY_EXHAUSTED = "No Available Data"

class MovieListViewModel(private val repository: MoviesRepository) : ViewModel() {
    enum class ListViewState {
        GET,
        SEARCH
    }

    companion object {
        private const val TAG = "MovieListViewModel"
    }

    var isQueryExhausted: Boolean = false
    var isPerformingQuery: Boolean = false
    var query: String? = null
    var category: Category = Category.POPULAR

    private val _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int>
        get() = _pageNumber

    private val _viewState = MutableLiveData<ListViewState>()
    val viewState: LiveData<ListViewState>
        get() = _viewState

    private val _movies = MediatorLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    init {
        _pageNumber.value = 1
        _viewState.value = ListViewState.GET
    }

    fun searchListMovie(pageNumber: Int, query: String) {
        if (!isPerformingQuery) {
            _pageNumber.value = pageNumber
            this.query = query
            isQueryExhausted = false
            executeSearchRequest()
        }
    }

    private fun executeSearchRequest() {
        isPerformingQuery = true
        _viewState.value = ListViewState.SEARCH
        query?.let {
            val repositorySource = repository.searchListMovie(pageNumber.value!!, it)
            _movies.addSource(repositorySource) { resourceListMovie ->
                if (resourceListMovie != null) {
                    _movies.value = resourceListMovie
                    if (resourceListMovie is Resource.Success) {
                        isPerformingQuery = false
                        if (resourceListMovie.data != null) {
                            if (resourceListMovie.data.isEmpty()) {
                                Log.d(TAG, "executeSearchRequest: Query is exhausted")
                                _movies.value =
                                    Resource.Error(QUERY_EXHAUSTED, resourceListMovie.data)
                            }
                        }
                        _movies.removeSource(repositorySource)
                    } else if (resourceListMovie is Resource.Error) {
                        isPerformingQuery = false
                        _movies.removeSource(repositorySource)
                    }
                } else {
                    _movies.removeSource(repositorySource)
                }
            }
        }
    }

    fun getListMovie(pageNumber: Int, category: Category) {
        if (!isPerformingQuery) {
            _pageNumber.value = pageNumber
            this.category = category
            isQueryExhausted = false
            executeGetRequest()
        }
    }

    private fun executeGetRequest() {
        isPerformingQuery = true
        _viewState.value = ListViewState.GET
        val repositorySource = repository.getListMovie(pageNumber.value!!, this.category)
        _movies.addSource(repositorySource) { resourceListMovie ->
            if (resourceListMovie != null) {
                _movies.value = resourceListMovie
                if (resourceListMovie is Resource.Success) {
                    isPerformingQuery = false
                    if (resourceListMovie.data != null) {
                        if (resourceListMovie.data.isEmpty()) {
                            Log.d(TAG, "executeSearchRequest: Query is exhausted")
                            _movies.value =
                                Resource.Error(QUERY_EXHAUSTED, resourceListMovie.data)
                        }
                    }
                    _movies.removeSource(repositorySource)
                } else if (resourceListMovie is Resource.Error) {
                    isPerformingQuery = false
                    _movies.removeSource(repositorySource)
                }
            } else {
                _movies.removeSource(repositorySource)
            }

        }
    }

}