package com.android.myapplication.movies.ui.detail

import PAGE_CAST
import PAGE_COUNT
import PAGE_INFO
import PAGE_REVIEW
import PAGE_VIDEOS
import YOUTUBE_BASE_URL
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.ActivityDetailBinding
import com.android.myapplication.movies.ui.detail.fragments.*
import com.android.myapplication.movies.util.EventObserver
import com.android.myapplication.popularmovies.api.model.Movie
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val INTENT_EXTRA_MOVIE = "movie"

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailFragmentViewModel by viewModel {

        parametersOf(
            intent?.extras?.getParcelable(INTENT_EXTRA_MOVIE) ?: Movie()
        )
    }
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initViewPager()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMovieDetails()
    }


    fun initViewPager() {
        viewPager = binding.contentDetail.viewpager
        tabLayout = binding.contentDetail.tabs
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return PAGE_COUNT
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    PAGE_INFO -> GeneralDetailFragment()
                    PAGE_VIDEOS -> TrailersFragment()
                    PAGE_REVIEW -> ReviewsFragment()
                    PAGE_CAST -> CastFragment()
                    else -> throw IndexOutOfBoundsException()
                }
            }

        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            PAGE_INFO -> "Info"
            PAGE_VIDEOS -> "Trailers"
            PAGE_REVIEW -> "Reviews"
            PAGE_CAST -> "Casts"
            else -> null
        }
    }


    companion object {
        private const val TAG = "DetailActivity"
        fun getIntent(movie:Movie, context: Context): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_MOVIE, movie)
            return intent
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.cancelRequest()
    }
}
