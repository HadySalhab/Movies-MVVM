package com.android.myapplication.movies.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.popularmovies.api.model.Movie

class MovieListViewModel(private val repository: MoviesRepository) : ViewModel() {
    enum class ListViewState {
        POPULAR,
        TOP_RATED,
        UPCOMING,
        SEARCH
    }

    private val _viewState = MutableLiveData<ListViewState>()
    val viewState: LiveData<ListViewState>
        get() = _viewState


    init {
        _viewState.value = ListViewState.POPULAR
    }
}