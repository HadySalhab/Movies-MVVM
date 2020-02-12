package com.android.myapplication.movies.repository

import androidx.lifecycle.LiveData
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.api.responses.ApiEmptyResponse
import com.android.myapplication.movies.api.responses.ApiErrorResponse
import com.android.myapplication.movies.api.responses.ApiResponse
import com.android.myapplication.movies.api.responses.ApiSuccessResponse
import com.android.myapplication.movies.models.MovieDetails
import com.android.myapplication.movies.util.NetworkBoundResource
import com.android.myapplication.movies.util.NetworkBoundResourceNoCaching
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.popularmovies.api.model.Movie
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse
import com.android.myapplication.popularmovies.api.responses.MoviesResponse

class MovieDetailRepository(
    private val movieApi: MoviesApi
) {

    fun getMovieDetail(movieId: Long): LiveData<Resource<MovieDetails>> {
        return object : NetworkBoundResourceNoCaching<MovieDetails, MovieDetailsResponse>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<MovieDetailsResponse>) {
                val detailsResponse= response.body
                val videos = detailsResponse.videoResponse?.videos
                val casts = detailsResponse.creditsResponse?.casts
                val reviews = detailsResponse.reviewResponse?.reviews
                result.value = Resource.Success(MovieDetails(videos,reviews,casts))
            }

            override fun handleApiEmptyResponse(response: ApiEmptyResponse<MovieDetailsResponse>) {
                //MovieDetails,has empty videos,casts,reviews
                result.value = Resource.Success(MovieDetails())
            }

            override fun handleApiErrorResponse(response: ApiErrorResponse<MovieDetailsResponse>) {
                result.value = Resource.Error(response.errorMessage,null)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailsResponse>> =
                movieApi.getMovieDetail(movieId)

        }.asLiveData()
    }
}