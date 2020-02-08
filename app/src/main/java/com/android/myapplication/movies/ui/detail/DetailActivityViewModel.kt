package com.android.myapplication.movies.ui.detail

import YOUTUBE_BASE_URL
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.ui.detail.fragments.TrailersFragment
import com.android.myapplication.movies.util.Event
import com.android.myapplication.movies.util.RemoteToLocal
import com.android.myapplication.popularmovies.api.model.Video
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse

class DetailActivityViewModel(app: Application,
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
        Log.d(TAG, "${remoteToLocal.getMovieDetails(detailMovieResponse)} ")
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