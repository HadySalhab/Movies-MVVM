package com.android.myapplication.movies.ui.common

import IMAGE_BACKDROP_FILE_SIZE
import IMAGE_BASE_URL
import IMAGE_FILE_SIZE
import YOUTUBE_THUMBNAIL_BASE_URL
import YOUTUBE_THUMBNAIL_URL_JPG
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.models.MovieDetails
import com.android.myapplication.movies.ui.detail.fragments.CastAdapter
import com.android.myapplication.movies.ui.detail.fragments.ReviewAdapter
import com.android.myapplication.movies.ui.detail.fragments.TrailerAdapter
import com.android.myapplication.movies.util.Resource
import com.android.myapplication.popularmovies.api.model.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("frontImage")
fun setFrontImage(imageView: ImageView, imageUrl: String?) {
    val image = IMAGE_BASE_URL + IMAGE_FILE_SIZE + imageUrl
    Glide.with(imageView.context)
        .load(image)
        .apply(
            RequestOptions()
                .error(R.drawable.ic_broken_image)
        ).into(imageView)

}

@BindingAdapter("backgroundImage")
fun setBackgroundImage(imageView: ImageView, imageUrl: String?) {
    val image = IMAGE_BASE_URL + IMAGE_BACKDROP_FILE_SIZE + imageUrl
    Glide.with(imageView.context)
        .load(image)
        .apply(
            RequestOptions()
                .error(R.drawable.ic_broken_image)
        ).into(imageView)

}

@BindingAdapter("videoImage")
fun setVideoImage(imageView: ImageView, videoKey: String?) {
    val image = YOUTUBE_THUMBNAIL_BASE_URL + videoKey +
            YOUTUBE_THUMBNAIL_URL_JPG
    Glide.with(imageView.context)
        .load(image)
        .apply(
            RequestOptions()
                .error(R.drawable.ic_broken_image)
        ).into(imageView)

}

@BindingAdapter("voteAvg")
fun setVoteAvg(textView: TextView, voteAvg: Double?) {
    val voteAvgString = voteAvg.toString()
    val text = voteAvgString + "/10"
    textView.setText(text)
}

@BindingAdapter("genre")
fun setGenre(textView: TextView, genres: List<Genre>?) {
    val stringBuild = StringBuilder()
    genres?.forEach { genres ->
        stringBuild.append("${genres.name}\n")
    }
    textView.setText(stringBuild)
}

@BindingAdapter("reviewAdapterList")
fun RecyclerView.submitReviewList(repoResult: Resource<MovieDetails?>?) {
    val adapter = this.adapter as ReviewAdapter
    repoResult?.let {
        repoResult.data?.let {
            val reviews = it.reviews
            reviews?.let {
                adapter.submitList(it)
            }
        }
    }
}

@BindingAdapter("videoAdapterList")
fun RecyclerView.submitVideoList(repoResult: Resource<MovieDetails?>?) {
    val adapter = this.adapter as TrailerAdapter
    repoResult?.let {
        repoResult.data?.let {
            val trailers = it.trailers
            trailers?.let {
                adapter.submitList(it)
            }
        }
    }
}

@BindingAdapter("castAdapterList")
fun RecyclerView.submitCastList(repoResult: Resource<MovieDetails?>?) {
    val adapter = this.adapter as CastAdapter
    repoResult?.let {
        repoResult.data?.let {
            val reviews = it.casts
            reviews?.let {
                adapter.submitList(it)
            }
        }
    }
}

@BindingAdapter("emptyCastVisibility")
fun View.emptyCastVisibility(repoResult: Resource<MovieDetails?>?) {
    repoResult?.let {
        repoResult.data?.let {
            if (repoResult is Resource.Success && repoResult.data.casts.isNullOrEmpty()) {
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }
        }
    }

}

@BindingAdapter("emptyTrailersVisibility")
fun View.emptyTrailersVisibility(repoResult: Resource<MovieDetails?>?) {
    repoResult?.let {
        repoResult.data?.let {
            if (repoResult is Resource.Success && repoResult.data.trailers.isNullOrEmpty()) {
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }
        }
    }

}

@BindingAdapter("emptyReviewsVisibility")
fun View.emptyReviewsVisibility(repoResult: Resource<MovieDetails?>?) {
    repoResult?.let {
        repoResult.data?.let {
            if (repoResult is Resource.Success && repoResult.data.reviews.isNullOrEmpty()) {
                this.visibility = View.VISIBLE
            } else {
                this.visibility = View.GONE
            }
        }
    }

}

@BindingAdapter("detailNetworkErrorVisibility")
fun View.detailNetworkErrorVisibility(repoResult: Resource<MovieDetails?>?) {
    repoResult?.let {
        if (repoResult is Resource.Error) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }

    }
}

@BindingAdapter("detailProgressBarVisibility")
fun ProgressBar.detailProgressBarVisibility(repoResult: Resource<MovieDetails?>?) {
    repoResult?.let {
        if (repoResult is Resource.Loading) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }

    }
}