package com.android.myapplication.movies.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.myapplication.movies.persistence.PreferencesStorage
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.util.Category
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.popularmovies.api.model.Movie

class MovieListViewModel(private val repository: MoviesRepository, val app: Application) :
    AndroidViewModel(app) {
    companion object {
        private const val TAG = "MovieListViewModel"
    }

    var isQueryExhausted: Boolean =
        false //query is exhausted when : data is null or empty, data returned < EXPECTED Total Result

    var isPerformingQuery: Boolean =
        false //is performing query, as long as In loading state, not( Error or success)
    var query: String? = PreferencesStorage.getStoredQuery(app.applicationContext)
    var category: Category = PreferencesStorage.getStoredCategory(app.applicationContext)
    var cancelRequest = false

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

    //for next page
    fun getNextPage() {
        if (!isQueryExhausted && !isPerformingQuery) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            executeRequest()
        }
    }

    private fun executeRequest() {
        Log.d(TAG, "executeRequest: ${pageNumber.value}")
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
            if (!cancelRequest) {
                if (resourceListMovie != null) {
                    _movies.value = resourceListMovie
                    if (resourceListMovie is Resource.Success || resourceListMovie is Resource.Error) {
                        unregisterMediatorLiveData(repositorySource)
                        resourceListMovie.data?.let {
                            //if data is null (when error or succes) recyclerview will be invisible, so the user cannot scroll to fetch the next page anyway
                            if (it.size < _pageNumber.value!! * 10) {
                                Log.d(TAG, "registerMediatorLiveData: ${it.size}")
                                Log.d(TAG, "registerMediatorLiveData: ${_pageNumber.value}")
                                isQueryExhausted = true
                            }
                        }
                    }
                } else {
                    unregisterMediatorLiveData(repositorySource)
                }
            } else {
                unregisterMediatorLiveData(repositorySource)
            }

        }
    }

    //unregister when whole response is null or when response ==Success or Error
    private fun unregisterMediatorLiveData(repositorySource: LiveData<Resource<List<Movie>>>) {
        isPerformingQuery = false
        _movies.removeSource(repositorySource)
    }

    fun resetPageNumber() {
        _pageNumber.value = 1
    }

    fun cancelRequest() {
        cancelRequest = true
        _pageNumber.value = 1
    }
}
