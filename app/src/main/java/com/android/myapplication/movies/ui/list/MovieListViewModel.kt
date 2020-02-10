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

class MovieListViewModel(private val repository: MoviesRepository) : ViewModel() {
    enum class ListViewState {
        POPULAR,
        TOP_RATED,
        UPCOMING,
        SEARCH
    }
    companion object {
        private const val TAG = "MovieListViewModel"
    }

    private val _viewState = MutableLiveData<ListViewState>()
    val viewState: LiveData<ListViewState>
        get() = _viewState

    private val _movies = MediatorLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    init {
        _viewState.value = ListViewState.POPULAR
    }

  /*  fun searchListMovie(pageNumber: Int, query: String) {
        val repositorySource = repository.searchListMovie(pageNumber, query)
        _movies.addSource(repositorySource) { resourceListMovie ->
            _movies.value = resourceListMovie
        }
    }*/

    fun getListMovie(pageNumber: Int, category: Category) {
        val repositorySource = repository.getListMovie(pageNumber, category)
        _movies.addSource(repositorySource) { resourceListMovie ->
            Log.d(TAG, "getListMovie: ${resourceListMovie.data}")
            _movies.value = resourceListMovie
        }
    }

}