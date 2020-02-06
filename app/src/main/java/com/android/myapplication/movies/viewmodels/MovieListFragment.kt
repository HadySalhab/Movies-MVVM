package com.android.myapplication.movies.viewmodels


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.android.myapplication.movies.R
import com.android.myapplication.movies.util.Categories
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment() {
    companion object {
        private const val TAG = "MovieListFragment"
    }
    private val movieListViewModel by viewModel<MovieListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
           testRetrofitApi()
        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer { movies->
            movies?.let {
                movies.forEach {
                    Log.d(TAG, "movie Title = ${it.title} ")
                }
            }
        })
        }

    fun testRetrofitApi(){
        getMovies(1,Categories.POPULAR)
    }
    fun getMovies(pageNumber:Int,sortBy:Categories){
        movieListViewModel.getMovies(pageNumber,sortBy)
    }


}
