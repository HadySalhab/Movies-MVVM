package com.android.myapplication.movies.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.myapplication.movies.R
import com.android.myapplication.movies.ui.detail.fragments.DetailFragment
import com.android.myapplication.popularmovies.api.model.Movie

private const val INTENT_EXTRA = "movie_id"

class DetailActivity : AppCompatActivity() {
    private lateinit var movies: List<Movie>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //initViewPager()
        displayDetailFragment(savedInstanceState)
    }

/*    fun initViewPager() {
        movies = viewModel.movieList.value?: emptyList()
        viewPager = findViewById(R.id.detail_container)
        val fragmentManager = supportFragmentManager
        viewPager.adapter = object :
            FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                val movie = viewModel.movieList.value?.get(position)
                return DetailFragment.getInstance(movieId = movie?.id)
            }

            override fun getCount(): Int {
                return viewModel.movieList.value?.size ?: 0
            }

        }
    }*/

    fun displayDetailFragment(savedInstanceState: Bundle?) {
        val id = intent?.extras?.getLong(INTENT_EXTRA)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.detail_container, DetailFragment.getInstance(id))
                .commit()
        }

    }

    companion object {
        private const val TAG = "DetailActivity"
        fun getIntent(movieId: Long, context: Context): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA, movieId)
            return intent
        }
    }
}
