package com.android.myapplication.movies.ui.detail.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.util.Event
import com.android.myapplication.movies.util.RemoteToLocal
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse

class DetailFragmentViewModel(app: Application,
                              private val repository: MoviesRepository,
                              private val remoteToLocal: RemoteToLocal
) : AndroidViewModel(app) {
    companion object {
        private const val TAG = "DetailActivityViewModel"
    }

    private val _showVideo = MutableLiveData<Event<Unit>>()
    val showVideo: LiveData<Event<Unit>>
        get() = _showVideo

    val detailMovieResponse: LiveData<MovieDetailsResponse> = repository.detailMovieResponse
    fun getDetails(id: Long) {
        repository.getDetails(id)
    }

    val movieDetail = Transformations.map(detailMovieResponse) { detailMovieResponse ->
        remoteToLocal.getMovieDetails(detailMovieResponse)
    }
    val castDetails = Transformations.map(detailMovieResponse){detailMovieResponse->
        remoteToLocal.getCastDetails(detailMovieResponse)
    }
    val reviewsDetails = Transformations.map(detailMovieResponse){detailMovieResponse->
        remoteToLocal.getReviewDetails(detailMovieResponse)
    }
    val isReviewListEmpty = Transformations.map(reviewsDetails){ reviews->
        reviews.isNullOrEmpty()
    }
    val trailersDetails = Transformations.map(detailMovieResponse){detailMovieResponse->
        remoteToLocal.getTrailerDetails(detailMovieResponse)
    }
    val isVideoListEmpty = Transformations.map(trailersDetails){ videos->
        videos.isNullOrEmpty()
    }

    fun onBackPosterClicked(){
        _showVideo.value = Event(Unit)
    }
}