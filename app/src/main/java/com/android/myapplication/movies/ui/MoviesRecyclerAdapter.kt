package com.android.myapplication.movies.ui

import IMAGE_BASE_URL
import IMAGE_FILE_SIZE
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.myapplication.movies.R
import com.android.myapplication.popularmovies.api.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MoviesRecyclerAdapter (private val onMovieClickListener:()->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies:MutableList<Movie> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.getInstance(parent,onMovieClickListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MovieViewHolder->{
                holder.bind(movies.get(position))
            }
        }
    }

    fun submitList(list:List<Movie>){
        movies = list.toMutableList()
        notifyDataSetChanged()
    }

}