package com.android.myapplication.movies.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.android.myapplication.movies.R
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.movies.util.HorizontalDottedProgress
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
    private lateinit var loadingView:View


    private var callbacks:Callbacks? =null
    interface Callbacks{
        fun onMovieListFragmentDisplayed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie_list, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerview)
        loadingView =rootView.findViewById(R.id.loading_view)
        initRecyclerView()
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        callbacks?.onMovieListFragmentDisplayed()
        testRetrofitApi()
        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                hideDisplayLoading()
                adapter.submitList(movies)
            }
        })
    }
    private fun hideDisplayLoading(){
        recyclerView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        movieListViewModel.isPerformingQuery = false
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_movie_list_menu,menu)
            setUpSearchViewListener(menu)
    }
    private fun setUpSearchViewListener(menu: Menu) {
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            queryHint = context.getString(R.string.query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    displayLoading()

                   query?.let {
                       movieListViewModel.searchMovies(1,it)
                   }
                    onActionViewCollapsed()
                    searchItem.collapseActionView()
                    hideSoftKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }

    }
    private fun displayLoading(){
        loadingView.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        movieListViewModel.isPerformingQuery = true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     return  when(item.itemId){
            R.id.menu_item_up_coming->{
                displayLoading()
                getMovies(pageNumber = 1,sortBy = Categories.UPCOMING)
                true
            }
            R.id.menu_item_popular_movies->{
                displayLoading()
                getMovies(pageNumber = 1,sortBy = Categories.POPULAR)
                true
            }
            R.id.menu_item_top_rated->{
                displayLoading()
                getMovies(pageNumber = 1,sortBy = Categories.TOP_RATED)
                true
            }

         else -> super.onOptionsItemSelected(item)
     }
    }

    fun hideSoftKeyboard() {
        view?.let {
            val imm = context?.getSystemService<InputMethodManager>()

            imm?.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onItemSelected(position: Int, item: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


