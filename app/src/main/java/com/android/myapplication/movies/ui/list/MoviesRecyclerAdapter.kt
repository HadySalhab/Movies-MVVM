package com.android.myapplication.movies.ui.list

import android.text.TextUtils
import android.view.ActionProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.databinding.MovieListItemBinding
import com.android.myapplication.popularmovies.api.model.Movie
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import java.util.*

class MoviesRecyclerAdapter(
    private val onMovieClickListener: (Movie) -> Unit,
    val preloadSizeProvider: ViewPreloadSizeProvider<String>,
    val requestManager: RequestManager?
) :
    androidx.recyclerview.widget.ListAdapter<Movie, MoviesRecyclerAdapter.MovieViewHolder>(
        MovieDiffUtil()
    ), ListPreloader.PreloadModelProvider<String> {
    //preload of type string, because Glide caches the url

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.getInstance(parent, onMovieClickListener,preloadSizeProvider)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)

    }


    class MovieViewHolder private constructor(
        private val binding: MovieListItemBinding
        , val onMovieClickListener: (Movie) -> Unit,
        val preloadSizeProvider: ViewPreloadSizeProvider<String>
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            private const val TAG = "MovieViewHolder"
            fun getInstance(
                parent: ViewGroup,
                onMovieClickListener: (Movie) -> Unit,
                preloadSizeProvider: ViewPreloadSizeProvider<String>
            ): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemBinding.inflate(inflater, parent, false)
                return MovieViewHolder(
                    binding,
                    onMovieClickListener,preloadSizeProvider
                )
            }
        }

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.viewHolder = this
            preloadSizeProvider.setView(binding.movieImage)
            binding.executePendingBindings()
        }


    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun getPreloadItems(position: Int): MutableList<String> {
        val url = getItem(position).posterPath
        if (url == null || TextUtils.isEmpty(url)) {
            return Collections.emptyList()
        } else {
            return Collections.singletonList(url)
        }
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*>? =
        requestManager?.load(item)
}