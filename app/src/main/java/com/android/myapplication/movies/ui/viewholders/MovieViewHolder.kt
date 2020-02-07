package com.android.myapplication.movies.ui.viewholders

import IMAGE_BASE_URL
import IMAGE_FILE_SIZE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.popularmovies.api.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieViewHolder private constructor(
      itemView: View
     ,private val onMovieClickListener:(Movie)->Unit
) : RecyclerView.ViewHolder(itemView) {
    private val movieImage = itemView.findViewById<ImageView>(R.id.movie_image)
    private val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
    private lateinit var movie:Movie


    companion object{
        private const val TAG = "MovieViewHolder"
        fun getInstance(parent:ViewGroup,onMovieClickListener: (Movie) -> Unit): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.movie_list_item,parent,false)
            return MovieViewHolder(
                itemView,
                onMovieClickListener
            )
        }
    }
    init {
        itemView.setOnClickListener {
            Log.d(TAG, "itemview:clicked ")
            onMovieClickListener.invoke(this.movie)
        }
    }

    fun bind(movie: Movie) {
        this.movie = movie
        val image = IMAGE_BASE_URL + IMAGE_FILE_SIZE + movie.posterPath
        Glide.with(itemView.context)
            .load(image)
            .apply(
                RequestOptions()
                .error(R.drawable.ic_broken_image)
            ).into(movieImage)

        movieTitle.setText(movie.title)
    }


}