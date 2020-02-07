package com.android.myapplication.movies.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.myapplication.movies.repository.MoviesRepository
import com.android.myapplication.movies.util.RemoteToLocal
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse

class DetailActivityViewModel(
    private val repository: MoviesRepository,
    private val remoteToLocal: RemoteToLocal
) : ViewModel() {
    companion object {
        private const val TAG = "DetailActivityViewModel"
    }

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
}