package com.android.myapplication.movies.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.android.myapplication.movies.R
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.android.myapplication.popularmovies.api.model.Movie
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment(), MoviesRecyclerAdapter.Interaction {
    companion object {
        private const val TAG = "MovieListFragment"
    }

    private val movieListViewModel by viewModel<MovieListViewModel>()
    private lateinit var adapter: MoviesRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerview)
        initRecyclerView()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        testRetrofitApi()
        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                adapter.submitList(movies)
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            this@MovieListFragment.adapter = MoviesRecyclerAdapter(this@MovieListFragment)
            addItemDecoration(RecyclerViewDecoration())
            adapter = this@MovieListFragment.adapter
        }
    }

    fun testRetrofitApi() {
        getMovies(1, Categories.POPULAR)
    }

    fun getMovies(pageNumber: Int, sortBy: Categories) {
        movieListViewModel.getMovies(pageNumber, sortBy)
    }

    override fun onItemSelected(position: Int, item: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
