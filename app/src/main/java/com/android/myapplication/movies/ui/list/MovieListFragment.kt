package com.android.myapplication.movies.ui.list


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.persistence.PreferencesStorage
import com.android.myapplication.movies.util.Categories
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.android.myapplication.popularmovies.api.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieListFragment : Fragment() {
    companion object {
        private const val TAG = "MovieListFragment"
    }

    private val movieListViewModel by viewModel<MovieListViewModel>()
    private lateinit var adapter: MoviesRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingView: View
    private lateinit var paginationLoadingView: View
    private lateinit var errorScreen:View

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onMovieClick(movieId:Long)
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
        loadingView = rootView.findViewById(R.id.loading_view)
        paginationLoadingView = rootView.findViewById(R.id.pagination_loading_view)
        errorScreen = rootView.findViewById(R.id.error_screen)
        initRecyclerView()
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fireRequest()
        movieListViewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                hideDisplayLoading()
                adapter.submitList(movies)
                movieListViewModel.areMoviesRetrieved = true
                hideErrorScreen()
            }
        })
        movieListViewModel.retrieveMoviesTimeOut.observe(viewLifecycleOwner, Observer {isTimedOut->
                if(isTimedOut && !movieListViewModel.areMoviesRetrieved){
                    showErrorScreen()
                }
            })
    }
    fun showErrorScreen(){
        errorScreen.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        paginationLoadingView.visibility = View.GONE
        recyclerView.visibility = View.INVISIBLE
   }
    fun hideErrorScreen(){
        errorScreen.visibility = View.GONE
        loadingView.visibility = View.GONE
        paginationLoadingView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun hideDisplayLoading() {
        loadingView.visibility = View.GONE
        paginationLoadingView.visibility = View.GONE
    }


    private fun showDisplayLoading() {
        movieListViewModel.areMoviesRetrieved =false
        loadingView.visibility = View.VISIBLE
        errorScreen.visibility = View.GONE
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            this@MovieListFragment.adapter =
                MoviesRecyclerAdapter(
                    onMovieClickListener
                )
            addItemDecoration(RecyclerViewDecoration())
            adapter = this@MovieListFragment.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        paginationLoadingView.visibility = View.VISIBLE
                        if (movieListViewModel.query == null) {
                            movieListViewModel.getMovieNextPage()
                        } else {
                            movieListViewModel.searchNextPage()
                        }
                    }
                }
            })
        }
    }

    private fun fireRequest() {
        val query = PreferencesStorage.getStoredQuery(this.requireContext())
        query?.let {
            showDisplayLoading()
            movieListViewModel.query = it
            movieListViewModel.searchMovies(1, it)
            return
        }
        val category = PreferencesStorage.getStoredCategory(this.requireContext())
        when (category) {
            "popular" -> getMovies(1, Categories.POPULAR)
            "top_rated" -> getMovies(1, Categories.TOP_RATED)
            "upComing" -> getMovies(1, Categories.UPCOMING)
        }
    }


    fun getMovies(pageNumber: Int, sortBy: Categories) {
        showDisplayLoading()
        movieListViewModel.query = null
        movieListViewModel.categories = sortBy
        movieListViewModel.getMovies(pageNumber, sortBy)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_movie_list_menu, menu)
        setUpSearchViewListener(menu)
    }

    private fun setUpSearchViewListener(menu: Menu) {
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            queryHint = context.getString(R.string.query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showDisplayLoading()

                    query?.let {
                        movieListViewModel.query = it
                        movieListViewModel.searchMovies(1, it)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_up_coming -> {
                getMovies(pageNumber = 1, sortBy = Categories.UPCOMING)
                true
            }
            R.id.menu_item_popular_movies -> {
                getMovies(pageNumber = 1, sortBy = Categories.POPULAR)
                true
            }
            R.id.menu_item_top_rated -> {
                getMovies(pageNumber = 1, sortBy = Categories.TOP_RATED)
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

    private val onMovieClickListener: (movie: Movie) -> Unit = { movie->
        Log.d(TAG, "onMovieClick: ${callbacks} ")
        callbacks?.onMovieClick(movie.id)
    }

    override fun onPause() {
        super.onPause()
        savePreferences()
    }

    fun savePreferences() {
        PreferencesStorage.setStoredQuery(this.requireContext(), movieListViewModel.query)
        val category = when (movieListViewModel.categories) {
            Categories.TOP_RATED -> "top_rated"
            Categories.UPCOMING -> "upComing"
            else -> "popular"
        }
        PreferencesStorage.setStoredCategory(this.requireContext(), category)
    }
}


