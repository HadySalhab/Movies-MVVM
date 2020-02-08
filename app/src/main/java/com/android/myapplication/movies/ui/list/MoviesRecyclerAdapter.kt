package com.android.myapplication.movies.ui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.android.myapplication.popularmovies.api.model.Movie

class MoviesRecyclerAdapter (private val onMovieClickListener:(Movie)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            is MovieViewHolder ->{
                holder.bind(movies.get(position))
            }
        }
    }

    fun submitList(list:List<Movie>){
        movies = list.toMutableList()
        notifyDataSetChanged()
    }

}