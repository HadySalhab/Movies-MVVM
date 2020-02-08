package com.android.myapplication.movies.ui.detail

import YOUTUBE_BASE_URL
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.R
import com.android.myapplication.movies.ui.detail.fragments.DetailFragment
import com.android.myapplication.movies.util.EventObserver
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val INTENT_EXTRA = "movie_id"

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        displayDetailFragment(savedInstanceState)
    }

    fun displayDetailFragment(savedInstanceState: Bundle?) {
        val id = intent?.extras?.getLong(INTENT_EXTRA)
        val isFragmentContainerEmpty = savedInstanceState == null
        id?.let {
            if (isFragmentContainerEmpty) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.detail_container, DetailFragment.getInstance(id))
                    .commit()
            }
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
