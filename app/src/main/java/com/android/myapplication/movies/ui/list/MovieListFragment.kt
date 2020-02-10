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
import com.android.myapplication.movies.util.Category
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
    private lateinit var errorScreen: View

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onMovieClick(movieId: Long)
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
      //  initRecyclerView()
        setHasOptionsMenu(true)
        return rootView
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            this@MovieListFragment.adapter =
                MoviesRecyclerAdapter(
                    onMovieClickListener
                )
            addItemDecoration(RecyclerViewDecoration())
            adapter = this@MovieListFragment.adapter

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieListViewModel.getListMovie(1,Category.TOPRATED)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        movieListViewModel.movies.observe(viewLifecycleOwner, Observer { resourcelistMovies->
            Log.d(TAG, "on Changed: ${resourcelistMovies.status}")
            resourcelistMovies.data?.let {data->
                Log.d(TAG, "data: ${data.size}")
                data.forEach {
                    Log.d(TAG, "subscribeObservers: ${it}")
                }
            }
        })
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
                    onActionViewCollapsed()
                    searchItem.collapseActionView()
                    hideSoftKeyboard()
                    return false
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
                true
            }
            R.id.menu_item_popular_movies -> {
                true
            }
            R.id.menu_item_top_rated -> {
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

    private val onMovieClickListener: (movie: Movie) -> Unit = { movie ->
        Log.d(TAG, "onMovieClick: ${callbacks} ")
        callbacks?.onMovieClick(movie.id)
    }

    override fun onPause() {
        super.onPause()
        //savePreferences()
    }

/*    fun savePreferences() {
        PreferencesStorage.setStoredQuery(this.requireContext(), movieListViewModel.query)
        val category = when (movieListViewModel.categories) {
            Categories.TOP_RATED -> "top_rated"
            Categories.UPCOMING -> "upComing"
            else -> "popular"
        }
        PreferencesStorage.setStoredCategory(this.requireContext(), category)
    }*/
}


