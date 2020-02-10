package com.android.myapplication.movies.ui.list


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.FragmentMovieListBinding
import com.android.myapplication.movies.persistence.PreferencesStorage
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
    private lateinit var binding: FragmentMovieListBinding

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
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        binding.viewModel = movieListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerview.apply {
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
        getMovieList()
    }
    fun getMovieList(){
        val query = movieListViewModel.query
        if(query!=null){
            movieListViewModel.searchListMovie(1,query =query)
        }else{
            movieListViewModel.getListMovie(1,movieListViewModel.category)
        }
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
                    query?.let {
                        movieListViewModel.searchListMovie(1, query)
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
                movieListViewModel.getListMovie(pageNumber = 1, category = Category.UPCOMING)
                true
            }
            R.id.menu_item_popular_movies -> {
                movieListViewModel.getListMovie(pageNumber = 1, category = Category.POPULAR)
                true
            }
            R.id.menu_item_top_rated -> {
                movieListViewModel.getListMovie(pageNumber = 1, category = Category.TOPRATED)
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
        Log.d(TAG, "onMovieClick: ")
    }

    override fun onPause() {
        super.onPause()
        savePreferences()
    }

    fun savePreferences() {
        PreferencesStorage.setStoredQuery(this.requireContext(), movieListViewModel.query)
        PreferencesStorage.setStoredCategory(this.requireContext(), movieListViewModel.category)
    }
}


