package com.android.myapplication.movies.ui.common

import IMAGE_BACKDROP_FILE_SIZE
import IMAGE_BASE_URL
import IMAGE_FILE_SIZE
import YOUTUBE_THUMBNAIL_BASE_URL
import YOUTUBE_THUMBNAIL_URL_JPG
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.ui.detail.fragments.CastAdapter
import com.android.myapplication.movies.ui.detail.fragments.ReviewAdapter
import com.android.myapplication.movies.ui.detail.fragments.TrailerAdapter
import com.android.myapplication.popularmovies.api.model.Cast
import com.android.myapplication.popularmovies.api.model.Genre
import com.android.myapplication.popularmovies.api.model.Review
import com.android.myapplication.popularmovies.api.model.Video
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

@BindingAdapter("castAdapterList")
fun RecyclerView.submitList(casts: List<Cast>?) {
    val adapter = this.adapter as CastAdapter
    casts?.let {
        adapter.submitList(casts)
    }
}
@BindingAdapter("reviewAdapterList")
fun RecyclerView.submitReviewList(reviews: List<Review>?) {
    val adapter = this.adapter as ReviewAdapter
    reviews?.let {
        adapter.submitList(reviews)
    }
}

@BindingAdapter("videoAdapterList")
fun RecyclerView.submitVideoList(videos: List<Video>?) {
    val adapter = this.adapter as TrailerAdapter
    videos?.let {
        adapter.submitList(videos)
    }
}

@BindingAdapter("castAdapterList")
fun RecyclerView.submitCastList(casts: List<Cast>?) {
    val adapter = this.adapter as CastAdapter
    casts?.let {
        adapter.submitList(casts)
    }
}